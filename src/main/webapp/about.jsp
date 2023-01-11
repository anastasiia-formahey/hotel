<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html class="h-100" lang=${sessionScope.locale} >
<head>
    <title><fmt:message key="header.login"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    </head>

<body class="d-flex flex-column h-100">
<header class="p-3 text-bg-dark">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <img src="${pageContext.request.contextPath}/images/—Pngtree—watermelon%20logo_6945475.png" height="50px" alt="watermelon">
            </a>
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <a href="${pageContext.request.contextPath}/user/?command=home" class="nav-link px-2 text-secondary"><fmt:message key="header.home"/></a>
                <li><a href="${pageContext.request.contextPath}/user/?command=rooms" class="nav-link px-2 text-white"><fmt:message key="header.rooms"/></a></li>
                <li><a href="${pageContext.request.contextPath}/user/?command=about" class="nav-link px-2 text-white"><fmt:message key="header.about"/></a></li>
            </ul>

            <div class="text-end d-flex" style="padding-right: 10px">
                <form action="${pageContext.request.contextPath}/login/" method="get">
                    <input type="hidden" name="command" value="loginPage"/>
                    <button class="btn btn-outline-light me-2" type="submit"><fmt:message key="header.login"/></button>
                </form>
                <form action="${pageContext.request.contextPath}/signUp/" method="get">
                    <input type="hidden" name="command" value="signUpPage"/>
                    <button class="btn btn-outline-warning me-2" type="submit"><fmt:message key="header.sign_up"/></button>
                </form>
            </div>
            <div class="locale">
                <form class="setlocale" action="${pageContext.request.contextPath}/locale/" method="get">
                    <input type="hidden" name="command" value="locale"/>
                    <select class="form-select-sm" id="locale" name="locale" onchange="submit()"
                            style="background-color: RGBA(33,37,41,var(--bs-bg-opacity,1))!important; color: white">
                        <option value="en" ${locale == 'en' ? 'selected' : ''}>EN</option>
                        <option value="ua" ${locale == 'ua' ? 'selected' : ''}>UA</option>
                    </select>
                </form>
            </div>
        </div>
    </div>
</header>

<%@ include file="/jspf/footer.jspf" %>

</body>
</html>
