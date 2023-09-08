package it.unicam.cs.followme.model.common;

import java.util.Random;

public class Utilities {
    /**
     * Utility method per effettuare il cast esplicito di un oggetto ad array di double
     * @param obj l'oggetto di classe Object da convertire
     * @return Double[] obj l'oggetto convertito.
     */
    public static double[] fromObjectToDoubleArray(Object obj){
        if(obj instanceof double[])
            return  (double[]) obj;
        return null;
    }

    /**
     * Utility method per effettuare il cast esplicito di un oggetto ad Integer
     * @param obj l'oggetto di classe Object da convertire
     * @return Integer obj l'oggetto convertito.
     */
    public static Integer fromObjectToInteger(Object obj){
        if(obj instanceof Integer)
            return  (Integer) obj;
        return 0;
    }

    /**
     * Utility method per effettuare il cast esplicito di un oggetto in Stringa
     * @param obj l'oggetto di classe Object da convertire
     * @return String obj l'oggetto convertito in stringa.
     */
    public static String fromObjectToString(Object obj){
        if(obj instanceof String)
            return  (String) obj;
        return null;
    }

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
     * compreso tra il limite superiore e il limite inferiore
     * @param minValue limite inferiore
     * @param maxValue limite superiore
     * @return randomNumber un numero in virgola mobile compreso tra -1 e +1
     */
    public static Double randomScaledNumber (Double minValue, Double maxValue){
        Random random = new Random();
        Double randomNumber = random.nextDouble(maxValue-minValue+1)+minValue;
        return ((randomNumber-minValue)/(maxValue-minValue))*2-1;
    }
}
