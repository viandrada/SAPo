package com.sapo.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AlmacenDAO {

	@PersistenceContext(unitName = "SAPo-Negocio")
	EntityManager em;

	public Almacen getAlmacen(int id) {
		return em.find(Almacen.class, id);
	}

	public Almacen getAlmacenPorId(int idAlmacen) {
		return em.find(Almacen.class, idAlmacen);
	}

	public boolean existeAlmacen(int idAlmacen) {
		return (em
				.createQuery("SELECT a FROM Almacen a WHERE a.email=:idAlmacen")
				.setParameter("idAlmacen", idAlmacen).getResultList().size() == 1);
	}

	public int insertarAlmacen(Almacen a) {
		em.persist(a);
		return a.getIdAlmacen();
	}

	public void actualizarAlmacen(Almacen a) {
		 //em.merge(a);
		em.persist(a);
	}

	/*
	 * public List<Almacen> getAlmacenesUsuario(String emailUsuario) {
	 * List<Almacen> listaAlmacenes = new ArrayList<Almacen>(); try { Query
	 * consulta = this.em
	 * .createNamedQuery("Almacen.getAlmacenesUsuario.Email");
	 * consulta.setParameter("email", emailUsuario); listaAlmacenes =
	 * consulta.getResultList(); } catch (Exception excep) { throw excep; }
	 * return listaAlmacenes; }
	 */
	@SuppressWarnings("unchecked")
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
	}
	
	public int getCantAlmacenesUsuario(String emailUsuario) {
		/*OBS: Este método cuenta todos los almacenes a los cuale el usuairo tiene
		 * acceso, en principio no muestra la cantidad de almacenes de los que
		 * es propietario
		 */
		
		List<Almacen> listaAlmacenes = new LinkedList<Almacen>();
		List<Usuario> listaUsu = new LinkedList<Usuario>();
		int cant = 0;
		try {
			listaAlmacenes = em.createQuery("SELECT a FROM Almacen a").getResultList();
			if (!listaAlmacenes.isEmpty()) {
				for (Almacen a : listaAlmacenes) {
					// listaUsu=a.getListaUsuariosPropietarios();
					listaUsu = a.getUsuarios();
					if (!listaUsu.isEmpty()) {
						for (Usuario u : listaUsu) {
							if (u.getEmail().equals(emailUsuario)) {
								cant++;
							}
						}
					}
				}
			}

		} catch (Exception excep) {
			throw excep;
		}
		return cant;
	}

}
