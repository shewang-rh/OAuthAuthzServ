package endpoint.token;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.http.*;
import javax.servlet.ServletException;

import model.Code_db;
import model.Token_db;

import org.apache.amber.oauth2.as.issuer.*;
import org.apache.amber.oauth2.as.request.*;
import org.apache.amber.oauth2.as.response.OAuthASResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.error.OAuthError;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.amber.oauth2.common.message.types.GrantType;


public class TokenServer extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		OAuthTokenRequest oauthRequest = null;
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		Code_db oauthCodetData = new Code_db();
		String accessToken = null;
		
		Enumeration     ee     =     (Enumeration)     request.getParameterNames();   
	    while(ee.hasMoreElements())     {   
	    String     parName=(String)ee.nextElement();   
	    
	              System.out.println(parName + ": " + request.getParameter(parName));   
	    }   
		
		try{
			oauthRequest = new OAuthTokenRequest(request);
			
			//Check GRANT_TYPE
			if(oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())){	//authorization code
				if (!oauthCodetData.legalCode(oauthRequest.getCode())){
                    OAuthResponse resp = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_GRANT)
                        .setErrorDescription("Invalid authorization code")
                        .buildJSONMessage();
                    
                    outputStream(response, resp);
                    return;
				} else if(!oauthCodetData.legalClient(oauthRequest.getClientId(), oauthRequest.getClientSecret())){
					OAuthResponse resp = OAuthASResponse
	                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
	                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
	                        .setErrorDescription("invalid client")
	                        .buildJSONMessage();
	                    
	                    outputStream(response, resp);
	                    return;
				}
			}else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.REFRESH_TOKEN.toString())) {
				OAuthResponse resp = OAuthASResponse
	                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
	                    .setError(OAuthError.TokenResponse.INVALID_GRANT)
	                    .setErrorDescription("invalid username or password")
	                    .buildJSONMessage();
				
				outputStream(response, resp);
				return;
			}
			accessToken = oauthIssuerImpl.accessToken();
			
			OAuthResponse resp = OAuthASResponse
	                .tokenResponse(HttpServletResponse.SC_OK)
	                .setAccessToken(accessToken)
	                .setExpiresIn("3600")
	                .buildJSONMessage();
			
			Token_db oauthTokenData = new Token_db()
			.setToken(accessToken)
			.setClientId(oauthRequest.getClientId())
			.setScope(oauthCodetData.getScope())
			.setExpireIn(3600);
			oauthTokenData.storeToken();
			
			outputStream(response, resp);
			return;
		}catch(OAuthProblemException e){
			e.printStackTrace();
			return;
		} catch (OAuthSystemException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

        try {
			OAuthResponse resp = OAuthASResponse
			    .tokenResponse(HttpServletResponse.SC_OK)
			    .setAccessToken(oauthIssuerImpl.accessToken())
			    .setExpiresIn("3600")
			    .buildJSONMessage();
			
			htmlTag(response, resp.getBody());
		} catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			htmlTag(response, "Error");
		}
        return;
    }
	*/
	
	protected void outputStream(HttpServletResponse response, OAuthResponse resp) throws IOException{
		response.setStatus(resp.getResponseStatus());
		PrintWriter pw = response.getWriter();
		pw.print(resp.getBody());
		pw.flush();
		pw.close();
	}
	
}
