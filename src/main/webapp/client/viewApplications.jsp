<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html class="h-100" lang=${sessionScope.locale}>
<head>
  <title><fmt:message key="header.applications"/></title>
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
        rel="Stylesheet" type="text/css"/></head>

<body class="h-100">
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
  <div class="px-3 py-2 border-bottom mb-3">
  <div class="container d-flex flex-wrap justify-content-end">
  <div class="dropdown">
    <button class="btn btn-warning dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
      <fmt:message key="sortBy"/>
    </button>
    <ul class="dropdown-menu dropdown-menu-end" >
      <li><p class="dropdown-item rounded-2" ><fmt:message key="form.byNumber"/>
        <a class="link-warning" href="${pageContext.request.contextPath}/client/?command=viewApplications&orderBy=id ASC"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-sort-up" viewBox="0 0 16 16">
          <path d="M3.5 12.5a.5.5 0 0 1-1 0V3.707L1.354 4.854a.5.5 0 1 1-.708-.708l2-1.999.007-.007a.498.498 0 0 1 .7.006l2 2a.5.5 0 1 1-.707.708L3.5 3.707V12.5zm3.5-9a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM7.5 6a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zm0 3a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1h-3zm0 3a.5.5 0 0 0 0 1h1a.5.5 0 0 0 0-1h-1z"/>
        </svg></a> <a class="link-warning" href="${pageContext.request.contextPath}/client/?command=viewApplications&orderBy=id DESC"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-sort-down" viewBox="0 0 16 16">
          <path d="M3.5 2.5a.5.5 0 0 0-1 0v8.793l-1.146-1.147a.5.5 0 0 0-.708.708l2 1.999.007.007a.497.497 0 0 0 .7-.006l2-2a.5.5 0 0 0-.707-.708L3.5 11.293V2.5zm3.5 1a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM7.5 6a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zm0 3a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1h-3zm0 3a.5.5 0 0 0 0 1h1a.5.5 0 0 0 0-1h-1z"/>
        </svg></a>
      </p></li>
      <li><p class="dropdown-item rounded-2" ><fmt:message key="form.status"/>
        <a class="link-warning" href="${pageContext.request.contextPath}/client/?command=viewApplications&orderBy=status ASC"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-sort-up" viewBox="0 0 16 16">
          <path d="M3.5 12.5a.5.5 0 0 1-1 0V3.707L1.354 4.854a.5.5 0 1 1-.708-.708l2-1.999.007-.007a.498.498 0 0 1 .7.006l2 2a.5.5 0 1 1-.707.708L3.5 3.707V12.5zm3.5-9a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM7.5 6a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zm0 3a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1h-3zm0 3a.5.5 0 0 0 0 1h1a.5.5 0 0 0 0-1h-1z"/>
        </svg></a> <a class="link-warning" href="${pageContext.request.contextPath}/client/?command=viewApplications&orderBy=status DESC"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-sort-down" viewBox="0 0 16 16">
          <path d="M3.5 2.5a.5.5 0 0 0-1 0v8.793l-1.146-1.147a.5.5 0 0 0-.708.708l2 1.999.007.007a.497.497 0 0 0 .7-.006l2-2a.5.5 0 0 0-.707-.708L3.5 11.293V2.5zm3.5 1a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM7.5 6a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5zm0 3a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1h-3zm0 3a.5.5 0 0 0 0 1h1a.5.5 0 0 0 0-1h-1z"/>
        </svg></a>
      </p></li>
    </ul>
  </div>
  </div>
  </div>
  <div class="container marketing" style="height: 100%">
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
        <th scope="col"><fmt:message key="form.comment"/></th>
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
        <td>${application.getComment()}</td>
        <td><c:if test="${application.getStatus() eq 'REVIEWED'}">
            <button type="submit" class="btn btn-outline-dark">
              <fmt:message key="getRequest"/>
            </button></c:if>
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
