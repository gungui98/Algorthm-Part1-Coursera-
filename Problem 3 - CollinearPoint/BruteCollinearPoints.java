import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] ls;
    public BruteCollinearPoints(Point[] points){
        ls = new LineSegment[1];
        if(points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for(Point i:points)
            if(i == null)
                throw new java.lang.IllegalArgumentException();
        check(points);
        Point[] copyPoints = points.clone();
        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        Arrays.sort(copyPoints);

        for(int iter1=0;iter1<copyPoints.length-3;iter1++)
            for(int iter2=iter1+1;iter2<copyPoints.length-2;iter2++)
                for(int iter3=iter2+1;iter3<copyPoints.length-1;iter3++)
                    for(int iter4=iter3+1;iter4<copyPoints.length;iter4++){
                        if(copyPoints[iter1].slopeTo(copyPoints[iter2])==copyPoints[iter2].slopeTo(copyPoints[iter3]))
                            if(copyPoints[iter2].slopeTo(copyPoints[iter3])==copyPoints[iter3].slopeTo(copyPoints[iter4])) {
                                lineSegments.add(new LineSegment(copyPoints[iter1],copyPoints[iter4]));
                            }
                    }
        ls = lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }// finds all line segments containing 4 points
    public int numberOfSegments(){

        return ls.length;
    }        // the number of line segments
    private void check(Point[] points){
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
    public LineSegment[] segments(){
        return ls;
    }                // the line segments
    private void lsresize(int size){
        LineSegment[] templs = new LineSegment[size];
        for(int i=0;i<ls.length;i++){
            templs[i] = ls[i];
        }
        ls = templs;
    }
    public static void main(String[] args){
    }
}