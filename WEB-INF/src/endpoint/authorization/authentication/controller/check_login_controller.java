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


public class check_login_controller extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
		Map<String, Object> model = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		HttpSession session = request.getSession();
		String login_uri = (String) session.getAttribute("last_page");
		
		if(id == "" || passwd == ""){
			model.put("err_message", "Id or Passwd empty");
			request.setAttribute("model", model);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		try {
			User_db user = new User_db();
			if(user.check_user(id, passwd)){
				session.setAttribute("user", user);
				
				if(login_uri != null){
					response.sendRedirect(login_uri);
				}else
					response.sendRedirect("index");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
