<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
    <title><fmt:message key="header.login"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</head>

<body>
<header class="p-3 text-bg-dark">
    <input type="hidden" name="role" value="CLIENT">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                <img src="${pageContext.request.contextPath}/images/—Pngtree—watermelon%20logo_6945475.png" height="50px">
            </a>

            <div class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <a href="${pageContext.request.contextPath}/client/?command=home" class="nav-link px-2 text-secondary"><fmt:message key="header.home"/></a>
                <li><a href="${pageContext.request.contextPath}/client/?command=rooms" class="nav-link px-2 text-white"><fmt:message key="header.rooms"/></a></li>
                <li><a href="${pageContext.request.contextPath}/client/?command=viewApplications" class="nav-link px-2 text-white"><fmt:message key="header.applications"/></a></li>
                <li><a href="${pageContext.request.contextPath}/client/?command=viewBooking" class="nav-link px-2 text-white"><fmt:message key="header.booking"/></a></li>
            </div>

            <div class="text-end d-flex" style="padding-right: 10px">
                <a href="${pageContext.request.contextPath}/client/?command=logout" class="btn btn-outline-warning me-2"><fmt:message key="header.logout"/></a>
            </div>
            <div class="locale">
                <form action="${pageContext.request.contextPath}/locale/" method="get">
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
<main>
    <div class="container marketing">
        <div class="table-responsive-sm">
            <div class="modal-header p-5 pb-4 border-bottom-0">
                <h1 class="fw-bold mb-0 fs-2">Bookings</h1>
            </div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col"><fmt:message key="form.numberOfRoom"/></th>
                    <th scope="col"><fmt:message key="form.numberOfPerson"/></th>
                    <th scope="col"><fmt:message key="form.checkInDate"/></th>
                    <th scope="col"><fmt:message key="form.checkOutDate"/></th>
                    <th scope="col"><fmt:message key="form.price"/></th>
                    <th scope="col"><fmt:message key="form.dateOfBooking"/></th>
                    <th scope="col"><fmt:message key="form.status"/></th>
                    <th scope="col"><fmt:message key="form.bookingExpirationDate"/></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.bookings}" var="booking" varStatus="loop">
                    <form class="needs-validation" novalidate action="${pageContext.request.contextPath}/client/"  method="post">
                        <input type="hidden" name="command" value="payForBooking">
                        <input type="hidden" name="booking" value="${booking.getId()}">
                    <tr>
                        <th scope="row"><a href="${pageContext.request.contextPath}/client/?command=applicationFormPage">${booking.getId()}</a></th>
                        <td>${booking.getRoomId()}</td>
                        <td>${booking.getNumberOfPerson()}</td>
                        <td>${booking.getCheckInDate()}</td>
                        <td>${booking.getCheckOutDate()}</td>
                        <td>${booking.getPrice()}</td>
                        <td>${booking.getDateOfBooking()}</td>
                        <td>${booking.getStatusOfBooking()}</td>
                        <c:if test="${booking.getStatusOfBooking() eq 'PAID'}">
                        <td>-</td>
                        </c:if>
                        <c:if test="${booking.getStatusOfBooking() eq 'BOOKED'}">
                            <td>${booking.getBookingExpirationDate()}</td>
                        <td><button type="submit" class="btn btn-outline-dark" value="${booking}">Pay</button></td>
                        </c:if>
                    </tr>
                    </form>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
</body>
</html>
