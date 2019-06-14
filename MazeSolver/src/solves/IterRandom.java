/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solves;

import application.FXMLDocumentController;
import static application.FXMLDocumentController.sizex;
import static application.FXMLDocumentController.sizey;
import graphics.DrawNew;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import mechanics.DataContent;
import mechanics.Point;

/**
 *
 * @author borys
 */
public final class IterRandom {

    private static int[][] t;
    private static int x, y;
    public static double scalex, scaley;
    private static Random rand;
    private final boolean tab[] = new boolean[4];
    private int losowa;
    private final List<Point> list;
    private int timeStart, timeStop, steps;
    private boolean timestarted, founded;

    public IterRandom() {

        list = new ArrayList<>();
        reset();
    }

    public void reset() {
        list.clear();
        list.add(new Point(FXMLDocumentController.x0, FXMLDocumentController.y0));
        x = FXMLDocumentController.x;
        y = FXMLDocumentController.y;
        t = FXMLDocumentController.copytab();
        rand = new Random();
        timestarted = false;
        steps = 0;
        founded = false;
    }

    private void resetBools() {
        for (int i = 0; i < 4; i++) {
            tab[i] = true;
        }
    }

    private double getTime() {
        return (double) (timeStop - timeStart) / 1000.0;
    }

    private int getLength() {
        return list.size();
    }

    private void checkways(int tmp) {
        resetBools();
        if (t[list.get(list.size() - 1).x + 1][list.get(list.size() - 1).y] != tmp) {
            tab[0] = false;
        }
        if (t[list.get(list.size() - 1).x][list.get(list.size() - 1).y + 1] != tmp) {
            tab[1] = false;
        }
        if (t[list.get(list.size() - 1).x - 1][list.get(list.size() - 1).y] != tmp) {
            tab[2] = false;
        }
        if (t[list.get(list.size() - 1).x][list.get(list.size() - 1).y - 1] != tmp) {
            tab[3] = false;
        }
    }

    private int howManyWays(int tmp) {
        int ile = 0;

        if (t[list.get(list.size() - 1).x - 1][list.get(list.size() - 1).y] == tmp) {
            ile++;
        }
        if (t[list.get(list.size() - 1).x + 1][list.get(list.size() - 1).y] == tmp) {
            ile++;
        }
        if (t[list.get(list.size() - 1).x][list.get(list.size() - 1).y + 1] == tmp) {
            ile++;
        }
        if (t[list.get(list.size() - 1).x][list.get(list.size() - 1).y - 1] == tmp) {
            ile++;
        }
        return ile;
    }

    public void move() {
        if (!timestarted) {
            timestarted = true;
            timeStart = (int) System.currentTimeMillis();

        }
        if (!founded) {
            steps++;
            if (t[list.get(list.size() - 1).x][list.get(list.size() - 1).y] == 3) {
                timeStop = (int) System.currentTimeMillis();
                founded = true;
                FXMLDocumentController.AddToData(new DataContent("Iteracyjnie losowo", steps, getTime(), getLength()));
            } else if (t[list.get(list.size() - 1).x - 1][list.get(list.size() - 1).y] == 3) {
                t[list.get(list.size() - 1).x][list.get(list.size() - 1).y] = 9;
                list.add(new Point(list.get(list.size() - 1).x - 1, list.get(list.size() - 1).y));
            } else if (t[list.get(list.size() - 1).x][list.get(list.size() - 1).y - 1] == 3) {
                t[list.get(list.size() - 1).x][list.get(list.size() - 1).y] = 9;
                list.add(new Point(list.get(list.size() - 1).x, list.get(list.size() - 1).y - 1));
            } else if (t[list.get(list.size() - 1).x + 1][list.get(list.size() - 1).y] == 3) {
                t[list.get(list.size() - 1).x][list.get(list.size() - 1).y] = 9;
                list.add(new Point(list.get(list.size() - 1).x + 1, list.get(list.size() - 1).y));
            } else if (t[list.get(list.size() - 1).x][list.get(list.size() - 1).y + 1] == 3) {
                t[list.get(list.size() - 1).x][list.get(list.size() - 1).y] = 9;
                list.add(new Point(list.get(list.size() - 1).x, list.get(list.size() - 1).y + 1));
            } else if (howManyWays(0) == 1) {
                checkways(0);
                for (int l = 0; l < 4; l++) {
                    if (tab[l]) {

                        t[list.get(list.size() - 1).x][list.get(list.size() - 1).y] = 9;
                        switch (l) {
                            case 0:
                                list.add(new Point(list.get(list.size() - 1).x + 1, list.get(list.size() - 1).y));
                                break;
                            case 1:
                                list.add(new Point(list.get(list.size() - 1).x, list.get(list.size() - 1).y + 1));
                                break;
                            case 2:
                                list.add(new Point(list.get(list.size() - 1).x - 1, list.get(list.size() - 1).y));
                                break;
                            case 3:
                                list.add(new Point(list.get(list.size() - 1).x, list.get(list.size() - 1).y - 1));
                                break;
                        }
                    }
                }
            } else if (howManyWays(0) > 1) {
                checkways(0);
                while (true) {
                    losowa = rand.nextInt(4);
                    if (tab[losowa]) {
                        break;
                    }
                }

                t[list.get(list.size() - 1).x][list.get(list.size() - 1).y] = 9;
                switch (losowa) {
                    case 0:
                        list.add(new Point(list.get(list.size() - 1).x + 1, list.get(list.size() - 1).y));
                        break;
                    case 1:
                        list.add(new Point(list.get(list.size() - 1).x, list.get(list.size() - 1).y + 1));
                        break;
                    case 2:
                        list.add(new Point(list.get(list.size() - 1).x - 1, list.get(list.size() - 1).y));
                        break;
                    case 3:
                        list.add(new Point(list.get(list.size() - 1).x, list.get(list.size() - 1).y - 1));
                        break;

                }
            } else {
                t[list.get(list.size() - 1).x][list.get(list.size() - 1).y] = 8;
                list.remove(list.size() - 1);
            }
        }
    }

    public void iterDraw(GraphicsContext gc) {
        scalex = DrawNew.getScaleX();
        scaley = DrawNew.getScaleY();
        gc.setFill(Color.BISQUE);
        gc.fillRect(0, 0, sizex, sizey);
        DrawNew.drawLabirynth(gc, FXMLDocumentController.x, FXMLDocumentController.y, FXMLDocumentController.tab);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (t[i][j] == 9) {
                    gc.setLineCap(StrokeLineCap.ROUND);
                    gc.setLineWidth((Math.min(scalex, scaley)) / 2);
                    gc.setStroke(Color.RED);
                    gc.strokeLine(scalex / 2 + i * scalex, scaley / 2 + j * scaley, scalex / 2 + i * scalex,
                            scaley / 2 + j * scaley);
                } else if (t[i][j] == 8) {
                    gc.setLineCap(StrokeLineCap.ROUND);
                    gc.setLineWidth((Math.min(scalex, scaley)) / 2);
                    gc.setStroke(Color.GRAY);
                    gc.strokeLine(scalex / 2 + i * scalex, scaley / 2 + j * scaley, scalex / 2 + i * scalex,
                            scaley / 2 + j * scaley);
                }
                gc.setLineCap(StrokeLineCap.ROUND);
                gc.setLineWidth((Math.min(scalex, scaley)) / 1.5);
                gc.setStroke(Color.GREEN);
                gc.strokeLine(scalex / 2 + list.get(list.size() - 1).x * scalex, scaley / 2 + list.get(list.size() - 1).y * scaley, scalex / 2 + list.get(list.size() - 1).x * scalex,
                        scaley / 2 + list.get(list.size() - 1).y * scaley);
            }
        }
    }

}
