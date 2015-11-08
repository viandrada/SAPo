package com.sapo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.order.AuditOrder;

import com.datatypes.DataCategoria;
import com.datatypes.DataProducto;
import com.sapo.entidades.Producto;
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
	
	/*CORREGIR
	 * */
	public List getProductosGenericoMasUsados(){
		//Me parece que no se puede hacer así ya que no hay un group by
		//Hay que hacerlo por sql. La consulta en SQL es: (comprobado que anda)
		String sqlQuery = "select productogenerico_idproductogenerico, "
				+ "count(productogenerico_idproductogenerico) as count_generico"
				+ "	from producto where productogenerico_idproductogenerico is"
				+ " not null group by productogenerico_idproductogenerico "
				+ "order by count_generico desc";
		
		Query queryProdGen = em.createNativeQuery(sqlQuery);
		return queryProdGen.getResultList();
		
		//Lo que andaría pero no hace el group by, sólo devuelve todos los productos
		//que se hicieron desde un genérico:
//		AuditReader reader = AuditReaderFactory.get(em);
//		List queryProducto = reader.createQuery()
//			    .forRevisionsOfEntity(Producto.class, false,true)
//			    .add(AuditEntity.relatedId("productogenerico").ne(null)) 
//			    .add(AuditEntity.revisionType().eq(RevisionType.ADD))
//			    .addProjection(AuditEntity.id().count())
//			    //.addOrder(AuditEntity.property("productogenerico").desc())
//			    .getResultList();	
//		
//		return queryProducto;
		
	}
}
