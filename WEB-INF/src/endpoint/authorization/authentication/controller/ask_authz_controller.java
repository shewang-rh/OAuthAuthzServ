package endpoint.authorization.authentication.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import endpoint.authorization.authentication.model.User_db;


public class ask_authz_controller extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Map<String, Object> model = new HashMap<String, Object>();
		HttpSession session = request.getSession();
				
		User_db user = (User_db) session.getAttribute("user");
		if(user == null){
			session.setAttribute("last_page", request.getRequestURI());
			setParameter(request);
			response.sendRedirect("login");
			return;
		}
		else{
			String response_type = (String) session.getAttribute("response_type");
			String redirect_uri = (String) session.getAttribute("redirect_uri");
			String client_id = (String) session.getAttribute("client_id");
			String client_role = (String) session.getAttribute("client_role");
			
			model.put("response_type", response_type);
			model.put("redirect_uri", redirect_uri);
			model.put("client_id", client_id);
			model.put("user", user.getUser_id());
			model.put("client_role", client_role);
			
			request.setAttribute("model", model);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/ask_authz.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	//取出所有參數並放到session中
		public void setParameter(HttpServletRequest request){
			HttpSession session = request.getSession();		
			Enumeration<String> parameter_list = (Enumeration<String>) request.getParameterNames();
			
		    while(parameter_list.hasMoreElements())     {   
		    String parName = (String) parameter_list.nextElement();   
		              session.setAttribute(parName, request.getParameter(parName));   
		    }   
		}
}
