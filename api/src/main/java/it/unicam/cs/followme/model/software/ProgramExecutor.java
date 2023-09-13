package it.unicam.cs.followme.model.software;

/**
 * Astrae il concetto di interprete dei comandi per un oggetto di tipo programmabile.
 * L'executor lancia l'esecuzioni delle istruzioni ricevute dal programma
 */
public interface ProgramExecutor {
    /**
     * Il metodo che si occupa di avviare le istruzioni
     */
    void executeProgram();
}
