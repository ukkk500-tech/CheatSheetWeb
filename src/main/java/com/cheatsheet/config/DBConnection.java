package com.cheatsheet.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
 
    private static final String URL = "jdbc:mysql://localhost:3306/cheatsheet_db?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; 
    private static final String PASS = "11111"; 

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
 
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Success: Database connected!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver not found! (Check your JAR file in lib)");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: Connection failed! (Check URL, User, or Pass)");
            e.printStackTrace();
        }
        return conn;
    }
}