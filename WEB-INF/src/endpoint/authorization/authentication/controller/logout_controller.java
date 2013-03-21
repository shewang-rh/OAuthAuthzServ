package endpoint.authorization.authentication.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import endpoint.authorization.authentication.model.User_db;

public class logout_controller extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		
		User_db user = (User_db) session.getAttribute("user");
		if(user != null ){
			user = null ;
			session.removeAttribute("user");
		}
		response.sendRedirect("login");
	}
}
