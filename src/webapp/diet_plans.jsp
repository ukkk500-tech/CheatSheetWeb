<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymSheets | Diet Plans</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/diet.css">
</head>
<body class="diet-page-body">

    <div class="container mt-5">
        <div class="row align-items-center mb-5">
            <div class="col-4">
                <a href="ExerciseServlet" class="btn btn-outline-light rounded-pill px-4 shadow-sm">
                    <i class="fas fa-chevron-left me-2"></i>BACK
                </a>
            </div>
            <div class="col-4 text-center">
                <h2 class="text-warning fw-bold mb-0 shadow-text">DIET PLANS</h2>
                <div class="header-underline mx-auto mt-2"></div>
            </div>
            <div class="col-4 text-end">
                <button class="btn btn-warning fw-bold rounded-pill px-4 shadow" onclick="openAddDietModal()">
                    <i class="fas fa-plus me-2"></i>ADD PLAN
                </button>
            </div>
        </div>

        <div class="row g-4">
            <c:forEach var="p" items="${plans}">
                <div class="col-md-6">
                    <div class="diet-plan-card shadow-lg" 
                         style="--bg-image: url('${pageContext.request.contextPath}/${(not empty p.imageUrl) ? p.imageUrl : "assets/images/default.jpg"}');"
                         onclick="location.href='DietDetailServlet?id=${p.id}&categoryId=${categoryId}'">
                        
                        <a href="DietServlet?action=delete&planId=${p.id}&id=${categoryId}" 
                           class="delete-btn-overlay text-decoration-none"
                           onclick="event.stopPropagation(); return confirm('ဖျက်မှာ သေချာလား?')">
                            <i class="fas fa-trash-alt"></i>
                        </a>

                        <a href="javascript:void(0)" 
                           class="edit-btn-overlay text-decoration-none"
                           onclick="event.stopPropagation(); openEditModal('${p.id}', '${p.planName}', '${p.planType}', '${p.imageUrl}')">
                            <i class="fas fa-edit"></i>
                        </a>

                        <div class="diet-content">
                            <h3 class="fw-bold mb-1 text-warning text-uppercase">${p.planName}</h3>
                            <span class="badge bg-warning text-dark rounded-pill px-3">${p.planType}</span>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="modal fade" id="addDietModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content border-0 bg-dark text-white" style="border-radius: 20px;">
                <form action="DietServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="categoryId" value="${categoryId}">
                    
                    <div class="modal-header border-secondary">
                        <h5 class="modal-title text-warning fw-bold">Add New Diet Plan</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label text-warning">Plan Name</label>
                            <input type="text" name="planName" class="form-control bg-secondary text-white border-0" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-warning">Plan Type</label>
                            <input type="text" name="planType" class="form-control bg-secondary text-white border-0" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-warning">Upload Image</label>
                            <input type="file" name="imageFile" class="form-control bg-secondary text-white border-0" accept="image/*">
                        </div>
                    </div>
                    
                    <div class="modal-footer border-0">
                        <button type="button" class="btn btn-secondary rounded-pill px-4" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-warning fw-bold rounded-pill px-4">SAVE PLAN</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="editDietModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content border-0 bg-dark text-white" style="border-radius: 20px;">
                <form action="DietServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="categoryId" value="${categoryId}">
                    <input type="hidden" name="planId" id="editPlanId">
                    <input type="hidden" name="oldImagePath" id="editOldImagePath">

                    <div class="modal-header border-secondary">
                        <h5 class="modal-title text-warning fw-bold">Edit Diet Plan</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    
                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label text-warning">Plan Name</label>
                            <input type="text" name="planName" id="editPlanName" class="form-control bg-secondary text-white border-0" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-warning">Plan Type</label>
                            <input type="text" name="planType" id="editPlanType" class="form-control bg-secondary text-white border-0" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label text-warning">Update Image (Optional)</label>
                            <input type="file" name="imageFile" class="form-control bg-secondary text-white border-0" accept="image/*">
                        </div>
                    </div>
                    
                    <div class="modal-footer border-0">
                        <button type="button" class="btn btn-secondary rounded-pill px-4" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-warning fw-bold rounded-pill px-4">UPDATE PLAN</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function openAddDietModal() {
            new bootstrap.Modal(document.getElementById('addDietModal')).show();
        }

        function openEditModal(id, name, type, img) {
            // Modal ထဲက field တွေထဲကို လက်ရှိ data တွေ လှမ်းထည့်မယ်
            document.getElementById('editPlanId').value = id;
            document.getElementById('editPlanName').value = name;
            document.getElementById('editPlanType').value = type;
            document.getElementById('editOldImagePath').value = img;
            
            // Modal ကို ဖွင့်မယ်
            var myModal = new bootstrap.Modal(document.getElementById('editDietModal'));
            myModal.show();
        }
    </script>
</body>
</html>