package com.cheatsheet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.cheatsheet.config.DBConnection;
import com.cheatsheet.model.User;

public class UserDAO {
    
    // Login စစ်တဲ့ Method
    public User login(String username, String password) {
        String query = "SELECT * FROM Users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), 
                               rs.getString("password"), rs.getString("role"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // Register လုပ်တဲ့ Method (User အသစ်သွင်းခြင်း)
    public boolean register(User user) {
        String query = "INSERT INTO Users(username, password, role) VALUES(?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, "USER"); // Default ကတော့ ရိုးရိုး user ပဲပေးမယ်
            return pst.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}