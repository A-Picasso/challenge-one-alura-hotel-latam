package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.Huesped;

public class HuespedDAO {
	
	private Connection con;

	public HuespedDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar( Huesped huesped ) {
		try {
			final PreparedStatement statement = con.prepareStatement("INSERT INTO huespedes(id_reserva, nombre, apellido, fecha_nacimiento, nacionalidad, telefono)"
												+ " VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			try( statement ){
				registrar( huesped, statement );
			}
		} catch (SQLException e) {
			throw new RuntimeException( e );
		}
	}

	private void registrar(Huesped huesped, PreparedStatement statement) throws SQLException {
		statement.setInt(1, huesped.getIdReserva());
		statement.setString(2, huesped.getNombre());
		statement.setString(3, huesped.getApellido());
		statement.setDate(4, huesped.getFechaNacimiento());
		statement.setString(5, huesped.getNacionalidad());
		statement.setString(6, huesped.getTelefono());
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		try(resultSet){
			while ( resultSet.next() ) {
				huesped.setId(resultSet.getInt(1));
			}
		}
	}

}
