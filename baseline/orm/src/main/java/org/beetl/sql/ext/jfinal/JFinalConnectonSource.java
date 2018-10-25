package org.beetl.sql.ext.jfinal;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.beetl.sql.core.BeetlSQLException;
import org.beetl.sql.core.DefaultConnectionSource;

public class JFinalConnectonSource extends DefaultConnectionSource {
	public JFinalConnectonSource(){
		
	}
	public JFinalConnectonSource(DataSource master,DataSource[] slaves){
		super(master,slaves);
		
	}
	
	protected Connection doGetConnectoin(DataSource ds){
		try {
			if(Trans.inTrans()){
				return Trans.getCurrentThreadConnection(ds);
			}else{
				//非事物环境,用户自己管理了
				return ds.getConnection();
			}
			
		} catch (SQLException e) {
			throw new BeetlSQLException(BeetlSQLException.CANNOT_GET_CONNECTION,e);
		}
	}
	
	public boolean isTransaction() {
		return Trans.inTrans();
	}

}
