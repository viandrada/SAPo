package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sapo.entidades.ProductoGenerico;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductoGenericoDAO {

	@PersistenceContext(unitName="SAPo-Negocio")
	EntityManager em;
	
	////EMAIL????? 
	public ProductoGenerico getProductoGenerico(String email){
		return em.find(ProductoGenerico.class, email);
	}
	
	public ProductoGenerico getProductoGenericoPorID(int id){
		ProductoGenerico prodGen;
		Query query =em.createQuery("SELECT a FROM ProductoGenerico d¡p WHERE a.idProductoGenerico=:idProductoGenerico");
		query.setParameter("idProductoGenerico", id);
		if ((query.getResultList() != null) && (query.getResultList().size() > 0)){
			prodGen = (ProductoGenerico) query.getSingleResult();
			return (ProductoGenerico) query.getSingleResult();
		}
		else 
			return null;	
	}
	
	public boolean existeProductoGenerico(int idProductoGenerico){
		return (em.createQuery("SELECT a FROM ProductoGenerico p WHERE a.idProductoGenerico=:idProductoGenerico")
				.setParameter("idProductoGenerico", idProductoGenerico)
				.getResultList().size()== 1);
	}
	
	public int insertarProductoGenerico (ProductoGenerico a){
		em.persist(a);
		em.flush(); 
		return a.getIdProductoGenerico();
	}
	
	public void actualizarProductoGenerico(ProductoGenerico a){
		em.merge(a);		
	}
}
