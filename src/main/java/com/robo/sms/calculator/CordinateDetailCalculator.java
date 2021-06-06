/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robo.sms.calculator;

import com.robo.sms.model.CordinatePoints;
import java.awt.geom.Line2D;

/**
 *
 * @author Anushree_Bose
 */
public class CordinateDetailCalculator {

    public CordinateDetailCalculator(double CF,CordinatePoints centerVertex, CordinatePoints t4Point) {
        this.centerVertex = centerVertex;
        this.t4Point = t4Point;
        this.CF =CF;
    }

    public double getDistanceInPixel() {
        return distanceInPixel;
    }

    public double getDistanceInCM() {
        return distanceInCM;
    }

    public double getAngleFromXPositiveAxis() {
        return angleFromXPositiveAxis;
    }
    
    public void calculateDetails(CordinatePoints refPoint){
        this.refPoint = refPoint;
        this.distanceInCM = getDistanceCms(centerVertex, refPoint);
        this.distanceInPixel = getDistancePixels(centerVertex,refPoint);
        this.angleFromXPositiveAxis = getAngleBetween2Lines();
    }
    
    public String getDetails(){
        StringBuilder details = new StringBuilder();    
        details.append(refPoint.getCordinateName()+" ");
        details.append(String.format("%.2f",this.distanceInCM)+"cm. ");
        details.append(String.format("%.2f",this.angleFromXPositiveAxis)+ " degree rot");
        return details.toString();
    }
    
    private double getDistanceCms(CordinatePoints a, CordinatePoints b){
        double distance =  a.getScreenCordinates().distance(b.getScreenCordinates());
        return this.CF * distance;
    }
    
    private double getDistancePixels(CordinatePoints a, CordinatePoints b){
        double distance =  a.getScreenCordinates().distance(b.getScreenCordinates());
        return distance;
    }
    
    private double getAngleBetween2Lines(){
        
        Line2D xAxisLine = new Line2D.Double(centerVertex.getScreenCordinates(),t4Point.getScreenCordinates());
        Line2D pointLine = new Line2D.Double(centerVertex.getScreenCordinates(),refPoint.getScreenCordinates());
        double angle1 = Math.atan2(xAxisLine.getY1() - xAxisLine.getY2(),
                                   xAxisLine.getX1() - xAxisLine.getX2());
        double angle2 = Math.atan2(pointLine.getY1() - pointLine.getY2(),
                                   pointLine.getX1() - pointLine.getX2());
        
        double angleInRadian = angle1 -angle2;
        return Math.toDegrees(angleInRadian);
    }
    
    private CordinatePoints refPoint;
    
    private double distanceInPixel;
    private double distanceInCM ;
    private double angleFromXPositiveAxis;
    private CordinatePoints centerVertex;
    private CordinatePoints t4Point;
    private double CF;
}
