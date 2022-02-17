package inc.premzl.f5;

import inc.premzl.f5.Models.DecompressionWrapper;
import inc.premzl.f5.Processing.Image.DCTProcessing;
import inc.premzl.f5.Processing.Image.F5Steganography;
import inc.premzl.f5.Processing.Image.ImageProcessing;
import inc.premzl.f5.Processing.Text.BinaryProcessing;
import inc.premzl.f5.Processing.Text.TextProcessing;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.List;
import java.util.Objects;

public class F5 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 5) throw new Exception("Invalid arguments size");
        final String compressOutput = "assets\\images\\out.bin";
        final String decodedOutput = "assets\\text\\out.txt";

        if (Objects.equals(args[1], "h")) {
            String compressed;
            Mat image = ImageProcessing.openImage(args[0]);
            String prepended = TextProcessing.getPrependedBinaryContent(args[2]);
            List<double[][][]> blocks = ImageProcessing.getBlocks(image);
            blocks = DCTProcessing.DCT(blocks);
            List<double[]> zigzag = DCTProcessing.zigzag(blocks);
            List<int[]> quantized = DCTProcessing.quantization(zigzag, Integer.parseInt(args[3]));
            F5Steganography.hideMessage(quantized, Integer.parseInt(args[3]), Integer.parseInt(args[4]), prepended);
            compressed = BinaryProcessing.compress(quantized);
            ImageProcessing.compressFile(
                    compressOutput,
                    BinaryProcessing.unsignedNumberToBinary(image.rows(), 16)
                            + BinaryProcessing.unsignedNumberToBinary(image.cols(), 16)
                            + BinaryProcessing.unsignedNumberToBinary(compressed.length() % 8, 4)
                            + compressed
            );
        } else if (Objects.equals(args[1], "e")) {
            String binary = TextProcessing.getBitString(TextProcessing.getFileContentBinary(args[0]));
            DecompressionWrapper wrapper = BinaryProcessing.decompress(binary);
            String binaryMessage = F5Steganography.readMessage(wrapper.getBlocks(),
                    Integer.parseInt(args[4]));
            List<double[][][]> blocks = DCTProcessing.reverseZigzag(wrapper.getBlocks());
            blocks = DCTProcessing.IDCT(blocks);
            Mat image = ImageProcessing.getMat(blocks, wrapper.getHeight(), wrapper.getWidth());
            ImageProcessing.writeImage(args[2], image);
            TextProcessing.writeToFile(decodedOutput, TextProcessing.getString(binaryMessage));
        }
    }
}
