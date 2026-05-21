package com.cheatsheet.dao;

import com.cheatsheet.model.WorkoutDetail;
import com.cheatsheet.config.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailDAO {

    // ၁။ Plan ID အလိုက် Exercise List ကို ဆွဲထုတ်ခြင်း (Read)
    public List<WorkoutDetail> getDetailsByPlanId(int planId) throws SQLException {
        List<WorkoutDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM workout_details WHERE plan_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setInt(1, planId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(new WorkoutDetail(
                        rs.getInt("id"),
                        rs.getInt("plan_id"),
                        rs.getString("exercise_name"),
                        rs.getString("sets_reps"),
                        rs.getString("description"),
                        rs.getString("image_url")
                    ));
                }
            }
        }
        return list;
    }

    // ၂။ Exercise အသစ်ထည့်ခြင်း (Create)
    public void addWorkoutDetail(WorkoutDetail detail) throws SQLException {
        String sql = "INSERT INTO workout_details (plan_id, exercise_name, sets_reps, description, image_url) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setInt(1, detail.getPlanId());
            st.setString(2, detail.getExerciseName());
            st.setString(3, detail.getSetsReps());
            st.setString(4, detail.getDescription());
            st.setString(5, detail.getImageUrl());
            st.executeUpdate();
        }
    }

    // ၃။ Exercise တစ်ခုတည်းကို ဖျက်ခြင်း (Delete)
    public void deleteWorkoutDetail(int detailId) throws SQLException {
        String sql = "DELETE FROM workout_details WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setInt(1, detailId);
            st.executeUpdate();
        }
    }
}