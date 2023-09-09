package it.unicam.cs.followme.model.common;

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
        if(dirX > 1 || dirX < -1 || dirY > 1 || dirY < -1)
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
}
