
import java.util.*;

/**
 * Created by GUNGUI on 28/9/2017.
 */
public class FastCollinearPoints {
    private ArrayList<LineSegment> ls;
    public FastCollinearPoints(Point[] points) {
        if(points == null)
            throw new java.lang.IllegalArgumentException();
        for(Point i:points)
            if(i==null)
                throw new java.lang.IllegalArgumentException();
        check(points);
        int numOfpoint;
        boolean b;
        Point[] copyPoints = points.clone();
        ls = new ArrayList<>();
        Point[] iter = points.clone();
        List<Point> listOfPoint;
        for (Point i : iter) {
            Arrays.sort(copyPoints, i.slopeOrder());
            for (int j = 0; j < copyPoints.length ; j++) {
                listOfPoint = new ArrayList<>();
                listOfPoint.add(i);
                listOfPoint.add(copyPoints[j]);
                numOfpoint = 1;
                while ((j + numOfpoint < copyPoints.length) && (i.slopeTo(copyPoints[j]) == i.slopeTo(copyPoints[j + numOfpoint])))
                    numOfpoint++;
                if (numOfpoint > 2) {
                    b=false;
                    for (int k = j + 1; k < j + numOfpoint; k++)
                        listOfPoint.add(copyPoints[k]);
                    Collections.sort(listOfPoint);
                    LineSegment line = new LineSegment(listOfPoint.get(0), listOfPoint.get(listOfPoint.size() - 1));
                    for (LineSegment k : ls) {
                        if (k.toString().compareTo(line.toString()) == 0)
                            b = true;
                    }
                    if (b)
                        continue;
                    ls.add(line);
                }
            }
        }
    }  // finds all line segments containing 4 or more points

    private void check(Point[] points){
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public int numberOfSegments() {
        return ls.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        return ls.toArray(new LineSegment[ls.size()]);
    }          // the line segments

    public static void main(String[] args) {

    }
}