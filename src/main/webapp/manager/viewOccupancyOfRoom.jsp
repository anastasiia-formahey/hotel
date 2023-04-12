<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ptg" uri="/WEB-INF/price.tld" %>
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
<main>
    <div class="container marketing">
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
        <div class="card mb-3 p-4">
            <div class="row g-0">
                    <div class="row g-2">
                        <div class="col-md-5">
                            <img class="bd-placeholder-img card-img-top form-control-lg" width="100%" height="100%" src="${pageContext.request.contextPath}/images/${requestScope.room.getImage()}" role="img"></div>
                        <div class="col-md-5">
                            <h3 class="featurette-heading fw-normal lh-1"><fmt:message key="room.number"/>${requestScope.room.getId()}</h3>
                            <div class="form-floating mb-2">
                                <input type="text" name="numberOfPerson" class="form-control" id="numberOfPerson"
                                       value="${requestScope.room.getNumberOfPerson()}" readonly>
                                <label for="numberOfPerson"><fmt:message key="form.numberOfPerson"/></label>
                            </div>
                            <div class="form-floating mb-2">
                                <input type="text" name="classOfRoom" class="form-control" id="classOfRoom"
                                       value="${requestScope.room.getClassOfRoom()}" readonly>
                                <label for="classOfRoom"><fmt:message key="form.classOfRoom"/></label>
                            </div>
                            <div class="form-floating mb-2">
                                <input type="text" name="price" class="form-control" id="price"
                                       value="<ptg:price value="${requestScope.room.getPrice()}"/>" readonly>
                                <label for="price"><fmt:message key="form.price"/></label>
                            </div>
                            <div class="form-floating mb-2">
                                <p class="form-control" id="features"><c:forEach items="${requestScope.room.getFeatures()}" var="feature" varStatus="loop">

                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-circle-fill" viewBox="0 0 16 16">
                                            <path fill="#fd7e14" d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"></path>
                                        </svg>
                                            ${feature.getName()}&nbsp;
                                </c:forEach>
                                </p>
                                <label for="features"><fmt:message key="features"/></label>
                            </div>
                        </div>
                        <div class="card-body">

                            <%--Booking --%>

                            <div class="form-floating">
                                <h3 class="featurette-heading fw-normal lh-2"><fmt:message key="header.booking"/></h3>
                            </div>
                                <form action="${pageContext.request.contextPath}/manager/" method="post">
                                    <input type="hidden" name="command" value="checkInRoom">
                                    <input type="hidden" name="roomId" value="${requestScope.room.getId()}">
                                <div class="form-floating">
                                <div class="input-group">

                            <div class="form-floating">
                                <input type="text" name="client" class="form-control" id="client"
                                       value="${requestScope.occupancyOfRoom.getClient().getFirstName()} ${requestScope.occupancyOfRoom.getClient().getLastName()}" readonly>
                                <label for="client"><fmt:message key="form.client"/></label>
                            </div>
                            <div class="form-floating">
                                <input type="text" name="client" class="form-control" id="email"
                                       value="${requestScope.occupancyOfRoom.getClient().getEmail()}" readonly>
                                <label for="email"><fmt:message key="label.email"/></label>
                            </div>
                            <div class="form-floating">
                                <input type="text" name="checkIn" class="form-control" id="checkInModal"
                                       value="${requestScope.occupancyOfRoom.getCheckInDate()}" readonly>
                                <label for="checkInModal"><fmt:message key="form.checkInDate"/></label>
                            </div>

                            <div class="form-floating">
                                <input type="text" name="checkOut" class="form-control" id="checkOutModal"
                                       value="${requestScope.occupancyOfRoom.getCheckOutDate()}" readonly>
                                <label for="checkOutModal"><fmt:message key="form.checkOutDate"/></label>
                            </div>
                                    <div class="form-floating">
                                        <p class="form-control">
                                    <span
                                            <c:if test="${requestScope.occupancyOfRoom.getStatus() == 'NEW'}">class="badge bg-primary" </c:if>
                                            <c:if test="${requestScope.occupancyOfRoom.getStatus() == 'PAID'}">class="badge bg-success" </c:if>
                                            <c:if test="${requestScope.occupancyOfRoom.getStatus() == 'NOT_CONFIRMED'}">class="badge bg-secondary" </c:if>
                                            <c:if test="${requestScope.occupancyOfRoom.getStatus() == 'BOOKED'}">class="badge bg-warning" </c:if>
                                            <c:if test="${requestScope.occupancyOfRoom.getStatus() == 'BUSY'}">class="badge bg-danger" </c:if>
                                    ><fmt:message key="status.${requestScope.occupancyOfRoom.getStatus()}"/></span></p>
                                        <label for="client"> <fmt:message key="form.bookingStatus"/></label>


                                    </div>

                                </div>
                                </div>
                                <div class="container d-flex flex-wrap justify-content-end">
                                    <c:if test="${requestScope.occupancyOfRoom.getStatus() eq 'PAID'}">
                                    <button type="submit" class="btn btn-warning" ><fmt:message key="checkIn"/></button>
                                    </c:if>
                                </div>
                                </form>
                        </div>

                            <%--Booking --%>
                        </div>
                    </div>

            </div>
    </div>
    </div>
</main>
<%@ include file="/jspf/footer.jspf" %>
</body>
</html>
