package mechanics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LabirynthGenerate {

    private boolean[][] lab;
    public static int[][] labirynt;
    public int x0, y0;
    public int x;
    public int y;
    private boolean[] tab;
    private Random r;
    private int ile, rand;
    private List<Point> historia;
    public int tempx, tempy;
    private int fx, fy;
    private final int loops;

    public int[][] copyLabirynth() {
        int[][] table = new int[x][y];
        for (int i = 0; i < x; i++) {
            System.arraycopy(labirynt[i], 0, table[i], 0, y);
        }
        return table;
    }

    private void createLoops(int loop, int row, int hei) {
        int tx, ty;

        for (int i = 0; i < loop; i++) {

            do {
                tx = Math.abs(r.nextInt() % (hei - 3)) + 1;
                ty = Math.abs(r.nextInt() % (row - 3)) + 1;

                otoczenie(tx, ty, 0);

                if (labirynt[tx][ty] == 1 && ile == 2) {
                    if (labirynt[tx - 1][ty] == 0 && labirynt[tx + 1][ty] == 0) {
                        labirynt[tx][ty] = 0;
                        break;
                    } else if (labirynt[tx][ty - 1] == 0 && labirynt[tx][ty + 1] == 0) {
                        labirynt[tx][ty] = 0;
                        break;
                    }
                }
            } while (true);
        }
    }

    private void consoleWrite() {
        System.out.println(x + "  " + y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(labirynt[i][j]);
            }
            System.out.println();
        }
    }

    public LabirynthGenerate(int wysokosc, int szerokosc, int petle) {
        x = wysokosc * 2 + 1;
        y = szerokosc * 2 + 1;
        loops = petle;
        reset();
    }

    private void reset() {
        lab = new boolean[x][y];
        labirynt = new int[x][y];
        tab = new boolean[4];
        r = new Random();
        historia = new ArrayList<>();
        ustaw();
        losujXY();
        historia.add(new Point(x0, y0));
        droga();
        createLoops(loops, y, x);
    }

    private void ustaw() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                lab[i][j] = false;
            }
        }
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                labirynt[i][j] = 1;
            }
        }
    }

    public void wyswietl() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print(labirynt[i][j]);

            }
            System.out.println();
        }
    }

    private void losujXY() {
        do {
            x0 = 2 * r.nextInt(x / 2 - 2) + 1;
            y0 = 2 * r.nextInt(y / 2 - 2) + 1;
        } while (lab[x0][y0]);
        lab[x0][y0] = true;
    }

    private void resettab() {
        ile = 0;
        for (int i = 0; i < 4; i++) {
            tab[i] = false;
        }
    }

    private void mozliwedrogi(boolean t) {
        resettab();
        if (x0 < x - 2 && lab[x0 + 2][y0] == t) {
            ile++;
            tab[0] = true;
        }
        if (x0 > 2 && lab[x0 - 2][y0] == t) {
            ile++;
            tab[1] = true;
        }
        if (y0 < y - 2 && lab[x0][y0 + 2] == t) {
            ile++;
            tab[2] = true;
        }
        if (y0 > 2 && lab[x0][y0 - 2] == t) {
            ile++;
            tab[3] = true;
        }
    }

    private void wybierzdroge() {
        do {
            rand = r.nextInt(4);
        } while (!tab[rand]);

        zrobkrok();
    }

    private void zrobkrok() {
        switch (rand) {
            case 0:
                x0++;
                lab[x0][y0] = true;
                x0++;
                break;
            case 1:
                x0--;
                lab[x0][y0] = true;
                x0--;
                break;
            case 2:
                y0++;
                lab[x0][y0] = true;
                y0++;
                break;
            case 3:
                y0--;
                lab[x0][y0] = true;
                y0--;
                break;
        }
        lab[x0][y0] = true;
    }

    private void ruch() {
        mozliwedrogi(false);
        if (ile != 0) {
            wybierzdroge();
            historia.add(new Point(x0, y0));

        } else {
            if (historia.size() > 1) {
                fx = x0;
                fy = y0;
                historia.remove(historia.size() - 1);

                x0 = historia.get(historia.size() - 1).x;
                y0 = historia.get(historia.size() - 1).y;
            }
        }
    }

    private void otoczenie(int tempx, int tempy, int value) {
        ile = 0;

        if (labirynt[tempx + 1][tempy] == value) {
            ile++;
        }
        if (labirynt[tempx - 1][tempy] == value) {
            ile++;
        }
        if (labirynt[tempx][tempy + 1] == value) {
            ile++;
        }
        if (labirynt[tempx][tempy - 1] == value) {
            ile++;
        }
    }

    private void losujkoniec() {

        for (int i = y - 2; i >= 0; i--) {
            if (i == 0) {
                reset();
                break;
            }
            otoczenie(x - 2, i, 1);

            if (ile == 3 && labirynt[x - 2][i] != 2 && labirynt[x - 2][i] != 1) {
                fx = x - 2;
                fy = i;
                break;
            }
        }
    }

    private void dokoncz() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (lab[i][j]) {
                    labirynt[i][j] = 0;
                } else {
                    labirynt[i][j] = 1;
                }
            }
        }
        losujkoniec();
        labirynt[fx][fy] = 3;
        x0 = 1;
        y0 = 1;
        labirynt[x0][y0] = 2;
    }

    private void droga() {
        do {
            ruch();
        } while (historia.size() > 1);
        dokoncz();
    }
}
