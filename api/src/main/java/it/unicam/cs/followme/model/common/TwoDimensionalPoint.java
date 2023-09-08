package it.unicam.cs.followme.model.common;

import java.util.Objects;
import java.util.Random;


/**
 * Implementa l'interfaccia Coordinates ed ha il compito di rappresentare un punto all'interno
 * di un piano cartesiano con sistema di coordinate espresso in numeri in virgola mobile.
 */


public class TwoDimensionalPoint implements Coordinates<Double> {

    private Double positionX;
    private Double positionY;

    /**
     * Definisce un punto di cordinate positionX e poistionY all'interno di un piano cartesiano.
     * @param  positionX posizione del punto rispetto all'asse X
     * @param  positionY posizione del punto rispetto all'asse Y
     */
    public TwoDimensionalPoint(Double positionX, Double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Definisce un punto di cordinate positionX e poistionY casuali all'interno di un piano cartesiano
     * entro i limiti positivo e negativo di range.
     * @param  range stabilisce il limite massimo lungo gli assi x e y
     * @return TwoDimensionalPoint un punto casuale all'interno di +/- range
     */
    public TwoDimensionalPoint(Double range) {
        Random random = new Random();
        this.positionX =  random.nextDouble(range) ;
        this.positionY =  random.nextDouble(range);
    }

    /**
     * Ritorna la posizione del punto rispetto all'asse X di un piano cartesiano
     * @return positionX la posizione del punto rispetto all'asse X
     */
    @Override
    public Double getX() {
        return positionX;
    }

    /**
     * Ritorna la posizione del punto rispetto all'asse Y di un piano cartesiano
     * @return positionY: la posizione del punto rispetto all'asse Y
     */
    @Override
    public Double getY() {
        return positionY;
    }


    /**
     * Setta la posizione del punto rispetto all'asse X di un piano cartesiano
     * @param  newX: la posizione del punto rispetto all'asse Y espressa nell'unità di misura del piano cartesiano
     */
    @Override
    public void setX(Double newX) {
        this.positionX = newX;
    }

    /**
     * Setta la posizione del punto rispetto all'asse X di un piano cartesiano
     * @param  newY: la posizione del punto rispetto all'asse Y espressa nell'unità di misura del piano cartesiano
     */
    @Override
    public void setY(Double newY) {
        this.positionY = newY;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoDimensionalPoint that = (TwoDimensionalPoint) o;
        return Objects.equals(positionX, that.positionX) && Objects.equals(positionY, that.positionY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionX, positionY);
    }

    /**
     * Incrementa la posizione x e y di un punto sul piano cartesiano in relazione alla
     * sua posizione corrente
     * @param stepX spostamento sull'asse X
     * @param stepY spostamento sull'asse Y
     */
    public void increasePosition(Double stepX, Double stepY) {
        this.positionX += stepX;
        this.positionY += stepY;
    }

}
