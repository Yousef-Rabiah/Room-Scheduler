
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author burabiah
 */
public class Room {
    
    public static void addRoom(String[] rooms){
        

    try {
            
            Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
            PreparedStatement ps = dbconnect.prepareStatement("INSERT INTO ROOMS (NAME,SEATS) VALUES (?,?)");
            ps.setString(1, rooms[1]);
            ps.setString(2, rooms[2]);
            ps.executeUpdate();

            
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public static void FillCombo3(JComboBox dateCB) {
        try{
    
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT \"ROOMS\" FROM JAVA.DATES FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String dates = rs.getString("DATES");
                dateCB.addItem(dates);
                
            }
    }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
    }
        
    
    }
   
   public static int getCapacity(String room){    
        
        try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT * FROM JAVA.ROOMS FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int seats = rs.getInt("SEATS");
            String Rom = (String) rs.getString("NAME");
            
            if (room.equals(Rom) ){
            
                return seats;
            }
            
        }
        
       
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
    return 0;
    }
    
}
