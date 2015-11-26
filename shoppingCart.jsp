<%@ page import="java.util.*,com.shohag.shopping.dao.*"%>

<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}
th, td {
	padding: 15px;
}
</style>

<%
	String orderFeedback = (String) request.getAttribute("orderFeedback");
	String feedbackColor = (String) request.getAttribute("feedbackColor");
	if (orderFeedback!= null && feedbackColor != null) {
%>
<h3><span style="color: <%=feedbackColor%>"> <%=orderFeedback%></span></h3>
<%}%>

<form action="shop" method="post">
	<input type="hidden" name="pageName" value="cart" />
	<br/>
	<table style="width: 100%">
		<tr>
			<th>&nbsp;</th>
			<th>Id</th>
			<th>Name</th>
			<th>Price</th>
			<th>Quantity</th>
		</tr>

		<%ArrayList<Item> items = (ArrayList<Item>) session.getAttribute("items");
		if(items != null && !items.isEmpty()){
		for (Item item : items) {%>
		<tr>
			<td><input type='checkbox' name='chkItem'
				value=<%=item.getId()%> /></td>
			<td><%=item.getId()%></td>
			<td><%=item.getName()%></td>
			<td>$<%=item.getPrice()%></td>
			<td><input type="text" name="<%=item.getId()%>"/></td>
		</tr>
		<%}}%>
	</table>
	
	<pre></pre><input type="submit" name="cartAction" value="Add to Cart"/>			<input type="submit" name="cartAction" value="Check out"/></pre>
</form>
