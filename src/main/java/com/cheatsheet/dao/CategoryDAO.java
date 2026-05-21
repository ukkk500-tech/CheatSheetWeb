package com.cheatsheet.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.cheatsheet.config.DBConnection;
import com.cheatsheet.model.Category;

public class CategoryDAO {

    // Database ထဲက Category အားလုံးကို List အနေနဲ့ ဆွဲထုတ်ပေးမယ့် Method
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM Categories";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

        	while (rs.next()) {
        	    Category cat = new Category();
        	    cat.setId(rs.getInt("category_id"));
        	    cat.setCategoryName(rs.getString("category_name")); // DB column name က category_name ဖြစ်ရမယ်
        	    cat.setIconPath(rs.getString("icon_path"));
        	    list.add(cat);
        	
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}