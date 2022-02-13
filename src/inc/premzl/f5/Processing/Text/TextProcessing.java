package inc.premzl.f5.Processing.Text;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public static String getLengthBitString(String content) {
        StringBuilder prefix = new StringBuilder();
        long length = Integer.toUnsignedLong(content.length());
        for (int i = 31; i >= 0; i--) {
            prefix.append((length >> i) & 1);
        }
        return prefix.toString();
    }

    public static String getPrependedBinaryContent(String path) throws IOException {
        return getLengthBitString(getFileContent(path)) + getBitString(getFileContentBinary(path));
    }
}
