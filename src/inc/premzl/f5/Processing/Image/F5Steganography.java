package inc.premzl.f5.Processing.Image;

import java.util.List;

public class F5Steganography {
    public static List<int[]> hideMessage(List<int[]> blocks, int N, int M, String message) {
        int max = Math.min(63 - N, 32), messageCounter = 0;
        int c1, c2, c3, x1, x2;
        for (int[] block : blocks) {
            for (int i = 4, j = 0; i + 2 < max && j < M && messageCounter + 1 < message.length(); i += 3, j++) {
                c1 = block[i] & 1;
                c2 = block[i + 1] & 1;
                c3 = block[i + 2] & 1;
                x1 = Character.getNumericValue(message.charAt(messageCounter));
                x2 = Character.getNumericValue(message.charAt(messageCounter + 1));
                //System.out.printf("ORIGINAL: %d %d %d ->", c1, c2, c3);

                if (x1 != (c1 ^ c2) && x2 == (c2 ^ c3)) {
                    block[i] ^= 1;
                }
                if (x1 == (c1 ^ c2) && x2 != (c2 ^ c3)) {
                    block[i + 2] ^= 1;
                }
                if (x1 != (c1 ^ c2) && x2 != (c2 ^ c3)) {
                    block[i + 1] ^= 1;
                }

                //System.out.printf("CHANGED: %d %d %d\t", (block[i] & 1), (block[i + 1] & 1), (block[i + 2] & 1));
                messageCounter += 2;
            }
            //System.out.println();
        }
        return blocks;
    }

    public static String readMessage(List<int[]> blocks, int N, int M) {
        StringBuilder bits = new StringBuilder();
        int max = Math.min(63 - N, 32);
        int c1, c2, c3;
        for (int[] block : blocks) {
            for (int i = 4, j = 0; i + 2 < max && j < M && bits.length() + 2 <= 328; i += 3, j++) {
                c1 = block[i] & 1;
                c2 = block[i + 1] & 1;
                c3 = block[i + 2] & 1;

                bits.append(c1 ^ c2);
                bits.append(c2 ^ c3);
            }
        }
        return bits.toString();
    }
}
