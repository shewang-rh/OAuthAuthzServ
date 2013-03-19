package database;

import java.sql.*;

import org.apache.amber.oauth2.common.exception.OAuthSystemException;

public class DB_init {
	
	protected Connection con = null;				//連接object
	protected Statement stat  = null;				//執行,傳入之sql為完整字串 
	protected ResultSet rs = null; 					//結果集
	protected PreparedStatement pst = null;	//執行，傳入之sql為預儲存之字串，需要傳入變數之位置
	protected String query;
	
	public void connect() throws ClassNotFoundException, SQLException{
		      //註冊driver 
		      Class.forName("com.mysql.jdbc.Driver"); 
		      //取得connection
		      con = DriverManager.getConnection("jdbc:mysql://localhost/oauth2", "root","permis_pep_demo");  
	}

	
	public void close() throws SQLException{		//完整使用完資料庫後,記得要關閉所有Object, 否則在等待Timeout時,可能會有Connection poor的狀況
														
			if(rs!=null){
					rs.close(); 
					rs = null;
	    	}
	    	if(stat!=null){
	    			stat.close(); 
	    			stat = null; 
	    	}
	      if(pst!=null){
	    	  		pst.close(); 
	    	  		pst = null; 
	      }
	}//close
	
	
}
