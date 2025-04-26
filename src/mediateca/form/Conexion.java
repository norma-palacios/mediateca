/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mediateca.form;

import java.sql.*;

/**
 *
 * @author maria
 */
public class Conexion {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //El puertoesopcional
    private static final String JDBC_URL = "jdbc:mysql://localhost/mediateca?useSSL=false";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";
    private static Driver driver = null;

    public static synchronized Connection getConnection()
            throws SQLException {
        if (driver == null) {
            try {
//Se registra el driver
                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver) jdbcDriverClass.getDeclaredConstructor().newInstance();
                DriverManager.registerDriver(driver);
            } catch (Exception e) {
                System.out.println("Fallo en cargar el driver JDBC");
                e.printStackTrace();
            }
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    //Cierre del resultSet
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
//Cierre del PrepareStatement

    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void close(Connection conn) {//Cierre de la conexion
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
