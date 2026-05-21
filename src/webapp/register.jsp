<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Account | Dev CheatSheet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/register.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet">
</head>
<body>

    <div class="register-container">
        <div class="register-header">
            <h2>Sign Up</h2>
            <p class="text-muted">Create your free account</p>
        </div>

        <c:if test="${not empty errorMsg}">
            <div class="error-msg">
                ${errorMsg}
            </div>
        </c:if>

        <form action="register" method="post">
            <div class="mb-3">
                <label class="form-label small fw-bold">Username</label>
                <input type="text" name="username" class="form-control" placeholder="Choose a username" required>
            </div>
            
            <div class="mb-3">
                <label class="form-label small fw-bold">Password</label>
                <input type="password" name="password" class="form-control" placeholder="Create a strong password" required>
            </div>

            <button type="submit" class="btn-register">Register Now</button>
        </form>

        <div class="login-link">
            Already have an account? <a href="login">Login here</a>
        </div>
        
        <div class="text-center mt-3">
            <a href="home" class="text-muted small" style="text-decoration:none;">← Back to Home</a>
        </div>
    </div>

</body>
</html>