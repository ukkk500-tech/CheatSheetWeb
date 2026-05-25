<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymSheets | Professional Fitness Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body {
            background-color: #f8fafc !important; /* မျက်စိအေးစေမယ့် Off-white */
            font-family: 'Poppins', sans-serif;
            margin: 0;
        }

        /* --- Header Section (သီးသန့်ခွဲထုတ်ထားသော အပိုင်း) --- */
        .gym-header {
            background: linear-gradient(rgba(15, 23, 42, 0.85), rgba(15, 23, 42, 0.95)), 
                        url('https://images.unsplash.com/photo-1534438327276-14e5300c3a48?q=80&w=2070&auto=format&fit=crop'); 
            background-size: cover;
            background-position: center;
            padding: 20px 0 140px 0;
            color: white;
            /* Header ကို အောက်ခြေ အစောင်းလေးလုပ်ထားတာပါ */
            clip-path: polygon(0 0, 100% 0, 100% 85%, 0 100%);
        }

        .navbar-brand {
            font-weight: 800;
            letter-spacing: 2px;
            color: #fbbf24 !important;
            font-size: 1.8rem;
        }

        .hero-content h1 {
            font-weight: 900;
            letter-spacing: -1px;
            margin-top: 60px;
            text-shadow: 2px 4px 10px rgba(0,0,0,0.3);
        }

        .hero-content p {
            color: #cbd5e1;
            max-width: 700px;
            margin: 20px auto 0;
        }

        /* --- Category Cards (Floating Layout) --- */
        .category-container {
            margin-top: -80px; /* Header ထဲကို Card လေးတွေ ကျွံဝင်နေအောင်လို့ပါ */
        }

        .category-card {
            background: white !important;
            border: none !important;
            border-radius: 24px;
            padding: 40px 30px;
            transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
            box-shadow: 0 15px 35px rgba(0,0,0,0.05);
            height: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .category-card:hover {
            transform: translateY(-15px);
            box-shadow: 0 25px 50px rgba(0,0,0,0.1);
        }

        /* --- Unique Icons --- */
        .icon-box {
            width: 90px;
            height: 90px;
            border-radius: 25px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 25px;
            font-size: 2.5rem;
            transition: 0.3s;
        }

        .icon-exercises { background: rgba(59, 130, 246, 0.1); color: #3b82f6; }
        .icon-diet { background: rgba(34, 197, 94, 0.1); color: #22c55e; }
        .icon-splits { background: rgba(239, 68, 68, 0.1); color: #ef4444; }

        .card-title {
            color: #0f172a !important;
            font-weight: 700;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .btn-warning-custom {
            background-color: #fbbf24 !important;
            color: #0f172a !important;
            font-weight: 700;
            border-radius: 15px;
            padding: 12px;
            border: none;
            width: 100%;
            transition: 0.3s;
        }
        
        .btn-warning-custom:hover {
            background-color: #f59e0b !important;
            box-shadow: 0 10px 20px rgba(251, 191, 36, 0.3);
            transform: scale(1.02);
        }
    </style>
</head>
<body>

    <header class="gym-header">
        <nav class="navbar navbar-expand-lg navbar-dark">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <i class="fas fa-dumbbell me-2"></i>gg
                </a>
                <div class="navbar-nav ms-auto align-items-center d-none d-md-flex">
                    <span class="nav-link text-white opacity-75">
                        Welcome, <span class="text-warning fw-bold">${currentUser.username}</span>
                    </span>
                    <a class="nav-link btn btn-outline-info btn-sm rounded-pill px-3 mx-2" href="${pageContext.request.contextPath}/myPlan">My Plan</a>
                    <a class="nav-link btn btn-danger btn-sm rounded-pill px-3" href="${pageContext.request.contextPath}/logout">Logout</a>
                </div>
            </div>
        </nav>

        <div class="container hero-content text-center">
            <div class="badge bg-warning text-dark mb-3 px-3 py-2 rounded-pill fw-bold">NO PAIN NO GAIN</div>
            <h1 class="display-3">UNLEASH YOUR <span class="text-warning">POTENTIAL</span></h1>
            <p class="lead">Everything you need for your transformation—structured splits, detailed guides, and meal plans.</p>
        </div>
    </header>

    <div class="container category-container pb-5">
        <div class="row g-4 justify-content-center">
            <c:forEach var="cat" items="${categories}">
                <div class="col-lg-4 col-md-6">
                    <div class="card category-card text-center">
                        
                        <c:choose>
                            <c:when test="${cat.categoryName.equalsIgnoreCase('EXERCISES')}">
                                <div class="icon-box icon-exercises">
                                    <i class="fas fa-dumbbell"></i>
                                </div>
                            </c:when>
                            <c:when test="${cat.categoryName.equalsIgnoreCase('DIET PLANS')}">
                                <div class="icon-box icon-diet">
                                    <i class="fas fa-apple-whole"></i>
                                </div>
                            </c:when>
                            <c:when test="${cat.categoryName.equalsIgnoreCase('WORKOUT SPLITS')}">
                                <div class="icon-box icon-splits">
                                    <i class="fas fa-calendar-check"></i>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="icon-box icon-exercises">
                                    <i class="fas fa-bolt"></i>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <div class="card-body p-0 d-flex flex-column">
                            <h4 class="card-title mb-2">${cat.categoryName}</h4>
                            <p class="card-text text-muted mb-4">Professional guides and structured plans to reach your fitness goals.</p>
                            
                            <div class="d-grid mt-auto">
                                <c:choose>
                                    <c:when test="${cat.categoryName.equalsIgnoreCase('EXERCISES')}">
                                        <a href="ExerciseServlet?id=${cat.id}" class="btn btn-warning-custom">VIEW WORKOUTS</a>
                                    </c:when>
                                    <c:when test="${cat.categoryName.equalsIgnoreCase('DIET PLANS')}">
                                        <a href="DietServlet?id=${cat.id}" class="btn btn-warning-custom">VIEW DIET PLANS</a>
                                    </c:when>
                                    <c:when test="${cat.categoryName.equalsIgnoreCase('WORKOUT SPLITS')}">
                                        <a href="WorkoutPlanServlet" class="btn btn-warning-custom">VIEW SPLITS</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="ExerciseServlet?id=${cat.id}" class="btn btn-warning-custom">EXPLORE</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>