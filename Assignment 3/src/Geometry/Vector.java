package Geometry;

/**
 * Created by admin on 02.09.2015.
 */
public class Vector extends Point{

    public Vector(){
        this(0,0);
    }

    public Vector(double X, double Y){
        x = X;
        y = Y;
    }

    public Vector(Point b){
        this(b.x, b.y);
    }

    public Vector(Point a, Point b){
        this(b.x - a.x, b.y - a.y);
    }
}
