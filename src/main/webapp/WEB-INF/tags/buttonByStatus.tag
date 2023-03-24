<%@ attribute name="value" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:choose>
    <c:when test="${value == 'NEW'}">
        class="btn btn-outline-primary"
    </c:when>
    <c:when test="${value == 'FREE'}">
        class="btn btn-outline-success"
    </c:when>
    <c:when test="${value == 'BUSY'}">
        class="btn btn-outline-danger"
    </c:when>
    <c:when test="${value == 'PAID'}">
        class="btn btn-outline-warning"
    </c:when>
    <c:when test="${value == 'BOOKED'}">
        class="btn btn-outline-warning"
    </c:when>
    <c:when test="${value == 'NOT_CONFIRMED'}">
        class="btn btn-outline-secondary"
    </c:when>
</c:choose>