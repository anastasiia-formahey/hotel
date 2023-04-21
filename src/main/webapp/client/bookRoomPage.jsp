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
      <a class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
        <img src="${pageContext.request.contextPath}/images/logo.png" height="50px">
      </a>

      <div class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
        <a href="${pageContext.request.contextPath}/client/?command=home" class="nav-link px-2 text-secondary"><fmt:message key="header.home"/></a>
        <li><a href="${pageContext.request.contextPath}/client/?command=rooms" class="nav-link px-2 text-white"><fmt:message key="header.rooms"/></a></li>
        <li><a href="${pageContext.request.contextPath}/client/?command=viewApplications" class="nav-link px-2 text-white"><fmt:message key="header.applications"/></a></li>
        <li><a href="${pageContext.request.contextPath}/client/?command=viewBooking" class="nav-link px-2 text-white"><fmt:message key="header.booking"/></a></li>
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
        <div class="col-md-6">
          <img class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" src="${pageContext.request.contextPath}/images/${sessionScope.room.getImage()}" role="img" aria-label="Placeholder: 500x500" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#eee"></rect></img>
        </div>
        <div class="col-md-6">
        <form action="${pageContext.request.contextPath}/client/">
          <input type="hidden" name="command" value="bookRoom">
          <c:forEach items="${sessionScope.bookingDTOS}" var="booking" varStatus="loop">
          <input type="hidden" name="roomId" value="${sessionScope.room.getId()}">
          <h2 class="featurette-heading fw-normal lh-1"><fmt:message key="book.room"/>${sessionScope.room.getId()}</h2>

          <div class="form-floating mb-3">
            <div class="input-group">
            <span class="input-group-text" id="basic-addon"><svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
            <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"></path>
        </svg></span>
            <input type="number" name="numberOfPerson" class="form-control" id="numberOfPerson" value="${booking.getRoom().getNumberOfPerson()}" readonly>
          </div>
          </div>
          <div class="form-floating mb-3">
          <div class="input-group">
            <span class="input-group-text" id="basic-addon2"><fmt:message key="form.classOfRoom"/></span>
            <input type="text" name="classOfRoom" class="form-control" id="classOfRoom" value="${booking.getRoom().getClassOfRoom()}" readonly>
          </div>
          </div>


          <div class="form-floating mb-3">
            <div class="input-group">
              <span class="input-group-text" id="basic-addon3"><fmt:message key="form.checkInDate"/></span>
              <input type="text" name="checkInDate" class="form-control" id="checkInDate" value="${booking.getCheckInDate()}" readonly>
            </div>
          </div>

          <div class="form-floating mb-3">
            <div class="input-group">
              <span class="input-group-text" id="basic-addon4"><fmt:message key="form.checkOutDate"/></span>
              <input type="text" name="checkOutDate" class="form-control" id="checkOutDate" value="${booking.getCheckOutDate()}" readonly>
            </div>
          </div>

          <div class="form-floating mb-3">
            <div class="input-group">
              <span class="input-group-text" id="basic-addon5"><fmt:message key="form.price"/></span>
              <input type="text" name="price" class="form-control" id="price" value="${booking.getPrice()}" readonly>
            </div>
          </div>

          <div class="col-12">
            <button class="w-100 mb-2 btn btn-lg rounded-3 btn-primary" type="submit"><fmt:message key="button.book"/></button>
          </div>
          </c:forEach>
            </form>
      </div>
    </div>
  </div>
  </div>
</main>
</body>
</html>
