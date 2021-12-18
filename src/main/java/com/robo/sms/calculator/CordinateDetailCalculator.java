/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robo.sms.calculator;

import com.robo.sms.model.CordinatePoints;
import java.awt.geom.Line2D;

enum Quadrant {
    FIRST,
    SECOND,
    THIRD,
    FOURTH
}

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
            CordinatePoints ozPoint,
            Double aLen ,
            Double bLen) {
        
        this.centerVertex = centerVertex;
        this.t4Point = t4Point;
        this.FpzPoint = fpzPoint;
        this.T3Point = t3Point;
        this.ozPoint = ozPoint;
        this.CF =CF;
        this.aLen= aLen;
        this.bLen = bLen;
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
        this.arcLength = getArchLengthBetweenPointsOnTheCurve(refPoint) * this.CF;
    }
    
    
    public Double getDistanceFromCz(){
        return this.distanceInCM;
    } 
    
    public Double getArcLength(){
        return this.arcLength;
    }
    
    public String getDescription(){
        return this.refPoint.getCordinateName();
    }
    
    public String getRefrencePoint(){
        
        String referencePoint = "CZ";
        if(refPoint.getCartesianCordinates().x >0 && refPoint.getCartesianCordinates().y >= 0){
            //First Quadrant
         
             //Major and Minor Axis is shifting 
           if (this.aLen < this.bLen){
            //reference point
                referencePoint = "Fpz";
            }else {
                referencePoint = "T4";
           }
            
        }else if (refPoint.getCartesianCordinates().x <= 0 && refPoint.getCartesianCordinates().y > 0){
            //second quadrant        
            if (this.aLen < this.bLen){
            //reference point
                referencePoint = "Fpz";
            }else {
                referencePoint = "T3";
           }
           
        }else if (refPoint.getCartesianCordinates().x < 0 && refPoint.getCartesianCordinates().y <= 0){
            //third quadrant
            if (this.aLen < this.bLen){
            //reference point
                referencePoint = "Oz";
            }else {
                referencePoint = "T3";
           }
            
        }else if (refPoint.getCartesianCordinates().x >= 0 && refPoint.getCartesianCordinates().y < 0){
            //fourth quadrant
           if (this.aLen < this.bLen){
            //reference point
                referencePoint = "Oz";
           }else {
                referencePoint = "T4";
            }
        }
        return referencePoint;
    }
    
    public String getDetails(){
        StringBuilder details = new StringBuilder();    
        details.append(refPoint.getCordinateName()+" ");
        details.append(String.format("%.2f",this.distanceInCM)+"cm. ");
        //details.append(String.format("%.2f",this.angleFromXPositiveAxis)+ " degree rot ");
        
        if (this.aLen < this.bLen){
            //reference point
            details.append(String.format("%.2f",this.arcLength)+ "cm  Arc Length from Y Axis ");
        }else {
            details.append(String.format("%.2f",this.arcLength)+ "cm  Arc Length from X Axis ");
        }
      
       // details.append(" Cordinate "+ String.format(" X :%d  Y: %d",this.refPoint.getCartesianCordinates().x , this.refPoint.getCartesianCordinates().y));
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

        CordinatePoints marginPoint = getMarginPoint(referedPoint);
        double angle = getAngleInRadianFromXAxis(referedPoint);     
        CordinatePoints r = convertFirstQuadrantCordinate(referedPoint);
        CordinatePoints m = this.t4Point;

        //double angle = getAngleInRadianFromXAxis(r);
        System.out.println("------------Point X"+ referedPoint.getCartesianCordinates().x + "  Y "+ referedPoint.getCartesianCordinates().y);
        System.out.println("---ALEN : "+ this.aLen + "  BLen "+this.bLen);
        System.out.println("---ALEN : "+ this.aLen *this.CF + "  BLen "+this.bLen *this.CF);
             
        //Major axis aLen 
        //Minon axis blen 
        double aLen = 0.0; 
        double bLen = 0.0;
        
        if (this.aLen < this.bLen){
            aLen  = this.bLen;
            bLen = this.aLen;
            angle = Math.toRadians(90.0) - angle;
        } else {
            aLen = this.aLen;
            bLen = this.bLen;
        }
 
        System.out.println("   Angle in Degree :"+ Math.toDegrees(angle));
        System.out.println("   Angle in Radian :"+ angle);
        
        //calculating using Simpson Rule Complete Integral of Second Kind 
        double lowerLimit = 0.0d; //0 radian degree
        double higherLimit = angle ; // angle of the point 
        
        double eccentricity = Math.sqrt( (1.0d - Math.pow((bLen/aLen), 2.0d))  );
        System.out.println("   Eccentricity of the Ellips :"+ eccentricity);
        
        int simpsonN =6;  // 10 intervals 
        double delta = (higherLimit - lowerLimit) / (double)simpsonN;
        System.out.println("delta :"+delta);
        double calculateValues = 0;
        
        double thetaValue = lowerLimit;
        for(int i =0; i <=simpsonN ; i++){        
            if (i ==0 || i == (simpsonN)){
                calculateValues += getFunctionValue(eccentricity, thetaValue);
            }else if ( i % 2 != 0){              
                //odd 
                calculateValues += (4.0d * getFunctionValue(eccentricity, thetaValue));
                System.out.println("Factor: 4");
            }else {
                //even
                calculateValues += (2.0d * getFunctionValue(eccentricity, thetaValue));
                 System.out.println("Factor: 2");
            }
            
            System.out.println(" Simpson Values  thetaValue :"+ thetaValue+ " Function Return :"+calculateValues);
            thetaValue += delta;
        }
        
        
        double arcLen = aLen * ((delta /3.0d) * calculateValues);
        
        System.out.println("Calculated Arc Length :"+arcLen);
        return arcLen;
    }
    
    private double getFunctionValue(double ecentricity,double angle){
        double functionValue = 0.0d;
        functionValue = Math.sqrt(( 1.0d - (Math.pow(ecentricity, 2.0d) * Math.pow(Math.sin(angle), 2.0d))));
        return functionValue;
    }
    
    private Quadrant getQuadrant(CordinatePoints refPoint){
        
        Quadrant calculatedQuadrant = Quadrant.FIRST;
        if(refPoint.getCartesianCordinates().x >0 && refPoint.getCartesianCordinates().y >= 0){
            //First Quadrant
            calculatedQuadrant = Quadrant.FIRST;
        }else if (refPoint.getCartesianCordinates().x <= 0 && refPoint.getCartesianCordinates().y > 0){
            //second quadrant
            calculatedQuadrant = Quadrant.SECOND;
        }else if (refPoint.getCartesianCordinates().x < 0 && refPoint.getCartesianCordinates().y <= 0){
            //third quadrant
            calculatedQuadrant = Quadrant.THIRD;
        }else if (refPoint.getCartesianCordinates().x >= 0 && refPoint.getCartesianCordinates().y < 0){
            //fourth quadrant
            calculatedQuadrant = Quadrant.FOURTH;
        }
        
        return calculatedQuadrant;
    }
    
    
    private CordinatePoints convertFirstQuadrantCordinate(CordinatePoints refPoint){
        
        Quadrant calculatedQuadrant = getQuadrant(refPoint);
        
        CordinatePoints calculatedPoint = new CordinatePoints(0,0);
        switch(calculatedQuadrant){
            case FIRST:
                calculatedPoint.setCartesianCordinates(refPoint.getCartesianCordinates().x, refPoint.getCartesianCordinates().y);
                break ;
            case SECOND:
                 calculatedPoint.setCartesianCordinates((-1) * refPoint.getCartesianCordinates().x, refPoint.getCartesianCordinates().y);
                break;
            case THIRD:
                 calculatedPoint.setCartesianCordinates((-1) *refPoint.getCartesianCordinates().x, (-1)* refPoint.getCartesianCordinates().y);
                break;
            case FOURTH:
                 calculatedPoint.setCartesianCordinates(refPoint.getCartesianCordinates().x, (-1) *refPoint.getCartesianCordinates().y);
            break;
                
        }
        return calculatedPoint;
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
    
    private double calculateAngleFromXAxis(CordinatePoints refferedPoint){
         CordinatePoints marginPoint = t4Point;
        Line2D xAxisLine = new Line2D.Double(centerVertex.getScreenCordinates(),marginPoint.getScreenCordinates());
        Line2D pointLine = new Line2D.Double(centerVertex.getScreenCordinates(),refferedPoint.getScreenCordinates());
        double angle1 = Math.atan2(xAxisLine.getY1() - xAxisLine.getY2(),
                                   xAxisLine.getX1() - xAxisLine.getX2());
        double angle2 = Math.atan2(pointLine.getY1() - pointLine.getY2(),
                                   pointLine.getX1() - pointLine.getX2());
        
        double angleInRadian = angle1 -angle2;
        
        if(refferedPoint.getCartesianCordinates().x >0 && refferedPoint.getCartesianCordinates().y >= 0){
            //First Quadrant
            
        }else if (refferedPoint.getCartesianCordinates().x <= 0 && refferedPoint.getCartesianCordinates().y > 0){
            //second quadrant
            angleInRadian = Math.PI - angleInRadian;
        }else if (refferedPoint.getCartesianCordinates().x < 0 && refferedPoint.getCartesianCordinates().y <= 0){
            //third quadrant
           angleInRadian =  angleInRadian - Math.PI; 
        }else if (refferedPoint.getCartesianCordinates().x >= 0 && refferedPoint.getCartesianCordinates().y < 0){
            //fourth quadrant
            angleInRadian = (2.0d *Math.PI) - angleInRadian;
        }
        
        return angleInRadian;
    }
    
    private double getAngleBetween2Lines(){
        
        CordinatePoints marginPoint = t4Point;
        Line2D xAxisLine = new Line2D.Double(centerVertex.getScreenCordinates(),marginPoint.getScreenCordinates());
        Line2D pointLine = new Line2D.Double(centerVertex.getScreenCordinates(),this.refPoint.getScreenCordinates());
        double angle1 = Math.atan2(xAxisLine.getY1() - xAxisLine.getY2(),
                                   xAxisLine.getX1() - xAxisLine.getX2());
        double angle2 = Math.atan2(pointLine.getY1() - pointLine.getY2(),
                                   pointLine.getX1() - pointLine.getX2());
        
        double angleInRadian = angle1 -angle2;      
        return Math.toDegrees(angleInRadian);
    }
    
    private double getAngleInRadianFromXAxis(CordinatePoints refferedPoint ){
//        CordinatePoints marginPoint = getMarginPoint(refPoints);
//        double aLen = this.aLen;
//        double X= refPoints.getCartesianCordinates().x;
//        double angleInRadian = Math.acos((X/aLen));
        double angleInRadian = calculateAngleFromXAxis(refferedPoint);   
        return angleInRadian;
    }
    
    private CordinatePoints refPoint;
    
    private double distanceInPixel;
    private double distanceInCM ;
    private double angleFromXPositiveAxis;
    private double arcLength;
    private double aLen;
    private double bLen;
    private CordinatePoints centerVertex;
    private CordinatePoints t4Point;
    private CordinatePoints FpzPoint ;
    private CordinatePoints T3Point;
    private CordinatePoints ozPoint;
    private double CF;
}
