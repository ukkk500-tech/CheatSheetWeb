package com.cheatsheet.controller;

import com.cheatsheet.dao.WorkoutDetailDAO; // DAO အသစ်ကို Import လုပ်ပါ
import com.cheatsheet.model.WorkoutDetail;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/WorkoutDetailServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, 
                 maxFileSize = 1024 * 1024 * 10,      
                 maxRequestSize = 1024 * 1024 * 50)   
public class WorkoutDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WorkoutDetailServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String planIdStr = request.getParameter("id");
        String action = request.getParameter("action");
        
        if (planIdStr == null || planIdStr.isEmpty()) {
            response.sendRedirect("WorkoutPlanServlet");
            return;
        }

        int planId = Integer.parseInt(planIdStr);
        // ✅ WorkoutDAO အစား WorkoutDetailDAO ကို ပြောင်းသုံးလိုက်ပါပြီ
        WorkoutDetailDAO detailDAO = new WorkoutDetailDAO();

        try {
            if ("delete".equals(action)) {
                int detailId = Integer.parseInt(request.getParameter("detailId"));
                detailDAO.deleteWorkoutDetail(detailId); // DAO အသစ်က method ကိုခေါ်တယ်
                response.sendRedirect("WorkoutDetailServlet?id=" + planId);
                return;
            }

            List<WorkoutDetail> details = detailDAO.getDetailsByPlanId(planId);
            request.setAttribute("details", details);
            request.setAttribute("planId", planId);
            request.getRequestDispatcher("workout_details.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        int planId = Integer.parseInt(request.getParameter("planId"));
        String exerciseName = request.getParameter("exerciseName");
        String setsReps = request.getParameter("setsReps");
        String description = request.getParameter("description");
        
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

        WorkoutDetail detail = new WorkoutDetail(0, planId, exerciseName, setsReps, description, dbPath);
        // ✅ ဒီမှာလည်း WorkoutDetailDAO ကို ပြောင်းသုံးပါ
        WorkoutDetailDAO detailDAO = new WorkoutDetailDAO();

        try {
            detailDAO.addWorkoutDetail(detail);
            response.sendRedirect("WorkoutDetailServlet?id=" + planId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}