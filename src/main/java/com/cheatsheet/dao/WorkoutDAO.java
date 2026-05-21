package com.cheatsheet.dao;

import com.cheatsheet.model.WorkoutPlan;
import com.cheatsheet.config.DBConnection; // DB connection class နာမည် ပြန်စစ်ပါ
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDAO {

    // ၁။ Workout Plan အားလုံးကို ဆွဲထုတ်ခြင်း (Read)
    public List<WorkoutPlan> getAllWorkoutPlans() throws SQLException {
        List<WorkoutPlan> list = new ArrayList<>();
        String sql = "SELECT * FROM workout_plans ORDER BY id DESC"; // အသစ်တင်တာ အပေါ်ရောက်အောင် DESC သုံးထားတယ်
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                list.add(new WorkoutPlan(
                    rs.getInt("id"),
                    rs.getString("plan_name"),
                    rs.getString("plan_type"),
                    rs.getString("description"),
                    rs.getString("image_url")
                ));
            }
        }
        return list;
    }

    // ၂။ Workout Plan အသစ်ထည့်ခြင်း (Create)
    public void addWorkoutPlan(WorkoutPlan plan) throws SQLException {
        String sql = "INSERT INTO workout_plans (plan_name, plan_type, description, image_url) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setString(1, plan.getPlanName());
            st.setString(2, plan.getPlanType());
            st.setString(3, plan.getDescription());
            st.setString(4, plan.getImageUrl());
            st.executeUpdate();
        }
    }

    // ၃။ Workout Plan ဖျက်ခြင်း (Delete)
    public void deleteWorkoutPlan(int id) throws SQLException {
        String sql = "DELETE FROM workout_plans WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setInt(1, id);
            st.executeUpdate();
        }
    }
    
}