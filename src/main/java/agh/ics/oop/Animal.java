package agh.ics.oop;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.System.out;

public class Animal implements IMapElement{
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    private final ArrayList<IPositionChangeObserver> observers;
    // todo

    public Animal(IWorldMap map) {
        this.map = map;
        this.position = new Vector2d(2, 2);
        this.observers = new ArrayList<>();
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
        this.observers = new ArrayList<>();
    }

    @Override
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
                    this.positionChanged(this.position, newPosition);
                    this.position = newPosition;
                }
            }
            case RIGHT -> this.direction = this.direction.next();
            case BACKWARD -> {
                Vector2d newPosition = this.position.subtract(this.direction.toUnitVector());
                if (this.map.canMoveTo(newPosition)) {
                    this.positionChanged(this.position, newPosition);
                    this.position = newPosition;
                }
            }
            case LEFT -> this.direction = this.direction.previous();
            default -> {
            }
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : this.observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }
}
