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
import com.datatypes.DataProductoCandidato;
import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.AlmacenIdealDAO;
import com.sapo.dao.CategoriaDAO;
import com.sapo.dao.HistoricoProductoDAO;
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
import com.sapo.entidades.Usuario;
import com.sapo.utils.Fabrica;

import static java.lang.Math.toIntExact;

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
	@EJB
	private HistoricoProductoDAO historicoDAO;
	@EJB
	private Fabrica fabrica;

	public ProductoNegocio() {
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
		dataProducto.setIdCategoria(producto.getCategoria().getIdCategoria());
		dataProducto.setNombreCategoria(producto.getCategoria().getNombre());
		// dataProducto.setIdUsuario(producto.getUsuario().getIdUsuario());
		List<DataImagen> imgs = new ArrayList<DataImagen>();
		for (int i = 0; i < producto.getFoto().size(); i++) {
			DataImagen im = new DataImagen();
			if (producto.getFoto() != null) {
				im.setIdImagen(producto.getFoto().get(i).getIdImagen());
				im.setDatos(producto.getFoto().get(i).getDatos());
				im.setMime(producto.getFoto().get(i).getMime());
				im.setNombre(producto.getFoto().get(i).getNombre());
			} else {
				im.setIdImagen(1);
			}
			imgs.add(im);
		}
		dataProducto.setFotos(imgs);
		dataProducto.setFotos(toDataImagen(producto.getFoto()));

		dataProducto.setIdHermano(producto.getIdHermano());// //////////////////////////////////////////////////

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
		// //////////////////seteo en id hermano del data el id del producto
		// generico////////////////
		dataProducto.setIdHermano(productoGenerico.getIdProductoGenerico());

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
		if (p.isEsIdeal()) {
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
		if (dataProducto.getFotos().get(0).getDatos() != null) {
			for (int i = 0; i < dataProducto.getFotos().size(); i++) {
				Imagen img = new Imagen();
				img.setDatos(dataProducto.getFotos().get(i).getDatos());
				imgs.add(img);
			}
		} else {
			Imagen i = this.imagenDAO.getImagen(dataProducto.getFotos().get(0)
					.getIdImagen());
			imgs.add(i);
		}
		p.setFoto(imgs);

		this.productoDAO.insertarProducto(p);
		// ///////////////////////////////////////////////////////////////////////////////////////
		p.setIdHermano(p.getIdProducto());
		// ////////////////////////////////////////guardo en idHermano el id de
		// el mismo
		this.productoDAO.actualizarProducto(p);

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

	//
	// /*Paso un id de un producto y obtengo el histórico de
	// * ese producto en forma de lista de producto.
	// * POR AHORA VOID, PUEDE CAMBIAR
	// */
	// public void buscarHistoricoProdPorId (int idProducto){
	//
	// List<Producto> listaProd =
	// this.productoDAO.getHistoricoProdPorId(idProducto);
	//
	// }
	//
	// /*Paso un id de un producto y obtengo el histórico de
	// * ese producto (sólo las modificaciones)
	// * POR AHORA VOID, PUEDE CAMBIAR
	// */
	// public void buscarHistoricoModificacionesProdPorId (int idProducto){
	//
	// List<Producto> listaProd =
	// this.productoDAO.getHistoricoModificacionesProdPorId(idProducto);
	//
	// }
	//
	//
	// /* Paso un id de usuario y obtengo el histórico de productos
	// * de ese usuario.
	// * POR AHORA VOID, PUEDE CAMBIAR
	// * */
	// public void buscarHistoricoProdPorUsuario(int idUsuario){
	//
	// List<Producto> listaProd =
	// this.productoDAO.getHistoricoProdPorUsuario(idUsuario);
	// }
	public void modificarProductoEspecifico(DataProducto dataProducto,
			DataCategoria dataCategoria, int idUsuario) {

		Producto pg = this.productoDAO
				.getProducto(dataProducto.getIdProducto());

		Categoria cat = new Categoria();
		if (dataCategoria.getNombre() == null) {
			cat = this.categoriaDAO
					.getCategoria(dataCategoria.getIdCategoria());
		} else {
			cat.setNombre(dataCategoria.getNombre());
			cat.setEsGenerica(false);
			Usuario u = this.usuarioDAO.getUsuarioPorEmail(dataCategoria
					.getEmailUsuario());
			cat.setUsu(u);
		}
		pg.setCategoria(cat);

		pg.setAtributos(dataProducto.getAtributos());
		pg.setNombre(dataProducto.getNombre());
		pg.setDescripcion(dataProducto.getDescripcion());
		pg.setEstaActivo(true);
		pg.setPrecio(dataProducto.getPrecio());
		pg.setStock(dataProducto.getStock());

		List<Imagen> imgs = new ArrayList<Imagen>();
		for (int i = 0; i < dataProducto.getFotos().size(); i++) {
			Imagen img = new Imagen();
			img.setDatos(dataProducto.getFotos().get(i).getDatos());
			img.setNombre(dataProducto.getFotos().get(i).getNombre());
			img.setMime(dataProducto.getFotos().get(i).getMime());
			this.imagenDAO.insertarImagen(img);
			imgs.add(img);
		}
		pg.getFoto().addAll(imgs);

		this.productoDAO.actualizarProducto(pg);
		// /////////cuando edito el producto el id de hermano el mismo ya que es
		// unico al haberlo editado
		// pg.setIdHermano(pg.getIdProducto());
		// this.productoDAO.actualizarProducto(pg);

		// /Tengo que editar a todos los hermanitos
		// List<Producto> listHermanos
		// =productoDAO.getProductosHermanos(dataProducto.getIdHermano());
		List<Producto> listHermanos = productoDAO.getProductosHermanos(pg
				.getIdHermano());

		if (!listHermanos.isEmpty()) {
			for (Producto aux : listHermanos) {
				if ((aux.getIdProducto() != pg.getIdProducto())) {
					// modificarProductoEspecifico(fabrica.convertirProducto(aux),
					// dataCategoria, idUsuario);
					Producto pg2 = this.productoDAO.getProducto(aux
							.getIdProducto());

					Categoria cat2 = new Categoria();
					if (dataCategoria.getNombre() == null) {
						cat2 = this.categoriaDAO.getCategoria(dataCategoria
								.getIdCategoria());
					} else {
						cat2.setNombre(dataCategoria.getNombre());
						cat2.setEsGenerica(false);
						Usuario u2 = this.usuarioDAO
								.getUsuarioPorEmail(dataCategoria
										.getEmailUsuario());
						cat2.setUsu(u2);
					}
					pg2.setCategoria(cat2);

					pg2.setAtributos(dataProducto.getAtributos());
					pg2.setNombre(dataProducto.getNombre());
					pg2.setDescripcion(dataProducto.getDescripcion());
					pg2.setEstaActivo(true);
					pg2.setPrecio(dataProducto.getPrecio());
					// pg2.setStock(dataProducto.getStock()); El stock para los
					// hermanos no se toca

					List<Imagen> imgs2 = new ArrayList<Imagen>();
					for (int i = 0; i < dataProducto.getFotos().size(); i++) {
						Imagen img2 = new Imagen();
						img2.setDatos(dataProducto.getFotos().get(i).getDatos());
						img2.setNombre(dataProducto.getFotos().get(i)
								.getNombre());
						img2.setMime(dataProducto.getFotos().get(i).getMime());
						this.imagenDAO.insertarImagen(img2);
						imgs2.add(img2);
					}
					pg2.getFoto().addAll(imgs2);

					this.productoDAO.actualizarProducto(pg2);

				}// if no es el que edite anteriormente
			}// for hermanos
		}// if tengo hermanos

	}

	public List<DataProductoCandidato> getProductosCandidatosAPromocion() {
		List candidatos = this.productoDAO.getProductosCandidatosAPromocion();
		List<DataProductoCandidato> dataCandidatosList = new ArrayList<DataProductoCandidato>();

		if (candidatos != null) {
			for (int i = 0; i < candidatos.size(); i++) {
				DataProductoCandidato dc = new DataProductoCandidato();
				Object[] objArray = (Object[]) candidatos.get(i);
				dc.setNombre(objArray[0].toString());
				dc.setCount(toIntExact((Long) objArray[1]));
				dataCandidatosList.add(dc);
			}
		}

		// Saco los que ya son genericos
		List<ProductoGenerico> prodGenericos = this.productoGenericoDAO
				.getProductosGenericos();

		List<DataProductoCandidato> dataCandidatosListFinal = new ArrayList<DataProductoCandidato>();

		if (prodGenericos != null && dataCandidatosList != null) {
			for (int k = 0; k < dataCandidatosList.size(); k++) {
				if (!esGenerico(dataCandidatosList.get(k), prodGenericos)) {
					dataCandidatosListFinal.add(dataCandidatosList.get(k));
				}
			}
		}
		return dataCandidatosListFinal;
	}

	public boolean esGenerico(DataProductoCandidato candidato,
			List<ProductoGenerico> dp) {

		for (int i = 0; i < dp.size(); i++) {
			if (dp.get(i).getNombre().equals(candidato.getNombre())) {
				return true;
			}
		}
		return false;
	}
}
