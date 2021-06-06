/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robo.sms.model;

import java.awt.Point;

/**
 *
 * @author Anushree_Bose
 */
public class CordinatePoints {
    
    
    public CordinatePoints(int x, int y , String name){
        this(x,y);
        this.cordinateName = name;
    }
    
    public CordinatePoints(int x , int y){
       cartesianCordinates.x = x;
       cartesianCordinates.y = y;
       
       //Formula Cartesian Cordinates to Screen Cordinates 
       //screenX = cartX + screen_width/2
       //screenY = screen_height/2 - cartY
       
       
       //Formula Screen Cordinates to Cartesian Cordinates
       //cartx = screenX - screen_width /2
       //cartY = screen_height/2 - screenY 
       
       screenCordinates.x = cartesianCordinates.x + (int)(widthOfTheScreen/2);
       screenCordinates.y = (int)(heightOfTheScreen/2) - cartesianCordinates.y;
    }
    
    public String getCordinateName(){
        return this.cordinateName;
    }

    public Point getScreenCordinates() {
        return screenCordinates;
    }

    public void setScreenCordinates(int x , int y) {
        this.screenCordinates.x = x;
        this.screenCordinates.y = y;
        
        this.cartesianCordinates.x = screenCordinates.x - (int)(widthOfTheScreen /2);
        this.cartesianCordinates.y = (int)(heightOfTheScreen/2) - screenCordinates.y;
    }

    public Point getCartesianCordinates() {
        return cartesianCordinates;
    }

    public void setCartesianCordinates(int x , int y) {
        this.cartesianCordinates.x = x;
        this.cartesianCordinates.y = y;
        
        screenCordinates.x = cartesianCordinates.x + (int)(widthOfTheScreen/2);
        screenCordinates.y = (int)(heightOfTheScreen/2) + cartesianCordinates.y;
    }
    
    public void setCordinateName(String cordinateName){
        this.cordinateName = cordinateName;
    }
    
    private String cordinateName ="";
    
    private Point screenCordinates = new Point(0,0);
    private Point cartesianCordinates = new Point(0,0);
    
    private final int widthOfTheScreen = 720;
    private final int heightOfTheScreen = 720;
    
}
