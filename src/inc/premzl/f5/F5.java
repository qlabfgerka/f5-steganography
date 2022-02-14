package inc.premzl.f5;

import inc.premzl.f5.Processing.Image.DCTProcessing;
import inc.premzl.f5.Processing.Image.F5Steganography;
import inc.premzl.f5.Processing.Image.ImageProcessing;
import inc.premzl.f5.Processing.Text.TextProcessing;
import org.opencv.core.Core;

import java.util.List;

public class F5 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 5) throw new Exception("Invalid arguments size");

        String prepended = TextProcessing.getPrependedBinaryContent(args[2]);
        System.out.println(prepended);

        List<double[][][]> blocks = ImageProcessing.getBlocks(ImageProcessing.openImage(args[0]));
        //printBlock(blocks);

        blocks = DCTProcessing.DCT(blocks);
        //printBlock(blocks);

        List<double[]> zigzag = DCTProcessing.zigzag(blocks);
        /*for (double[] block : zigzag) {
            for (int i = 0; i < 64; i++) {
                System.out.printf("%f ", block[i]);
            }
            System.out.println();
        }*/

        List<int[]> quantized = DCTProcessing.quantization(zigzag, Integer.parseInt(args[3]));
        /*System.out.printf("LENGTH: %d", quantized.size());
        for (int[] block : quantized) {
            for (int i = 0; i < 64; i++) {
                System.out.printf("%d ", block[i]);
            }
            System.out.println();
        }*/

        quantized = F5Steganography.hideMessage(quantized, Integer.parseInt(args[3]), Integer.parseInt(args[4]), prepended);
        System.out.println(F5Steganography.readMessage(quantized, Integer.parseInt(args[3]), Integer.parseInt(args[4])));

        /*
            REVERSE

        blocks = DCTProcessing.reverseZigzag(zigzag);

        blocks = DCTProcessing.IDCT(blocks);
        printBlock(blocks);
        */
    }

    private static void printBlock(List<double[][][]> blocks) {
        System.out.println(blocks.size());
        for (double[][][] block : blocks) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.printf("%d/%d/%d\t", (int) block[i][j][0], (int) block[i][j][1], (int) block[i][j][2]);
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }
    }
}
