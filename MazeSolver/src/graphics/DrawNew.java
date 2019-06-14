/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import application.FXMLDocumentController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author borys
 */
public class DrawNew {

    private static double scalex, biasx;
    private static double scaley, biasy;

    public static double getScaleX() {
        return scalex;
    }

    public static double getScaleY() {
        return scaley;
    }

    private static void countScale(int x, int y) {
        scalex = FXMLDocumentController.sizex / x;
        scaley = FXMLDocumentController.sizey / y;
        biasx = scalex / 2;
        biasy = scaley / 2;
    }

    public static void drawLabirynth(GraphicsContext gc, int x, int y, int[][] t) {
        countScale(x, y);

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i < x - 1 && j < y - 1) {
                    switch (t[i][j]) {
                        case 1:
                            gc.setLineWidth(1);
                            gc.setStroke(Color.BLACK);
                            if (t[i + 1][j] == 1) {
                                gc.strokeLine(scalex * i + biasx, scaley * j + biasy, scalex * i + scalex + biasx, scaley * j + biasy);
                            }
                            if (t[i][j + 1] == 1) {
                                gc.strokeLine(scalex * i + biasx, scaley * j + biasy, scalex * i + biasx, scaley * j + scaley + biasy);
                            }
                            break;
                        case 2:
                            gc.setFill(Color.GREEN);
                            gc.fillRoundRect(scalex * i, scaley * j, scalex, scaley, 10, 10);
                            break;
                        case 3:
                            gc.setFill(Color.RED);
                            gc.fillRoundRect(scalex * i, scaley * j, scalex, scaley, 10, 10);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        gc.strokeLine(biasx, scaley * (y - 1) + biasy, scalex * (x - 1) + biasx, scaley * (y - 1) + biasy);
        gc.strokeLine(scalex * (x - 1) + biasx, biasy, scalex * (x - 1) + biasx, scaley * (y - 1) + biasy);
    }

}
