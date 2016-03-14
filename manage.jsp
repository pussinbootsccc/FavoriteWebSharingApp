<%--  
 08-672 Homework#4.
 @author Yujie Cha (Andrew ID: yujiecha)
 December 12, 2015
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Favorites Websites of ${user.firstName} ${user.lastName}
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="POST" action="add.do" >
	    <table>
	        <tr><td colspan="3"><hr/></td></tr>
	        <tr>
	            <td style="font-size: large">URL:</td>
	            <td colspan="2"><input type="text" size="40" name="url" value="${form.url}"/></td>
	        </tr>
	        <tr>
	            <td style="font-size: large">Comment:</td>
	            <td colspan="2"><input type="text" size="40" name="comment" value="${form.comment}"/></td>
	        </tr>
	        <tr>
	            <td></td>
	            <td><input type="submit" name="button" value="Add Favorite"/></td>
	        </tr>
	        <tr><td colspan="3"><hr/></td></tr>
	    </table>
	</form>
</p>

<%@ page import="databeans.FavoriteBean" %>

<table>
	<c:forEach var="favorite" items="${favoriteList}">
           <tr>
           	<td>
                <form method="POST" action="delete.do">
                    <!--Use hidden field to track which favorite is clicked-->
                	<input type="hidden" name="id" value="${favorite.favoriteId}"/>
                	<input type="submit" value="X"/>
            	</form>
            </td>
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