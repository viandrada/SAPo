package com.sapo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sapo.entidades.NotificacionGenericaConfig;

/**
 * Session Bean implementation class NotificacionGenericaConfigDAO
 */
@Stateless
@LocalBean
public class NotificacionGenericaConfigDAO {

	/**
	 * Default constructor.
	 */
	public NotificacionGenericaConfigDAO() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "SAPo-Negocio")
	private EntityManager em;

	public NotificacionGenericaConfig getNotificacionGenericaConfigPorNombre(
			String nombre) {
		NotificacionGenericaConfig n = new NotificacionGenericaConfig();
		try {
			Query consulta = this.em
					.createNamedQuery("NotificacionGenericaConfig.getConfiguracionePorNombre");
			consulta.setParameter("nombreParametro", nombre);
			n = (NotificacionGenericaConfig) consulta.getSingleResult();
		} catch (NoResultException e) {
			System.out
					.print("No se encontró la configuración de la notificación.");
		}
		return n;
	}
	
	public List<NotificacionGenericaConfig> getNotificacionesGenericasConfigActivas() {
		List<NotificacionGenericaConfig> n = new ArrayList<NotificacionGenericaConfig>();
		try {
			Query consulta = this.em
					.createNamedQuery("NotificacionGenericaConfig.getConfiguracionesActivas");
			n = consulta.getResultList();
		} catch (NoResultException e) {
			System.out
					.print("No se encontró la configuración de la notificación.");
		}
		return n;
	}

	public void crearNotificacionGenericaConfig(NotificacionGenericaConfig n) {
		em.persist(n);
	}

	public void actualizarNotificacionGenericaConfig(
			NotificacionGenericaConfig n) {
		em.merge(n);
	}
}
