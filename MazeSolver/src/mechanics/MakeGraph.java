package mechanics;

import static application.FXMLDocumentController.sizex;
import static application.FXMLDocumentController.sizey;
import graphics.DrawNew;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MakeGraph {

    private List<Branch> root;
    private double scalex, scaley;
    private List<Integer> correctWay;

    public MakeGraph() {
        root = new ArrayList<>();
        correctWay = new ArrayList<>();
    }

    public void reset(List<Branch> root, List<Integer> correctWay) {
        this.root = root;
        this.correctWay = correctWay;
        scalex = DrawNew.getScaleX();
        scaley = DrawNew.getScaleY();
    }

    public void draw(GraphicsContext gc) {

        gc.setFill(Color.BISQUE);
        gc.fillRect(0, 0, sizex, sizey);
        int x, y,x1,y1;
        gc.setFill(Color.GRAY);
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(3);
        for(int i=0;i<root.size();i++){
            for(int j=0;j<root.get(i).Paths.size();j++){{
                    x=root.get(i).Paths.get(j).GetNode().x;
                    y=root.get(i).Paths.get(j).GetNode().y;
                    gc.fillOval(x*scalex-scalex, y*scaley-scaley, scalex, scaley);
                    x1=root.get(i).Paths.get(j).GetLast().x;
                    y1=root.get(i).Paths.get(j).GetLast().y;
                    gc.strokeLine(x*scalex-scalex/2, y*scaley-scaley/2, x1*scalex-scalex/2, y1*scaley-scaley/2);
                    gc.fillOval(x1*scalex-scalex, y1*scaley-scaley, scalex, scaley);
                }
            }
        }
        gc.setStroke(Color.RED);
        for(int i=0;i<correctWay.size();i++){
            x=root.get(i).Paths.get(correctWay.get(i)).GetNode().x;
            y=root.get(i).Paths.get(correctWay.get(i)).GetNode().y;
            x1=root.get(i).Paths.get(correctWay.get(i)).GetLast().x;
            y1=root.get(i).Paths.get(correctWay.get(i)).GetLast().y;
            gc.strokeLine(x*scalex-scalex/2, y*scaley-scaley/2, x1*scalex-scalex/2, y1*scaley-scaley/2);
        }
    }

}
