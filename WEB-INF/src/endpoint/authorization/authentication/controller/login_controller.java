package endpoint.authorization.authentication.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import endpoint.authorization.authentication.model.User_db;

public class login_controller extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		HttpSession session = request.getSession();
		Map<String, Object> model = new HashMap<String, Object>();
		String login_uri = request.getParameter("login");
		

		if(login_uri != ""){
			model.put("login_uri", login_uri);
			request.setAttribute("model", model);
		}
		User_db user = (User_db) session.getAttribute("user");
		if(user == null){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		else{			
			response.sendRedirect("index");
		}
		
	}
}