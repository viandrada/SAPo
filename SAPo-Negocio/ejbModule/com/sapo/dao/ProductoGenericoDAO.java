package com.sapo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.datatypes.DataCategoria;
import com.datatypes.DataProducto;
import com.sapo.entidades.ProductoGenerico;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductoGenericoDAO {

	@PersistenceContext(unitName="SAPo-Negocio")
	EntityManager em;
	
	public ProductoGenerico getProductoGenerico(int id){
		return em.find(ProductoGenerico.class, id);
	}
	
	public ProductoGenerico getProductoGenericoPorId(int id){
		ProductoGenerico prodGen;
		Query query =em.createQuery("SELECT p FROM ProductoGenerico p WHERE p.idProductoGenerico=:idProductoGenerico");
		query.setParameter("idProductoGenerico", id);
		if ((query.getResultList() != null) && (query.getResultList().size() > 0)){
			prodGen = (ProductoGenerico) query.getSingleResult();
			return prodGen;
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
	
	public List<ProductoGenerico> getProductosGenericos(){
		Query consulta = this.em
				.createNamedQuery("ProductosGenericos.getProductos");
		List<ProductoGenerico> productos = (List<ProductoGenerico>)consulta.getResultList();
		return productos;
		
	}
	
	public void eliminarProductoGenerico(int idProductoGenerico){
		ProductoGenerico p = getProductoGenerico(idProductoGenerico);
		p.setEstaActivo(false);
		actualizarProductoGenerico(p);
	}
}
