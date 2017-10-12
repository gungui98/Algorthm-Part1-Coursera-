import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

/**
 * Created by GUNGUI on 10/5/2017.
 */
public class PointSET {
    private SET<Point2D> setOfPoint;

    public PointSET() {
        setOfPoint = new SET<>();
    }                               // construct an empty set of points

    public boolean isEmpty() {
        return setOfPoint.size() == 0;
    }                      // is the set empty?

    public int size() {
        return setOfPoint.size();
    }                         // number of points in the set

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException("Null 2D Point is not acceptable");
        }
        if (!contains(p))
            setOfPoint.add(p);
    }              // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException("Null 2D Point is not acceptable");
        }
        return setOfPoint.contains(p);
    }            // does the set contain point p?

    public void draw() {
        for (Point2D i : setOfPoint) {
            i.draw();
        }
    }                         // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException("Null rectangle is not acceptable");
        }
        ArrayList<Point2D> inside = new ArrayList<>();
        for (Point2D i : setOfPoint) {
            if (i.x() <= rect.xmax() && i.x() >= rect.xmin())
                if (i.y() <= rect.ymax() && i.y() >= rect.ymin())
                    inside.add(i);
        }
        return inside;
    }

    ;             // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException("Null 2D Point is not acceptable");
        }
        if (setOfPoint.isEmpty())
            return null;
        Point2D min = setOfPoint.min();
        double mindinc = Double.POSITIVE_INFINITY;
        for (Point2D i : setOfPoint) {
            if (p.distanceTo(i) < mindinc) {
                min = i;
                mindinc = p.distanceTo(i);
            }
        }
        return min;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

    }                  // unit testing of the methods (optional)
}