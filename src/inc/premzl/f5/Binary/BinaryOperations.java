package inc.premzl.f5.Binary;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BinaryOperations {
    public static int binaryToSignedNumber(String bitString) {
        int num = 0;
        for (int i = bitString.length() - 1; i >= 1; i--) {
            if (bitString.charAt(i) == '1') num |= 1L << (bitString.length() - i - 1);
        }
        return bitString.charAt(0) == '0' ? num : -num;
    }

    public static int binaryToUnsignedNumber(String bitString) {
        int num = 0;
        for (int i = bitString.length() - 1; i >= 0; i--) {
            if (bitString.charAt(i) == '1') num |= 1L << (bitString.length() - i - 1);
        }
        return num;
    }

    public static String signedNumberToBinary(int number, int bitNum) {
        StringBuilder bits = new StringBuilder();

        if (number < 0) bits.append("1");
        else bits.append(0);

        number = Math.abs(number);
        for (int i = bitNum - 2; i >= 0; i--) {
            bits.append((number >> i) & 1);
        }

        return bits.toString();
    }

    public static String unsignedNumberToBinary(int number, int bitNum) {
        StringBuilder bits = new StringBuilder();

        for (int i = bitNum - 1; i >= 0; i--) {
            bits.append((number >> i) & 1);
        }

        return bits.toString();
    }

    public static int countBitsUnsigned(int number) {
        return (int) (Math.log(Math.abs(number)) / Math.log(2) + 1);
    }

    public static int countBitsSigned(int number) {
        return countBitsUnsigned(number) + 1;
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

    public static byte[] getBytes(String binary) {
        byte[] bytes = new byte[binary.length() / 8];
        int index = 0;

        for (int i = 0; i < binary.length(); ) {
            bytes[index] = (byte) ((short) Integer.parseInt(binary.substring(i, i + 8), 2));
            ++index;
            i += 8;
        }

        return bytes;
    }

    public static String getLengthBitString(String content) {
        StringBuilder prefix = new StringBuilder();
        long length = Integer.toUnsignedLong(content.length());
        for (int i = 31; i >= 0; i--) {
            prefix.append((length >> i) & 1);
        }
        return prefix.toString();
    }

    public static String getPrependedBinaryContent(byte[] bytes) {
        String content = getBitString(bytes);
        return getLengthBitString(content) + content;
    }
}
