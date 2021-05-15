/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robo.sms.ui;

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
    
    private Point getNashionPoint(double length, double width, int x , int y){
        int nashionPointX =  x + (int)(width/2);
        int nashionPointY = y;
        Point na = new Point(nashionPointX,nashionPointY);
        return na;
    }
    
    private Point getInionPoint(Point nasionPoint,double length){
        int InionPointX = nasionPoint.x ;
        int InionPointY = nasionPoint.y + (int)length;
        Point na = new Point(InionPointX,InionPointY);
        return na;
    }
    
    private Point getCZPoint(Point nasionPoint, double length){
        int x = nasionPoint.x;
        int y = nasionPoint.y + (int)(length/2);
        Point na = new Point(x, y);
        return na;
    }
    
    private Point getLeftTragusPoint(Point czPoint, int xPosition){
        int x = xPosition;
        int y = czPoint.y;
        return new Point(x,y);
    }
    
    private Point getRightTragusPoint(Point czPoint,double width){
        int y = czPoint.y;
        int x = czPoint.x + (int)(width/2);
        return new Point(x,y);
    }
    
    private Point getFPzPoint(double gapDistance, Point nasionPoint){
        Point nm = new Point (nasionPoint.x, nasionPoint.y + (int)gapDistance);
        return nm;
    }
    
    private Point getOzPoint(double gapDistance, Point inionPoint){
        Point nm = new Point (inionPoint.x, inionPoint.y - (int)gapDistance);
        return nm;
    }
    
    private double getDistanceBetweenPoint(Point a, Point b){
        double distance =  a.distance(b);
        return this.CF * distance;
    }
    
    
    
    @Override
    public void paint(Graphics g)
    {
        double length = conversionFactor;
        double width = (conversionFactor * this.model.TragusToTragus) / this.model.NashionToInion;        
        this.CF = this.model.NashionToInion / conversionFactor;
        
        
        Ellipse2D ed = new Ellipse2D.Double(this.model.positionX,this.model.positionY,width,
                length);
            
        Point nasionPoint = getNashionPoint(length, width, this.model.positionX, this.model.positionY);
        Point inionPoint = getInionPoint(nasionPoint, length);
        Point czPoint = getCZPoint(nasionPoint, length);
        Point leftTragusPoint = getLeftTragusPoint(czPoint, this.model.positionX);
        Point rightTragusPoint = getRightTragusPoint(czPoint, width);
        
        
        Graphics2D g1 = (Graphics2D)g;
  
        g1.setColor(Color.RED);
  
        // draw the first ellipse
        g1.draw(ed);
  
        g1.setColor(Color.blue);
        
        g1.drawLine(nasionPoint.x, nasionPoint.y, nasionPoint.x, nasionPoint.y);
        g1.drawLine(nasionPoint.x, nasionPoint.y, inionPoint.x, inionPoint.y);
        g1.drawLine(leftTragusPoint.x,leftTragusPoint.y, rightTragusPoint.x, rightTragusPoint.y);
        
        double lengthBetweenNashionPoints = getDistanceBetweenPoint(nasionPoint, inionPoint);
        double lengthBetweenCZToNashio = getDistanceBetweenPoint(czPoint, nasionPoint);
        
        //draw labels 
        g1.drawString("Nz ["+ String.valueOf(lengthBetweenCZToNashio)+" cm]",nasionPoint.x, nasionPoint.y);
        double lnFromIzToCz = getDistanceBetweenPoint(czPoint, inionPoint);
        g1.drawString("Iz ["+String.valueOf(lnFromIzToCz)+" cm]", inionPoint.x, inionPoint.y);
        g1.drawString("Cz", czPoint.x, czPoint.y);
        
        double lnFromCztoT1 = getDistanceBetweenPoint(czPoint, leftTragusPoint);
        g1.drawString("T1 ["+String.valueOf(lnFromCztoT1)+" cm]", leftTragusPoint.x,leftTragusPoint.y);
        
        double lnFromCzToT2 = getDistanceBetweenPoint(czPoint, rightTragusPoint);
        g1.drawString("T2 ["+String.valueOf(lnFromCzToT2)+" cm]", rightTragusPoint.x,rightTragusPoint.y);  
        
        
        //Step 2
        
        //FPz
        double tenPrtGap = 0.10 * length; //10 % of the length
        Point fPzPoint = getFPzPoint(tenPrtGap, nasionPoint);
        double lnFPzToVertex = getDistanceBetweenPoint(czPoint, fPzPoint);
        g1.drawString("FPz ["+String.valueOf(lnFPzToVertex)+" cm]", fPzPoint.x,fPzPoint.y);
        
        //Oz
        Point ozPoint =getOzPoint(tenPrtGap,inionPoint);
        double lnOzToVertex = getDistanceBetweenPoint(czPoint, ozPoint);
        g1.drawString("Oz ["+String.valueOf(lnOzToVertex)+" cm]", ozPoint.x,ozPoint.y); 
    
    
    }
    ScullModelElliptical model =null;
    static double conversionFactor = 512D;
    double CF = 0;
        
}
