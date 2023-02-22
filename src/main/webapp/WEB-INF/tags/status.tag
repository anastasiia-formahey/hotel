<%@ attribute name="value" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<c:choose>
        <c:when test="${value == 'NEW'}">
                class="badge bg-primary"
        </c:when>
        <c:when test="${value == 'FREE'}">
                class="badge bg-success"
        </c:when>
        <c:when test="${value == 'PAID'}">
               class="badge bg-success"
        </c:when>
        <c:when test="${value == 'BUSY'}">
                class="badge bg-danger"
        </c:when>
        <c:when test="${value == 'CONFIRMED'}">
               class="badge bg-success"
        </c:when>
        <c:when test="${value == 'REVIEWED'}">
               class="badge bg-warning"
        </c:when>
        <c:when test="${value == 'BOOKED'}">
               class="badge bg-warning"
        </c:when>
        <c:when test="${value == 'NOT_CONFIRMED'}">
                class="badge bg-secondary"
        </c:when>
</c:choose>