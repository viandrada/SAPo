package com.sapo.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import com.sapo.entidades.Almacen;
import com.sapo.entidades.AlmacenIdeal;
import com.sapo.entidades.Producto;
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
				.createQuery("SELECT a FROM Almacen a WHERE a.idAlmacen=:idAlmacen")
				.setParameter("idAlmacen", idAlmacen).getResultList().size() == 1);
	}
	
	public boolean tieneAlmacenIdeal(int idAlmacen){
		Almacen almacen = (Almacen) em.createQuery("SELECT a FROM Almacen a WHERE a.idAlmacen=:idAlmacen");
		return !(almacen.getAlmacenIdeal()==null);	
	}
	
	public AlmacenIdeal getAlmacenIdealDeAlmacenReal(int idAlmacenReal){
		Almacen almacen = (Almacen) em.createQuery("SELECT a FROM Almacen a WHERE a.idAlmacen=:idAlmacen");
		if (almacen.getAlmacenIdeal()==null)
			return null;
		else
			return almacen.getAlmacenIdeal();
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
	
	public int getCantAlmacenesUsuario(int idUser) {
	
		List<Almacen> listaAlmacenes = new LinkedList<Almacen>();
		List<Usuario> listaUsu = new LinkedList<Usuario>();
		int cant = 0;
		try {
			listaAlmacenes = em.createQuery("SELECT a FROM Almacen a").getResultList();
			if (!listaAlmacenes.isEmpty()) {
				for (Almacen a : listaAlmacenes) {
					// listaUsu=a.getListaUsuariosPropietarios();
					if (a.getPropietario().getIdUsuario()==idUser){
						cant++;
					}
				}
			}

		} catch (Exception excep) {
			throw excep;
		}
		return cant;
	}


	
	/*
	 * Obtiene una lista con los almacenes y sus históricos de un usuario.
	 */
	public List<Almacen> getHistoricoAlmacenesPorUsuario(int idUsuario) {
		
		AuditReader reader = AuditReaderFactory.get(em);
		List query1 = reader.createQuery()
			    .forRevisionsOfEntity(Almacen.class, false,true)
			    .add(AuditEntity.relatedId("propietario").eq(idUsuario))
			    .getResultList();	
		List<Almacen> listaAlm = new ArrayList<Almacen>();		
		if (query1!=null){	
			for (int i=0; i<query1.size();i++){
				Object[] objArray = (Object[])query1.get(i);
				Almacen alm = (Almacen)objArray[0];
				System.out.println("Hist almacen"+i+": "+alm.getNombre() + " - id: "+alm.getPropietario().getEmail());	
				listaAlm.add(alm);
				}
			
		}
		return listaAlm;
		
	}
	
}
