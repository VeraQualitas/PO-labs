package agh.ics.oop;
import java.util.HashMap;


public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final HashMap<Vector2d, Animal> animalMap;
    private final MapVisualizer mapVisualizer;

    public AbstractWorldMap() {
        this.animalMap = new HashMap<>();
        this.mapVisualizer = new MapVisualizer(this);
    }

    public String toString() {
        Vector2d[] boundaries = this.getDrawBoundaries();
        return this.mapVisualizer.draw(boundaries[0], boundaries[1]);
    }


    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            this.animalMap.put(position, animal);
            animal.addObserver(this);
            return true;
        }
        else return false;
    }

    public boolean isOccupied(Vector2d position) {
        return (objectAt(position) != null);
    }


    public Object objectAt(Vector2d position) {
        return this.animalMap.get(position);
    }

    abstract protected Vector2d[] getDrawBoundaries();

    abstract public boolean canMoveTo(Vector2d position);

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        this.animalMap.put(newPosition, (Animal) objectAt(oldPosition));
        this.animalMap.remove(oldPosition);
    }

}