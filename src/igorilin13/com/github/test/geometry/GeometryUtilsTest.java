package igorilin13.com.github.test.geometry;

import igorilin13.com.github.main.geometry.Direction;
import igorilin13.com.github.main.geometry.Point;
import org.junit.Test;

import static igorilin13.com.github.main.geometry.GeometryUtils.angleDirection;
import static org.junit.Assert.assertEquals;

public class GeometryUtilsTest {
    @Test
    public void testAngleDirection() {
        Point start = Point.of(2, 2);
        Point middle = Point.of(4, 5);

        assertEquals(Direction.CLOCKWISE, angleDirection(start, middle, Point.of(6, 3)));
        assertEquals(Direction.COUNTERCLOCKWISE, angleDirection(start, middle, Point.of(2, 4)));
        assertEquals(Direction.COLLINEAR, angleDirection(start, middle, Point.of(6, 8)));
    }
}
