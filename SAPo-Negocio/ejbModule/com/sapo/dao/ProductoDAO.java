package com.sapo.dao;

import java.util.ArrayList;
import java.util.Date;
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

import com.sapo.entidades.Producto;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductoDAO {

	@PersistenceContext(unitName = "SAPo-Negocio")
	EntityManager em;

	public Producto getProducto(int id) {
		return em.find(Producto.class, id);
	}

	public List<Producto> getProductosAlmacen(int idAlmacen) {
		Query consulta = this.em
				.createNamedQuery("Productos.getProductosDeAlmacen.IdAlmacen");
		consulta.setParameter("idAlmacen", idAlmacen);
		List<Producto> productos = (List<Producto>) consulta.getResultList();
		return productos;
	}
	
	public boolean existeProducto(int idProducto) {
		return (em
				.createQuery(
						"SELECT a FROM Producto p WHERE a.idProducto=:idProducto")
				.setParameter("idProducto", idProducto).getResultList().size() == 1);
	}

	public void insertarProducto(Producto a) {
		em.persist(a);
	}

	public void actualizarProducto(Producto a) {
		em.merge(a);
	}

	public void eliminarProducto(int idProducto) {
		Producto p = getProducto(idProducto);
		p.setEstaActivo(false);
		actualizarProducto(p);
	}

	public void deleteProducto(int idProducto) {
		Producto p = getProducto(idProducto);
		em.remove(em.merge(p));
	}
	
	
	/* Obtiene una lista con los productos y sus históricos de un usuario.
	 * */
	public List getHistoricoProdPorUsuario(int idUsuario) {
		
		AuditReader reader = AuditReaderFactory.get(em);
		List queryProducto = reader.createQuery()
			    .forRevisionsOfEntity(Producto.class, false,true)
			    .add(AuditEntity.relatedId("usuario").eq(idUsuario))   
			    .getResultList();	
		
		return queryProducto;
		
	}
	
	
	/* Obtiene una lista con los productos y sus históricos de un usuario.
	 * */
	public List getHistoricoCambioStockProdPorAlmacen(int idAlmacen) {
		
		AuditReader reader = AuditReaderFactory.get(em);
		List queryProducto = reader.createQuery()
			    .forRevisionsOfEntity(Producto.class, false,true)
			    .addOrder(AuditEntity.revisionNumber().desc())
			    .add(AuditEntity.relatedId("almacen").eq(idAlmacen))  
			    .add(AuditEntity.property("stock").hasChanged())
			    .setMaxResults(5)
			    .getResultList();	
		
		return queryProducto;
		
	}
	
	
	/* Obtiene una lista con los productos y sus históricos de un usuario
	 * dentro de un lapso de tiempo.
	 * */
	public List getHistoricoProdPorUsuarioEnFecha(int idUsuario, Date fechaInicio, Date fechaFin) {
		
		AuditReader reader = AuditReaderFactory.get(em);
		Number fInicio = reader.getRevisionNumberForDate(fechaInicio);
		Number fFin = reader.getRevisionNumberForDate(fechaFin);
		
		List queryProducto = reader.createQuery()
			    .forRevisionsOfEntity(Producto.class, false,true)
			    .addOrder(AuditEntity.revisionNumber().desc())
			    .add(AuditEntity.relatedId("usuario").eq(idUsuario))  
			    .add(AuditEntity.revisionNumber().between(fInicio, fFin))
			    .getResultList();		
		return queryProducto;
		
	}
	
	
	/* Obtiene una lista con los productos y sus históricos de un usuario
	 * dentro específicamente para un almacén
	 * */
	public List getHistoricoProdPorUsuarioEnAlmacen(int idUsuario, int idAlmacen) {
		AuditReader reader = AuditReaderFactory.get(em);
				
		List queryProducto = reader.createQuery()
			    .forRevisionsOfEntity(Producto.class, false,true)
			    .addOrder(AuditEntity.revisionNumber().desc())
			    .add(AuditEntity.relatedId("usuario").eq(idUsuario))  
			    .add(AuditEntity.relatedId("almacen").eq(idAlmacen))
			    .getResultList();		
		return queryProducto;
		
	}
	
	
	
	/* Paso un id de un producto y obtengo el histórico de 
	 * ese producto. Es decir, obengo una lista de productos
	 * con todas sus versiones.
	 * SI SE USA HAY QUE REVISAR ESTO - getHistoricoProdPorUsuario
	 */
	public List<Producto> getHistoricoProdPorId(int idProd){
		AuditReader reader = AuditReaderFactory.get(em);
		List query1 = reader.createQuery()
			    .forRevisionsOfEntity(Producto.class, false,true)
			    .add(AuditEntity.id().eq(idProd))   
			    .getResultList();	
		List<Producto> listaProd = new ArrayList<Producto>();		
		if (query1!=null){	
			for (int i=0; i<query1.size();i++){
				Object[] objArray = (Object[])query1.get(i);
				Producto prod = (Producto)objArray[0];
				//System.out.println("Hist"+i+": "+prod.getNombre() + " - id: "+prod.getIdProducto());//.getNombre()+ " "+prueba.getDescripcion());	
				listaProd.add(prod);
				}
			
		}
		return listaProd;
	}
	
	/* Paso un id de un producto y obtengo el histórico de 
	 * las MODIFICACIONES de ese producto. (sólo las modificaciones)
	 * SI SE USA HAY QUE REVISAR ESTO - getHistoricoProdPorUsuario
	 */
	public List<Producto> getHistoricoModificacionesProdPorId(int idProd){
		AuditReader reader = AuditReaderFactory.get(em);
		List query1 = reader.createQuery()
			    .forRevisionsOfEntity(Producto.class, false,true)
			    .add(AuditEntity.id().eq(idProd)) 
			    .add(AuditEntity.revisionType().eq(RevisionType.MOD))
			    .getResultList();		
		List<Producto> listaProd = new ArrayList<Producto>();	
		if (query1!=null){	
			for (int i=0; i<query1.size();i++){
				Object[] objArray = (Object[])query1.get(i);
				Producto prod = (Producto)objArray[0];
				//System.out.println("Lista Productos Mod "+i+": "+prod.getNombre() + " - stock: "+prod.getStock());//.getNombre()+ " "+prueba.getDescripcion());	
				listaProd.add(prod);
				}		
		}
		return listaProd;
	}
	
	/* Paso un id de un producto y obtengo el producto en su
	 * estado inicial cuando se dió de alta. 
	 * SI SE USA HAY QUE REVISAR ESTO - getHistoricoProdPorUsuario
	 */
	public Producto getHistoricoInicialProdPorId(int idProd){
		AuditReader reader = AuditReaderFactory.get(em);
		Object query2 = reader.createQuery()
			    .forRevisionsOfEntity(Producto.class, false,true)
			    .add(AuditEntity.id().eq(idProd)) 
			    .add(AuditEntity.revisionType().eq(RevisionType.ADD))
			    .getSingleResult();	
		Object[] objArray1 = (Object[])query2;
		Producto primerProd = (Producto)objArray1[0];
		System.out.println("Lista Productos Mod A: " +primerProd.getNombre() + " - stock: "+primerProd.getStock());//.getNombre()+ " "+prueba.getDescripcion());	
		
		return primerProd;
	
	}


	

}
