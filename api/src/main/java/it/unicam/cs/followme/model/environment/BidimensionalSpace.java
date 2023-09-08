package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.programmables.ProgrammableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rappresenta un ambiente bidimensionale all'interno del quale possono muoversi differenti
 * oggetti programmabili e dove trovano posizione oggetti statici fissi. *
 * @param <S> rappresenta le possibili forme fisse nell'ambiente
 * @param <P> rappresenta i possibili oggetti programmabili
 */
public class BidimensionalSpace<S extends Shape,
                                P extends ProgrammableObject> implements Environment<S, P>{

    private  List<P> programmablesInSpace;
    private  List<S> shapesInSpace;

    /**
     * Costruisce uno spazio bidimensionale vuoto
     */
    public BidimensionalSpace() {
        this.programmablesInSpace = new ArrayList<P>();
        this.shapesInSpace        = new ArrayList<S>();
    }

    /**
     * Costruisce uno spazio bidimensionale cui assegna oggetti programmabili e statici
     * @param programmables una lista contenente una serie di programmabili
     * @param shapes una lista contenente una serie di forme statiche.
     */
    public BidimensionalSpace(List<P> programmables, List<S> shapes) {
        this.programmablesInSpace = programmables;
        this.shapesInSpace        = shapes;
    }

    /**
     * Ritorna una collezione di tutti gli oggetti attualmente presenti nello spazio
     * @return programmableOnSpace tutti gli oggetti programmabili nello spazio
     */
    @Override
    public List<P> getProgrammableInSpace() {return programmablesInSpace;}

    /**
     * Ritorna una collezione di tutti gli oggetti statici attualmente presenti nello spazio
     * @return shapesOnSpace tutti gli oggetti statici nello spazio
     */
    @Override
    public List<S> getShapesInSpace() {
        return shapesInSpace;
    }

    /**
     * Aggiunge un oggetto programmabile all'ambiente
     * @param programmable un oggetto di tipo programmabile
     */
    @Override
    public void addProgrammableToSpace(P programmable){
        this.programmablesInSpace.add(programmable);
    };

    /**
     * Aggiunge un oggetto foram all'ambiente
     * @param shape un oggetto di tipo forma.
     */
    @Override
    public  void addShapesToSpace(S shape){
        this.shapesInSpace.add(shape);
    }

    public List<P> getNeighbours(TwoDimensionalPoint positionToCheck, String labelToCheck, Double range){
       return this.programmablesInSpace.parallelStream()
               .filter(programmable->programmable.getLabel() == labelToCheck)
               .filter(programmable-> {
                   TwoDimensionalPoint center = (TwoDimensionalPoint) programmable.getPosition();
                   return Utilities.checkPointInsideCircle(positionToCheck, center, range);}
               ).collect(Collectors.toList());
    }

    //boolean
    public List<S> getShapesWhereIamIn(TwoDimensionalPoint positionToCheck, String labelToCheck){
        return this.shapesInSpace.parallelStream()
                .filter(shape->shape.getLabel() == labelToCheck)
                .filter(shape->shape.isInternal(positionToCheck))
                .collect(Collectors.toList());
    }
}
