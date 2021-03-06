package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;


public class App extends Application implements IGenericObserver {
    private RectangularMap map;
    private RectangularMap map2;
    private GridPane grid;
    private GridPane grid2;
    private SimulationEngine engine;
    private SimulationEngine engine2;
    private XYChart.Series animalsSize, animalsSize2;
    private XYChart.Series grassesSize, grassesSize2;
    private XYChart.Series averageEnergy, averageEnergy2;
    private XYChart.Series averageLife, averageLife2;
    private XYChart.Series averageChildrenAmount, averageChildrenAmount2;
    private Text dominantGenotype, dominantGenotype2;
    private CSVWriter writer;
    private int speed = 300;
    private int width = 4;
    private int height = 4;
    private int animalAmount = 2;
    private int startEnergy = 100;
    private int moveEnergy = 1;
    private int plantEnergy = 30;
    private double jungleRatio = 10;
    private boolean isMagical = false;
    private boolean isMagical2 = false;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
    }

    private boolean initialize() {
        map = new RectangularMap(width, height, jungleRatio, true);
        map2 = new RectangularMap(width, height, jungleRatio, false);
        ArrayList<Vector2d> firstAnimals = new ArrayList<>();
        ArrayList<Vector2d> firstGrasses = new ArrayList<>();
        ArrayList<Vector2d> emptyPositions = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                emptyPositions.add(new Vector2d(x, y));
            }
        }
        try {
            if (emptyPositions.size() < animalAmount + 2) throw new IllegalArgumentException("Liczba p??l jest za ma??a, aby pomie??ci?? " + animalAmount + " zwi??rz??t i dwie trawy");
            else {
                Vector2d emptyPosition;
                // = emptyPositions.get((int) (Math.random() * emptyPositions.size()))
                emptyPosition = this.map.getEmptyPosition(true);
                firstGrasses.add(emptyPosition);
                emptyPositions.remove(emptyPosition);

                emptyPosition = this.map.getEmptyPosition(false);
                firstGrasses.add(emptyPosition);
                emptyPositions.remove(emptyPosition);

                for (int animal = 0; animal < animalAmount; animal++) {
                    emptyPosition = emptyPositions.get((int) (Math.random() * emptyPositions.size()));
                    emptyPositions.remove(emptyPosition);
                    firstAnimals.add(emptyPosition);
                }
            }
        }
        catch (IllegalArgumentException e) {
            return false;
        }

        this.engine = new SimulationEngine(map, speed, isMagical, firstAnimals, firstGrasses, startEnergy, moveEnergy, plantEnergy);
        this.engine2 = new SimulationEngine(map2, speed, isMagical2, firstAnimals, firstGrasses, startEnergy, moveEnergy, plantEnergy);
        this.engine.addObserver(this);
        this.engine2.addObserver(this);
        this.grid = new GridPane();
        this.grid2 = new GridPane();
        return true;
    }

    public ScrollPane simulationGui() {
        Text wrappedMap = new Text("Mapa zawini??ta");
        Text unwrappedMap = new Text("Mapa z murem");

        Button startButton = new Button("Zatrzymaj/Wzn??w map?? zawini??t??");
        startButton.setAlignment(Pos.CENTER);
        startButton.setMaxWidth(300);
        startButton.setMinWidth(300);

        Button exportToCSV = new Button("Wyeksportuj map?? zawini??t?? do .csv");
        exportToCSV.setDisable(true);
        exportToCSV.setMinWidth(300);
        exportToCSV.setMaxWidth(300);

        Button startButton2 = new Button("Zatrzymaj/Wzn??w map?? z murem");
        startButton2.setAlignment(Pos.CENTER);
        startButton2.setMaxWidth(300);
        startButton2.setMinWidth(300);

        Button exportToCSV2 = new Button("Wyeksportuj map?? z murem do .csv");
        exportToCSV2.setDisable(true);
        exportToCSV2.setMinWidth(300);
        exportToCSV2.setMaxWidth(300);


        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Liczba dni");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ilo????");

        LineChart animalChart = new LineChart(xAxis, yAxis);
        animalChart.setCreateSymbols(false);

        NumberAxis xAxis2 = new NumberAxis();
        xAxis.setLabel("Liczba dni");

        NumberAxis yAxis2 = new NumberAxis();
        yAxis.setLabel("Ilo????");

        LineChart energyChart = new LineChart(xAxis2, yAxis2);
        energyChart.setCreateSymbols(false);

        this.animalsSize = new XYChart.Series();
        this.animalsSize2 = new XYChart.Series();
        animalsSize.setName("Liczba zwierz??t na zawini??tej mapie");
        animalsSize2.setName("Liczba zwi??rzat na mapie z murem");

        this.grassesSize = new XYChart.Series();
        this.grassesSize2 = new XYChart.Series();
        grassesSize.setName("Liczba traw na zawini??tej mapie");
        grassesSize2.setName("Liczba traw na mapie z murem");

        this.averageEnergy = new XYChart.Series();
        this.averageEnergy2 = new XYChart.Series();
        averageEnergy.setName("??rednia ilo???? energii na zawini??tej mapie");
        averageEnergy2.setName("??rednia ilo???? energii na mapie z murem");

        this.averageLife = new XYChart.Series();
        this.averageLife2 = new XYChart.Series();
        averageLife.setName("??rednia ilo???? prze??ytych dni na zawini??tej mapie");
        averageLife2.setName("??rednia ilo???? prze??ytych dni na mapie z murem");

        this.averageChildrenAmount = new XYChart.Series();
        this.averageChildrenAmount2 = new XYChart.Series();
        averageChildrenAmount.setName("??rednia ilo???? dzieci ??yj??cych zwierz??t na zawini??tej mapie");
        averageChildrenAmount2.setName("??rednia ilo???? dzieci ??yj??cych zwierz??t na mapie z murem");

        animalChart.getData().add(animalsSize);
        animalChart.getData().add(animalsSize2);
        animalChart.getData().add(grassesSize);
        animalChart.getData().add(grassesSize2);

        animalChart.getData().add(averageChildrenAmount);
        animalChart.getData().add(averageChildrenAmount2);

        energyChart.getData().add(averageEnergy);
        energyChart.getData().add(averageEnergy2);
        energyChart.getData().add(averageLife);
        energyChart.getData().add(averageLife2);


        VBox chartBox = new VBox(animalChart, energyChart);

        this.dominantGenotype = new Text("");
        this.dominantGenotype2 = new Text("");
        this.dominantGenotype.setStyle("-fx-font: 14 arial;");
        this.dominantGenotype2.setStyle("-fx-font: 14 arial;");


//        grid.setAlignment(Pos.CENTER_RIGHT);
//        grid2.setAlignment(Pos.CENTER_LEFT);
        VBox mapBox1 = new VBox(wrappedMap, this.grid,new VBox(startButton, exportToCSV));
        mapBox1.setAlignment(Pos.CENTER);
        mapBox1.setSpacing(10);

        VBox mapBox2 = new VBox(unwrappedMap, this.grid2, new VBox(startButton2, exportToCSV2));
        mapBox2.setAlignment(Pos.CENTER);
        mapBox2.setSpacing(10);

        HBox mapsBox = new HBox(mapBox1, mapBox2);
        mapsBox.setAlignment(Pos.CENTER);
        mapsBox.setSpacing(10);


        VBox appBox = new VBox(new Text(""), mapsBox, chartBox, dominantGenotype, dominantGenotype2);
        ScrollPane scroll = new ScrollPane(appBox);
        scroll.setFitToWidth(true);
        //        appBox.setAlignment(Pos.CENTER);
        draw();

        startButton.setOnAction(ev -> {
            engine.changeFlag();
            exportToCSV.setDisable(!exportToCSV.isDisabled());
        });
        exportToCSV.setOnAction(ev -> {
            try {
                this.writer = new CSVWriter(new File("day-" + this.engine.getDaysAmount() + "-wrapped.csv"), null);
                this.writer.writeHeader(new String[]{"Dzie??", "Liczba zwierz??t", "Liczba traw", "??redni poziom energii", "??rednia d??ugo???? ??ycia", "??rednia liczba dzieci"});
                double averageAnimalSize = 0;
                double averageGrassesSize = 0;
                double averageEnergy = 0;
                double averageLife = 0;
                double averageChildrenAmount = 0;
                for (int counter=0; counter < this.engine.getDaysAmount(); counter++) {
                    averageAnimalSize += (double)(int) (((XYChart.Data) this.animalsSize.getData().get(counter)).getYValue());
                    averageGrassesSize += (double)(int) (((XYChart.Data) this.grassesSize.getData().get(counter)).getYValue());
                    averageEnergy += (double) (((XYChart.Data) this.averageEnergy.getData().get(counter)).getYValue());
                    averageLife += (double) (((XYChart.Data) this.averageLife.getData().get(counter)).getYValue());
                    averageChildrenAmount += (double) (((XYChart.Data) this.averageChildrenAmount.getData().get(counter)).getYValue());
                    this.writer.writeData(new String[]{
                            String.valueOf(counter),
                            ((XYChart.Data) this.animalsSize.getData().get(counter)).getYValue().toString(),
                            ((XYChart.Data) this.grassesSize.getData().get(counter)).getYValue().toString(),
                            ((XYChart.Data) this.averageEnergy.getData().get(counter)).getYValue().toString(),
                            ((XYChart.Data) this.averageLife.getData().get(counter)).getYValue().toString(),
                            ((XYChart.Data) this.averageChildrenAmount.getData().get(counter)).getYValue().toString()
                    });
                }
                this.writer.writeData(new String[]{
                        "??rednia",
                        "" + averageAnimalSize / this.animalsSize.getData().size(),
                        "" + averageGrassesSize / this.grassesSize.getData().size(),
                        "" + averageEnergy / this.averageEnergy.getData().size(),
                        "" + averageLife / this.averageLife.getData().size(),
                        "" + averageChildrenAmount / this.averageChildrenAmount.getData().size()
                });
                this.writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        startButton2.setOnAction(ev -> {
            engine2.changeFlag();
            exportToCSV2.setDisable(!exportToCSV2.isDisabled());
        });
        exportToCSV2.setOnAction(ev -> {
            try {
                this.writer = new CSVWriter(new File("day-" + this.engine2.getDaysAmount() + "-unwrapped.csv"), null);
                this.writer.writeHeader(new String[]{"Dzie??", "Liczba zwierz??t", "Liczba traw", "??redni poziom energii", "??rednia d??ugo???? ??ycia", "??rednia liczba dzieci"});
                double averageAnimalSize = 0;
                double averageGrassesSize = 0;
                double averageEnergy = 0;
                double averageLife = 0;
                double averageChildrenAmount = 0;
                for (int counter=0; counter < this.engine2.getDaysAmount(); counter++) {
                    averageAnimalSize += (double)(int) (((XYChart.Data) this.animalsSize2.getData().get(counter)).getYValue());
                    averageGrassesSize += (double)(int) (((XYChart.Data) this.grassesSize2.getData().get(counter)).getYValue());
                    averageEnergy += (double) (((XYChart.Data) this.averageEnergy2.getData().get(counter)).getYValue());
                    averageLife += (double) (((XYChart.Data) this.averageLife2.getData().get(counter)).getYValue());
                    averageChildrenAmount += (double) (((XYChart.Data) this.averageChildrenAmount2.getData().get(counter)).getYValue());
                    this.writer.writeData(new String[]{
                            String.valueOf(counter),
                            ((XYChart.Data) this.animalsSize2.getData().get(counter)).getYValue().toString(),
                            ((XYChart.Data) this.grassesSize2.getData().get(counter)).getYValue().toString(),
                            ((XYChart.Data) this.averageEnergy2.getData().get(counter)).getYValue().toString(),
                            ((XYChart.Data) this.averageLife2.getData().get(counter)).getYValue().toString(),
                            ((XYChart.Data) this.averageChildrenAmount2.getData().get(counter)).getYValue().toString()
                    });
                }
                this.writer.writeData(new String[]{
                        "??rednia",
                        "" + averageAnimalSize / this.animalsSize2.getData().size(),
                        "" + averageGrassesSize / this.grassesSize2.getData().size(),
                        "" + averageEnergy / this.averageEnergy2.getData().size(),
                        "" + averageLife / this.averageLife2.getData().size(),
                        "" + averageChildrenAmount / this.averageChildrenAmount2.getData().size()
                });
                this.writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return scroll;
    }
    private boolean validate(TextField speed, TextField width, TextField height, TextField animalAmount,
                             TextField startEnergy, TextField moveEnergy, TextField plantEnergy,
                             TextField jungleRatio, CheckBox isMagical, CheckBox isMagical2) {
        try {
            this.speed = Integer.parseInt(speed.getText());
            this.width = Integer.parseInt(width.getText());
            this.height = Integer.parseInt(height.getText());
            this.animalAmount = Integer.parseInt(animalAmount.getText());
            this.startEnergy = Integer.parseInt(startEnergy.getText());
            this.moveEnergy = Integer.parseInt(moveEnergy.getText());
            this.plantEnergy = Integer.parseInt(plantEnergy.getText());
            this.jungleRatio = Double.parseDouble(jungleRatio.getText());
            this.isMagical = isMagical.isSelected();
            this.isMagical2 = isMagical2.isSelected();

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public void start(Stage primaryStage) {
        Text speedText = new Text("\nSzybko???? symulacji [ms] [1-...] (mo??e zaci??c komputer):\n");
        TextField speed = new TextField("" + this.speed);
        speed.setAlignment(Pos.CENTER);

        Text widthText = new Text("\nSzeroko???? mapy [1-...]:\n");
        TextField width = new TextField("" + this.width);
        width.setAlignment(Pos.CENTER);

        Text heightText = new Text("\nWysoko???? mapy [1-...]:\n");
        TextField height = new TextField("" + this.height);
        height.setAlignment(Pos.CENTER);

        Text animalAmountText = new Text("\nIlo???? zwierz??t na mapie [1-...]:\n");
        TextField animalAmount = new TextField("" + this.animalAmount);
        animalAmount.setAlignment(Pos.CENTER);

        Text startEnergyText = new Text("\nPocz??tkowa energia zwierz??t [1-...]:\n");
        TextField startEnergy = new TextField("" + this.startEnergy);
        startEnergy.setAlignment(Pos.CENTER);

        Text moveEnergyText = new Text("\nKoszt ruchu [energia] [1-...]:\n");
        TextField moveEnergy = new TextField("" + this.moveEnergy);
        moveEnergy.setAlignment(Pos.CENTER);

        Text plantEnergyText = new Text("\nIlo???? energii w trawie [1-...]:\n");
        TextField plantEnergy = new TextField("" + this.plantEnergy);
        plantEnergy.setAlignment(Pos.CENTER);

        Text jungleRatioText = new Text("\nStosunek jungli do mapy [0.0-100.0]:\n");
        TextField jungleRatio = new TextField("" + this.jungleRatio);
        jungleRatio.setAlignment(Pos.CENTER);

        CheckBox isMagical = new CheckBox("Strategia magiczna na mapie zawini??tej");
        isMagical.setSelected(this.isMagical);
        CheckBox isMagical2 = new CheckBox("Strategia magiczna na mapie z murem");
        isMagical2.setSelected(this.isMagical);
        Button startButton = new Button("Rozpocznij symulacje");

        Text incorrectData = new Text("Wprowadzone dane s?? nieprawid??owe.");
        incorrectData.setFill(Color.RED);
        incorrectData.setVisible(false);

        startButton.setOnAction(ev -> {
            if (this.validate(speed, width, height, animalAmount, startEnergy,
                    moveEnergy, plantEnergy, jungleRatio, isMagical, isMagical2) && this.initialize()) {
                incorrectData.setVisible(false);

                Scene scene = new Scene(simulationGui(), 600, 600);
                primaryStage.setScene(scene);
                primaryStage.show();
                primaryStage.setMaximized(true);

                Thread engineThread = new Thread(engine);
                engineThread.start();

                Thread engineThread2 = new Thread(engine2);
                engineThread2.start();
            }
            else {
                incorrectData.setVisible(true);
            }

        });
        VBox appBox = new VBox(speedText, speed, widthText, width, heightText, height, animalAmountText, animalAmount,
                startEnergyText, startEnergy, moveEnergyText, moveEnergy, plantEnergyText, plantEnergy,
                jungleRatioText, jungleRatio);
        appBox.setAlignment(Pos.TOP_CENTER);
        VBox appBoxs = new VBox(appBox, new Text(""), isMagical, new Text(""), isMagical2, new Text(""), startButton, incorrectData);
        Scene primaryScene = new Scene(appBoxs, 400, 680);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    public void draw() {
        this.grid.getChildren().clear();
        drawBase(grid, this.map, 27);
        drawFill(grid, this.map);
        this.grid2.getChildren().clear();
        drawBase(grid2, this.map2, 27);
        drawFill(grid2, this.map2);
        animalsSize.getData().add(new XYChart.Data( engine.getDaysAmount(), engine.getAnimalSize()));
        animalsSize2.getData().add(new XYChart.Data( engine2.getDaysAmount(), engine2.getAnimalSize()));
        grassesSize.getData().add(new XYChart.Data( engine.getDaysAmount(), engine.getGrassesSize()));
        grassesSize2.getData().add(new XYChart.Data( engine2.getDaysAmount(), engine2.getGrassesSize()));
        averageEnergy.getData().add(new XYChart.Data( engine.getDaysAmount(), engine.getAverageEnergy()));
        averageEnergy2.getData().add(new XYChart.Data( engine2.getDaysAmount(), engine2.getAverageEnergy()));
        averageLife.getData().add(new XYChart.Data( engine.getDaysAmount(), engine.getAverageAnimalLife()));
        averageLife2.getData().add(new XYChart.Data( engine2.getDaysAmount(), engine2.getAverageAnimalLife()));
        averageChildrenAmount.getData().add(new XYChart.Data( engine.getDaysAmount(), engine.getAverageChildrenAmount()));
        averageChildrenAmount2.getData().add(new XYChart.Data( engine2.getDaysAmount(), engine2.getAverageChildrenAmount()));

        this.dominantGenotype.setText("Dominanta genotypu dla mapy zawini??tej:\n" + (engine.getDominantGenotype() == null ? "-" : engine.getDominantGenotype().toString()));
        this.dominantGenotype2.setText("Dominanta genotypu dla mapy z murem:\n" + (engine2.getDominantGenotype() == null ? "-" : engine2.getDominantGenotype().toString()));
        if (engine.ifMagicHappened()) {
            Alert magicalAlert = new Alert(Alert.AlertType.INFORMATION);
            magicalAlert.setHeaderText("Na mapie zawini??tej magiczna sztuczka zosta??a u??yta");
            magicalAlert.show();
        }
        if (engine2.ifMagicHappened()) {
            Alert magicalAlert = new Alert(Alert.AlertType.INFORMATION);
            magicalAlert.setHeaderText("Na mapie z murem magiczna sztuczka zosta??a u??yta");
            magicalAlert.show();
        }
    }

    public void drawBase(GridPane grid, RectangularMap map, int grid_size) {
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        Vector2d[] boundaries = map.getDrawBoundaries();
        for (int i = boundaries[0].x; i <= boundaries[1].x; i++) {
            for (int j = boundaries[0].y; j <= boundaries[1].y; j++) {
                Vector2d position = new Vector2d(i, j);
                if (map.isJungle(position)) {
                    Pane isJungle = new Pane();
                    isJungle.setStyle("-fx-background-color: lightgreen");
                    grid.add(isJungle,
                            i - boundaries[0].x + 1, boundaries[1].y - j);
                }
            }
        }
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

    public void drawFill(GridPane grid, RectangularMap map) {

        Vector2d[] boundaries = map.getDrawBoundaries();

        for (int i = boundaries[0].x; i <= boundaries[1].x; i++) {
            for (int j = boundaries[0].y; j <= boundaries[1].y; j++) {
                Vector2d position = new Vector2d(i, j);
                if (map.isAnimalAt(position)) {
                    TreeSet<Animal> animals = map.getAnimalAt(position);
                    grid.add(new GuiElementBox(animals.first()).mapElementView(),
                            i - boundaries[0].x + 1, boundaries[1].y-j);
                }
                else if (map.isGrassAt(position)) {
                    Grass grass = map.getGrassAt(position);
                    grid.add(new GuiElementBox(grass).mapElementView(),
                            i - boundaries[0].x + 1, boundaries[1].y-j);
                }

            }
        }
    }

    @Override
    public void update() {
        Platform.runLater(this::draw);
//        this.draw();
    }
}