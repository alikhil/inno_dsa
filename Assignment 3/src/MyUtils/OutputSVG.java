package MyUtils;

/**
 * Created by admin on 02.09.2015.
 */
import Geometry.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/**
 * Draws geometric figures and saves result to file
 * <p>
 * ?????? ?????????????? ?????? ? ????????? ?? ? ????
 *
 * @author Tushov Ruslan
 */
public class OutputSVG {
    String buffer;
    static final int
            lineWidth = 2,
            pointRadius = 2,
            padding = 30;
    static final String
            lineColor = "blue",
            pointColor = "red",
            backgroundColor = "palegoldenrod";
    double minX, minY, maxX, maxY;

    /**
     * Constructor/
     * <p>
     * ???????????
     */
    public OutputSVG () {
        minX = Double.POSITIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        maxX = Double.NEGATIVE_INFINITY;
        maxY = Double.NEGATIVE_INFINITY;
        buffer = "";
        Locale.setDefault(Locale.US);
    }

    /**
     * Draws polygon defined by array of vertices `points`
     * <p>
     * ?????? ????????????? ???????? ???????? ??? ?????? `points`
     * @param points
     */
    public void addPolygon (ArrayList<Point> points) {
        String data = "";
        double x, y;
        Point point;
        for (int i = 0; i < points.size(); i ++) {
            point = points.get(i);
            extendBoundary (point);
            x = point.x;
            y = point.y;
            data += String.format ("%.03f,%.03f ", x, y);
        }
        buffer += String.format ("<polygon points=\"%s\" />\n", data);
    }

    /**
     * Draws point at `point`
     * <p>
     * ?????? ????? ? `point`
     * @param point
     */
    public void addPoint (Point point) {
        extendBoundary (point);
        buffer += String.format ("<circle class=\"point\" cx=\"%.03f\" cy=\"%.03f\" r=\"%d\" />\n", point.x, point.y, pointRadius);
    }
    public void addPoint (Point point, Boolean contains) {
        extendBoundary (point);
        buffer += String.format ("<circle style=\"fill: %s;\" class=\"point\" cx=\"%.03f\" cy=\"%.03f\" r=\"%d\" />\n", contains ? "lime" : "red", point.x, point.y, pointRadius);
    }
    /**
     * Draws a line segment which endpoints are `begin` and `end`
     * <p>
     * ?????? ??????? ? ??????? ? ?????? `begin` ? `end`
     * @param begin
     * @param end
     */
    public void addLineSegment (Point begin, Point end) {
        extendBoundary (begin);
        extendBoundary (end);
        buffer += String.format ("<line x1=\"%.03f\" y1=\"%.03f\" x2=\"%.03f\" y2=\"%.03f\" />\n",
                begin.x, begin.y,
                end.x, end.y
        );
    }

    /**
     * Saves result to file XXX.html, where XXX is value of `outputFileName`
     * <p>
     * ????????? ????????? ? ???? ???.html, ?????? ??? ???????????? ???????? `outputFileName`
     * @param outputFileName
     * @throws IOException
     */
    public void saveToFile (String outputFileName) throws IOException {
        int width = (int) (maxX - minX),
                height = (int) (maxY - minY);

        String output = "";

        output += "<!DOCTYPE HTML><html><head><meta http-equiv=\"refresh\" content=\"2\"><title>" + outputFileName + "</title></head><body>\n";

        output += "<style>\n";
        output += "html {background: " + backgroundColor + ";}\n";
        output += String.format ("svg * {stroke: %s; stroke-width: %d;}\n", lineColor, lineWidth);
        output += String.format ("polygon {fill: none;}\n");
        output += String.format ("circle.point {fill: %s; stroke-width: 0;}\n", pointColor);
        output += "</style>\n";

        output += String.format ("<svg width=\"%d\" height=\"%d\">\n", width + 2 * padding, height + 2 * padding);

        output += String.format ("<g transform=\"translate (%d %d)\">\n", (int) -minX + padding, (int) -minY + padding);

        output += buffer;

        output += "</g>\n</svg>\n</body></html>";

        FileWriter fileWriter = new FileWriter (outputFileName + ".html");
        BufferedWriter bufferedWriter = new BufferedWriter (fileWriter);
        bufferedWriter.write (output);
        bufferedWriter.close ();
    }

    /**
     * Saves result to file output.html
     * <p>
     * ????????? ????????? ? ???? output.html
     * @throws IOException
     */
    public void saveToFile () throws IOException {
        saveToFile ("output");
    }

    void extendBoundary (Point point) {
        double x, y;
        x = point.x;
        y = point.y;
        minX = Math.min (minX, x);
        minY = Math.min (minY, y);
        maxX = Math.max (maxX, x);
        maxY = Math.max (maxY, y);
    }
}

// "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">"