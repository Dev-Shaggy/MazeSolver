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
import mechanics.Branch;
import mechanics.Path;
import mechanics.Point;
import mechanics.DataContent;

public final class BFS {

    private final List<Branch> root;
    private int[][] t;
    private int x, y;
    private double scalex, scaley;

    private boolean finished, founded,timestarted;

    private int layerID, branchID;
    private Point ENDPOINT;
    private final List<Integer> correctWay;
    
    private int steps,timeStart,timeStop;

    public BFS() {
        root = new ArrayList<>();
        correctWay = new ArrayList<>();
        reset();
    }

    public List<Branch> GetTree() {
        return root;
    }
    private double GetTime(){
        return (double)(timeStop-timeStart)/1000.0;
    }
    private int length(){
        int tmp=0;
        for(int i=0;i<correctWay.size();i++){
            tmp+=root.get(i).Paths.get(correctWay.get(i)).GetLenght();
        }
        tmp-=correctWay.size();
        tmp++;
        return tmp;
    }

    public List<Integer> GetCorrectWay() {
        return correctWay;
    }

    public void reset() {
        root.clear();
        
        correctWay.clear();
        x = FXMLDocumentController.x;
        y = FXMLDocumentController.y;
        t = FXMLDocumentController.copytab();
        root.add(new Branch());
        root.add(new Branch());
        Point point = new Point(FXMLDocumentController.x0, FXMLDocumentController.y0);
        layerID = -1;
        branchID = 0;
        AddBranches(point);
        scalex = DrawNew.getScaleX();
        scaley = DrawNew.getScaleY();
        steps=0;
        timestarted=finished=founded=false;
    }

    public void move() {
        layerID = root.size() - 2;
        if(!timestarted){
            timestarted=true;
            timeStart=(int) System.currentTimeMillis();
        }
        if (!finished) {
            if (!founded) {
                steps++;
                
                if (!root.get(layerID).Paths.get(branchID).IsEnd()) {
                    if (howManyWays(root.get(layerID).Paths.get(branchID).GetLast()) > 1) {
                        AddBranches(root.get(layerID).Paths.get(branchID).GetLast());
                        root.get(layerID).Paths.get(branchID).EndWay();
                    } else if (howManyWays(root.get(layerID).Paths.get(branchID).GetLast()) == 0) {
                        step(9);
                        root.get(layerID).Paths.get(branchID).EndWay();
                    } else {
                        step(0);
                        step(3);
                    }

                } else {
                    branchID++;
                    if (branchID == root.get(layerID).Paths.size()) {
                        branchID = 0;
                        root.add(new Branch());
                    }
                }
            } else {
                t[ENDPOINT.x][ENDPOINT.y] = 3;
                finished = true;
                complete();
                timeStop=(int) System.currentTimeMillis();
                FXMLDocumentController.AddToData(new DataContent("Przeszukiwanie wszerz", steps, GetTime(), length()));
            }
        }
    }

    private void step(int target) {

        Point p = root.get(layerID).Paths.get(branchID).GetLast();
        t[p.x][p.y] = 8;
        if (t[p.x + 1][p.y] == target) {
            root.get(layerID).Paths.get(branchID).AddStep(new Point(p.x + 1, p.y));
            if (t[p.x + 1][p.y] == 3) {
                founded = true;
                ENDPOINT = new Point(p.x + 1, p.y);
            }
            t[p.x + 1][p.y] = 8;
        } else if (t[p.x - 1][p.y] == target) {
            root.get(layerID).Paths.get(branchID).AddStep(new Point(p.x - 1, p.y));
            if (t[p.x + -1][p.y] == 3) {
                founded = true;
                ENDPOINT = new Point(p.x - 1, p.y);
            }
            t[p.x - 1][p.y] = 8;
        } else if (t[p.x][p.y + 1] == target) {
            root.get(layerID).Paths.get(branchID).AddStep(new Point(p.x, p.y + 1));
            if (t[p.x][p.y + 1] == 3) {
                founded = true;
                ENDPOINT = new Point(p.x, p.y + 1);
            }
            t[p.x][p.y + 1] = 8;
        } else if (t[p.x][p.y - 1] == target) {
            root.get(layerID).Paths.get(branchID).AddStep(new Point(p.x, p.y - 1));
            if (t[p.x][p.y - 1] == 3) {
                founded = true;
                ENDPOINT = new Point(p.x, p.y - 1);
            }
            t[p.x][p.y - 1] = 8;
        }
    }

    private void AddBranches(Point p) {
        int id = root.get(layerID + 1).Paths.size();

        if (t[p.x + 1][p.y] == 0) {
            t[p.x][p.y] = 8;
            root.get(layerID + 1).Paths.add(new Path(p));
            root.get(layerID + 1).Paths.get(id).AddStep(new Point(p.x + 1, p.y));
            id++;
        }
        if (t[p.x - 1][p.y] == 0) {
            t[p.x][p.y] = 8;
            root.get(layerID + 1).Paths.add(new Path(p));
            root.get(layerID + 1).Paths.get(id).AddStep(new Point(p.x - 1, p.y));
            id++;
        }
        if (t[p.x][p.y + 1] == 0) {
            t[p.x][p.y] = 8;
            root.get(layerID + 1).Paths.add(new Path(p));
            root.get(layerID + 1).Paths.get(id).AddStep(new Point(p.x, p.y + 1));
            id++;
        }
        if (t[p.x][p.y - 1] == 0) {
            t[p.x][p.y] = 8;
            root.get(layerID + 1).Paths.add(new Path(p));
            root.get(layerID + 1).Paths.get(id).AddStep(new Point(p.x, p.y - 1));
            id++;
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

    public void draw(GraphicsContext gc) {

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

    private int findPath(Point p, int layer) {
        int i = 0;
        while (!p.isEaual(root.get(layer).Paths.get(i).GetLast()) && i < root.get(layer).Paths.size()) {
            i++;
        }
        return i;
    }

    private void complete() {
        int i = root.size() - 1, j;
        Point tmp = new Point(ENDPOINT);
        while (i > 0) {
            i--;
            j = findPath(tmp, i);
            correctWay.add(0, j);
            tmp = root.get(i).Paths.get(j).GetNode();
        }
        drawPath();
    }

    private void drawPath() {
        int tmpx, tmpy;

        for (int i = 0; i < correctWay.size(); i++) {
            for (int j = 0; j < root.get(i).Paths.get(correctWay.get(i)).GetLenght(); j++) {
                tmpx = root.get(i).Paths.get(correctWay.get(i)).GetBody(j).x;
                tmpy = root.get(i).Paths.get(correctWay.get(i)).GetBody(j).y;
                t[tmpx][tmpy] = 9;
            }
        }
    }
}
