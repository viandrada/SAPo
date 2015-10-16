package com.sapo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sapo.entidades.Producto;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductoDAO {
	
	@PersistenceContext(unitName="SAPo-Negocio")
	EntityManager em;
	
	public Producto getProducto(String email){
		return em.find(Producto.class, email);
	}
	
	public List<Producto> getProductosAlmacen(int idAlmacen){
		Query consulta = this.em
				.createNamedQuery("Productos.getProductosDeAlmacen.IdAlmacen");
		consulta.setParameter("idAlmacen", idAlmacen);
		List<Producto> productos = (List<Producto>)consulta.getResultList();
		return productos;
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
