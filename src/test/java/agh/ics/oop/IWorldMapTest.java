package agh.ics.oop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IWorldMapTest {

    @Test
    public void grassFieldPlaceTest() {
        IWorldMap map = new GrassField(10);
        Vector2d position = new Vector2d(2, 2);

        Animal animal = new Animal(map, position);
        assertTrue(map.place(animal));
        assertTrue(map.isOccupied(position));
        assertEquals(map.objectAt(position), animal);

        Animal animal2 = new Animal(map, position);
        assertFalse(map.place(animal2));
        assertNotEquals(map.objectAt(position), animal2);

        Vector2d huge_position_indices = new Vector2d(1563756, 1563756);
        Vector2d negative_position_indices = new Vector2d(-1563756, -1563756);
        assertTrue(map.place(new Animal(map, huge_position_indices)));
        assertTrue(map.place(new Animal(map, negative_position_indices)));

        assertTrue(map.isOccupied(huge_position_indices));
        assertTrue(map.isOccupied(negative_position_indices));
    }

    @Test
    public void rectangularPlaceTest() {
        IWorldMap map = new RectangularMap(2, 2);
        Vector2d position = new Vector2d(1, 1);

        Animal animal = new Animal(map, position);
        assertTrue(map.place(animal));
        assertTrue(map.isOccupied(position));
        assertEquals(map.objectAt(position), animal);

        Animal animal2 = new Animal(map, position);
        assertFalse(map.place(animal2));
        assertEquals(map.objectAt(position), animal);
    }

    @Test
    public void canMoveToTest() {
        IWorldMap grassFieldMap = new GrassField(10);

        assertTrue(grassFieldMap.canMoveTo(new Vector2d(0, 0)));
        assertTrue(grassFieldMap.canMoveTo(new Vector2d(-1563756, -1563756)));
        assertTrue(grassFieldMap.canMoveTo(new Vector2d(1563756, 1563756)));

        Animal animal = new Animal(grassFieldMap, new Vector2d(0, 0));
        grassFieldMap.place(animal);
        assertFalse(grassFieldMap.canMoveTo(new Vector2d(0, 0)));

        //Rectangular map has strict boundaries so cannot have negative positions
        IWorldMap rectMap = new RectangularMap(10, 10);
        assertTrue(rectMap.canMoveTo(new Vector2d(0, 0)));
        assertTrue(rectMap.canMoveTo(new Vector2d(9, 9)));
        assertFalse(rectMap.canMoveTo(new Vector2d(10, 9)));
        assertFalse(rectMap.canMoveTo(new Vector2d(-1, 4)));
        assertFalse(rectMap.canMoveTo(new Vector2d(99, 99)));
    }

    @Test
    public void grassFieldIsAnimalInsteadOfGrassTest() {
        IWorldMap map = new GrassField(1);

        int counter = 0;
        Vector2d grassPosition = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                if (map.objectAt(new Vector2d(i, j)) instanceof Grass) {
                    counter++;
                    grassPosition = new Vector2d(i, j);
                }
            }
        }
        assertEquals(1, counter);

        assertTrue(map.isOccupied(grassPosition));
        assertTrue(map.objectAt(grassPosition) instanceof Grass);

        assertTrue(map.canMoveTo(grassPosition));
        Animal animal = new Animal(map, grassPosition);
        assertTrue(map.place(animal));

        assertTrue(map.isOccupied(grassPosition));
        assertEquals(map.objectAt(grassPosition), animal);

        animal.move(MoveDirection.FORWARD);
        assertTrue(map.isOccupied(grassPosition));
        assertTrue(map.objectAt(grassPosition) instanceof Grass);
        assertTrue(map.canMoveTo(grassPosition));
    }

}