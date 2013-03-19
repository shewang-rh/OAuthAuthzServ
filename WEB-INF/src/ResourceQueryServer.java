/**
 *       Copyright 2010 Newcastle University
 *
 *          http://research.ncl.ac.uk/smart/
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.amber.oauth2.common.OAuth;
import org.apache.amber.oauth2.common.exception.OAuthProblemException;
import org.apache.amber.oauth2.common.exception.OAuthSystemException;
import org.apache.amber.oauth2.common.message.OAuthResponse;
import org.apache.amber.oauth2.common.message.types.ParameterStyle;
import org.apache.amber.oauth2.common.utils.OAuthUtils;
import org.apache.amber.oauth2.integration.Common;
import org.apache.amber.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.amber.oauth2.rs.response.OAuthRSResponse;
import org.apache.amber.oauth2.common.error.OAuthError;

import database.Token_db;


/**
 *
 *
 *
 */
public class ResourceQueryServer extends HttpServlet{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println(request.getQueryString());

        try {

            // Make the OAuth Request out of this request
            OAuthAccessResourceRequest oauthRequest = null;
            
			try {
				oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
			} catch (OAuthProblemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // Get the access token
            String accessToken = oauthRequest.getAccessToken();

            // Validate the access token
            if (!new Token_db().legalToken(accessToken)) {

                // Return the OAuth error message
                OAuthResponse oauthResponse = OAuthRSResponse
                    .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                    .setRealm(Common.RESOURCE_SERVER_NAME)
                    .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                    .buildHeaderMessage();

                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                 response.setHeader(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
                 PrintWriter pw = response.getWriter();
                 pw.print(oauthResponse.getBody());
                 pw.flush();
                 pw.close();

            }
            // Return the resource
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter pw = response.getWriter();
            pw.print("Access Resource Success");
            pw.flush();
            pw.close();

        } catch (OAuthSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            String errorCode = e.getMessage();

          //return Response.status(Response.Status.UNAUTHORIZED).build();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter pw = response.getWriter();
            pw.print(errorCode);
            pw.flush();
            pw.close();
		}catch (ClassNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
            String errorCode = e.getMessage();

          //return Response.status(Response.Status.UNAUTHORIZED).build();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter pw = response.getWriter();
            pw.print(errorCode);
            pw.flush();
            pw.close();
		}
    }
}