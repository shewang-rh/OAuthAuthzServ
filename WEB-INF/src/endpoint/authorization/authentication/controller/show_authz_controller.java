package endpoint.authorization.authentication.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import endpoint.authorization.authentication.model.User_authz_db;
import endpoint.authorization.authentication.model.User_db;

public class show_authz_controller extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//Init Pipe Object
		Map<String, Object> model = new HashMap<String, Object>();		
		//Init Database List
		List<User_authz_db> user_authz_list;
		//Get Session Value  
		HttpSession session = request.getSession();
		User_db user = (User_db) session.getAttribute("user");

		//如果user為登入，導回登入畫面
		if(user == null){
			session.setAttribute("last_page", request.getRequestURI());
			response.sendRedirect("login");
			return;
		}
		
		try {
			//取得DB中user的授權資料
			user_authz_list = new User_authz_db().selectAll(user.getUser_id());
			model.put("authz_list", user_authz_list);
			request.setAttribute("model",model);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/show_authz.jsp");
			dispatcher.forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
