package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application implements IGenericObserver {
    private GrassField map;
    private GridPane grid;
    private SimulationEngine engine;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();

        map = new GrassField(10);
        Vector2d[] firstPlaces = {new Vector2d(2, 4), new Vector2d(3, 5)};

        this.engine = new SimulationEngine(map, firstPlaces);
        this.engine.addObserver(this);
        this.grid = new GridPane();
    }


    @Override
    public void start(Stage primaryStage) {
        TextField movesInput = new TextField();
        Button startButton = new Button("Move");
        VBox inputBox = new VBox(movesInput, startButton);
        VBox appBox = new VBox(this.grid, inputBox);
        grid.setAlignment(Pos.CENTER);
        inputBox.setAlignment(Pos.CENTER);
        appBox.setAlignment(Pos.CENTER);
        movesInput.setMaxWidth(100);

        draw();
        Scene scene = new Scene(appBox, 1000, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();

        startButton.setOnAction(ev -> {
            String[] args = movesInput.getText().split("f:forward:l:left:r:right:b:backward");
            MoveDirection[] directions = OptionsParser.parse(args);
            engine.setMoves(directions);
            Thread engineThread = new Thread(engine);
            engineThread.start();
        });
    }

    public void draw() {
        this.grid.getChildren().clear();
        drawBase(grid, map, 20);
        drawFill(grid, map);
    }

    public void drawBase(GridPane grid, GrassField map, int grid_size) {
        this.grid.getColumnConstraints().clear();
        this.grid.getRowConstraints().clear();
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);

        Vector2d[] bounds = map.getDrawBoundaries();
        for (int i = 0; i <= bounds[1].x-bounds[0].x; i++) {
            Label label = new Label(String.valueOf(bounds[0].x+i));
            grid.add(label, i+1, bounds[1].y-bounds[0].y+1);
            GridPane.setHalignment(label, HPos.CENTER);
            grid.getColumnConstraints().add(new ColumnConstraints(grid_size));
        }
        for (int i = 0; i <= bounds[1].y-bounds[0].y; i++) {
            Label label = new Label(String.valueOf(bounds[0].y+i));
            grid.add(label, 0, bounds[1].y-bounds[0].y-i);
            GridPane.setHalignment(label, HPos.CENTER);
            grid.getRowConstraints().add(new RowConstraints(grid_size));
        }

        Label xylabel = new Label("y/x");
        grid.add(xylabel, 0, bounds[1].y-bounds[0].y+1);
        GridPane.setHalignment(xylabel, HPos.CENTER);
        grid.getRowConstraints().add(new RowConstraints(grid_size));
        grid.getColumnConstraints().add(new ColumnConstraints(grid_size));
    }

    public void drawFill(GridPane grid, GrassField map) {

        Vector2d[] bounds = map.getDrawBoundaries();

        for (int i = bounds[0].x; i <= bounds[1].x; i++) {
            for (int j = bounds[0].y; j <= bounds[1].y; j++) {
                Object object = map.objectAt(new Vector2d(i, j));
                if (object != null) {
                    grid.add(new GuiElementBox((IMapElement) object).mapElementView(),
                            i - bounds[0].x + 1, bounds[1].y-j);
                }
            }
        }
    }

    @Override
    public void update() {
        Platform.runLater(this::draw);
    }
}