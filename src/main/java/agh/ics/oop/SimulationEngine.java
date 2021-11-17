package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine {
    private final IWorldMap map;
    private final MoveDirection[] moves;
    private final ArrayList<Animal> animals;


    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] firstPlaces) {
        this.map = map;
        this.moves = moves;
        this.animals = new ArrayList<>();

        for (Vector2d firstPlace: firstPlaces){
            Animal animal = new Animal(map, firstPlace);
            if (map.place(animal)) {
                animals.add(animal);
            }
        }
    }

    public void run(){
        for (int i=0; i < this.moves.length; i++) {
            this.animals.get(i % animals.size()).move(moves[i]);
            System.out.println(map);
        }
    }

}
