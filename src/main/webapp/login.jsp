<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<html>
<head>
    <title><fmt:message key="header.login"/></title>
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
                <!-- <h1 class="modal-title fs-5" >Modal title</h1> -->
                <a class="text-decoration-none" href="${pageContext.request.contextPath}/?command=home">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-arrow-left-circle" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8zm15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5z"></path>
                    </svg>
                </a>
                <h1 class="fw-bold mb-0 fs-2"><fmt:message key="header.login"/></h1>
                <div class="locale">
                    <form class="setlocale" action="locale/" method="get">
                        <input type="hidden" name="command" value="locale"/>
                        <select class="form-select-sm" id="locale" name="locale" onchange="submit()"
                                style="background-color: white;color: black;">
                            <option value="en" ${locale == 'en' ? 'selected' : ''}>EN</option>
                            <option value="ua" ${locale == 'ua' ? 'selected' : ''}>UA</option>
                        </select>
                    </form>
                </div>
            </div>

            <div class="modal-body p-5 pt-0">
                <form class="row g-3 needs-validation"  action="${pageContext.request.contextPath}/auth/"  method="post" novalidate>
                    <input type="hidden" name="command" value="login">

                    <div class="form-floating mb-3">
                        <input type="email" name="email" class="form-control rounded-3" id="floatingInput" placeholder="name@example.com" required>
                        <label for="floatingInput"><fmt:message key="label.email"/></label>
                        <div class="invalid-feedback">
                            <fmt:message key="enter.email"/>
                        </div>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="password" name="password" class="form-control rounded-3" id="floatingPassword" placeholder="Password" required>
                        <label for="floatingPassword"><fmt:message key="label.password"/></label>
                        <div class="invalid-feedback">
                            <fmt:message key="enter.password"/>
                        </div>
                    </div>
                    <c:if test="${error eq true}">
                    <div class="alert alert-danger" role="alert">
                        <fmt:message key="message.error"/>
                    </div>
                    </c:if>

                    <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" type="submit"><fmt:message key="header.login"/></button>
                </form>
                <div>
                    <span><fmt:message key="not.registered.yet"/></span>
                <a class="text-decoration-none mb-3" href="${pageContext.request.contextPath}/signUp/?command=signUpPage">
                    <fmt:message key="create.an.account"/>
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
