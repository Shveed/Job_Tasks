package com.company;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // To check point if us on side
    public static boolean isOnSide(Point p1, Point p2, int a, int b){
        return ((p1.x-a)*(p2.y-b)-(p2.x-a)*(p1.y-b)) == 0;
    }
    // To check if point is inside
    public static int IsContaining(Point point, Polygon polygon, ArrayList<Point> quad){
        if(polygon.contains(point.x, point.y)){
            if(isOnSide(quad.get(0), quad.get(1), point.x , point.y) ||
                    isOnSide(quad.get(1), quad.get(2), point.x, point.y) ||
                    isOnSide(quad.get(2), quad.get(3), point.x, point.y) ||
                    isOnSide(quad.get(3), quad.get(0), point.x, point.y)){
                return 1;
            }
            else{ return 2; }
        }
        else{
            return 3;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите имя первого файла");
        String filename1 = sc.next();
        System.out.println("Введите имя второго файла");
        String filename2 = sc.next();
        FileInputStream fis1 = new FileInputStream(filename1);
        FileInputStream fis2 = new FileInputStream(filename2);
        BufferedReader bf1 = new BufferedReader(new InputStreamReader(fis1));
        BufferedReader bf2 = new BufferedReader(new InputStreamReader(fis2));
        ArrayList<Point> quadPoints = new ArrayList<>();
        ArrayList<Point> points = new ArrayList<>();
        Polygon polygon = new Polygon();
        String strLine;
        ArrayList<Integer> pointsState = new ArrayList<>();

        // Filling quad coordinates' array
        while ((strLine = bf1.readLine()) != null){
            quadPoints.add(new Point((int)(Float.parseFloat(strLine.substring(0, strLine.indexOf(" "))) * 100),
                    (int)(Float.parseFloat(strLine.substring(strLine.indexOf(" "), strLine.length() - 2)) * 100)));
        }
        // Filling points' array
        while ((strLine = bf2.readLine()) != null){
            points.add(new Point((int)(Float.parseFloat(strLine.substring(0, strLine.indexOf(" "))) * 100),
                    (int)(Float.parseFloat(strLine.substring(strLine.indexOf(" "), strLine.length() - 2)) * 100)));
        }
        // Filling the polygon
        for(Point p : quadPoints){
            polygon.addPoint(p.x, p.y);
        }

        for(Point p : points){
            boolean isOnVertex = false;
            for(Point q: quadPoints){
                if(q.x == p.x && q.y == p.y){
                    isOnVertex = true;
                }
            }
            if(isOnVertex){
                pointsState.add(0);
            }
            else{
                pointsState.add(IsContaining(p, polygon, quadPoints));
            }
        }
        for(int c : pointsState){
            System.out.print(c + "\n");
        }
        fis1.close();
        fis2.close();
    }
}
