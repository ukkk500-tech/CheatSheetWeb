package com.cheatsheet.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.dao.CategoryDAO;
import com.cheatsheet.model.Category;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home") // ဒီနေရာမှာ browser ကခေါ်မယ့် path ကို /home လို့ ပြင်လိုက်ပါ
public class HomeServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;
    
    // DAO ကို အပေါ်မှာ တစ်ခါတည်း ကြေညာထားမယ်
    private CategoryDAO categoryDAO = new CategoryDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
    }

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // --- ဒီအပိုင်းကို အသစ်ထည့်ပါ (Session စစ်ဆေးခြင်း) ---
        javax.servlet.http.HttpSession session = request.getSession();
        if (session.getAttribute("currentUser") == null) {
            // Login မဝင်ထားရင် login page ကို ပြန်လွှတ်မယ်
            response.sendRedirect("login"); 
            return; // အောက်က code တွေကို ဆက်အလုပ်မလုပ်စေဖို့ return ပြန်ရမယ်
        }
        // -------------------------------------------

        // ၁။ Database ကနေ Category list ကို ယူမယ်
        List<Category> categoryList = categoryDAO.getAllCategories();
        
        // ၂။ JSP မှာ သုံးနိုင်အောင် Request ထဲ ထည့်မယ်
        request.setAttribute("categories", categoryList);
        
        // ၃။ home.jsp ဆီကို လမ်းကြောင်းလွှဲပေးမယ်
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // အခုလောလောဆယ် Display ပဲ လုပ်မှာမလို့ doGet ကိုပဲ ပြန်ခေါ်ထားပါမယ်
  doGet(request, response);
 }

}