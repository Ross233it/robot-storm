package it.unicam.cs.followme.controller;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.TestBidimensionalSpace;
import it.unicam.cs.followme.model.environment.Circle;
import it.unicam.cs.followme.model.environment.Rectangle;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.language.RobotProgram;
import it.unicam.cs.followme.model.programmables.Robot;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.ShapeData;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private Path programPath;
    private Path environmentPath;
    private final RobotProgram program  = new RobotProgram();
    private final FollowMeParser parser = new FollowMeParser(program);
    private TestBidimensionalSpace environment;

    public Controller (Path programPath, Path environmentPath){
        this();
        this.programPath = programPath;
        this.environmentPath = environmentPath;
    }

    /**
     * genera un controller con programma e ambiente di default     *
     */
    public Controller(){
       this.programPath      = Paths.get("src/main/resources/assets/default_program.txt");
       this.environmentPath  = Paths.get("src/main/resources/assets/default_environment.txt");

        RobotProgram currentProgram = generateRobotProgram(programPath);

        List<Callable<Robot>> currentRobots = generateRobotsRandomly(5,  currentProgram, 5.0);

        List<Shape> currentShapes = shapes(environmentPath);

        this.environment = new TestBidimensionalSpace(currentRobots, currentShapes);
    }


    public void runSimulation(Integer robotNumber, Integer timeUnit){
//        RobotProgram currentProgram = generateRobotProgram(this.programPath);
//
//        List<Callable<Robot>> currentRobots = generateRobotsRandomly(robotNumber,  currentProgram, 100.0);
//
//        List<Shape> currentShapes = shapes(this.environmentPath);
//
//        this.environment = new TestBidimensionalSpace(currentRobots, currentShapes);
//
//        lunchRobots(currentRobots);
//        //TODO Implementare la possibilità di cambiare il tempo
//        SimulationTimer timer = new SimulationTimer(timeUnit);
//        timer.start();
    }

    /**
     * Crea una serie di robot in posizione randomica compresa in un determinato range
     * ciascuno dei quali con un proprio programma eseguibile.
     * @param objectNumber numero dei robots creati
     * @param program programma assegnato a ciascun robot
     * @param range limite di posizionamento dei robot nello spazio.
     * @return allRobots lista dei robots creati
     */
    public List<Callable<Robot>> generateRobotsRandomly(Integer objectNumber,
                                                         RobotProgram program,
                                                         Double range){
            List<Callable<Robot>> allRobots = new ArrayList<>();
            for(int t=0; t<objectNumber; t++){allRobots.add(new Robot(range, t, program));}
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
     * Ricevuta una lista di Robot che estendono Callable, avvia un ExecutorService
     * e lancia l'esecuzione parallela dei rispettivi programmi su tutti i robot della lista.
     * @param allRobots
     */
    private void lunchRobots(List<Callable<Robot>> allRobots){
            ExecutorService executor = Executors.newCachedThreadPool();
            try {
                executor.invokeAll(allRobots);
            } catch (InterruptedException e) {
                 throw new RuntimeException(e);
            }
            executor.shutdown();
    }

    /**
     * da sistemare
     * @param environmentPath
     */
    private List<Shape> shapes(Path environmentPath){
    //FollowMeParser parser = new FollowMeParser(program);
        List<ShapeData> environmentShapes = null;
        try {
            environmentShapes = parser.parseEnvironment(environmentPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FollowMeParserException e) {
            throw new RuntimeException(e);
        }
        List<Shape> shapeList = new ArrayList<Shape>();
        ListIterator<ShapeData> iterator = environmentShapes.listIterator();
        while(iterator.hasNext()) {
            ShapeData currentShape = iterator.next();
            TwoDimensionalPoint position = new TwoDimensionalPoint(currentShape.args()[0], currentShape.args()[1]);
            if(currentShape.shape() == "CIRCLE"){
                shapeList.add(new Circle(position, currentShape.label(), currentShape.args()[2]));
            }else   if(currentShape.shape() == "RECTANGLE") {
                shapeList.add(new Rectangle(position, currentShape.label(), currentShape.args()[2], currentShape.args()[3]));
            }
            System.out.println(currentShape.label() + " " + currentShape.shape() + " " + currentShape.args()[0] );
        }
    return  shapeList;
    }

    public RobotProgram getProgram(){return this.program;}

    public List<Callable<Robot>>getCallableRobots(){ return this.environment.getProgrammableInSpace();}

    public List<Robot> getRobots(){
        List<Robot> robotList = this.environment.getProgrammableInSpace().stream()
                .map(callable -> {
                    try {
                        return callable.call();
                    } catch (Exception e) {return null;}
                })
                .filter(robot -> robot != null) // Rimuove eventuali risultati nulli
                .collect(Collectors.toList());
        return robotList;
    }

    public List<Shape>getShapes(){ return this.environment.getShapesInSpace();}

}
