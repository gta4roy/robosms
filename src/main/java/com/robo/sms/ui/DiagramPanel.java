/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robo.sms.ui;

import com.robo.sms.model.CordinatePoints;
import com.robo.sms.model.ScullModelElliptical;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

/**
 *
 * @author Anushree_Bose
 */
public class DiagramPanel extends JPanel {
    
    public DiagramPanel(ScullModelElliptical model){    
        
        this.setSize(1024, 1024);
        this.model = model;
    }
    
     // paint the applet
    
    private CordinatePoints getNashionPoint(){
        int nashionPointX =  this.model.getCartesianCenter().getCartesianCordinates().x;
        int nashionPointY = this.model.getCartesianCenter().getCartesianCordinates().y +
                                 (int)(this.model.getHeight()/2);
        CordinatePoints na = new CordinatePoints(nashionPointX, nashionPointY);
        return na;
    }
    
    private CordinatePoints getInionPoint(){
       int nashionPointX =  this.model.getCartesianCenter().getCartesianCordinates().x;
        int nashionPointY = this.model.getCartesianCenter().getCartesianCordinates().y -
                                 (int)(this.model.getHeight()/2);
        CordinatePoints na = new CordinatePoints(nashionPointX, nashionPointY);
        return na;
    }
    
    private Point getCZPoint(Point nasionPoint, double length){
        int x = nasionPoint.x;
        int y = nasionPoint.y + (int)(length/2);
        Point na = new Point(x, y);
        return na;
    }
    
    private CordinatePoints getLeftTragusPoint(){
       int lTrPointX =  this.model.getCartesianCenter().getCartesianCordinates().x - (int)(this.model.getWidth()/2.0D);
       int ltrPointY = this.model.getCartesianCenter().getCartesianCordinates().y ;
                              
        CordinatePoints na = new CordinatePoints(lTrPointX, ltrPointY);
        return na;
    }
    
    private CordinatePoints getRightTragusPoint(){
        int lTrPointX =  this.model.getCartesianCenter().getCartesianCordinates().x + (int)(this.model.getWidth()/2.0D);
        int ltrPointY = this.model.getCartesianCenter().getCartesianCordinates().y ;
                              
        CordinatePoints na = new CordinatePoints(lTrPointX, ltrPointY);
        return na;
    }
    
    private CordinatePoints getFPzPoint(double gapDistance, CordinatePoints nasionPoint){
        CordinatePoints nm = new CordinatePoints (nasionPoint.getCartesianCordinates().x, nasionPoint.getCartesianCordinates().y - (int)gapDistance);
        return nm;
    }
    
    private CordinatePoints getOzPoint(double gapDistance, CordinatePoints inionPoint){
        CordinatePoints nm = new CordinatePoints (inionPoint.getCartesianCordinates().x, inionPoint.getCartesianCordinates().y + (int)gapDistance);
        return nm;
    }
    
    private double getDistanceCms(CordinatePoints a, CordinatePoints b){
        double distance =  a.getScreenCordinates().distance(b.getScreenCordinates());
        return this.CF * distance;
    }
    
    private double getDistancePixels(CordinatePoints a, CordinatePoints b){
        double distance =  a.getScreenCordinates().distance(b.getScreenCordinates());
        return distance;
    }
    
    
    private CordinatePoints getFzPoint(double gapDistance, CordinatePoints vertex){
        CordinatePoints nm = new CordinatePoints(vertex.getCartesianCordinates().x,vertex.getCartesianCordinates().y + (int)gapDistance);
        return nm;
    }
    
    private CordinatePoints getPzPoint (double gapDistance, CordinatePoints vertex ){
        CordinatePoints nm = new CordinatePoints(vertex.getCartesianCordinates().x, vertex.getCartesianCordinates().y - (int)gapDistance);
        return nm;
    }
    
    private CordinatePoints getT3Point(double gapDistance, CordinatePoints leftTragusPoint){
        CordinatePoints nm = new CordinatePoints (leftTragusPoint.getCartesianCordinates().x + (int)gapDistance , leftTragusPoint.getCartesianCordinates().y);
        return nm;
    }
    
    private CordinatePoints getT4Point (double gapDistance ,CordinatePoints rightTragusPoint){
        CordinatePoints nm = new CordinatePoints (rightTragusPoint.getCartesianCordinates().x - (int)gapDistance , rightTragusPoint.getCartesianCordinates().y);
        return nm;
    }
    
    private CordinatePoints getC3Point (double gap, CordinatePoints czPoint){
        CordinatePoints nm = new CordinatePoints (czPoint.getCartesianCordinates().x - (int)gap , czPoint.getScreenCordinates().y);
        return nm;
    }
    
    private CordinatePoints getC4Point (double gap, CordinatePoints czPoint){
        CordinatePoints nm = new CordinatePoints (czPoint.getCartesianCordinates().x + (int)gap , czPoint.getCartesianCordinates().y);
        return nm;
    }
    

    @Override
    public void paint(Graphics g)
    {
      
        this.CF =  this.model.getNashionToInion() / conversionFactor;
        Ellipse2D ed = this.model.getEllipse();
            
        CordinatePoints nasionPoint = getNashionPoint();
        CordinatePoints inionPoint = getInionPoint();
        CordinatePoints czPoint = this.model.getCartesianCenter();
        
        CordinatePoints leftTragusPoint = getLeftTragusPoint();
        CordinatePoints rightTragusPoint = getRightTragusPoint();
        
        
        Graphics2D g1 = (Graphics2D)g;
  
        g1.setColor(Color.RED);
  
        // draw the first ellipse
        g1.draw(ed);
  
        g1.setColor(Color.blue);
        
        g1.drawLine(nasionPoint.getScreenCordinates().x, nasionPoint.getScreenCordinates().y, inionPoint.getScreenCordinates().x, inionPoint.getScreenCordinates().y);
        g1.drawLine(leftTragusPoint.getScreenCordinates().x,leftTragusPoint.getScreenCordinates().y, rightTragusPoint.getScreenCordinates().x, rightTragusPoint.getScreenCordinates().y);
          
        double lengthBetweenNashionPoints = getDistanceCms(nasionPoint, inionPoint);
        double lengthBetweenCZToNashio = getDistanceCms(czPoint, nasionPoint);
        
        //draw labels 
        g1.drawString("Nz ["+ String.format("%.2f",lengthBetweenCZToNashio)+" cm]",nasionPoint.getScreenCordinates().x, nasionPoint.getScreenCordinates().y);
        double lnFromIzToCz = getDistanceCms(czPoint, inionPoint);
        g1.drawString("Iz ["+String.format("%.2f",lnFromIzToCz)+" cm]", inionPoint.getScreenCordinates().x, inionPoint.getScreenCordinates().y);
        g1.drawString("Cz", czPoint.getScreenCordinates().x, czPoint.getScreenCordinates().y);
        
        double lnFromCztoT1 = getDistanceCms(czPoint, leftTragusPoint);
        g1.drawString("T1 ["+String.format("%.2f",lnFromCztoT1)+" cm]", leftTragusPoint.getScreenCordinates().x,leftTragusPoint.getScreenCordinates().y);
        
        double lnFromCzToT2 = getDistanceCms(czPoint, rightTragusPoint);
        g1.drawString("T2 ["+String.format("%.2f",lnFromCzToT2)+" cm]", rightTragusPoint.getScreenCordinates().x,rightTragusPoint.getScreenCordinates().y);  
        
       
        //Step 2
        
        //FPz
        double tenPrtGap = 0.10 * this.model.getHeight(); //10 % of the height
        CordinatePoints fPzPoint = getFPzPoint(tenPrtGap, nasionPoint);
        double lnFPzToVertex = getDistanceCms(czPoint, fPzPoint);
        g1.drawString("FPz ["+String.format("%.2f",lnFPzToVertex)+" cm]", fPzPoint.getScreenCordinates().x,fPzPoint.getScreenCordinates().y);
        
        //Oz
        CordinatePoints ozPoint =getOzPoint(tenPrtGap,inionPoint);
        double lnOzToVertex = getDistanceCms(czPoint, ozPoint);
        g1.drawString("Oz ["+String.format("%.2f",lnOzToVertex)+" cm]", ozPoint.getScreenCordinates().x,ozPoint.getScreenCordinates().y); 
    
        //Step 4 
        double twentyPrtGap = 0.20 * this.model.getHeight() ; //20 % of the length
        CordinatePoints fzPoint = getFzPoint(twentyPrtGap, czPoint);
        double lnFzToVertex = getDistanceCms(czPoint, fzPoint);
        g1.drawString("Fz ["+String.format("%.2f",lnFzToVertex)+" cm]", fzPoint.getScreenCordinates().x,fzPoint.getScreenCordinates().y); 
        
        CordinatePoints PzPoint = getPzPoint(twentyPrtGap, czPoint);
        double lnPzToVertex = getDistanceCms(czPoint, PzPoint);
        g1.drawString("Pz ["+String.format("%.2f",lnPzToVertex)+" cm]", PzPoint.getScreenCordinates().x,PzPoint.getScreenCordinates().y); 
        
        //Step 7
        double leftGap =0.10 * model.getWidth();
        CordinatePoints t3Point = getT3Point(leftGap, leftTragusPoint);
        double lnT3ToVertex = getDistanceCms(czPoint, t3Point);
        g1.drawString("T3 ["+String.format("%.2f",lnT3ToVertex)+" cm]", t3Point.getScreenCordinates().x,t3Point.getScreenCordinates().y); 
        
        
        CordinatePoints t4Point = getT4Point(leftGap, rightTragusPoint);
        double lnT4ToVertex = getDistanceCms(czPoint, t4Point);
        g1.drawString("T4 ["+String.format("%.2f",lnT4ToVertex)+" cm]", t4Point.getScreenCordinates().x,t4Point.getScreenCordinates().y); 
        
        //Step 9
        double c3Gap = 0.50D * getDistancePixels(t3Point,czPoint);
        double c4Gap = 0.50D * getDistancePixels(t4Point,czPoint);
        
        CordinatePoints c3Point = getC3Point(c3Gap, czPoint);
        CordinatePoints c4Point = getC4Point(c4Gap, czPoint);
        
        double lnC3ToVertex = getDistanceCms(czPoint, c3Point);
        g1.drawString("C3 ["+String.format("%.2f",lnC3ToVertex)+" cm]", c3Point.getScreenCordinates().x,c3Point.getScreenCordinates().y); 
        
        double lnC4ToVertex = getDistanceCms(czPoint, c4Point);
        g1.drawString("C4 ["+String.format("%.2f",lnC4ToVertex)+" cm]", c4Point.getScreenCordinates().x,c4Point.getScreenCordinates().y); 
        
        double circum = model.getCircumferenceOfSkullEllipse();
        System.out.println("Complete Circulference "+ circum + "px ");
        
        
        double a = getDistancePixels(czPoint,t4Point) /2 ;    //width
        double b = getDistancePixels(czPoint,ozPoint) /2 ;    //height
        
        //Calculations of the Internal Ellipse 
        double bLenInnerCircle = getDistancePixels(czPoint,fPzPoint); //height
        double aLenInnerCircle = getDistancePixels(czPoint,t4Point); //width
        
        System.out.println("ALen Inner Circle "+ aLenInnerCircle);
        System.out.println("BLen Inner Circle "+ bLenInnerCircle);
        
         
        double innerCircleCircumference = getCircumferenceOfEllipse(aLenInnerCircle, bLenInnerCircle);
        double fivePrtOfCircum = 0.05 * innerCircleCircumference;
        System.out.println("Inner Circumference: "+innerCircleCircumference+" 5 %C "+ fivePrtOfCircum + "px ");
        
        // Check 
        //CordinatePoints nearToT4Point =new CordinatePoints(t4Point.getCartesianCordinates().x,t4Point.getCartesianCordinates().y+1);
        
        //CordinatePoints nextPoint = getNextPoint(aLenInnerCircle, bLenInnerCircle,fivePrtOfCircum, nearToT4Point);
        //System.out.println("T4 Cartesian Cordinate "+ nearToT4Point.getCartesianCordinates());
        //System.out.println("F8 Cartesian Cordinate "+ nextPoint.getCartesianCordinates());
        
        //double distanceFromCenter = getDistanceCms(czPoint, nextPoint);
        //g1.drawString("F8 ["+String.format("%.2f",distanceFromCenter)+" cm]", nextPoint.getScreenCordinates().x,nextPoint.getScreenCordinates().y);    
        
        //288
        for (double angle = 0; angle <= 360.0 ; angle = angle + 36)
        {
              CordinatePoints pointToCheck = CalculatePointsOnEllipse(angle,aLenInnerCircle, bLenInnerCircle, g1);
        }    
    }
    
    public  double getCircumferenceOfEllipse(double aLen, double bLen){
        
        double circumference = Math.PI * ( 3 * (bLen +aLen ) - Math.sqrt(      (3*bLen + aLen) * (bLen+3*aLen)     ));
        return circumference;
    }
    
    public CordinatePoints CalculatePointsOnEllipse(double angle ,double aLen , double bLen,Graphics2D graphics){
            
        double valueInRadian = Math.toRadians(angle);
        double x = aLen * Math.cos(valueInRadian);
        double y = bLen * Math.sin(valueInRadian);

        int xT = (int)x;
        int yT = (int)y;

        CordinatePoints point = new CordinatePoints(xT,yT);
        System.out.println("Point "+point.getCartesianCordinates());
        graphics.drawString("["+point.getCartesianCordinates().x+","+point.getCartesianCordinates().y+"]", point.getScreenCordinates().x,point.getScreenCordinates().y);    
            
        return point;
    }
    
    
    public CordinatePoints getNextPoint(double aLen, double bLen, double distance , CordinatePoints refPoint){
        int x =0;
        int y =0;
        
        int refX = refPoint.getCartesianCordinates().x;
        int refY =  refPoint.getCartesianCordinates().y;
        
        /*
                           kZ               dZ
        x' = x + d / sqrt(1 + b²x² / (a²(a²-x²)))
        y' = b sqrt(1 - x'²/a²)
        */
        System.out.println("RefPoints "+ refPoint.getCartesianCordinates());
        
        System.out.println("Math.pow(aLen, 2) "+ Math.pow(aLen, 2));
        System.out.println(" Math.pow((double)refX, 2)) "+  Math.pow((double)refX, 2));
        
        
        double dZ = Math.pow(aLen, 2) *((Math.pow(aLen, 2) - Math.pow((double)refX, 2)));
        double kZ = 1D + ((Math.pow(bLen, 2) * Math.pow((double)refX, 2)));
        
        System.out.println("DZ "+dZ+" kz" +kZ + "distance"+distance);
        double calculatedX = (double)refX + (distance / Math.sqrt((kZ/dZ)));
        double calculatedY = bLen * Math.sqrt((1 - Math.pow(calculatedX, 2) / Math.pow(aLen, 2)));
        
        x = (int)calculatedX;
        y = (int)calculatedY;
          
        CordinatePoints calculatedPoint = new CordinatePoints(x,y);
        return calculatedPoint;
    }
    
    
    ScullModelElliptical model =null;
    static double conversionFactor = 512D;
    double CF = 0;
        
}
