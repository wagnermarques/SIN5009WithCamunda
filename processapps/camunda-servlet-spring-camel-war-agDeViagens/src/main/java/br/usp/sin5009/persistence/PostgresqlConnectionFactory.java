package br.usp.sin5009.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import br.usp.sin5009.ServiceTasks.VerificaSeOClienteEVip;
import br.usp.sin5009.security.Credentials;

public class PostgresqlConnectionFactory {

	static Logger LOGGER = Logger.getLogger(PostgresqlConnectionFactory.class.getName());
	private static Connection conn;
	private static String url = "jdbc:postgresql://45.79.225.175:5432/sin5009";	
	
	public static Connection getConnectionInstance() throws SQLException, ClassNotFoundException {
		if(conn == null) {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, Credentials.usuarioDoPostgresql, Credentials.senhaDoPostgresql);
		}		
		
		LOGGER.info("\n\n\n\n %%%%%%%%%% Returning Connection :" + conn);
		return conn;		
	}
	
}
