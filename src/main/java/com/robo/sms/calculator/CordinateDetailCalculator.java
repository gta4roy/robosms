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

    public CordinateDetailCalculator(double CF,
            CordinatePoints centerVertex,
            CordinatePoints t4Point,
            CordinatePoints fpzPoint,
            CordinatePoints t3Point,
            CordinatePoints ozPoint) {
        
        this.centerVertex = centerVertex;
        this.t4Point = t4Point;
        this.FpzPoint = fpzPoint;
        this.T3Point = t3Point;
        this.ozPoint = ozPoint;
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
        this.arcLength = getArchLengthBetweenPointsOnTheCurve(refPoint) *this.CF;
    }
    
    public String getDetails(){
        StringBuilder details = new StringBuilder();    
        details.append(refPoint.getCordinateName()+" ");
        details.append(String.format("%.2f",this.distanceInCM)+"cm. ");
        details.append(String.format("%.2f",this.angleFromXPositiveAxis)+ " degree rot");
        details.append(String.format("%.2f",this.arcLength)+ "pixel arc");
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
    
    /*
    https://www.codeproject.com/Articles/566614/Elliptic-integrals
    Abramowitz and Stegun p.591, formula 17.3.12
    */
    private double completeEllipticIntegralSecondKind(double k){
        double sum , term , above , below;
        sum = 1.0;
        term = 1.0;
        above = 1.0;
        below = 2.0;
        
        for (int i = 1; i <100; i++){
            term *= above/below;
            sum -= Math.pow(k, i) * Math.pow(term, 2) /above ;
            above +=2 ;
            below +=2;
        }
        
        
        sum *= 0.5 * Math.PI;
        return sum;
    }
    
    
    
    private double getArchLengthBetweenPointsOnTheCurve(CordinatePoints referedPoint){
        double factor = Math.PI / (2 * Math.sqrt(2.0));
        
        CordinatePoints marginPoint = getMarginPoint(referedPoint);
        
        double x1 = (double) marginPoint.getCartesianCordinates().x;
        double x2 = (double) referedPoint.getCartesianCordinates().x;
        
        double y1 = (double) marginPoint.getCartesianCordinates().y;
        double y2 = (double) referedPoint.getCartesianCordinates().y;
        
        double equationRes = Math.pow((x2 - x2), 2.0) + Math.pow((y2 - y1), 2.0);
        double arcLen = factor * Math.sqrt(equationRes);
        return arcLen;
    }
    
    private CordinatePoints getMarginPoint(CordinatePoints refPoint){
        CordinatePoints marginPoint = null;
        if(refPoint.getCartesianCordinates().x >0 && refPoint.getCartesianCordinates().y >= 0){
            //First Quadrant
            marginPoint = this.t4Point;
        }else if (refPoint.getCartesianCordinates().x <= 0 && refPoint.getCartesianCordinates().y > 0){
            //second quadrant
            marginPoint = this.FpzPoint;
        }else if (refPoint.getCartesianCordinates().x < 0 && refPoint.getCartesianCordinates().y <= 0){
            //third quadrant
            marginPoint = this.T3Point;
        }else if (refPoint.getCartesianCordinates().x >= 0 && refPoint.getCartesianCordinates().y < 0){
            //fourth quadrant
            marginPoint = this.ozPoint;
        }
        return marginPoint;
    }
    
    private double getAngleBetween2Lines(){
        
        CordinatePoints marginPoint = getMarginPoint(refPoint);
        
        Line2D xAxisLine = new Line2D.Double(centerVertex.getScreenCordinates(),marginPoint.getScreenCordinates());
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
    private double arcLength;
    private CordinatePoints centerVertex;
    private CordinatePoints t4Point;
    private CordinatePoints FpzPoint ;
    private CordinatePoints T3Point;
    private CordinatePoints ozPoint;
    private double CF;
}
