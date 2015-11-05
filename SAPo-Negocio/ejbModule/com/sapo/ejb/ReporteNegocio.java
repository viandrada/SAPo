package com.sapo.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;

import com.datatypes.DataAlmacen;
import com.datatypes.DataUsuario;
import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.ProductoDAO;
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
	private ProductoDAO productoDAO;
	
	
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
	 * Por ahora es VOID pero se puede cambiar
	 */
	public void buscarHistoricoAlmacenesPorUsuario(int idUsuario){
		
		List<Almacen> listaAlm = this.almacenDAO.getHistoricoAlmacenesPorUsuario(idUsuario);
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
	
	
	/* Paso un id de usuario y obtengo el histórico de productos
	 * de ese usuario.
	 * POR AHORA VOID, PUEDE CAMBIAR
	 * */
	public void buscarHistoricoProdPorUsuario(int idUsuario){
		
		List<Producto> listaProd = this.productoDAO.getHistoricoProdPorUsuario(idUsuario);
	}
	

}
