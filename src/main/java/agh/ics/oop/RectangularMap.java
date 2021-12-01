package agh.ics.oop;


public class RectangularMap extends AbstractWorldMap implements IWorldMap {
    private final Vector2d upperRightBound;
    private final Vector2d lowerLeftBound;

    public RectangularMap(int width, int height) {
        this.lowerLeftBound = new Vector2d(0,0);
        this.upperRightBound = new Vector2d(width-1, height-1);
    }

    public String toString() {
        return new MapVisualizer(this).draw(this.lowerLeftBound, this.upperRightBound);
    }

    @Override
    protected Vector2d[] getDrawBoundaries() {
        return new Vector2d[]{this.lowerLeftBound, this.upperRightBound};
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(this.lowerLeftBound) && position.precedes(this.upperRightBound)){
            return !isOccupied(position);
        } else return false;
    }

    @Override
    public boolean place(Animal animal) {
        return super.place(animal);
    }

}