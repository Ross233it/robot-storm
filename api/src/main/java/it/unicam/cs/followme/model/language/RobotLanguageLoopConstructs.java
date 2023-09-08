package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.programmables.Robot;

import java.util.Map;

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
    private Integer untilFirstCommandIndex;
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


    public Integer until(Integer currentCommandIndex){
        this.untilFirstCommandIndex = currentCommandIndex + 1;
        return untilFirstCommandIndex;
    }

    /**
     * Il comando doForever registra la prima posizione dei comandi che devono essere
     * ripetuti all'infinito
     */
    public Integer doForever(Integer currentCommandIndex){
        this.foreverFirstCommandIndex = currentCommandIndex + 1;
        return foreverFirstCommandIndex;
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
        //todo remove print
        System.out.println("COMANDO DONE");
        if (repetitionCount != null  && toRepeatCommandIndex != null
            && repetitionCount > 0 && toRepeatCommandIndex > 0) {
            repetitionCount--;
            return toRepeatCommandIndex;
        }
        if ( foreverFirstCommandIndex != null && foreverFirstCommandIndex >= 0)
             return foreverFirstCommandIndex;

//        if(untilFirstCommandIndex != null && untilFirstCommandIndex >= 0)
//            if(checkLabel(String label)) return currentCommandIndex+1;
//            else return untilFirstCommandIndex;

        return currentCommandIndex+1;
    }


}
