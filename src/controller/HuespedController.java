package controller;

import java.sql.Date;
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
	
	
	public void actualizar( String nombre, String apellido, Date fechaNacimiento, 
			String nacionalidad, String telefono,  Integer id ) {
		this.huespedDAO.actualizar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, id);
	}
	
}
