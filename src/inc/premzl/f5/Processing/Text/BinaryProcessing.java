package inc.premzl.f5.Processing.Text;

import inc.premzl.f5.Models.DecompressionWrapper;

import java.util.ArrayList;
import java.util.List;

public class BinaryProcessing {
    public static String compress(List<int[]> blocks) {
        StringBuilder bits = new StringBuilder();
        int zeroCounter;

        for (int[] block : blocks) {
            zeroCounter = 0;
            bits.append(signedNumberToBinary(block[0], 12));
            for (int i = 1; i < block.length; i++) {
                if (block[i] != 0) {
                    if (zeroCounter == 0) {
                        bits.append("1")
                                .append(unsignedNumberToBinary(countBitsSigned(block[i]), 4))
                                .append(signedNumberToBinary(block[i], countBitsSigned(block[i])));
                    } else {
                        bits.append("0")
                                .append(unsignedNumberToBinary(zeroCounter, 6))
                                .append(unsignedNumberToBinary(countBitsSigned(block[i]), 4))
                                .append(signedNumberToBinary(block[i], countBitsSigned(block[i])));
                        zeroCounter = 0;
                    }
                } else {
                    ++zeroCounter;
                }
            }
            if (zeroCounter > 0) bits.append("0").append(unsignedNumberToBinary(zeroCounter, 6));
        }

        return bits.toString();
    }

    public static DecompressionWrapper decompress(String binary) {
        List<int[]> blocks = new ArrayList<>();
        int[] block = new int[64];
        int counter = 0, length;
        int height = binaryToUnsignedNumber(binary.substring(0, 16));
        int width = binaryToUnsignedNumber(binary.substring(16, 32));
        int offset = binaryToUnsignedNumber(binary.substring(32, 36));

        for (int i = 36; i < binary.length() - (offset == 0 ? 8 : offset); ) {
            if (counter >= 64) {
                blocks.add(block);
                counter = 0;
            }
            if (counter == 0) {
                block = new int[64];

                block[counter] = binaryToSignedNumber(binary.substring(i, i + 12));
                i += 12;
                ++counter;
            } else {
                if (binary.charAt(i) == '1') {
                    ++i;
                    length = binaryToUnsignedNumber(binary.substring(i, i + 4));
                    i += 4;
                    block[counter] = binaryToSignedNumber(binary.substring(i, i + length));
                    i += length;
                    ++counter;
                } else {
                    ++i;
                    length = binaryToUnsignedNumber(binary.substring(i, i + 6));
                    i += 6;

                    if (length + counter == 64) {
                        blocks.add(block);
                        counter = 0;
                    } else {
                        for (int j = 0; j < length; j++) {
                            block[counter] = 0;
                            ++counter;
                        }
                        length = binaryToUnsignedNumber(binary.substring(i, i + 4));
                        i += 4;
                        block[counter] = binaryToSignedNumber(binary.substring(i, i + length));
                        i += length;
                        ++counter;
                    }
                }
            }
        }
        blocks.add(block);

        return new DecompressionWrapper(blocks, height, width);
    }

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
}
