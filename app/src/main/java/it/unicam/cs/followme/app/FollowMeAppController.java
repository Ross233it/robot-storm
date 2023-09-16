package it.unicam.cs.followme.app;
import it.unicam.cs.followme.controller.Controller;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.StaticCircle;
import it.unicam.cs.followme.model.environment.StaticRectangle;
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
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.hardware.Robot;


public class FollowMeAppController{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final Double SPACE_SQUARE = 700.0;
    public static final Double AXES_ZERO = SPACE_SQUARE / 2.0;
    private int firstColumn = 0;
    private CartesianAxisManager axes;
    private Controller controller;
    private List<Robot>currentRobots;
    private List<Shape>currentShapes;
    private Path programPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "assets", "defaultProgram.txt");
    private Path environmentPath =  Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "assets", "defaultEnvironment.txt");;
    private int robotNumber;
    private int timeUnit;
    private int currentTime;
    private int simDuration;
    Map<Robot, Label> symbolMap = new HashMap<>();

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
     * Registra i dati di configurazione della simulazione nell'interfaccia utente.
     * @param programFile il file contenente il programma da eseguire
     * @param environmentFile il file contenente le impostazioni delle forme geometriche
     * @param robotNumber numero dei robot che si desidera rappresentare     *
     * @param timeUnit unità di tempo in millisecondi.
     */
    public void simSetup(File programFile, File environmentFile, int robotNumber, int timeUnit, int simDuration){
        this.timeUnit = timeUnit;
        if(environmentFile != null)
            this.environmentPath = environmentFile.toPath();

        if(programFile != null)
            this.programPath = programFile.toPath();

        controller.simulationSetup(robotNumber,30.0,  programPath, environmentPath);
        this.currentRobots = controller.getRobots();
        this.currentShapes = controller.getShapes();
        this.simDuration = simDuration;
        this.robotNumber = robotNumber;
        robotInitialize(cartesian);
        displayShapes();
      }

    /**
     * Esegue la simulazione uno step alla volta.
     */
    private void simStepExecution(){
        if(currentTime < simDuration){
            controller.runNextRobotCommand();
            rebuildScene(axes.getScale());
        }
    }

    /**
     * Funzione associata al comando play esegue la simulazione in una sequenza
     * continua fino all'esaurimento del tempo impostato ed alla velocità della time
     * unit impostata.
     */
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

    /**
     * Stampa il tempo corrente della simulazione sul menu
     * @param time il tempo corrente della simulazione.
     */
    public void printTime(Integer time){
        timeLabels.getChildren().clear();
        String toStamp = time < simDuration ? time.toString() : "Simulazione Terminata";
        Label timeLabel = new Label(toStamp);
        timeLabels.getChildren().add(timeLabel);
    }

    /**
     * Avvicina il punto di vista di osservazione.
     */
    private void zoomIn() {rebuildScene(axes.getScale()-10);}

    /**
     * Allontana il punto di vista di osservazione.
     */
    private void zoomOut() {rebuildScene(axes.getScale()+10);}

    /**
     * Posiziona le forme puntiformi dei robot sull'interfaccia grafica
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

    /**
     * Genera gli elementi grafici dei robot sottoforma di punti rossi. I punti
     * hanno informazione della coordinata x e y nella simulazione
     */
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

    /**
     * Genera le forme da creare in base al programma contenuto nel file di testo.
     */
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
                getSceneX(staticRectangle, axes) - (scaleToScene(staticRectangle.getWidth(), axes)/2),
                 getSceneY(staticRectangle, axes) - (scaleToScene(staticRectangle.getHeight(),axes)/2),
                 scaleToScene(staticRectangle.getWidth(), axes),
                 scaleToScene(staticRectangle.getHeight(), axes)
        );
        sceneRectangle.setOpacity(0.5);
        sceneRectangle.setFill(Color.BLUE);
        cartesian.getChildren().add(sceneRectangle);
    }

    /**
     * Converte la x di una forma nella scala dell'interfaccia grafica
     * @param shape la forma di cui scalare la posizione
     * @param axes gli assi cartesiani di riferimento
     * @return misura proporzionata alla scala degli assi
     */
    private Double getSceneX(Shape<TwoDimensionalPoint> shape, CartesianAxisManager axes){
        return AXES_ZERO + (shape.getPosition().getX() * axes.getTickSize());
    }

    /**
     * Converte la y di una forma nella scala dell'interfaccia grafica
     * @param shape la forma di cui scalare la posizione
     * @param axes gli assi cartesiani di riferimento
     * @return misura proporzionata alla scala degli assi
     */
    private Double getSceneY(Shape<TwoDimensionalPoint> shape, CartesianAxisManager axes){
        return AXES_ZERO - (shape.getPosition().getY() * axes.getTickSize());
    }

    /**
     * Presa una misura come parametro la restituisce nella scala del piano cartesiano
     * corrente.
     * @param misure la forma di cui scalare la posizione
     * @param axes gli assi cartesiani di riferimento
     * @return misura proporzionata alla scala degli assi
     */
    private Double scaleToScene(Double misure, CartesianAxisManager axes){
        return misure * axes.getTickSize();
    }
}



