package com.sapo.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataAlmacen;
import com.datatypes.DataCategoria;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.AlmacenIdealDAO;
import com.sapo.dao.CategoriaDAO;
import com.sapo.dao.ImagenDAO;
import com.sapo.dao.ProductoDAO;
import com.sapo.dao.ProductoGenericoDAO;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.AlmacenIdeal;
import com.sapo.entidades.Categoria;
import com.sapo.entidades.Imagen;
import com.sapo.entidades.Producto;
import com.sapo.entidades.ProductoGenerico;
import com.sapo.utils.Fabrica;

/**
 * Session Bean implementation class ProductoNegocio
 */
@Stateless
@LocalBean
public class ProductoNegocio {

	@EJB
	private ProductoDAO productoDAO;
	@EJB
	private ProductoGenericoDAO productoGenericoDAO;
	@EJB
	private UsuarioDAO usuarioDAO;
	@EJB
	private AlmacenDAO almacenDAO;
	@EJB
	private AlmacenIdealDAO almacenIdealDAO;
	@EJB
	private CategoriaDAO categoriaDAO;
	@EJB
	private ImagenDAO imagenDAO;

	private Producto producto;

	public ProductoNegocio() {
		producto = new Producto();
	}

	public boolean altaProductoGenerico(DataProducto productoData,
			DataCategoria categoria) {
		boolean altaOK = false;
		ProductoGenerico productoGenerico = new ProductoGenerico();

		productoGenerico.setNombre(productoData.getNombre());
		productoGenerico.setDescripcion(productoData.getDescripcion());
		productoGenerico.setEstaActivo(productoData.isEstaActivo());
		productoGenerico.setFechaAlta(new Date());
		productoGenerico.setAtributos(productoData.getAtributos());

		Categoria catGuardar;
		if (categoria.getNombre() == null) {
			catGuardar = this.categoriaDAO.getCategoria(categoria
					.getIdCategoria());
		} else {
			catGuardar = new Categoria();
			catGuardar.setNombre(categoria.getNombre());
			catGuardar.setEsGenerica(true);
		}
		productoGenerico.setCategoria(catGuardar);

		List<Imagen> imgs = new ArrayList<Imagen>();
		for (int i = 0; i < productoData.getFotos().size(); i++) {
			Imagen img = new Imagen();
			img.setDatos(productoData.getFotos().get(i).getDatos());
			imgs.add(img);
		}
		productoGenerico.setFoto(imgs.get(0));// Se puede cambiar para guardar
												// m�s de na foto.

		try {
			this.productoGenericoDAO.insertarProductoGenerico(productoGenerico);
			altaOK = true;
		} catch (Exception e) {

		}

		return altaOK;
	}

	public DataProducto getProductoPorId(int idProducto) {
		DataProducto dataProducto = new DataProducto();
		Producto producto = this.productoDAO.getProducto(idProducto);

		dataProducto.setIdProducto(producto.getIdProducto());
		dataProducto.setAtributos(producto.getAtributos());
		dataProducto.setDescripcion(producto.getDescripcion());
		dataProducto.setNombre(producto.getNombre());
		dataProducto.setPrecio(producto.getPrecio());
		dataProducto.setStock(producto.getStock());
		dataProducto.setStockIdeal(producto.getStockIdeal());
		//dataProducto.setIdUsuario(producto.getUsuario().getIdUsuario());
		dataProducto.setFotos(toDataImagen(producto.getFoto()));

		return dataProducto;
	}

	// TODO funci�n auxiliar q se usa bastante. Pasar a paquete utils.
	public List<DataImagen> toDataImagen(List<Imagen> imagenes) {
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

	public List<DataProducto> getProductosGenericos() {
		List<DataProducto> productosGenericos = new ArrayList<DataProducto>();
		Fabrica fabrica = new Fabrica();
		productosGenericos = fabrica.toDataProducto(productoGenericoDAO
				.getProductosGenericos());
		return productosGenericos;
	}

	public DataProducto getProductoGenericoPorId(int idProductoGenerico) {
		DataProducto dataProducto = new DataProducto();
		ProductoGenerico productoGenerico = this.productoGenericoDAO
				.getProductoGenerico(idProductoGenerico);

		dataProducto.setIdProducto(productoGenerico.getIdProductoGenerico());
		dataProducto.setAtributos(productoGenerico.getAtributos());
		dataProducto.setDescripcion(productoGenerico.getDescripcion());
		dataProducto.setNombre(productoGenerico.getNombre());
		dataProducto.setPrecio(productoGenerico.getPrecio());
		List<DataImagen> imgs = new ArrayList<DataImagen>();
		DataImagen i = new DataImagen();
		if (productoGenerico.getFoto() != null) {
			i.setIdImagen(productoGenerico.getFoto().getIdImagen());
			i.setDatos(productoGenerico.getFoto().getDatos());
			i.setMime(productoGenerico.getFoto().getMime());
			i.setNombre(productoGenerico.getFoto().getNombre());
		} else {
			i.setIdImagen(1);
		}
		imgs.add(i);
		dataProducto.setFotos(imgs);
		dataProducto.setIdCategoria(productoGenerico.getCategoria()
				.getIdCategoria());
		dataProducto.setNombreCategoria(productoGenerico.getCategoria()
				.getNombre());
		return dataProducto;
	}

	public void eliminarProductoGenerico(int idProductoGenerico) {
		this.productoGenericoDAO.eliminarProductoGenerico(idProductoGenerico);
	}
	
	public void eliminarProducto(int idProducto) {
		this.productoDAO.eliminarProducto(idProducto);
	}
	
	public void eliminarProductoAlmacenIdeal(int idProducto, int idAlmacenIdeal) {
		AlmacenIdeal ai = this.almacenIdealDAO.getAlmacenIdeal(idAlmacenIdeal);
		Producto p = this.productoDAO.getProducto(idProducto);
		ai.getProductos().remove(p);
		this.almacenIdealDAO.actualizarAlmacenIdeal(ai);
		p.setEstaActivo(false);
		if(p.isEsIdeal()){
			this.productoDAO.deleteProducto(idProducto);
		}
	}

	public void modificarProductoGenerico(DataProducto dataProducto,
			DataCategoria dataCategoria) {

		ProductoGenerico pg = this.productoGenericoDAO
				.getProductoGenerico(dataProducto.getIdProducto());

		Categoria cat = this.categoriaDAO.getCategoria(dataCategoria
				.getIdCategoria());
		pg.setCategoria(cat);

		pg.setAtributos(dataProducto.getAtributos());
		pg.setNombre(dataProducto.getNombre());
		pg.setDescripcion(dataProducto.getDescripcion());
		pg.setEstaActivo(true);
		pg.setPrecio(dataProducto.getPrecio());

		List<Imagen> imgs = new ArrayList<Imagen>();
		for (int i = 0; i < dataProducto.getFotos().size(); i++) {
			Imagen img = new Imagen();
			img.setDatos(dataProducto.getFotos().get(i).getDatos());
			img.setNombre(dataProducto.getFotos().get(i).getNombre());
			img.setIdImagen(dataProducto.getFotos().get(i).getIdImagen());
			img.setMime(dataProducto.getFotos().get(i).getMime());
			imgs.add(img);
		}
		if (imgs.get(0) != null && imgs.get(0).getDatos() == null) {
			Imagen i = this.imagenDAO.getImagen(imgs.get(0).getIdImagen());
			pg.setFoto(i);
		} else {
			pg.setFoto(imgs.get(0));
		}

		this.productoGenericoDAO.actualizarProductoGenerico(pg);

	}

	public void altaProductoDesdePlantilla(DataProducto dataProducto,
			DataAlmacen dataAlmacen) {

		Producto p = new Producto();
		ProductoGenerico pg = this.productoGenericoDAO
				.getProductoGenerico(dataProducto.getIdProductoGenerico());
		p.setProductoGenerico(pg);
		Categoria cat = this.categoriaDAO.getCategoria(dataProducto
				.getIdCategoria());
		p.setCategoria(cat);
		Almacen a = new Almacen();
		a = this.almacenDAO.getAlmacen(dataAlmacen.getIdAlmacen());
		p.setAlmacen(a);

		p.setAtributos(dataProducto.getAtributos());
		p.setNombre(dataProducto.getNombre());
		p.setDescripcion(dataProducto.getDescripcion());
		p.setEstaActivo(true);
		p.setPrecio(dataProducto.getPrecio());
		p.setFechaAlta(new Date());
		p.setStock(dataProducto.getStock());

		List<Imagen> imgs = new ArrayList<Imagen>();
		for (int i = 0; i < dataProducto.getFotos().size(); i++) {
			Imagen img = new Imagen();
			img.setDatos(dataProducto.getFotos().get(i).getDatos());
			imgs.add(img);
		}
		p.setFoto(imgs);

		this.productoDAO.insertarProducto(p);
	}

	public void actualizarStock(int idProducto, int stock) {
		Producto p = new Producto();
		if (stock >= 0) {
			p = this.productoDAO.getProducto(idProducto);
			p.setStock(stock);
			this.productoDAO.actualizarProducto(p);
		}
	}
	
	public void actualizarStockIdeal(int idProducto, int stockIdeal) {
		Producto p = new Producto();
		if (stockIdeal >= 0) {
			p = this.productoDAO.getProducto(idProducto);
			p.setStockIdeal(stockIdeal);
			this.productoDAO.actualizarProducto(p);
		}
	}
}
