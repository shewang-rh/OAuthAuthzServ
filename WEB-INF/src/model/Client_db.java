package model;

import java.sql.SQLException;

public class Client_db extends DB_init {
	protected String client_id;
	protected String secret;
	protected String client_name;
	protected String client_decription;
	protected String redirect_url;
	
	public Boolean legalClient(String client_id) throws ClassNotFoundException{
		query = "select * from client where client_id = '" + client_id + "'";
		//query = "select * from client";
		
		 try {
			connect();
			stat = con.createStatement();
			 rs = stat.executeQuery(query);
			 rs.next();
			 this.client_id = rs.getString("client_id");
			 this.secret = rs.getString("secret");
			 this.redirect_url = rs.getString("redirect_url");
			 close();
			 return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public Boolean legalClient(String client_id, String secret) throws ClassNotFoundException{
		query = "select * from client where client_id = '" + client_id + "' AND secret = '" + secret + "'";
		//query = "select * from client";
		
		 try {
			connect();
			stat = con.createStatement();
			 rs = stat.executeQuery(query);
			 rs.next();
			 secret = rs.getString("secret");
			 redirect_url = rs.getString("redirect_url");
			 close();
			 return true;
		} catch (SQLException e) {
			return false;
		}
	}
 
	 public Client_db setClientId(String client_id){
		 this.client_id = client_id;
		 return this;
	 }
	 
	 public Client_db setSecret(String secret){
		 this.secret = secret;
		 return this;
	 }
	 
	 public Client_db setClientName(String client_name){
		 this.client_name = client_name;
		 return this;
	 }
	 
	 public Client_db serClientDecription(String client_decription){
		 this.client_decription = client_decription;
		 return this;
	 }
	 
	 public Client_db serRedirectUrl(String redirect_url){
		 this.redirect_url = redirect_url;
		 return this;
	 }
	 
	 public String getClientId(){
		 return client_id;
	 }
	 
	 public String getSecret(){
		 return secret;
	 }
	 
	 public String getClientName(){
		 return client_name;
	 }
	 
	 public String getClientDecription(){
		 return client_decription;
	 }
	 
	 public String getRedirectUrl(){
		 return redirect_url;
	 }

}
