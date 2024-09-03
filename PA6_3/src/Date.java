
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Date {
    static void addDate(String dmy){
        

    try {
            
            Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
            PreparedStatement ps = dbconnect.prepareStatement("INSERT INTO DATES (DATES) VALUES (?)");
            ps.setString(1, dmy);
            ps.executeUpdate();

            
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void getFaculty() throws SQLException{

        try{
        
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
        PreparedStatement ps = dbconnect.prepareStatement("SELECT \"DATES\" FROM JAVA.FACULTY FETCH FIRST 100 ROWS ONLY");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String dates = rs.getString("Date");
        }
        
        
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
        
    }
    }
}
