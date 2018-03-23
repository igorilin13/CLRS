package igorilin13.com.github.test.geometry;

import igorilin13.com.github.main.geometry.Direction;
import igorilin13.com.github.main.geometry.Vector;
import org.junit.Test;

import static org.junit.Assert.*;

public class VectorTest {

    @Test
    public void testDirectionFrom() {
        Vector vector = new Vector(2, 2, 4, 5);

        Vector counterclockwise = new Vector(7, 4, 10, 3);
        Vector clockwise = new Vector(4, 6, 1, 8);
        Vector colinear = new Vector(-1, -3, -5, -9);

        assertEquals(Direction.COUNTERCLOCKWISE, vector.directionFrom(counterclockwise));
        assertEquals(Direction.CLOCKWISE, vector.directionFrom(clockwise));
        assertEquals(Direction.COLLINEAR, vector.directionFrom(colinear));
    }

    @Test
    public void testIntersects() {
        Vector vector = new Vector(-8, 2, -5, 5);

        assertTrue(vector.intersects(new Vector(-5, 2, -6, 6)));
        assertFalse(vector.intersects(new Vector(6, 9, 9, 6)));

        assertTrue(vector.intersects(new Vector(-7, 3, -5, 1)));
        assertFalse(vector.intersects(new Vector(0, 10, 1, 7)));

        assertTrue(vector.intersects(new Vector(-7, 7, -6, 4)));
        assertFalse(vector.intersects(new Vector(-5, 9, -3, 7)));

        assertTrue(vector.intersects(new Vector(-6, 6, 1, -1)));
        assertFalse(vector.intersects(new Vector(-4, 8, -1, 5)));

        assertTrue(vector.intersects(new Vector(-9, 3, -7, 1)));
        assertFalse(vector.intersects(new Vector(-10, 2, -1, -1)));
    }
}
