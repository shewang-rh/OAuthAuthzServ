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
import endpoint.authorization.authentication.model.User_authz_db;
import endpoint.authorization.authentication.model.User_db;

public class select_role_controller extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
	//Init Pipe Object
	Map<String, Object> model = new HashMap<String, Object>();
	//Init Database Value 
	Client_db oauthClientData = new Client_db();
	User_authz_db user_authz = new User_authz_db();
	//Get Session Value  
	HttpSession session = request.getSession();
	User_db user = (User_db) session.getAttribute("user");
	//Fetch Get Parameter
	String client = request.getParameter("client");
	
	if(user == null){
		response.sendRedirect("login");
		return;
	}
	
	try {
		if(oauthClientData.legalClient(client)){
			model.put("role_list", oauthClientData.getClient_role());	
			request.setAttribute("model", model);
			user_authz.setClient_id(client);
			session.setAttribute("user_authz",user_authz);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/select_role.jsp");
			dispatcher.forward(request, response);
		}
		else{		//Print client ERROR message
			System.out.println("client ERROR message");
		}
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
