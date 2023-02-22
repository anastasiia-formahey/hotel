<%@ attribute name="value" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:choose>
    <c:when test="${value == 'NEW'}">
        class="btn btn-primary"
    </c:when>
    <c:when test="${value == 'FREE'}">
        class="btn btn-success"
    </c:when>
    <c:when test="${value == 'BUSY'}">
        class="btn btn-danger"
    </c:when>
    <c:when test="${value == 'PAID'}">
        class="btn btn-warning"
    </c:when>
    <c:when test="${value == 'BOOKED'}">
        class="btn btn-warning"
    </c:when>
    <c:when test="${value == 'NOT_CONFIRMED'}">
        class="btn btn-secondary"
    </c:when>
</c:choose>