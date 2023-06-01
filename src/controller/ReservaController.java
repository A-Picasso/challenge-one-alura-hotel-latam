package controller;

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

}
