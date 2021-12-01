package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine {
    private final IWorldMap map;
    private final MoveDirection[] moves;
    private final ArrayList<Animal> animals;


    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] firstPlaces) {
        this.moves = moves;
        this.map = map;
        this.animals = new ArrayList<>();

        for (Vector2d firstPlace: firstPlaces){
            Animal animal = new Animal(map, firstPlace);
            if (map.place(animal)) {
                this.animals.add(animal);
            }
        }
    }

    public void run(){
        for (int i=0; i < this.moves.length; i++) {
            this.animals.get(i % this.animals.size()).move(this.moves[i]);
            System.out.println(this.map);
        }
    }

}
