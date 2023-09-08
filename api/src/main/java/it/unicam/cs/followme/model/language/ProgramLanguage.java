package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.model.programmables.ProgrammableObject;

/**
 * Astrae il concetto di linguaggio di programmazione per gli oggetti che
 * possono essere programmati.
 *
 * @param <A> Tutti i sottotipi di ProgrammableObject
 * @param <P> Tutti i sottotipi di ProgrammableObject
 */

public interface ProgramLanguage<A, P>{
    /**
     * Definisce le istruzioni contemplate nel linguaggio di programmazione
     * e ne consente l'esecuzione.
     *
     * @param programmableObject oggetto programmabile
     */
    //void execute(A command, P programmableObject);
}
