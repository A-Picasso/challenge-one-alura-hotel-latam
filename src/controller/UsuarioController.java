package controller;

import dao.UsuarioDAO;
import factory.ConnectionFactory;

public class UsuarioController {
	
	private UsuarioDAO usuarioDao;

	public UsuarioController() {
		ConnectionFactory factory = new ConnectionFactory();
		this.usuarioDao = new UsuarioDAO(factory.recuperarConexion());
	}
	
	public boolean consultaUsuario( String nombre, String contrasena ) {
		return this.usuarioDao.consultaUsuario(nombre, contrasena);
	}

}
