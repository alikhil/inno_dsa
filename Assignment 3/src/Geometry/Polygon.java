package Geometry;

import MyUtils.ArrayList;

import java.util.Random;

/**
 * Created by Alik Khilazhev on 01.09.2015.
 */

public class Polygon {
    public ArrayList<Point> points;
    public int vertexCount;
    public int distanceToVertex;
    private int checkRaysCount = 20;
    public Polygon(){
        this(8,10);
    }

    public Polygon(int count, int distanceFromCenterToVertex){
        vertexCount = count;
        distanceToVertex = distanceFromCenterToVertex;
        points = new ArrayList<>(count);
        double angle = 2. * Math.PI / count;
        for(int i = 0;i < count;i++){
            points.add(i,
                    new Point(Math.cos(angle * i) * distanceToVertex, Math.sin(angle * i) * distanceToVertex)
            );
        }
    }

    public ArrayList<Point> randomizePolygon(){
        points = new ArrayList<>(vertexCount);
        Random rand = new Random();
        double angle = 2. * Math.PI / vertexCount;
        for(int i = 0;i < vertexCount;i++){
            points.add(i,
                    new Point(Math.cos(angle * i) * rand.nextInt(distanceToVertex), Math.sin(angle * i) * rand.nextInt(distanceToVertex))
            );
        }
        return points;
    }

    public boolean containsPoint(Point b){
        int[] counts = new int[2];
        Random random = new Random();
        for(int i = 0;i < checkRaysCount;i++){
            Point randPoint = new Point(random.nextInt(distanceToVertex + 5),random.nextInt(distanceToVertex + 5));
            int intersects = 0;
            for(int j = 0;j < vertexCount;j++){
                Point segmentA = points.get(j);
                Point segmentB = points.get((j + 1) % vertexCount);
                Point intersectionPoint = new Point();
                Line segment = new Line(segmentA, segmentB);
                Line ray = new Line(b, randPoint);
                if(ray.Intersect(segment, intersectionPoint)){
                    if(((intersectionPoint.x >= segmentA.x && intersectionPoint.x <= segmentB.x)
                            || (intersectionPoint.x >= segmentB.x && intersectionPoint.x <= segmentA.x ))
                            && ((intersectionPoint.y >= segmentA.y && intersectionPoint.y <= segmentB.y) ||
                        intersectionPoint.y >= segmentB.y && intersectionPoint.y <= segmentA.y)){
                        if(((randPoint.x > b.x && intersectionPoint.x >= b.x) || (randPoint.x < b.x && intersectionPoint.x < b.x))
                        && ((randPoint.y > b.y && intersectionPoint.y  >= b.y) || (randPoint.y <= b.y && intersectionPoint.y <= b.y))){
                            intersects++;
                        }
                    }
                }
            }
            counts[intersects % 2]++;
        }

        return counts[1] > counts[0];
    }
}
