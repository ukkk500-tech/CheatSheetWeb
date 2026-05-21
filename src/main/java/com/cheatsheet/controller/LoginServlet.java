package com.cheatsheet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cheatsheet.dao.UserDAO;
import com.cheatsheet.model.User;

@WebServlet("/login") // URL ကို အတိုလေး /login လို့ ပြင်လိုက်ပါ
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    public LoginServlet() {
        super();
    }

    // ၁။ Login Page ကို ပြသရန် (Browser ကနေ /login လို့ ခေါ်တဲ့အခါ)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    // ၂။ Login Form Submit လုပ်တဲ့အခါ စစ်ဆေးရန်
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Form ထဲက username နဲ့ password ကို လှမ်းယူမယ်
        String name = request.getParameter("username");
        String pass = request.getParameter("password");

        // DAO သုံးပြီး DB မှာ ရှိမရှိ စစ်မယ်
        User user = userDAO.login(name, pass);

        if (user != null) {
            // Login အောင်မြင်ရင် Session ထဲ ထည့်ထားမယ်
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            
            // Home Page ဆီကို ပြန်ပို့မယ်
            response.sendRedirect("home");
        } else {
            // Login မအောင်မြင်ရင် Error Message ပြပြီး Login Page မှာပဲ ထားမယ်
            request.setAttribute("errorMsg", "Username သို့မဟုတ် Password မှားနေပါသည်!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}