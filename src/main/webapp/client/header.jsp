<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang=${sessionScope.locale}>
<header class="p-3 text-bg-dark">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <img src="${pageContext.request.contextPath}/images/—Pngtree—watermelon%20logo_6945475.png" height="50px">
            </a>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="#" class="nav-link px-2 text-secondary"><fmt:message key="header.home"/></a></li>
                <li><a href="#" class="nav-link px-2 text-white"><fmt:message key="header.rooms"/></a></li>
                <li><a href="#" class="nav-link px-2 text-white"><fmt:message key="header.offers"/></a></li>
                <li><a href="#" class="nav-link px-2 text-white">FAQs</a></li>
                <li><a href="#" class="nav-link px-2 text-white">About</a></li>
            </ul>

            <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3" role="search">
                <input type="search" class="form-control form-control-dark text-bg-dark" placeholder="Search..." aria-label="Search">
            </form>

            <%--            <div class="text-end d-flex" style="padding-right: 10px">--%>
            <%--                <form action="login/" method="get">--%>
            <%--                    <input type="hidden" name="command" value="loginPage"/>--%>
            <%--                    <button class="btn btn-outline-light me-2" type="submit"><fmt:message key="header.login"/></button>--%>
            <%--                </form>--%>
            <%--                <form action="signUp/" method="get">--%>
            <%--                    <input type="hidden" name="command" value="signUpPage"/>--%>
            <%--                    <button class="btn btn-outline-light me-2" type="submit"><fmt:message key="header.sign_up"/></button>--%>
            <%--                </form>--%>
            <%--            </div>--%>
            <div class="locale">
                <form class="setlocale" action="locale/" method="get">
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
</html>