package datastructure;

/**
 * A {@link Point} represents a <em>city</em> when this class used
 * in a <em>Traveling salesman problem</em> or simply a dot in the
 * area, that has an x and y coordinates.
 *
 * @author Sarunas Sarakojis
 */
public class Point implements Comparable<Point> {

    private final Integer xCoordinate;
    private final Integer yCoordinate;

    public Point(Integer xCoordinate, Integer yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public Integer getXCoordinate() {
        return xCoordinate;
    }

    public Integer getyCoordinate() {
        return yCoordinate;
    }

    public double getDistanceToPoint(Point anotherPoint) {
        double xDifference = xCoordinate - anotherPoint.xCoordinate;
        double yDifference = yCoordinate - anotherPoint.yCoordinate;

        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }

    @Override
    public int compareTo(Point o) {
        return xCoordinate.compareTo(o.xCoordinate);
    }

    @Override
    public String toString() {
        return "x: " + xCoordinate + ", y: " + yCoordinate;
    }
}
