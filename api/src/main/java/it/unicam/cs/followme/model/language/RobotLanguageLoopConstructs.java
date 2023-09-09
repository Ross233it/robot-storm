package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.environment.Shape;

import java.util.List;

/**
 * Questa classe ha la responsabilità di rappresentare e gestire i loop del linguaggio di programmazione
 * intesi come deroghe al flusso sequenziale di esequzione della lista di istruzioni che è il programma.
 * Di fatto si occupa di stabilire  l'indice del prossimo comando che
 * l'oggetto programmabile deve eseguire nel successivo step di esecuzione.
 */

public class RobotLanguageLoopConstructs {

    private Integer repetitionCount;
    private Integer toRepeatCommandIndex;
    private Integer foreverFirstCommandIndex;
    private Integer untilCommandIndex;
    /**
     * Il comando repeat rileva il numero di ripetizioni cui è soggetto un blocco di istruzioni
     * @param param integer
     */
    public Integer repeat(Object param, Integer currentCommandIndex){
        //todo remove print
        System.out.println("INIZIO REPEAT");
        this.repetitionCount = Utilities.fromObjectToInteger(param)-1;
            if(repetitionCount<0) repetitionCount  = 0;
        this.toRepeatCommandIndex  = currentCommandIndex + 1;
        return toRepeatCommandIndex;
    }


    public Integer until(Integer currentCommandIndex, Object label, List<Shape>shapes, TwoDimensionalPoint positionToCheck){
        //todo remove print
        //System.out.println("INIZIO UNTIL");
        this.untilCommandIndex = currentCommandIndex;
        //if(checkShape(label, shapes, positionToCheck))
            //todo remove print
            //System.out.println("true");
        //todo remove print
        //System.out.println(currentCommandIndex+1);
        return currentCommandIndex+1;
    }

    /**
     * Il comando doForever registra la prima posizione dei comandi che devono essere
     * ripetuti all'infinito
     */
    public Integer doForever(Integer currentCommandIndex){
        return this.foreverFirstCommandIndex = currentCommandIndex + 1;
    }

    /**
     * Il comando skipForever consente di uscire dal loop forever al verirficarsi di
     * specifiche condizioni (es. a seguito di un comando UNTIL)
     *
     */
    public void skipForever(){
        this.foreverFirstCommandIndex = null;
    }

    /**
     * Viene richiamato a chiusura di comandi doForever, repeat, until e
     * riposiziona l'esecuzione del programma a partire dalla corretta linea
     * in conformità a quanto richiesto dall'iterazione che lo precede.
     * @return toRepeatCommandIndex l'indice del primo comando da ripetere
     * @return forevereFirstCommandIndex l'indice del primo comando da ripetere per sempre
     */
    public Integer done(Integer currentCommandIndex) {
        if (repetitionCount != null  && toRepeatCommandIndex != null
            && repetitionCount > 0 && toRepeatCommandIndex > 0) {
            repetitionCount--;
            return toRepeatCommandIndex;
        }
        if ( foreverFirstCommandIndex != null && foreverFirstCommandIndex >= 0)
             return foreverFirstCommandIndex;

        if(untilCommandIndex != null && untilCommandIndex >=0)
             return untilCommandIndex;

        return currentCommandIndex+1;
    }


    private boolean checkShape(Object label,
                               List<Shape>shapes,
                               TwoDimensionalPoint positionToCheck){
        //todo remove print
        //System.out.println("CHEK SHAPE LANCIATO");
        String labelToCheck = Utilities.fromObjectToString(label);
        //System.out.println("IO SONO label to check" + labelToCheck + shapes.toArray().length);

        boolean test = shapes.stream()
                .filter(s->s.getLabel().equals(labelToCheck))
                .filter(s->s.isInternal(positionToCheck))
                .findFirst().isPresent();
        //todo remove print
        //System.out.println("IO SONO TEST" + test);
        return test;
    }

}
