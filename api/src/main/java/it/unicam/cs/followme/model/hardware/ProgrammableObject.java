package it.unicam.cs.followme.model.hardware;

import it.unicam.cs.followme.model.environment.Positionable;

/**
 * Astrae il concetto di 'oggetto programmabile', ovvero
 * un elemento fisico inserito nell'ambiente, che possiede uno stato definito in una label,
 * una posizione nello spazio ed ha la capacità modificarle.
 * @param C oggetti riferiti al posizionamento del programmabile
 * @param V oggetti riferiti allo spostamento del programmabile
 */

public interface ProgrammableObject<V, C> extends Positionable<C> {

    String getLabel();

    void setLabel(String label);

    V getDirection();

    void setDirection(V vec);

    int getId();

    C getPosition();

    void setPosition(C pos);
}
