package com.sapo.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Almacen;
import com.sapo.entidades.AlmacenIdeal;
import com.sapo.entidades.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AlmacenIdealDAO {

	@PersistenceContext(unitName = "SAPo-Negocio")
	EntityManager em;

	public AlmacenIdeal getAlmacenIdeal(int id) {
		return em.find(AlmacenIdeal.class, id);
	}

	public AlmacenIdeal getAlmacenIdealPorId(int idAlmacenIdeal) {
		return em.find(AlmacenIdeal.class, idAlmacenIdeal);
	}

	public boolean existeAlmacenIdeal(int idAlmacenIdeal) {
		return (em
				.createQuery("SELECT a FROM AlmacenIdeal a WHERE a.email=:idAlmacenIdeal")
				.setParameter("idAlmacenIdeal", idAlmacenIdeal).getResultList().size() == 1);
	}

	public int insertarAlmacenIdeal(AlmacenIdeal a) {
		em.persist(a);
		return a.getIdAlmacenIdeal();
	}

	public void actualizarAlmacenIdeal(AlmacenIdeal a) {
		 //em.merge(a);
		em.persist(a);
	}


/*	@SuppressWarnings("unchecked")
	public List<Almacen> getAlmacenesUsuario(String emailUsuario) {
		List<Almacen> listaAlmacenes = new LinkedList<Almacen>();
		List<Usuario> listaUsu = new LinkedList<Usuario>();

		List<Almacen> lisR = new LinkedList<Almacen>();
		try {
			listaAlmacenes = em.createQuery("SELECT a FROM Almacen a")
					.getResultList();
			if (!listaAlmacenes.isEmpty()) {
				for (Almacen a : listaAlmacenes) {
					// listaUsu=a.getListaUsuariosPropietarios();
					listaUsu = a.getUsuarios();
					if (!listaUsu.isEmpty()) {
						for (Usuario u : listaUsu) {
							if (u.getEmail().equals(emailUsuario)) {
								lisR.add(a);
							}
						}
					}
				}
			}

		} catch (Exception excep) {
			throw excep;
		}

		return lisR;
	}*/
	
}
