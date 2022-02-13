package inc.premzl.f5.Processing.Image;

import java.util.ArrayList;
import java.util.List;

public class DCTProcessing {
    public static List<double[][][]> DCT(List<double[][][]> blocks) {
        List<double[][][]> dct = new ArrayList<>();
        double value;
        double[][][] newBlock;
        for (double[][][] block : blocks) {
            newBlock = new double[8][8][3];
            for (int u = 0; u < 8; u++) {
                for (int v = 0; v < 8; v++) {
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            for (int channel = 0; channel < 3; channel++) {
                                newBlock[u][v][channel] += block[x][y][channel]
                                        * Math.cos(((2 * x + 1) * u * Math.PI) / 16)
                                        * Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                            }
                        }
                    }
                    value = (1.0 / 4.0) * (u == 0 ? (1 / Math.sqrt(2)) : 1) * (v == 0 ? (1 / Math.sqrt(2)) : 1);
                    newBlock[u][v][0] *= value;
                    newBlock[u][v][1] *= value;
                    newBlock[u][v][2] *= value;
                }
            }
            dct.add(newBlock);
        }
        return dct;
    }

    public static List<double[][][]> IDCT(List<double[][][]> blocks) {
        List<double[][][]> idct = new ArrayList<>();
        double value;
        double[][][] newBlock;
        for (double[][][] block : blocks) {
            newBlock = new double[8][8][3];
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    for (int u = 0; u < 8; u++) {
                        for (int v = 0; v < 8; v++) {
                            value = (u == 0 ? (1 / Math.sqrt(2)) : 1) * (v == 0 ? (1 / Math.sqrt(2)) : 1);
                            for (int channel = 0; channel < 3; channel++) {
                                newBlock[x][y][channel] += value * block[u][v][channel]
                                        * Math.cos(((2 * x + 1) * u * Math.PI) / 16)
                                        * Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                            }
                        }
                    }
                    newBlock[x][y][0] *= (1.0 / 4.0);
                    newBlock[x][y][1] *= (1.0 / 4.0);
                    newBlock[x][y][2] *= (1.0 / 4.0);
                }
            }
            idct.add(newBlock);
        }

        return idct;
    }

    public static List<double[]> zigzag(List<double[][][]> blocks) {
        List<double[]> zigzags = new ArrayList<>();
        double[] zigzag;
        for (double[][][] block : blocks) {

            for (int channel = 0; channel < 3; channel++) {
                zigzag = new double[64];
                zigzag[0] = block[0][0][channel];
                zigzag[1] = block[0][1][channel];
                zigzag[2] = block[1][0][channel];
                zigzag[3] = block[2][0][channel];
                zigzag[4] = block[1][1][channel];
                zigzag[5] = block[0][2][channel];
                zigzag[6] = block[0][3][channel];
                zigzag[7] = block[1][2][channel];

                zigzag[8] = block[2][1][channel];
                zigzag[9] = block[3][0][channel];
                zigzag[10] = block[4][0][channel];
                zigzag[11] = block[3][1][channel];
                zigzag[12] = block[2][2][channel];
                zigzag[13] = block[1][3][channel];
                zigzag[14] = block[0][4][channel];
                zigzag[15] = block[0][5][channel];

                zigzag[16] = block[1][4][channel];
                zigzag[17] = block[2][3][channel];
                zigzag[18] = block[3][2][channel];
                zigzag[19] = block[4][1][channel];
                zigzag[20] = block[5][0][channel];
                zigzag[21] = block[6][0][channel];
                zigzag[22] = block[5][1][channel];
                zigzag[23] = block[4][2][channel];

                zigzag[24] = block[3][3][channel];
                zigzag[25] = block[2][4][channel];
                zigzag[26] = block[1][5][channel];
                zigzag[27] = block[0][6][channel];
                zigzag[28] = block[0][7][channel];
                zigzag[29] = block[1][6][channel];
                zigzag[30] = block[2][5][channel];
                zigzag[31] = block[3][4][channel];

                zigzag[32] = block[4][3][channel];
                zigzag[33] = block[5][2][channel];
                zigzag[34] = block[6][1][channel];
                zigzag[35] = block[7][0][channel];
                zigzag[36] = block[7][1][channel];
                zigzag[37] = block[6][2][channel];
                zigzag[38] = block[5][3][channel];
                zigzag[39] = block[4][4][channel];

                zigzag[40] = block[3][5][channel];
                zigzag[41] = block[2][6][channel];
                zigzag[42] = block[1][7][channel];
                zigzag[43] = block[2][7][channel];
                zigzag[44] = block[3][6][channel];
                zigzag[45] = block[4][5][channel];
                zigzag[46] = block[5][4][channel];
                zigzag[47] = block[6][3][channel];

                zigzag[48] = block[7][2][channel];
                zigzag[49] = block[7][3][channel];
                zigzag[50] = block[6][4][channel];
                zigzag[51] = block[5][5][channel];
                zigzag[52] = block[4][6][channel];
                zigzag[53] = block[3][7][channel];
                zigzag[54] = block[4][7][channel];
                zigzag[55] = block[5][6][channel];

                zigzag[56] = block[6][5][channel];
                zigzag[57] = block[7][4][channel];
                zigzag[58] = block[7][5][channel];
                zigzag[59] = block[6][6][channel];
                zigzag[60] = block[5][7][channel];
                zigzag[61] = block[6][7][channel];
                zigzag[62] = block[7][6][channel];
                zigzag[63] = block[7][7][channel];

                zigzags.add(zigzag);
            }
        }

        return zigzags;
    }

    public static List<double[][][]> reverseZigzag(List<double[]> blocks) {
        List<double[][][]> reverseZigzags = new ArrayList<>();
        double[][][] reverseZigzag = new double[8][8][3];

        for (int i = 0; i < blocks.size(); i++) {
            reverseZigzag[0][0][i % 3] = blocks.get(i)[0];
            reverseZigzag[0][1][i % 3] = blocks.get(i)[1];
            reverseZigzag[1][0][i % 3] = blocks.get(i)[2];
            reverseZigzag[2][0][i % 3] = blocks.get(i)[3];
            reverseZigzag[1][1][i % 3] = blocks.get(i)[4];
            reverseZigzag[0][2][i % 3] = blocks.get(i)[5];
            reverseZigzag[0][3][i % 3] = blocks.get(i)[6];
            reverseZigzag[1][2][i % 3] = blocks.get(i)[7];

            reverseZigzag[2][1][i % 3] = blocks.get(i)[8];
            reverseZigzag[3][0][i % 3] = blocks.get(i)[9];
            reverseZigzag[4][0][i % 3] = blocks.get(i)[10];
            reverseZigzag[3][1][i % 3] = blocks.get(i)[11];
            reverseZigzag[2][2][i % 3] = blocks.get(i)[12];
            reverseZigzag[1][3][i % 3] = blocks.get(i)[13];
            reverseZigzag[0][4][i % 3] = blocks.get(i)[14];
            reverseZigzag[0][5][i % 3] = blocks.get(i)[15];

            reverseZigzag[1][4][i % 3] = blocks.get(i)[16];
            reverseZigzag[2][3][i % 3] = blocks.get(i)[17];
            reverseZigzag[3][2][i % 3] = blocks.get(i)[18];
            reverseZigzag[4][1][i % 3] = blocks.get(i)[19];
            reverseZigzag[5][0][i % 3] = blocks.get(i)[20];
            reverseZigzag[6][0][i % 3] = blocks.get(i)[21];
            reverseZigzag[5][1][i % 3] = blocks.get(i)[22];
            reverseZigzag[4][2][i % 3] = blocks.get(i)[23];

            reverseZigzag[3][3][i % 3] = blocks.get(i)[24];
            reverseZigzag[2][4][i % 3] = blocks.get(i)[25];
            reverseZigzag[1][5][i % 3] = blocks.get(i)[26];
            reverseZigzag[0][6][i % 3] = blocks.get(i)[27];
            reverseZigzag[0][7][i % 3] = blocks.get(i)[28];
            reverseZigzag[1][6][i % 3] = blocks.get(i)[29];
            reverseZigzag[2][5][i % 3] = blocks.get(i)[30];
            reverseZigzag[3][4][i % 3] = blocks.get(i)[31];

            reverseZigzag[4][3][i % 3] = blocks.get(i)[32];
            reverseZigzag[5][2][i % 3] = blocks.get(i)[33];
            reverseZigzag[6][1][i % 3] = blocks.get(i)[34];
            reverseZigzag[7][0][i % 3] = blocks.get(i)[35];
            reverseZigzag[7][1][i % 3] = blocks.get(i)[36];
            reverseZigzag[6][2][i % 3] = blocks.get(i)[37];
            reverseZigzag[5][3][i % 3] = blocks.get(i)[38];
            reverseZigzag[4][4][i % 3] = blocks.get(i)[39];

            reverseZigzag[3][5][i % 3] = blocks.get(i)[40];
            reverseZigzag[2][6][i % 3] = blocks.get(i)[41];
            reverseZigzag[1][7][i % 3] = blocks.get(i)[42];
            reverseZigzag[2][7][i % 3] = blocks.get(i)[43];
            reverseZigzag[3][6][i % 3] = blocks.get(i)[44];
            reverseZigzag[4][5][i % 3] = blocks.get(i)[45];
            reverseZigzag[5][4][i % 3] = blocks.get(i)[46];
            reverseZigzag[6][3][i % 3] = blocks.get(i)[47];

            reverseZigzag[7][2][i % 3] = blocks.get(i)[48];
            reverseZigzag[7][3][i % 3] = blocks.get(i)[49];
            reverseZigzag[6][4][i % 3] = blocks.get(i)[50];
            reverseZigzag[5][5][i % 3] = blocks.get(i)[51];
            reverseZigzag[4][6][i % 3] = blocks.get(i)[52];
            reverseZigzag[3][7][i % 3] = blocks.get(i)[53];
            reverseZigzag[4][7][i % 3] = blocks.get(i)[54];
            reverseZigzag[5][6][i % 3] = blocks.get(i)[55];

            reverseZigzag[6][5][i % 3] = blocks.get(i)[56];
            reverseZigzag[7][4][i % 3] = blocks.get(i)[57];
            reverseZigzag[7][5][i % 3] = blocks.get(i)[58];
            reverseZigzag[6][6][i % 3] = blocks.get(i)[59];
            reverseZigzag[5][7][i % 3] = blocks.get(i)[60];
            reverseZigzag[6][7][i % 3] = blocks.get(i)[61];
            reverseZigzag[7][6][i % 3] = blocks.get(i)[62];
            reverseZigzag[7][7][i % 3] = blocks.get(i)[63];

            if (i % 3 == 2) {
                reverseZigzags.add(reverseZigzag);
                reverseZigzag = new double[8][8][3];
            }
        }

        return reverseZigzags;
    }
}
