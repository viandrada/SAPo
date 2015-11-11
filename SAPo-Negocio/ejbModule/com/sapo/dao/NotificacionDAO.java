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

import com.sapo.entidades.Almacen;
import com.sapo.entidades.Notificacion;
import com.sapo.entidades.Usuario;

/**
 * Session Bean implementation class NotificacionDAO
 */
@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class NotificacionDAO {

    /**
     * Default constructor. 
     */
    public NotificacionDAO() {
        // TODO Auto-generated constructor stub
    }
	@PersistenceContext(unitName = "SAPo-Negocio")
	private EntityManager em;
	
	public Notificacion getNotificacion(int idNotificacion) {
		Notificacion n = new Notificacion();
		try {
			n = em.find(Notificacion.class, idNotificacion);
		} catch (NoResultException e) {
			System.out.print("No se encontró la notificación.");
		}
		return n;
	}
	
	public List<Notificacion> getNotificaciones(int idUsuario) {
		List<Notificacion> nList = new ArrayList<Notificacion>();
		try {
			Query consulta = this.em
					.createNamedQuery("Notificacion.getNotificaciones");
			consulta.setParameter("idUsuario", idUsuario);
			nList = consulta.getResultList();
		} catch (Exception excep) {
			throw excep;
		}
		return nList;
	}
	
	public void crearNotificacion(Notificacion n) {
		em.persist(n);
	}
	
	public void actualizarNotificacion(Notificacion n) {
		 em.merge(n);
	}
}
