package agh.ics.oop;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final ConcurrentHashMap<Vector2d, TreeSet<Animal>> animalMap;
    protected final EnergyComparator energyComparator;
    protected final ConcurrentHashMap<Vector2d, Grass> grassMap;

    public AbstractWorldMap() {
        this.animalMap = new ConcurrentHashMap<>();
        this.grassMap = new ConcurrentHashMap<>();
        this.energyComparator = new EnergyComparator();
    }

    //    public boolean isOccupied(Vector2d position) {
    //        return (objectAt(position) != null);
    //    }

    //    public IMapElement objectAt(Vector2d position) {
    //        return this.animalMap.get(position);
    //    }

    abstract public Vector2d[] getDrawBoundaries();

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        if (this.animalMap.containsKey(newPosition)) {
            this.animalMap.get(newPosition).add(animal);
        }
        else {
            TreeSet<Animal> treeSetAnimal = new TreeSet<>(energyComparator);
            treeSetAnimal.add(animal);
            this.animalMap.put(newPosition, treeSetAnimal);
        }
        if (this.animalMap.containsKey(oldPosition)) {
            this.animalMap.get(oldPosition).remove(animal);

            if (this.animalMap.get(oldPosition).isEmpty()) {
                this.animalMap.remove(oldPosition);
            }
        }

    }
    public boolean isAnimalAt(Vector2d position) {
        return animalMap.containsKey(position);
    }
    public boolean isGrassAt(Vector2d position) {
        return grassMap.containsKey(position);
    }
    public TreeSet<Animal> getAnimalAt(Vector2d position) {
        return animalMap.get(position);
    }
    public Grass getGrassAt(Vector2d position) {
        return grassMap.get(position);
    }
}