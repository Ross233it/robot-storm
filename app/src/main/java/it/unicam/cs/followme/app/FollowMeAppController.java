package it.unicam.cs.followme.app;



import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.StaticCircle;
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
import java.util.List;


import it.unicam.cs.followme.controller.Controller;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.programmables.Robot;


public class FollowMeAppController {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final Double SPACE_SQUARE = 700.0;

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
    @FXML
    public void initialize() {
        this.axes = new CartesianAxisManager(40, cartesian);
        this.controller = new Controller();
          controller.simulationSetup(5, 30.0, programPath, environmentPath);
          controller.runSimulation(1000);
        this.currentRobots = controller.getRobots();
        this.currentShapes = controller.getShapes();




        currentRobots.stream()
                .flatMap(robot -> robot.getMemory().getAllStates().values().stream()) // Ottieni uno stream di RobotState da ciascun robot
                .forEach(robotState -> {
                    // Qui puoi elaborare ciascun stato singolarmente

                    System.out.println("Robot: " + robotState.robotId());
                    System.out.println("Position: " + robotState.position().getX());
                    if(robotState.direction()!= null)
                        System.out.println("Vector: " + robotState.direction().getSpeed());
                    System.out.println("Label: " + robotState.label());
                    // Esegui altre operazioni sui RobotState, se necessario
                });

        robotInitialize(cartesian);
        displayShapes();
        //this.controller.launchRobots();
        setup();
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
    public void onOpenFile(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RobotSelectionFXML.fxml"));
        Parent parent = loader.load();
        //SimulationController cont = loader.getController();
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        Scene scene = new Scene(parent, 500, 600);
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
    private void onZoomInCommand(Event event) {
        zoomIn();
    }

    /**
     * This is the method invoked when the cells are zoomed out.
     *
     * @param event the triggering event.
     */
    @FXML
    private void onZoomOutCommand(Event event) {
        zoomOut();
    }

    /**
     * This is the method invoked when the cells are cleared.
     *
     * @param event the triggering event.
     */
    @FXML
    private void onClearCommand(Event event) {
        System.out.println("CLEAR COMMAND FIRED");
//            controller.clear();
//            refreshCells();
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


    /**
     * This method is used to scroll down the observed cells.
     */
    private void scrollDown() {
        firstColumn++;
        //refreshCells();
    }

    /**
     * This method is used to scroll up the observed cells.
     */
    private void scrollUp() {
        firstColumn--;
        //refreshCells();
    }

    /**
     * This method is used to scroll on the right the observed cells.
     */
    private void scrollRight() {
        firstRow++;
        //refreshCells();
    }

    /**
     * This method is used to scroll on the left the observed cells.
     */
    private void scrollLeft() {
        firstRow--;
//            refreshCells();
    }

    /**
     * This method is used to zoom in the observed cells, namely to increase the number of cells depicted in the interface.
     */
    private void zoomIn() {
        this.axes.axisSetup(
                this.axes.getScale()-10, this.cartesian);
               // robotInitialize(this.cartesian);
    }

    /**
     * This method is used to zoom out the observed cells, namely to increase the cells size.
     */
    private void zoomOut() {
        this.axes.axisSetup(this.axes.getScale()+10, this.cartesian);
       // robotInitialize(this.cartesian);
        }


    private void setup() {
       // robotInitialize(this.cartesian);
    }

    private void robotInitialize(Group cartesian) {
        System.out.println("Comando richiamato");
        double tickSize = this.axes.getTickSize();
        double yZero = (SPACE_SQUARE / 2.0)-10;
        double xZero = (SPACE_SQUARE) / 2.0;

        this.currentRobots.stream().forEach(e->{Label
                            label =new Label( e.getId() +" "+ e.getLabel(),
                               new Circle(2,Color.RED));
                            label.setLayoutX(xZero+((e.getPosition().getX())*tickSize));
                            label.setLayoutY(yZero+((e.getPosition().getY())*tickSize));
                            cartesian.getChildren().add(label);
                        });
        System.out.println("Comando completato");
        };

    private void displayShapes(){
        this.currentShapes.stream().forEach(e->{
            if(e instanceof StaticCircle) this.addCircle((StaticCircle) e, this.cartesian);
        });
    }

    public void simSetup(File programFile, File environmentFile, int robotNumber, int timeUnit){
        this.robotNumber = robotNumber;
        this.timeUnit = timeUnit;
        this.environmentPath = environmentFile.toPath();
        this.programPath = programFile.toPath();
    }

    public void addCircle(StaticCircle<TwoDimensionalPoint> staticCircle, Group cartesian){
        double yZero = (SPACE_SQUARE / 2.0)-10;
        double xZero = (SPACE_SQUARE) / 2.0;

        Double x = (staticCircle.getPosition().getX()) * axes.getTickSize();
        Double y = (staticCircle.getPosition().getY()) * axes.getTickSize();
        Double radius = staticCircle.getRadius()*(axes.getTickSize());
        Circle sceneCircle = new Circle(x, y, radius, Color.GREEN);

        sceneCircle.setOpacity(0.5);
        Label label = new Label(staticCircle.getLabel(),sceneCircle);
        this.cartesian.getChildren().add(label);
    }

//    private void addCircle(Map.Entry<Figure,Cordinates>e){
//        it.unicam.cs.followme.model.Circle circle=(it.unicam.cs.followme.model.Circle)e.getKey();
//        Circle c = new Circle
//                (mapX(circle.getR())-mapX(0),Color.BLUE);
//        c.setOpacity(0.5);
//        Label label = new Label(circle.getLabel(),c);
//        label.setLayoutX(mapX(e.getValue().getX())-c.getRadius());
//        label.setLayoutY(mapY(e.getValue().getY())-c.getRadius()/1.35);
//        this.figureMap.put(label,circle);
//        //controllo se il cerchio e' nella simulazione
//        if(isInX(label.getLayoutX()-c.getRadius())&&isInX(c.getRadius()+label.getLayoutX())
//                && isInY(label.getLayoutY()-c.getRadius())&&isInY(c.getRadius()+label.getLayoutY()))
//            this.cartesian.getChildren().add(label);
//    }

//    private void initFigure(){
//        if(this.figureMap.size()>0)this.cartesian.getChildren().removeAll(this.figureMap.keySet());
//        this.figureMap=new HashMap<>();
//        List<Map.Entry<Figure,Cordinates>> figures = this.modelController.getFigures().entrySet().stream().toList();
//        if(figures.size()!=0)
//            figures.forEach(e->{
//                if(e.getKey().getType()==FigureEnumeration.CIRCLE)
//                    this.addCircle(e);
//                else
//                    this.addRectangle(e);
//            });
//    }
}



