package ra.edu.business.config;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/personnel_recruitment";
    private static final String USER = "root";
    private static final String PASS = "265205";
    public static Connection openConnection() {
        Connection conn = null;
        try{
            conn =  DriverManager.getConnection(URL, USER, PASS);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void closeConnection(Connection conn, CallableStatement call) {
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(call != null){
            try {
                call.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
