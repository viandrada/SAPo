package com.sapo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sapo.entidades.Notificacion;
import com.sapo.entidades.NotificacionConfig;

/**
 * Session Bean implementation class NotificacionConfigDAO
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificacionConfigDAO {

    /**
     * Default constructor. 
     */
    public NotificacionConfigDAO() {
        // TODO Auto-generated constructor stub
    }
	@PersistenceContext(unitName = "SAPo-Negocio")
	private EntityManager em;
	
	public NotificacionConfig getNotificacionConfig(int idNotificacionConfig) {
		NotificacionConfig n = new NotificacionConfig();
		try {
			n = em.find(NotificacionConfig.class, idNotificacionConfig);
		} catch (NoResultException e) {
			System.out.print("No se encontró la configuración de la notificación.");
		}
		return n;
	}
	
	public void configurarNotificacion(NotificacionConfig n) {
		em.persist(n);
	}
	
	public List<NotificacionConfig> getNotificacionesCongif(int idUsuario){
		List<NotificacionConfig> configs = new ArrayList<NotificacionConfig>();
		try {
			Query consulta = this.em
					.createNamedQuery("NotificacionConfig.getConfiguraciones");
			consulta.setParameter("idUsuario", idUsuario);
			configs = consulta.getResultList();
		} catch (NoResultException e) {
			System.out.print("No se encontró la configuración de la notificación.");
		}
		return configs;
	}
	
	public List<NotificacionConfig> getNotificacionesCongifPorProducto(int idProducto){
		List<NotificacionConfig> configs = new ArrayList<NotificacionConfig>();
		try {
			Query consulta = this.em
					.createNamedQuery("NotificacionConfig.getConfiguracionesPorProducto");
			consulta.setParameter("idProducto", idProducto);
			configs = consulta.getResultList();
		} catch (NoResultException e) {
			System.out.print("No se encontró la configuración de la notificación.");
		}
		return configs;
	}
}
