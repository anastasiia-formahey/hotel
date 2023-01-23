<%@ attribute name="value" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<c:choose>
        <c:when test="${value == 'NEW'}">
                <span class="badge bg-primary">NEW</span>
        </c:when>
        <c:when test="${value == 'FREE'}">
                <span class="badge bg-success">FREE</span>
        </c:when>
        <c:when test="${value == 'PAID'}">
                <span class="badge bg-success">PAID</span>
        </c:when>
        <c:when test="${value == 'BUSY'}">
                <span class="badge bg-danger">BUSY</span>
        </c:when>
        <c:when test="${value == 'CONFIRMED'}">
                <span class="badge bg-success">CONFIRMED</span>
        </c:when>
        <c:when test="${value == 'REVIEWED'}">
                <span class="badge bg-warning">REVIEWED</span>
        </c:when>
        <c:when test="${value == 'BOOKED'}">
                <span class="badge bg-warning">BOOKED</span>
        </c:when>
        <c:when test="${value == 'NOT_CONFIRMED'}">
                <span class="badge bg-secondary">NOT_CONFIRMED</span>
        </c:when>
</c:choose>