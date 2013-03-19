<!--
 AuthzEndpoint: http://localhost:8080/OAuthAuthzServ/servlet/AuthzServer?response_type=code&client_id=123&redirect_uri=www.example.com&state=test&scope=test
 TokenEndpoint: http://localhost:8080/OAuthAuthzServ/servlet/TokenServer?client_id=test_id&grant_type=authorization_code&code=known_authz_code
 -->

<html>
<head>
<title>OAuth Authorization Server Implement</title>
</head>
<body>
<h1 align="center">OAuth Authorization Server Implement</h1>
<ul>
  <li>
    <a href="pages/myhelloworld_jstl.jsp">pages/myhelloworld_jstl.jsp</a> - JSTL Hello World Example
  </li>
  <li>
       <a href="servlet/TokenServer">servlet/TokenServer</a> - OAuth Token Server Example
  </li>
  
  <li>
    <a href="servlet/AuthzServer">servlet/AuthzServer</a> - OAuth Authorization Server Example
  </li>

  <a href="servlet/HelloWorldExample">
</ul>
</body>
</html>