package com.sapo.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.CategoriaDAO;
import com.sapo.dao.ProductoDAO;
import com.sapo.dao.UsuarioDAO;
import com.datatypes.DataAlmacen;
import com.datatypes.DataCategoria;
import com.datatypes.DataProducto;
import com.datatypes.DataUsuario;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.Categoria;
import com.sapo.entidades.Imagen;
import com.sapo.entidades.Producto;
import com.sapo.entidades.Usuario;

/**
 * Session Bean implementation class AlmacenNegocio
 */
@Stateless
@LocalBean
public class AlmacenNegocio {

	/**
	 * Default constructor.
	 */
	public AlmacenNegocio() {
		this.almacen = new Almacen();
	}

	@EJB
	private AlmacenDAO almacenDAO;
	@EJB
	private UsuarioDAO usuarioDAO;
	@EJB
	private ProductoDAO productoDAO;
	@EJB
	private CategoriaDAO categoriaDAO;

	private Almacen almacen;

	public int altaAlmacen(DataAlmacen almacen, DataUsuario usuario) {
		int idAlmacenGenerado = 0;
		Usuario usr;
		Imagen img = new Imagen();

		img.setDatos(almacen.getFoto());
		usr = this.usuarioDAO.getUsuarioPorEmail(usuario.getEmail());

		this.almacen.setNombre(almacen.getNombre());
		this.almacen.setDescripcion(almacen.getDescripcion());
		this.almacen.setEstaActivo(true);
		this.almacen.setFechaAlta(new Date());
		this.almacen.setPropietario(usr);
		this.almacen.setFoto(img);

		try {
			idAlmacenGenerado = this.almacenDAO.insertarAlmacen(this.almacen);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return idAlmacenGenerado;

	}

	public List<DataAlmacen> getAlmacenes(String emailUsr) {

		List<DataAlmacen> listaDataAlmacen = new ArrayList<DataAlmacen>();
		List<Almacen> listaAlmacenes = new ArrayList<Almacen>();

		try {
			listaAlmacenes = this.almacenDAO.getAlmacenesUsuario(emailUsr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		for (int i = 0; i < listaAlmacenes.size(); i++) {
			DataAlmacen dataAlmacen = new DataAlmacen();
			dataAlmacen.setNombre(listaAlmacenes.get(i).getNombre());
			dataAlmacen.setDescripcion(listaAlmacenes.get(i).getDescripcion());
			dataAlmacen.setFoto(listaAlmacenes.get(i).getFoto().getDatos());
			dataAlmacen.setIdAlmacen(listaAlmacenes.get(i).getIdAlmacen());
			listaDataAlmacen.add(dataAlmacen);
		}
		return listaDataAlmacen;
	}

	public DataAlmacen getAlmacenPorId(int idAlmacen) {
		DataAlmacen dataAlmacen = new DataAlmacen();
		Almacen almacen = this.almacenDAO.getAlmacenPorId(idAlmacen);

		dataAlmacen.setNombre(almacen.getNombre());
		dataAlmacen.setDescripcion(almacen.getDescripcion());
		dataAlmacen.setFoto(almacen.getFoto().getDatos());
		dataAlmacen.setProductos(toDataProductos(almacen.getProductos()));
		return dataAlmacen;
	}

	public boolean altaProducto(DataProducto producto, DataAlmacen almacen,
			DataCategoria categoria) {
		boolean altaOK = false;
		Producto productoGuardar = new Producto();
		Almacen almacenGuardar;// = new Almacen(almacen.getIdAlmacen());
		almacenGuardar = this.almacenDAO.getAlmacen(almacen.getIdAlmacen());
		Categoria catGuardar;// = new Categoria(categoria.getIdCategoria());
		catGuardar = this.categoriaDAO.getCategoria(categoria.getIdCategoria());
		
		productoGuardar.setNombre(producto.getNombre());
		productoGuardar.setDescripcion(producto.getDescripcion());
		productoGuardar.setEstaActivo(true);
		productoGuardar.setPrecio(producto.getPrecio());
		productoGuardar.setAlmacen(almacenGuardar);
		productoGuardar.setCategoria(catGuardar);
		
		try {
			this.productoDAO.insertarProducto(productoGuardar);
			altaOK = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return altaOK;
	}
	
	public List<DataProducto> getProductosDeAlmacen(int idAlmacen){
		List<DataProducto> dataProductos = new ArrayList<DataProducto>();
		List<Producto> productos = this.productoDAO.getProductosAlmacen(idAlmacen);
		dataProductos = this.toDataProductos(productos);
		return dataProductos;
	}

	public List<DataProducto> toDataProductos(List<Producto> productos) {
		List<DataProducto> dataProductos = new ArrayList<DataProducto>();
		for (int i = 0; i < productos.size(); i++) {
			DataProducto dataProducto = new DataProducto();
			dataProducto.setNombre(productos.get(i).getNombre());
			dataProducto.setDescripcion(productos.get(i).getDescripcion());
			dataProducto.setPrecio(productos.get(i).getPrecio());
			// TODO Agregar mas campos al data (imagenes y atributos)
			dataProductos.add(dataProducto);
		}
		return dataProductos;
	}
}
