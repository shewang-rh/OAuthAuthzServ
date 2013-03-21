package endpoint.authorization.authentication.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Client_db extends DB_init {
	public String client_id;
	protected String secret;
	protected String client_name;
	protected String client_description;
	protected String redirect_url;
	protected String client_role; 

	List<Client_db> client_list = new ArrayList<Client_db>();
	List<String> role_list = new ArrayList<String>();
	
	public Boolean legalClient(String client_id) throws ClassNotFoundException{
		query = "select * from client where client_id = '" + client_id + "'";
		
		 try {
			connect();
			stat = con.createStatement();
			 rs = stat.executeQuery(query);
			 rs.next();
			 this.client_id = rs.getString("client_id");
			 this.secret = rs.getString("secret");
			 this.redirect_url = rs.getString("redirect_url");
			 this.client_role = rs.getString("client_role");
			 close();
			 return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public Boolean legalClient(String client_id, String secret) throws ClassNotFoundException{
		query = "select * from client where client_id = '" + client_id + "' AND secret = '" + secret + "'";
		
		 try {
			connect();
			stat = con.createStatement();
			 rs = stat.executeQuery(query);
			 rs.next();
			 this.client_id = client_id;
			 this.secret = secret;
			 this.redirect_url = rs.getString("redirect_url");
			 this.client_role = rs.getString("client_role");
			 close();
			 return true;
		} catch (SQLException e) {
			return false;
		}
	}
 
	public List<Client_db> getClientList() throws ClassNotFoundException{
		 //Set<String> clientList = new HashSet<String>();
		 query = "select * from client";
		 
		 try{
			 connect();
			 stat = con.createStatement();
			 rs = stat.executeQuery(query);
			 while(rs.next()){
				 Client_db oauthClientData = new Client_db();				 
				 oauthClientData.client_name = rs.getString("client_name");
				 oauthClientData.client_id = rs.getString("client_id");
				 oauthClientData.client_description = rs.getString("client_description");
				 client_list.add(oauthClientData);
			 }
			 close();
			 return client_list;
		 }catch(SQLException e){
			 e.printStackTrace();
			 return client_list;
		 }
	}
	
	 public Client_db setClient_id(String client_id){
		 this.client_id = client_id;
		 return this;
	 }
	 
	 public Client_db setSecret(String secret){
		 this.secret = secret;
		 return this;
	 }
	 
	 public Client_db setClient_name(String client_name){
		 this.client_name = client_name;
		 return this;
	 }
	 
	 public Client_db serClient_description(String client_description){
		 this.client_description = client_description;
		 return this;
	 }
	 
	 public Client_db serRedirect_url(String redirect_url){
		 this.redirect_url = redirect_url;
		 return this;
	 }
	 
	 public String getClient_id(){
		 return client_id;
	 }
	 
	 public String getSecret(){
		 return secret;
	 }
	 
	 public String getClient_name(){
		 return client_name;
	 }
	 
	 public String getClient_description(){
		 return client_description;
	 }
	 
	 public String getRedirect_url(){
		 return redirect_url;
	 }
	 
	 public List<String> getClient_role(){
		 StringTokenizer tokenizer = new StringTokenizer(this.client_role, " ");
		 
		 while(tokenizer.hasMoreElements()){
			 role_list.add(tokenizer.nextToken());
		 }
		 return role_list;
	 }

}