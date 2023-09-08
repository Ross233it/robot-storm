package it.unicam.cs.followme.model.programmables;

import it.unicam.cs.followme.model.common.SpeedVector;

/**
 * Astrae il concetto di 'oggetto programmabile', ovvero
 * un elemento inserito nell'ambiente, che possiede uno stato definito in una label e
 * la capacità di ricevere ed eseguire una serie di istruzioni {@link it.unicam.cs.followme.model.language.ProgramCommand}
 * in un linguaggio a lui comprensibile {@link it.unicam.cs.followme.model.language.ProgramLanguage}
 * @param P oggetti riferiti ai programmi eseguibili dal programmabile
 * @param C oggetti riferiti al posizionamento del programmabile
 */

public interface ProgrammableObject<P, C, V> {
    void executeProgram();

    C getPosition();

    void setPosition(C pos);

    void changePosition(V pos);


    String getLabel();

    void setLabel(String label);

    V getDirection();
}
