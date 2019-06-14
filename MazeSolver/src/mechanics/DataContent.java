package mechanics;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataContent {

    private final SimpleStringProperty Metoda;
    private final SimpleIntegerProperty Iteracje;
    private final SimpleIntegerProperty Slady;
    private final SimpleDoubleProperty Czas;

    public DataContent(String Metoda, Integer Iteracje, Double Czas, Integer Slady) {
        this.Metoda = new SimpleStringProperty(Metoda);
        this.Iteracje = new SimpleIntegerProperty(Iteracje);
        this.Czas = new SimpleDoubleProperty(Czas);
        this.Slady = new SimpleIntegerProperty(Slady);
    }

    public Integer getIteracje() {
        return Iteracje.get();
    }

    public Integer getSlady() {
        return Slady.get();
    }

    public Double getCzas() {
        return Czas.get();
    }

    public String getMetoda() {
        return Metoda.get();
    }

}
