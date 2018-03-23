package igorilin13.com.github.main.geometry;

public class Vector {
    private final Point start;
    private final Point end;
    private final double x;
    private final double y;

    public Vector(double x0, double y0, double x1, double y1) {
        this(Point.of(x0, y0), Point.of(x1, y1));
    }

    Vector(Point start, Point end) {
        this.start = start;
        this.end = end;
        x = end.getX() - start.getX();
        y = end.getY() - start.getY();
    }

    public Direction directionFrom(Vector oth) {
        double crossProduct = crossProduct(oth);
        if (crossProduct > 0) {
            return Direction.CLOCKWISE;
        } else if (crossProduct == 0) {
            return Direction.COLLINEAR;
        } else {
            return Direction.COUNTERCLOCKWISE;
        }
    }

    private double crossProduct(Vector oth) {
        return x * oth.y - oth.x * y;
    }

    public boolean intersects(Vector oth) {
        Point p1 = start;
        Point p2 = end;
        Point p3 = oth.start;
        Point p4 = oth.end;

        Direction d1 = GeometryUtils.angleDirection(p3, p4, p1);
        Direction d2 = GeometryUtils.angleDirection(p3, p4, p2);
        Direction d3 = GeometryUtils.angleDirection(p1, p2, p3);
        Direction d4 = GeometryUtils.angleDirection(p1, p2, p4);

        if (d1 == Direction.COLLINEAR && isOnSegment(p3, p4, p1)) {
            return true;
        }

        if (d2 == Direction.COLLINEAR && isOnSegment(p3, p4, p2)) {
            return true;
        }

        if (d3 == Direction.COLLINEAR && isOnSegment(p1, p2, p3)) {
            return true;
        }

        if (d4 == Direction.COLLINEAR && isOnSegment(p1, p2, p4)) {
            return true;
        }

        return d1 != d2 && d3 != d4;
    }

    private boolean isOnSegment(Point p0, Point p1, Point p2) {
        double x0 = p0.getX();
        double y0 = p0.getY();

        double x1 = p1.getX();
        double y1 = p1.getY();

        double x2 = p2.getX();
        double y2 = p2.getY();

        return Math.min(x0, x1) <= x2 && x2 <= Math.max(x0, x1)
                && Math.min(y0, y1) <= y2 && y2 <= Math.max(y0, y1);
    }

    public Point getStartPoint() {
        return start;
    }

    public Point getEndPoint() {
        return end;
    }

    public Point getLeftPoint() {
        return start.getX() < end.getX() ? start : end;
    }

    public Point getRightPoint() {
        return start.getX() > end.getX() ? start : end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Vector vector = (Vector) o;

        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0
                && start.equals(vector.start) && end.equals(vector.end);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = start.hashCode();
        result = 31 * result + end.hashCode();
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
