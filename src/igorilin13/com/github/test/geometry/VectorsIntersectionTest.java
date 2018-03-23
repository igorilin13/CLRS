package igorilin13.com.github.test.geometry;

import igorilin13.com.github.main.geometry.Vector;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static igorilin13.com.github.main.geometry.VectorsIntersection.anyIntersect;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VectorsIntersectionTest {
    @Test
    public void testAnyIntersect() {
        Set<Vector> vectors = createNonIntersectingVectors();
        assertFalse(anyIntersect(vectors));

        Vector v = new Vector(4, 2, 2, 5);
        vectors.add(v);
        assertTrue(anyIntersect(vectors));
        vectors.remove(v);

        vectors.add(new Vector(4, 5, 6, 3));
        assertTrue(anyIntersect(vectors));
    }

    private Set<Vector> createNonIntersectingVectors() {
        Set<Vector> vectors = new HashSet<>();

        vectors.add(new Vector(-8, 2, -5, 5));
        vectors.add(new Vector(-1, -3, -5, -9));
        vectors.add(new Vector(2, 2, 4, 5));
        vectors.add(new Vector(4, 6, 1, 8));
        vectors.add(new Vector(7, 4, 10, 3));
        vectors.add(new Vector(6, 9, 9, 6));
        vectors.add(new Vector(2, 2, 4, 5));

        return vectors;
    }
}
