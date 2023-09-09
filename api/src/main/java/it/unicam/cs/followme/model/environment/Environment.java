package it.unicam.cs.followme.model.environment;

import java.util.List;


/**
 * L'interfaccia astrae l'ambiente illimitato all'interno del quale sono posizionati
 * oggetti statici e nel quale svolgono le loro attività oggetti mobili e programmati.
 * Le classi che implementano questa interfaccia costruiscono l'ambiente dove opera l'applicazione.
 *
 * @param <S> oggetti di tipo Shape
 * @param <P> oggetti di tipo Programmable
 */
public interface Environment<S, P>{
    /**
     * Ritorna una lista degli oggetti programmabili presenti nell'ambiente.
     * @return
     */
    List<P> getProgrammableInSpace();

    /**
     * Ritorna una collezione degli oggetti geometrici presenti nell'ambiente.
     * @return
     */
    List<S> getShapesInSpace();
}

