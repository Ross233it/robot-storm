package it.unicam.cs.followme.model.common;

import java.util.Objects;

/**
 * Estende la classe TwoDimensionalPoint ed ha la responsabilità di rappresentare un vettore di movimento.
 * Definisce una direzione di movimento caratterizzata da coordinate cartesiane comprese
 * tra i valori +1 e -1 su entrambi gli assi e da una velocità di spostamento.
 */
public class SpeedVector extends TwoDimensionalPoint {

    private double speed;
    /**
     * Ha la responsabilità di rappresentare un vettore di movimento.
     * Definisce una direzione di movimento caratterizzata da delle coordinate cartesiane comprese
     * tra i valori +1 e -1 in entrambi i casi e da una velocità di sposrtamento
     * @param dirX posizione del punto rispetto all'asse X
     * @param dirY posizione del punto rispetto all'asse Y
     * @param speed velocità di movimento degli oggetti
     */
    public SpeedVector(Double dirX, Double dirY, double speed) {
        super(dirX, dirY);
        if(dirX > 1 || dirX < -1 || dirY > 1 || dirY < -1 ||(dirY ==0.0 && dirX ==0.0))
            throw new IllegalArgumentException
                    ("Il valore della direzione non è nei valori consentiti");
        this.speed = speed;
    }

    /**
     * Ritorna la velocità di spostamento di un ogggetto in movimento
     * @return Double velocità di movimento di un oggetto.
     */
    public Double getSpeed(){
        return speed;
    }

    /**
     * Aggiunge il parametro speed al metodo equals ereditato da TwoDimensionalPosition
     * @param o Oggetto da confrontare con l'oggetto corrente
     * @return True se o e lo SpeedVector corrente sono uguali.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SpeedVector that = (SpeedVector) o;
        return Double.compare(speed, that.speed) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), speed);
    }
}
