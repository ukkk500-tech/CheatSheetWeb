package com.cheatsheet.controller;

import com.cheatsheet.dao.ExerciseDAO;
import com.cheatsheet.model.ExerciseDetail;
import java.io.*;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ExerciseDetailServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10)
public class ExerciseDetailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");
	    String idParam = request.getParameter("id");

	    // Parameter မပါလာရင် Error မတက်အောင် Check လုပ်တာပါ
	    if (idParam == null || idParam.isEmpty()) {
	        response.sendRedirect("ExerciseServlet");
	        return;
	    }

	    int muscleId = Integer.parseInt(idParam);
	    ExerciseDAO dao = new ExerciseDAO();

	    try {
	        // ၁။ Delete လုပ်တဲ့ အပိုင်း
	        if ("delete".equals(action)) {
	            int detailId = Integer.parseInt(request.getParameter("detailId"));
	            dao.deleteExerciseDetail(detailId);
	            response.sendRedirect("ExerciseDetailServlet?id=" + muscleId);
	            return;
	        }

	        // ၂။ Exercise Details တွေကို ဆွဲထုတ်တာ
	        List<ExerciseDetail> details = dao.getDetailsByMuscleId(muscleId);

	        // ၃။ 🔥 ဒီအပိုင်းက အရေးကြီးဆုံးပါ 🔥
	        // Muscle ID ကနေတစ်ဆင့် သူ့ရဲ့ Category ID (ဥပမာ- Gym သို့မဟုတ် Home) ကို ရှာတာပါ
	        // (မှတ်ချက် - ဒီ method ကို ExerciseDAO ထဲမှာ ထည့်ထားဖို့ လိုပါမယ်)
	        int categoryId = dao.getCategoryIdByMuscleId(muscleId);

	        // JSP ဆီကို Data တွေ ပို့မယ်
	        request.setAttribute("details", details);
	        request.setAttribute("muscleId", muscleId);
	        request.setAttribute("categoryId", categoryId); // BACK ခလုတ်အတွက်

	        request.getRequestDispatcher("exercise_details.jsp").forward(request, response);

	    } catch (SQLException e) { 
	        throw new ServletException(e); 
	    }
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8"); // မြန်မာစာအတွက်
	    String action = request.getParameter("action");
	    int muscleId = Integer.parseInt(request.getParameter("muscleId"));
	    
	    ExerciseDetail detail = new ExerciseDetail();
	    detail.setExerciseId(muscleId);
	    detail.setExerciseName(request.getParameter("exerciseName"));
	    detail.setSetsReps(request.getParameter("setsReps"));
	    detail.setDescription(request.getParameter("description"));

	    // ၁။ ပုံသိမ်းမယ့် Folder ဆောက်တဲ့အပိုင်း
	    Part filePart = request.getPart("imageFile");
	    if (filePart != null && filePart.getSize() > 0) {
	        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	        
	        // Tomcat ရဲ့ Temporary Path (Eclipse ထဲက tmp0 နေရာ)
	        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
	        
	        // Folder မရှိရင် ဆောက်ခိုင်းမယ် (ဒါမှ NoSuchFileException မတက်မှာပါ)
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdirs(); 
	        }

	        // ပုံကို File အဖြစ် သိမ်းဆည်းမယ်
	        filePart.write(uploadPath + File.separator + fileName);
	        detail.setImageUrl("uploads/" + fileName);
	    } else {
	        // ပုံမတင်ရင် ပုံဟောင်းကိုပဲ ပြန်သုံးမယ် (Edit အတွက်)
	        detail.setImageUrl(request.getParameter("oldImageUrl"));
	    }

	    ExerciseDAO dao = new ExerciseDAO();
	    try {
	        if ("update".equals(action)) {
	            detail.setId(Integer.parseInt(request.getParameter("detailId")));
	            dao.updateExerciseDetail(detail);
	        } else {
	            dao.addExerciseDetail(detail);
	        }
	        response.sendRedirect("ExerciseDetailServlet?id=" + muscleId);
	    } catch (SQLException e) { 
	        throw new ServletException(e); 
	    }
	}
}