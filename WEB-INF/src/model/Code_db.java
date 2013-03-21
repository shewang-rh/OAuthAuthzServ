package model;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Code_db extends DB_init {
	protected String code;
	protected String client_id;
	protected String scope;
	protected long expire_in;
	protected boolean callback;
	protected boolean valid;
	
	public Code_db setCode(String code){
		this.code = code;
		return this;
	}
	
	public Code_db setClientId(String client_id){
		this.client_id = client_id;
		return this;
	}
	
	public Code_db setScope(String scope){
		this.scope = scope;
		return this;
	}
	
	public Code_db setExpireIn(long expire_in){
		this.expire_in = expire_in;
		return this;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getClientId(){
		return client_id;
	}
	
	public String getScope(){
		return scope;
	}
	
	public long getExpireIn(){
		return expire_in;
	}
	
	public boolean getCallBack(){
		return callback;
	}
	
	public boolean getValid(){
		return valid;
	}
	
	public Boolean legalCode(String code) throws ClassNotFoundException{
		query = "select * from authorization_code where code = '" + code + "'";
		Timestamp valid_time;
		
		try {
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			rs.next();
			this.code = rs.getString("code");
			this.client_id = rs.getString("client_id");
			this.scope = rs.getString("scope");
			this.expire_in = rs.getLong("expire_in");
			this.callback = rs.getBoolean("callback");
			valid_time = new Timestamp(rs.getTimestamp("create_time").getTime() + expire_in*1000);
			close();
			
			return (valid = valid_time.after(new Date(System.currentTimeMillis())))?true:false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean legalClient(String client_id, String secret) throws ClassNotFoundException{
		if(new Client_db().legalClient(client_id, secret))
			return true;
		else
			return false;
	}
	
	/*
	public OAuthCodeData searchCode(String code) throws ClassNotFoundException{
		query = "select * from authorization_code where code = '" + code + "'";
		
		try {
			connect();
			stat = con.createStatement();
			rs = stat.executeQuery(query);
			client_id = rs.getString("client_id");
			scope = rs.getString("scope");
			expire_in = rs.getLong("expire_in");
			callback = rs.getBoolean("callbak");
			close();
			return this;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this;
		}
	}
	*/
	
	public Boolean storeCodeData() throws ClassNotFoundException{
		query = "INSERT INTO authorization_code SET code=?, client_id=?, scope=?, expire_in=?, callback=?";
		
		try {
			connect();
			pst = con.prepareStatement(query);
			pst.setString(1, code);
			pst.setString(2, client_id);
			pst.setString(3, scope);
			pst.setLong(4, expire_in);
			pst.setBoolean(5, false);
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
