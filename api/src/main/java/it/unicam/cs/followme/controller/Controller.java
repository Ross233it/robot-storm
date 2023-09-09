package it.unicam.cs.followme.controller;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.StaticCircle;
import it.unicam.cs.followme.model.environment.StaticRectangle;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.language.RobotProgram;
import it.unicam.cs.followme.model.programmables.ProgrammableObject;
import it.unicam.cs.followme.model.programmables.Robot;
import it.unicam.cs.followme.model.programmables.RobotActivities;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.ShapeData;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import it.unicam.cs.followme.model.timeManagment.SimulationTimer;
import it.unicam.cs.followme.model.environment.Environment;
public class Controller<S extends Shape, P extends ProgrammableObject> {

    private final Path programPath;

    private final Path environmentPath;

    private final RobotProgram program;

    private final FollowMeParser parser ;

    private BidimensionalSpace environment;

    private Supplier<Environment<S, P>> environmentBuilder;


    /**
     * genera un controller con programma e ambiente di default     *
     */
    public Controller(Path programPath, Path environmentPath){
        this.programPath     = programPath;
        this.environmentPath = environmentPath;
        this.program         = new RobotProgram();
        this.parser          = new FollowMeParser(program);

        RobotProgram currentProgram = generateRobotProgram(programPath);

        List<Robot> currentRobots = generateRobotsRandomly(1,  currentProgram, 40.0);

        List<Shape> currentShapes = shapes(environmentPath);

        this.environment = new BidimensionalSpace(currentRobots, currentShapes);

//        TwoDimensionalPoint testPoint = new TwoDimensionalPoint(15.0);
//        List<Robot> testList = environment.getNeighbours(testPoint, "Init",25.0);
//        testList.stream().forEach(e->System.out.println(e.getId()));

        SimulationTimer timer = new SimulationTimer(1000);
            timer.start();
    }



    public void runSimulation(Integer robotNumber, Integer timeUnit){
//        RobotProgram currentProgram = generateRobotProgram(this.programPath);
//
//        List<Callable<Robot>> currentRobots = generateRobotsRandomly(robotNumber,  currentProgram, 100.0);
//
//        List<Shape> currentShapes = shapes(this.environmentPath);
//
//        this.environment = new BidimensionalSpace(currentRobots, currentShapes);
//
//        lunchRobots(currentRobots);
//        //TODO Implementare la possibilità di cambiare il tempo
//        SimulationTimerOLD timer = new SimulationTimerOLD(timeUnit);
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
    public List<Robot> generateRobotsRandomly(Integer objectNumber,
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
            if(currentShape.shape().equals("CIRCLE")){
                //todo remove print
                System.out.println("HO CREATO UN CERCHIO NELLA LISTA");
                shapeList.add(new StaticCircle(position, currentShape.label(), currentShape.args()[2]));
            }else   if(currentShape.shape().equals("RECTANGLE")) {
                //todo remove print
                System.out.println("HO CREATO UN RETTANGOLO NELLA LISTA");
                shapeList.add(new StaticRectangle(position, currentShape.label(), currentShape.args()[2], currentShape.args()[3]));
            }
            //todo remove print
            System.out.println("metodo shapes");
            System.out.println(currentShape.label() + " " + currentShape.shape() + " " + currentShape.args()[0] );
        }
    return  shapeList;
    }

    public RobotProgram getProgram(){return this.program;}

    public List<Robot>getRobots(){ return this.environment.getProgrammableInSpace();}


    public List<Shape>getShapes(){ return this.environment.getShapesInSpace();}

}
