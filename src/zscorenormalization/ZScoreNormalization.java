/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zscorenormalization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author fachrulpbm
 */
public class ZScoreNormalization {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String csvFile = "/dataset/bank-full-preprocess-mantap.csv";
        String line = "";
        String cvsSplitBy = ",";

        int row = 0;
        int[][] data = new int[45213][31];
        String[] kolom = null;
        double[] penjumlahanKolom = new double[31];
        double[] mean = new double[31];
        double[][] hasilDataDikurangMean = new double[45213][31];
        double[][] hasilDataDikurangMean2 = new double[45213][31];
        double[] jumlahHasilDataDikurangmean2 = new double[31];
        double[] std = new double[31];
        double[][] zscore = new double[45213][31];

        URL url = ZScoreNormalization.class.getResource(csvFile);
        File file = new File(url.getPath());

        // Input data dari csv kemudian di-convert ke int
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                row++;
                kolom = line.split(cvsSplitBy);
                for (int i = 0; i < kolom.length - 1; i++) {
                    data[row][i] = Integer.parseInt(kolom[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Penjumlahan data dari setiap kolom
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < kolom.length - 1; j++) {
                penjumlahanKolom[j] = penjumlahanKolom[j] + data[i][j];
            }
        }

        // Hitung mean dari setiap kolom
        for (int j = 0; j < kolom.length - 1; j++) {
            mean[j] = penjumlahanKolom[j] / row;
        }

        // Data - mean
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < kolom.length - 1; j++) {
                hasilDataDikurangMean[i][j] = data[i][j] - mean[j];
            }
        }

        // (Data - mean)^2
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < kolom.length - 1; j++) {
                hasilDataDikurangMean2[i][j] = Math.pow(data[i][j] - mean[j], 2);
            }
        }

        // Penjumlahan hasil perhitungan  (Data - mean)^2  dari setiap kolom
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < kolom.length - 1; j++) {
                jumlahHasilDataDikurangmean2[j] = jumlahHasilDataDikurangmean2[j] + hasilDataDikurangMean2[i][j];
            }
        }

        // Proses perhitungan nilai standar deviasi
        for (int i = 0; i < kolom.length - 1; i++) {
            std[i] = jumlahHasilDataDikurangmean2[i] / row;
        }

        // Hitung zscore
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < kolom.length - 1; j++) {
                zscore[i][j] = hasilDataDikurangMean[i][j] / std[j];
            }
        }

        for (int i = 1; i <= row; i++) {
            System.out.println(
                    zscore[i][0] + ","
                    + zscore[i][1] + "," + zscore[i][2] + "," + zscore[i][3] + ","
                    + zscore[i][4] + "," + zscore[i][5] + "," + zscore[i][6] + ","
                    + zscore[i][7] + "," + zscore[i][8] + "," + zscore[i][9] + ","
                    + zscore[i][10] + "," + zscore[i][11] + "," + zscore[i][12] + ","
                    + zscore[i][13] + "," + zscore[i][14] + "," + zscore[i][15] + ","
                    + zscore[i][16] + "," + zscore[i][17] + "," + zscore[i][18] + ","
                    + zscore[i][19] + "," + zscore[i][20] + "," + zscore[i][21] + ","
                    + zscore[i][22] + "," + zscore[i][23] + "," + zscore[i][24] + ","
                    + zscore[i][25] + "," + zscore[i][26] + "," + zscore[i][27] + ","
                    + zscore[i][28] + "," + zscore[i][29] + "," + zscore[i][30] 
            );
        }
//        System.out.println("Row = " + row);
//        System.out.println("Cth. Data = " + data[1][0]);
//        System.out.println("Penjumlahan kolom = " + penjumlahanKolom[0]);
//        System.out.println("Mean = " + mean[0]);
//        System.out.println("Data - mean = " + hasilDataDikurangMean[1][0]);
//        System.out.println("Kuadrat = " + hasilDataDikurangMean2[1][0]);
//        System.out.println("Jumlah kuadrat = " + jumlahHasilDataDikurangmean2[0]);
//        System.out.println("Standar deviasi = " + std[0]);
    }

}
