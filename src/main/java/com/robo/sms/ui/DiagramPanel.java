/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robo.sms.ui;

import com.robo.sms.calculator.CordinateDetailCalculator;
import com.robo.sms.calculator.CordinateListController;
import com.robo.sms.model.CordinatePoints;
import com.robo.sms.model.ScullModelElliptical;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Anushree_Bose
 */
public class DiagramPanel extends JPanel {
    
    public DiagramPanel(ScullModelElliptical model){    
        
        this.setSize(1024, 1024);
        this.model = model;
        this.setToolTipText("");
        this.cordinateList = new CordinateListController();
        this.innerCircleCordinateMaps =new HashMap<Double,String>();
        innerCircleCordinateMaps.put(36.0D, "F8");
        innerCircleCordinateMaps.put(72.0D, "Fp2");
        innerCircleCordinateMaps.put(108.0D, "Fp1");
        innerCircleCordinateMaps.put(144.0D, "F7");
        innerCircleCordinateMaps.put(216.0D, "T5");
        innerCircleCordinateMaps.put(252.0D, "O1");
        innerCircleCordinateMaps.put(288.0D, "O2");
        innerCircleCordinateMaps.put(324.0D, "T6");
    }
    
     // paint the applet
    
    private CordinatePoints getNashionPoint(){
        int nashionPointX =  this.model.getCartesianCenter().getCartesianCordinates().x;
        int nashionPointY = this.model.getCartesianCenter().getCartesianCordinates().y +
                                 (int)(this.model.getHeight()/2);
        CordinatePoints na = new CordinatePoints(nashionPointX, nashionPointY,"Nz");
        return na;
    }
    
    private CordinatePoints getInionPoint(){
       int nashionPointX =  this.model.getCartesianCenter().getCartesianCordinates().x;
        int nashionPointY = this.model.getCartesianCenter().getCartesianCordinates().y -
                                 (int)(this.model.getHeight()/2);
        CordinatePoints na = new CordinatePoints(nashionPointX, nashionPointY,"Iz");
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
                              
        CordinatePoints na = new CordinatePoints(lTrPointX, ltrPointY,"T1");
        return na;
    }
    
    private CordinatePoints getRightTragusPoint(){
        int lTrPointX =  this.model.getCartesianCenter().getCartesianCordinates().x + (int)(this.model.getWidth()/2.0D);
        int ltrPointY = this.model.getCartesianCenter().getCartesianCordinates().y ;
                              
        CordinatePoints na = new CordinatePoints(lTrPointX, ltrPointY,"T2");
        return na;
    }
    
    private CordinatePoints getFPzPoint(double gapDistance, CordinatePoints nasionPoint){
        CordinatePoints nm = new CordinatePoints (nasionPoint.getCartesianCordinates().x, nasionPoint.getCartesianCordinates().y - (int)gapDistance,"Fpz");
        return nm;
    }
    
    private CordinatePoints getOzPoint(double gapDistance, CordinatePoints inionPoint){
        CordinatePoints nm = new CordinatePoints (inionPoint.getCartesianCordinates().x, inionPoint.getCartesianCordinates().y + (int)gapDistance,"Oz");
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
        CordinatePoints nm = new CordinatePoints(vertex.getCartesianCordinates().x,vertex.getCartesianCordinates().y + (int)gapDistance,"Fz");
        return nm;
    }
    
    private CordinatePoints getPzPoint (double gapDistance, CordinatePoints vertex ){
        CordinatePoints nm = new CordinatePoints(vertex.getCartesianCordinates().x, vertex.getCartesianCordinates().y - (int)gapDistance,"Pz");
        return nm;
    }
    
    private CordinatePoints getT3Point(double gapDistance, CordinatePoints leftTragusPoint){
        CordinatePoints nm = new CordinatePoints (leftTragusPoint.getCartesianCordinates().x + (int)gapDistance , leftTragusPoint.getCartesianCordinates().y,"T3");
        return nm;
    }
    
    private CordinatePoints getT4Point (double gapDistance ,CordinatePoints rightTragusPoint){
        CordinatePoints nm = new CordinatePoints (rightTragusPoint.getCartesianCordinates().x - (int)gapDistance , rightTragusPoint.getCartesianCordinates().y,"T4");
        return nm;
    }
    
    private CordinatePoints getC3Point (double gap, CordinatePoints czPoint){
        CordinatePoints nm = new CordinatePoints (czPoint.getCartesianCordinates().x - (int)gap , czPoint.getCartesianCordinates().y,"C3");
        return nm;
    }
    
    private CordinatePoints getC4Point (double gap, CordinatePoints czPoint){
        CordinatePoints nm = new CordinatePoints (czPoint.getCartesianCordinates().x + (int)gap , czPoint.getCartesianCordinates().y,"C4");
        return nm;
    }
    
    @Override 
    public String getToolTipText(MouseEvent event){
        Point p = event.getPoint();
        CordinatePoints point = cordinateList.getCordinatePoint(p);
        if(point != null){
            dtCalculator.calculateDetails(point);
            return dtCalculator.getDetails();
        }else {
            return "";
        }
    } 

    @Override
    public void paintComponent(Graphics g)
    {
      
        this.CF =  this.model.getNashionToInion() / conversionFactor;
        Ellipse2D ed = this.model.getEllipse();
            
        CordinatePoints nasionPoint = getNashionPoint();
        CordinatePoints inionPoint = getInionPoint();
        CordinatePoints czPoint = this.model.getCartesianCenter();
        czPoint.setCordinateName("Cz");
        
        cordinateList.addCordinate(nasionPoint);
        cordinateList.addCordinate(czPoint);
        cordinateList.addCordinate(inionPoint);
        
        dtCalculator = new CordinateDetailCalculator(this.CF,czPoint,nasionPoint);
        
        CordinatePoints leftTragusPoint = getLeftTragusPoint();
        CordinatePoints rightTragusPoint = getRightTragusPoint();
        cordinateList.addCordinate(leftTragusPoint);
        cordinateList.addCordinate(rightTragusPoint);
        
        
        Graphics2D g1 = (Graphics2D)g;
  
        g1.setColor(Color.RED);
  
        // draw the first ellipse
        g1.draw(ed);
  
        g1.setColor(Color.blue);
        
        g1.drawLine(nasionPoint.getScreenCordinates().x, nasionPoint.getScreenCordinates().y, inionPoint.getScreenCordinates().x, inionPoint.getScreenCordinates().y);
        g1.drawLine(leftTragusPoint.getScreenCordinates().x,leftTragusPoint.getScreenCordinates().y, rightTragusPoint.getScreenCordinates().x, rightTragusPoint.getScreenCordinates().y);
                  
        //draw labels 
        g1.drawString(nasionPoint.getCordinateName(),nasionPoint.getScreenCordinates().x, nasionPoint.getScreenCordinates().y);
        g1.drawString(inionPoint.getCordinateName(), inionPoint.getScreenCordinates().x, inionPoint.getScreenCordinates().y);
        g1.drawString(czPoint.getCordinateName(), czPoint.getScreenCordinates().x, czPoint.getScreenCordinates().y);
        
        
        g1.drawString(leftTragusPoint.getCordinateName(), leftTragusPoint.getScreenCordinates().x,leftTragusPoint.getScreenCordinates().y);        
        g1.drawString(rightTragusPoint.getCordinateName(), rightTragusPoint.getScreenCordinates().x,rightTragusPoint.getScreenCordinates().y);  
        
       
        //Step 2
        
        //FPz
        double tenPrtGap = 0.10 * this.model.getHeight(); //10 % of the height
        CordinatePoints fPzPoint = getFPzPoint(tenPrtGap, nasionPoint);
        cordinateList.addCordinate(fPzPoint);
        g1.drawString(fPzPoint.getCordinateName(), fPzPoint.getScreenCordinates().x,fPzPoint.getScreenCordinates().y);
        
        //Oz
        CordinatePoints ozPoint =getOzPoint(tenPrtGap,inionPoint);
        cordinateList.addCordinate(ozPoint);
        g1.drawString(ozPoint.getCordinateName(), ozPoint.getScreenCordinates().x,ozPoint.getScreenCordinates().y); 
    
        //Step 4 
        double twentyPrtGap = 0.20 * this.model.getHeight() ; //20 % of the length
        CordinatePoints fzPoint = getFzPoint(twentyPrtGap, czPoint);
        cordinateList.addCordinate(fzPoint);   
        g1.drawString(fzPoint.getCordinateName(), fzPoint.getScreenCordinates().x,fzPoint.getScreenCordinates().y); 
        
        CordinatePoints PzPoint = getPzPoint(twentyPrtGap, czPoint);
        cordinateList.addCordinate(PzPoint);
        g1.drawString(PzPoint.getCordinateName(), PzPoint.getScreenCordinates().x,PzPoint.getScreenCordinates().y); 
      
        //Step 7
        double leftGap =0.10 * model.getWidth();
        CordinatePoints t3Point = getT3Point(leftGap, leftTragusPoint);
        cordinateList.addCordinate(t3Point);
        g1.drawString(t3Point.getCordinateName(), t3Point.getScreenCordinates().x,t3Point.getScreenCordinates().y); 
        
        
        CordinatePoints t4Point = getT4Point(leftGap, rightTragusPoint);
        cordinateList.addCordinate(t4Point);
        g1.drawString(t4Point.getCordinateName(), t4Point.getScreenCordinates().x,t4Point.getScreenCordinates().y); 
        
        //Step 9
        double c3Gap = 0.50D * getDistancePixels(t3Point,czPoint);
        double c4Gap = 0.50D * getDistancePixels(t4Point,czPoint);
        
        CordinatePoints c3Point = getC3Point(c3Gap, czPoint);
        CordinatePoints c4Point = getC4Point(c4Gap, czPoint);
        cordinateList.addCordinate(c3Point);
        cordinateList.addCordinate(c4Point);
        
     
        g1.drawString(c3Point.getCordinateName(), c3Point.getScreenCordinates().x,c3Point.getScreenCordinates().y); 
        g1.drawString(c4Point.getCordinateName(), c4Point.getScreenCordinates().x,c4Point.getScreenCordinates().y); 
        
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
        
        //288
        for (double angle = 0; angle <= 360.0 ; angle = angle + 36)
        {
              CordinatePoints pointOnInnerCircle = CalculatePointsOnEllipse(angle,aLenInnerCircle, bLenInnerCircle);            
              if (innerCircleCordinateMaps.getOrDefault(angle, "NA") !="NA"){
                    pointOnInnerCircle.setCordinateName(innerCircleCordinateMaps.getOrDefault(angle,"NA"));     
                    cordinateList.addCordinate(pointOnInnerCircle);
                    g1.drawString(pointOnInnerCircle.getCordinateName(), pointOnInnerCircle.getScreenCordinates().x,pointOnInnerCircle.getScreenCordinates().y);    
              }  
        } 
        
        CordinatePoints p1 = cordinateList.getCordinatePointByName("F7");
        CordinatePoints p2 = fzPoint;
        CordinatePoints p3 = cordinateList.getCordinatePointByName("F8");
        
        QuadCurve2D.Double firstHalfCurve = getCurveLine(p1,p2,p3);
       //g1.draw(firstHalfCurve);
        
        Rectangle boundedRect = firstHalfCurve.getBounds();
        double halfHeight = boundedRect.height /2;
        double halfLength = boundedRect.width /2;
        
        double yF4 = fzPoint.getCartesianCordinates().y + halfHeight;
        double xF4 = (p3.getCartesianCordinates().x - p2.getCartesianCordinates().x)/2;
        
        CordinatePoints F4Point = new CordinatePoints((int)xF4,(int)yF4,"F4");
        cordinateList.addCordinate(F4Point);
        g1.drawString(F4Point.getCordinateName(), F4Point.getScreenCordinates().x,F4Point.getScreenCordinates().y);    
        
        double yF3 =  yF4;
        double xF3 = -xF4;
        
        CordinatePoints F3Point = new CordinatePoints((int)xF3,(int)yF3,"F3");
        cordinateList.addCordinate(F3Point);
        g1.drawString(F3Point.getCordinateName(), F3Point.getScreenCordinates().x,F3Point.getScreenCordinates().y);  

        
        QuadCurve2D.Double secondHalfCurve = getCurveLine(
                cordinateList.getCordinatePointByName("T5"),
                cordinateList.getCordinatePointByName("Pz"),
                cordinateList.getCordinatePointByName("T6"));
       
       // g1.draw(secondHalfCurve);
        
        Rectangle boundedRectSecondHalf = secondHalfCurve.getBounds();
        halfHeight = boundedRectSecondHalf.height /2;
        halfLength = boundedRectSecondHalf.width /2;
        
        double yP4 = PzPoint.getCartesianCordinates().y - halfHeight;
        double xP4 = (cordinateList.getCordinatePointByName("T6").getCartesianCordinates().x - cordinateList.getCordinatePointByName("Pz").getCartesianCordinates().x)/2;
        
        CordinatePoints P4Point = new CordinatePoints((int)xP4,(int)yP4,"P4");
        cordinateList.addCordinate(P4Point);
        g1.drawString(P4Point.getCordinateName(), P4Point.getScreenCordinates().x,P4Point.getScreenCordinates().y); 
        
        double yP3 =  yP4;
        double xP3 = -xP4;
        
        CordinatePoints P3Point = new CordinatePoints((int)xP3,(int)yP3,"P3");
        cordinateList.addCordinate(P3Point);
        g1.drawString(P3Point.getCordinateName(), P3Point.getScreenCordinates().x,P3Point.getScreenCordinates().y);  
    }
    
    public QuadCurve2D.Double getCurveLine(CordinatePoints firstPoint, CordinatePoints middlePoint , CordinatePoints endPoint ){
    
        QuadCurve2D.Double curveLine1 =new QuadCurve2D.Double();    
        curveLine1.setCurve((double)(firstPoint.getScreenCordinates().x),
                (double)(firstPoint.getScreenCordinates().y),
                (double)(middlePoint.getScreenCordinates().x),
                (double)(middlePoint.getScreenCordinates().y),
                (double)(endPoint.getScreenCordinates().x),
                (double)(endPoint.getScreenCordinates().y));
        
        return curveLine1;
    }
    

    public  double getCircumferenceOfEllipse(double aLen, double bLen){
        
        double circumference = Math.PI * ( 3 * (bLen +aLen ) - Math.sqrt(      (3*bLen + aLen) * (bLen+3*aLen)     ));
        return circumference;
    }
    
    public CordinatePoints CalculatePointsOnEllipse(double angle ,double aLen , double bLen){
            
        double valueInRadian = Math.toRadians(angle);
        double x = aLen * Math.cos(valueInRadian);
        double y = bLen * Math.sin(valueInRadian);

        int xT = (int)x;
        int yT = (int)y;

        CordinatePoints point = new CordinatePoints(xT,yT);        
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
    
    CordinateDetailCalculator  dtCalculator = null;
    CordinateListController    cordinateList = null;
    
    private Map<Double,String> innerCircleCordinateMaps;
    ScullModelElliptical model =null;
    static double conversionFactor = 512D;
    double CF = 0;
        
}
