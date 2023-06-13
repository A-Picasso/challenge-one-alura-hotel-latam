package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
			throw new RuntimeException("Ocurrió una excepción en el método guardar de la clase HuespedDAO: " + e.getMessage(), e);
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
	
	
	public List<Huesped> listar(){
		List<Huesped> listarResultado = new ArrayList<>();
		try {
			final PreparedStatement statement = con.prepareStatement("SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes");
			try( statement ){
				statement.execute();
				final ResultSet rst = statement.getResultSet();
				try( rst ){
					while( rst.next() ){
						Huesped fila = new Huesped(rst.getInt("id"), rst.getString("nombre"), rst.getString("apellido"), rst.getDate("fecha_nacimiento"), 
									rst.getString("nacionalidad"), rst.getString("telefono"), rst.getInt("id_reserva"));
						listarResultado.add(fila);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ocurrió una excepción en el método listar de la clase HuespedDAO: " + e.getMessage(), e);
		}
		return listarResultado;
	}
	
	
	public List<Huesped> listarPorApellido( String apellido ){
		List<Huesped> resultado = new ArrayList<>();
		try {
			final PreparedStatement statement = con.prepareStatement("SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE apellido=?");
			try( statement ){
				statement.setString(1, apellido);
				statement.execute();
				final ResultSet rst = statement.getResultSet();
				try( rst ){
					while( rst.next() ) {
						Huesped fila = new Huesped(rst.getInt("id"), rst.getString("nombre"), rst.getString("apellido"), rst.getDate("fecha_nacimiento"), 
									rst.getString("nacionalidad"), rst.getString("telefono"), rst.getInt("id_reserva"));
						resultado.add(fila);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ocurrió una excepción en el método listarPorApellido de la clase HuespedDAO: " + e.getMessage(), e);
		}
		return resultado;
	}
	
	
	public void actualizar(String nombre, String apellido, Date fechaNacimiento, 
			String nacionalidad, String telefono,  Integer id ) {
		try {
			final PreparedStatement statement = con.prepareStatement("UPDATE huespedes SET " + 
												"nombre=?, " + 
												"apellido=?, " + 
												"fecha_nacimiento=?, " + 
												"nacionalidad=?, " + 
												"telefono=? " + 
												"WHERE id=?");
			try( statement ){
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setDate(3, fechaNacimiento);
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				statement.setInt(6, id);
				statement.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ocurrió una excepción en el método actualizar de la clase HuespedDAO: " + e.getMessage(), e);
		}
	}

	
	public void eliminar( Integer id ) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id=?");
			try( statement ){
				statement.setInt(1, id);
				statement.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ocurrió una excepción en el método eliminar de la clase HuespedDAO: " + e.getMessage(), e);
		}
	}
	
	
	public void eliminarPorIdReserva( Integer idReserva ) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id_reserva=?");
			try( statement ){
				statement.setInt(1, idReserva);
				statement.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ocurrió una excepción en el método eliminarPorIdReserva de la clase HuespedDAO: " + e.getMessage(), e);
		}
	}
}
