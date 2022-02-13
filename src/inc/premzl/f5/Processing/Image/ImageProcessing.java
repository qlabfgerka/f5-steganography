package inc.premzl.f5.Processing.Image;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.ArrayList;
import java.util.List;

public class ImageProcessing {
    public static Mat openImage(String path) {
        return Imgcodecs.imread(path);
    }

    public static List<double[][][]> getBlocks(Mat image) {
        List<double[][][]> blocks = new ArrayList<>();
        int rowIteration = 0, columnIteration = 0, rowIndex = 0, columnIndex = 0;
        double[] value;

        while (true) {
            double[][][] block = new double[8][8][3];
            rowIndex = 0;
            for (int i = rowIteration * 8; i < (rowIteration * 8) + 8; i++) {
                columnIndex = 0;
                for (int j = columnIteration * 8; j < (columnIteration * 8) + 8; j++) {
                    if (i >= image.rows() || j >= image.cols()) {
                        block[rowIndex][columnIndex][0] = 0;
                        block[rowIndex][columnIndex][1] = 0;
                        block[rowIndex][columnIndex][2] = 0;
                    } else {
                        value = image.get(i, j);
                        block[rowIndex][columnIndex][0] = value[2];
                        block[rowIndex][columnIndex][1] = value[1];
                        block[rowIndex][columnIndex][2] = value[0];
                    }

                    ++columnIndex;
                }
                ++rowIndex;
            }

            blocks.add(block);
            ++columnIteration;

            if ((columnIteration * 8) >= image.cols()) {
                ++rowIteration;

                if ((rowIteration * 8) >= image.rows()) break;

                columnIteration = 0;
            }
        }

        return blocks;
    }
}
