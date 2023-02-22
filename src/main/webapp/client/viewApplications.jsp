<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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
            <option value="en" ${locale == 'en' ? 'selected' : ''}><fmt:message key="lang.en"/></option>
            <option value="ua" ${locale == 'ua' ? 'selected' : ''}><fmt:message key="lang.ua"/></option>
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
        <h1 class="fw-bold mb-0 fs-2"><fmt:message key="applications"/></h1>
      </div>
    <table class="table table-hover">
      <thead>
      <tr>
        <th scope="col">№</th>
        <th scope="col"><fmt:message key="form.numberOfPerson"/></th>
        <th scope="col"><fmt:message key="form.classOfRoom"/></th>
        <th scope="col"><fmt:message key="form.lengthOfStay"/></th>
        <th scope="col"><fmt:message key="form.status"/></th>
        <th scope="col"></th>
      </tr>
      </thead>
      <tbody>
        <c:forEach items="${sessionScope.applications}" var="application" varStatus="loop">
        <form class="needs-validation" novalidate action="${pageContext.request.contextPath}/client/"  method="get">
          <input type="hidden" name="command" value="getRequest">
          <input type="hidden" name="applicationId" value="${application.getId()}">
      <tr>
        <th scope="row">${application.getId()}</th>
        <td>${application.getNumberOfGuests()}</td>
        <td>${application.getClassOfRoom()}</td>
        <td>${application.getLengthOfStay()} day(s)</td>
        <td><span
                <tags:status value="${application.getStatus()}"/>><fmt:message key="status.${application.getStatus()}"/></span>
        </td>
        <td>
            <button type="submit" class="btn btn-outline-dark"
                    <c:if test="${application.getStatus() != 'REVIEWED'}">
                      disabled</c:if>>
              Get request
            </button>
</td>
      </tr>
        </form>
        </c:forEach>
      </tbody>
    </table>
      <nav aria-label="Navigation for responses">
      <ul class="pagination justify-content-center">
        <c:if test="${requestScope.currentPage != 1}">
          <li class="page-item"><a class="page-link"
                                   href="${pageContext.request.contextPath}/client/?command=viewApplications&currentPage=${requestScope.currentPage-1}&recordsPerPage=${requestScope.recordsPerPage}&orderBy=${requestScope.orderBy}"><</a>
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
                                       href="${pageContext.request.contextPath}/client/?command=viewApplications&currentPage=${i}&recordsPerPage=${requestScope.recordsPerPage}&orderBy=${requestScope.orderBy}">${i}</a>
              </li>
            </c:otherwise>
          </c:choose>
        </c:forEach>

        <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
          <li class="page-item"><a class="page-link"
                                   href="${pageContext.request.contextPath}/client/?command=viewApplications&currentPage=${requestScope.currentPage+1}&recordsPerPage=${requestScope.recordsPerPage}&orderBy=${requestScope.orderBy}">></a>
          </li>
        </c:if>
      </ul>
    </nav>
    </div>

  </div>

    </div>
  </div>
</main>
<jsp:include page="/jspf/footer.jspf"/>
</body>
</html>
