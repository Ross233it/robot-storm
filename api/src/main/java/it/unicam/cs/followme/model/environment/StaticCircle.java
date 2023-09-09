package it.unicam.cs.followme.model.environment;


import it.unicam.cs.followme.model.common.Coordinates;
import it.unicam.cs.followme.model.common.Utilities;

/**
 * Rappresenta la forma statica circolare che può essere disposta nello spazio 2D.
 */
public class StaticCircle<C extends Coordinates<Double>> implements Shape<C> {
    private final C position;
    private String label;
    private final Double radius;


    /**
     * Crea una forma di tipo StaticCircle
     * @param position la posizione del centro della circonferenza
     * @param radius raggio della circonferenza
     * @param label  stato/condizione della figura
     * @throws IllegalArgumentException se il raggio del cerchio è negativo
     */
    public StaticCircle(C position, String label, Double radius) {
        if(radius < 0)
            throw new IllegalArgumentException("Negative circle radius");
        this.position = position;
        this.radius = radius;
        this.label = label;
    }

    /**
     * Dato un punto del piano cartesiano verifica se è interno o esterno alla figura
     * @return TRUE se il punto è interno o sulla circonferenza
     * @return FALSE se il punto è esterno alla circonferenza
     */
    @Override
    public Boolean isInternal (C pointToCheck){
        return Math.sqrt(Math.pow(pointToCheck.getX() - this.position.getX(), 2)
                + Math.pow(pointToCheck.getY() - this.position.getY(), 2)) <=  radius;
    }

    /**
     * Ritorna la condizione corrente del cerchio
     * @return label condizione del cerchio
     */
    @Override
    public String getLabel() {
        return label;
    }

    /**
     * Ritorna il raggio del cerchio
     * @return radius condizione del cerchio
     */
    public Double getRadius() {
        return radius;
    }

    /**
     * Ritorna la posizione del punto di centro della circonferenza.
     * @return position centro del StaticCircle
     */
    @Override
    public C getPosition() {
        return position;
    }
}
