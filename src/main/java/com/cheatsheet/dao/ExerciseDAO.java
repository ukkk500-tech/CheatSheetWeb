package com.cheatsheet.dao;

import com.cheatsheet.config.DBConnection;
import com.cheatsheet.model.Exercise;
import com.cheatsheet.model.ExerciseDetail;
import java.sql.*;
import java.util.*;
public class ExerciseDAO {

    // ၁။ Muscle Groups သိမ်းဆည်းရန် (Image URL ပါဝင်သည်)
    public int addExercise(Exercise ex) throws SQLException {
        String sql = "INSERT INTO exercises (category_id, sub_category, image_url) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, ex.getCategoryId());
            st.setString(2, ex.getSubCategory());
            st.setString(3, ex.getImageUrl());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // ၂။ Muscle Groups List ပြသရန် (Image ပါဝင်သည်)
    public List<Exercise> getMuscleGroupsWithImages(int catId) throws SQLException {
        List<Exercise> list = new ArrayList<>();
        String sql = "SELECT * FROM exercises WHERE category_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, catId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(new Exercise(rs.getInt("id"), rs.getInt("category_id"), 
                                        rs.getString("sub_category"), rs.getString("image_url")));
                }
            }
        }
        return list;
    }

    // ၃။ Exercise Details များကို JOIN ပြီး ဆွဲထုတ်ရန်
    public List<Map<String, Object>> getFullExerciseData(int catId, String muscle) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT d.exercise_name, d.sets_reps, d.description, d.image_url " +
                     "FROM exercises e JOIN exercise_details d ON e.id = d.exercise_id " +
                     "WHERE e.category_id = ? AND e.sub_category = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, catId);
            st.setString(2, muscle);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", rs.getString("exercise_name"));
                    map.put("reps", rs.getString("sets_reps"));
                    map.put("desc", rs.getString("description"));
                    map.put("img", rs.getString("image_url"));
                    list.add(map);
                }
            }
        }
        return list;
    }
 // ၄။ Muscle Group ကို ပြန်ဖျက်ရန်
    public void deleteExercise(int id) throws SQLException {
        String sql = "DELETE FROM exercises WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    // ၅။ Muscle Group ကို ပြန်ပြင်ရန်
    public void updateExercise(Exercise ex) throws SQLException {
        String sql;
        boolean hasImage = (ex.getImageUrl() != null && !ex.getImageUrl().isEmpty());

        if (hasImage) {
            // ပုံပါရင် နာမည်ရော ပုံပါ Update လုပ်မယ်
            sql = "UPDATE exercises SET sub_category = ?, image_url = ? WHERE id = ?";
        } else {
            // ပုံမပါရင် နာမည်ပဲ Update လုပ်မယ်
            sql = "UPDATE exercises SET sub_category = ? WHERE id = ?";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setString(1, ex.getSubCategory());
            
            if (hasImage) {
                st.setString(2, ex.getImageUrl());
                st.setInt(3, ex.getId());
            } else {
                st.setInt(2, ex.getId());
            }
            
            st.executeUpdate();
        }
    }
 // --- ၆။ Exercise Detail အသစ်ထည့်ရန် ---
    public void addExerciseDetail(ExerciseDetail d) throws SQLException {
        String sql = "INSERT INTO exercise_details (exercise_id, exercise_name, sets_reps, description, image_url) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, d.getExerciseId());
            st.setString(2, d.getExerciseName());
            st.setString(3, d.getSetsReps());
            st.setString(4, d.getDescription());
            st.setString(5, d.getImageUrl());
            st.executeUpdate();
        }
    }

    // --- ၇။ Exercise Detail တစ်ခုကို ပြန်ဖျက်ရန် ---
    public void deleteExerciseDetail(int id) throws SQLException {
        String sql = "DELETE FROM exercise_details WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        }
    }

    // --- ၈။ Exercise Detail ကို ပြန်ပြင်ရန် ---
    public void updateExerciseDetail(ExerciseDetail d) throws SQLException {
        String sql;
        boolean hasImage = (d.getImageUrl() != null && !d.getImageUrl().isEmpty());

        if (hasImage) {
            sql = "UPDATE exercise_details SET exercise_name = ?, sets_reps = ?, description = ?, image_url = ? WHERE id = ?";
        } else {
            sql = "UPDATE exercise_details SET exercise_name = ?, sets_reps = ?, description = ? WHERE id = ?";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setString(1, d.getExerciseName());
            st.setString(2, d.getSetsReps());
            st.setString(3, d.getDescription());
            
            if (hasImage) {
                st.setString(4, d.getImageUrl());
                st.setInt(5, d.getId());
            } else {
                st.setInt(4, d.getId());
            }
            
            st.executeUpdate();
        }
    }

    // --- ၉။ Muscle ID အလိုက် Exercise Details အားလုံးကို ဆွဲထုတ်ရန် ---
    public List<ExerciseDetail> getDetailsByMuscleId(int muscleId) throws SQLException {
        List<ExerciseDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM exercise_details WHERE exercise_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, muscleId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    // ဒီနေရာမှာ parameter ၆ ခုလုံး အတိအကျ ဖြစ်ရပါမယ်
                    list.add(new ExerciseDetail(
                        rs.getInt("id"),
                        rs.getInt("exercise_id"),
                        rs.getString("exercise_name"),
                        rs.getString("sets_reps"),
                        rs.getString("description"),
                        rs.getString("image_url") // ဒီစာကြောင်း ပါမှ Error ပျောက်မှာပါ
                    ));
                }
            }
        }
        return list;
    }
    public int getCategoryIdByMuscleId(int muscleId) throws SQLException {
        String sql = "SELECT category_id FROM exercises WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, muscleId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("category_id");
                }
            }
        }
        return 0; // မတွေ့ရင်
    }
}