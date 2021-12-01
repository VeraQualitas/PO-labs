package agh.ics.oop;

import java.util.HashMap;

public class GrassField extends AbstractWorldMap {
    protected final HashMap<Vector2d, Grass> grassMap;

    public GrassField(int n) {
        super();
        this.grassMap = new HashMap<>();
        int randomMax = (int) Math.sqrt(n * 10);
        while (n > 0) {
            Vector2d randomPosition = new Vector2d((int) (Math.random() * randomMax), (int) (Math.random() * randomMax));
            if (placeGrass(randomPosition)){
                n--;
            }
        }
    }
    public boolean placeGrass(Vector2d position) {
        if (this.objectAt(position) == null) {
            this.grassMap.put(position, new Grass(position));
            return true;
        }
        else return false;
    }

    @Override
    protected Vector2d[] getDrawBoundaries() {
        Vector2d[] boundaries = {null, null};
        for (Vector2d element : this.animalMap.keySet()) {
            if (boundaries[0] == null) {
                boundaries[0] = element;
            }
            if (boundaries[1] == null) {
                boundaries[1] = element;
            }
            boundaries[0] = boundaries[0].lowerLeft(element);
            boundaries[1] = boundaries[1].upperRight(element);
        }
        return boundaries;
    }

    @Override
    public boolean place(Animal animal) {
        return super.place(animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(this.objectAt(position) instanceof Animal);
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object response = this.animalMap.get(position);
        if (response != null) return response;
        else return this.grassMap.get(position);
    }

}