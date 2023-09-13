package it.unicam.cs.followme.model.environment;

/**
 * Astrae il concetto di posizione nello spazio.
 * @param <C> oggetto che rappresenta un punto nello spazio.
 */
public interface Positionable<C> {

    C getPosition();

    void setPosition(C pos);


}
