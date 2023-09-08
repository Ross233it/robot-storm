package it.unicam.cs.followme.model.common;

/**
 * Estende la classe Location ed ha la responsabilità di rappresentare un vettore di movimento.
 * Definisce una direzione di movimento caratterizzata da delle coordinate cartesiane comprese
 * tra i valori +1 e -1 in entrambi gli assi e da una velocità di sposrtamento.
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
        super(dirX * speed, dirY * speed);
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

    /**
     * Cambia la velocità di movimento impostata per questa direzione
     * @param newSpeed  nuova velocità di movimento di un oggetto.
     */
    public void changeSpeed(Double newSpeed){
        this.setX((this.getX()/speed) * newSpeed);
        this.setY((this.getY()/speed) * newSpeed);
    }
}
