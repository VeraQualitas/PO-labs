import agh.ics.oop.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class Vector2dTest {
    @Test
    public void equalsTest(){
        assertFalse(new Vector2d(0,1).equals("sth"));
        assertTrue(new Vector2d(0,1).equals( new Vector2d(0, 1)) );
        assertFalse( new Vector2d(1,0).equals( new Vector2d(0, 1)) );
    }

    @Test
    public void toStringTest(){
        assertTrue( new Vector2d(0, 1).toString().equals("(0, 1)"));
    }

    @Test
    public void precedesTest(){
        assertTrue( new Vector2d(1, 0).precedes( new Vector2d(2, 1) ));
        assertTrue( new Vector2d(1, 1).precedes( new Vector2d(1, 1) ));
        assertFalse( new Vector2d(2, 1).precedes( new Vector2d(1, 1) ));
    }

    @Test
    public void followsTest(){
        assertTrue( new Vector2d(2, 2).follows( new Vector2d(1, 1) ));
        assertTrue( new Vector2d(1, 1).follows( new Vector2d(1, 1) ));
        assertFalse(new Vector2d(1, 2).follows( new Vector2d(2, 2) ));
    }

    @Test
    public void upperRight(){
        Vector2d vector1 = new Vector2d(0, 1);
        Vector2d vector2 = new Vector2d(2, 1);

        assertTrue(vector1.upperRight(vector2).equals( new Vector2d(2, 1) ));
        assertFalse(vector1.upperRight(vector2).equals( new Vector2d(0, 1) ));
    }

    @Test
    public void lowerLeft(){
        Vector2d vector1 = new Vector2d(0, 1);
        Vector2d vector2 = new Vector2d(2, 1);

        assertTrue(vector1.lowerLeft(vector2).equals( new Vector2d(0, 1) ));
        assertFalse(vector1.lowerLeft(vector2).equals( new Vector2d(2, 1) ));
    }

    @Test
    public void addTest(){
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(4, 8);

        assertTrue(vector1.add(vector2).equals( new Vector2d(5, 10)));
        assertTrue(vector1.add(vector1).equals( new Vector2d(2, 4)));

        assertFalse(vector2.add(vector2).equals( new Vector2d(0, 0) ));
    }

    @Test
    public void subtractTest(){
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(4, 8);

        assertTrue(vector1.subtract(vector2).equals( new Vector2d(-3, -6) ));
        assertTrue(vector1.subtract(vector1).equals( new Vector2d(0, 0) ));

        assertFalse(vector1.subtract(vector2).equals( new Vector2d(0, 0) ));
    }

    @Test
    public void oppositeTest(){
        Vector2d vector1 = new Vector2d(1, 2);
        Vector2d vector2 = new Vector2d(4, 8);

        assertTrue(vector1.opposite().equals( new Vector2d(-1, -2) ));
        assertTrue(vector2.opposite().equals( new Vector2d(-4, -8) ));

        assertFalse(vector2.opposite().equals( new Vector2d(4, 8) ));
    }

}
