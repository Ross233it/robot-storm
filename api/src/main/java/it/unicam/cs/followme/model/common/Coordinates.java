package it.unicam.cs.followme.model.common;

/**
 * Rappresenta l'astrazione della coordinata spaziale. Le classi che implementano questa interfaccia
 * devono definire almeno due grandezze X e Y.
 */
public interface Coordinates<I> {
    /**
     * Ritorna la posizione X della coordinata.
     * @return X posizione x
     */
    I getX();

    /**
     * Ritorna la posizione Y della coordinata.
     * @return Y posizione Y
     */
    I getY();


    /**
     * Modifica la posizione X della coordinata.
     * @param newX la nuova posizione sull'asse
     */
    void setX(I newX);

    /**
     * Modifica la posizione X della coordinata.
     * @param newY la nuova posizione sull'asse
     */
    void setY(I newY);
}
