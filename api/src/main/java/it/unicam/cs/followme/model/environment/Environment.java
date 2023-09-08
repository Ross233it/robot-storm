package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.model.programmables.ProgrammableObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * L'interfaccia astrae l'ambiente illimitato all'interno del quale sono posizionati
 * oggetti statici e nel quale svolgono le loro attività oggetti mobili e programmati.
 * Le classi che implementano questa interfaccia costruiscono l'ambiente dove opera l'applicazione.
 *
 * @param <S> oggetti di tipo Shape
 * @param <P> oggetti di tipo Programmable
 */

public interface Environment<S, P>{

    List<P> getProgrammableInSpace();

    List<S> getShapesInSpace();

    void addProgrammableToSpace(P prog);

    void addShapesToSpace(S shape);

}

