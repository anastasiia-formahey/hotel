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
<main style="height: 70%">
    <section>
        <div class="container py-5">
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
            <div class="row">
                <div class="col-lg-4">
                    <div class="card mb-4">
                        <div class="card-body text-center">
                            <img src="../images/person-circle.svg" alt="avatar"
                                 class="rounded-circle img-fluid" style="width: 140px">
                            <h5 class="my-3">${sessionScope.user.getFirstName()} ${sessionScope.user.getLastName()}</h5>
                            <p class="text-muted mb-3">${sessionScope.user.getEmail()}</p>
                            <p class="text-muted mb-5">${sessionScope.user.getRole()}</p>
                            <div class="d-flex justify-content-center mb-4">
                                <div class="col-sm-6">
                                <button class="btn btn-warning border-0" data-bs-toggle="modal" data-bs-target="#editProfile" data-bs-whatever="@mdo">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                        <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                        <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                    </svg>  <fmt:message key="editProfile"/>
                                </button>
                            </div>

                                <div class="modal fade" id="editProfile" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header align-content-center">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel"><fmt:message key="editProfile"/></h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <form class="needs-validation" novalidate action="${pageContext.request.contextPath}/manager/"  method="post">
                                                <div class="modal-body">
                                                    <input type="hidden" name="command" value="editProfile">
                                                    <input type="hidden" name="id" value="${sessionScope.user.getId()}">
                                                    <div class="form-floating mb-3">
                                                        <div class="input-group">
                                                            <span class="input-group-text" id="basic-addon6"><fmt:message key="label.first_name"/></span>
                                                            <input type="text" name="firstName" class="form-control" id="firstName"  value="${sessionScope.user.getFirstName()}" required>
                                                        </div>
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <div class="input-group">
                                                            <span class="input-group-text" id="basic-addon2"><fmt:message key="label.last_name"/></span>
                                                            <input type="text" name="lastName" class="form-control" id="lastName"  value="${sessionScope.user.getLastName()}" required>
                                                        </div>
                                                    </div>

                                                    <div class="form-floating mb-3">
                                                        <div class="input-group">
                                                            <span class="input-group-text" id="basic-addon3"><fmt:message key="label.email"/></span>
                                                            <input type="email" name="email" class="form-control" id="email" value="${sessionScope.user.getEmail()}" required>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="close"/></button>
                                                    <button class="btn btn-warning" type="submit"><fmt:message key="edit"/></button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <button class="btn btn-warning border-0" data-bs-toggle="modal" data-bs-target="#editPassword" data-bs-whatever="@mdo">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
                                            <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                            <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                                        </svg> <fmt:message key="changePassword"/>
                                    </button>
                                </div>
                                <div class="modal fade" id="editPassword" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header align-content-center">
                                                <h1 class="modal-title fs-5" id="ModalLabel"><fmt:message key="changePassword"/></h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <form class="needs-validation" novalidate action="${pageContext.request.contextPath}/manager/"  method="post">
                                                <div class="modal-body">
                                                    <input type="hidden" name="command" value="editPassword">
                                                    <input type="hidden" name="id" value="${sessionScope.user.getId()}">
                                                    <div class="form-floating mb-3">
                                                        <div class="input-group">
                                                            <span class="input-group-text" id="basic-addon"><fmt:message key="label.password"/></span>
                                                            <input type="password" name="password" class="form-control" id="password" required>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="close"/></button>
                                                    <button class="btn btn-warning" type="submit"><fmt:message key="edit"/></button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-6">
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
                            <div class="card-body" style="padding-top: 0;padding-bottom: 0;">

                                <div class="row col-lg-5 p-4" style="width: 600px; margin-left: 0px;">
                                    <c:forEach items="${sessionScope.roomMap}" var="room" varStatus="loop">
                                        <div class="col" style="width: 17%; margin-bottom: 5px">
                                            <div class="card shadow-sm">
                                                <a <c:if test="${room.getValue() ne 'FREE'}"> href="${pageContext.request.contextPath}/manager/?command=viewOccupancyOfRoom&numberOfRoom=${room.getKey()}&status=${room.getValue()}"</c:if> <tags:buttonByStatus value="${room.getValue()}"/>
                                                        data-bs-toggle="tooltip"
                                                        data-bs-title="<fmt:message key="room.number"/>${room.getKey()} <fmt:message key="form.status"/>&nbsp;:&nbsp;<fmt:message key="status.${room.getValue()}"/>" data-bs-content="${room.getValue()}"
                                                >
                                                    <c:if test="${room.getValue() eq 'FREE'}"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-door-open-fill" viewBox="0 0 16 16">
                                                        <path d="M1.5 15a.5.5 0 0 0 0 1h13a.5.5 0 0 0 0-1H13V2.5A1.5 1.5 0 0 0 11.5 1H11V.5a.5.5 0 0 0-.57-.495l-7 1A.5.5 0 0 0 3 1.5V15H1.5zM11 2h.5a.5.5 0 0 1 .5.5V15h-1V2zm-2.5 8c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z"/>
                                                    </svg></c:if>
                                                    <c:if test="${room.getValue() ne 'FREE'}">
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-door-closed-fill" viewBox="0 0 16 16">
                                                            <path d="M12 1a1 1 0 0 1 1 1v13h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V2a1 1 0 0 1 1-1h8zm-2 9a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                                                        </svg>
                                                    </c:if>
                                                    <p class="mb-0">${room.getKey()}</p>
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
    </section>


    </div>
    </div>
</main>
<%@ include file="/jspf/footer.jspf" %>
<script src="../script.js" ></script>
</body>
</html>
