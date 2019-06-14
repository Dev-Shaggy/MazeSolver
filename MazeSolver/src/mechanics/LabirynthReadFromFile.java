/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author borys
 */
public class LabirynthReadFromFile {

    private  int x, y;
    private  int[][] labirynth;
    private  int sX, sY;

    public  int getX() {
        return x;
    }

    public  int getY() {
        return y;
    }

    public  int StartX() {
        return sX;
    }

    public  int StartY() {
        return sY;
    }

    public  int[][] copyLabirynth() {
        int[][] tab = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                tab[i][j] = labirynth[i][j];
            }
        }

        return tab;
    }

    public  void consolewrite() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(labirynth[i][j]);
            }
            System.out.println();
        }

    }

    public  void readLabirynth(String source) {
        String rows = null, columns = null;
        FileReader file = null;

        source = "labirynty/labirynt2.txt";

        try {
            file = new FileReader(source);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(file);

        try {
            columns = br.readLine();
            rows = br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        x = Integer.parseInt(columns);
        y = Integer.parseInt(rows);

        labirynth = new int[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                try {
                    labirynth[i][j] = br.read() - 48;
                    if (labirynth[i][j] == 2) {
                        sX = i;
                        sY = j;
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        try {
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
