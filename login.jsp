<%--  
 08-672 Homework#4.
 @author Yujie Cha (Andrew ID: yujiecha)
 December 12, 2015
--%>
<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Please login below or click <a href="register.do">here</a> to register as a new user.
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action="login.do">
		<table>
			<tr>
				<td> Email address: </td>
				<td><input type="text" name="emailAddress" value="${form.emailAddress}"/></td>
			</tr>
			<tr>
				<td> Password: </td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Login"/>
				</td>
			</tr>
		</table>
	</form>
</p>

<jsp:include page="template-bottom.jsp" />
