package com.sapo.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;

import com.datatypes.DataAlmacen;
import com.datatypes.DataReporteAlmacen;
import com.datatypes.DataReporteProducto;
import com.datatypes.DataUsuario;
import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.AlmacenIdealDAO;
import com.sapo.dao.ProductoDAO;
import com.sapo.dao.ProductoGenericoDAO;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.Producto;
import com.sapo.entidades.Usuario;
import com.sapo.utils.Fabrica;


@Stateless
@LocalBean
public class ReporteNegocio {

	/**
	 * Default constructor.
	 */
	public ReporteNegocio() {
		
	}

	@EJB
	private UsuarioDAO usuarioDAO;
	
	@EJB
	private AlmacenDAO almacenDAO;
	
	@EJB
	private ProductoGenericoDAO prodGenericoDAO;
	
	@EJB
	private ProductoDAO productoDAO;
	
	@EJB
	private Fabrica fabrica;
	
	
	public float ganancias(){
		
		Fabrica f = new Fabrica();
		List<DataUsuario> dataUsrLista = new ArrayList<DataUsuario>();
		dataUsrLista = f.convertirUsu(this.usuarioDAO.getUsuarios());
		
		float gana = 0;
		
		for (int i = 0; i < dataUsrLista.size(); i++) {
			
			if  (dataUsrLista.get(i).isPremium()){
				
				gana = gana + dataUsrLista.get(i).getMonto();
			}
		};
		return gana;
	}
	
	
	/*
	 * Devuelve una lista con todos los historicos de Almacenes de un usuario
	 * particular.
	 */
	public List<DataReporteAlmacen> buscarHistoricoAlmacenesPorUsuario(int idUsuario){	
		List listaAlm = this.almacenDAO.getHistoricoAlmacenesPorUsuario(idUsuario);
		List<DataReporteAlmacen> listaRepAlm = new ArrayList<DataReporteAlmacen>();	
		if (listaAlm!=null){	
			for (int i=0; i<listaAlm.size();i++){
				Object[] objArray = (Object[])listaAlm.get(i);
				Almacen alm = (Almacen)objArray[0];
				String tipoMov = objArray[2].toString();
				DefaultRevisionEntity info = (DefaultRevisionEntity) objArray[1];
				DataReporteAlmacen dataRepAlm = this.fabrica.toDataReporteAlmacen(alm, tipoMov, info.getRevisionDate());
				System.out.println("Hist Alm x Usr "+i+": "+alm.getNombre() + " - email: "+alm.getPropietario().getEmail()+
						" TipoMov "+ tipoMov);	
				listaRepAlm.add(dataRepAlm);
				}
		}
		return  listaRepAlm;
	}

	
	/* Paso un id de usuario y obtengo el histórico de productos
	 * de ese usuario.
	 * */
	public  List<DataReporteProducto>  buscarHistoricoProdPorUsuario(int idUsuario){
		List listaProd = this.productoDAO.getHistoricoProdPorUsuario(idUsuario);
		List<DataReporteProducto> listaRepProd = new ArrayList<DataReporteProducto>();
		if (listaProd!=null){	
			for (int i=0; i<listaProd.size();i++){
				Object[] objArray = (Object[])listaProd.get(i);
				Producto prod = (Producto)objArray[0];
				DefaultRevisionEntity info = (DefaultRevisionEntity) objArray[1];
				String tipoMov = objArray[2].toString();
				DataReporteProducto dataRepProd = this.fabrica.toDataReporteProducto(prod, tipoMov, info.getRevisionDate());
				System.out.println("Hist Prod x Usr "+i+": "+prod.getNombre() + " - id: "+prod.getIdProducto()+
						" TipoMov "+ tipoMov);
				listaRepProd.add(dataRepProd);
				}		
		}	
		return listaRepProd;
	}
	
	
	/* Paso un id de almacén y obtengo el histórico de productos 
	 * que han cambiado de stock 
	 * */
	public  List<DataReporteProducto>  buscarHistoricoCambioStockProdPorAlmacen(int idAlmacen){
		List listaProd = this.productoDAO.getHistoricoCambioStockProdPorAlmacen(idAlmacen);
		List<DataReporteProducto> listaRepProd = new ArrayList<DataReporteProducto>();
		if (listaProd!=null){	
			for (int i=0; i<listaProd.size();i++){
				Object[] objArray = (Object[])listaProd.get(i);
				Producto prod = (Producto)objArray[0];
				String tipoMov = objArray[2].toString();
				DefaultRevisionEntity info = (DefaultRevisionEntity) objArray[1];
				DataReporteProducto dataRepProd = this.fabrica.toDataReporteProducto(prod, tipoMov, info.getRevisionDate());
				System.out.println("Hist Stock Alm x Usr "+i+": "+prod.getNombre() + " - Stock: "+prod.getStock()+
						" TipoMov "+ tipoMov + " Fecha " + info.getRevisionDate().toString());
				listaRepProd.add(dataRepProd);
				}		
		}	
		return listaRepProd;
	}
	
	/*Devuelve los históricos de productos de un usuario entre determinadas fechas
	 * */
	public List<DataReporteProducto>  buscarHistoricoProdPorUsuarioEnFecha(int idUsuario, Date fInicio, Date fFin){
		List listaProd = this.productoDAO.getHistoricoProdPorUsuarioEnFecha(idUsuario, fInicio, fFin);
		List<DataReporteProducto> listaRepProd = new ArrayList<DataReporteProducto>();
		if (listaProd!=null){	
			for (int i=0; i<listaProd.size();i++){
				Object[] objArray = (Object[])listaProd.get(i);
				Producto prod = (Producto)objArray[0];
				String tipoMov = objArray[2].toString();
				DefaultRevisionEntity info = (DefaultRevisionEntity) objArray[1];
				DataReporteProducto dataRepProd = this.fabrica.toDataReporteProducto(prod, tipoMov, info.getRevisionDate());
				System.out.println("Hist Prod en Fecha x Usu "+i+": "+prod.getNombre() + " - Stock: "+prod.getStock()+
						" TipoMov "+ tipoMov);
				listaRepProd.add(dataRepProd);
			}		
		}	
		return listaRepProd;
	}
	
	/*Devuelve los históricos de productos de un usuario para un almacén determinado
	 * */
	public List<DataReporteProducto> buscarHistoricoProdPorUsuarioYAlmacen(int idUsuario, int idAlmacen){
		List listaProd = this.productoDAO.getHistoricoProdPorUsuarioEnAlmacen(idUsuario, idAlmacen);
		List<DataReporteProducto> listaRepProd = new ArrayList<DataReporteProducto>();
		if (listaProd!=null){	
			for (int i=0; i<listaProd.size();i++){
				Object[] objArray = (Object[])listaProd.get(i);
				Producto prod = (Producto)objArray[0];
				String tipoMov = objArray[2].toString();
				DefaultRevisionEntity info = (DefaultRevisionEntity) objArray[1];
				DataReporteProducto dataRepProd = this.fabrica.toDataReporteProducto(prod, tipoMov, info.getRevisionDate());
				System.out.println("Hist Prod por usr y alm "+i+": "+prod.getNombre() + " - Stock: "+prod.getStock()+
						" TipoMov "+ tipoMov);
				listaRepProd.add(dataRepProd);
			}		
		}	
		return listaRepProd;
	}
	
	
	/* Obtengo una lista con los prductos genéricos utilizados más veces
	 * */
	public  List<DataReporteProducto>  buscarProductosGenericosMasUtilizados(){
		List listaProd = this.prodGenericoDAO.getProductosGenericoMasUsados();
		List<DataReporteProducto> listaRepProd = new ArrayList<DataReporteProducto>();
		if (listaProd!=null){	
			for (int i=0; i<listaProd.size();i++){
				Object obj = (Object)listaProd.get(i);
//				Producto prod = (Producto)objArray[0];
//				DefaultRevisionEntity info = (DefaultRevisionEntity) objArray[1];
//				String tipoMov = objArray[2].toString();
//				DataReporteProducto dataRepProd = this.fabrica.toDataReporteProducto(prod, tipoMov, info.getRevisionDate());
//				System.out.println("Prod Generico + Usado "+i+": "+prod.getNombre() + " - id: "+prod.getIdProducto()+
//						" Id Prod Gen "+ prod.getProductoGenerico().getIdProductoGenerico());
//				listaRepProd.add(dataRepProd);
				System.out.println("prod gen más usado" + i + " " + obj.toString());
				}		
		}	
		return listaRepProd;
	}
	
	

	/*Paso un id de un producto y obtengo el histórico de 
	 * ese producto en forma de lista de producto.
	 * POR AHORA VOID, PUEDE CAMBIAR
	 */
	public void buscarHistoricoProdPorId (int idProducto){
		
		List<Producto> listaProd = this.productoDAO.getHistoricoProdPorId(idProducto);
		
	}
	
	/*Paso un id de un producto y obtengo el histórico de 
	 * ese producto (sólo las modificaciones)
	 * POR AHORA VOID, PUEDE CAMBIAR
	 */
	public void buscarHistoricoModificacionesProdPorId (int idProducto){
		
		List<Producto> listaProd = this.productoDAO.getHistoricoModificacionesProdPorId(idProducto);
		
	}
	

	

}
