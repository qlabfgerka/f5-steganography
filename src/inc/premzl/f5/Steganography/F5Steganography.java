package inc.premzl.f5.Steganography;

import java.util.List;

import static inc.premzl.f5.Binary.BinaryOperations.binaryToUnsignedNumber;

public class F5Steganography {
    public static void hideMessage(List<int[]> blocks, int N, int M, String message) {
        int max = Math.min(64 - N, 32), messageCounter = 0;
        int c1, c2, c3, x1, x2;
        for (int[] block : blocks) {
            for (int i = 4, j = 0; i + 2 < max && j < M && messageCounter + 1 < message.length(); i += 3, j++) {
                c1 = block[i] & 1;
                c2 = block[i + 1] & 1;
                c3 = block[i + 2] & 1;
                x1 = Character.getNumericValue(message.charAt(messageCounter));
                x2 = Character.getNumericValue(message.charAt(messageCounter + 1));

                if (x1 != (c1 ^ c2) && x2 == (c2 ^ c3)) block[i] ^= 1;
                if (x1 == (c1 ^ c2) && x2 != (c2 ^ c3)) block[i + 2] ^= 1;
                if (x1 != (c1 ^ c2) && x2 != (c2 ^ c3)) block[i + 1] ^= 1;

                messageCounter += 2;
            }
        }
    }

    public static String readMessage(List<int[]> blocks, int N, int M) {
        StringBuilder bits = new StringBuilder();
        int max = Math.min(64 - N, 32);
        long limit = 0;
        int c1, c2, c3;
        for (int[] block : blocks) {
            for (int i = 4, j = 0; i + 2 < max && j < M; i += 3, j++) {
                if (limit != 0 && bits.length() > (limit + 30)) break;
                c1 = block[i] & 1;
                c2 = block[i + 1] & 1;
                c3 = block[i + 2] & 1;

                bits.append(c1 ^ c2);
                bits.append(c2 ^ c3);

                if (bits.length() == 32) limit = binaryToUnsignedNumber(bits.toString());
            }
        }
        return bits.substring(32, bits.length());
    }
}
