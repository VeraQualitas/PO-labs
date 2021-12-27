package agh.ics.oop;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo
 *
 */
public interface IWorldMap {
    Vector2d moveTo(Vector2d oldPosition, Vector2d newPosition);
    void place(Animal animal);
    void place(Grass grass);
    int getSize();
    ArrayList<Grass> addDailyGrasses();
    Vector2d[] getDrawBoundaries();

    void removeAnimal(Animal animal);
    Grass removeGrass(Grass grass);
    boolean isAnimalAt(Vector2d position);
    boolean isGrassAt(Vector2d position);
    TreeSet<Animal> getAnimalAt(Vector2d position);
    Grass getGrassAt(Vector2d position);
    ArrayList<Animal> reproduceAnimals(int startEnergy);
    Vector2d getEmptyPosition(boolean inJungle);

//    boolean isOccupied(Vector2d position);

//    Object objectAt(Vector2d position);
}