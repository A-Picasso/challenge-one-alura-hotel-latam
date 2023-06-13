package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Reserva;

public class ReservaDAO {
	
	private Connection con;

	public ReservaDAO( Connection con ) {
		this.con = con;
	}
	
	public void guardar( Reserva reserva ) {
		try {
			final PreparedStatement statement = con.prepareStatement("INSERT INTO reservas(fecha_entrada, fecha_salida, valor, forma_pago)" + 
										" VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			try ( statement ) {
				ejecutarRegistro( reserva, statement );
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error de ejecucion: " + e.getMessage(),e);
		}
	}

	private void ejecutarRegistro(Reserva reserva, PreparedStatement statement) throws SQLException  {
		statement.setDate(1, reserva.getFechaEntrada());
		statement.setDate(2, reserva.getFechaSalida());
		statement.setDouble(3, reserva.getValor());
		statement.setString(4, reserva.getFormaPago());
		statement.execute();
		
		final ResultSet resultSet = statement.getGeneratedKeys();
		try ( resultSet ) {
			while ( resultSet.next() ) {
				reserva.setId(resultSet.getInt(1));
			}
		}
	}
	
	public List<Reserva> listar(){
		List<Reserva> resultado = new ArrayList<>();
		try {
			final PreparedStatement statement = con.prepareStatement("SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas");
			try( statement ){
				statement.execute();
				final ResultSet rst = statement.getResultSet();
				try( rst ){
					while( rst.next() ) {
						Reserva fila = new Reserva(rst.getInt("id"), rst.getDate("fecha_entrada"), rst.getDate("fecha_salida"), 
										rst.getDouble("valor"), rst.getString("forma_pago"));
						resultado.add(fila);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}
	
	
	public List<Reserva> buscarPorId( String id ){
		List<Reserva> resultadoId = new ArrayList<>();
		try {
			final PreparedStatement statement = con.prepareStatement("SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas WHERE id=?");
			try(statement){
				statement.setString(1, id);
				statement.execute();
				final ResultSet rst = statement.getResultSet();
				try( rst ){
					while( rst.next() ) {
						Reserva fila = new Reserva(rst.getInt("id"), rst.getDate("fecha_entrada"), rst.getDate("fecha_salida"), 
									rst.getDouble("valor"), rst.getString("forma_pago"));
						resultadoId.add( fila );
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultadoId;
	}
	
	public int actualizar( Date fechaEntrada, Date fechaSalida, Double valor, String formaPago, Integer id ) {
		try {
			final PreparedStatement statement = con.prepareStatement(
												"UPDATE reservas SET " + 
												"fecha_entrada=?, " + 
												"fecha_salida=?, " + 
												"valor=?, " + 
												"forma_pago=? " + 
												"WHERE id=?");
			try( statement ){
				statement.setDate(1, fechaEntrada);
				statement.setDate(2, fechaSalida);
				statement.setDouble(3, valor);
				statement.setString(4, formaPago);
				statement.setInt(5, id);
				statement.execute();
				
				int contadorActualizaciones = statement.getUpdateCount();
				return contadorActualizaciones;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Ocurrió una excepción en ReservaDao" + e.getMessage(), e);
		}
	}

}
