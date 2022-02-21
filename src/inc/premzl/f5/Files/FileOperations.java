package inc.premzl.f5.Files;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOperations {
    public static String getFileContent(String path) throws IOException {
        return Files.readString(Path.of(path), StandardCharsets.UTF_8);
    }

    public static byte[] getFileContentBinary(String path) throws IOException {
        return Files.readAllBytes(Path.of(path));
    }

    public static void writeToFile(String filename, String content) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(content
            );
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}
