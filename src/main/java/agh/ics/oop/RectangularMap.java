package agh.ics.oop;


public class RectangularMap extends AbstractWorldMap implements IWorldMap {
    private final Vector2d upperRightBound;
    private final Vector2d lowerLeftBound;

    public RectangularMap(int width, int height) {
        lowerLeftBound = new Vector2d(0,0);
        upperRightBound = new Vector2d(width-1, height-1);
    }

    public String toString() {
        return new MapVisualizer(this).draw(lowerLeftBound, upperRightBound);
    }

    @Override
    protected Vector2d[] getDrawBoundaries() {
        return new Vector2d[]{lowerLeftBound, upperRightBound};
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(lowerLeftBound) && position.precedes(upperRightBound)){
            return !isOccupied(position);
        } else return false;
    }

    @Override
    public boolean place(Animal animal) {
        return super.place(animal);
    }

}