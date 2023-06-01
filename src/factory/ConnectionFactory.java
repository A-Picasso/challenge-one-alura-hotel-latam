package factory;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {
	
	private DataSource datasource;
	
	private String DB = "hotel_alura";
	private String URL = "jdbc:mysql://localhost/" + DB + "?useTimeZone=true&serverTimeZone=UTC";
	private String USER = "adminHotel";
	private String PASSWORD = "DT83r4";

	public ConnectionFactory() {
		ComboPooledDataSource pooleDataSource = new ComboPooledDataSource();
		pooleDataSource.setJdbcUrl(URL);
		pooleDataSource.setUser(USER);
		pooleDataSource.setPassword(PASSWORD);
		pooleDataSource.setMaxPoolSize(10);
		this.datasource = pooleDataSource;
	}
	
	
	public Connection recuperarConexion() {
		try {
			return this.datasource.getConnection();
		} catch (SQLException e) {
			System.out.print(e.getMessage());
			throw new RuntimeException(e);
		}
	}

}
