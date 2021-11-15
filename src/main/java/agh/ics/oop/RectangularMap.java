package agh.ics.oop;


public class RectangularMap implements IWorldMap {
    private final Vector2d upperRightBound;
    private final Vector2d lowerLeftBound;
    private final Animal[][] map;   // dla jakich map to ma sens, a dla jakich nie ma? wadą jest, że trzeba zejść na niższy poziom abstrakcji na potrzeby indeksowania

    public RectangularMap(int width, int height) {
        lowerLeftBound = new Vector2d(0,0);
        upperRightBound = new Vector2d(width-1, height-1);
        map = new Animal[width][height];
    }

    public String toString() {
        return new MapVisualizer(this).draw(lowerLeftBound, upperRightBound);
    }

    @Override
    public Object objectAt(Vector2d position) {
        return map[position.x][position.y];
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return (map[position.x][position.y] != null);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position.follows(lowerLeftBound) && position.precedes(upperRightBound)){
            return !isOccupied(position);
        } else return false;
    }

    @Override
    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            map[position.x][position.y] = animal;
            return true;
        } else return false;
    }

    @Override
    public boolean move(Vector2d oldPosition, Vector2d newPosition){
        if (canMoveTo(newPosition)) {
            map[newPosition.x][newPosition.y] = map[oldPosition.x][oldPosition.y];
            map[oldPosition.x][oldPosition.y] = null;
            return true;
        } else return false;
    }

}