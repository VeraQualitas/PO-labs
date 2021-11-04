package agh.ics.oop;

import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalMoveTest {
    private final IWorldMap map = new RectangularMap(4, 4);
    @Test
    public void directionTest1() {
        Animal animal = new Animal(this.map);
        MoveDirection[] directions = {
                MoveDirection.LEFT,
                MoveDirection.LEFT,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD
        };
        MapDirection expected = MapDirection.NORTH;

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isOrientedAt(expected));
    }

    @Test
    public void directionTest2() {
        Animal animal = new Animal(this.map);
        MoveDirection[] directions = {
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD,
                MoveDirection.RIGHT,
        };
        MapDirection expected = MapDirection.SOUTH;

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isOrientedAt(expected));
    }

    @Test
    public void directionTest3() {
        Animal animal = new Animal(this.map);
        MoveDirection[] directions = {
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.RIGHT,
        };
        MapDirection expected = MapDirection.WEST;

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isOrientedAt(expected));
    }

    @Test
    public void positionTest1() {
        Animal animal = new Animal(this.map);

        MoveDirection[] directions = {
                MoveDirection.FORWARD,
                MoveDirection.BACKWARD,
                MoveDirection.FORWARD
        };
        Vector2d expected = new Vector2d(2,3);

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isAt(expected));
    }

    @Test
    public void positionTest2() {
        Animal animal = new Animal(this.map);

        MoveDirection[] directions = {
                MoveDirection.FORWARD, // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.BACKWARD,// (1, 3)
                MoveDirection.RIGHT,   // (1, 3)
                MoveDirection.FORWARD, // (1, 2)
                MoveDirection.LEFT,    // (1, 2)
                MoveDirection.BACKWARD // (0, 2)
        };
        Vector2d expected = new Vector2d(0,2);

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isAt(expected));
    }

    @Test
    public void positionTest3() {
        Animal animal = new Animal(this.map);

        MoveDirection[] directions = {
                MoveDirection.FORWARD, // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.BACKWARD,// (1, 3)
                MoveDirection.RIGHT,   // (1, 3)
                MoveDirection.FORWARD, // (1, 2)
                MoveDirection.FORWARD, // (1, 1)
                MoveDirection.FORWARD, // (1, 0)
                MoveDirection.LEFT,    // (1, 0)
                MoveDirection.BACKWARD // (0, 0)
        };
        Vector2d expected = new Vector2d(0,0);

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isAt(expected));
    }

    @Test
    public void boundaryTest1() {
        Animal animal = new Animal(this.map);

        MoveDirection[] directions = {
                MoveDirection.FORWARD, // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.BACKWARD,// (1, 3)
                MoveDirection.BACKWARD,// (0, 3)
                MoveDirection.BACKWARD,// (0, 3)
                MoveDirection.BACKWARD,// (0, 3)
        };
        Vector2d expected = new Vector2d(0,3);

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isAt(expected));
    }

    @Test
    public void boundaryTest2() {
        Animal animal = new Animal(this.map);

        MoveDirection[] directions = {
                MoveDirection.FORWARD, // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.BACKWARD,// (2, 2)
                MoveDirection.BACKWARD,// (2, 1)
                MoveDirection.BACKWARD,// (2, 0)
                MoveDirection.BACKWARD,// (2, 0)
        };
        Vector2d expected = new Vector2d(2,0);

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isAt(expected));
    }

    @Test
    public void boundaryTest3() {
        Animal animal = new Animal(this.map);

        MoveDirection[] directions = {
                MoveDirection.FORWARD, // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.FORWARD,// (2, 3)
                MoveDirection.FORWARD,// (2, 3)
        };
        Vector2d expected = new Vector2d(2,3);

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isAt(expected));
    }

    @Test
    public void boundaryTest4() {
        Animal animal = new Animal(this.map);

        MoveDirection[] directions = {
                MoveDirection.FORWARD, // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.RIGHT,   // (2, 3)
                MoveDirection.FORWARD,// (3, 3)
                MoveDirection.FORWARD,// (3, 3)
                MoveDirection.FORWARD,// (3, 3)
        };
        Vector2d expected = new Vector2d(3,3);

        for (MoveDirection direction: directions) animal.move(direction);
        assertTrue(animal.isAt(expected));
    }

    @Test
    public void parseTest1() {
        String[] arguments = {"f", "forward", "r", "sth", "right", "b", "backward", "l", "left", "sth"};
        MoveDirection[] directions = {
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.BACKWARD,
                MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.LEFT,
        };

        assertArrayEquals(OptionsParser.parse(arguments), directions);
    }
}