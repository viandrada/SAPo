package com.sapo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
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
				.createNamedQuery("Productos.getProductosActivosDeAlmacen.IdAlmacen");
		consulta.setParameter("idAlmacen", idAlmacen);
		List<Producto> productos = (List<Producto>) consulta.getResultList();
		return productos;
	}

	public List<Producto> getProductosActivosAlmacen(int idAlmacen) {
		Query consulta = this.em
				.createNamedQuery("Productos.getProductosActivosDeAlmacen.IdAlmacen");
		consulta.setParameter("idAlmacen", idAlmacen);
		List<Producto> productos = (List<Producto>) consulta.getResultList();
		return productos;
	}

	public List<Producto> getProductosActivosAlmacenConAtributo(int idAlmacen,
			String atributos) {
		
		/*System.out.println("ENTRE AL GET PRODUCTOS ACTIVOS ALMACEN: "
				+ idAlmacen + " CON ATRIBUTO:  " + atributos
				+ "  de ProductoDAO");*/
		
		
		// Query consulta =
		// em.createQuery("SELECT p FROM Producto p WHERE"+" p.almacen.idAlmacen = :idAlmacen and p.esIdeal = FALSE and p.estaActivo = TRUE"+" and p.atributos LIKE :atributos");

		// ESTA NO REVIENTA Query consulta =
		// em.createQuery("SELECT p FROM Producto p WHERE p.atributos LIKE :atributos");

		Query consulta = em
				.createQuery("SELECT p FROM Producto p WHERE p.almacen.idAlmacen = :idAlmacen and p.atributos LIKE :atributos and p.esIdeal = FALSE and p.estaActivo = TRUE");

		// .createQuery("SELECT p FROM Producto p WHERE"
		// +
		// "p.almacen.idAlmacen = :idAlmacen and p.esIdeal = FALSE and p.estaActivo = TRUE"
		// + "and p.atributos LIKE :atributos");

		consulta.setParameter("idAlmacen", idAlmacen);
		consulta.setParameter("atributos", "%" + atributos + "%");

		
		List<Producto> productos = (List<Producto>) consulta.getResultList();

		/*if (!productos.isEmpty()) {
			for (Producto p : productos) {
				System.out.println("Producto de get LIKE NOMBRE: "
						+ p.getNombre() );

			}
		}else {System.out.println(" get de la consulta NO HAY PRODUCTOS CON ATRIBUTO: "+ atributos +" ProductoDAO");}*/

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

	/*
	 * Obtiene una lista con los productos y sus hist�ricos de un usuario.
	 */
	public List getHistoricoProdPorUsuario(int idUsuario) {

		AuditReader reader = AuditReaderFactory.get(em);
		List queryProducto = reader.createQuery()
				.forRevisionsOfEntity(Producto.class, false, true)
				.add(AuditEntity.relatedId("usuario").eq(idUsuario))
				.getResultList();

		return queryProducto;

	}

	/*
	 * Obtiene una lista con los productos y sus hist�ricos de un usuario.
	 */
	public List getHistoricoCambioStockProdPorAlmacen(int idAlmacen) {

		AuditReader reader = AuditReaderFactory.get(em);
		List queryProducto = reader.createQuery()
				.forRevisionsOfEntity(Producto.class, false, true)
				.addOrder(AuditEntity.revisionNumber().desc())
				.add(AuditEntity.relatedId("almacen").eq(idAlmacen))
				.add(AuditEntity.property("stock").hasChanged())
				.setMaxResults(5).getResultList();

		return queryProducto;

	}

	/*
	 * Obtiene una lista con los productos y sus hist�ricos de un usuario dentro
	 * de un lapso de tiempo.
	 */
	public List getHistoricoProdPorUsuarioEnFecha(int idUsuario,
			Date fechaInicio, Date fechaFin) {

		AuditReader reader = AuditReaderFactory.get(em);
		Number fInicio = 1;
		try {
			fInicio = reader.getRevisionNumberForDate(fechaInicio);
		} catch (Exception e) {
			System.out.println("Error en fecha de revisión, se utiliza"
					+ "la primer revisión");
		}
		
		Number fFin = reader.getRevisionNumberForDate(fechaFin);

		List queryProducto = reader.createQuery()
				.forRevisionsOfEntity(Producto.class, false, true)
				.addOrder(AuditEntity.revisionNumber().desc())
				.add(AuditEntity.relatedId("usuario").eq(idUsuario))
				.add(AuditEntity.revisionNumber().between(fInicio, fFin))
				.getResultList();
		return queryProducto;

	}

	/*
	 * Obtiene una lista con los productos y sus hist�ricos de un usuario dentro
	 * espec�ficamente para un almac�n
	 */
	public List getHistoricoProdPorUsuarioEnAlmacen(int idUsuario, int idAlmacen) {
		AuditReader reader = AuditReaderFactory.get(em);

		List queryProducto = reader.createQuery()
				.forRevisionsOfEntity(Producto.class, false, true)
				.addOrder(AuditEntity.revisionNumber().desc())
				.add(AuditEntity.relatedId("usuario").eq(idUsuario))
				.add(AuditEntity.relatedId("almacen").eq(idAlmacen))
				.getResultList();
		return queryProducto;

	}

	/*
	 * Paso un id de un producto y obtengo el hist�rico de ese producto. Es
	 * decir, obengo una lista de productos con todas sus versiones. SI SE USA
	 * HAY QUE REVISAR ESTO - getHistoricoProdPorUsuario
	 */
	public List<Producto> getHistoricoProdPorId(int idProd) {
		AuditReader reader = AuditReaderFactory.get(em);
		List query1 = reader.createQuery()
				.forRevisionsOfEntity(Producto.class, false, true)
				.add(AuditEntity.id().eq(idProd)).getResultList();
		List<Producto> listaProd = new ArrayList<Producto>();
		if (query1 != null) {
			for (int i = 0; i < query1.size(); i++) {
				Object[] objArray = (Object[]) query1.get(i);
				Producto prod = (Producto) objArray[0];
				// System.out.println("Hist"+i+": "+prod.getNombre() +
				// " - id: "+prod.getIdProducto());//.getNombre()+
				// " "+prueba.getDescripcion());
				listaProd.add(prod);
			}

		}
		return listaProd;
	}

	/*
	 * Paso un id de un producto y obtengo el hist�rico de las MODIFICACIONES de
	 * ese producto. (s�lo las modificaciones) SI SE USA HAY QUE REVISAR ESTO -
	 * getHistoricoProdPorUsuario
	 */
	public List<Producto> getHistoricoModificacionesProdPorId(int idProd) {
		AuditReader reader = AuditReaderFactory.get(em);
		List query1 = reader.createQuery()
				.forRevisionsOfEntity(Producto.class, false, true)
				.add(AuditEntity.id().eq(idProd))
				.add(AuditEntity.revisionType().eq(RevisionType.MOD))
				.getResultList();
		List<Producto> listaProd = new ArrayList<Producto>();
		if (query1 != null) {
			for (int i = 0; i < query1.size(); i++) {
				Object[] objArray = (Object[]) query1.get(i);
				Producto prod = (Producto) objArray[0];
				// System.out.println("Lista Productos Mod "+i+": "+prod.getNombre()
				// + " - stock: "+prod.getStock());//.getNombre()+
				// " "+prueba.getDescripcion());
				listaProd.add(prod);
			}
		}
		return listaProd;
	}

	/*
	 * Paso un id de un producto y obtengo el producto en su estado inicial
	 * cuando se dió de alta. SI SE USA HAY QUE REVISAR ESTO -
	 * getHistoricoProdPorUsuario
	 */
	public Producto getHistoricoInicialProdPorId(int idProd) {
		AuditReader reader = AuditReaderFactory.get(em);
		Object query2 = reader.createQuery()
				.forRevisionsOfEntity(Producto.class, false, true)
				.add(AuditEntity.id().eq(idProd))
				.add(AuditEntity.revisionType().eq(RevisionType.ADD))
				.getSingleResult();
		Object[] objArray1 = (Object[]) query2;
		Producto primerProd = (Producto) objArray1[0];
		System.out.println("Lista Productos Mod A: " + primerProd.getNombre()
				+ " - stock: " + primerProd.getStock());// .getNombre()+
														// " "+prueba.getDescripcion());

		return primerProd;

	}

	public boolean existeProductoHermano(int idAlmacen, int idHermano) {
		Query consulta = this.em
				.createNamedQuery("Productos.getProductosDeAlmacen.IdHermano");
		consulta.setParameter("idAlmacen", idAlmacen);
		consulta.setParameter("idHermano", idHermano);
		// List<Producto> productos = (List<Producto>) consulta.getResultList();
		return (consulta.getResultList().size() >= 1);
	}

	public Producto getProductoHermano(int idAlmacen, int idHermano) {
		Query consulta = this.em
				.createNamedQuery("Productos.getProductosDeAlmacen.IdHermano");
		consulta.setParameter("idAlmacen", idAlmacen);
		consulta.setParameter("idHermano", idHermano);
		// List<Producto> productos = (List<Producto>) consulta.getResultList();
		return (Producto) consulta.getResultList().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Producto> getProductosHermanos(int idHermano) {
		Query consulta = this.em
				.createNamedQuery("Productos.getProductos.Hermanos");
		consulta.setParameter("idHermano", idHermano);
		// return List<Producto> productos = (List<Producto>)
		// consulta.getResultList();
		// return (Producto) consulta.getResultList().get(0);
		return (List<Producto>) consulta.getResultList();
	}

	public Producto verificarNotificacion(String query) {
		Query consulta = em.createQuery(query);
		List<Producto> pl = (List<Producto>) consulta.getResultList();
		Producto p = new Producto();
		if (!pl.isEmpty()) {
			p = pl.get(0);
		}
		return p;
	}

	public ArrayList<Object> getProductosCandidatosAPromocion() {
		ArrayList<Object> candidatos = new ArrayList<>();
		Query q = em
				.createQuery("select p.nombre, count(p) from Producto p group by p.nombre having count(p) > 1 order by count(p) desc");
		candidatos = (ArrayList<Object>) q.getResultList();
		return candidatos;
	}
}
