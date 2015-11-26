package com.shohag.shopping.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shohag.shopping.service.BusinessService;

@SuppressWarnings("serial")
public class ControllerServlet extends HttpServlet {
	private String nextPage = "/login.jsp";
	private BusinessService service = new BusinessService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		String page = request.getParameter("pageName");
		
		switch (page){
			case "login":
				nextPage = service.doLogin(request, response, session);
				break;
				
			case "cart":
				String action = request.getParameter("cartAction");
				if (session !=null && action != null && action.equals("Check out")) {
					nextPage = service.doCheckOut(request, response, session);
				} 
				else if (session !=null && action != null && action.equals("Add to Cart")) {
					service.addToCart(request, response, session);
					nextPage = "/cart.jsp";
				}
				break;
				
			case "summary":
				String summaryAction = request.getParameter("summaryAction");
				if (session != null && summaryAction != null && summaryAction.equals("Check out")) {
					service.finalCheckOut(request, response, session);
					nextPage = "/thankyou.jsp";
				} 
				else if (session !=null && summaryAction != null && summaryAction.equals("Back to Cart")) {
					nextPage = "/cart.jsp";
				}
				break;
				
			case "logout":
				service.doLogout(request, response, session);
				nextPage = "/login.jsp";
				break;
				
			case "menue":
				nextPage = "/help.jsp";
				break;	
		}		
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}
	
}
