package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
	
	private Connection con;

	public UsuarioDAO(Connection con) {
		this.con = con;
	}
	
	public boolean consultaUsuario( String nombre, String contrasena ) {
		try {
			final PreparedStatement statement = con.prepareStatement("SELECT * FROM usuarios WHERE nombre=? AND contrasena=?");
			try (statement){
				statement.setString(1, nombre);
				statement.setString(2, contrasena);
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				try (resultSet){
					while(resultSet.next()) {
						if ( resultSet.getString("nombre").equals(nombre) && resultSet.getString("contrasena").equals(contrasena)  ) {
							return true;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return false;
	}

}
