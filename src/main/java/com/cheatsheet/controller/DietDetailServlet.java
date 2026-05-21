package com.cheatsheet.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.cheatsheet.dao.DietDetailDAO;
import com.cheatsheet.model.DietDetail;

@WebServlet("/DietDetailServlet")
public class DietDetailServlet extends HttpServlet {
	// DietDetailServlet.java ထဲက doGet ကို ဒီလိုပြင်ပါ
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");
	    String planIdParam = request.getParameter("id");
	    String catIdParam = request.getParameter("categoryId");

	    // CRITICAL: "null" (စာသား) ဖြစ်နေရင်လည်း parseInt မလုပ်အောင် စစ်ရပါမယ်
	    if (planIdParam == null || planIdParam.trim().isEmpty() || planIdParam.equals("null")) { 
	        response.sendRedirect("DietServlet?id=" + catIdParam); 
	        return; 
	    }

	    DietDetailDAO dao = new DietDetailDAO();

	    try {
	        int planId = Integer.parseInt(planIdParam); // ဒီနေရာမှာ Error မတက်တော့ပါဘူး

	     // doGet ထဲက delete action စစ်တဲ့နေရာမှာ ဒီလိုလေး ပြင်ပေးပါ
	        if ("delete".equals(action)) {
	            String detailIdStr = request.getParameter("detailId");
	            if (detailIdStr != null && !detailIdStr.trim().isEmpty()) {
	                int detailId = Integer.parseInt(detailIdStr);
	                dao.deleteDietDetail(detailId);
	            }
	            
	            // ဒီနေရာမှာ DietServlet လို့ မရေးဘဲ DietDetailServlet လို့ ပြောင်းရေးရပါမယ်
	            // id parameter က plan id (ဥပမာ id=13) ဖြစ်ရပါမယ်
	            response.sendRedirect("DietDetailServlet?id=" + planIdParam + "&categoryId=" + catIdParam);
	            return; 
	        }

	        // ၂။ List ပြသည့်အပိုင်း
	        List<DietDetail> details = dao.getDetailsByPlanId(planId);
	        
	        request.setAttribute("details", details);
	        request.setAttribute("planId", planId);
	        request.setAttribute("categoryId", catIdParam); 
	        
	        request.getRequestDispatcher("diet_details.jsp").forward(request, response);
	        
	    } catch (NumberFormatException e) {
	        response.sendRedirect("DietServlet?id=" + catIdParam);
	    } catch (SQLException e) { 
	        throw new ServletException(e); 
	    }
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // ၁။ planId ရော categoryId ကိုပါ ယူရပါမယ်
	    int planId = Integer.parseInt(request.getParameter("planId"));
	    String categoryId = request.getParameter("categoryId"); // Back button အတွက် လိုအပ်ပါတယ်
	    
	    String mealTime = request.getParameter("mealTime");
	    String foodItems = request.getParameter("foodItems");
	    String protein = request.getParameter("proteinGrams");
	    String calories = request.getParameter("calories");

	    DietDetailDAO dao = new DietDetailDAO();
	    try {
	        DietDetail detail = new DietDetail(0, planId, mealTime, foodItems, protein, calories);
	        dao.addDietDetail(detail);
	        
	        // ၂။ redirect လုပ်တဲ့အခါ categoryId ကိုပါ တွဲပို့မှ Back button မပျောက်မှာပါ
	        response.sendRedirect("DietDetailServlet?id=" + planId + "&categoryId=" + categoryId);
	    } catch (SQLException e) {
	        throw new ServletException(e);
	    }
	}
}