package it.unicam.cs.followme.app;

import javafx.beans.binding.Bindings;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.chart.NumberAxis;

public class CartesianAxisManager {
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private Double tickSize;
    private Integer scale;
    private final Double SPACE_SQUARE = 700.0;

    private Group cartesian;

    /**
     * @param scale
     * @param cartesian
     */
    public CartesianAxisManager(Integer scale, Group cartesian){
        this.cartesian = cartesian;
        this.scale = scale;
        axisSetup(scale, cartesian);
    }

    /**
     * Esegue l'aggiornamento degli assi in base ad una scala dimensionale
     * @param scale fattore moltiplicativo dei tick.
     */
    public void axisSetup(Integer scale, Group cartesian){
        NumberAxis xAxis = generateXAxis(scale);
        NumberAxis yAxis = generateYAxis(scale);
        updateTickSize(scale);
        updateAxis(xAxis, yAxis, cartesian);
        this.scale = scale;
        this.cartesian = cartesian;
    }

    /**
     * Genera un asse per le ascisse
     * @param scale fattore di scala per l'asse
     * @return xAxis l'asse x allocato al centro dello spazio della simulazione.
     */
    private NumberAxis generateXAxis(Integer scale){
        xAxis = new NumberAxis(-scale, scale, 1);
        xAxis.setSide(Side.BOTTOM);
        xAxis.setMinorTickVisible(false);
        xAxis.setPrefWidth(SPACE_SQUARE);
        xAxis.setLayoutY((SPACE_SQUARE) / 2.0);
        return xAxis;
    }

    /**
     * Genera un asse per le ordinate
     * @param scale fattore di scala per l'asse
     * @return yAxis l'asse y allocato al centro dello spazio della simulazione.
     */
    private NumberAxis generateYAxis(Integer scale) {
        yAxis = new NumberAxis(-scale, scale, 1);
        yAxis.setSide(Side.LEFT);
        yAxis.setMinorTickVisible(false);
        yAxis.setPrefHeight(SPACE_SQUARE);
        yAxis.layoutXProperty().bind(
                Bindings.subtract(
                        ((SPACE_SQUARE) / 2) + 1,
                        yAxis.widthProperty())
        );
        return yAxis;
    }


    /**
     * Aggiorna il valore assoluto di un tick degli assi
     * @param scale fattore moltiplicativo ad esempio di zoom
     */
    private void updateTickSize(Integer scale){
        this.tickSize = (SPACE_SQUARE/scale)/2;
    }


    /**
     * Aggiorna gli assi cartesiani presenti nella scena
     * @param xAxis un asse per le ascisse
     * @param yAxis un assse per le ordinate
     */
    private void updateAxis(NumberAxis xAxis, NumberAxis yAxis, Group cartesian){
        cartesian.getChildren().clear();
        cartesian.getChildren().addAll(xAxis, yAxis);
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }


    /**
     * Ritorna la dimensione assoluta del tick.
     */
    public Double getTickSize(){return this.tickSize;}

    /**
     * Ritorna la scala dimensionale degli assi
     */
    public Integer getScale(){return this.scale;}

    public NumberAxis getxAxis() {
        return xAxis;
    }

    public NumberAxis getyAxis() {
        return yAxis;
    }
}
