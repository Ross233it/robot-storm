package it.unicam.cs.followme.model.common;

import it.unicam.cs.followme.model.hardware.ProgrammableObject;
import it.unicam.cs.followme.model.hardware.Robot;
import java.util.Arrays;
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
        Random random = new Random();
        Double randomNumber = random.nextDouble(maxValue-minValue+1)+minValue;
        return randomNumber;
    }

    /**
     * Prende due valori in virgola mobile e restituisce un numero randomico
     * compreso tra il limite superiore e il limite inferiore
     * @param minValue limite inferiore
     * @param maxValue limite superiore
     * @return randomNumber un numero in virgola mobile compreso tra -1 e +1
     */
    public static Double randomScaledNumber (Double minValue, Double maxValue){
        Double randomNumber = randomDoubleNumber(minValue, maxValue);
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
     * @param x
     * @param y
     * @return
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
}
