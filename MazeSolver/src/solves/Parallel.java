package solves;

import application.FXMLDocumentController;
import static application.FXMLDocumentController.sizex;
import static application.FXMLDocumentController.sizey;
import graphics.DrawNew;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import mechanics.DataContent;
import mechanics.Path;
import mechanics.Point;

public final class Parallel {

    private int[][] t;
    private int x, y;
    private double scalex, scaley;
    private boolean founded, finished, timeStarted;
    private final List<Path> ways;
    private Point ENDPOINT;
    private final List<Integer> correctWay;
    private int timeStart, timeStop, steps;

    public Parallel() {
        ways = new ArrayList<>();
        correctWay = new ArrayList<>();
        reset();
    }
        private double getTime(){
        return (double)(timeStop-timeStart)/1000.0;
    }
    private  int getLength(){
              int tmp=0;
        for(int i=0;i<correctWay.size();i++){
            tmp+=ways.get(correctWay.get(i)).GetLenght();
        }
        tmp-=correctWay.size();
        tmp++;
        return tmp;
    }

    public void reset() {
        ways.clear();
        correctWay.clear();
        Point start = new Point(FXMLDocumentController.x0, FXMLDocumentController.y0);
        ways.add(new Path(start));

        t = FXMLDocumentController.copytab();
        x = FXMLDocumentController.x;
        y = FXMLDocumentController.y;

        steps=0;
        founded = false;
        finished = false;
        timeStarted=false;
    }

    public void move() {

        int lenght = ways.size();
        for (int i = 0; i < lenght; i++) {

            if (!finished) {
                if (!founded) {
                    if(!timeStarted){
                        timeStarted=true;
                        timeStart=(int) System.currentTimeMillis();
                    }
                    if (!ways.get(i).IsEnd()) {
                        steps++;
                        switch (howManyWays(ways.get(i).GetLast())) {
                            case 0:
                                ways.get(i).EndWay();
                                break;
                            case 1:
                                oneWay(3, i, 8);
                                oneWay(0, i, 8);
                                break;
                            default:
                                cross(0, i, 8);
                                break;
                        }
                    }
                } else {
                    t[ENDPOINT.x][ENDPOINT.y] = 3;

                    complete();
                    finished = true;
                    drawCorrectWay();
                    timeStop=(int) System.currentTimeMillis();
                    FXMLDocumentController.AddToData(new DataContent("Równolegle", steps, getTime(), getLength()));
                }
            } else {

            }

        }
    }

    private int howManyWays(Point p) {
        int ile = 0;

        if (t[p.x + 1][p.y] == 0 || t[p.x + 1][p.y] == 3) {
            ile++;
        }
        if (t[p.x][p.y + 1] == 0 || t[p.x][p.y + 1] == 3) {
            ile++;
        }
        if (t[p.x - 1][p.y] == 0 || t[p.x - 1][p.y] == 3) {
            ile++;
        }
        if (t[p.x][p.y - 1] == 0 || t[p.x][p.y - 1] == 3) {
            ile++;
        }

        return ile;
    }

    private void cross(int target, int id, int footprint) {
        Point p = new Point(ways.get(id).GetLast());

        if (t[p.x - 1][p.y] == target) {
            t[p.x - 1][p.y] = footprint;
            ways.add(new Path(p));
            ways.get(ways.size() - 1).AddStep(new Point(p.x - 1, p.y));
        }
        if (t[p.x][p.y - 1] == target) {
            t[p.x][p.y - 1] = footprint;
            ways.add(new Path(p));
            ways.get(ways.size() - 1).AddStep(new Point(p.x, p.y - 1));
        }
        if (t[p.x + 1][p.y] == target) {
            t[p.x + 1][p.y] = footprint;
            ways.add(new Path(p));
            ways.get(ways.size() - 1).AddStep(new Point(p.x + 1, p.y));
        }
        if (t[p.x][p.y + 1] == target) {
            t[p.x][p.y + 1] = footprint;
            ways.add(new Path(p));
            ways.get(ways.size() - 1).AddStep(new Point(p.x, p.y + 1));
        }
    }

    private void oneWay(int target, int id, int footprint) {

        Point p = new Point(ways.get(id).GetLast());

        if (t[p.x - 1][p.y] == target) {
            ways.get(id).AddStep(new Point(p.x - 1, p.y));
            if (t[ways.get(id).GetLast().x][ways.get(id).GetLast().y] == 3) {
                ENDPOINT = ways.get(id).GetLast();
                founded = true;
            }
            t[p.x - 1][p.y] = footprint;

        } else if (t[p.x][p.y - 1] == target) {
            ways.get(id).AddStep(new Point(p.x, p.y - 1));
            if (t[ways.get(id).GetLast().x][ways.get(id).GetLast().y] == 3) {
                ENDPOINT = ways.get(id).GetLast();
                founded = true;
            }
            t[p.x][p.y - 1] = footprint;

        } else if (t[p.x + 1][p.y] == target) {
            ways.get(id).AddStep(new Point(p.x + 1, p.y));
            if (t[ways.get(id).GetLast().x][ways.get(id).GetLast().y] == 3) {
                ENDPOINT = ways.get(id).GetLast();
                founded = true;
            }
            t[p.x + 1][p.y] = footprint;

        } else if (t[p.x][p.y + 1] == target) {
            ways.get(id).AddStep(new Point(p.x, p.y + 1));
            if (t[ways.get(id).GetLast().x][ways.get(id).GetLast().y] == 3) {
                ENDPOINT = ways.get(id).GetLast();
                founded = true;
            }
            t[p.x][p.y + 1] = footprint;
        }
    }

    private void drawCorrectWay() {
        int tmpx, tmpy;
        for (int i = 0; i < correctWay.size(); i++) {
            for (int j = 0; j < ways.get(correctWay.get(i)).GetLenght(); j++) {
                tmpx = ways.get(correctWay.get(i)).GetBody(j).x;
                tmpy = ways.get(correctWay.get(i)).GetBody(j).y;
                t[tmpx][tmpy] = 9;
            }

        }
    }

    public void draw(GraphicsContext gc) {
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
                    gc.setStroke(Color.GREY);
                    gc.strokeLine(scalex / 2 + i * scalex, scaley / 2 + j * scaley, scalex / 2 + i * scalex,
                            scaley / 2 + j * scaley);
                }
            }
        }
    }

    private int findPath(Point p) {
        int i = 0;
        while (!p.isEaual(ways.get(i).GetLast()) && i < ways.size()) {
            i++;
        }
        return i;
    }

    private void complete() {
        int i;
        Point tmp = new Point(ENDPOINT);
        do {
            i = findPath(tmp);
            correctWay.add(0, i);
            tmp = ways.get(i).GetNode();
        } while (i > 0);

    }

}
