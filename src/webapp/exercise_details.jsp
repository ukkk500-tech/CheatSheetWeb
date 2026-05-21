<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Workout Grid - Shit Pele</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="assets/shit-pele.css">
</head>
<body>

    <div class="container detail-container">
        <div class="d-flex justify-content-between align-items-center mb-5">
            <div>
                <h1 class="text-warning fw-bold">WORKOUT LIST</h1>
                <p class="text-secondary">Explore your muscle group exercises</p>
            </div>
            <div>
               <a href="ExerciseServlet?id=${categoryId}" class="btn btn-outline-light me-2">BACK</a>
                <button class="btn btn-warning fw-bold" onclick="openAddModal()">+ ADD EXERCISE</button>
            </div>
        </div>

        <div class="workout-grid">
            <c:forEach var="d" items="${details}">
                <div class="workout-item" onclick="showViewModal('${d.exerciseName}', '${d.setsReps}', '${d.description}', '${d.imageUrl}')">
                    <img src="${d.imageUrl}" class="workout-img-box" alt="exercise">
                    <div class="mt-3">
                        <div class="d-flex justify-content-between align-items-center">
                            <h4 class="text-warning mb-0">${d.exerciseName}</h4>
                            <span class="badge-reps">${d.setsReps}</span>
                        </div>
                    </div>
                    
                    <div class="mt-3 d-flex gap-2" onclick="event.stopPropagation();">
                        <button class="btn btn-sm btn-outline-info flex-grow-1" 
                                onclick="openEditModal('${d.id}', '${d.exerciseName}', '${d.setsReps}', '${d.description}')">
                            Edit
                        </button>
                        <a href="ExerciseDetailServlet?action=delete&detailId=${d.id}&id=${muscleId}" 
                           class="btn btn-sm btn-outline-danger" 
                           onclick="return confirm('ဖျက်မှာ သေချာလား Bro?')">
                            <i class="fas fa-trash"></i>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="modal fade" id="viewModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content border-warning shadow-lg">
                <div class="modal-body text-center p-4">
                    <img id="vImg" src="" class="img-fluid rounded-4 mb-3 border border-warning" style="max-height: 250px;">
                    <h2 id="vName" class="text-warning fw-bold"></h2>
                    <span id="vReps" class="badge-reps d-inline-block my-2"></span>
                    <p id="vDesc" class="text-light mt-3"></p>
                    <button class="btn btn-outline-warning mt-4 w-100 rounded-pill" data-bs-dismiss="modal">GOT IT!</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="exerciseModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="ExerciseDetailServlet" method="post" enctype="multipart/form-data">
                    <div class="modal-header border-0">
                        <h5 class="modal-title text-warning" id="modalTitle">Add New Exercise</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" name="action" id="formAction" value="add">
                        <input type="hidden" name="muscleId" value="${muscleId}">
                        <input type="hidden" name="detailId" id="detailId">
                        <div class="mb-3">
                            <label class="form-label text-warning">Exercise Name</label>
                            <input type="text" name="exerciseName" id="exerciseName" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-warning">Sets & Reps</label>
                            <input type="text" name="setsReps" id="setsReps" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-warning">Description</label>
                            <textarea name="description" id="description" class="form-control" rows="3"></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-warning">Upload Image</label>
                            <input type="file" name="imageFile" class="form-control">
                        </div>
                    </div>
                    <div class="modal-footer border-0">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-warning fw-bold">SAVE DATA</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const myModal = new bootstrap.Modal(document.getElementById('exerciseModal'));
        const viewModal = new bootstrap.Modal(document.getElementById('viewModal'));

        function showViewModal(name, reps, desc, img) {
            document.getElementById('vName').innerText = name;
            document.getElementById('vReps').innerText = reps;
            document.getElementById('vDesc').innerText = desc;
            document.getElementById('vImg').src = img;
            viewModal.show();
        }

        function openAddModal() {
            document.getElementById('modalTitle').innerText = "Add New Exercise";
            document.getElementById('formAction').value = "add";
            document.getElementById('exerciseName').value = "";
            document.getElementById('setsReps').value = "";
            document.getElementById('description').value = "";
            myModal.show();
        }

        function openEditModal(id, name, reps, desc) {
            document.getElementById('modalTitle').innerText = "Edit Exercise";
            document.getElementById('formAction').value = "update";
            document.getElementById('detailId').value = id;
            document.getElementById('exerciseName').value = name;
            document.getElementById('setsReps').value = reps;
            document.getElementById('description').value = desc;
            myModal.show();
        }
    </script>
</body>
</html>