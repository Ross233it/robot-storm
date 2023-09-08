package it.unicam.cs.followme.model.environment;


import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.common.Utilities;

/**
 * Rappresenta la forma statica circolare che può essere disposta nello spazio planare
 */
public class Circle implements Shape<TwoDimensionalPoint> {
    private TwoDimensionalPoint position;
    private String label;
    private Double radius;


    /**
     * Crea una forma di tipo Circle
     * @param position la posizione del centro della circonferenza
     * @param radius raggio della circonferenza
     * @param label  stato/condizione della figura
     * @throws IllegalArgumentException se il raggio del cerchio è negativo
     */
    public Circle(TwoDimensionalPoint position, String label, Double radius) {
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
    public Boolean isInternal (TwoDimensionalPoint pointToCheck){
        return Utilities.checkPointInsideCircle(pointToCheck, this.position, this.radius);
    };


    /**
     * Ritorna la condizione corrente del cerchio
     * @return label condizione del cerchio
     */
    public String getLabel() {
        return label;
    }


    /**
     * Ritorna la posizione del punto di centro della circonferenza.
     * @return position centro del Circle
     */
    public TwoDimensionalPoint getPosition() {
        return position;
    }
}
