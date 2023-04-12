<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ptg" uri="/WEB-INF/price.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html class="h-100" lang=${sessionScope.locale}>
<head>
  <title><fmt:message key="title.reviewApplication"/></title>
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
<div class="px-3 py-2 border-bottom mb-3">
  <div class="container d-flex flex-wrap justify-content-center">
    <form class="row g-3 needs-validation" action="${pageContext.request.contextPath}/manager/"  method="get" novalidate>
      <input type="hidden" name="command" value="findRoomForBooking">
      <div class="form-floating">
        <div class="input-group">
          <div class="form-floating">
            <input type="number" name="applicationNumber" class="form-control" id="applicationNumber"
                   value="${sessionScope.app.getId()}" readonly>
            <label for="applicationNumber"><fmt:message key="header.applicationNumber"/></label>
          </div>
          <div class="form-floating">
            <input type="text" name="classOfRoom" class="form-control" id="classOfRoom"
                   value="${sessionScope.app.getClassOfRoom()}" readonly>
            <label for="classOfRoom"><fmt:message key="form.classOfRoom"/></label>
          </div>
          <div class="form-floating">
            <input type="text" name="lengthOfStay" class="form-control" id="lengthOfStay"
                   value="${sessionScope.app.getLengthOfStay()} <fmt:message key="form.days"/>" readonly>
            <label for="lengthOfStay"><fmt:message key="form.lengthOfStay"/></label>
          </div>

          <div class="form-floating">
          <input type="number" name="numberOfPerson" class="form-control" id="numberOfPerson"
                 value="${sessionScope.app.getNumberOfGuests()}" min="1" required>
            <label for="numberOfPerson"><fmt:message key="form.numberOfPerson"/></label>
          </div>
          <div class="form-floating">
          <input type="date" class=" form-control" name="checkInDate" id="checkInDate"
                 value="${requestScope.checkIn}" required>
            <label for="checkInDate"><fmt:message key="form.checkInDate"/></label>
          </div>
          <div class="form-floating">
          <input type="date" class=" form-control" name="checkOutDate" id="checkOutDate"
                 value="${requestScope.checkOut}" required>
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

          <button type="submit" class="btn btn-warning">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search"
                 viewBox="0 0 16 16">
              <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"></path>
            </svg>
          </button>
        </div>
      </div>
    </form>

  </div>
<%--  Place for rooms to book--%>
<main>
  <div class="container marketing" style="height: 100%">
    <hr class="feature-divider">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <c:forEach items="${sessionScope.listOfRoomInReview}" var="room" varStatus="loop">
          <form action="${pageContext.request.contextPath}/manager/" method="get">
            <div class="col">
              <div class="card shadow-sm">
                <input type="hidden" name="numberOfRoom" value="${room.getId()}">
                <img class="bd-placeholder-img card-img-top" width="100%" height="225" src="${pageContext.request.contextPath}/images/${room.getImage()}" role="img">
                <div class="card-body">
                  <p class="card-text"><fmt:message key="room.number"/> ${room.getId()}  &nbsp;&nbsp;&nbsp; <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
                    <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"></path>
                  </svg>- ${room.getNumberOfPerson()}</p>
                  <p class="card-text"> <b><fmt:message key="form.price"/></b> : <ptg:price value="${room.getPrice()}"/>, <b><fmt:message key="form.classOfRoom"/></b>: ${room.getClassOfRoom()}</p>
                  <div class="d-flex justify-content-between align-items-center">

                    <button id="delete" type="submit" name="command" value="deleteRoomFromReview" class="btn btn-sm btn-outline-dark" title="<fmt:message key="delete.room"/>"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                      <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5ZM11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H2.506a.58.58 0 0 0-.01 0H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1h-.995a.59.59 0 0 0-.01 0H11Zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5h9.916Zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47ZM8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5Z"></path>
                    </svg></button>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </c:forEach>
      </div>

    <c:if test="${not empty sessionScope.listOfRoomInReview}" >
    <div></div>
    <hr class="feature-divider">
    <%--start modal window--%>
    <div class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
      <div class="d-grid gap-2 col-6 mx-auto">
        <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#viewRequest" data-bs-whatever="@mdo"><fmt:message key="review"/> </button>
      </div><div class="modal fade" id="viewRequest" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-xl">
        <div class="modal-content">
          <div class="modal-header align-content-center">
            <h1 class="modal-title fs-5" id="exampleModalLabel"><fmt:message key="header.applicationNumber"/>${sessionScope.app.getId()}</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <form class="needs-validation" novalidate action="${pageContext.request.contextPath}/manager/"  method="post">
            <div class="modal-body">
              <input type="hidden" name="command" value="sendRequest">
              <div class="form-floating">
                <div class="input-group">
                  <div class="form-floating">
                    <input type="text" name="applicationNumber" class="form-control" id="userModal"
                           value="${sessionScope.app.getUserDTO().getFirstName()}
                   ${sessionScope.app.getUserDTO().getLastName()}" readonly>
                    <label for="userModal"><fmt:message key="form.client"/></label>
                  </div>
                  <div class="form-floating">
                    <input type="text" name="classOfRoom" class="form-control" id="classOfRoomModal"
                           value="${sessionScope.app.getClassOfRoom()}" readonly>
                    <label for="classOfRoomModal"><fmt:message key="form.classOfRoom"/></label>
                  </div>
                  <div class="form-floating">
                    <input type="text" name="lengthOfStay" class="form-control" id="lengthOfStayModal"
                           value="${sessionScope.app.getLengthOfStay()} <fmt:message key="form.days"/>" readonly>
                    <label for="lengthOfStayModal"><fmt:message key="form.lengthOfStay"/></label>
                  </div>

                  <div class="form-floating">
                    <input type="number" name="numberOfPersonModal" class="form-control" id="numberOfPersonModal"
                           value="${sessionScope.app.getNumberOfGuests()}" readonly>
                    <label for="numberOfPersonModal"><fmt:message key="form.numberOfPerson"/></label>
                  </div>
                </div></div>
              <%--              request part--%>
              <div class="modal-header align-content-center">
                <h1 class="modal-title fs-5" id="exampleModalLabel1"><fmt:message key="request"/>${sessionScope.app.getId()}</h1>
              </div>

              <c:forEach items="${sessionScope.requestDTO.getRequestElements()}" var="requestElement" varStatus="loop">
                <div class="form-floating">
                  <div class="input-group">
                    <div class="form-floating">
                      <img class="bd-placeholder-img card-img-top form-control-lg" width="100%" height="100%" src="${pageContext.request.contextPath}/images/${requestElement.getRoom().getImage()}" role="img">
                    </div>
                    <div class="form-floating">
                      <input type="text" name="applicationNumber" class="form-control" id="roomId"
                             value="${requestElement.getRoom().getId()}" readonly>
                      <label for="roomId"><fmt:message key="room.number"/></label>
                    </div>
                    <div class="form-floating">
                      <input type="text" name="classOfRoom" class="form-control" id="classOfRoomModal1"
                             value="${requestElement.getRoom().getClassOfRoom()}" readonly>
                      <label for="classOfRoomModal"><fmt:message key="form.classOfRoom"/></label>
                    </div>
                    <div class="form-floating">
                      <input type="text" name="classOfRoom" class="form-control" id="numberOfPersonModal1"
                             value="${requestElement.getRoom().getNumberOfPerson()}" readonly>
                      <label for="numberOfPersonModal1"><fmt:message key="form.numberOfPerson"/></label>
                    </div>
                    <div class="form-floating">
                      <input type="text" name="lengthOfStay" class="form-control" id="checkInModal"
                             value="${requestElement.getCheckInDate()}" readonly>
                      <label for="checkInModal"><fmt:message key="form.checkInDate"/></label>
                    </div>

                    <div class="form-floating">
                      <input type="text" name="lengthOfStay" class="form-control" id="checkOutModal"
                             value="${requestElement.getCheckOutDate()}" readonly>
                      <label for="checkOutModal"><fmt:message key="form.checkOutDate"/></label>
                    </div>
                  </div></div>
              </c:forEach>
              <%--              request part--%>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="close"/></button>
              <button class="btn btn-warning" type="submit"><fmt:message key="form.send"/></button>
            </div>
          </form>
        </div>
      </div>
    </div>
    </div>
    </c:if>
    <%--end modal window--%>
  </div>
</main>
  <%--END Place for rooms to book--%>
</div>
<div class="album">
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
  <div class="alert alert-warning alert-dismissible fade show" role="alert">
    <strong><svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
      <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
    </svg>  </strong><span style="padding-left: 10px;"></span>
    <fmt:message key="info.${infoMessage}"/>
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
  </div>
</c:if>
  </div></div>
<c:if test="${sessionScope.roomsReview ne ''}">
<div class="album py-5">
  <div class="container">
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
      <c:forEach items="${sessionScope.rooms}" var="room" varStatus="loop">

        <form action="${pageContext.request.contextPath}/manager/" method="get">
          <div class="col">
            <div class="card shadow-sm">
              <input type="hidden" name="numberOfRoom" value="${room.getId()}">
              <img class="bd-placeholder-img card-img-top" width="100%" height="225" src="${pageContext.request.contextPath}/images/${room.getImage()}" role="img">
              <div class="card-body">
                <p class="card-text"><fmt:message key="room.number"/> ${room.getId()}  &nbsp;&nbsp;&nbsp; <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
                  <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"></path>
                </svg>- ${room.getNumberOfPerson()}</p>
                <p class="card-text"> <b><fmt:message key="form.price"/></b> : <ptg:price value="${room.getPrice()}"/>, <b><fmt:message key="form.classOfRoom"/></b>: ${room.getClassOfRoom()}</p>
                <div class="d-flex justify-content-between align-items-center">
                  <div class="btn-group">
                    <button type="submit" name="command" value="viewRoom" class="btn btn-sm btn-outline-dark"><fmt:message key="button.view"/></button>
                    </div>
                  <c:if test="${empty sessionScope.addedRooms}">

                    <button id="choose" type="submit" name="command" value="insertRoomInReview" class="btn btn-sm btn-outline-dark" title="<fmt:message key="addRoomToRequest"/>"
                    ><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                      <path fill-rule="evenodd" d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2Z"></path>
                    </svg></button>
                  </c:if>
                  <c:if test="${not empty sessionScope.addedRooms}">

                  <button id="choose" type="submit" name="command" value="insertRoomInReview" class="btn btn-sm btn-outline-dark" title="<fmt:message key="addRoomToRequest"/>"
            <c:forEach items="${sessionScope.addedRooms}" var="added" varStatus="loop">
              <c:if test="${added eq room.getId()}">
                disabled
              </c:if>
            </c:forEach>
                  ><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2Z"></path>
                  </svg></button>

                  </c:if>

                </div>
              </div>
            </div>
          </div>
        </form>
      </c:forEach>
    </div>
  </div>
</div>

</c:if>
<jsp:include page="/jspf/footer.jspf"/>

<script src="../script.js" ></script>
</body>
</html>
