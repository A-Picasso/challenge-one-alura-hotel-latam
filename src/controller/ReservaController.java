package controller;

import java.sql.Date;
import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import modelo.Reserva;

public class ReservaController {
	
	private ReservaDAO reservaDao;

	public ReservaController() {
		ConnectionFactory factory = new ConnectionFactory();
		this.reservaDao = new ReservaDAO( factory.recuperarConexion() );
	}
	
	public void guardar( Reserva reserva ) {
		this.reservaDao.guardar(reserva);
	}
	
	public List<Reserva> listar(){
		return this.reservaDao.listar();
	}

	public List<Reserva> buscarPorId( String id ){
		return this.reservaDao.buscarPorId(id);
	}
	
	
	public int actualizar( Date fechaEntrada, Date fechaSalida, Double valor, String formaPago, Integer id ) {
		return this.reservaDao.actualizar(fechaEntrada, fechaSalida, valor, formaPago, id);
	}
}
