package agh.ics.oop;

import java.util.Objects;

import static java.lang.System.out;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);

    public String toString(){
        return "Position: " + this.position.toString() + ", Direction: " + this.direction.toString();
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public boolean isOrientedAt(MapDirection direction) {
        return this.direction == direction;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case FORWARD -> {
                Vector2d newPosition = this.position.add(this.direction.toUnitVector());
                if (newPosition.follows(new Vector2d(0,0)) && newPosition.precedes(new Vector2d(4,4))) {
                    this.position = newPosition;
                }
            }
            case RIGHT -> this.direction = this.direction.next();
            case BACKWARD -> {
                Vector2d newPosition = this.position.subtract(this.direction.toUnitVector());
                if (newPosition.follows(new Vector2d(0,0)) && newPosition.precedes(new Vector2d(4,4))) {
                    this.position = newPosition;
                }
            }
            case LEFT -> this.direction = this.direction.previous();
            default -> {}
        }
    }
}
