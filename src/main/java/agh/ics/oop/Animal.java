package agh.ics.oop;

import java.util.Objects;

import static java.lang.System.out;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    // todo

    public Animal(IWorldMap map) {
        this.map = map;
        this.position = new Vector2d(2, 2);
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
    }

    public String toString() {
        return switch (this.direction) {
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public boolean isOrientedAt(MapDirection direction) {
        return this.direction == direction;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case FORWARD -> {
                Vector2d newPosition = this.position.add(this.direction.toUnitVector());
                if (this.map.canMoveTo(newPosition)) {
                    this.position = newPosition;

                }
            }
            case RIGHT -> this.direction = this.direction.next();
            case BACKWARD -> {
                Vector2d newPosition = this.position.subtract(this.direction.toUnitVector());
                if (this.map.canMoveTo(newPosition)) {
                    this.position = newPosition;
                }
            }
            case LEFT -> this.direction = this.direction.previous();
            default -> {
            }
        }
    }
    public Vector2d getPosition() {
        return position;
    }
}
