<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymSheets | Diet Details</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/Diet_Detail.css">
</head>
<body class="diet-page-body">

    <div class="container mt-5">
        <div class="row align-items-center mb-5">
            <div class="col-4">
                <a href="DietServlet?id=${categoryId}" class="btn btn-outline-light rounded-pill px-4 shadow-sm">
                    <i class="fas fa-chevron-left me-2"></i>BACK
                </a>
            </div>
            <div class="col-4 text-center">
                <h2 class="text-warning fw-bold mb-0 shadow-text">MEAL DETAILS</h2>
                <div class="header-underline mx-auto mt-2"></div>
            </div>
            <div class="col-4 text-end">
                <button class="btn btn-warning fw-bold rounded-pill px-4 shadow" data-bs-toggle="modal" data-bs-target="#addDetailModal">
                    <i class="fas fa-plus me-2"></i>ADD MEAL
                </button>
            </div>
        </div>

        <div class="card bg-dark border-secondary shadow-lg overflow-hidden" style="border-radius: 20px;">
            <div class="table-responsive">
                <table class="table table-dark table-hover mb-0 align-middle">
                    <thead class="bg-secondary text-warning">
                        <tr>
                            <th class="ps-4 py-3">Meal Time</th>
                            <th class="py-3">Food Items</th>
                            <th class="py-3">Protein</th>
                            <th class="py-3">Calories</th>
                            <th class="pe-4 py-3 text-center">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                       <c:forEach var="d" items="${details}">
                            <tr>
                                <td class="ps-4 fw-bold text-warning">${d.mealTime}</td>
                                <td>${d.foodItems}</td>
                                <td><span class="badge bg-info text-dark rounded-pill">${d.proteinGrams}</span></td>
                                <td><span class="badge bg-warning text-dark rounded-pill">${d.calories}</span></td>
                                <td class="pe-4 text-center">
                                    <a href="DietDetailServlet?action=delete&detailId=${d.id}&id=${planId}&categoryId=${categoryId}" 
   class="text-danger fs-5" 
   onclick="return confirm('ဖျက်မှာ သေချာလား?')">
    <i class="fas fa-trash-alt"></i>
</a>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty details}">
                            <tr>
                                <td colspan="5" class="text-center py-5 text-muted italic">No meals added yet. Click 'ADD MEAL' to start.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="modal fade" id="addDetailModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content border-0 bg-dark text-white" style="border-radius: 20px;">
                <form action="DietDetailServlet" method="post">
                    <input type="hidden" name="planId" value="${planId}">
                    <input type="hidden" name="categoryId" value="${categoryId}">
                    
                    <div class="modal-header border-secondary">
                        <h5 class="modal-title text-warning fw-bold">Add New Meal Info</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Meal Time</label>
                            <input type="text" name="mealTime" class="form-control bg-secondary text-white border-0 shadow-none" placeholder="e.g. Breakfast / Post-Workout" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Food Items</label>
                            <textarea name="foodItems" class="form-control bg-secondary text-white border-0 shadow-none" rows="3" placeholder="e.g. 2 Boiled Eggs, 1 Banana" required></textarea>
                        </div>
                        <div class="row">
                            <div class="col-6 mb-3">
                                <label class="form-label">Protein (g)</label>
                                <input type="text" name="proteinGrams" class="form-control bg-secondary text-white border-0 shadow-none" placeholder="e.g. 25g">
                            </div>
                            <div class="col-6 mb-3">
                                <label class="form-label">Calories</label>
                                <input type="text" name="calories" class="form-control bg-secondary text-white border-0 shadow-none" placeholder="e.g. 400 kcal">
                            </div>
                        </div>
                    </div>
                    
                    <div class="modal-footer border-0">
                        <button type="button" class="btn btn-outline-secondary rounded-pill px-4" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-warning fw-bold rounded-pill px-4">SAVE DETAILS</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>