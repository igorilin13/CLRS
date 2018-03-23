package igorilin13.com.github.main.geometry;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class VectorsIntersection {
    /**
     * Assumes that no input segment is vertical and no three input segments intersect at a single point.
     */
    public static boolean anyIntersect(Set<Vector> vectors) {
        TotalPreorder T = new TotalPreorder();
        for (EventPoint eventPoint : createEventPoints(vectors)) {
            Vector vector = eventPoint.vector;
            if (eventPoint.isLeftPoint) {
                T.insert(vector);
                if (T.intersectionFoundAfterModify) {
                    return true;
                }

                Vector above = T.above(vector);
                if (above != null && vector.intersects(above)) {
                    return true;
                }

                Vector below = T.below(vector);
                if (below != null && vector.intersects(below)) {
                    return true;
                }
            } else {
                Vector above = T.above(vector);
                Vector below = T.below(vector);
                if (above != null && below != null && above.intersects(below)) {
                    return true;
                }
                T.delete(vector);
                if (T.intersectionFoundAfterModify) {
                    return true;
                }
            }
        }

        return false;
    }

    private static List<EventPoint> createEventPoints(Set<Vector> vectors) {
        List<EventPoint> eventPoints = new ArrayList<>();
        for (Vector vector : vectors) {
            Point start = vector.getStartPoint();
            Point end = vector.getEndPoint();
            eventPoints.add(new EventPoint(start, vector, start.getX() < end.getX()));
            eventPoints.add(new EventPoint(end, vector, start.getX() > end.getX()));
        }
        eventPoints.sort((first, second) -> {
            if (first.point.getX() < second.point.getX()) {
                return -1;
            }
            if (first.point.getX() > second.point.getX()) {
                return 1;
            }
            if (first.isLeftPoint != second.isLeftPoint) {
                return first.isLeftPoint ? -1 : 1;
            }
            return Double.compare(first.point.getY(), second.point.getY());
        });
        return eventPoints;
    }

    private static class EventPoint {
        private final Point point;
        private final Vector vector;
        private final boolean isLeftPoint;

        private EventPoint(Point point, Vector vector, boolean isLeftPoint) {
            this.point = point;
            this.vector = vector;
            this.isLeftPoint = isLeftPoint;
        }
    }

    private static class TotalPreorder {
        private boolean intersectionFoundAfterModify;

        private final TreeSet<Vector> current = new TreeSet<>((first, second) -> {
            if (first.equals(second)) {
                return 0;
            }
            if (first.intersects(second)) {
                intersectionFoundAfterModify = true;
                return 1;
            }
            Point firstLeftPoint = first.getLeftPoint();
            Point firstRightPoint = first.getRightPoint();
            Point secondLeftPoint = second.getLeftPoint();

            Direction angleDirection = GeometryUtils.angleDirection(firstLeftPoint,
                    firstRightPoint, secondLeftPoint);
            if (angleDirection == Direction.COLLINEAR) {
                return secondLeftPoint.getY() > Math.max(firstLeftPoint.getY(), firstRightPoint.getY()) ? -1 : 1;
            }

            return angleDirection == Direction.COUNTERCLOCKWISE ? -1 : 1;
        });

        private void insert(Vector v) {
            current.add(v);
        }

        private void delete(Vector v) {
            current.remove(v);
        }

        @Nullable
        private Vector above(Vector v) {
            return current.higher(v);
        }

        @Nullable
        private Vector below(Vector v) {
            return current.lower(v);
        }
    }
}
