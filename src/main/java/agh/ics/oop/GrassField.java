package agh.ics.oop;

import java.util.HashMap;

public class GrassField extends AbstractWorldMap {
    protected final MapBoundary mapBoundary;
    private final int randomMax;

    public GrassField(int n) {
        super();
        this.mapBoundary = new MapBoundary();
        this.randomMax = (int) Math.sqrt(n * 10);
        while (n > 0) {
            Vector2d randomPosition = new Vector2d((int) (Math.random() * randomMax), (int) (Math.random() * randomMax));
            if (placeGrass(randomPosition)){
                n--;
            }
        }
    }
    @Override
    protected Vector2d[] getDrawBoundaries() {
        return mapBoundary.getBoundaries();
    }
    public boolean placeGrass(Vector2d position) {
        if (this.objectAt(position) == null) {
            Grass grass = new Grass(position);
            animalMap.put(position, grass);
            this.mapBoundary.place(grass);
            return true;
        }
        else return false;
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        Vector2d position = animal.getPosition();
        if (objectAt(position) instanceof Grass) {
            Vector2d randomPosition = new Vector2d((int) (Math.random() * randomMax), (int) (Math.random() * randomMax));
            int counter = 0;
            while (!placeGrass(randomPosition)) {   // czy to nie powinno być wydzielone do osobnej metody?
                if (counter >= this.randomMax * this.randomMax + 10) {return false; }   // co oznacza wartość false? nie może być tak, że nie da się dodać zwierzęcia do mapy, bo nie znaleźliśmy miejsca dla trawy
                randomPosition = new Vector2d((int) (Math.random() * randomMax), (int) (Math.random() * randomMax));
                counter++;
            };
        } else if (objectAt(position) instanceof Animal) {
            throw new IllegalArgumentException("Cannot place animal at " + position);
        }
        mapBoundary.place(animal);
        animal.addObserver(mapBoundary);    // właściwie to powinno być w MapBoundary
        animalMap.put(position, animal);
        animal.addObserver(this);
        return true;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(this.objectAt(position) instanceof Animal);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if (objectAt(oldPosition) instanceof Animal && objectAt(newPosition) instanceof Grass) {
            Vector2d randomPosition = new Vector2d((int) (Math.random() * randomMax), (int) (Math.random() * randomMax));
            int counter = 0;
            while (!placeGrass(randomPosition)) {
                randomPosition = new Vector2d((int) (Math.random() * randomMax), (int) (Math.random() * randomMax));
                counter++;
            };
        }
        animalMap.put(newPosition, (IMapElement) objectAt(oldPosition));
        animalMap.remove(oldPosition);
    }

}