<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<html>
<head>
    <title><fmt:message key="header.sign_up"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>

</head>
<body>
<div class="modal modal-signin position-static d-block bg-secondary py-5" tabindex="-1" role="dialog" id="modalSignin">
    <div class="modal-dialog" role="document">
        <div class="modal-content rounded-4 shadow">
            <div class="modal-header p-5 pb-4 border-bottom-0">
                <a class="text-decoration-none" href="${pageContext.request.contextPath}/?command=home">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-arrow-left-circle" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"></path>
                    </svg>
                </a>
                <h1 class="fw-bold mb-0 fs-2"><fmt:message key="header.sign_up"/></h1>
                <div class="locale">
                    <form action="${pageContext.request.contextPath}/locale/" method="get">
                        <input type="hidden" name="command" value="locale"/>
                        <select class="form-select-sm" id="locale" name="locale" onchange="submit()"
                                style="background-color: RGBA(33,37,41,var(--bs-bg-opacity,1))!important; color: white">
                            <option value="en" ${locale == 'en' ? 'selected' : ''}><fmt:message key="lang.en"/></option>
                            <option value="ua" ${locale == 'ua' ? 'selected' : ''}><fmt:message key="lang.ua"/></option>
                        </select>
                    </form>
                </div>
            </div>

            <div class="modal-body p-5 pt-0">
                <form class="row g-3 needs-validation" novalidate action="${pageContext.request.contextPath}/login/"  method="post">
                    <input type="hidden" name="command" value="signUp">
                    <div class="form-floating mb-3">
                        <input type="text" name="firstName" class="form-control rounded-3" id="floatingFName" placeholder="first name" required>
                        <label for="floatingInput"><fmt:message key="label.first_name"/></label>
                        <div class="invalid-feedback">
                            <fmt:message key="enter.first_name"/>
                        </div>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="text" name="lastName" class="form-control rounded-3" id="floatingLName" placeholder="last name" required>
                        <label for="floatingInput"><fmt:message key="label.last_name"/></label>
                        <div class="invalid-feedback">
                            <fmt:message key="enter.last_name"/>
                        </div>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="email" name="email" class="form-control rounded-3" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" id="floatingInput" placeholder="name@example.com" required>
                        <label for="floatingInput"><fmt:message key="label.email"/></label>
                        <div class="invalid-feedback">
                            <fmt:message key="enter.email"/>
                        </div>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="password" name="password" class="form-control rounded-3" pattern="^\S{4,10}$" id="floatingPassword" placeholder="Password" required>
                        <label for="floatingPassword"><fmt:message key="label.password"/></label>
                        <div class="invalid-feedback">
                            <fmt:message key="enter.password"/>
                        </div>
                    </div>
                    <c:if test="${exception ne null}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <strong><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-exclamation-octagon-fill" viewBox="0 0 16 16">
                                <path d="M11.46.146A.5.5 0 0 0 11.107 0H4.893a.5.5 0 0 0-.353.146L.146 4.54A.5.5 0 0 0 0 4.893v6.214a.5.5 0 0 0 .146.353l4.394 4.394a.5.5 0 0 0 .353.146h6.214a.5.5 0 0 0 .353-.146l4.394-4.394a.5.5 0 0 0 .146-.353V4.893a.5.5 0 0 0-.146-.353L11.46.146zM8 4c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995A.905.905 0 0 1 8 4zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                            </svg>   </strong><span style="padding-left: 10px;"></span>
                            <fmt:message key="exception.${exception}"/>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" type="submit"><fmt:message key="header.sign_up"/></button>
                </form>
                <div>
                    <span><fmt:message key="already.have.an.account"/></span>
                    <a class="text-decoration-none mb-3" href="${pageContext.request.contextPath}/login/?command=loginPage">
                        <fmt:message key="header.login"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    <%@ include file="script.js" %>
</script>
</body>
</html>
