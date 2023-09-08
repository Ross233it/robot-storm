package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;

public record StaticCircle(String label, TwoDimensionalPoint position, Double radius) /*implements Shape<TwoDimensionalPoint>*/
{
    /**
     * Factory method per consentire i controlli minimi dei parametri
     * @param label
     * @param position
     * @param radius
     * @return
     */
    public StaticCircle customConstructor(String label, TwoDimensionalPoint position, Double radius){
        if(radius < 0)
            throw new IllegalArgumentException("Negative circle radius");
        return new StaticCircle(label, position, radius);
    }

    /**
     * Dato un punto del piano cartesiano verifica se è interno o esterno alla figura
     * @return TRUE se il punto è interno o sulla circonferenza
     * @return FALSE se il punto è esterno alla circonferenza
     */
    public Boolean isInternal (TwoDimensionalPoint pos){
        double distance = Math.sqrt(Math.pow
                (pos.getX() - position().getX(), 2) + Math.pow(pos.getY() - position().getY(), 2));
        return distance <=  radius;
    };
}
