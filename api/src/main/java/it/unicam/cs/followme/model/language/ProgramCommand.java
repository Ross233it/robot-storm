package it.unicam.cs.followme.model.language;

/**
 * Questa classe rappresenta la singola istruzione di un programma.
 * Ha la responsabilità di fornire la dichiarazione del comando da eseguire
 * e gli argomenti necessari.
 */
public class ProgramCommand<T>{
    private final String instruction;
    private final T parameters;

    /**
     * Genera un nuovo comando eseguibile con parametri.
     * @param instruction rappresenta il nome del comando da eseguire
     * @param parameters le informazioni complementari necessario per eseguire il comando
     */
    public ProgramCommand(String instruction, T parameters){
        this.instruction = instruction;
        this.parameters = parameters;
    }

    /**
     * Genera un nuovo comando eseguibile che non necessita di parametri.
     * @param instruction rappresenta il nome del comando da eseguire
     */
    public ProgramCommand(String instruction){
        this.instruction = instruction;
        this.parameters = null;
    }

    /**
     * Metodo getter per il parametro instruction.
     * @return instruction dichiarazione del comando da eseguire
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * Metodo getter per il parametro parameters.
     * @return parameters parametri per l'esecuzione del comando
     */
    public T getParameters() {
        return parameters;
    }

}
