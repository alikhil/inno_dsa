package Geometry;

/**
 * Created by Alik Khilazhev on 02.09.2015.
 */
public class Line {
    public Point a;
    public Point b;
    public double A, B, C;
    public Line(){
        this(new Point(), new Point(1,1));
    }

    public Line(Point a_, Point b_){
        a = a_;
        b = b_;

        Vector v = new Vector(a, b);
        A = -v.y;
        B = v.x;
        C = -A * a_.x - B * a_.y;
    }

    public boolean Intersect(Line b, Point ans){
        if (Math.abs(A * b.B - b.A * B) < 0.00001)
            return false;
        double[] first = new double[3];
        double[] second = new double[3];
        first[0] = A; first[1] = B; first[2] = -C;
        second[0] = b.A; second[1] = b.B; second[2] = -b.C;
        if (A != 0) {
            for(int i = 0;i < 3;i++){
                double tmp = first[i];
                first[i] = second[i];
                second[i] = tmp;
            }
        }
        double a1 = first[0];
        for(int i = 0;i < 3; i++)
            first[i] /= a1;
        double a2 = -second[0];

        for(int i = 0;i < 3; i++)
            second[i] += first[i] * a2;
        double b2 = second[1];

        for(int i = 0;i < 3; i++)
            second[i] /= b2;

        double b1 = -first[1];

        for(int i = 0;i < 3; i++)
            first[i] += second[i] * b1;
        ans.x = first[2];
        ans.y = second[2];
        return true;
    }
}
