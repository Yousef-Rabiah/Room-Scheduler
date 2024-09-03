
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author burabiah
 */
public class dbConnection {


    dbConnection() throws SQLException{
        Connection dbconnect = DriverManager.getConnection("jdbc:derby://localhost:1527/RoomScheduler", "java", "java");
    }
}
