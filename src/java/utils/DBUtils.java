package utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Thien
 */
public class DBUtils implements Serializable{
//    Do not change this code
    public static Connection makeConnection() throws ClassNotFoundException, SQLException{
        Connection conn= null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url= "jdbc:sqlserver://localhost:1433;databaseName=eRecruitment";
        conn= DriverManager.getConnection(url, "sa", "12345");
        return conn;
    }
}
