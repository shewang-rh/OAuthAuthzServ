import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.amber.oauth2.as.request.*;
import org.apache.amber.oauth2.as.response.*;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.*;
import org.apache.amber.oauth2.common.message.*;
import org.apache.amber.oauth2.common.message.OAuthResponse.OAuthResponseBuilder;
import org.apache.amber.oauth2.common.message.types.ResponseType;
import org.apache.amber.oauth2.common.utils.OAuthUtils;


public class AuthzServer extends HttpServlet  {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		
		OAuthAuthzRequest oauthRequest = null;
		OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		
		try {
			oauthRequest = new OAuthAuthzRequest(request);
			
			//bind response according to the response_type
			String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			
			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse
					.authorizationResponse(request, HttpServletResponse.SC_FOUND);
			
			if(responseType.equals(ResponseType.CODE.toString())){
				builder.setCode(oauthIssuerImpl.authorizationCode());
				System.out.println("test");
			}
			if (responseType.equals(ResponseType.TOKEN.toString())) {
				builder.setAccessToken(oauthIssuerImpl.accessToken());
				builder.setExpiresIn(3600l);
			}
			
			String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
			
			OAuthResponse resp = builder.location(redirectURI).buildQueryMessage();
			
			//response.sendRedirect(resp.getLocationUri());
			
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head>");
	        out.println("<title>Hello World</title>");
	        out.println("</head>");
	        out.println("<body>");
	        out.println("<h1 align=\"center\">Hello World (Servlet Example)</h1>");
	        out.println("<p>" + resp.getLocationUri()  + "</p>");
	        out.println("</body>");
	        out.println("</html>");

	         //if something goes wrong
	    } catch(OAuthProblemException e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    } catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
