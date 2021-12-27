package agh.ics.oop;

import agh.ics.oop.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
//    @Test
//    public void equalsTest(){
//        assertNotEquals("sth", new Vector2d(0, 1));
//        assertEquals(new Vector2d(0, 1), new Vector2d(0, 1));
//        assertNotEquals(new Vector2d(1, 0), new Vector2d(0, 1));
//    }
//
//    @Test
//    public void toStringTest(){
//        assertEquals("(0, 1)", new Vector2d(0, 1).toString());
//    }
//
//    @Test
//    public void precedesTest(){
//        assertTrue( new Vector2d(1, 0).precedes( new Vector2d(2, 1) ));
//        assertTrue( new Vector2d(1, 1).precedes( new Vector2d(1, 1) ));
//        assertFalse( new Vector2d(2, 1).precedes( new Vector2d(1, 1) ));
//        assertFalse( new Vector2d(1, 0).precedes( new Vector2d(0, 1) ));
//        assertTrue( new Vector2d(10, 0).precedes( new Vector2d(20, 10) ));
//        assertFalse( new Vector2d(5, 5).precedes( new Vector2d(2, 8) ));
//    }
//
//    @Test
//    public void followsTest(){
//        assertTrue( new Vector2d(2, 1).follows( new Vector2d(1, 0) ));
//        assertTrue( new Vector2d(1, 1).follows( new Vector2d(1, 1) ));
//        assertFalse( new Vector2d(1, 1).follows( new Vector2d(2, 1) ));
//        assertFalse( new Vector2d(0, 1).follows( new Vector2d(1, 0) ));
//        assertTrue( new Vector2d(20, 10).follows( new Vector2d(10, 0) ));
//        assertFalse( new Vector2d(2, 8).follows( new Vector2d(5, 5) ));
//    }
//
//    @Test
//    public void upperRight(){
//        Vector2d vector1 = new Vector2d(0, 1);
//        Vector2d vector2 = new Vector2d(2, 1);
//
//        assertEquals(vector1.upperRight(vector2), new Vector2d(2, 1));
//        assertNotEquals(vector1.upperRight(vector2), new Vector2d(0, 1));
//    }
//
//    @Test
//    public void lowerLeft(){
//        Vector2d vector1 = new Vector2d(0, 1);
//        Vector2d vector2 = new Vector2d(2, 1);
//
//        assertEquals(vector1.lowerLeft(vector2), new Vector2d(0, 1));
//        assertNotEquals(vector1.lowerLeft(vector2), new Vector2d(2, 1));
//    }
//
//    @Test
//    public void addTest(){
//        Vector2d vector1 = new Vector2d(1, 2);
//        Vector2d vector2 = new Vector2d(4, 8);
//
//        assertEquals(vector1.add(vector2), new Vector2d(5, 10));
//        assertEquals(vector1.add(vector1), new Vector2d(2, 4));
//
//        assertNotEquals(vector2.add(vector2), new Vector2d(0, 0));
//    }
//
//    @Test
//    public void subtractTest(){
//        Vector2d vector1 = new Vector2d(1, 2);
//        Vector2d vector2 = new Vector2d(4, 8);
//
//        assertEquals(vector1.subtract(vector2), new Vector2d(-3, -6));
//        assertEquals(vector1.subtract(vector1), new Vector2d(0, 0));
//
//        assertNotEquals(vector1.subtract(vector2), new Vector2d(0, 0));
//    }
//
//    @Test
//    public void oppositeTest(){
//        Vector2d vector1 = new Vector2d(1, 2);
//        Vector2d vector2 = new Vector2d(4, 8);
//
//        assertEquals(vector1.opposite(), new Vector2d(-1, -2));
//        assertEquals(vector2.opposite(), new Vector2d(-4, -8));
//
//        assertNotEquals(vector2.opposite(), new Vector2d(4, 8));
//    }
}
