package database;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Token_db extends DB_init {
	protected String token;
	protected String token_type;
	protected String client_id;
	protected long expire_in;
	protected String scope;
	protected boolean valid;
	
	public Token_db setToken(String token){
		this.token = token;
		return this;
	}
	
	public Token_db setTokenType(String token_type){
		this.token_type = token_type;
		return this;
	}
	
	public Token_db setClientId(String client_id){
		this.client_id = client_id;
		return this;
	}

	public Token_db setExpireIn(long expire_in){
		this.expire_in = expire_in;
		return this;
	}
	
	public Token_db setScope(String scope){
		this.scope = scope;
		return this;
	}
	
	public String getToken(){
		return token;
	}
	
	public String getTokenType(){
		return token_type;
	}
	
	public String getClientId(){
		return client_id;
	}
	
	public Long getExpireIn(){
		return expire_in;
	}
	
	public String getScope(){
		return scope;
	}
	
	public boolean getValid(){
		return valid;
	}
	
	public Boolean legalToken(String token) throws ClassNotFoundException{
		query = "select * from access_token where token = '" + token + "'";
		Timestamp valid_time;
		
		try {
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			rs.next();
			this.token = rs.getString("token");
			this.token_type = rs.getString("token_type");
			this.client_id = rs.getString("client_id");
			this.expire_in = rs.getLong("expire_in");
			this.scope = rs.getString("scope");
			valid_time = new Timestamp(rs.getTimestamp("create_time").getTime() + expire_in*1000);
			close();
			
			return (valid = valid_time.after(new Date(System.currentTimeMillis())))?true:false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean storeToken() throws ClassNotFoundException{
		query = "INSERT INTO access_token SET token=?, token_type=?, client_id=?, expire_in=?, scope=?";
		
		try {
			connect();
			pst = con.prepareStatement(query);
			pst.setString(1, token);
			pst.setString(2, token_type);
			pst.setString(3,client_id);
			pst.setLong(4, expire_in);
			pst.setString(5, scope);
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
