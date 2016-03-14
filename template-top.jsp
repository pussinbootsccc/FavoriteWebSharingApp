<%--  
 08-672 Homework#4.
 @author Yujie Cha (Andrew ID: yujiecha)
 December 12, 2015
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<title>Favorites Sharing Website</title>
	<style>
		html * { font-family:'Open Sans', sans-serif; }
  		.menu-head {font-size: 11pt; font-weight: bold; color: black; }
		.menu-item {font-size: 11pt;  color: black }
		h1 { color: #F90B6D; font-size: 34px; font-weight: 300; line-height: 40px; margin: 0 0 16px; }
		h2 { color: #F90B6D; font-size: 24px; font-weight: 300; line-height: 32px; margin: 0 0 14px; }
		p { color: #222; font-size: 15px; font-weight: 400; line-height: 24px; margin: 0 0 14px; }		
    </style>
</head>

<body>
<%@ page import="databeans.User" %>
<%@ page import="databeans.FavoriteBean" %>

<table cellpadding="10" cellspacing="0">
    <tr>
	    <!-- Banner row across the top -->
        <td width="130" bgcolor="#FFE4E1"> </td>
        <td bgcolor="#FFE4E1">&nbsp;  </td>
        <td width="500" bgcolor="#FFE4E1">
            <p align="center">
				<c:choose>
				    <c:when test="${empty title}">
						<h1>Favorites Sharing Website</h1>
				    </c:when>
				    <c:otherwise>
				        <font size="5">${title}</font>
				    </c:otherwise>
				</c:choose>
			</p>
		</td>
    </tr>
	
	<!-- Spacer row -->
	<tr>
		<td bgcolor="#FFE4E1" style="font-size:5px">&nbsp;</td>
		<td colspan="2" style="font-size:5px">&nbsp;</td>
	</tr>
	
	<tr>
		<td bgcolor="#FFE4E1" valign="top" height="500">
			<!-- Navigation bar is one table cell down the left side -->
            <p align="left">

		<c:choose>
		    <c:when test="${empty sessionScope.user}">
				<span class="menu-item"><a href="login.do">Login</a></span><br/>
				<span class="menu-item"><a href="register.do">Register</a></span><br/>
		    </c:when>
		    <c:otherwise>
				<span class="menu-head">${sessionScope.user.firstName} ${sessionScope.user.lastName}</span><br/>
				<span class="menu-item"><a href="manage.do">Manage My Favorites</a></span><br/>
				<span class="menu-item"><a href="change-pwd.do">Change Password</a></span><br/>
				<span class="menu-item"><a href="logout.do">Logout</a></span><br/>
		    </c:otherwise>
		</c:choose>           

				<span class="menu-item">&nbsp;</span><br/>
				<span class="menu-head">Favorites From:</span><br/>

        <!-- show all user names with hyper-link in navigation bar -->
		<c:forEach var="user" items="${userList}">
			    <span class="menu-item">
					<a href="list.do?userId=${user.userId}">${user.firstName} ${user.lastName}</a>
				</span>
				<br/>
		</c:forEach>

		</p>
		</td>
		
		<td>
			<!-- Padding (blank space) between navbar and content -->
		</td>
		<td valign="top">		
