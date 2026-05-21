package com.cheatsheet.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.cheatsheet.dao.DietDAO;
import com.cheatsheet.model.DietPlan;

@WebServlet("/DietServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class DietServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "assets/images";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        
        // ၁။ idParam က null ဖြစ်နေတာ သို့မဟုတ် အလွတ် "" ဖြစ်နေတာကို စစ်ပါ
        if (idParam == null || idParam.trim().isEmpty()) {
            // တန်ဖိုးမပါလာရင် home.jsp သို့မဟုတ် error မတက်မယ့် page တစ်ခုခုကို ပို့ပေးပါ
            response.sendRedirect("home.jsp"); 
            return;
        }
        
        DietDAO dao = new DietDAO();

        try {
            // ၂။ parsed မလုပ်ခင် error handle လုပ်နိုင်အောင် try-catch ထဲ ထည့်ထားပါမယ်
            int categoryId = Integer.parseInt(idParam); 

            /* Diet Plan ဖျက်သည့်အပိုင်း */
            if ("delete".equals(action)) {
                String planIdStr = request.getParameter("planId");
                if (planIdStr != null && !planIdStr.isEmpty()) {
                    int planId = Integer.parseInt(planIdStr);
                    dao.deleteDietPlan(planId);
                }
                response.sendRedirect("DietServlet?id=" + categoryId);
                return;
            }

            /* Category အလိုက် Plans များ ဆွဲထုတ်ပြီး JSP သို့ ပို့ခြင်း */
            List<DietPlan> plans = dao.getPlansByCategoryId(categoryId);
            request.setAttribute("plans", plans);
            request.setAttribute("categoryId", categoryId);
            request.getRequestDispatcher("diet_plans.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            // id က ဂဏန်းမဟုတ်ဘဲ စာသားတွေ ပါလာရင်လည်း home ကို ပြန်ပို့ပါမယ်
            response.sendRedirect("home.jsp");
        } catch (SQLException e) { 
            throw new ServletException(e); 
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String catIdStr = request.getParameter("categoryId");
        String planName = request.getParameter("planName");
        String planType = request.getParameter("planType");

        if (catIdStr == null || catIdStr.trim().isEmpty()) {
            response.sendRedirect("home.jsp");
            return;
        }

        try {
            int categoryId = Integer.parseInt(catIdStr);
            DietDAO dao = new DietDAO();

            // --- ပုံသိမ်းသည့် Logic အပိုင်း ---
            Part filePart = request.getPart("imageFile");
            String dbPath = null;

            if ("add".equals(action)) {
                dbPath = UPLOAD_DIR + "/default.jpg";
            } else if ("update".equals(action)) {
                dbPath = request.getParameter("oldImagePath"); // အရင်ဆုံး ပုံဟောင်းကို ယူထားမယ်
            }

            // ပုံအသစ် ပါလာခဲ့ရင် သိမ်းမယ်
            if (filePart != null && filePart.getSize() > 0) {
            	String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
                String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
                
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                filePart.write(uploadPath + File.separator + uniqueFileName);
                dbPath = UPLOAD_DIR + "/" + uniqueFileName;
            }

            // --- Database Logic အပိုင်း ---
            if ("add".equals(action)) {
                DietPlan plan = new DietPlan(0, categoryId, planName, planType, dbPath);
                dao.addDietPlan(plan);
            } 
            else if ("update".equals(action)) {
                int planId = Integer.parseInt(request.getParameter("planId"));
                DietPlan plan = new DietPlan(planId, categoryId, planName, planType, dbPath);
                dao.updateDietPlan(plan); // DAO မှာ update method လိုအပ်ပါသည်
            }
            
            response.sendRedirect("DietServlet?id=" + categoryId);
            
        } catch (NumberFormatException | SQLException e) {
            response.sendRedirect("home.jsp");
        }
    }
    }