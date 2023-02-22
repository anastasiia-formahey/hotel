<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html class="h-100" lang=${sessionScope.locale}>
<head>
  <title><fmt:message key="header.rooms"/></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body class="h-100">
<header class="p-3 text-bg-dark">
  <div class="container">
    <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
      <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
        <img src="${pageContext.request.contextPath}/images/—Pngtree—watermelon%20logo_6945475.png" height="50px">
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
        <a href="${pageContext.request.contextPath}/manager/?command=logout" class="btn btn-outline-warning me-2"><fmt:message key="header.logout"/></a>
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
    <div class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
      <a href="${pageContext.request.contextPath}/manager/?command=addRoomPage" class="btn btn-warning"><fmt:message key="button.addRoom"/></a>
  </div>
    <div class="dropdown">
      <button class="btn btn-warning dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
        <fmt:message key="sortBy"/>
      </button>
      <ul class="dropdown-menu dropdown-menu-end" >
        <li><a class="dropdown-item rounded-2" href="${pageContext.request.contextPath}/manager/?command=rooms&orderBy=price"><fmt:message key="form.price"/></a></li>
        <li><a class="dropdown-item rounded-2" href="${pageContext.request.contextPath}/manager/?command=rooms&orderBy=number_of_person"><fmt:message key="form.numberOfPerson"/></a></li>
        <li><a class="dropdown-item rounded-2" href="${pageContext.request.contextPath}/manager/?command=rooms&orderBy=class_of_room"><fmt:message key="form.classOfRoom"/></a></li>
      </ul>
    </div>
</div>
</div>
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
            <p class="card-text"> <b><fmt:message key="form.price"/></b> : ${room.getPrice()}&#8372;, <b><fmt:message key="form.classOfRoom"/></b>: ${room.getClassOfRoom()}</p>
            <div class="d-flex justify-content-between align-items-center">
              <div class="btn-group">
                <button type="submit" name="command" value="viewRoom" class="btn btn-sm btn-outline-dark"><fmt:message key="button.view"/></button>
                <button type="submit" name="command" value="editRoomPage" class="btn btn-sm btn-outline-dark"><fmt:message key="button.edit"/></button>
              </div>
              <tags:status value="${room.getStatus()}"/>
            </div>
          </div>
        </div>
      </div>
        </form>
      </c:forEach>
    </div>
  </div>
</div>

<nav aria-label="Navigation for responses">
  <ul class="pagination justify-content-center">
    <c:if test="${requestScope.currentPage != 1}">
      <li class="page-item "><a class="page-link "
                               href="${pageContext.request.contextPath}/manager/?command=rooms&currentPage=${requestScope.currentPage-1}&recordsPerPage=${requestScope.recordsPerPage}&orderBy=${requestScope.orderBy}"><</a>
      </li>
    </c:if>

    <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
      <c:choose>
        <c:when test="${requestScope.currentPage eq i}">
          <li class="page-item active"><a class="page-link">
              ${i} <span class="sr-only"></span></a>
          </li>
        </c:when>
        <c:otherwise>
          <li class="page-item"><a class="page-link"
                                   href="${pageContext.request.contextPath}/manager/?command=rooms&currentPage=${i}&recordsPerPage=${requestScope.recordsPerPage}&orderBy=${requestScope.orderBy}">${i}</a>
          </li>
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
      <li class="page-item"><a class="page-link"
                               href="${pageContext.request.contextPath}/manager/?command=rooms&currentPage=${requestScope.currentPage+1}&recordsPerPage=${requestScope.recordsPerPage}&orderBy=${requestScope.orderBy}">></a>
      </li>
    </c:if>
  </ul>
</nav>

<%@ include file="/jspf/footer.jspf" %>
</body>
</html>
