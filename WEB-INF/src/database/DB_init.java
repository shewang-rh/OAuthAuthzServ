package database;

import java.sql.*;

import org.apache.amber.oauth2.common.exception.OAuthSystemException;

public class DB_init {
	
	protected Connection con = null;				//�s��object
	protected Statement stat  = null;				//����,�ǤJ��sql������r�� 
	protected ResultSet rs = null; 					//���G��
	protected PreparedStatement pst = null;	//����A�ǤJ��sql���w�x�s���r��A�ݭn�ǤJ�ܼƤ���m
	protected String query;
	
	public void connect() throws ClassNotFoundException, SQLException{
		      //���Udriver 
		      Class.forName("com.mysql.jdbc.Driver"); 
		      //���oconnection
		      con = DriverManager.getConnection("jdbc:mysql://localhost/oauth2", "root","permis_pep_demo");  
	}

	
	public void close() throws SQLException{		//����ϥΧ���Ʈw��,�O�o�n�����Ҧ�Object, �_�h�b����Timeout��,�i��|��Connection poor�����p
														
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
