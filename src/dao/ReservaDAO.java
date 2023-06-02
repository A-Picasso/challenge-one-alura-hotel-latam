package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelo.Reserva;

public class ReservaDAO {
	
	private Connection con;

	public ReservaDAO( Connection con ) {
		this.con = con;
	}
	
	public void guardar( Reserva reserva ) {
		try {
			PreparedStatement statement = con.prepareStatement("INSERT INTO reservas(fecha_entrada, fecha_salida, valor, forma_pago)" + 
										"VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			try ( statement ) {
				ejecutarRegistro( reserva, statement );
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void ejecutarRegistro(Reserva reserva, PreparedStatement statement) throws SQLException {
		statement.setDate(1, reserva.getFechaEntrada());
		statement.setDate(2, reserva.getFechaSalida());
		statement.setDouble(3, reserva.getValor());
		statement.setString(4, reserva.getFormaPago());
		statement.execute();
		
		final ResultSet resultSet = statement.getResultSet();
		try ( resultSet ) {
			while ( resultSet.next() ) {
				reserva.setId(resultSet.getInt(1));
			}
		}
	}

}
