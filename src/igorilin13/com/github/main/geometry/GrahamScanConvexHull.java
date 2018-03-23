package igorilin13.com.github.main.geometry;

import igorilin13.com.github.main.datastructures.Stack;

import java.util.*;

public class GrahamScanConvexHull {
    private final Set<Point> inputPoints;
    private final List<Point> result;

    public GrahamScanConvexHull(Set<Point> inputPoints) {
        if (inputPoints.size() < 3) {
            throw new IllegalArgumentException("Illegal number of points: should be at least 3");
        }
        this.inputPoints = inputPoints;
        result = run();
    }

    private List<Point> run() {
        Point origin = findOrigin();

        List<Point> remainingPoints = preProcessRemainingPoints(origin);
        if (remainingPoints.size() < 3) {
            throw new IllegalArgumentException("At least 2 points (excluding origin point) should be not collinear");
        }

        Stack<Point> stack = new Stack<>();
        stack.push(origin);
        stack.push(remainingPoints.get(0));
        stack.push(remainingPoints.get(1));
        for (int i = 2; i < remainingPoints.size(); i++) {
            Point point = remainingPoints.get(i);
            while (GeometryUtils.angleDirection(stack.peekFromTop(1), stack.peek(), point)
                    != Direction.COUNTERCLOCKWISE) {
                stack.pop();
            }
            stack.push(point);
        }
        return stack.toList();
    }

    private Point findOrigin() {
        return Collections.min(inputPoints,
                Comparator.comparingDouble(Point::getY).thenComparingDouble(Point::getX));
    }

    private List<Point> preProcessRemainingPoints(Point origin) {
        List<Point> result = new ArrayList<>(inputPoints);

        result.remove(origin);
        result.sort(new OriginPointComparator(origin));

        int i = 1;
        while (i < result.size()) {
            Point prev = result.get(i - 1);
            Point current = result.get(i);
            if (GeometryUtils.angleDirection(origin, prev, current) == Direction.COLLINEAR) {
                result.remove(i - 1);
            } else {
                i++;
            }
        }

        return result;
    }

    public List<Point> getResult() {
        return result;
    }

    private static class OriginPointComparator implements Comparator<Point> {
        private final Point origin;

        private OriginPointComparator(Point origin) {
            this.origin = origin;
        }

        @Override
        public int compare(Point first, Point second) {
            Direction angleTurn = GeometryUtils.angleDirection(origin, first, second);
            switch (angleTurn) {
                case COUNTERCLOCKWISE:
                    return -1;
                case CLOCKWISE:
                    return 1;
                case COLLINEAR:
                default:
                    double firstDistance = GeometryUtils.squareDistance(origin, first);
                    double secondDistance = GeometryUtils.squareDistance(origin, second);
                    return Double.compare(firstDistance, secondDistance);
            }
        }
    }
}
