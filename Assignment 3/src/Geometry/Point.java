package Geometry;

/**
 * Created by Alik Khilazhev on 02.09.2015.
 */
public class Point {
    public double x;
    public double y;

    public Point(){
        this(0,0);
    }

    public Point(double X, double Y){
        x = X;
        y = Y;
    }

    public Point add(Point a){
        Point b = new Point(x,y);
        b.x += a.x;
        b.y += a.y;
        return b;
    }

    public double distanceTo(Point b){
        return Math.sqrt((b.x - x) * (b.x - x) + (y - b.y) * (y - b.y));
    }

    public double crossProduct(Vector b){
        return x * b.x + y * b.y;
    }

    public double dotProduct(Vector b){
        return x * b.y - y * b.x;
    }


}
