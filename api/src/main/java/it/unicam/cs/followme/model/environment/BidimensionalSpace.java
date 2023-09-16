package it.unicam.cs.followme.model.environment;


import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.hardware.ProgrammableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Rappresenta un ambiente bidimensionale all'interno del quale possono muoversi differenti
 * oggetti programmabili e dove trovano posizione oggetti statici fissi. *
 * @param <S> rappresenta le possibili forme fisse nell'ambiente
 * @param <P> rappresenta i possibili oggetti programmabili
 */
public class BidimensionalSpace<S extends Shape, P extends ProgrammableObject> implements Environment<S, P>{

    private  List<P> programmablesInSpace;
    private  List<S> shapesInSpace;

    /**
     * Costruisce uno spazio bidimensionale cui assegna oggetti programmabili e statici
     * @param programmables una lista contenente una serie di programmabili
     * @param shapes una lista contenente una serie di forme statiche.
     */
    public BidimensionalSpace(List<P> programmables, List<S> shapes) {
        this();
        this.programmablesInSpace = programmables;
        this.shapesInSpace        = shapes;
    }

    /**
     * Costruisce uno spazio bidimensionale privo di oggetti.
     */
    public BidimensionalSpace() {
        this.programmablesInSpace = new ArrayList<>();
        this.shapesInSpace        = new ArrayList<>();
    }

    /**
     * Ritorna una collezione di tutti gli oggetti attualmente presenti nello spazio
     * @return programmableOnSpace tutti gli oggetti programmabili nello spazio
     */

   // public static List<Callable<Robot>> getProgrammableInSpace() {return programmablesInSpace;}
    public List<P> getProgrammableInSpace() {return programmablesInSpace;}

    /**
     * Ritorna una collezione di tutti gli oggetti statici attualmente presenti nello spazio
     * @return shapesOnSpace tutti gli oggetti statici nello spazio
     */
    public List<S> getShapesInSpace() {return shapesInSpace;}


    /**
     * Presi come parametro una posizione nello spazio e una label, verifica i
     * programmabili entro range e filtra i risultati restituendo uno stream.
     * @param positionToCheck la posizione da verificare
     * @param labelToCheck la label da verificare
     * @param range raggio di vicinanza dei robot adiacenti.
     * @return streamOfNeighbours lo stream di tutti i robot vicini.
     */
    public Stream<P> getNeighbours(TwoDimensionalPoint positionToCheck, String labelToCheck, Double range){
        Stream<P> streamOfNeighbours = programmablesInSpace.stream()
                .filter(p->p.getLabel().equals(labelToCheck))
                .filter(programmable-> {
                    TwoDimensionalPoint center = (TwoDimensionalPoint) programmable.getPosition();
                    return Utilities.checkPointInsideCircle(positionToCheck, center, range);}
                );
        return streamOfNeighbours;
    }
}
