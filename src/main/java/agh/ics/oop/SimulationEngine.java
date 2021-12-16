package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine, Runnable {
    private final IWorldMap map;
    private MoveDirection[] moves;
    private final ArrayList<Animal> animals;
    private final ArrayList<IGenericObserver> observers = new ArrayList<>();
    public int counter;


    public SimulationEngine(IWorldMap map, Vector2d[] firstPlaces) {
        this.counter = 0;
        this.map = map;
        this.animals = new ArrayList<>();

        for (Vector2d firstPlace: firstPlaces){
            Animal animal = new Animal(map, firstPlace);
            if (map.place(animal)) {
                this.animals.add(animal);
            }
        }
    }
    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] firstPlaces){
        this(map, firstPlaces);
        this.moves = moves;
    }

    public void setMoves(MoveDirection[] moves) {
        this.moves = moves;
    }

    public void addObserver(IGenericObserver observer) {
        this.observers.add(observer);
    }

    private void updateObservers() {
        for (IGenericObserver observer: this.observers) {
            observer.update();
        }
    }

    public void run(){
        try {
            for (int i=0; i<this.moves.length; i++) {
                System.out.println(this.animals.get(1));
                this.animals.get(this.counter % this.animals.size()).move(this.moves[i]);
                updateObservers();
                this.counter++;
                Thread.sleep(300);
            }

        }  catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }
    }

}
