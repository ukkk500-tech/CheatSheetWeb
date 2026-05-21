<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Exercise Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        /* CSS ကို အောက်က Section မှာ သီးသန့်ပေးထားပေမဲ့ ဒီမှာ တစ်ခါတည်း ထည့်ပေးလိုက်ပါတယ် */
        body { background-color: #121212; color: #e0e0e0; font-family: 'Inter', sans-serif; }
        .header-section { background: linear-gradient(135deg, #1e1e1e 0%, #121212 100%); padding: 30px 0; border-bottom: 1px solid #333; }
        .exercise-card { background: #1e1e1e; border: 1px solid #333; border-radius: 15px; transition: 0.3s; overflow: hidden; margin-bottom: 20px; }
        .exercise-card:hover { transform: translateY(-5px); border-color: #ffc107; box-shadow: 0 10px 20px rgba(0,0,0,0.5); }
        .exercise-img { width: 100%; height: 200px; object-fit: cover; border-radius: 10px; }
        .text-warning-custom { color: #ffc107; }
        .btn-add { background-color: #ffc107; color: #000; fw-bold; border-radius: 50px; padding: 10px 25px; transition: 0.3s; }
        .btn-add:hover { background-color: #e0a800; transform: scale(1.05); }
        .sets-reps-badge { background: rgba(255, 193, 7, 0.1); color: #ffc107; border: 1px solid #ffc107; padding: 5px 15px; border-radius: 50px; font-weight: bold; }
        .modal-content { background-color: #1e1e1e; color: white; border: 1px solid #444; }
        .form-control { background-color: #2d2d2d; border: 1px solid #444; color: white; }
        .form-control:focus { background-color: #333; border-color: #ffc107; color: white; box-shadow: none; }
    </style>
</head>
<body>

    <div class="header-section mb-5">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-4">
                    <a href="WorkoutPlanServlet" class="btn btn-outline-light rounded-pill px-4">
                        <i class="fas fa-chevron-left me-2"></i>Back to Plans
                    </a>
                </div>
                <div class="col-md-4 text-center">
                    <h2 class="fw-bold text-uppercase m-0">Exercises</h2>
                </div>
                <div class="col-md-4 text-end">
                    <button class="btn btn-add fw-bold shadow" data-bs-toggle="modal" data-bs-target="#addExerciseModal">
                        <i class="fas fa-plus-circle me-2"></i>ADD NEW
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="container pb-5">
        <div class="row">
            <c:choose>
                <c:when test="${not empty details}">
                    <c:forEach var="ex" items="${details}">
                        <div class="col-lg-6 mb-4">
                            <div class="exercise-card p-3 h-100">
                                <div class="row g-3">
                                    <div class="col-md-4">
                                        <img src="${ex.imageUrl}" class="exercise-img shadow-sm" onerror="this.src='https://via.placeholder.com/200x200?text=No+GIF'">
                                    </div>
                                    <div class="col-md-8 d-flex flex-column">
                                        <div class="d-flex justify-content-between align-items-start">
                                            <h4 class="fw-bold text-warning-custom text-uppercase">${ex.exerciseName}</h4>
                                            <a href="WorkoutDetailServlet?action=delete&detailId=${ex.id}&id=${planId}" 
                                               class="text-danger" onclick="return confirm('Delete this exercise?')">
                                                <i class="fas fa-trash-alt"></i>
                                            </a>
                                        </div>
                                        <div class="mt-2 mb-3">
                                            <span class="sets-reps-badge text-uppercase small">
                                                <i class="fas fa-redo me-2"></i>${ex.setsReps}
                                            </span>
                                        </div>
                                        <p class="text-secondary small flex-grow-1">${ex.description}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-12 text-center py-5">
                        <i class="fas fa-dumbbell fa-4x text-secondary mb-3"></i>
                        <h4 class="text-secondary">No exercises added to this plan yet.</h4>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="modal fade" id="addExerciseModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content shadow-lg">
                <form action="WorkoutDetailServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="planId" value="${planId}">
                    <div class="modal-header border-secondary">
                        <h5 class="modal-title fw-bold text-warning-custom">Add Exercise Detail</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label small text-secondary">Exercise Name</label>
                            <input type="text" name="exerciseName" class="form-control" placeholder="e.g. Bench Press" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label small text-secondary">Sets & Reps</label>
                            <input type="text" name="setsReps" class="form-control" placeholder="e.g. 4 Sets x 12 Reps">
                        </div>
                        <div class="mb-3">
                            <label class="form-label small text-secondary">Instructions / Note</label>
                            <textarea name="description" class="form-control" rows="3" placeholder="Describe the form..."></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label small text-secondary">Upload GIF / Image</label>
                            <input type="file" name="imageFile" class="form-control" accept="image/*">
                        </div>
                    </div>
                    <div class="modal-footer border-0 pt-0">
                        <button type="submit" class="btn btn-add w-100 py-2 fw-bold text-uppercase">Save Exercise</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>