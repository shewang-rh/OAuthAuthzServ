package endpoint.authorization.authentication.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import endpoint.authorization.authentication.model.User_authz_db;
import endpoint.authorization.authentication.model.User_db;

public class pre_authz_controller extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Get Session Value  
		HttpSession session = request.getSession();
		User_db user = (User_db) session.getAttribute("user");
		User_authz_db user_authz = (User_authz_db) session.getAttribute("user_authz");
		
		if(user == null){
			response.sendRedirect("login");
			return;
		}
		
		try {
			user_authz.setUser_id(user.getUser_id());
			user_authz.setClient_role(request.getParameter("client_role"));
			user_authz.store();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.removeAttribute("user_authz");
		response.sendRedirect("show_authz");
	}

}
