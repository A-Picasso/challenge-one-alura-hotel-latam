package controller;

import java.util.List;

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
	
	
	public List<Huesped> listar(){
		return this.huespedDAO.listar();
	}

	
	public List<Huesped> listarPorApellido( String apellido ){
		return this.huespedDAO.listarPorApellido(apellido);
	}
	
}
