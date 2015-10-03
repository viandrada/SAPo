package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.ProductoGenerico;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductoGenericoDAO {

	@PersistenceContext(unitName="SAPo-AccesoDatos")
	EntityManager em;
	
	public ProductoGenerico getProductoGenerico(String email){
		return em.find(ProductoGenerico.class, email);
	}
	
	public boolean existeProductoGenerico(int idProductoGenerico){
		return (em.createQuery("SELECT a FROM ProductoGenerico p WHERE a.idProductoGenerico=:idProductoGenerico")
				.setParameter("idProductoGenerico", idProductoGenerico)
				.getResultList().size()== 1);
	}
	
	public void insertarProductoGenerico (ProductoGenerico a){
		em.persist(a);
	}
	
	public void actualizarProductoGenerico(ProductoGenerico a){
		em.merge(a);		
	}
}
