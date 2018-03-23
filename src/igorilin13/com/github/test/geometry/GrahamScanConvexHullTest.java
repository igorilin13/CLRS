package igorilin13.com.github.test.geometry;

import igorilin13.com.github.main.geometry.GrahamScanConvexHull;
import igorilin13.com.github.main.geometry.Point;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GrahamScanConvexHullTest {
    private static final Point[] POINTS = {
            Point.of(-1, -2),
            Point.of(-3, 2),
            Point.of(7, -1),
            Point.of(6, 1),
            Point.of(9, 3),
            Point.of(7, 3),
            Point.of(6, 6),
            Point.of(4, 7),
            Point.of(3, 6),
            Point.of(2, 4),
            Point.of(1, 5),
            Point.of(-1, 5),
            Point.of(2, 12),
    };

    private static final List<Point> EXPECTED_CONVEX_HULL = Arrays.asList(
            Point.of(-1, -2),
            Point.of(7, -1),
            Point.of(9, 3),
            Point.of(2, 12),
            Point.of(-3, 2)
    );

    private final Set<Point> pointSet = new HashSet<>();

    @Before
    public void setUp() {
        pointSet.addAll(Arrays.asList(POINTS));
    }

    @Test
    public void testGrahamScan() {
        assertEquals(EXPECTED_CONVEX_HULL, new GrahamScanConvexHull(pointSet).getResult());
    }
}
