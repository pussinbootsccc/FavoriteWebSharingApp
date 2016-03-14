<%--  
 08-672 Homework#4.
 @author Yujie Cha (Andrew ID: yujiecha)
 December 12, 2015
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="template-top.jsp" />

<%@ page import="databeans.User" %>
<%@ page import="databeans.FavoriteBean" %>

<p style="font-size:medium">
	You are browsing the favorites of ${user.firstName} ${user.lastName}
</p>
<jsp:include page="error-list.jsp" />

<table>
	<c:forEach var="error" items="${errors}">
		${error}<br/>
	</c:forEach>
	
	<c:forEach var="favorite" items="${favoriteList}">
	    <tr>
	        <td>
	        	<a href="click.do?favoriteId=${favorite.favoriteId}">${favorite.url}</a>
	        	<br/>
	        	${favorite.comment}
	        	<br/>
	        	${favorite.clickCount} Clicks
	        </td>
    	</tr>
	</c:forEach>
</table>
<jsp:include page="template-bottom.jsp" />