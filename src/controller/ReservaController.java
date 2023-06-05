package controller;

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
	
	public List<Reserva> listarReservas(){
		return this.reservaDao.listarReservas();
	}

	public List<Reserva> buscarPorId( String id ){
		return this.reservaDao.buscarPorId(id);
	}
}
