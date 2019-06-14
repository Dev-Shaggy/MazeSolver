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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import mechanics.DataContent;
import mechanics.Point;

/**
 *
 * @author borys
 */
public final class Iterative {

    private int[][] t;
    List<Point> lista;
    private double scalex, scaley;
    private boolean freeWay;
    private int x, y;
    private int timeStart, timeStop, steps;
    private boolean timestarted, founded;

    public Iterative() {
        lista = new ArrayList<>();

        reset();

    }
    private double getTime(){
        return (double)(timeStop-timeStart)/1000.0;
    }
    private  int getLength(){
        return lista.size();
    }

    public void reset() {
        x = FXMLDocumentController.x;
        y = FXMLDocumentController.y;
        t = FXMLDocumentController.copytab();
        lista.clear();
        lista.add(new Point(FXMLDocumentController.x0, FXMLDocumentController.y0));
        founded =false;
        timestarted=false;
        steps=0;
    }

    private void checkway(int target, int id, int footprint) {
        freeWay = true;
        if (t[(lista.get(id).x) - 1][(lista.get(id).y)] == target) {
            t[lista.get(id).x][lista.get(id).y] = footprint;
            lista.add(new Point(lista.get(id).x - 1, lista.get(id).y));
        } else if (t[lista.get(id).x][lista.get(id).y - 1] == target) {
            t[lista.get(id).x][lista.get(id).y] = footprint;
            lista.add(new Point(lista.get(id).x, lista.get(id).y - 1));
        } else if (t[lista.get(id).x + 1][lista.get(id).y] == target) {
            t[lista.get(id).x][lista.get(id).y] = footprint;
            lista.add(new Point(lista.get(id).x + 1, lista.get(id).y));
        } else if (t[lista.get(id).x][lista.get(id).y + 1] == target) {
            t[lista.get(id).x][lista.get(id).y] = footprint;
            lista.add(new Point(lista.get(id).x, lista.get(id).y + 1));
        } else {
            freeWay = false;
        }
    }

    private void back() {
        int id = lista.size() - 1;

        t[lista.get(id).x][lista.get(id).y] = 8;
        lista.remove(id);
    }

    public void move() {
        if(!founded)
        {
            if(!timestarted)
            {
                timestarted=true;
                timeStart=(int) System.currentTimeMillis();
            }
            steps++;
            int id = lista.size() - 1;
            if (t[lista.get(id).x][lista.get(id).y] != 3) {
                checkway(3, id, 9);
                if (!freeWay) {
                    checkway(0, id, 9);
                }
                if (!freeWay) {
                    back();
                }
            }else{
                founded=true;
                timeStop=(int) System.currentTimeMillis();
                FXMLDocumentController.AddToData(new DataContent("Iteracyjnie z ustaloną sekwencją", steps, getTime(), getLength()));
            }
            
        }
    }

    public void draw(GraphicsContext gc) {
        scalex = DrawNew.getScaleX();
        scaley = DrawNew.getScaleY();
        int id = lista.size() - 1;
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
                gc.strokeLine(scalex / 2 + lista.get(id).x * scalex, scaley / 2 + lista.get(id).y * scaley, scalex / 2 + lista.get(id).x * scalex,
                        scaley / 2 + lista.get(id).y * scaley);
            }
        }
    }

}
