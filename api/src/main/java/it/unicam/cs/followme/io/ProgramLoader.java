package it.unicam.cs.followme.io;

import it.unicam.cs.followme.model.software.ProgramCommand;
import it.unicam.cs.followme.utilities.FollowMeParserHandler;

import java.util.ArrayList;

/**
 * Questa classe ha la responsabilità di costruire il programma dei robot inteso come
 * sequenza di istruzioni nel formato eseguibile per il destinatario.
 */
public class ProgramLoader implements FollowMeParserHandler{

    public ArrayList<ProgramCommand> commandList = new ArrayList<ProgramCommand>();

    @Override
    public void parsingStarted(){ commandList.clear();}

    @Override
    public void parsingDone() {this.programOutput();}

    @Override
    public void moveCommand(double[] args) {
        this.commandList.add(new ProgramCommand<>("MOVE", args));
    }

    @Override
    public void moveRandomCommand(double[] args) {
        this.commandList.add(new ProgramCommand<>("MOVE RANDOM", args)); }

    @Override
    public void signalCommand(String label) {
        this.commandList.add(new ProgramCommand<>("SIGNAL", label));
    }

    @Override
    public void unsignalCommand(String label) {
        this.commandList.add(new ProgramCommand<>("UNSIGNAL", label));
    }

    @Override
    public void followCommand(String label, double[] args) {
        this.commandList.add(new ProgramCommand<>("FOLLOW", label, args));}

    @Override
    public void stopCommand() {
        this.commandList.add(new ProgramCommand<>("STOP"));
    }

    @Override
    public void continueCommand(int s) {
        double[] arg = {};
        for(int i=0; i<s; i++)
            this.commandList.add(new ProgramCommand<>("MOVE",arg));
    }

    @Override
    public void repeatCommandStart(int n) {
        this.commandList.add(new ProgramCommand<>("REPEAT", n));
    }

    @Override
    public void untilCommandStart(String label) {
        this.commandList.add(new ProgramCommand<>("UNTIL", label));
    }

    @Override
    public void doForeverStart() {
        this.commandList.add(new ProgramCommand<>("DO FOREVER"));
    }

    @Override
    public void doneCommand() {
        this.commandList.add(new ProgramCommand<>("DONE"));
    }

    /**
     * Fornisce una collezione in sequenza di tutti i comandi nella forma corretta.
     * @return commandList programma eseguibile
     */
    public ArrayList<ProgramCommand> programOutput(){
        return this.commandList;
    }

    /**
     * Aggiunge uno specifico comando al programma
     * @param command un {@link ProgramCommand} da aggiungere
     */
    public void addCommand(ProgramCommand command){this.commandList.add(command);}
}