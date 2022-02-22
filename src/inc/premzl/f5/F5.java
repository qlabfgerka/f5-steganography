package inc.premzl.f5;

import inc.premzl.f5.Cryptography.Cryptography;
import inc.premzl.f5.Models.DecompressionWrapper;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.List;
import java.util.Objects;

import static inc.premzl.f5.Binary.BinaryOperations.*;
import static inc.premzl.f5.Binary.Compression.compress;
import static inc.premzl.f5.Binary.Compression.decompress;
import static inc.premzl.f5.Cryptography.Cryptography.decrypt;
import static inc.premzl.f5.DCT.DCT.*;
import static inc.premzl.f5.Files.FileOperations.*;
import static inc.premzl.f5.Steganography.F5Steganography.hideMessage;
import static inc.premzl.f5.Steganography.F5Steganography.readMessage;

public class F5 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 5) throw new Exception("Invalid arguments size");
        final String compressOutput = "assets\\images\\out.bin";
        final String decodedOutput = "assets\\text\\out.txt";

        if (Objects.equals(args[1], "h")) {
            Mat image = openImage(args[0]);
            List<double[][][]> blocks = getBlocks(image);
            blocks = DCT(blocks);
            List<int[]> quantized = quantization(zigzag(blocks), Integer.parseInt(args[3]));
            hideMessage(quantized,
                    Integer.parseInt(args[3]),
                    Integer.parseInt(args[4]),
                    getPrependedBinaryContent(Cryptography.encrypt(getFileContent(args[2]), args[5])));
            String compressed = compress(quantized, Integer.parseInt(args[3]));
            compressFile(
                    compressOutput,
                    unsignedNumberToBinary(image.rows(), 16)
                            + unsignedNumberToBinary(image.cols(), 16)
                            + unsignedNumberToBinary(compressed.length() % 8, 4)
                            + compressed
            );
        } else if (Objects.equals(args[1], "e")) {
            DecompressionWrapper wrapper = decompress(getBitString(getFileContentBinary(args[0])), Integer.parseInt(args[3]));
            String message = decrypt(getBytes(readMessage(wrapper.getBlocks(),
                    Integer.parseInt(args[3]),
                    Integer.parseInt(args[4]))), args[5]);
            System.out.println(message);
            List<double[][][]> blocks = reverseZigzag(wrapper.getBlocks());
            blocks = IDCT(blocks);
            writeImage(args[2], getMat(blocks, wrapper.getHeight(), wrapper.getWidth()));
            writeToFile(decodedOutput, message);
        }
    }
}
