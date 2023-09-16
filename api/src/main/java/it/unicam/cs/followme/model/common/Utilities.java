package it.unicam.cs.followme.model.common;

import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.Environment;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.hardware.ProgrammableObject;
import it.unicam.cs.followme.model.hardware.Robot;
import it.unicam.cs.followme.model.software.RobotLanguageAtomicConstructs;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

/**
 * La classe raccoglie una serie di metodi statici di utilità per
 * operazioni ricorrenti all'interno dell'applicazione
 */
public class Utilities {

    /**
     * Dato un punto del piano cartesiano verifica se è interno o esterno ad un cerchio di
     * raggio radius e di centro center.
     * @return TRUE se il punto è interno o sulla circonferenza
     * @return FALSE se il punto è esterno alla circonferenza
     */
    public static boolean checkPointInsideCircle(TwoDimensionalPoint pointToCheck, TwoDimensionalPoint center, double radius){
       return Math.sqrt(Math.pow(pointToCheck.getX() - center.getX(), 2)
              + Math.pow(pointToCheck.getY() - center.getY(), 2)) <=  radius;
    }

    /**
     * Prende due valori in virgola mobile e restituisce un numero randomico
     * @param minValue limite inferiore
     * @param maxValue limite superiore
     * @return randomNumber un numero in virgola mobile compreso tra -1 e +1
     */
    public static Double randomDoubleNumber (Double minValue, Double maxValue){
        if (minValue == null || maxValue == null) {
            throw new NullPointerException("minValue e maxValue non possono essere null");
        }
        Random random = new Random();
        double randomNumber = random.nextDouble()*(maxValue-minValue)+minValue;
        return randomNumber;
    }

    /**
     * Prende due valori in virgola mobile e restituisce un numero randomico
     * compreso tra il limite superiore e il limite inferiore
     * @param minValue limite inferiore
     * @param maxValue limite superiore
     * @return randomNumber un numero in virgola mobile compreso tra -1 e +1
     */
    public static double randomScaledNumber (Double minValue, Double maxValue){
        if (minValue == null || maxValue == null) {
            throw new NullPointerException("minValue e maxValue non possono essere null");
        }
        double randomNumber = randomDoubleNumber(minValue, maxValue);
        return ((randomNumber-minValue)/(maxValue-minValue))*2-1;
    }

    /**
     * Ordina due double e restituisce un array ordinato
     * @param first il primo double
     * @param second secondo double
     * @return due cifre disposte in ordine crescente.
     */
    public static double[] sortTwoDouble(double first, double second){
        double[] range = {first, second};
        Arrays.sort(range);
        return range;
    }

    /**
     * Calcola la diagonale rispetto a due lati di un rettangolo
     * @param x un lato dell'angolo retto
     * @param y il secondo lato dell'angolo retto
     * @return il valore della diagonale del rettangolo dato
     */
    public static double getDiagonal(double x, double y) {
        double diagonal = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return diagonal;
    }

    /**
     * Calcola la posizione lungo uno degli assi in relazione alla diagonale di spostamento,
     * considerando che due rettangoli i cui lati minore e maggiore sono reciprocamente in prpoporizone
     * hanno diagonali in proporzione.
     * @param objectDiagonal
     * @param directionDiagonal
     * @param directionSide
     * @return
     * @throws IllegalArgumentException se directionDiagonal == 0
     */
    public static double getSideFromDiagonal(Double objectDiagonal, Double directionDiagonal, Double directionSide ){
        if(directionDiagonal != 0)
            return directionSide * objectDiagonal/directionDiagonal;
        else
            throw new IllegalArgumentException("Il valore della diagonale non può essere zero");
    }

    /** Robot position utilities*/
    /**
     * Calcola la posizione media di y su una serie di robot
     * @param neighbours
     * @return
     */
    public static double averageY(Stream<Robot> neighbours){
        return  neighbours.mapToDouble(t->t.getPosition().getY()).average().orElse(0.0);
    }

    /**
     * Calcola la posizione media di x su una serie di robot
     * @param neighbours
     * @return
     */
    public static double averageX(Stream<Robot> neighbours){
        return  neighbours.mapToDouble(t->t.getPosition().getX()).average().orElse(0.0);
    }

    /**
     * Determina in base a un vettore di movimento lo spostamento diagonale di un programmabile
     * nella direzione di due coordinate x e y.
     * @param speedVector vettore di movimento {@link SpeedVector}
     * @param programmable oggetto programmabile {@link ProgrammableObject}
     * @return newPosition nuova posizione sul piano {@link TwoDimensionalPoint}
     */
    public static TwoDimensionalPoint calculateNewPosition(SpeedVector speedVector, ProgrammableObject programmable){
        Double vector = Utilities.getDiagonal(speedVector.getX(), speedVector.getY());
        Double newX = Utilities.getSideFromDiagonal(speedVector.getSpeed(), vector,  speedVector.getX());
        Double newY = Utilities.getSideFromDiagonal(speedVector.getSpeed(), vector,  speedVector.getY());
        return new TwoDimensionalPoint(newX+((TwoDimensionalPoint) programmable.getPosition()).getX(),
                newY+((TwoDimensionalPoint) programmable.getPosition()).getY());
    }

    /**
     * Verifica se un robot è all'interno di una delle forme dell'environment
     * @param robot il robot di cui valutare la posizione
     * @param environment l'ambiente corrente in cui si trova il robot.
     */
    public synchronized static void checkShape(Robot robot, Environment environment) {
        List<Shape> shapes = environment.getShapesInSpace();
        TwoDimensionalPoint positionToCheck = robot.getPosition();
        shapes.stream().filter(shape -> shape.isInternal(positionToCheck))
                .forEach(internalShape -> RobotLanguageAtomicConstructs.signal(internalShape.getLabel(), robot));
        shapes.stream().filter(shape -> !shape.isInternal(positionToCheck))
                .forEach(internalShape -> RobotLanguageAtomicConstructs.unsignal(internalShape.getLabel(), robot));
    }

    /**
     * Verifica se un robot si trova all'interno di una forma
     * @param robot il programmabile dd verificare
     * @param labelToCheck la label da verificare
     * @param environment l'ambiente della simulazione corrente
     * @return true se il robot si trova in una delle forme dell'ambiente.
     */
    public  static boolean checkShape(Robot robot, String labelToCheck, BidimensionalSpace environment) {
        List<Shape> shapes = environment.getShapesInSpace();
        TwoDimensionalPoint positionToCheck = robot.getPosition();
        Optional<Shape> firstShape = shapes.stream()
                .filter(shape -> shape.isInternal(positionToCheck)
                        && robot.getLabel().equals(labelToCheck))
                .findFirst();
        if (firstShape.isPresent()) return true;
        return false;
    }
}
