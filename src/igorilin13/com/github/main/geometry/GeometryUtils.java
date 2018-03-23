package igorilin13.com.github.main.geometry;

public class GeometryUtils {
    public static Direction angleDirection(Point p0, Point p1, Point p2) {
        Vector p0p2 = new Vector(p0, p2);
        Vector p0p1 = new Vector(p0, p1);
        return p0p2.directionFrom(p0p1);
    }

    public static double squareDistance(Point origin, Point point) {
        double x = point.getX() - origin.getX();
        double y = point.getY() - origin.getY();
        return x * x + y * y;
    }
}
