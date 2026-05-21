package com.cheatsheet.controller;

import com.cheatsheet.dao.ExerciseDAO;
import com.cheatsheet.model.Exercise;
import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ExerciseServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, 
                 maxFileSize = 1024 * 1024 * 10,      
                 maxRequestSize = 1024 * 1024 * 50)   
public class ExerciseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        int id = Integer.parseInt(idStr);
        ExerciseDAO dao = new ExerciseDAO();

        try {
            // --- DELETE LOGIC ---
            if ("delete".equals(action)) {
                int catId = Integer.parseInt(request.getParameter("catId"));
                dao.deleteExercise(id);
                response.sendRedirect("ExerciseServlet?id=" + catId);
                return;
            }

            // --- READ LOGIC (Normal List View) ---
            List<Exercise> muscles = dao.getMuscleGroupsWithImages(id);
            request.setAttribute("muscles", muscles);
            request.setAttribute("categoryId", id);
            request.getRequestDispatcher("muscle_groups.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String muscleName = request.getParameter("subCategory");
        
        ExerciseDAO dao = new ExerciseDAO();
        Exercise ex = new Exercise();
        ex.setCategoryId(categoryId);
        ex.setSubCategory(muscleName);

        // --- FILE UPLOAD LOGIC ---
        Part filePart = request.getPart("imageFile");
        String dbPath = null;

        // Edit လုပ်တဲ့အခါ ပုံအသစ်မရွေးရင် နဂိုပုံအတိုင်းထားဖို့ စစ်ရပါမယ်
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            filePart.write(uploadPath + File.separator + fileName);
            dbPath = "uploads/" + fileName;
            ex.setImageUrl(dbPath);
        }

        try {
            if ("update".equals(action)) {
                // --- UPDATE LOGIC ---
                int muscleId = Integer.parseInt(request.getParameter("muscleId"));
                ex.setId(muscleId);
                dao.updateExercise(ex);
            } else {
                // --- CREATE LOGIC ---
                dao.addExercise(ex);
            }
            response.sendRedirect("ExerciseServlet?id=" + categoryId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}