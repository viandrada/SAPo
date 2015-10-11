package com.sapo.ejb;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataProducto;
import com.datatypes.DataUsuario;
import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.ProductoDAO;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.Producto;
import com.sapo.entidades.Usuario;

/**
 * Session Bean implementation class ProductoNegocio
 */
@Stateless
@LocalBean
public class ProductoNegocio {

	@EJB
	private ProductoDAO productoDAO;
	
	@EJB
	private UsuarioDAO usuarioDAO;
	
	@EJB
	private AlmacenDAO almacenDAO;
	
	private Producto producto;

		
    public ProductoNegocio() {
        producto = new Producto();
    }
    
    
    public boolean altaProductoGenerico(DataProducto productoData, DataUsuario usuario, int idAlmacen ){
    	boolean altaOK = false;
    	Usuario user;
    	user = this.usuarioDAO.getUsuarioPorEmail(usuario.getEmail());
    	Almacen almacen;
    	//FALTA CREAR getAlmacenPorID en AlmacenDAO
    	// almacen = this.almacenDAO.getAlmacenPorID(idAlmacen);
    	
    	this.producto.setNombre(productoData.getNombre());
    	this.producto.setDescripcion(productoData.getDescripcion());
    	this.producto.setEstaActivo(productoData.isEstaActivo());
    	this.producto.setFechaAlta(new Date());
    	//FALTA this.productoData.setFoto(...);
    	//FALTA this.productoData.setAlmacenes(almacen);
    	this.producto.setPrecio(productoData.getPrecio());
    	
    	try {
    		this.productoDAO.insertarProducto(producto);
    		altaOK=true;
    	} catch (Exception e){
    		
    	}
    	
	
    	return altaOK;
    }

}
