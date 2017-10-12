/**
 * Created by GUNGUI on 10/5/2017.
 */

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


import java.util.ArrayList;

public class KdTree {
    private final RectHV rectHV0 = new RectHV(0, 0, 1, 1);
    private Node root;
    private int size;
    private Node bestSofar;
    private double distance;

    public KdTree() {
        size = 0;
    }                               // construct an empty set of points

    /**
     * @return
     */
    public boolean isEmpty() {
        return this.size == 0;
    }// is the set empty?

    /**
     * @return
     */
    public int size() {
        return this.size;
    }                         // number of points in the set

    /**
     * @param p
     */
    public void insert(Point2D p) {
        if(p == null)
            throw new java.lang.IllegalArgumentException("Null 2D Point is not acceptable");
        if(size == 0){
            root = new Node(p,'x',rectHV0);
            size++;
        }
        if(!contains(p)) {
            put(root, p, 'y',null);
            size++;
        }
    }              // add the point to the set (if it is not already in the set)

    /**
     * @param x
     * @param p
     * @param parentAxis
     * @param
     * @return
     */
    private void put(Node x, Point2D p, char parentAxis, Node parent) {


        if (x.getAxis() == 'x') {
            if (p.x() < x.getPoint().x()) {
                if (x.getLeft() != null) {
                    put(x.getLeft(), p, x.getAxis(), x);
                } else {
                    x.left = creatNewNode(p,x.getAxis(),x);
                }
            }
            else {
                if (x.getRight() != null) {
                    put(x.getRight(), p, x.getAxis(), x);
                }
                else {
                    x.right = creatNewNode(p,x.getAxis(),x);
                }
            }
        } else {
            if (p.y() < x.getPoint().y())
                if (x.getLeft() != null) {
                    put(x.getLeft(), p, x.getAxis(), x);
                } else {
                    x.left = creatNewNode(p,x.getAxis(),x);
                }
            else
            if (x.getRight() != null) {
                put(x.getRight(), p, x.getAxis(), x);
            }
            else {
                x.right = creatNewNode(p,x.getAxis(),x);
            }
        }

    }
    private Node creatNewNode( Point2D p, char parentAxis, Node parent){
        RectHV rectHV;
        if(parentAxis == 'x'){
            if (p.x() < parent.getPoint().x())
                rectHV = new RectHV(parent.rectHV.xmin(), parent.rectHV.ymin(), parent.getPoint().x(), parent.rectHV.ymax());
            else
                rectHV =  new RectHV(parent.getPoint().x(), parent.rectHV.ymin(), parent.rectHV.xmax(), parent.rectHV.ymax());
        }
        else{
            if (p.y() < parent.getPoint().y())
                rectHV = new RectHV(parent.rectHV.xmin(), parent.rectHV.ymin(), parent.rectHV.xmax(), parent.getPoint().y());
            else
                rectHV = new  RectHV(parent.rectHV.xmin(), parent.getPoint().y(), parent.rectHV.xmax(), parent.rectHV.ymax());
        }
       return new Node(p, rolate(parentAxis), rectHV);
    }
    /**
     * @param axis
     * @return
     */

    private char rolate(char axis) {
        if (axis == 'x')
            return 'y';
        else
            return 'x';
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new java.lang.IllegalArgumentException();
        return get(root, p) != null;
    }            // does the set contain point p?

    private Node get(Node x, Point2D p) {
        if (x == null) return null;
        if (x.getPoint().compareTo(p) == 0)
            return x;
        if (x.getAxis() == 'x') {
            if (p.x() < x.getPoint().x())
                return get(x.getLeft(), p);
            else
                return get(x.getRight(), p);
        } else {
            if (p.y() < x.getPoint().y())
                return get(x.getLeft(), p);
            else {
                return get(x.getRight(), p);
            }
        }
    }

    public void draw() {
        draw(root);
    }                         // draw all points to standard draw

    private void draw(Node node) {
   /*     if (node == null)
            return;

        if (node.getAxis() == 'x') {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.005);
            StdDraw.line(node.getPoint().x(), node.rectHV.ymin(), node.getPoint().x(), node.rectHV.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.005);
            StdDraw.line(node.rectHV.xmin(), node.getPoint().y(), node.rectHV.xmax(), node.getPoint().y());
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(node.getPoint().x(), node.getPoint().y());
        draw(node.getLeft());

        draw(node.getRight());
*/
    }

    public Iterable<Point2D> range(RectHV rect) {
        if(rect == null)
            throw new java.lang.IllegalArgumentException("Null rectangle is not acceptable");
        ArrayList<Point2D> arr = new ArrayList<>();
        contain(root, rect, arr);
        return arr;
    }             // all points that are inside the rectangle (or on the boundary)

    private void contain(Node x, RectHV rect, ArrayList<Point2D> arr) {
        if (x == null || !x.rectHV.intersects(rect)) return;
        if (rect.contains(x.getPoint()))
            arr.add(x.getPoint());
        if (x.getAxis() == 'x') {
            if (x.getPoint().x() >= rect.xmin())
                contain(x.left, rect, arr);
            if (x.getPoint().x() <= rect.xmax())
                contain(x.right, rect, arr);
        }
        if (x.getAxis() == 'y') {
            if (x.getPoint().y() >= rect.ymin())
                contain(x.left, rect, arr);
            if (x.getPoint().y() <= rect.ymax())
                contain(x.right, rect, arr);
        }

    }

    public Point2D nearest(Point2D p) {
        if(p == null)
            throw new java.lang.IllegalArgumentException("Null 2D Point is not acceptable");
        if (size == 0)
            return null;
        this.bestSofar = root;
        findNext(root, p);
        return bestSofar.getPoint();

    }             // a nearest neighbor in the set to point p; null if the set is empty

    private void findNext(Node x, Point2D p) {
        if (x == null)
            return;
        if (x.rectHV.distanceTo(p) >= bestSofar.getPoint().distanceTo(p))
            return;
        if (x.getPoint().distanceTo(p) < bestSofar.getPoint().distanceTo(p)) {
            bestSofar = x;
        }
        if(x.getAxis() == 'x'){
            if(x.getPoint().x()<p.x()) {
                findNext(x.right, p);
                findNext(x.left, p);
            }
            else {
                findNext(x.left, p);
                findNext(x.right, p);
            }
        }
        if(x.getAxis() == 'y'){
            if(x.getPoint().y()<p.y()){
                findNext(x.right, p);
                findNext(x.left, p);
            }
            else{
                findNext(x.left, p);
                findNext(x.right, p);
            }
        }

    }

    private class Node {
        private char axis;
        RectHV rectHV;
        private Point2D point;
        Node left, right;

        Node(Point2D p, char axis, RectHV rectHV) {
            this.rectHV = rectHV;
            this.axis = axis;
            this.point = p;

        }
        public char getAxis() {
            return this.axis;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getRight() {
            return right;
        }

        public Point2D getPoint() {
            return point;
        }
    }

    public static void main(String[] args) {

        In in = new In("test.txt");

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        KdTree kdtree = new KdTree();
       for(int i=0;i<10;i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        StdOut.print(kdtree.contains(new Point2D( 0.185,0.875)));
    }                 // unit testing of the methods (optional)
}