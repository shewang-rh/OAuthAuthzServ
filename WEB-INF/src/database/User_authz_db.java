package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class User_authz_db extends DB_init{
	protected String user_id;
	protected String client_id;
	protected String client_name;
	protected String client_description;
	protected String role;
	
	public void setUser_id(String user_id){
		this.user_id = user_id;
	}
	
	public void setClient_id(String client_id){
		this.client_id = client_id;
	}
	
	public void setRole(String role){
		this.role = role;
	}
	
	public void setClient_name(String client_name){
		this.client_name = client_name;
	}
	
	public void setClient_description(String client_description){
		this.client_description = client_description;
	}
	
	public String getUser_id(){
		return this.user_id;
	}
	
	public String getClient_id(){
		return this.client_id;
	}
	
	public String getRole(){
		return this.role;
	}
	
	public String getClient_name(){
		return this.client_name;
	}
	
	public String getClient_description(){
		return this.client_description;
	}
	
	public boolean is_authz_role(String client_id, String role) throws ClassNotFoundException{
		query = "SELECT role FROM user_aurhz WHERE user_id = '" + this.user_id + "' AND client_id = '" + this.client_id + "'";

		try{
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			rs.next();
			StringTokenizer tokenizer = new StringTokenizer(rs.getString("role"), " ");
			close();
			
			while(tokenizer.hasMoreElements())
				if(tokenizer.nextToken().equals(role))
					return true;
			
			return false;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<User_authz_db> selectAll(String user_id) throws ClassNotFoundException{
		List<User_authz_db> user_authz_list = new ArrayList<User_authz_db>();
		query = "SELECT * FROM user_aurhz , client WHERE user_id = '" + user_id + "' AND user_aurhz.client_id = client.client_id" ;
		
		try{
			connect();
			stat = con.createStatement();
 			rs = stat.executeQuery(query);
			while(rs.next()){
				User_authz_db user_authz = new User_authz_db();
				user_authz.client_id = rs.getString("client_id");
				user_authz.client_name = rs.getString("client_name");
				user_authz.client_description = rs.getString("client_description");
				user_authz.role = rs.getString("role");
				user_authz_list.add(user_authz);
			}
			close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return user_authz_list;
	}
	
	public void store() throws ClassNotFoundException{
		query = "SELECT * FROM user_aurhz WHERE user_id = '" + this.user_id + "' AND client_id = '" + this.client_id + "'";
		
		try{
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			rs.next();
			this.role = this.role + " " +  rs.getString("role");
			close();
			update();
		}catch(SQLException e){
			insert();
			//e.printStackTrace();
		}
	}
	
	public void insert() throws ClassNotFoundException{
		query = "INSERT INTO user_aurhz SET user_id=?, client_id=?, role=?";
		
		try {
			connect();
			pst = con.prepareStatement(query);
			pst.setString(1, user_id);
			pst.setString(2, client_id);
			pst.setString(3, role);
			pst.execute();
			close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean update() throws ClassNotFoundException{
		query = "UPDATE user_aurhz SET role=? where user_id=? AND client_id=?";
		
		try {
			connect();
			pst = con.prepareStatement(query);
			pst.setString(1, role);
			pst.setString(2, user_id);
			pst.setString(3, client_id);
			pst.execute();
			close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
