package it.unicam.cs.followme.app;



import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.StaticCircle;
import it.unicam.cs.followme.model.environment.StaticRectangle;
import it.unicam.cs.followme.model.timeManagment.SimulationTimer;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private Button saveButton;

    @FXML
    private Button zoomInButton;

    @FXML
    private Button zoomOutButton;

    @FXML
    private Button scrollLeftButton;

    @FXML
    private Button scrollRightButton;

    @FXML
    private Button scrollUpButton;

    @FXML
    private Button scrollDownButton;

    @FXML
    private Button stepForwardButton;

    @FXML
    private Button stepBackwardButton;

    private Rectangle[][] rectangles;

    @FXML
    private Group cartesian;
    //private CartesianAxisManager axes;

    private int row = 50;
    private int columns = 50;
    private int cellSize = 30;

    private int firstRow = 0;

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

//    ConvertPointScale add =((axesZero, programmable, axes1) ->{
//                TwoDimensionalPoint valueToConvert = (TwoDimensionalPoint) programmable.getPosition();
//                double value = axesZero + ( valueToConvert.getX()) * axes1.getTickSize());
//                return value; });

    @FXML
    public void initialize() {
        this.axes = new CartesianAxisManager(40, cartesian);
        Supplier<Controller> instance = Controller::new;
        this.controller = instance.get();


         // controller.simulationSetup(this.robotNumber, 30.0, programPath, environmentPath);
         // controller.runSimulation(this.timeUnit);
       // this.currentRobots = controller.getRobots();
       // this.currentShapes = controller.getShapes();


//        currentRobots.stream()
//                .flatMap(robot -> robot.getMemory().getAllStates().values().stream()) // Ottieni uno stream di RobotState da ciascun robot
//                .forEach(robotState -> {
//                    // Qui puoi elaborare ciascun stato singolarmente
//
//                    System.out.println("Robot: " + robotState.robotId());
//                    System.out.println("Position: " + robotState.position().getX());
//                    if(robotState.direction()!= null)
//                        System.out.println("Vector: " + robotState.direction().getSpeed());
//                    System.out.println("Label: " + robotState.label());
//                    // Esegui altre operazioni sui RobotState, se necessario
//                });

       // robotInitialize(cartesian);
       // displayShapes();
        //this.controller.launchRobots();
      //  setup();
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
        robotInitialize(cartesian);
        displayShapes();
    }

    private void simStepExecution(){
//        int i = simDuration;
//        SimulationTimer timer = new SimulationTimer(timeUnit, simDuration);
//        timer.start();
//        while(currentTime < i){
//                timer.updateTime();
//            try {
//                timer.sleep(timeUnit);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (currentTime <= timer.getTime()) {
//                System.out.println("TIMER "+ timer.getTime());
//                System.out.println("CURRENT TIME "+ currentTime);
//               if(controller.runRobotCommand()){ }
//            }

            controller.runNextRobotCommand();
            currentTime++;
            doThings();
        //}
   }

    public void doThings(){
        this.axes.axisSetup(this.axes.getScale(), cartesian);
        robotInitialize(this.cartesian);
        displayShapes();
        System.out.println("QUI è DOPO INIZIALIZE");
        System.out.println("QUI FINISCE L'ITERAZIONE");
    }
    /**
     * This is the method invoked when the observed cells are scrolled on the left.
     *
     * @param event the triggering event.
     */
    @FXML
    private void onScrollLeftCommand(Event event) {
        scrollLeft();
    }

    /**
     * This is the method invoked when the observed cells are scrolled on the right.
     *
     * @param event the triggering event.
     */
    @FXML
    private void onScrollRightCommand(Event event) {
        scrollRight();
    }

    @FXML
    public void onStepForwardCommand(Event event){
        simStepExecution();
    }



    @FXML
    public void onSaveButton(Event event){

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
     * This is the method invoked when the observed cells are up scrolled.
     *
     * @param event the triggering event.
     */
    @FXML
    private void onScrollUpCommand(Event event) {
        scrollUp();
    }

    @FXML
    private void testMethod(Event event) {
        System.out.println("Comando lanciato");
    }

    /**
     * This is the method invoked when the observed cells are down scrolled.
     *
     * @param event the triggering event.
     */
    @FXML
    private void onScrollDownCommand(Event event) {
        scrollDown();
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
        cartesian.getChildren().clear();
    }

    /**
     * This is the method invoked to handle key events.
     *
     * @param event the triggering event.
     */
    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.LEFT) {
            scrollLeft();
        }
        if (event.getCode() == KeyCode.RIGHT) {
            scrollRight();
        }
        if (event.getCode() == KeyCode.UP) {
            scrollUp();
        }
        if (event.getCode() == KeyCode.DOWN) {
            scrollDown();
        }
        if (event.getCode() == KeyCode.PLUS) {
            zoomIn();
        }
        if (event.getCode() == KeyCode.MINUS) {
            zoomOut();
        }
        //refreshCells();
    }

    private void changeBounds(double lx,double ly,double ux,double uy){
        this.axes.getxAxis().setUpperBound(this.axes.getxAxis().getUpperBound()+ux);
        this.axes.getyAxis().setUpperBound(this.axes.getyAxis().getUpperBound()+uy);
        this.axes.getyAxis().setLowerBound(this.axes.getyAxis().getLowerBound()+ly);
        this.axes.getxAxis().setLowerBound(this.axes.getxAxis().getLowerBound()+lx);
    }

    /**
     * This method is used to scroll down the observed cells.
     */
    private void scrollRight(){
        double dist= this.axes.getxAxis().getUpperBound()- this.axes.getxAxis().getLowerBound();
        changeBounds(0.10*dist,0,0.10*dist,0);
//        entropyX+=(0.10*dist);
//        initAll();
    }

    private void scrollUp(){
        double dist= this.axes.getyAxis().getUpperBound()-this.axes.getyAxis().getLowerBound();
        changeBounds(0,0.10*dist,0,0.10*dist);
//        this.entropyY+=(0.10*dist);
//        initAll();
    }

    private void scrollLeft(){
        double dist= this.axes.getxAxis().getUpperBound()-this.axes.getxAxis().getLowerBound();
        changeBounds(-0.10*dist,0,-0.10*dist,0);
//        entropyX+=(-0.10*dist);
//        initAll();
    }

    private void scrollDown(){
        double dist= this.axes.getyAxis().getUpperBound()-this.axes.getyAxis().getLowerBound();
        changeBounds(0,-0.10*dist,0,-0.10*dist);
//        this.entropyY+=(-0.10*dist);
//        initAll();
    }


    /**
     * This method is used to zoom in the observed cells, namely to increase the number of cells depicted in the interface.
     */
    private void zoomIn() {
        this.axes.axisSetup(
                this.axes.getScale()-10, cartesian);
                robotInitialize(cartesian);
                displayShapes();
    }

    /**
     * This method is used to zoom out the observed cells, namely to increase the cells size.
     */
    private void zoomOut() {
        this.axes.axisSetup(this.axes.getScale()+10, cartesian);
        robotInitialize(cartesian);
        displayShapes();
        }

    /**
     * Inizializza le forme puntiformi dei robot sull'interfaccia grafica
     * @param cartesian un Group destinatario.
     */
    private void robotInitialize(Group cartesian) {
        this.cartesian.getChildren().removeAll(this.symbolMap.keySet());
        System.out.println("IOSONOROBOTINITIALIZE!!!!!");
        symbolMap = this.currentRobots.stream()
                    .filter(e-> e.getPosition().getX()< axes.getScale() && e.getPosition().getY()<axes.getScale())
                    .collect(Collectors.toMap(
                        e -> e,
                        e -> {Label label = new Label( "Id"+e.getId() +" "+ e.getLabel(), new Circle(2,Color.RED));
                        label.setLayoutX(AXES_ZERO + ((e.getPosition().getX()) * axes.getTickSize()));
                        label.setLayoutY(AXES_ZERO - ((e.getPosition().getY()) * axes.getTickSize()));
                        return label;}
                    ));

        this.symbolMap.keySet().stream().forEach(e->{
            if(e.getPosition().getX() < axes.getScale() && e.getPosition().getY()<axes.getScale()){
                this.cartesian.getChildren().add(symbolMap.get(e));
            }
        });
    }

//        public void robotUpdate(){
//            this.symbolMap.keySet().forEach(e->{
//                if(e.getPosition().getX()< axes.getScale() && e.getPosition().getY()<axes.getScale()){
//                        this.cartesian.getChildren().add(e);
//                }
//            });
//
//        }

   // }

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
    public void addCircle(StaticCircle<TwoDimensionalPoint> staticCircle, Group cartesian){
       Circle sceneCircle = new Circle(
               AXES_ZERO + (staticCircle.getPosition().getX() * axes.getTickSize()),
               AXES_ZERO - (staticCircle.getPosition().getY() * axes.getTickSize()),
                staticCircle.getRadius() * axes.getTickSize(), Color.GREEN);
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
                AXES_ZERO  + ((staticRectangle.getPosition().getX() * axes.getTickSize()) - (staticRectangle.getWidth() * axes.getTickSize()/2)),
                AXES_ZERO - ((staticRectangle.getPosition().getY() * axes.getTickSize()) +  (staticRectangle.getHeight() * axes.getTickSize()/2)),
                staticRectangle.getWidth() * axes.getTickSize(),
                staticRectangle.getHeight() * axes.getTickSize()
        );
        sceneRectangle.setOpacity(0.5);
        sceneRectangle.setFill(Color.BLUE);
        cartesian.getChildren().add(sceneRectangle);
    }
//TODO VEDERE SE SERVE
    public double tickSize(){ return axes.getTickSize();}
}



