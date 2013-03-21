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
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.types.ResponseType;
import endpoint.authorization.authentication.model.User_authz_db;
import endpoint.authorization.authentication.model.User_db;



public class authz_redirect_controller extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Map<String, Object> model = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		
		User_db user = (User_db) session.getAttribute("user");
		if(user == null){
			response.sendRedirect("login");
			return;
		}
		else{
			try {
				String redirect_uri = request.getParameter("redirect_uri");
				String client_id = request.getParameter("client_id");
				String client_role = request.getParameter("client_role");
				String scope = request.getParameter("scope");
				
				User_authz_db user_authz = new User_authz_db();
				user_authz.setClient_id(client_id);
				user_authz.setUser_id(user.getUser_id());
				user_authz.setClient_role(client_role);
				user_authz.store();
				
				OAuthClientRequest oauthRequest = OAuthClientRequest
		                .authorizationLocation("https://140.113.216.109:8443/OAuthAuthzServ/servlet/AuthzServer")
		                .setClientId(client_id)
		                .setRedirectURI(redirect_uri)
		                .setClientRole(client_role)
		                .setResponseType(ResponseType.CODE.toString())
		                .setScope(scope)
		                .buildQueryMessage();
				
				response.sendRedirect(oauthRequest.getLocationUri()+ "&user=" + user.getUser_id());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
