<title>Log in</title>
<br/><br/>
<h2>Welcome to TestMart</h2>
<br/>
<hr/>
<br/><br/>
<%
	String loginFeedback = (String) request.getAttribute("loginFeedback");
	String loginFeedbackColor = (String) request.getAttribute("loginFeedbackColor");
	if (loginFeedback != null && loginFeedbackColor != null) {
%>
<h3><span style="color: <%=loginFeedbackColor%>"> <%=loginFeedback%></span></h3>
<%}%>

<form action="shop" method="post">
	<p>
		User Name : <input type="text" name="user">
	</p>
	<p>
		Password : <input type="password" name="password">
	</p>
		<pre><input type="submit" value="Login">            <input type="reset" value="Reset"></pre>
		<input type="hidden" name="pageName" value="login"/>
</form>
