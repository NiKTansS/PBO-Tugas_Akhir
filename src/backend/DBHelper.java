/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;
import java.sql.*;

/**
 *
 * @author NIKY
 */
public class DBHelper {
        private static String url = "jdbc:mysql://localhost:3306/peminjaman_lab";
        private static String user = "root";
        private static String pass = "";
public static Connection getConnection() throws SQLException {
return DriverManager.getConnection(url, user, pass);
    }
}
