<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html class="h-100" lang=${sessionScope.locale}>
<head>
    <title><fmt:message key="header.login"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
</head>

<body class="h-100">
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
    <div class="container marketing" style="height: 100%">
        <hr class="feature-divider">
        <div class="h-100 card mb-3 p-4 text-center">
            <div class="row g-0">
                <div class="table-responsive-sm">
                    <div class="card-text p-2 pb-4 border-bottom-0">
                        <h1 class="fw-bold mb-0 fs-2">Bookings</h1>
                    </div>
                    <table class="table table-hover table-responsive align-middle">
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
                        <input type="hidden" name="roomId" value="${booking.getRoomId()}">
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
                        <td><button type="submit" class="btn btn-outline-dark" value="${booking}">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cash-coin" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z"></path>
                                <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z"></path>
                                <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z"></path>
                                <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z"></path>
                            </svg>Pay</button></td>
                        </c:if>
                    </tr>
                    </form>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
        </div>
    </div>
</main>
<jsp:include page="../jspf/footer.jspf"/>
</body>
</html>
