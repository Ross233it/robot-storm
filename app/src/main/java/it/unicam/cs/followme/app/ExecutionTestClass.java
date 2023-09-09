package it.unicam.cs.followme.app;


import it.unicam.cs.followme.model.language.RobotProgram;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.ShapeData;
import it.unicam.cs.followme.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


public class ExecutionTestClass /*extends Application */{
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/FollowMeApp.fxml")));
//        primaryStage.setTitle("Follow Me App");
//        primaryStage.setScene(new Scene(root, FollowMeAppController.WIDTH, FollowMeAppController.HEIGHT));
//        primaryStage.setResizable(false);
//        primaryStage.show();
//    }

    public static void main(String[] args) { //launch(args);



        //Controller newController = new Controller();
        //newController.runSimulation();
        //timer

//        SimulationTimerOLD simulationTimer = new SimulationTimerOLD(time, 1000,5);


        //program


        //shapes
//        List<ShapeData> environmentShapes = parser.parseEnvironment(environmentPath);
//        ListIterator<ShapeData> iterator = environmentShapes.listIterator();
//
//        while(iterator.hasNext()) {
//            ShapeData testShape = iterator.next();
//            System.out.println(testShape.label() + " " + testShape.shape() + " " + testShape.args()[0] );
//        }
        //environment creation
//        BidimensionalSpaceOld activeEnvironment = new BidimensionalSpaceOld(robotsMap,environmentShapes);


//esecuzione multithread ------------ ok --------------
//        List<Callable<Object>> allRobots = new ArrayList<>();
//
//        for(int t=0; t<1000; t++)        {
//            allRobots.add(new Robot(150.0+(t*100), t, program));
//            //TODO Remove print
//            System.out.println("io sono robot " + t);
//        }
//
//        ExecutorService executor = Executors.newCachedThreadPool();
//        try {
//            executor.invokeAll(allRobots);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        executor.shutdown();
//-------------------------------------------------------------------

        //stampa comandi
     //   RobotProgram myProgram = newController.getProgram();
       // myProgram.programOutput().stream().forEach(ShapeBuilder->System.out.println(ShapeBuilder.getInstruction()));
//
    }
}






