import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.amber.oauth2.as.issuer.MD5Generator;
import org.apache.amber.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.amber.oauth2.as.request.*;
import org.apache.amber.oauth2.as.response.*;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.*;
import org.apache.amber.oauth2.common.message.*;
import org.apache.amber.oauth2.common.message.types.ResponseType;
import org.apache.amber.oauth2.integration.Common;

import database.Client_db;
import database.Code_db;


public class AuthzServer extends HttpServlet  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
		
		OAuthAuthzRequest oauthRequest = null;
		OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		String authorezationCode = null;
		
		try {
			oauthRequest = new OAuthAuthzRequest(request);
			
			//bind response according to the response_type
			String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			
			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse
					.authorizationResponse(request, HttpServletResponse.SC_FOUND);
			
			//client_id authentication
			if(!new Client_db().legalClient(oauthRequest.getClientId())){
				OAuthResponse resp = OAuthASResponse
	                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
	                    .setError(OAuthError.TokenResponse.INVALID_CLIENT)
	                    .setErrorDescription("Invalid client id")
	                    .buildJSONMessage();
				
				outputStream(response, resp);
				return;
			}
						
			//user authentication & authorization
			if(request.getParameter("user") == null){
				response.sendRedirect("/OAuthAuthenServ/oauth?redirect_uri=" + oauthRequest.getRedirectURI() + "&client_id=" + oauthRequest.getClientId() + "response_type=" + oauthRequest.getResponseType());
				return;
			}
			
			
			if(responseType.equals(ResponseType.CODE.toString())){
				authorezationCode = oauthIssuerImpl.authorizationCode();
				builder.setCode(authorezationCode);
			}
			if (responseType.equals(ResponseType.TOKEN.toString())) {
				builder.setAccessToken(oauthIssuerImpl.accessToken());
				builder.setExpiresIn(3600l);
			}
			
			String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
			//System.out.println(builder.location(redirectURI));
			
			if(!redirectURI.contains("http"))
            	redirectURI = "http://" +  redirectURI;

			OAuthResponse resp = builder.location(redirectURI).buildQueryMessage();
			//URI url = new URI(resp.getLocationUri());
			
			Code_db oauthCodeData = new Code_db()
			.setCode(authorezationCode)
			.setClientId(oauthRequest.getClientId())
			.setScope(oauthRequest.getParam(OAuth.OAUTH_SCOPE))
			.setExpireIn(3601);
			oauthCodeData.storeCodeData();
			
			response.setStatus(resp.getResponseStatus());
			response.sendRedirect(resp.getLocationUri());
	    } catch(OAuthProblemException e) {			//if something goes wrong
	    	e.printStackTrace();
	    } catch (OAuthSystemException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	protected void outputStream(HttpServletResponse response, OAuthResponse resp) throws IOException{
		response.setStatus(resp.getResponseStatus());
		PrintWriter pw = response.getWriter();
		pw.print(resp.getBody());
		pw.flush();
		pw.close();
	}

}
