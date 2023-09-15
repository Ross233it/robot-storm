package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.model.common.Coordinates;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;


/**
 * Rappresenta la forma statica rettangolare che può essere disposta nello spazio
 * Gestisce dati che implementano l'interfaccia Coordinates con formati numerici double
 */
public class StaticRectangle<C extends Coordinates> implements Shape<TwoDimensionalPoint>{
        private       TwoDimensionalPoint position;
        private       String label;
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
    public StaticRectangle(TwoDimensionalPoint position, String label, Double width, Double height) {
        if(width.doubleValue()<=0 || height.doubleValue()<=0)
            throw new IllegalArgumentException("Negative StaticRectangle dimensions");
        this.position = position;
        this.width = width;
        this.height = height;
        this.label = label;
    }

    /**
     * Dato un punto del piano cartesiano verifica se è interno o esterno alla figura.
     * @return TRUE se il punto è interno alla figura
     * @return FALSE se il punto è esterno alla figura
     */
    @Override
    public Boolean isInternal(TwoDimensionalPoint pos){
        return  (  (pos.getX()  >= position.getX()-(width/2))
                && (pos.getX()  <= position.getX()+(width/2))
                && (pos.getY()  <= position.getY()+(height/2))
                && (pos.getY()  >= position.getY()-(height/2)));
    }

    /**
     * Ritorna la larghezza della figura
     * @return width larghezza del rettangolo
     */
    public Double getWidth() {return width;}

    /**
     * Ritorna l'altezza della figura
     * @return height altezza del rettangolo
     */
    public Double getHeight() {return height;}

    /**
     * Ritorna la condizione corrente del rettangolo
     * @return label condizione del rettangolo
     */
    @Override
    public String getLabel() {return label;}

    /**
     * Ritorna il punto di incrocio delle diagonali del rettangolo inteso come
     * posizione e punto di centro della figura
     * @return position centro del rettangolo.
     */
    @Override
    public TwoDimensionalPoint getPosition() {return position;}

    /**
     * Assegna una nuova posizione all'oggetto rettangolo.
     * @param pos la nuova posizione
     */
    @Override
    public void setPosition(TwoDimensionalPoint pos) {this.position = pos;}

}
