/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.String;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
/**
 *
 * @author burabiah
 */
public class Faculty{
    
    static void addFaculty(String facname){
        

    try {
            
            Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
            PreparedStatement ps = dbconnect.prepareStatement("INSERT INTO FACULTY (NAME) VALUES (?)");
            ps.setString(1, facname);
            ps.executeUpdate();

            
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void getFaculty() throws SQLException{

        try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT \"NAME\" FROM JAVA.FACULTY FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String facnames = rs.getString("name");
        }
        
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        
    }
    }
        
    public static void FillCombo(JComboBox facultyResCB) {
         try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT \"NAME\" FROM JAVA.FACULTY FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String facnames = rs.getString("name");
            facultyResCB.addItem(facnames);
        }
        
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        
    }
    }
        
    
    
}
