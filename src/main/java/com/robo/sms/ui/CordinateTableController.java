/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robo.sms.ui;

import com.robo.sms.calculator.CordinateDetailCalculator;
import com.robo.sms.calculator.CordinateListController;
import com.robo.sms.model.CordinatePoints;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Anushree_Bose
 */
public class CordinateTableController {
    
    public CordinateTableController(CordinateDetailCalculator cordinateCalculator,CordinateListController cordinateListCtrl){
        this.dtCalculator =  cordinateCalculator;
        this.cordinateList = cordinateListCtrl;
    }
    
    public void populateTable(){
        
        List<CordinatePoints> allCordinates = cordinateList.getAllCordinates();
        data = new Object[allCordinates.size()][4];
        
        int row = 0;
        for (CordinatePoints cordinate : allCordinates){
            this.dtCalculator.calculateDetails(cordinate);   
            //"Desc", "Distance from CZ", "Arc Length", "Arc Point"
            data[row][0] = (String) dtCalculator.getDescription();
            data[row][1] = (String) String.format("%.2f cm",dtCalculator.getDistanceFromCz());
            data[row][2] = (String) String.format("%.2f cm",dtCalculator.getArcLength());
            data[row][3] = (String) dtCalculator.getRefrencePoint();
            row ++;
        }
        
        cordinateTable = new JTable(data,columnNames);
        cordinateTable.setPreferredSize(new Dimension(300,400));
        cordinateTable.setPreferredScrollableViewportSize(new Dimension(300,400));
        scrollPane = new JScrollPane(cordinateTable);
        cordinateTable.setFillsViewportHeight(true);
        cordinateTable.setVisible(true);
        scrollPane.setVisible(true);
        
    }   
    public JTable getCordinateTable(){
        return this.cordinateTable;
    }
    
    public JScrollPane getTablePanel(){ 
        return scrollPane;
    }
    private CordinateDetailCalculator  dtCalculator = null;
    private CordinateListController    cordinateList = null;
    private Object[][] data= null;
    
    private JScrollPane scrollPane = null;
    
    JTable cordinateTable = null;
    private String[] columnNames = {
        "Desc", "Distance from CZ", "Arc Length", "Arc Point"
    };
}
