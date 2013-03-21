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
import endpoint.authorization.authentication.model.Client_db;
import endpoint.authorization.authentication.model.User_db;

public class select_client_controller extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Client_db oauthClientData = new Client_db();
		Map<String, Object> model = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		User_db user = (User_db) session.getAttribute("user");
		
		session.setAttribute("last_page", request.getRequestURI());
		
		if(user == null){
			response.sendRedirect("login");
			return;
		}
		
		try {
			model.put("client_list", oauthClientData.getClientList());
			request.setAttribute("model", model);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/select_client.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
