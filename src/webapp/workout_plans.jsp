<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Workout Plans</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #1a1a1a; color: white; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .card { background: #2d2d2d; border-radius: 15px; border: none; overflow: hidden; transition: 0.3s; }
        .card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,0.4); }
        .btn-warning { background-color: #ffc107; font-weight: bold; border-radius: 20px; color: black; }
        .badge-type { background-color: #34495e; color: #ffc107; font-size: 0.8rem; }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <a href="home" class="btn btn-outline-light rounded-pill px-4">
        <i class="fas fa-arrow-left me-2"></i>BACK
    </a>
            <h3 class="text-warning fw-bold m-0 text-uppercase">Workout Plans</h3>
            <button class="btn btn-warning px-4 shadow-sm" data-bs-toggle="modal" data-bs-target="#addPlanModal">
                <i class="fas fa-plus me-2"></i>ADD NEW PLAN
            </button>
        </div>

        <div class="row g-4">
            <c:forEach var="plan" items="${plans}">
                <div class="col-md-4">
                    <div class="card h-100 shadow-sm">
                        <img src="${plan.imageUrl}" class="card-img-top" style="height: 200px; object-fit: cover;" onerror="this.src='https://via.placeholder.com/400x200?text=No+Image'">
                        <div class="card-body d-flex flex-column">
                            <div class="d-flex justify-content-between align-items-start mb-2">
                                <h5 class="fw-bold m-0">${plan.planName}</h5>
                                <span class="badge badge-type rounded-pill px-3 py-2 text-uppercase">${plan.planType}</span>
                            </div>
                            <p class="text-secondary small mb-3">${plan.description}</p>
                            
                            <div class="mt-auto d-flex justify-content-between border-top border-secondary pt-3">
                                <a href="WorkoutDetailServlet?id=${plan.id}" class="btn btn-outline-warning btn-sm px-3 rounded-pill">VIEW EXERCISES</a>
                                <a href="WorkoutPlanServlet?action=delete&id=${plan.id}" class="btn btn-outline-danger btn-sm rounded-circle" onclick="return confirm('Delete this plan?')">
                                    <i class="fas fa-trash"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="modal fade" id="addPlanModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content bg-dark text-white border-secondary">
                <form action="WorkoutPlanServlet" method="post" enctype="multipart/form-data">
                    <div class="modal-header border-secondary">
                        <h5 class="modal-title text-warning">Create New Workout Plan</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Plan Name (e.g., Push-Pull-Legs)</label>
                            <input type="text" name="planName" class="form-control bg-secondary text-white border-0 shadow-none" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Plan Type (e.g., 3-Day Split)</label>
                            <input type="text" name="planType" class="form-control bg-secondary text-white border-0 shadow-none" placeholder="Strength / Hypertrophy">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Brief Description</label>
                            <textarea name="description" class="form-control bg-secondary text-white border-0 shadow-none" rows="3"></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Upload Cover Image</label>
                            <input type="file" name="imageFile" class="form-control bg-secondary text-white border-0 shadow-none">
                        </div>
                    </div>
                    <div class="modal-footer border-0">
                        <button type="submit" class="btn btn-warning w-100 py-2">SAVE PLAN</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>