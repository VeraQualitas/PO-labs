package agh.ics.oop;


public class RectangularMap extends AbstractWorldMap {
    private final Vector2d upperRightBound;
    private final Vector2d lowerLeftBound;

    public RectangularMap(int width, int height) {
        this.lowerLeftBound = new Vector2d(0,0);
        this.upperRightBound = new Vector2d(width-1, height-1);
    }
    @Override
    protected Vector2d[] getDrawBoundaries() {
        return new Vector2d[]{this.lowerLeftBound, this.upperRightBound};
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return (super.canMoveTo(position) &&
                position.follows(lowerLeftBound)
                && position.precedes(upperRightBound));
    }

}