package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.utilities.FollowMeParserHandler;

import java.util.ArrayList;

/**
 * Questa classe ha la responsabilità di costruire il programma dei robot
 * rappresentato da una Array lista di {@Link ProgamCommand}
 * che rappresenta la lista di istruzioni che il destinatario deve eseguire.
 */
public class RobotProgram implements FollowMeParserHandler{

    public static ArrayList<ProgramCommand> commandList;

    @Override
    public void parsingStarted(){
        this.commandList = new ArrayList<ProgramCommand>();
    }

    @Override
    public void parsingDone() {
        this.programOutput();
    }

    @Override
    public void moveCommand(double[] args) {
        this.commandList.add(new ProgramCommand("MOVE", args));
    }

    @Override
    public void moveRandomCommand(double[] args) {this.commandList.add(new ProgramCommand("MOVE RANDOM", args)); }

    @Override
    public void signalCommand(String label) {
        this.commandList.add(new ProgramCommand("SIGNAL", label));
    }

    @Override
    public void unsignalCommand(String label) {
        this.commandList.add(new ProgramCommand("UNSIGNAL", label));
    }

    @Override
    public void followCommand(String label, double[] args) {
        this.commandList.add(new ProgramCommand("FOLLOW", args));
    }

    @Override
    public void stopCommand() {
        this.commandList.add(new ProgramCommand("STOP"));
    }

    @Override
    public void continueCommand(int s) {
        this.commandList.add(new ProgramCommand("CONTINUE", s));
        double[] args = {};
        while(s>0){
            this.commandList.add(new ProgramCommand("MOVE",null));
            s--;}
    }

    @Override
    public void repeatCommandStart(int n) {
        this.commandList.add(new ProgramCommand("REPEAT", n));
    }

    @Override
    public void untilCommandStart(String label) {
        this.commandList.add(new ProgramCommand("UNTIL", label));
    }

    @Override
    public void doForeverStart() {
        this.commandList.add(new ProgramCommand("DO FOREVER"));
    }

    @Override
    public void doneCommand() {
        this.commandList.add(new ProgramCommand("DONE"));
    }

    /**
     * Fornisce una collezione in sequenza di tutti i comandi nella forma corretta.
     * @return commandList programma eseguibile
     */
    public ArrayList<ProgramCommand> programOutput(){
        return this.commandList;
    }


}
