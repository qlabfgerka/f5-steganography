package inc.premzl.f5.Binary;

import inc.premzl.f5.Models.DecompressionWrapper;

import java.util.ArrayList;
import java.util.List;

import static inc.premzl.f5.Binary.BinaryOperations.*;

public class Compression {
    public static String compress(List<int[]> blocks, int N) {
        StringBuilder bits = new StringBuilder();
        int zeroCounter;

        for (int[] block : blocks) {
            zeroCounter = 0;
            bits.append(signedNumberToBinary(block[0], 12));
            for (int i = 1; i < 64 - N; i++) {
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

    public static DecompressionWrapper decompress(String binary, int N) {
        List<int[]> blocks = new ArrayList<>();
        int[] block = new int[64];
        int counter = 0, length;
        int height = binaryToUnsignedNumber(binary.substring(0, 16));
        int width = binaryToUnsignedNumber(binary.substring(16, 32));
        int offset = binaryToUnsignedNumber(binary.substring(32, 36));

        for (int i = 36; i + 7 < binary.length() - (offset == 0 ? 8 : offset); ) {
            if (counter >= 64 - N) {
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

                    if (length + counter == 64 - N) {
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
}
