<%@ page import="java.util.*,com.shohag.shopping.dao.Item"%>
<title>Order Summary</title>

<style>
table, td {
	border: 1px solid black;
	border-collapse: collapse;
}
td {
	padding: 15px;
}
</style>
<h3>Cart summary</h3>

<form action="shop" method="post">
	<input type="hidden" name="pageName" value="summary" />
	
	<table style="width: 100%">
		<%ArrayList<Item> items = (ArrayList<Item>) session.getAttribute("items");
		Integer totalPrice = 0;
		Hashtable<Integer,Integer> order = (Hashtable<Integer,Integer>) session.getAttribute("order");
		if (items != null && !items.isEmpty() && order !=null && !order.isEmpty()){
		Set<Integer> set = order.keySet();
		
		for (Item item : items) {
			if(set.contains(item.getId())){
		%>
		<tr>
			<td><%=item.getName()%></td>
			<td><%=order.get(item.getId())%></td>
			<td>$<%=order.get(item.getId())*item.getPrice()%></td>
		</tr>
		<% totalPrice = totalPrice + order.get(item.getId())*item.getPrice();
			}
		}}
		%>
	</table>
	<hr/>
	<h3><pre>Total :                $<%=totalPrice %></pre></h3>
	<pre><input type="submit" name="summaryAction" value="Back to Cart"/>			<input type="submit" name="summaryAction" value="Check out"/></pre>
</form>
