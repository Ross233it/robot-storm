package it.unicam.cs.followme.app;



import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.StaticCircle;
import it.unicam.cs.followme.model.environment.StaticRectangle;
import it.unicam.cs.followme.model.hardware.ProgrammableObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.scene.layout.StackPane;

import it.unicam.cs.followme.controller.Controller;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.hardware.Robot;


public class FollowMeAppController{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final Double SPACE_SQUARE = 700.0;
    public static final Double AXES_ZERO = SPACE_SQUARE / 2.0;


    @FXML
    private AnchorPane fieldArea;

    @FXML
    private Button openButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button playButton;

    @FXML
    private Button zoomInButton;

    @FXML
    private Button zoomOutButton;

    @FXML
    private Button stepForwardButton;

    @FXML
    private Button stepBackwardButton;

    @FXML
    private Group cartesian;

    @FXML
    private Group timeLabels;


    private int firstColumn = 0;
    private CartesianAxisManager axes;
    private Controller controller;
    private List<Robot>currentRobots;
    private List<Shape>currentShapes;
    private Path programPath      = Path.of("C:\\JavaProjects\\followme-main\\app\\src\\main\\resources\\assets\\defaultProgram.txt");
    private Path environmentPath  = Path.of("C:\\JavaProjects\\followme-main\\app\\src\\main\\resources\\assets\\defaultEnvironment.txt");
    private int robotNumber;
    private int timeUnit;
    private int currentTime;
    private int simDuration;
    Map<Robot, Label> symbolMap = new HashMap<>();

    @FXML
    public void initialize() {
        this.axes = new CartesianAxisManager(40, cartesian);
        Supplier<Controller> instance = Controller::new;
        this.controller = instance.get();
    }

    @FXML
    public void onStepForwardCommand(Event event){
        simStepExecution();
    }

    @FXML
    public void onStepBackwardCommand(Event event){
        simStepBackExecution();
    }

    @FXML
    public void onPlayButton(Event event){
        simTimeExecution();
    }

    @FXML
    public void onOpenFile(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FollowMeSetup.fxml"));
        Parent parent = loader.load();
        FollowMeSetupController setupController = loader.getController();
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Scene scene = new Scene(parent, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("CREATE SIMULATION");
        stage.setResizable(false);
        stage.show();
    }


    /**
     * This is the method invoked when the cells are zoomed in.
     *
     * @param event the triggering event.
     */
    @FXML
    private void onZoomInCommand(Event event) {  zoomIn(); }

    /**
     * This is the method invoked when the cells are zoomed out.
     *
     * @param event the triggering event.
     */
    @FXML
    private void onZoomOutCommand(Event event) { zoomOut();}

    /**
     * This is the method invoked when the cells are cleared.
     */
    @FXML
    private void onClearCommand() {
        currentTime = 0;
        cartesian.getChildren().clear();
        currentRobots.stream().forEach(
                robot -> robot.setPosition(robot.getMemory().getState(0).position()));
        rebuildScene(axes.getScale());
    }

    /**
     * Registra i dati di configurazione della simulazione nell'interfaccia utente.
     * @param programFile il file contenente il programma da eseguire
     * @param environmentFile il file contenente le impostazioni delle forme geometriche
     * @param robotNumber numero dei robot che si desidera rappresentare     *
     * @param timeUnit unità di tempo in millisecondi.
     */
    public void simSetup(File programFile, File environmentFile, int robotNumber, int timeUnit, int simDuration){
        this.robotNumber = robotNumber;
        this.timeUnit = timeUnit;
        this.environmentPath = environmentFile.toPath();
        this.programPath = programFile.toPath();
        controller.simulationSetup(robotNumber,30.0,  programPath, environmentPath);
        this.currentRobots = controller.getRobots();
        this.currentShapes = controller.getShapes();
        this.simDuration = simDuration;
        this.robotNumber = robotNumber;
        robotInitialize(cartesian);
        displayShapes();
    }

    private void simStepExecution(){
        if(currentTime < simDuration){
            controller.runNextRobotCommand();
            rebuildScene(axes.getScale());
        }

    }

    private void simStepBackExecution(){
//        if(currentTime>=0){
//            currentTime--;
//        currentRobots.stream()
//                .filter(robot -> robot.getMemory().getState(currentTime).position() != null)
//                .forEach(robot -> robot.setPosition(robot.getMemory().getState(currentTime).position()));
//        rebuildScene(axes.getScale());}
    }

//    public void simTimeExecution(){
//        SimulationTimer timer = new SimulationTimer(timeUnit, simDuration);
//        timer.start();
//        while(currentTime < simDuration){
//            timer.updateTime();
//            try {
//                timer.sleep(timeUnit);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (currentTime <= timer.getTime()) {
//                System.out.println("TIMER "+ timer.getTime());
//                System.out.println("CURRENT TIME "+ currentTime);
//                if(controller.runNextRobotCommand()){
//                    rebuildScene(axes.getScale());
//                    currentTime++;
//                }
//            }
//        }
//    }

    public void simTimeExecution() {
        javafx.util.Duration duration =  javafx.util.Duration.millis(timeUnit);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            if (currentTime < simDuration) {
                if (controller.runNextRobotCommand()) {
                    rebuildScene(axes.getScale());
                   }
            }
        }));
        timeline.setCycleCount(simDuration);
        timeline.play();
    }

    /**
     * Aggiorna la visualizzazione degli elementi nell'interfaccia grafica
     * @param axisScale scala dimensionale dello spazio
     */
    public void rebuildScene(Integer axisScale){
        axes.axisSetup(axisScale, cartesian);
        robotInitialize(this.cartesian);
        displayShapes();
        currentTime++;
        printTime(currentTime);
    }

    public void printTime(Integer time){
        timeLabels.getChildren().clear();
        String toStamp = time < simDuration ? time.toString() : "Simulazione Terminata";
        Label timeLabel = new Label(toStamp);
        timeLabels.getChildren().add(timeLabel);
    }

    /**
     * This method is used to scroll down the observed cells.
     */
//    private void scrollRight(){
//        double dist= this.axes.getxAxis().getUpperBound()- this.axes.getxAxis().getLowerBound();
//        changeBounds(0.10*dist,0,0.10*dist,0);
////        entropyX+=(0.10*dist);
////        initAll();
//    }
//
//    private void scrollUp(){
//        double dist= this.axes.getyAxis().getUpperBound()-this.axes.getyAxis().getLowerBound();
//        changeBounds(0,0.10*dist,0,0.10*dist);
////        this.entropyY+=(0.10*dist);
////        initAll();
//    }
//
//    private void scrollLeft(){
//        double dist= this.axes.getxAxis().getUpperBound()-this.axes.getxAxis().getLowerBound();
//        changeBounds(-0.10*dist,0,-0.10*dist,0);
////        entropyX+=(-0.10*dist);
////        initAll();
//    }
//
//    private void scrollDown(){
//        double dist= this.axes.getyAxis().getUpperBound()-this.axes.getyAxis().getLowerBound();
//        changeBounds(0,-0.10*dist,0,-0.10*dist);
//    }


    /**
     * Avvicina il punto di vista di osservazione.
     * @param scala la scala attuale di visualizzazione.
     */
    private void zoomIn() {rebuildScene(axes.getScale()-10);}

    /**
     * Allontana il punto di vista di osservazione.
     * @param scala la scala attuale di visualizzazione.
     */
    private void zoomOut() {rebuildScene(axes.getScale()+10);}

    /**
     * Inizializza le forme puntiformi dei robot sull'interfaccia grafica
     * @param cartesian un Group destinatario.
     */
    private void robotInitialize(Group cartesian) {
        robotSetup();
        Platform.runLater(() -> {
            this.symbolMap.keySet().stream().forEach(e->{
                if(e.getPosition().getX() < axes.getScale() && e.getPosition().getY()<axes.getScale()){
                    this.cartesian.getChildren().add(symbolMap.get(e));
                }
            });
        });
    }

    private void robotSetup(){
        this.cartesian.getChildren().removeAll(this.symbolMap.keySet());
        symbolMap = this.currentRobots.parallelStream()
                .filter(e-> e.getPosition().getX()< axes.getScale() && e.getPosition().getY()<axes.getScale())
                .collect(Collectors.toMap(
                        e -> e,
                        e -> {Label label = new Label( "Id"+e.getId() +" "+ e.getLabel(), new Circle(2,Color.RED));
                            label.setLayoutX(AXES_ZERO + ((e.getPosition().getX()) * axes.getTickSize()));
                            label.setLayoutY(AXES_ZERO - ((e.getPosition().getY()) * axes.getTickSize()));
                            return label;}
                ));
    }

    private void displayShapes(){
        this.currentShapes.stream().forEach(e->{
            if(e instanceof StaticCircle)    this.addCircle((StaticCircle) e, this.cartesian);
            if(e instanceof StaticRectangle) this.addRectangle((StaticRectangle<TwoDimensionalPoint>) e, this.cartesian);
        });
    }


    /**
     * Genera una figura circolare nell'interfaccia grafica
     * @param staticCircle un oggetto di tipo {@link StaticCircle}
     * @param cartesian un gruppo target.
     */
    public void addCircle(StaticCircle staticCircle, Group cartesian){
        Double x =   getSceneX(staticCircle, axes);
        Double y =   getSceneY(staticCircle, axes);
        Circle sceneCircle = new Circle(x, y,
                scaleToScene(staticCircle.getRadius(), axes),
                Color.GREEN);
        sceneCircle.setOpacity(0.5);
        cartesian.getChildren().add(sceneCircle);
    }

    /**
     * Genera una figura rettangolare nell'interfaccia grafica
     * @param staticRectangle un oggetto di tipo {@link StaticRectangle}
     * @param cartesian un gruppo target.
     */
    public void addRectangle(StaticRectangle<TwoDimensionalPoint> staticRectangle, Group cartesian){
         Rectangle sceneRectangle = new Rectangle(
                (getSceneX(staticRectangle, axes) - (scaleToScene(staticRectangle.getWidth(), axes)/2)),
                (getSceneY(staticRectangle, axes) + (scaleToScene(staticRectangle.getHeight(),axes)/2)),
                scaleToScene(staticRectangle.getWidth(), axes),
                scaleToScene(staticRectangle.getHeight(), axes)
        );
        sceneRectangle.setOpacity(0.5);
        sceneRectangle.setFill(Color.BLUE);
        cartesian.getChildren().add(sceneRectangle);
    }

    /**
     * Converte una generica X nella scala dell'interfaccia grafica
     * @param shape
     * @param axes
     * @return
     */
    private static Double getSceneX(Shape<TwoDimensionalPoint> shape, CartesianAxisManager axes){
        return AXES_ZERO + (shape.getPosition().getX() * axes.getTickSize());
    }

    /**
     * Converte una generica y nella scala dell'interfaccia grafica
     * @param shape
     * @param axes
     * @return
     */
    private static Double getSceneY(Shape<TwoDimensionalPoint> shape, CartesianAxisManager axes){
        return AXES_ZERO - (shape.getPosition().getY() * axes.getTickSize());
    }

    /**
     * Presa una misura come parametro la restituisce nella scala del piano cartesiano
     * corrente.
     * @param misure
     * @param axes
     * @return
     */
    private static Double scaleToScene(Double misure, CartesianAxisManager axes){
        return misure * axes.getTickSize();
    }

    public void addLabelToShape(javafx.scene.shape.Shape shape, String shapeLabel) {
        Label label = new Label(shapeLabel);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(shape, label);

        // Imposta la posizione della Label nel StackPane (puoi personalizzare la posizione a tuo piacimento)
      //  StackPane.setTranslateX(); // Esempio: sposta la Label 20 pixel a destra
       // StackPane.setTranslateY(label, -20); // Esempio: sposta la Label 20 pixel verso l'alto

        // Aggiungi il StackPane (con il Circle e la Label) al contenitore desiderato nella tua GUI
        // Ad esempio, se hai un Pane chiamato "root" nella tua GUI, puoi aggiungerlo così:

        cartesian.getChildren().add(stackPane);
    }


}



