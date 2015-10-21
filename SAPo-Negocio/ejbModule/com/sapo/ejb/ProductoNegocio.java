package com.sapo.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.datatypes.DataUsuario;
import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.ProductoDAO;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.Imagen;
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

    public DataProducto getProductoPorId(int idProducto){
    	DataProducto dataProducto = new DataProducto();
    	Producto producto = this.productoDAO.getProducto(idProducto);
    	
    	dataProducto.setIdProducto(producto.getIdProducto());
    	dataProducto.setAtributos(producto.getAtributos());
    	dataProducto.setDescripcion(producto.getDescripcion());
    	dataProducto.setNombre(producto.getNombre());
    	dataProducto.setPrecio(producto.getPrecio());
    	dataProducto.setStock(producto.getStock());
    	dataProducto.setFotos(toDataImagen(producto.getFoto()));
    	
    	return dataProducto;
    }
    
    //TODO función auxiliar q se usa bastante. Pasar a paquete utils.
    public List<DataImagen> toDataImagen(List<Imagen> imagenes){
		List<DataImagen> dataImagenes = new ArrayList<DataImagen>();
		for (int i = 0; i < imagenes.size(); i++) {
			DataImagen dataImg = new DataImagen();
			dataImg.setIdImagen(imagenes.get(i).getIdImagen());
			dataImg.setNombre(imagenes.get(i).getNombre());
			dataImg.setMime(imagenes.get(i).getMime());
			dataImg.setDatos(imagenes.get(i).getDatos());
			dataImagenes.add(dataImg);
		}
		return dataImagenes;
	}
}
