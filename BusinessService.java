package com.shohag.shopping.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shohag.shopping.dao.DataClass;
import com.shohag.shopping.dao.Item;
import com.shohag.shopping.email.EmailClass;
import com.shohag.shopping.transformer.PdfCreator;

public class BusinessService {
	private DataClass data = new DataClass();
	private Hashtable<Integer, Integer> order = new Hashtable<Integer, Integer>();
	
	public ArrayList<Item> getItems() {
		ArrayList<Item> items = data.getItemList();
		return items;
	}

	public String doLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String user = request.getParameter("user").trim();
		String password = request.getParameter("password");
		String nextPage = null;
		if (user != null && password != null && !user.isEmpty() && !password.isEmpty())   {
			boolean authentic = isAuthentic(user, password);
			if (authentic) {
				session = request.getSession(true);
				session.setAttribute("user", user);
				session.setAttribute("items", getItems());
				System.out.println("Created session : "+session.getId()+" Time :"+new Date(session.getCreationTime()));
				nextPage = "/cart.jsp";
			} 
			else {
				request.setAttribute("loginFeedback", "Invalid user name/password");
				request.setAttribute("loginFeedbackColor", "red");
				nextPage = "/login.jsp";
			}
		}
		else {	
				request.setAttribute("loginFeedback", "user/password can not be empty");
				request.setAttribute("loginFeedbackColor", "red");
				nextPage = "/login.jsp";
		} 		
		return nextPage;
	}
	
	public void doLogout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		request.setAttribute("loginFeedback", "You are successfully logged out!!");
		request.setAttribute("loginFeedbackColor", "green");
		System.out.println("closed logout : "+session.getId());
		session.invalidate();
	}

	public void addToCart(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String[] checkSelectedList = request.getParameterValues("chkItem");
		if (checkSelectedList != null && checkSelectedList.length != 0)
			verifyOrder(request, checkSelectedList, session);
		else {
			request.setAttribute("orderFeedback", "No selected item found");
			request.setAttribute("feedbackColor", "red");
		}
	}

	public String doCheckOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String[] checkSelectedList = request.getParameterValues("chkItem");
		String nextPage = null;
		if (checkSelectedList == null) {
			if (order == null || order.isEmpty()){
				request.setAttribute("orderFeedback", "No item found on your cart");
				request.setAttribute("feedbackColor", "red");
				nextPage = "cart.jsp";
			}
			else 
				nextPage = "summary.jsp";
		}
		else {// newly added item
				if(verifyOrder(request, checkSelectedList, session))
					nextPage = "summary.jsp";
				else
					nextPage = "cart.jsp";
		}
		return nextPage;
	}
	
	public void finalCheckOut(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		System.out.println("closed :"+session.getId());
		String user = (String)session.getAttribute("user");
		Hashtable<Integer,Integer> order = (Hashtable<Integer,Integer>) session.getAttribute("order");
		String orderDetails = order.toString();
		String pdfFileName = user+".pdf";
		PdfCreator.createPdf(user, orderDetails, pdfFileName);
		EmailClass.sendEmail(data.getEmail(user),pdfFileName);
		session.removeAttribute("order");
		session.invalidate();
	}
	

	private boolean verifyOrder(HttpServletRequest request, String[] checkList, HttpSession session) {
		boolean error = false;
		ArrayList<String> qtyList = new ArrayList<String>();
		for (String selectedItem : checkList) {
			String qty = request.getParameter(selectedItem);
			if (qty != null && qty.isEmpty()) {
				error = true;
				request.setAttribute("orderFeedback", "Please select checkbox & Quantity");
				request.setAttribute("feedbackColor", "red");
				qtyList.clear();
				qtyList = null;
				break;
			}
			qtyList.add(qty);
		}

		if (error == false) {
			updateOrder(request, checkList, session, qtyList);
			request.setAttribute("orderFeedback", "Items are added to your cart!!");
			request.setAttribute("feedbackColor", "green");
			session.setAttribute("order", order);
		}
		return !error;
	}

	private void updateOrder(HttpServletRequest request, String[] checkList, HttpSession session,
			ArrayList<String> qtyList) {
		for (int i = 0; i < checkList.length; i++) {
			Integer itemId = Integer.parseInt(checkList[i]);
			Integer quantity = Integer.parseInt(qtyList.get(i));
			if (order.isEmpty() || !order.containsKey(itemId)) {
				order.put(itemId, quantity);
			} 
			else {
				Integer updatedQty = order.get(itemId) + quantity;
				order.replace(itemId, updatedQty);
			}
		}
		qtyList= null;
	}
	
	
	private boolean isAuthentic(String user, String password){
		String storedPassword = data.getPassword(user);
		if	(storedPassword != null && password.equals(storedPassword))
			return true;
		else
			return false;
	}

}
