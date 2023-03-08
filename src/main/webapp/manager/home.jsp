<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ttg" uri="/WEB-INF/timeTag.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html class="h-100" lang=${sessionScope.locale}>
<head>
    <title><fmt:message key="header.home"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</head>

<body class="h-100">
<header class="p-3 text-bg-dark">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <img src="${pageContext.request.contextPath}/images/—Pngtree—watermelon%20logo_6945475.png" height="50px">
            </a>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="${pageContext.request.contextPath}/manager/?command=home" class="nav-link px-2 text-secondary"><fmt:message key="header.home"/></a></li>
                <li><a href="${pageContext.request.contextPath}/manager/?command=rooms" class="nav-link px-2 text-white"><fmt:message key="header.rooms"/></a></li>
                <li><a href="${pageContext.request.contextPath}/manager/?command=viewApplications" class="nav-link px-2 text-white position-relative">
                    <fmt:message key="header.applications"/>
                    <c:if test="${sessionScope.applicationCount > 0}">
                    <span class="position-absolute top-10 start-100 translate-middle badge rounded-pill bg-danger">
                        ${sessionScope.applicationCount}
    <span class="visually-hidden">unread messages</span>
                            </c:if>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/manager/?command=viewBooking" class="nav-link px-2 text-white"><fmt:message key="header.booking"/></a></li>
            </ul>

            <div class="text-end d-flex" style="padding-right: 10px">
                <a href="${pageContext.request.contextPath}/?command=logout" class="btn btn-outline-warning me-2"><fmt:message key="header.logout"/></a>
            </div>
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
    </div>
</header>
<main style="height: 68%">
    <div class="container marketing" style="height: 100%">
        <hr class="feature-divider">
        <c:if test="${exception ne null}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-exclamation-octagon-fill" viewBox="0 0 16 16">
                    <path d="M11.46.146A.5.5 0 0 0 11.107 0H4.893a.5.5 0 0 0-.353.146L.146 4.54A.5.5 0 0 0 0 4.893v6.214a.5.5 0 0 0 .146.353l4.394 4.394a.5.5 0 0 0 .353.146h6.214a.5.5 0 0 0 .353-.146l4.394-4.394a.5.5 0 0 0 .146-.353V4.893a.5.5 0 0 0-.146-.353L11.46.146zM8 4c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995A.905.905 0 0 1 8 4zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </svg>   </strong><span style="padding-left: 10px;"></span>
                <fmt:message key="exception.${exception}"/>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${infoMessage ne null}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
                    <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </svg>  </strong><span style="padding-left: 10px;"></span>
                <fmt:message key="info.${infoMessage}"/>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${successMessage ne null}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <strong><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-check-circle-fill" viewBox="0 0 16 16">
                    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                </svg>   </strong><span style="padding-left: 10px;"></span>
                <fmt:message key="successMessage.${successMessage}"/>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <div class="card mb-3 p-4" style="height: 100%">
            <div class="row g-0">
                <div class="col-md-2">
                    <div class="card-body">
                    <svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" fill="currentColor" class="bi bi-person-check" viewBox="0 0 16 16">
                        <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm1.679-4.493-1.335 2.226a.75.75 0 0 1-1.174.144l-.774-.773a.5.5 0 0 1 .708-.708l.547.548 1.17-1.951a.5.5 0 1 1 .858.514ZM11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0ZM8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4Z"></path>
                        <path d="M8.256 14a4.474 4.474 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10c.26 0 .507.009.74.025.226-.341.496-.65.804-.918C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4s1 1 1 1h5.256Z"></path>
                    </svg>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card-body">
                        <h5 class="card-title">${sessionScope.user.getFirstName()} ${sessionScope.user.getLastName()}</h5>
                        <p class="card-text">${sessionScope.user.getRole()}</p>
                        <p class="card-text">${sessionScope.user.getEmail()}</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <fmt:message key="occupancy.of.rooms"/>
                            <form action="${pageContext.request.contextPath}/manager/" method="get"><div class="form-floating">
                                <div class="input-group">

                                        <input type="hidden" name="command" value="getOccupancyOfRoom">
                                <input type="date" class=" form-control" name="dateOfOccupancy" id="dateOfOccupancy"
                                       value="${sessionScope.dateOfOccupancy}" required>
                                        <button type="submit" class="btn btn-warning">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-search"
                                                                                           viewBox="0 0 16 16">
                                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
                                        </svg>
                                        </button>

                                </div>
                            </div> </form></div>
                        <div class="card-body">

                                <div class="row row-cols-sm-5 row-cols-sm-2 row-cols-md-5 p-5" style="width: 500px; margin-left: -29px;">
                                    <c:forEach items="${sessionScope.roomMap}" var="room" varStatus="loop">
                                        <div class="col" style="width: 17%; margin-bottom: 5px">
                                            <div class="card shadow-sm">
                                                <a <c:if test="${room.getValue() ne 'FREE'}"> href="${pageContext.request.contextPath}/manager/?command=viewOccupancyOfRoom&numberOfRoom=${room.getKey()}&status=${room.getValue()}"</c:if> <tags:buttonByStatus value="${room.getValue()}"/>
                                                        data-bs-toggle="tooltip"
                                                        data-bs-title="<fmt:message key="room.number"/>${room.getKey()} <fmt:message key="form.status"/>&nbsp;:&nbsp;<fmt:message key="status.${room.getValue()}"/>" data-bs-content="${room.getValue()}"
                                                >${room.getKey()}
                                                </a>
                                            </div>
                                        </div>

                                    </c:forEach>
                                </div>
                            <script>
                                const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
                                const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl))
                            </script>

                        </div>
                    </div>

                </div>
            </div>
        </div>


    </div>
    </div>
</main>
<%@ include file="/jspf/footer.jspf" %>
<script src="../script.js" ></script>
</body>
</html>
