package inc.premzl.f5.Processing.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TextProcessing {
    public static String getFileContent(String path) throws IOException {
        return Files.readString(Path.of(path), StandardCharsets.UTF_8);
    }

    public static byte[] getFileContentBinary(String path) throws IOException {
        return Files.readAllBytes(Path.of(path));
    }

    public static String getBitString(byte b) {
        StringBuilder bits = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            bits.append((b >> i) & 1);
        }
        return bits.toString();
    }

    public static String getBitString(byte[] bytes) {
        StringBuilder bits = new StringBuilder();
        for (byte b : bytes) {
            bits.append(getBitString(b));
        }
        return bits.toString();
    }

    public static String getString(String binary) {
        return Arrays.stream(binary.split("(?<=\\G.{8})"))
                .map(s -> Integer.parseInt(s, 2))
                .map(i -> "" + (char) i.intValue())
                .collect(Collectors.joining(""));
    }

    public static String getLengthBitString(String content) {
        StringBuilder prefix = new StringBuilder();
        long length = Integer.toUnsignedLong(content.length());
        for (int i = 31; i >= 0; i--) {
            prefix.append((length >> i) & 1);
        }
        return prefix.toString();
    }

    public static String getPrependedBinaryContent(String path) throws IOException {
        String content = getBitString(getFileContentBinary(path));
        return getLengthBitString(content) + content;
    }

    public static void writeToFile(String filename, String content) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
