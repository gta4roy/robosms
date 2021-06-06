/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robo.sms.calculator;

import com.robo.sms.model.CordinatePoints;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Anushree_Bose
 */
public class CordinateListController {
    
    public CordinateListController(){   
    }
    public void  addCordinate(CordinatePoints pnt){
        listOfCordinates.add(pnt);
    }
    
    public CordinatePoints getCordinatePointByName(String pName){
         for (CordinatePoints p : listOfCordinates){
            if(p.getCordinateName().equals(pName)){
                return p;
            }
        }
        return null;
    }
    
    public CordinatePoints getCordinatePoint(Point p1){
        for (CordinatePoints p : listOfCordinates){
            if(p.getScreenCordinates().x == p1.x && p.getScreenCordinates().y == p1.y){
                return p;
            }else{
                double distanceBetweenThePoints = p.getScreenCordinates().distance(p1);
                if(distanceBetweenThePoints <= 10.0D){
                    return p;
                }
            }
        }
        return null;
    }
    private List<CordinatePoints> listOfCordinates = new ArrayList<CordinatePoints>();
}
