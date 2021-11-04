package agh.ics.oop;

public class SimulationEngine implements IEngine {
    private final IWorldMap map;
    private final MoveDirection[] moves;
    private final Animal[] animals;


    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] firstPlaces) {
        this.map = map;
        this.moves = moves;
        this.animals = new Animal[firstPlaces.length];
        int counter = 0;
        for (Vector2d firstPlace:firstPlaces) {
            this.animals[counter++] = new Animal(map, firstPlace);
        }

        for (Animal animal:animals) {
            this.map.place(animal);
        }
    }

    public void run(){
        Vector2d oldPosition, newPosition;
        for (int i=0; i < this.moves.length; i++) {
            oldPosition = this.animals[i % this.animals.length].getPosition();
            this.animals[i % this.animals.length].move(this.moves[i]);
            newPosition = this.animals[i % this.animals.length].getPosition();
            this.map.move(oldPosition, newPosition);
        }
    }

}
