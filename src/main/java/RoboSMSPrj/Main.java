/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RoboSMSPrj;
import com.robo.sms.calculator.EllipticalIntegral;
import com.robo.sms.model.CordinatePoints;
import com.robo.sms.model.ScullModelElliptical;
import com.robo.sms.ui.mainUI;
/**
 *
 * @author Anushree_Bose
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("ROBO SMS Skull Measurement System");
           /* Create and display the form */
        http://www.geocities.ws/web_sketches/ellipse_notes/ellipse_arc_length/ellipse_arc_length.html
//        double b = 2.23;
//        double a = 3.05;
//        double angle = Math.toRadians(50);
//        System.out.println("Arc Length : --- ");
//        
//        double arcLength = EllipticalIntegral.GetArcLengthOfEllipse(a, b, angle);
//        System.out.println("Arc Length : --- "+ arcLength);
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ScullModelElliptical model = new ScullModelElliptical(new CordinatePoints(0,0),36D,38D);
                mainUI ui = new mainUI(model);
                ui.setVisible(true);
            }
        });
    }
    
}
