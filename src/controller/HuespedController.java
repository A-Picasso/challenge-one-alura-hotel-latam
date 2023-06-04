package controller;

import dao.HuespedDAO;
import factory.ConnectionFactory;
import modelo.Huesped;

public class HuespedController {
	
	private HuespedDAO huespedDAO;

	public HuespedController() {
		ConnectionFactory factory = new ConnectionFactory();
		this.huespedDAO = new HuespedDAO(factory.recuperarConexion());
	}
	
	public void guardar( Huesped huesped ) {
		this.huespedDAO.guardar(huesped);
	}

}
