package br.com.fiap.fintech.singleton;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

	private static ConnectionManager conecctionManager;
	
	private ConnectionManager () {	
	}
	
	public static ConnectionManager getInstance() {
		if (conecctionManager == null) {
			conecctionManager = new ConnectionManager();
		}
		return conecctionManager;
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			connection = DriverManager.getConnection("{ORACLE_DB_URL}", "{ORACLE_DB_USERNAME}",
					"{ORACLE_DB_PASSWORD}");
		} catch (Exception e){
			e.printStackTrace();
		}
		return connection;
		}
}
