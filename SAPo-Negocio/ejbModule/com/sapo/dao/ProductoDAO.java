package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Producto;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductoDAO {
	
	@PersistenceContext(unitName="SAPo-AccesoDatos")
	EntityManager em;
	
	public Producto getProducto(String email){
		return em.find(Producto.class, email);
	}
	
	public boolean existeProducto(int idProducto){
		return (em.createQuery("SELECT a FROM Producto p WHERE a.idProducto=:idProducto")
				.setParameter("idProducto", idProducto)
				.getResultList().size()== 1);
	}

	
	public void insertarProducto (Producto a){
		em.persist(a);
	}
	
	public void actualizarProducto(Producto a){
		em.merge(a);		
	}


}
