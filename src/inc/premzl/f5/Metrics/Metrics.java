package inc.premzl.f5.Metrics;

import org.opencv.core.Mat;

public class Metrics {
    //Mat -> BGR
    public static double MSE(Mat original, Mat other) {
        double mseR = 0, mseG = 0, mseB = 0;
        double[] originalPixel, otherPixel;
        for (int i = 0; i < original.rows(); i++) {
            for (int j = 0; j < original.cols(); j++) {
                originalPixel = original.get(i, j);
                otherPixel = other.get(i, j);
                mseB += Math.pow(originalPixel[0] - otherPixel[0], 2);
                mseG += Math.pow(originalPixel[1] - otherPixel[1], 2);
                mseR += Math.pow(originalPixel[2] - otherPixel[2], 2);
            }
        }
        mseB /= (original.rows() * original.cols());
        mseG /= (original.rows() * original.cols());
        mseR /= (original.rows() * original.cols());

        return (mseB + mseG + mseR) / 3.0;
    }

    public static double PSNR(Mat original, Mat other) {
        return 10 * Math.log10(Math.pow(255, 2) / MSE(original, other));
    }

    public static double blockage(Mat image) {
        double sumB = 0, sumG = 0, sumR = 0;
        double[] pixel1, pixel2;

        for (int i = 0; i < (image.rows() - 1) / 8; i++) {
            for (int j = 0; j < image.cols(); j++) {
                pixel1 = image.get(8 * i, j);
                pixel2 = image.get(8 * i + 1, j);
                sumB += pixel1[0] - pixel2[0];
                sumG += pixel1[1] - pixel2[1];
                sumR += pixel1[2] - pixel2[2];
            }
        }

        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < (image.cols() - 1) / 8; j++) {
                pixel1 = image.get(i, 8 * j);
                pixel2 = image.get(i, 8 * j + 1);
                sumB += pixel1[0] - pixel2[0];
                sumG += pixel1[1] - pixel2[1];
                sumR += pixel1[2] - pixel2[2];
            }
        }

        return (sumB + sumG + sumR) / 3.0;
    }

    public static double shannonEntropy(Mat image) {
        double sumB = 0, sumG = 0, sumR = 0;
        double[] pixel;

        for (int i = 0; i < image.rows(); i++) {
            for (int j = 0; j < image.cols(); j++) {
                pixel = image.get(i, j);
                sumB += pixel[0] * log2(pixel[0]);
                sumG += pixel[1] * log2(pixel[1]);
                sumR += pixel[2] * log2(pixel[2]);
            }
        }

        return -((sumB + sumG + sumR) / 3.0);
    }

    public static double log2(double number) {
        return Math.log(number) / Math.log(2);
    }
}
