package com.cheatsheet.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.cheatsheet.model.DietPlan;
import com.cheatsheet.config.DBConnection;

public class DietDAO {
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection(); 
    }

    public List<DietPlan> getPlansByCategoryId(int categoryId) throws SQLException {
        List<DietPlan> plans = new ArrayList<>();
        String sql = "SELECT * FROM diet_plans WHERE category_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                DietPlan plan = new DietPlan();
                plan.setId(rs.getInt("id"));
                plan.setCategoryId(rs.getInt("category_id"));
                plan.setPlanName(rs.getString("plan_name"));
                plan.setPlanType(rs.getString("plan_type"));
                plan.setImageUrl(rs.getString("image_url")); // DB မှ image_url ကို ဆွဲထုတ်သည်
                plans.add(plan);
            }
        }
        return plans;
    }

    public void addDietPlan(DietPlan plan) throws SQLException {
        String sql = "INSERT INTO diet_plans (category_id, plan_name, plan_type, image_url) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, plan.getCategoryId());
            st.setString(2, plan.getPlanName());
            st.setString(3, plan.getPlanType());
            st.setString(4, plan.getImageUrl());
            st.executeUpdate();
        }
    }

    public void deleteDietPlan(int id) throws SQLException {
        String sql = "DELETE FROM diet_plans WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }
    public void updateDietPlan(DietPlan plan) throws SQLException {
        String sql = "UPDATE diet_plans SET plan_name=?, plan_type=?, image_url=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, plan.getPlanName());
            st.setString(2, plan.getPlanType());
            st.setString(3, plan.getImageUrl());
            st.setInt(4, plan.getId());
            st.executeUpdate();
        }
    }
}