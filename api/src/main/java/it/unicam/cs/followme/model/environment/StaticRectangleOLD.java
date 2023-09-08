package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;

public record StaticRectangle(String label,
                              TwoDimensionalPoint position,
                              Double width,
                              Double height){

    public StaticRectangle customConstructor(String label, TwoDimensionalPoint position, Double width, Double height){
        if(width.doubleValue()<0 || height.doubleValue()<0)
            throw new IllegalArgumentException("Negative Rectangle dimensions");
        return new StaticRectangle(label, position, width, height);
    }

    /**
     * Dato un punto del piano cartesiano verifica se è interno o esterno alla figura.
     * @return TRUE se il punto è interno alla figura
     * @return FALSE se il punto è esterno alla figura
     */
    public Boolean isInternal(TwoDimensionalPoint pos){
        return  (  (pos.getX()  >= position().getX()-(width()/2))
                && (pos.getX()  <= position().getX()+(width()/2))
                && (pos.getY()  <= position().getY()+(height()/2))
                && (pos.getY()  >= position().getY()-(height()/2))
        );
    };
}
