<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GymSheets | Target Muscles</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        :root {
            --primary-color: #0f172a;
            --accent-color: #fbbf24;
            --bg-body: #f1f5f9;
        }

        body {
            background-color: var(--bg-body) !important;
            font-family: 'Poppins', sans-serif;
            margin: 0;
            color: #334155;
        }

        /* --- ကြီးမားသော Header ပိုင်း (Big Premium Header) --- */
        .premium-header {
            background-color: #ffffff;
            padding: 50px 0; /* Header ကို သိသိသာသာ ကြီးအောင် လုပ်ထားပါတယ် */
            border-bottom: 1px solid #e2e8f0;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
        }

        .header-top-nav {
            display: flex;
            align-items: center;
            gap: 15px;
            margin-bottom: 15px;
        }

        .btn-back-circle {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #e2e8f0;
            color: #64748b;
            text-decoration: none;
            transition: 0.2s;
        }

        .btn-back-circle:hover {
            background-color: #f8fafc;
            color: var(--primary-color);
        }

        .category-label {
            font-size: 0.85rem;
            font-weight: 600;
            color: #94a3b8;
            letter-spacing: 1px;
            text-transform: uppercase;
        }

        .header-title {
            font-weight: 800;
            font-size: 2.5rem; /* Title အကြီးကြီး ထားထားပါတယ် */
            color: var(--primary-color);
            margin: 0;
        }

        .header-stats {
            display: flex;
            gap: 20px;
            margin-top: 15px;
        }

        .stat-item {
            background: #f8fafc;
            padding: 8px 16px;
            border-radius: 12px;
            font-size: 0.9rem;
            font-weight: 600;
            color: #475569;
            border: 1px solid #f1f5f9;
        }

        .stat-item i {
            color: var(--accent-color);
        }

        /* --- ပုံကို Fix ဖြစ်စေသော Card ပုံစံ --- */
        .muscle-card {
            background: #ffffff;
            border-radius: 24px;
            border: 1px solid #e2e8f0;
            overflow: hidden;
            height: 100%;
            transition: box-shadow 0.3s ease;
        }

        .muscle-card:hover {
            box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
        }

        .img-fix-container {
            width: 100%;
            height: 260px; /* ပုံအမြင့်ကို Fix လုပ်ထားပါတယ် */
            background-color: #f8fafc;
            overflow: hidden;
        }

        .img-fix-container img {
            width: 100%;
            height: 100%;
            object-fit: cover; /* ပုံတွေ မပြဲသွားအောင် Auto-Fix လုပ်ပေးပါတယ် */
            object-position: center;
        }

        .card-body-custom {
            padding: 25px;
        }

        .muscle-name {
            font-weight: 700;
            font-size: 1.4rem;
            color: var(--primary-color);
            margin-bottom: 12px;
            text-transform: uppercase;
        }

        .btn-view {
            background-color: var(--accent-color);
            color: var(--primary-color);
            font-weight: 700;
            border: none;
            border-radius: 12px;
            padding: 10px 20px;
            width: 100%;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            transition: 0.2s;
        }

        .btn-view:hover {
            background-color: #f59e0b;
            color: var(--primary-color);
        }

        /* Action Buttons */
        .card-actions-footer {
            padding: 15px 25px;
            background: #fafafa;
            border-top: 1px solid #f1f5f9;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }

        .btn-mini-action {
            width: 36px;
            height: 36px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            border: 1px solid #e2e8f0;
            background: white;
            color: #64748b;
            transition: 0.2s;
        }

        .btn-mini-action:hover {
            background: #f1f5f9;
            color: var(--primary-color);
        }

        .btn-add-new {
            background-color: var(--primary-color);
            color: white;
            border-radius: 14px;
            padding: 12px 28px;
            font-weight: 600;
            border: none;
            transition: 0.3s;
        }

        .btn-add-new:hover {
            background-color: #1e293b;
            color: white;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>

    <header class="premium-header">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-8">
                    <div class="header-top-nav">
                        <a href="ExerciseServlet" class="btn-back-circle">
                            <i class="fas fa-arrow-left"></i>
                        </a>
                        <span class="category-label">Training Module</span>
                    </div>
                    <h1 class="header-title">TARGET <span style="color: var(--accent-color);">MUSCLES</span></h1>
                    
                    <div class="header-stats">
                        <div class="stat-item">
                            <i class="fas fa-layer-group me-2"></i> ${fn:length(muscles)} Groups Found
                        </div>
                        <div class="stat-item">
                            <i class="fas fa-check-circle me-2"></i> High Quality Guides
                        </div>
                    </div>
                </div>
                <div class="col-lg-4 text-lg-end mt-4 mt-lg-0">
                    <button class="btn btn-add-new shadow-sm" onclick="openAddMuscleModal()">
                        <i class="fas fa-plus-circle me-2"></i> ADD NEW MUSCLE
                    </button>
                </div>
            </div>
        </div>
    </header>

    <main class="container my-5">
        <div class="row g-4">
            <c:forEach var="m" items="${muscles}">
                <div class="col-xl-4 col-md-6">
                    <div class="card muscle-card">
                        <div class="img-fix-container">
                            <img src="${m.imageUrl}" alt="${m.subCategory}">
                        </div>

                        <div class="card-body-custom">
                            <h4 class="muscle-name">${m.subCategory}</h4>
                            <a href="ExerciseDetailServlet?id=${m.id}&muscleName=${m.subCategory}" class="btn-view">
                                VIEW EXERCISES <i class="fas fa-chevron-right ms-2"></i>
                            </a>
                        </div>

                        <div class="card-actions-footer">
                            <button class="btn btn-mini-action" onclick="openEditModal('${m.id}', '${m.subCategory}')" title="Edit">
                                <i class="fas fa-edit"></i>
                            </button>
                            <a href="ExerciseServlet?action=delete&id=${m.id}&catId=${categoryId}" 
                               class="btn btn-mini-action text-danger" 
                               onclick="return confirm('Delete this muscle group?')" title="Delete">
                                <i class="fas fa-trash"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </main>

    <div class="modal fade" id="addMuscleModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content border-0 shadow-lg" style="border-radius: 20px;">
                <form action="ExerciseServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="categoryId" value="${categoryId}">
                    <div class="modal-header border-0 pb-0">
                        <h5 class="modal-title fw-bold">Create New Muscle</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body p-4">
                        <div class="mb-3">
                            <label class="form-label fw-600">Muscle Group Name</label>
                            <input type="text" name="subCategory" class="form-control rounded-3" placeholder="e.g. Chest, Back" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-600">Choose Image</label>
                            <input type="file" name="imageFile" class="form-control rounded-3" accept="image/*" required>
                        </div>
                    </div>
                    <div class="modal-footer border-0 p-4 pt-0">
                        <button type="submit" class="btn btn-warning fw-bold w-100 py-3 rounded-pill">SAVE CHANGES</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="editMuscleModal" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content border-0 shadow-lg" style="border-radius: 20px;">
                <form action="ExerciseServlet" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="muscleId" id="editMuscleId">
                    <input type="hidden" name="categoryId" value="${categoryId}">
                    <div class="modal-header border-0 pb-0">
                        <h5 class="modal-title fw-bold">Update Muscle Group</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body p-4">
                        <div class="mb-3">
                            <label class="form-label fw-600">Name</label>
                            <input type="text" name="subCategory" id="editMuscleName" class="form-control rounded-3" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-600">Change Image (Optional)</label>
                            <input type="file" name="imageFile" class="form-control rounded-3" accept="image/*">
                        </div>
                    </div>
                    <div class="modal-footer border-0 p-4 pt-0">
                        <button type="submit" class="btn btn-primary fw-bold w-100 py-3 rounded-pill text-white">UPDATE MUSCLE</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        function openAddMuscleModal() {
            new bootstrap.Modal(document.getElementById('addMuscleModal')).show();
        }
        function openEditModal(id, name) {
            document.getElementById('editMuscleId').value = id;
            document.getElementById('editMuscleName').value = name;
            new bootstrap.Modal(document.getElementById('editMuscleModal')).show();
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>