package factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

	public static void main(String[] args) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection con = factory.recuperarConexion();
		
		System.out.println("Conexion Exitosa");
		
		con.close();
		
		System.out.println("Conexion cerrada");

	}

}
