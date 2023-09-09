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
        this.positionX =  Utilities.randomDoubleNumber(-range, range);
        this.positionY =  Utilities.randomDoubleNumber(-range, range);
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


    /**
     * Definisce l'uguaglianza di due oggetti della classe quando i loro campi positionX
     * e positionY sono uguali.
     * @param o Oggetto da confrontare con l'oggetto corrente
     * @return True se gli oggetti hanno uguali campi False in caso contrario.
     */
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
}
