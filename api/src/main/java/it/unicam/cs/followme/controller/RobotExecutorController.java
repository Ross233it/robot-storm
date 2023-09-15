package it.unicam.cs.followme.controller;

import it.unicam.cs.followme.io.ProgramLoader;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.Environment;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.hardware.Robot;
import it.unicam.cs.followme.model.software.RobotLanguageAtomicConstructs;
import it.unicam.cs.followme.model.software.RobotProgramExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RobotExecutorController extends Controller {



    /**
     * Crea una serie di robot in posizione randomica compresa in un determinato range
     * ciascuno dei quali con un proprio programma eseguibile.
     * @param objectNumber numero dei robots creati
     * @param program programma assegnato a ciascun robot
     * @param range limite di posizionamento dei robot nello spazio.
     * @return allRobots lista dei robots creati
     */
    public static List<Robot> generateRobotsRandomly(Integer objectNumber,
                                               ProgramLoader program,
                                               Double range){
        List<Robot> allRobots = new ArrayList<>();
        for(int t=0; t<objectNumber; t++){allRobots.add(new Robot(range, t, program));}
        return allRobots;
    }


    /**
     * Identifica  e raccoglie i ProgramExecutors di tutti i robot presenti nell'ambiente
     * @return robotExecutors lista di {@Link RobotProgramExecutor}
     */
    public static List<Callable<Robot>> launchRobots(Environment environment) {
        List<Robot> allRobots = environment.getProgrammableInSpace();
        List<Callable<Robot>> robotExecutors = allRobots.stream()
                .map(robot -> robot.getRobotExcutor())
                .map(executor -> (Callable<Robot>) executor)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return robotExecutors;
    }

    /**
     * Avvia il {@link RobotProgramExecutor} per ciascun robot effettuando esecuzione
     * multithread
     * @return true se la computazione è terminata correttamente.
     */
    public static boolean runRobotCommand(List<Callable<Robot>> executors, Environment environment) {
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            List<Future<Robot>> futures =  executor.invokeAll(executors);
            for (Future<Robot> future : futures) {
                Robot robot = future.get();
                resetLabel(robot);
                checkShape(robot, environment);
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
     * Ritorna i robots instanziati sulla scena corrente
     * @return robots una lista dei robot presenti nell'ambiente
     */
    public List<Robot>getRobots(Environment environment){ return environment.getProgrammableInSpace();}

    /**
     * Ritorna i robots instanziati sulla scena corrente
     * @return robots una lista dei robot presenti nell'ambiente
     */
    public List<Shape>getShapes(Environment environment){ return environment.getShapesInSpace();}


    public synchronized static void checkShape(Robot robot, Environment environment) {
        List<Shape> shapes = environment.getShapesInSpace();
        TwoDimensionalPoint positionToCheck = robot.getPosition();
        shapes.stream().filter(shape -> shape.isInternal(positionToCheck))
                .forEach(internalShape -> RobotLanguageAtomicConstructs.signal(internalShape.getLabel(), robot));
    }

    private static void resetLabel(Robot robot){RobotLanguageAtomicConstructs.unsignal(robot);}


}
