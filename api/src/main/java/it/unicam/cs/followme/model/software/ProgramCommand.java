package it.unicam.cs.followme.model.software;

/**
 * Questa classe rappresenta la singola istruzione di un programma.
 * Ha la responsabilità di fornire la dichiarazione del comando da eseguire
 * e gli argomenti necessari.
 */
public class ProgramCommand<T>{
    private final String instruction;
    private T parameter;
    private T []multipleParameters;

    /**
     * Genera un nuovo comando eseguibile che non necessita di parametri.
     * @param instruction rappresenta il nome del comando da eseguire
     */
    public ProgramCommand(String instruction){
        this.instruction = instruction;
    }

    /**
     * Genera un nuovo comando eseguibile con parametri di un solo tipo.
     * @param instruction rappresenta il nome del comando da eseguire
     * @param parameters il parametro necessario per eseguire il comando
     */
    public ProgramCommand(String instruction, T parameters){
        this(instruction);
        this.parameter = parameters;
    }

    /**
     * Genera un nuovo comando eseguibile con parametri di un solo tipo.
     * @param instruction rappresenta il nome del comando da eseguire
     * @param multipleParameters parametri multipli necessari per eseguire il comando
     */
    public ProgramCommand(String instruction, T... multipleParameters){
        this(instruction);
        this.multipleParameters = multipleParameters;
    }

    /**
     * Metodo getter per il parametro instruction.
     * @return instruction dichiarazione del comando da eseguire
     */
    public String getInstruction() {
        return instruction;
    }

    /**
     * Metodo getter per il parametro abbinato al comando.
     * @return parameter parametro per l'esecuzione del comando
     */
    public T getParameter() {return parameter;}

    /**
     * Metodo getter per i parametri multipli abbinati al comando.
     * @return multipleParameters parametri per l'esecuzione del comando
     */
    public T[] getMultipleParameters(){return multipleParameters;}
}
