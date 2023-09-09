package it.unicam.cs.followme.controller;

import it.unicam.cs.followme.io.ShapeBuilder;
import it.unicam.cs.followme.io.ShapeLoader;
import it.unicam.cs.followme.io.ShapesCreator;
import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.language.RobotProgram;
import it.unicam.cs.followme.model.programmables.ProgrammableObject;
import it.unicam.cs.followme.model.programmables.Robot;
import it.unicam.cs.followme.model.programmables.RobotActivities;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import it.unicam.cs.followme.model.timeManagment.SimulationTimer;

public class Controller<S extends Shape, P extends ProgrammableObject> {

    private  RobotProgram program;
    private final FollowMeParser parser ;
    private BidimensionalSpace environment;

    /**
     * Genera un controller
     */
    public Controller(){
        this.program         = new RobotProgram();
        this.parser          = new FollowMeParser(program);
    }

    /**
     * Inizializza gli elementi oggetto della simulazione
     * @param robotNumber il numero dei robot da generare entro un raggio +/- range
     */
    public void simulationSetup(Integer robotNumber,  Double range, Path programPath, Path environmentPath){

        List<Robot> currentRobots = generateRobotsRandomly(robotNumber,  program, range);
        List<Shape> loadedShapes = shapesSetup(environmentPath);
        this.program         = generateRobotProgram(programPath);
        this.environment = new BidimensionalSpace(currentRobots, loadedShapes);
    }

    /**
     * Avvia il flusso di esecuzione della simulaizone.
     * @param timeUnit la velocità in millisecondi della simulazione
     * @param timeUnit
     */
    public void runSimulation(Integer timeUnit){
        SimulationTimer timer = new SimulationTimer(timeUnit);
        timer.start();
        launchRobots();
    }

    /**
     * Crea una serie di robot in posizione randomica compresa in un determinato range
     * ciascuno dei quali con un proprio programma eseguibile.
     * @param objectNumber numero dei robots creati
     * @param program programma assegnato a ciascun robot
     * @param range limite di posizionamento dei robot nello spazio.
     * @return allRobots lista dei robots creati
     */
    private List<Robot> generateRobotsRandomly(Integer objectNumber,
                                              RobotProgram program,
                                              Double range){
        List<Robot> allRobots = new ArrayList<>();
        for(int t=0; t<objectNumber; t++){allRobots.add(new Robot(range, t));}
        return allRobots;
    }

    /**
     * Recupera il programma da un file di testo in posizione programPath e
     * restituisce un programma sintatticamente corretto e valido per l'esecuzione da
     * parte dei robot;
     * @param programPath il percorso del file di programma
     * @return program programma eseguibile dai robot
     */
    private RobotProgram generateRobotProgram(Path programPath) {
            try {
                this.parser.parseRobotProgram(programPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (FollowMeParserException e) {
                throw new RuntimeException(e);
            }
        return this.program;
    }

    /**
     * Ricevuta una lista di Robot raccoglie i loro programExecutor, avvia un ExecutorService
     * e lancia l'esecuzione parallela dei rispettivi programmi su tutti i robot della lista.
     */
    public void launchRobots(){
            List<Robot> allRobots = this.environment.getProgrammableInSpace();

            List<Callable<RobotActivities>> robotExecutors = allRobots.stream()
                    .map(robot-> new RobotActivities(robot, program))
                    .collect(Collectors.toList());

            ExecutorService executor = Executors.newCachedThreadPool();
            try {
                executor.invokeAll(robotExecutors);
            } catch (InterruptedException e) {
                 throw new RuntimeException(e);
            }
            executor.shutdown();
    }

    /**
     * Genera le forme geometriche statiche per l'inserimento nell'environment
     * @return
     */
    private List<Shape> shapesSetup(Path environmentPath){
        ShapeBuilder shapeCreator = new ShapesCreator();
        ShapeLoader shapeLoader = new ShapeLoader(parser, environmentPath, shapeCreator);
        return shapeLoader.loadShapes();
    }

    public RobotProgram getProgram(){return this.program;}

    public List<Robot>getRobots(){ return this.environment.getProgrammableInSpace();}

    public List<Shape>getShapes(){ return this.environment.getShapesInSpace();}



}
