package inc.premzl.f5.Processing.Image;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessing {
    public static Mat openImage(String path) {
        return Imgcodecs.imread(path);
    }

    public static void writeImage(String filename, Mat image) {
        Imgcodecs.imwrite(filename, image);
    }

    public static void compressFile(String filename, String bits) throws IOException {
        File output = new File(filename);
        byte b = 0;
        int index = 7;

        try (FileOutputStream outputStream = new FileOutputStream(output)) {
            for (int i = 0; i < bits.length(); i++) {
                if (bits.charAt(i) == '1') b |= 1 << index;
                --index;

                if (i % 8 == 7) {
                    outputStream.write(b);
                    b = 0;
                    index = 7;
                }
            }
        }
    }

    public static List<double[][][]> getBlocks(Mat image) {
        List<double[][][]> blocks = new ArrayList<>();
        int rowIteration = 0, columnIteration = 0, rowIndex, columnIndex;
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

    public static Mat getMat(List<double[][][]> blocks, int height, int width) {
        Mat mat = new Mat(height, width, 16);
        int rowIteration = 0, columnIteration = 0, rowIndex, columnIndex;

        for (double[][][] block : blocks) {
            rowIndex = 0;
            for (int i = rowIteration * 8; i < (rowIteration * 8) + 8; i++) {
                columnIndex = 0;
                for (int j = columnIteration * 8; j < (columnIteration * 8) + 8; j++) {
                    if (i < height && j < width)
                        mat.put(i,
                                j,
                                block[rowIndex][columnIndex][2],
                                block[rowIndex][columnIndex][1],
                                block[rowIndex][columnIndex][0]);
                    ++columnIndex;
                }
                ++rowIndex;
            }

            ++columnIteration;
            if ((columnIteration * 8) >= width) {
                ++rowIteration;

                if ((rowIteration * 8) >= height) break;

                columnIteration = 0;
            }
        }

        return mat;
    }
}
