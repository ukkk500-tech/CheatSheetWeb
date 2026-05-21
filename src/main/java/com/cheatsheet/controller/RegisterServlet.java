package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cheatsheet.dao.UserDAO;
import com.cheatsheet.model.User;

@WebServlet("/register") // URL ကို /register လို့ ပြင်လိုက်ပါ
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    public RegisterServlet() {
        super();
    }

    /**
     * GET method: Register Page ကို ပြသရန်
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // register.jsp ဖိုင်ဆီကို ပို့ပေးမယ်
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    /**
     * POST method: Form ကလာတဲ့ Data တွေကို Database ထဲ သိမ်းရန်
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ၁။ Form ထဲက ရိုက်လိုက်တဲ့ အချက်အလက်တွေကို ယူမယ်
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        
        // ၂။ User Object အသစ်တစ်ခု ဆောက်မယ်
        User newUser = new User();
        newUser.setUsername(name);
        newUser.setPassword(pass);
        newUser.setRole("USER"); // Default role ကို USER လို့ သတ်မှတ်မယ်

        // ၃။ DAO သုံးပြီး DB ထဲ ထည့်မယ်
        if (userDAO.register(newUser)) {
            // အောင်မြင်ရင် Login Page ကို ပို့မယ် (အောင်မြင်ကြောင်း Message လေးပါးလိုက်မယ်)
            response.sendRedirect("login.jsp?status=success");
        } else {
            // မအောင်မြင်ရင် Error ပြပြီး Register Page မှာပဲ ထားမယ်
            request.setAttribute("errorMsg", "Registration failed! Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}