package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;


/**
 * Rappresenta la forma statica rettangolare che può essere disposta nello spazio
 *
 */
public class Rectangle implements Shape<TwoDimensionalPoint> {
        private final TwoDimensionalPoint position;
        private String label;
        private final Double width;
        private final Double height;

    /**
     * Crea una forma di tipo rettangolo.
     * @param position la posizione del punto centrale della figura
     * @param label  condizione del rettangolo
     * @param width  la larghezza del rettangolo
     * @param height altezza del rettangolo
     * @throws IllegalArgumentException if width or height are negative.
     */
    public Rectangle(TwoDimensionalPoint position, String label, Double width, Double height) {
        if(width.doubleValue()<0 || height.doubleValue()<0)
            throw new IllegalArgumentException("Negative Rectangle dimensions");
        this.position = position;
        this.width = width;
        this.height = height;
        this.label = label;
    }

    /**
     *
     * Crea un rettangolo con etichetta vuota
     * @param position la posizione del punto centrale della figura
     * @param width la larghezza del rettangolo
     * @param height altezza del rettangolo
     * @throws IllegalArgumentException if width or height are negative.
     */
    public Rectangle(TwoDimensionalPoint position,  Double width, Double height) {
        if(width.doubleValue()<0 || height.doubleValue()<0)
            throw new IllegalArgumentException("Negative Rectangle dimensions");
        this.position = position;
        this.width = width;
        this.height = height;
        this.label = "";
    }

    /**
     * Dato un punto del piano cartesiano verifica se è interno o esterno alla figura.
     *
     * @return TRUE se il punto è interno alla figura
     * @return FALSE se il punto è esterno alla figura
     */
    public Boolean isInternal(TwoDimensionalPoint pos){
        return  (  (pos.getX()  >= position.getX()-(width/2))
                && (pos.getX()  <= position.getX()+(width/2))
                && (pos.getY()  <= position.getY()+(height/2))
                && (pos.getY()  >= position.getY()-(height/2))
        );
    };

    /**
     * Ritorna la larghezza della figura
     * @return width larghezza del rettangolo
     */
    public Double getWidth() {
        return width;
    }

    /**
     * Ritorna l'altezza della figura
     * @return height altezza del rettangolo
     */
    public Double getHeight() {
        return height;
    }

    /**
     * Ritorna la condizione corrente del rettangolo
     * @return label condizione del rettangolo
     */
    public String getLabel() {
        return label;
    }

    /**
     * Ritorna il punto di incrocio delle diagonali del rettangolo inteso come
     * posizione e punto di centro della figura
     * @return position centro del rettangolo.
     */
    public TwoDimensionalPoint getPosition() {
        return position;
    }

}
