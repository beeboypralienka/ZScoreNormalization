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
public class ZScoreIris {
    
    public static void main(String[] args) {
        
        String csvFile = "/dataset/iris.arff";
        String line = "";
        String cvsSplitBy = ",";

        int row = 0;
        double[][] data = new double[151][4];
        String[] kolom = null;
        double[] penjumlahanKolom = new double[4];
        double[] mean = new double[4];
        double[][] hasilDataDikurangMean = new double[151][4];
        double[][] hasilDataDikurangMean2 = new double[151][4];
        double[] jumlahHasilDataDikurangmean2 = new double[4];
        double[] std = new double[4];
        double[][] zscore = new double[151][4];

        URL url = ZScoreNormalization.class.getResource(csvFile);
        File file = new File(url.getPath());

        // Input data dari csv kemudian di-convert ke int
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                row++;
                kolom = line.split(cvsSplitBy);
                for (int i = 0; i < kolom.length - 1; i++) {
                    data[row][i] = Double.parseDouble(kolom[i]);
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
                    + zscore[i][1] + "," + zscore[i][2] + "," + zscore[i][3] 
            );
        }
        
    }
    
}
