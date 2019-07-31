package com.company;
////////////////////////////////////////////////////////////////////////////////////
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
/////////////////////////////////////////////////////////////////////////////////////////
public class Main {
/////////////////////////////////////////////////////////////////////////////////////////
final static int START_WORKING_MINUTES = 480;
final static int END_WORKING_MINUTES = 1200;

    public static ArrayList<TimePair> countTopInterval(int startDay, int endDay, ArrayList<TimePair> times){
        int maxClients = 0;
        // Array of clients amount for every minute
        ArrayList<Integer> countClients = new ArrayList<Integer>();
////////Going through every minute///////////////////////////////////////////////////////
        for(int index = startDay; index < endDay + 1; index++){
            int currMinuteClientsCount = 0;
////////////Counting clients for every minute////////////////////////////////////////////
            for(TimePair client : times){
                if(index >= client.startMinutes && index < client.endMinutes){
                    currMinuteClientsCount++;
                }
            }
            countClients.add(currMinuteClientsCount);
            if(currMinuteClientsCount > maxClients){ maxClients = currMinuteClientsCount; }
        }
        int firstMinuteWithMax = countClients.indexOf(maxClients);
        ArrayList<TimePair> topIntervals = new ArrayList<TimePair>();
        int start = -1;
////////Searching for intervals//////////////////////////////////////////////////////////
        int iter = firstMinuteWithMax;
        while(iter < countClients.size()){
            if(countClients.get(iter) == maxClients) {
                start = iter;
                while (countClients.get(iter) == maxClients && iter < countClients.size()) {
                    iter++;
                }
                topIntervals.add(new TimePair(start + 480, iter + 480));
            }
            iter++;
        }

        return topIntervals;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file path and name");
        String file = sc.next();
        FileInputStream fis = new FileInputStream(file);
        BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
        ArrayList<TimePair> clientsTime = new ArrayList<TimePair>();
        String inputLine;
////////Parsing line to minutes//////////////////////////////////////////////////////////
        while((inputLine = bf.readLine()) != null){
            clientsTime.add(new TimePair(
                    Integer.parseInt(inputLine.substring(0, inputLine.indexOf(":"))) * 60 +
                    Integer.parseInt(inputLine.substring(
                            inputLine.indexOf(":") + 1, inputLine.indexOf(":") + 3)),
                    Integer.parseInt(inputLine.substring(
                            inputLine.indexOf(" ") + 1, inputLine.indexOf("n") - 4)) * 60 +
                            Integer.parseInt(inputLine.substring(
                                    inputLine.indexOf("n") - 3, inputLine.indexOf("n") - 1))
            ));
//////////////////////////////////////////////////////////////////////////////////////
        }
        DecimalFormat format = new DecimalFormat("00");
        for(TimePair pair :countTopInterval(START_WORKING_MINUTES, END_WORKING_MINUTES, clientsTime)){
            System.out.print((pair.startMinutes / 60) + ":" +
                             format.format((pair.startMinutes % 60)) + " " +
                             (pair.endMinutes / 60) + ":" +
                             format.format((pair.endMinutes % 60)) + "\n");
        }
    }
}
