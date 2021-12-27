package agh.ics.oop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationEngineTest {
//    @Test
//    public void test1(){
//        String[] args = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
//        MoveDirection[] directions = OptionsParser.parse(args);
//        IWorldMap map = new RectangularMap(10, 5);
//        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
//
//        IEngine engine = new SimulationEngine(directions, map, positions);
//        engine.run();
//
//        assertTrue(map.objectAt(new Vector2d(2,0)) instanceof Animal);
//        assertTrue(map.objectAt(new Vector2d(3,4)) instanceof Animal);
//
//        assertNull(map.objectAt(new Vector2d(3, 3)));
//        assertNull(map.objectAt(new Vector2d(2, 2)));
//        assertNull(map.objectAt(new Vector2d(0, 1)));
//
//
//        Animal animal1 = (Animal) map.objectAt(new Vector2d(2,0));
//        assertTrue(animal1.isOrientedAt(MapDirection.SOUTH));
//
//        Animal animal2 = (Animal) map.objectAt(new Vector2d(3,4));
//        assertTrue(animal2.isOrientedAt(MapDirection.NORTH));
//    }
//
//    @Test
//    public void test2(){
//        String[] args = {"l", "f", "f", "r", "r", "f", "f", "r", "r", "f", "f", "r", "r", "f", "f"};
//        MoveDirection[] directions = OptionsParser.parse(args);
//        IWorldMap map = new RectangularMap(2, 2);
//        Vector2d[] positions = { new Vector2d(1,0), new Vector2d(0,0) };
//
//
//
//        IEngine engine = new SimulationEngine(directions, map, positions);
//        engine.run();
//
//        assertTrue(map.objectAt(new Vector2d(1,0)) instanceof Animal);
//        assertTrue(map.objectAt(new Vector2d(0,0)) instanceof Animal);
//
//        assertNull(map.objectAt(new Vector2d(1, 1)));
//        assertNull(map.objectAt(new Vector2d(0, 1)));
//
//        Animal animal1 = (Animal) map.objectAt(new Vector2d(1,0));
//        assertTrue(animal1.isOrientedAt(MapDirection.SOUTH));
//        Animal animal2 = (Animal) map.objectAt(new Vector2d(0,0));
//        assertTrue(animal2.isOrientedAt(MapDirection.WEST));
//    }
//
//    @Test
//    public void test3(){
//        String[] args = {"b", "b", "b", "r", "r", "f", "f", "f"};
//        MoveDirection[] directions = OptionsParser.parse(args);
//        IWorldMap map = new RectangularMap(4, 4);
//        Vector2d[] positions = { new Vector2d(2,3) , new Vector2d(1,3), new Vector2d(3,3) };
//
//        IEngine engine = new SimulationEngine(directions, map, positions);
//        engine.run();
//        System.out.println(map);
//
//        assertTrue(map.objectAt(new Vector2d(3,2)) instanceof Animal);
//        assertTrue(map.objectAt(new Vector2d(2,2)) instanceof Animal);
//        assertTrue(map.objectAt(new Vector2d(3,3)) instanceof Animal);
//
//        assertNull(map.objectAt(new Vector2d(1, 2)));
//        assertNull(map.objectAt(new Vector2d(2, 3)));
//        assertNull(map.objectAt(new Vector2d(1, 3)));
//
//        Animal animal1 = (Animal) map.objectAt(new Vector2d(3,3));
//        Animal animal2 = (Animal) map.objectAt(new Vector2d(2,2));
//        Animal animal3 = (Animal) map.objectAt(new Vector2d(3,2));
//        assertTrue(animal1.isOrientedAt(MapDirection.NORTH));
//        assertTrue(animal2.isOrientedAt(MapDirection.EAST));
//        assertTrue(animal3.isOrientedAt(MapDirection.EAST));
//
//    }
}
