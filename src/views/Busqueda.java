package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.HuespedController;
import controller.ReservaController;
import modelo.Huesped;
import modelo.Reserva;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.awt.event.KeyAdapter;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	private ReservaController reservaController;
	private HuespedController huespedController;
	private int estadia = 1000;
	int xMouse, yMouse;

	
	/**
	 * Create the frame.
	 */
	public Busqueda() {
		this.reservaController = new ReservaController();
		this.huespedController = new HuespedController();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if( panel.getSelectedIndex() == 0 ) {
					txtBuscar.setText("BUSCAR POR ID");
					limpiarTabla(modelo);
					cargarTbReservas();
				} else if( panel.getSelectedIndex() == 1 ) {
					txtBuscar.setText("BUSCAR POR APELLIDO");
					limpiarTabla(modeloHuesped);	
					cargarTbHuespedes();
				}
			}
		});
		
		txtBuscar = new JTextField("BUSCAR POR ID");
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		txtBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if( txtBuscar.getText().equals("BUSCAR POR ID") || txtBuscar.getText().equals("BUSCAR POR APELLIDO") ) {
					limpiarBuscador();
				}
			}
		});
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				int numTabla = panel.getSelectedIndex();
				
				switch( numTabla ) {
				case 0:
					if(Character.isLetter(evt.getKeyChar()) || (evt.getKeyChar()==KeyEvent.VK_SPACE) || (evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
				          evt.consume();
				      }
					break;
				case 1:
					if(!Character.isLetter(evt.getKeyChar()) && !(evt.getKeyChar()==KeyEvent.VK_SPACE) && !(evt.getKeyChar()==KeyEvent.VK_BACK_SPACE)){
				          evt.consume();
				      }
					break;
				}
			}
		});
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 303, 42);
		contentPane.add(lblNewLabel_4);
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		cargarTbReservas();
		
		
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		cargarTbHuespedes();
		
		
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int numTabla = panel.getSelectedIndex();
				
				switch( numTabla ) {
				case 0:
					if( !txtBuscar.getText().isEmpty() ) {
						limpiarTabla(modelo);
						cargarTbReservasPorId();
						limpiarBuscador();
					} else {
						JOptionPane.showMessageDialog(null, "Reserva - Ingresa el ID a buscar",
								"¡Ingrese un criterio de busqueda valido!", JOptionPane.WARNING_MESSAGE);
						txtBuscar.requestFocus();
						limpiarTabla(modelo);
						cargarTbReservas();
					}
					break;
				case 1:
					if( !txtBuscar.getText().isEmpty() ) {
						limpiarTabla(modeloHuesped);
						cargarTbHuespedesPorApellido();
						limpiarBuscador();
					} else {
						JOptionPane.showMessageDialog(null, "Huesped - Ingresa el Apellido a buscar",
								"¡Ingrese un criterio de busqueda valido!", JOptionPane.WARNING_MESSAGE);
						txtBuscar.requestFocus();
						limpiarTabla(modeloHuesped);
						cargarTbHuespedes();
					}
					break;
				}

			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int numTabla = panel.getSelectedIndex();
				
				switch( numTabla ) {
				case 0:
					actualizarReservas();
					tbReservas.clearSelection();
					limpiarTabla(modelo);
					cargarTbReservas();
					break;
				case 1:
					actualizarHuespedes();
					tbHuespedes.clearSelection();
					limpiarTabla(modeloHuesped);
					cargarTbHuespedes();
					break;
				}
			}
		});
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int numTabla = panel.getSelectedIndex();
				
				switch( numTabla ) {
				case 0:
					eliminarReserva();
					tbReservas.clearSelection();
					limpiarTabla(modelo);
					cargarTbReservas();
					break;
				case 1:
					eliminarHuesped();
					tbHuespedes.clearSelection();
					limpiarTabla(modeloHuesped);
					cargarTbHuespedes();
					break;
				}
			}
		});
		setResizable(false);
	}
	
	
	private void cargarTbReservas() {
		List<Reserva> listaReserva = this.reservaController.listar();
		listaReserva.forEach( reserva -> modelo.addRow( new Object[] {
				reserva.getId(), reserva.getFechaEntrada(), reserva.getFechaSalida(),
				reserva.getValor(), reserva.getFormaPago()
		}) );
	}
	
	
	private void cargarTbReservasPorId() {
		List<Reserva> listaReservaId = this.reservaController.buscarPorId(txtBuscar.getText());
		listaReservaId.forEach( reserva -> modelo.addRow( new Object[] {
				reserva.getId(), reserva.getFechaEntrada(), reserva.getFechaSalida(),
				reserva.getValor(), reserva.getFormaPago()
		}) );
	}
	
	
	private void cargarTbHuespedes() {
		List<Huesped> listaHuespedes = this.huespedController.listar();
		listaHuespedes.forEach( huesped -> modeloHuesped.addRow( new Object[] {
				huesped.getId(), huesped.getNombre(), huesped.getApellido(), huesped.getFechaNacimiento(),
				huesped.getNacionalidad(), huesped.getTelefono(), huesped.getIdReserva()
		}) );
	}
	
	
	private void cargarTbHuespedesPorApellido() {
		List<Huesped> listarHuespedApellido = this.huespedController.listarPorApellido(txtBuscar.getText());
		listarHuespedApellido.forEach( huesped -> modeloHuesped.addRow( new Object[] {
				huesped.getId(), huesped.getNombre(), huesped.getApellido(), huesped.getFechaNacimiento(),
				huesped.getNacionalidad(), huesped.getTelefono(), huesped.getIdReserva()
		}) );
	}
	
	
	private void actualizarReservas() {
		if( tbReservas.getSelectedRow() < 0 || tbReservas.getSelectedRow() < 0 ) {
			JOptionPane.showMessageDialog(null, "Reserva - Por favor, seleccione un registro",
					"¡Error, debe seleccionar un registro!", JOptionPane.WARNING_MESSAGE);
		}else {
			Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn())).ifPresent( celda -> {
				Integer id = Integer.valueOf( modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString() );
				LocalDate fEntrada = LocalDate.parse( modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString() );
				LocalDate fSalida = LocalDate.parse( modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString() );
				Double valor = recalcularValorReserva( fEntrada, fSalida );
				String formaPago = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
				if( tbReservas.getSelectedColumn() == 0 ) {
					JOptionPane.showMessageDialog(null, "Reserva - No se pueden editar los ID",
							"¡No se pueden editar los ID!", JOptionPane.WARNING_MESSAGE);
				}else if( valor < 0 ) {
					JOptionPane.showMessageDialog(null, "Reserva - La fecha de CheckOut no puede ser posterior a la fecha de CheckIn",
							"¡Error en las fechas!", JOptionPane.WARNING_MESSAGE);
				}else {
					this.reservaController.actualizar(Date.valueOf(fEntrada), Date.valueOf(fSalida), valor, formaPago, id);
					JOptionPane.showMessageDialog(null, String.format( "Registro modificado con exito para la reserva: " + id ),
							"¡Registro modificado con exito!", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
	}
	
	
	private void actualizarHuespedes() {
		if( tbHuespedes.getSelectedRow() < 0 || tbHuespedes.getSelectedRow() < 0 ) {
			JOptionPane.showMessageDialog(null, "Huésped - Por favor, seleccione un registro",
					"¡Error, debe seleccionar un registro!", JOptionPane.WARNING_MESSAGE);
		}else {
			Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn())).ifPresent( celda -> {
			Integer id = Integer.valueOf( modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString() );
			String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
			String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
			LocalDate fechaNacimiento = LocalDate.parse( modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString() );
			String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
			String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
			
			if( tbHuespedes.getSelectedColumn() == 0 || tbHuespedes.getSelectedColumn() == 6 ) {
				JOptionPane.showMessageDialog(null, "Huésped - No se pueden editar los ID",
						"¡No se pueden editar los ID!", JOptionPane.WARNING_MESSAGE);
			}else {
				this.huespedController.actualizar(nombre, apellido, Date.valueOf(fechaNacimiento), nacionalidad, telefono, id);
				JOptionPane.showMessageDialog(null, String.format( "Registro modificado con exito para el huésped: " + id ),
						"¡Registro modificado con exito!", JOptionPane.INFORMATION_MESSAGE);
			}
			});
		}		
	}
	
	
	private void eliminarReserva() {
		if( tbReservas.getSelectedRow() < 0 || tbReservas.getSelectedRow() < 0 ) {
			JOptionPane.showMessageDialog(null, "Reserva - Por favor, seleccione un registro",
					"¡Error, debe seleccionar un registro!", JOptionPane.WARNING_MESSAGE);
		}else {
			Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn())).ifPresent( celda ->{
				Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
				int confirmacion = JOptionPane.showConfirmDialog(null, String.format( "Reserva - Desea eliminar la reserva N°: " + id ), 
						"¿Eliminar la reserva?", JOptionPane.INFORMATION_MESSAGE);
				if( confirmacion == JOptionPane.YES_OPTION ) {
					this.huespedController.eliminarPorIdReserva(id);
					this.reservaController.eliminar(id);
					modelo.removeRow(tbReservas.getSelectedRow());
					JOptionPane.showMessageDialog(null, "¡Reserva eliminada con exito!",
							"Reserva eliminada", JOptionPane.INFORMATION_MESSAGE);
				}
				
			});
		}
	}
	
	
	private void eliminarHuesped() {
		if( tbHuespedes.getSelectedRow() < 0 || tbHuespedes.getSelectedRow() < 0 ) {
			JOptionPane.showMessageDialog(null, "Huésped - Por favor, seleccione un registro",
					"¡Error, debe seleccionar un registro!", JOptionPane.WARNING_MESSAGE);
		}else {
			Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn())).ifPresent( celda ->{
				Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
				Integer idReserva = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6).toString());
				int confirmacion = JOptionPane.showConfirmDialog(null, String.format( "Huésped - Desea eliminar la información del huésped N°: " + id ), 
						"¿Eliminar la información del huésped?", JOptionPane.INFORMATION_MESSAGE);
				if( confirmacion == JOptionPane.YES_OPTION ) {
					this.huespedController.eliminar(id);
					this.reservaController.eliminar(idReserva);
					modeloHuesped.removeRow(tbHuespedes.getSelectedRow());
					JOptionPane.showMessageDialog(null, "¡Información del huésped eliminada con exito!",
							"Información del huésped eliminada", JOptionPane.INFORMATION_MESSAGE);
				}
				
			});
		}
	}
	
	
	private Double recalcularValorReserva(LocalDate fE, LocalDate fS) {
		if( fE !=null && fS !=null ) {
			int dias = (int) ChronoUnit.DAYS.between(fE, fS);
			Double valor = (double) (dias * estadia);
			return valor;
		}
		return null;
	}

	
	private void limpiarBuscador() {
		txtBuscar.setText("");
	}
	
	private void limpiarTabla( DefaultTableModel table ) {
		table.getDataVector().clear();
	}
	
	
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
