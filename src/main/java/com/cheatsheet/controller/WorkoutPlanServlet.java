package com.cheatsheet.controller;

import com.cheatsheet.dao.WorkoutDAO;
import com.cheatsheet.model.WorkoutPlan;
import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/WorkoutPlanServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10)
public class WorkoutPlanServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        WorkoutDAO dao = new WorkoutDAO();
        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.deleteWorkoutPlan(id);
                response.sendRedirect("WorkoutPlanServlet");
                return;
            }
            request.setAttribute("plans", dao.getAllWorkoutPlans());
            request.getRequestDispatcher("workout_plans.jsp").forward(request, response);
        } catch (SQLException e) { throw new ServletException(e); }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("planName");
        String type = request.getParameter("planType");
        String desc = request.getParameter("description");
        
        Part filePart = request.getPart("imageFile");
        String dbPath = "";
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            filePart.write(uploadPath + File.separator + fileName);
            dbPath = "uploads/" + fileName;
        }

        try {
            new WorkoutDAO().addWorkoutPlan(new WorkoutPlan(0, name, type, desc, dbPath));
            response.sendRedirect("WorkoutPlanServlet");
        } catch (SQLException e) { throw new ServletException(e); }
    }
}