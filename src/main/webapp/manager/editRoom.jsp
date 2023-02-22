<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html class="h-100" lang=${sessionScope.locale}>
<head>
  <title><fmt:message key="title.editRoom"/></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.min.js" integrity="sha384-IDwe1+LCz02ROU9k972gdyvl+AESN10+x7tBKgc9I5HFtuNz0wWnPclzo6p9vxnk" crossorigin="anonymous"></script>
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
<main>
  <div class="container marketing">
    <hr class="feature-divider">

    <div class="card mb-3 p-4">
      <div class="row g-0">
        <div class="col-md-6">
        <img class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" src="${pageContext.request.contextPath}/images/${sessionScope.roomEdit.getImage()}" role="img" aria-label="Placeholder: 500x500" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#eee"></rect></img>
        </div>
        <div class="col-md-6">
        <form class="needs-validation" novalidate action="${pageContext.request.contextPath}/manager/"  method="post">
          <input type="hidden" name="command" value="editRoom">
        <h2 class="featurette-heading fw-normal lh-1"><fmt:message key="edit.room"/>${sessionScope.roomEdit.getId()}</h2>
          <div class="form-floating mb-3">
            <label for="image"><fmt:message key="form.image"/></label>
            <div class="input-group">
              <input type="file" name="image" class="form-control" id="image">
              <span class="input-group-text" id="basic-addon0">.jpg</span>
            </div>
          </div>
          <div class="form-floating mb-3">
            <div class="input-group">
              <span class="input-group-text" id="basic-addon"><svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
            <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"></path>
        </svg></span>
              <input type="number" name="numberOfPerson" class="form-control" id="numberOfPerson" value="${sessionScope.roomEdit.getNumberOfPerson()}" min="1" required>
</div>
          </div>
          <div class="form-floating mb-3">
            <div class="input-group">
              <span class="input-group-text" id="basic-addon3"><fmt:message key="form.price"/></span>
              <input type="number" name="price" class="form-control" id="price" value="${sessionScope.roomEdit.getPrice()}" required>
              <span class="input-group-text" id="basic-addon1">&#8372;</span>
            </div>
          </div>
          <div class="form-floating mb-3">
            <div class="input-group">
              <span class="input-group-text" id="basic-addon2"><fmt:message key="form.classOfRoom"/></span>
              <select name="classOfRoom" class="form-select" id="classOfRoom"  required>
                <c:forEach items="${sessionScope.classOfRoom}" var="classOfRoom" varStatus="loop">
                  <option
                          <c:if test="${sessionScope.roomEdit.getClassOfRoom() eq classOfRoom}">selected</c:if>>
                      ${classOfRoom}</option>
                </c:forEach>
              </select>
            </div>
          </div>
          <c:forEach items="${sessionScope.roomEdit.getFeatures()}" var="feature" varStatus="loop">
            <div class="form-check form-check-inline">
              <input type="checkbox" class="form-check-input" name="features" id="${feature.getName()}" value="${feature.getId()}"
              <c:if test="${feature.isChecked() eq true}">checked</c:if>>
              <label class="form-check-label" for="${feature.getName()}">${feature.getName()}</label>
            </div>
          </c:forEach>
          <div class="col-12">
            <button class="w-100 mb-2 btn btn-lg rounded-3 btn-warning" type="submit"><fmt:message key="form.edit"/></button>
          </div></form>
      </div>
    </div>
  </div>
  </div>
</main>
</div>
<script src="../script.js" ></script>
<%@ include file="/jspf/footer.jspf" %>
</body>
</html>
