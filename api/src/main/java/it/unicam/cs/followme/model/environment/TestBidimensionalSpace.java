package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.programmables.ProgrammableObject;
import it.unicam.cs.followme.model.programmables.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * Rappresenta un ambiente bidimensionale all'interno del quale possono muoversi differenti
 * oggetti programmabili e dove trovano posizione oggetti statici fissi. *
 * @param <Shape> rappresenta le possibili forme fisse nell'ambiente
 * @param <Robot> rappresenta i possibili oggetti programmabili
 */
public class TestBidimensionalSpace /*implements Environment<Shape, Robot>*/{

    private  static List<Callable<Robot>> programmablesInSpace;
    private  static List<Shape> shapesInSpace;

    /**
     * Costruisce uno spazio bidimensionale vuoto
     */
    public TestBidimensionalSpace() {
        this.programmablesInSpace = new ArrayList<Callable<Robot>>();
        this.shapesInSpace        = new ArrayList<Shape>();
    }

    /**
     * Costruisce uno spazio bidimensionale cui assegna oggetti programmabili e statici
     * @param programmables una lista contenente una serie di programmabili
     * @param shapes una lista contenente una serie di forme statiche.
     */
    public TestBidimensionalSpace(List<Callable<Robot>> programmables, List<Shape> shapes) {
        this.programmablesInSpace = programmables;
        this.shapesInSpace        = shapes;
    }

    /**
     * Ritorna una collezione di tutti gli oggetti attualmente presenti nello spazio
     * @return programmableOnSpace tutti gli oggetti programmabili nello spazio
     */

    public static List<Callable<Robot>> getProgrammableInSpace() {return programmablesInSpace;}

    /**
     * Ritorna una collezione di tutti gli oggetti statici attualmente presenti nello spazio
     * @return shapesOnSpace tutti gli oggetti statici nello spazio
     */

    public static List<Shape> getShapesInSpace() {
        return shapesInSpace;
    }

    /**
     * Aggiunge un oggetto programmabile all'ambiente
     * @param programmable un oggetto di tipo programmabile
     */
    public void addProgrammableToSpace(Robot programmable){
        this.programmablesInSpace.add(programmable);
    };

    /**
     * Aggiunge un oggetto foram all'ambiente
     * @param shape un oggetto di tipo forma.
     */
    public  void addShapesToSpace(Shape shape){
        this.shapesInSpace.add(shape);
    }

//    public List<Robot> getNeighbours(TwoDimensionalPoint positionToCheck, String labelToCheck, Double range){
//        return this.programmablesInSpace.parallelStream()
//                .filter(programmable->programmable.getLabel() == labelToCheck)
//                .filter(programmable-> {
//                    TwoDimensionalPoint center = (TwoDimensionalPoint) programmable.getPosition();
//                    return Utilities.checkPointInsideCircle(positionToCheck, center, range);}
//                ).collect(Collectors.toList());
//    }

    //boolean
    public List<Shape> getShapesWhereIamIn(TwoDimensionalPoint positionToCheck, String labelToCheck){
        return this.shapesInSpace.parallelStream()
                .filter(shape->shape.getLabel() == labelToCheck)
                .filter(shape->shape.isInternal(positionToCheck))
                .collect(Collectors.toList());
    }
}
