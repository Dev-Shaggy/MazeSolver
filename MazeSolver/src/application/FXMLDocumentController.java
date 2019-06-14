/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import static graphics.DrawNew.drawLabirynth;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import mechanics.LabirynthGenerate;
import mechanics.MakeGraph;
import mechanics.DataContent;
import solves.BFS;
import solves.IterRandom;
import solves.Iterative;
import solves.Parallel;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Canvas canvas, newCanvas;
    @FXML
    private TextField numrows, numcolumns, odsetek;
    @FXML
    private RadioMenuItem solve0, solve1, solve2, solve3;
    @FXML
    private Button showGraph;

    public static LabirynthGenerate lab;
    public static GraphicsContext gc, tempgc;
    public static double sizex, sizey;
    public static int[][] tab;
    public int[][] tabsave;
    public static int x, y, x0, y0;
    public int choose = 0;
    private IterRandom itr;
    boolean RUN = false;

    private Parallel parallel;
    private Iterative iteracyjnie;
    private BFS bfs;
    private MakeGraph MG;

    private static ObservableList<DataContent> data;
    @FXML
    private TableView<DataContent> table;
    @FXML
    private TableColumn<DataContent, String> Metoda;
    @FXML
    private TableColumn<DataContent, Integer> Iteracje;
    @FXML
    private TableColumn<DataContent, Double> Czas;
    @FXML
    private TableColumn<DataContent, Integer> Slady;

    private boolean labirynthDone, useDone;
    
    @FXML
    private BarChart<String, Number> barChartIterations,barChartTimes, barChartLengths;
    @FXML
    private NumberAxis yAxisIterations,yAxisTimes,yAxisLength;
    @FXML
    private CategoryAxis xAxisIterations,xAxisTimes,xAxisLength;
    
    private static XYChart.Series<String,Number> seriesIterations, seriesTimes,seriesLengths;
            

    public static int[][] copytab() {
        int t[][] = new int[x][y];

        for (int i = 0; i < x; i++) {
            System.arraycopy(tab[i], 0, t[i], 0, y);
        }
        return t;
    }

    @FXML
    public void togglebuttonSolve() {
        if (solve0.isSelected()) {
            choose = 0;
        } else if (solve1.isSelected()) {
            choose = 1;
        } else if (solve2.isSelected()) {
            choose = 2;
        } else if (solve3.isSelected()) {
            choose = 3;
        }
    }

    @FXML
    public void buttonFile() {

    }

    @FXML
    public void buttonStart() {

        if (useDone) {
            RUN = true;
        }
    }

    @FXML
    public void buttonStop() {
        RUN = false;
    }

    @FXML
    public void showGraph() {
        if (choose == 3) {
            choose = 4;
            showGraph.setText("Pokaż labirynt");

        } else {
            choose = 3;
            showGraph.setText("Pokaż graf");
        }

    }

    @FXML
    public void buttonReset() {

        if (useDone) {
            gc.setFill(Color.BISQUE);
            gc.fillRect(0, 0, sizex, sizex);
            drawLabirynth(gc, x, y, tab);
            RUN = false;
            itr.reset();
            iteracyjnie.reset();
            bfs.reset();
            parallel.reset();
            MG.reset(bfs.GetTree(), bfs.GetCorrectWay());
        }

    }

    @FXML
    public void buttonClose() {
        Platform.exit();
    }

    @FXML
    public void buttonNew() {
        if (!numrows.getText().isEmpty() && !numcolumns.getText().isEmpty()) {
            tempgc.setFill(Color.BISQUE);
            tempgc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            int rows, columns, loops;
            try {
                rows = Integer.parseInt(numrows.getText());
                columns = Integer.parseInt(numcolumns.getText());
                if (!odsetek.getText().isEmpty()) {
                    loops = Integer.parseInt(odsetek.getText());
                } else {
                    loops = 0;
                }

                lab = new LabirynthGenerate(columns, rows, loops);
                drawLabirynth(tempgc, lab.x, lab.y, lab.copyLabirynth());
                labirynthDone = true;
            } catch (NumberFormatException e) {

            }

        }
    }

    @FXML
    public void buttonSave() {

    }

    @FXML
    public void buttonUse() {
        if (labirynthDone) {
            gc.setFill(Color.BISQUE);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            tab = lab.copyLabirynth();
            tabsave = lab.copyLabirynth();
            x = lab.x;
            y = lab.y;
            x0 = lab.x0;
            y0 = lab.y0;
            drawLabirynth(gc, x, y, tab);
            itr = new IterRandom();
            iteracyjnie = new Iterative();
            parallel = new Parallel();
            bfs = new BFS();
            MG = new MakeGraph();
            MG.reset(bfs.GetTree(), bfs.GetCorrectWay());

            useDone = true;
        }
    }

    public static void AddToData(DataContent record) {
        data.add(record);
        
       seriesIterations.getData().add(new XYChart.Data(record.getMetoda(),record.getIteracje()));
       seriesTimes.getData().add(new XYChart.Data(record.getMetoda(),record.getCzas()));
       seriesLengths.getData().add(new XYChart.Data(record.getMetoda(),record.getSlady()));
        
        
        
    }

    @FXML
    public void buttonClear() {
        data.clear();
        seriesIterations.getData().clear();
        seriesLengths.getData().clear();
        seriesTimes.getData().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Rectangle2D rect = Screen.getPrimary().getVisualBounds();

        sizex = rect.getWidth() - 200.0;
        sizey = rect.getHeight() - 130.0;

        canvas.setWidth(sizex);
        canvas.setHeight(sizey);

        newCanvas.setWidth(sizex);
        newCanvas.setHeight(sizey);

        gc = canvas.getGraphicsContext2D();
        tempgc = newCanvas.getGraphicsContext2D();

        tempgc.setFill(Color.BISQUE);
        tempgc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.BISQUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        data = FXCollections.observableArrayList();
        Metoda.setCellValueFactory(new PropertyValueFactory<>("Metoda"));
        Iteracje.setCellValueFactory(new PropertyValueFactory<>("Iteracje"));
        Czas.setCellValueFactory(new PropertyValueFactory<>("Czas"));
        Slady.setCellValueFactory(new PropertyValueFactory<>("Slady"));
        table.setItems(data);
        
        xAxisIterations.setLabel("Metoda przechodzenaia");
        yAxisIterations.setLabel("Liczba odkrytych pól");
        
        seriesIterations = new XYChart.Series();

        barChartIterations.getData().add(seriesIterations);
        
        xAxisTimes.setLabel("Metoda przechodzenaia");
        yAxisTimes.setLabel("Czas w sekundach");
        
        seriesTimes = new XYChart.Series();

        barChartTimes.getData().add(seriesTimes);
        
        xAxisLength.setLabel("Metoda przechodzenai");
        yAxisLength.setLabel("Gługość prawidłowej ścieżki");
        
        seriesLengths = new XYChart.Series();
        barChartLengths.getData().add(seriesLengths);
//        barChartLengths.getData().addAll(seriesLengths,seriesIterations,seriesTimes);
        
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (RUN) {
                    switch (choose) {
                        case 0:
                            itr.move();
                            itr.iterDraw(gc);
                            break;
                        case 1:
                            iteracyjnie.move();
                            iteracyjnie.draw(gc);
                            break;
                        case 2:
                            parallel.move();
                            parallel.draw(gc);
                            break;
                        case 3:
                            bfs.move();
                            bfs.draw(gc);
                            break;
                        case 4:
                            MG.draw(gc);
                            break;
                        default:
                            break;
                    }
                }
            }
        }.start();

    }
}
