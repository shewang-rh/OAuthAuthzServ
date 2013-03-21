package endpoint.authorization.authentication.model;

import java.sql.SQLException;

public class User_db extends DB_init{
	protected String user_id;
	protected String passwd;
	protected String name;
	protected String email;
	
	public boolean check_user(String id, String passwd) throws ClassNotFoundException{
		String query = "SELECT * from user WHERE user_id = '" + id  + "' and passwd = '" + passwd + "'";
		
		try{
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			rs.next();
			this.user_id = rs.getString("user_id");
			this.passwd = rs.getString("passwd");
			this.name = rs.getString("name");
			this.email = rs.getString("email");
			close();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void seUser_iId(String user_id){
		this.user_id = user_id;
	}
	
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getUser_id(){
		return this.user_id;
	}
	
	public String getPasswd(){
		return this.passwd;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getEmail(){
		return this.email;
	}
}
