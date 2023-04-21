<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ptg" uri="/WEB-INF/price.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html lang=${sessionScope.locale}>
<head>
  <title><fmt:message key="header.rooms"/></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js" type="text/javascript"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"
          type="text/javascript"></script>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
        rel="Stylesheet"type="text/css"/>
  <script src="../dateValidation.js" ></script>
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
<div class="px-3 py-2 border-bottom mb-3">
  <div class="container d-flex flex-wrap justify-content-center">
    <%@ include file="applicationModalWindow.jspf" %>
    <div class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
      <form class="row g-3 needs-validation" action="${pageContext.request.contextPath}/client/"  method="get" novalidate>
          <div class="input-group">
            <div class="form-floating">
            <input type="number" name="numberOfPerson" class="form-control" id="numberOfPerson"
                   value="${sessionScope.numberOfPerson}" min="1" required>
              <label for="numberOfPerson"><fmt:message key="form.numberOfPerson"/> </label>
            </div>
            <div class="form-floating">
            <input type="date" class=" form-control" name="checkInDate" id="checkInDate"
                   value="${sessionScope.checkIn}" required >
              <label for="checkInDate"><fmt:message key="form.checkInDate"/></label>
            </div>
            <div class="form-floating">
            <input type="date" class=" form-control" name="checkOutDate" id="checkOutDate"
                   value="${sessionScope.checkOut}" required>
              <label for="checkOutDate"><fmt:message key="form.checkOutDate"/></label>
            </div>
            <script>
              const options1={
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
              }
              const checkIn11 = new Date()
              checkIn11.setDate(checkIn11.getDate()+1)
              const minCheckIn1 = checkIn11.toLocaleString("ru",options1).split('.').reverse().join('-')
              document.getElementById("checkInDate").setAttribute("min",minCheckIn1)
              const getCheckIn1 = document.getElementById("checkInDate")
              getCheckIn1.addEventListener('input', getN)
              function getN(){
                const checkOut1 = document.getElementById("checkInDate").value
                const checkOut21 = new Date(Date.parse(checkOut1))
                checkOut21.setDate(checkOut21.getDate()+1)
                const minCheckOut1 = checkOut21.toLocaleString("ru",options1).split('.').reverse().join('-')
                document.getElementById("checkOutDate").setAttribute("min",minCheckOut1)
              }
            </script>

            <button type="submit" name="command" value="findRoomForBooking" class="btn btn-warning">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search"
                   viewBox="0 0 16 16">
                <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
              </svg>
            </button>
          </div>
      </form>
    </div>

    </div>

  </div>
</div>
<div class="album py-5">
  <div class="container">
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
      <div class="alert alert-info alert-dismissible fade show" role="alert">
        <strong><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
          <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
        </svg>  </strong><span style="padding-left: 10px;"></span>
        <fmt:message key="info.${infoMessage}"/>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      </div>
    </c:if>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">

      <c:forEach items="${sessionScope.rooms}" var="room" varStatus="loop">
        <form action="${pageContext.request.contextPath}/client/" method="get">
          <div class="col">
            <div class="card shadow-sm">
              <input type="hidden" name="numberOfRoom" value="${room.getId()}">
              <img class="bd-placeholder-img card-img-top" width="100%" height="225" src="${pageContext.request.contextPath}/images/${room.getImage()}" role="img">
              <div class="card-body">
                <p class="card-text"><svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
                  <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"></path>
                </svg>- ${room.getNumberOfPerson()}</p>
                <p class="card-text"> <b><fmt:message key="form.price"/></b> : <ptg:price value="${room.getPrice()}"/>, <b><fmt:message key="form.classOfRoom"/></b>: ${room.getClassOfRoom()}</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="submit" name="command" value="viewRoom" class="btn btn-sm btn-outline-dark"><fmt:message key="button.view"/></button>
                    <c:if test="${requestScope.isBooking eq true}">
                      <button type="submit" name="command" value="bookRoomPage" class="btn btn-sm btn-outline-dark"><fmt:message key="button.book"/></button>
                    </c:if></div>
                </div>
              </div>
            </div>
          </div>
        </form>
      </c:forEach>
    </div>
  </div>
</div>




</body>

<script src="../script.js" ></script>
</html>
