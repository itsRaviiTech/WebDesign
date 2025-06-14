<%@ page contentType="text/html;charset=UTF-8" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link href="styles.css" rel="stylesheet">

<header class="header-darkish w-100">
    <div class="responsive-box d-flex justify-content-between align-items-center">
        <div class="d-flex align-items-center">
            <img src="logo.png" alt="Logo" style="height: 40px; margin-right: 12px;">
            <span class="header-title">Quiz Management System</span>
        </div>
        <a class="btn btn-outline-primary btn-logout" href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
    </div>
</header>

