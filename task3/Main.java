package com.company;
//////////////////////////////////////////////////////////////////
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
//////////////////////////////////////////////////////////////////
public class Main {
    final static int CASH_AMOUNT = 5;
    final static int INTERVALS   = 16;
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        double[][] cashAndInterval =
                new double[CASH_AMOUNT][INTERVALS];
        System.out.println("Enter path");
        String filesPath = sc.next();
////////File reading process//////////////////////////////////////
        for(int i = 0; i < CASH_AMOUNT; i++){
            int interval = 0;
            FileInputStream fis =
                    new FileInputStream(
                            filesPath +
                                    "\\Cash" + (i+1) + ".txt"
                                        );
            BufferedReader bf =
                    new BufferedReader(
                            new InputStreamReader(fis)
                                        );
////////////Reading file data/////////////////////////////////////
            String inputLine;
            while((inputLine = bf.readLine()) != null){
                cashAndInterval[i][interval] =
                        Double.parseDouble(
                                inputLine.substring(0,
                                        inputLine.indexOf("\\")));
                interval++;
            }
            fis.close();
            bf.close();
        }
////////Counting maximum cash loading/////////////////////////////
        double max = 0;
        int maxIndex = 0;
        for(int j = 0; j < 16; j++){
            double currSum = 0;
            for(int i = 0; i < 5; i++){
                currSum += cashAndInterval[i][j];
            }
            if(currSum > max){
                max = currSum;
                maxIndex = j + 1;
            }
        }
        System.out.println(maxIndex);
    }
}
