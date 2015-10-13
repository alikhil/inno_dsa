import Geometry.*;
import MyUtils.*;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Alik Khilazhev on 01.09.2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        Random random = new Random();
        Polygon polygon = new Polygon(5 + random.nextInt(50), 200);
        OutputSVG outputSVG = new OutputSVG();
        outputSVG.addPolygon(polygon.randomizePolygon());
        for(int i = 0;i < 200;i++){

            Point p = new Point(random.nextInt(300) - 100,random.nextInt(300) - 100);
            boolean cont = polygon.containsPoint(p);
            outputSVG.addPoint(p, cont);

        }
        outputSVG.saveToFile("polygon");
    }

}
