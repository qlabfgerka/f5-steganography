package inc.premzl.f5;

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
        List<double[][][]> blocks = ImageProcessing.getBlocks(ImageProcessing.openImage(args[0]));
    }
}
