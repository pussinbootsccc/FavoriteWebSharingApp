<%--  
 08-672 Homework#4.
 @author Yujie Cha (Andrew ID: yujiecha)
 December 12, 2015
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty errors && fn:length(errors) > 0}">
	<p style="font-size:medium; color:red">
		<c:forEach var="error" items="${errors}">
			${error}<br/>
		</c:forEach>
	</p>
</c:if>