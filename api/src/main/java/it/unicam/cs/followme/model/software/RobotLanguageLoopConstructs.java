package it.unicam.cs.followme.model.software;

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
     * @param repetitionCount integer
     */
    public Integer repeat(int repetitionCount, Integer currentCommandIndex){
        this.repetitionCount =  repetitionCount<0 ? 0 : repetitionCount;
        this.toRepeatCommandIndex  = currentCommandIndex + 1;
        return toRepeatCommandIndex;
    }

    public Integer until(Integer currentCommandIndex, Object label, List<Shape>shapes, TwoDimensionalPoint positionToCheck){
        this.untilCommandIndex = currentCommandIndex;
        return currentCommandIndex+1;
    }

    public void skipUntil(){ this.untilCommandIndex = null;}

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
}
