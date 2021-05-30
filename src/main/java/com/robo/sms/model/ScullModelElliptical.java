/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robo.sms.model;

import java.awt.geom.Ellipse2D;

/**
 *
 * @author Anushree_Bose
 */
public class ScullModelElliptical {
    private double NashionToInion;  //height in Cms
    private double TragusToTragus;  //width in Cms
    private double curcumference;    
    private CordinatePoints cartesianCenter = new CordinatePoints(0,0);
    private CordinatePoints topLeftCornerCartesian = new CordinatePoints(0,0);
    static double conversionFactor = 512D;
    /*
    Factor
    1 Cm = conversionFactor / height in CM  Pixels
    */
    private double height; //pixels 
    private double width ; //pixels 
    
    public ScullModelElliptical(CordinatePoints cartesianCenter, double ht, double wid){
        this.NashionToInion = ht;
        this.TragusToTragus = wid;
        
        this.cartesianCenter= cartesianCenter;
        this.height = conversionFactor;
        this.width = (conversionFactor * this.TragusToTragus) / this.NashionToInion;  
        
        //Calculate Screen Start Point of the Ellipse 
        int x = (cartesianCenter.getCartesianCordinates().x -  (int) (width /2.0D));
        int y = (cartesianCenter.getCartesianCordinates().y -  (int) (height /2.0D));
        
        topLeftCornerCartesian.setCartesianCordinates(x, y);
        
        System.out.println("Center "+ this.cartesianCenter.getCartesianCordinates());
        System.out.println("Top Left "+ this.topLeftCornerCartesian.getCartesianCordinates());
        System.out.println("Width "+ this.width);
        System.out.println("Height "+ this.height);
    }
    
    public Ellipse2D getEllipse(){
         Ellipse2D ed = new Ellipse2D.Double(topLeftCornerCartesian.getScreenCordinates().x,
                 topLeftCornerCartesian.getScreenCordinates().y ,width,height);
        //https://gamedev.stackexchange.com/questions/1692/what-is-a-simple-algorithm-for-calculating-evenly-distributed-points-on-an-ellip
        // x' = x + d / sqrt(1 + b²x² / (a²(a²-x²)))
        //y' = b sqrt(1 - x'²/a²)
         return ed;
    } 

    public CordinatePoints getCartesianCenter() {
        return cartesianCenter;
    }   

    public double getNashionToInion() {
        return NashionToInion;
    }

    public double getTragusToTragus() {
        return TragusToTragus;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
    
    public  double getCircumferenceOfSkullEllipse(){
        
        double a = (height / 2.0D);
        double b = (width /  2.0D);
        double circumference = Math.PI * ( 3 * (a +b ) - Math.sqrt(      (3*a + b) * (a+3*b)     ));
        return circumference;
    }
    
    
}
