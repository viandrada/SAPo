package com.sapo.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataNotificacion;
import com.datatypes.DataNotificacionConfig;
import com.sapo.dao.NotificacionConfigDAO;
import com.sapo.dao.NotificacionDAO;
import com.sapo.dao.ProductoDAO;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Notificacion;
import com.sapo.entidades.NotificacionConfig;
import com.sapo.entidades.Producto;
import com.sapo.entidades.Usuario;
import com.sapo.utils.Fabrica;

@Stateless
@LocalBean
public class NotificacionNegocio {
	public NotificacionNegocio() {
	}

	@EJB
	private NotificacionConfigDAO notificacionConfigDAO;
	@EJB
	private NotificacionDAO notificacionDAO;
	@EJB
	private ProductoDAO productoDAO;
	@EJB
	private UsuarioDAO usuarioDAO;
	@EJB
	private Fabrica fabrica;

	public void configurarNotificacion(DataNotificacionConfig dNotificacionConf) {
		NotificacionConfig nc = new NotificacionConfig();
		nc.setNombreCampo(dNotificacionConf.getNombreCampo());
		nc.setOperador(dNotificacionConf.getOperador());
		Producto p = this.productoDAO.getProducto(dNotificacionConf
				.getIdProducto());
		nc.setProducto(p);
		Usuario u = this.usuarioDAO
				.getUsuario(dNotificacionConf.getIdUsuario());
		nc.setUsuario(u);
		nc.setValor(String.valueOf(dNotificacionConf.getValor()));

		this.notificacionConfigDAO.configurarNotificacion(nc);
	}

	public List<DataNotificacion> obtenerNotificaciones(int idUsuario) {
		List<DataNotificacion> dataNotificaciones = new ArrayList<DataNotificacion>();
		dataNotificaciones = fabrica.toDataNotificacion(this.notificacionDAO
				.getNotificaciones(idUsuario));
		return dataNotificaciones;
	}

	public void generarNotificaciones(int idUsuario) {
		// Generar las queries y guardar las notificaciones
		// 1. Obtener la configuracion de notificaciones del usuario
		List<NotificacionConfig> nc = this.notificacionConfigDAO
				.getNotificacionesCongif(idUsuario);
		Usuario u = this.usuarioDAO.getUsuario(idUsuario);
		// 2. Generar las queries para cada configuracion, ejecutarla y si
		// devuelve un producto, generar una notificacion
		for (int i = 0; i < nc.size(); i++) {
			String query = "SELECT p FROM Producto p WHERE p."
					+ nc.get(i).getNombreCampo().toLowerCase() + " "
					+ nc.get(i).getOperador() + " " + nc.get(i).getValor()
					+ " AND p.idProducto = "+ nc.get(i).getProducto().getIdProducto();
			System.out.println(query);
			Producto p = this.productoDAO.verificarNotificacion(query);
			//Si devuelve un producto es porq se cumplió la condición y hay que generar una notificación.
			if(p.getIdProducto() != 0){
				Notificacion n = new Notificacion();
				n.setLeida(false);
				n.setUsuario(u);
				n.setMensaje("El producto "+p.getNombre()+ " tiene " +nc.get(i).getNombreCampo()+" "+ nc.get(i).getOperador() +" a " + nc.get(i).getValor());
				
				this.notificacionDAO.crearNotificacion(n);
			}
		}
	}
	
	public void actualizarNotificaciones(List<DataNotificacion> dataNotificaciones){
		for (int i = 0; i < dataNotificaciones.size(); i++) {
			Notificacion n = this.notificacionDAO.getNotificacion(dataNotificaciones.get(i).getIdNotificacion());
			n.setLeida(true);
			this.notificacionDAO.actualizarNotificacion(n);
		}
	}
	
	public List<DataNotificacionConfig> obtenerConfiguracionNotificaciones(int idProducto){
		List<DataNotificacionConfig> dn = new ArrayList<DataNotificacionConfig>();
		Fabrica f = new Fabrica();
		dn = f.toDataNotificacionConfig(this.notificacionConfigDAO.getNotificacionesCongifPorProducto(idProducto));
		return dn;
	}
}
