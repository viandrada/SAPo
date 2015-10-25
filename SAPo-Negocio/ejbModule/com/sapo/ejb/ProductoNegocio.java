package com.sapo.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataCategoria;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.CategoriaDAO;
import com.sapo.dao.ProductoDAO;
import com.sapo.dao.ProductoGenericoDAO;
import com.sapo.dao.UsuarioDAO;
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
	private CategoriaDAO categoriaDAO;

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
		this.producto.setFoto(imgs);

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
		dataProducto.setFotos(toDataImagen(producto.getFoto()));

		return dataProducto;
	}

	// TODO función auxiliar q se usa bastante. Pasar a paquete utils.
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
}
