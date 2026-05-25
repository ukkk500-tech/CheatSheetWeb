<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login | Dev CheatSheet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/login.css">
</head>
<body>

    <div class="login-container">
        <div class="login-header">
            <h2>Welcome Back</h2>
            <p class="text-muted">Please login to your account</p>
        </div>

        <c:if test="${not empty errorMsg}">
            <div class="error-msg">
                ${errorMsg}
            </div>
        </c:if>

        <form action="login" method="post">
            <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text" name="username" class="form-control" placeholder="Enter your username" required>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" name="password" class="form-control" placeholder="Enter your password" required>
            </div>

            <button type="submit" class="btn-login">Login</button>
        </form>

        <div class="register-link">
            Don't have an account? <a href="register">Register here</a>
        </div>
        
        <div class="text-center mt-3">
            <a href="home" class="text-muted small"      style="text-decoration:none;">← Back to house</a>
        </div>
    </div>
System.out.println("Helllo world");
</body>
</html>