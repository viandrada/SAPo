package com.sapo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sapo.entidades.Almacen;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AlmacenDAO {
	
	@PersistenceContext(unitName="SAPo-Negocio")
	EntityManager em;
	
	public Almacen getAlmacen(String email){
		return em.find(Almacen.class, email);
	}
	public Almacen getAlmacenPorId(int idAlmacen){
		return em.find(Almacen.class, idAlmacen);
	}
	public boolean existeAlmacen(int idAlmacen){
		return (em.createQuery("SELECT a FROM Almacen p WHERE a.email=:idAlmacen")
				.setParameter("idAlmacen", idAlmacen)
				.getResultList().size()== 1);
	}

	public void insertarAlmacen (Almacen a){
		em.persist(a);
	}
	
	public void actualizarAlmacen(Almacen a){
		em.merge(a);		
	}
	public List<Almacen> getAlmacenesUsuario(String emailUsuario)
	{
		List<Almacen> listaAlmacenes = new ArrayList<Almacen>();
		try {
			Query consulta = this.em
					.createNamedQuery("Almacen.getAlmacenesUsuario.Email");
			consulta.setParameter("email", emailUsuario);
			listaAlmacenes = consulta.getResultList();
		} catch (Exception excep) {
			throw excep;
		}
		return listaAlmacenes;
	}
}
