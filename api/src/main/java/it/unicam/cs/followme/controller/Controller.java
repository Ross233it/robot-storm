package it.unicam.cs.followme.controller;

import it.unicam.cs.followme.io.ShapeBuilder;
import it.unicam.cs.followme.io.ShapeLoader;
import it.unicam.cs.followme.io.ShapesCreator;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.io.ProgramLoader;
import it.unicam.cs.followme.model.hardware.ProgrammableObject;
import it.unicam.cs.followme.model.hardware.Robot;
import it.unicam.cs.followme.model.software.RobotProgramExecutor;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Controller<S extends Shape, P extends ProgrammableObject> {

    private ProgramLoader program;
    private final FollowMeParser parser;
    private BidimensionalSpace environment;
    private List<Callable<Robot>> executors;

    /**
     * Metodo costruttore Controller
     */
    public Controller() {
        this.program = new ProgramLoader();
        this.parser = new FollowMeParser(program);
    }

    /**
     * Inizializza tutti gli elementi necessari per la simulazione.
     * @param robotNumber numero dei robot da generare randomicamente.
     * @param range limite massimo di x e y per la generazione dei robot.
     * @param programPath percorso del file di programma.
     * @param environmentPath percorso del file di ambiente.
     */
    public void simulationSetup(Integer robotNumber, Double range, Path programPath, Path environmentPath) {
        List<Robot> currentRobots = generateRobotsRandomly(robotNumber, program, range);
        List<Shape> loadedShapes = shapesSetup(environmentPath);
        this.program = generateRobotProgram(programPath);
        this.environment = new BidimensionalSpace(currentRobots, loadedShapes);
        this.executors = launchRobots();
    }

    /**
     * Crea una serie di robot in posizione randomica compresa in un determinato range
     * ciascuno dei quali con un proprio programma eseguibile.
     * @param objectNumber numero dei robots creati
     * @param program      programma assegnato a ciascun robot
     * @param range        limite di posizionamento dei robot nello spazio.
     * @return allRobots lista dei robots creati
     */
    private List<Robot> generateRobotsRandomly(Integer objectNumber, ProgramLoader program, Double range) {
        List<Robot> allRobots = new ArrayList<>();
        for (int t = 0; t < objectNumber; t++) {
            allRobots.add(new Robot(range, t, program));}
        return allRobots;
    }

    /**
     * Recupera il programma da un file di testo in posizione programPath e
     * restituisce un programma sintatticamente corretto e valido per l'esecuzione da
     * parte dei robot;
     * @param programPath il percorso del file di programma.
     * @return program programma eseguibile dai robot.
     */
    public ProgramLoader generateRobotProgram(Path programPath) {
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
     * Raccoglie in una struttura datii ProgramExecutors di tutti i robot presenti nell'ambiente
     * @return robotExecutors lista di {@Link RobotProgramExecutor}
     */
    private List<Callable<Robot>> launchRobots() {
        List<Robot> allRobots = this.environment.getProgrammableInSpace();
        List<Callable<Robot>> robotExecutors = allRobots.stream()
                .map(robot -> robot.getRobotExcutor())
                .map(executor -> (Callable<Robot>) executor)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return robotExecutors;
    }

    /**
     * Avvia il {@link RobotProgramExecutor} per ciascun robot effettuando esecuzione
     * multithread.
     * @return true se la computazione è terminata correttamente.
     */
    public boolean runNextRobotCommand() {
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            List<Future<Robot>> futures = executor.invokeAll(executors);
            for (Future<Robot> future : futures) {
                Robot robot = future.get();
                Utilities.checkShape(robot, environment);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
            return true;
        }
    }

    /**
     * Genera le forme geometriche statiche per l'inserimento nell'environment
     * @return
     */
    private List<Shape> shapesSetup(Path environmentPath) {
        ShapeBuilder shapeCreator = new ShapesCreator();
        ShapeLoader shapeLoader = new ShapeLoader(parser, environmentPath, shapeCreator);
        return shapeLoader.loadShapes();
    }

    /**
     * Ritorna i robots instanziati sulla scena corrente
     * @return robots una lista dei robot presenti nell'ambiente
     */
    public List<Robot> getRobots() {
        return this.environment.getProgrammableInSpace();
    }

    /**
     * Ritorna i robots instanziati sulla scena corrente
     *
     * @return robots una lista dei robot presenti nell'ambiente
     */
    public List<Shape> getShapes() {
        return environment.getShapesInSpace();
    }

}
