<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ptg" uri="/WEB-INF/price.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html class="h-100" lang=${sessionScope.locale}>
<head>
    <title><fmt:message key="title.viewRequests"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</head>

<body class="h-100">
<header class="p-3 text-bg-dark">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <img src="${pageContext.request.contextPath}/images/logo.png" height="50px">
            </a>

            <div class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <a href="${pageContext.request.contextPath}/manager/?command=home" class="nav-link px-2 text-secondary"><fmt:message key="header.home"/></a>
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
            </div>

            <div class="text-end d-flex" style="padding-right: 10px">
                <a href="${pageContext.request.contextPath}/logout/?command=logout" class="btn btn-outline-warning me-2"><fmt:message key="header.logout"/></a>
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
<main>
    <div class="container marketing">
        <hr class="feature-divider">
        <div class="card mb-3 p-4">
            <div class="row g-0">
                <div class="modal-header align-content-center">
                    <h1 class="fs-2"><fmt:message key="request"/> ${application.getId()}</h1>
                </div>

                    <input type="hidden" name="applicationId" value="${application.getId()}">
                    <c:forEach items="${sessionScope.bookingDTOS}" var="booking" varStatus="loop">

                        <div class="card mb-3 p-2">
                            <div class="row g-2">
                                <div class="col-md-7">
                                    <img class="bd-placeholder-img card-img-top form-control-lg" width="100%" height="100%" src="${pageContext.request.contextPath}/images/${booking.getRoom().getImage()}" role="img"></div>
                                <div class="col-md-5">
                                    <h3 class="featurette-heading fw-normal lh-1"><fmt:message key="room.number"/>${booking.getRoom().getId()}</h3>
                                    <div class="form-floating mb-2">
                                        <p class="card-text"> <fmt:message key="form.status"/>
                                            <span <tags:status value="${application.getStatus()}"/>><fmt:message key="status.${application.getStatus()}"/></span>

                                    </div>
                                    <div class="form-floating mb-2">
                                        <input type="text" name="client" class="form-control" id="client"
                                               value="${application.getUserDTO().getFirstName()} ${application.getUserDTO().getLastName()}" readonly>
                                        <label for="client"><fmt:message key="form.client"/></label>
                                    </div>
                                    <div class="form-floating mb-2">
                                        <input type="text" name="classOfRoom" class="form-control" id="numberOfPersonModal1"
                                               value="${booking.getRoom().getNumberOfPerson()}" readonly>
                                        <label for="numberOfPersonModal1"><fmt:message key="form.numberOfPerson"/></label>
                                    </div>

                                    <div class="form-floating mb-2">
                                        <input type="text" name="classOfRoom" class="form-control" id="classOfRoomModal1"
                                               value="${booking.getRoom().getClassOfRoom()}" readonly>
                                        <label for="classOfRoomModal1"><fmt:message key="form.classOfRoom"/></label>
                                    </div>

                                    <div class="form-floating mb-2">
                                        <input type="text" name="lengthOfStay" class="form-control" id="checkInModal"
                                               value="${booking.getCheckInDate()}" readonly>
                                        <label for="checkInModal"><fmt:message key="form.checkInDate"/></label>
                                    </div>

                                    <div class="form-floating mb-2">
                                        <input type="text" name="lengthOfStay" class="form-control" id="checkOutModal"
                                               value="${booking.getCheckOutDate()}" readonly>
                                        <label for="checkOutModal"><fmt:message key="form.checkOutDate"/></label>
                                    </div>

                                    <div class="form-floating mb-2">
                                        <input type="text" name="price" class="form-control" id="price"
                                               value="<ptg:price value="${booking.getPrice()}"/>" readonly>
                                        <label for="price"><fmt:message key="form.price"/>/<fmt:message key="uah"/></label>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </c:forEach>
            </div>
        </div>
    </div>
</main>
<%@ include file="/jspf/footer.jspf" %>
</body>
</html>
