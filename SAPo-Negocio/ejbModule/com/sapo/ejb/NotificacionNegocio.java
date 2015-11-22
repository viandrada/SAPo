package com.sapo.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataNotificacion;
import com.datatypes.DataNotificacionConfig;
import com.datatypes.DataNotificacionGenericaConfig;
import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.ConfiguarcionDAO;
import com.sapo.dao.NotificacionConfigDAO;
import com.sapo.dao.NotificacionDAO;
import com.sapo.dao.NotificacionGenericaConfigDAO;
import com.sapo.dao.ProductoDAO;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Notificacion;
import com.sapo.entidades.NotificacionConfig;
import com.sapo.entidades.NotificacionGenericaConfig;
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
	private NotificacionGenericaConfigDAO notificacionGenericaConfigDAO;
	@EJB
	private NotificacionDAO notificacionDAO;
	@EJB
	private ProductoDAO productoDAO;
	@EJB
	private UsuarioDAO usuarioDAO;
	@EJB
	private Fabrica fabrica;
	@EJB
	private AlmacenDAO almacenDAO;
	@EJB
	private ConfiguarcionDAO configDAO;

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
					+ " AND p.idProducto = "
					+ nc.get(i).getProducto().getIdProducto();
			System.out.println(query);
			Producto p = this.productoDAO.verificarNotificacion(query);
			// Si devuelve un producto es porq se cumplió la condición y hay que
			// generar una notificación.
			if (p.getIdProducto() != 0) {
				Notificacion n = new Notificacion();
				n.setLeida(false);
				n.setUsuario(u);
				n.setMensaje("El producto " + p.getNombre() + " tiene "
						+ nc.get(i).getNombreCampo() + " "
						+ nc.get(i).getOperador() + " a "
						+ nc.get(i).getValor());

				this.notificacionDAO.crearNotificacion(n);
			}
		}

		// TODO Verificar si hay notificaciones genéricas
		NotificacionGenericaConfig notificacionGenericaConfig = new NotificacionGenericaConfig();
		List<NotificacionGenericaConfig> n = new ArrayList<NotificacionGenericaConfig>();
		n = this.getNotificacionesGenericasConfigActivas();
		if (n.size() != 0) {
			for (NotificacionGenericaConfig notif : n) {
				if (notif.getNombreParametro().equals("cantidadAlmacenes")) {
					notificacionGenericaConfig = notif;
				}
			}
		}
		// Traigo la cantidad de almacenes del usuario y comparo con el valor de
		// la configuracion.
		int cantAlmacensUsuario = this.almacenDAO
				.getCantAlmacenesUsuarioHabilitados(idUsuario);
		int maxAlmacenesPremium = this.configDAO
				.getValorConfigInt("maxCantAlmPremium");
		int maxAlmacenesComun = this.configDAO
				.getValorConfigInt("maxCantAlmComun");
		// Genero la notificacion
		if (u.isPremium()
				&& (maxAlmacenesPremium - cantAlmacensUsuario <= notificacionGenericaConfig
						.getValor())) {
			Notificacion not = new Notificacion();
			not.setLeida(false);
			not.setUsuario(u);
			not.setMensaje("Está por alcanzar el límite. Podrá crear sólo "
					+ (maxAlmacenesPremium - cantAlmacensUsuario)
					+ " almacenes más.");

			this.notificacionDAO.crearNotificacion(not);
		}
		if ((!u.isPremium())
				&& (maxAlmacenesComun - cantAlmacensUsuario <= notificacionGenericaConfig
						.getValor())) {
			Notificacion not = new Notificacion();
			not.setLeida(false);
			not.setUsuario(u);
			not.setMensaje("Está por alcanzar el límite. Podrá crear sólo "
					+ (maxAlmacenesComun - cantAlmacensUsuario)
					+ " almacenes más o si lo prefiere, pasar a ser usuario Premium.");

			this.notificacionDAO.crearNotificacion(not);
		}
	}

	public void actualizarNotificaciones(
			List<DataNotificacion> dataNotificaciones) {
		for (int i = 0; i < dataNotificaciones.size(); i++) {
			Notificacion n = this.notificacionDAO
					.getNotificacion(dataNotificaciones.get(i)
							.getIdNotificacion());
			n.setLeida(true);
			this.notificacionDAO.actualizarNotificacion(n);
		}
	}

	public List<DataNotificacionConfig> obtenerConfiguracionNotificaciones(
			int idProducto) {
		List<DataNotificacionConfig> dn = new ArrayList<DataNotificacionConfig>();
		Fabrica f = new Fabrica();
		dn = f.toDataNotificacionConfig(this.notificacionConfigDAO
				.getNotificacionesCongifPorProducto(idProducto));
		return dn;
	}

	public DataNotificacionGenericaConfig getNotificacionGenericaConfig(
			String nombreParametro) {
		NotificacionGenericaConfig n = new NotificacionGenericaConfig();
		DataNotificacionGenericaConfig dn = new DataNotificacionGenericaConfig();
		n = this.notificacionGenericaConfigDAO
				.getNotificacionGenericaConfigPorNombre(nombreParametro);
		dn.setIdNotificacionGenericaConfig(n.getIdNotificacionGenericaConfig());
		dn.setNombreParametro(n.getNombreParametro());
		dn.setValor(n.getValor());
		dn.setActiva(n.isActiva());
		return dn;
	}

	// Se usa en el front, trae sólo las activas para generar las notificaciones
	// al usuario.
	public List<NotificacionGenericaConfig> getNotificacionesGenericasConfigActivas() {
		List<NotificacionGenericaConfig> n = new ArrayList<NotificacionGenericaConfig>();
		n = this.notificacionGenericaConfigDAO
				.getNotificacionesGenericasConfigActivas();
		return n;
	}

	public void guardarNotificacionGenericaConfig(
			DataNotificacionGenericaConfig configuracion) {
		NotificacionGenericaConfig n = new NotificacionGenericaConfig();
		n = this.notificacionGenericaConfigDAO
				.getNotificacionGenericaConfigPorNombre(configuracion
						.getNombreParametro());
		if (n.getIdNotificacionGenericaConfig() != 0) {
			n.setActiva(configuracion.isActiva());
			n.setValor(configuracion.getValor());
			this.notificacionGenericaConfigDAO
					.actualizarNotificacionGenericaConfig(n);
		} else {
			n.setNombreParametro(configuracion.getNombreParametro());
			n.setActiva(configuracion.isActiva());
			n.setValor(configuracion.getValor());
			this.notificacionGenericaConfigDAO
					.crearNotificacionGenericaConfig(n);
		}

	}
}
