package com.cheatsheet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.cheatsheet.model.DietDetail;
import com.cheatsheet.config.DBConnection;

public class DietDetailDAO {
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    // Plan ID အလိုက် အသေးစိတ်အချက်အလက်များ ဆွဲထုတ်ရန်
    public List<DietDetail> getDetailsByPlanId(int planId) throws SQLException {
        List<DietDetail> details = new ArrayList<>();
        String sql = "SELECT * FROM diet_details WHERE plan_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, planId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                DietDetail detail = new DietDetail();
                detail.setId(rs.getInt("id"));
                detail.setPlanId(rs.getInt("plan_id"));
                detail.setMealTime(rs.getString("meal_time"));
                detail.setFoodItems(rs.getString("food_items"));
                detail.setProteinGrams(rs.getString("protein_grams"));
                detail.setCalories(rs.getString("calories"));
                details.add(detail);
            }
        }
        return details;
    }

    public void addDietDetail(DietDetail detail) throws SQLException {
        String sql = "INSERT INTO diet_details (plan_id, meal_time, food_items, protein_grams, calories) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setInt(1, detail.getPlanId());
            st.setString(2, detail.getMealTime());
            st.setString(3, detail.getFoodItems());
            st.setString(4, detail.getProteinGrams());
            st.setString(5, detail.getCalories());
            
            st.executeUpdate();
        }
    }
    public void deleteDietDetail(int id) throws SQLException {
        String sql = "DELETE FROM diet_details WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }
}