package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.HistoricoProducto;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HistoricoProductoDAO {
	
	@PersistenceContext(unitName="SAPo-AccesoDatos")
	EntityManager em;
	
	public HistoricoProducto getHistoricoProducto(String email){
		return em.find(HistoricoProducto.class, email);
	}
	
	public boolean existeHistoricoProducto(int idHistoricoProducto){
		return (em.createQuery("SELECT a FROM HistoricoProducto p WHERE a.idHistoricoProducto=:idHistoricoProducto")
				.setParameter("idHistoricoProducto", idHistoricoProducto)
				.getResultList().size()== 1);
	}

	
	public void insertarHistoricoProducto (HistoricoProducto a){
		em.persist(a);
	}
	
	public void actualizarHistoricoProducto(HistoricoProducto a){
		em.merge(a);		
	}

}
