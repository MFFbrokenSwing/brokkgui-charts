package com.github.mffbrokenswing.charts.math;

import java.util.Collection;
import java.util.LinkedList;

public class Graph
{

    private Collection<Point> points;

    public Graph()
    {
        this.points = new LinkedList<>();
    }

    public void addPoint(Point point)
    {
        this.points.add(point);
    }

    public Collection<Point> getPoints()
    {
        return this.points;
    }

}
