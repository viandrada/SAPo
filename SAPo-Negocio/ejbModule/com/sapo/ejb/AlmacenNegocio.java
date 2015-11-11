package com.sapo.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.AlmacenIdealDAO;
import com.sapo.dao.CategoriaDAO;
import com.sapo.dao.ComentarioDAO;
import com.sapo.dao.ConfiguarcionDAO;
import com.sapo.dao.ImagenDAO;
import com.sapo.dao.ProductoDAO;
import com.sapo.dao.ProductoGenericoDAO;
import com.sapo.dao.UsuarioDAO;
import com.datatypes.DataAlmacen;
import com.datatypes.DataCategoria;
import com.datatypes.DataComentario;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.datatypes.DataUsuario;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.AlmacenIdeal;
import com.sapo.entidades.Categoria;
import com.sapo.entidades.Comentario;
import com.sapo.entidades.Imagen;
import com.sapo.entidades.Producto;
import com.sapo.entidades.ProductoGenerico;
import com.sapo.entidades.Usuario;
import com.sapo.utils.Fabrica;

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
	private AlmacenIdealDAO almacenIdealDAO;
	@EJB
	private ComentarioDAO comentarioDAO;
	@EJB
	private UsuarioDAO usuarioDAO;
	@EJB
	private ProductoDAO productoDAO;
	@EJB
	private ProductoGenericoDAO productoGenericoDAO;
	@EJB
	private CategoriaDAO categoriaDAO;
	@EJB
	private Fabrica fabrica;
	@EJB
	private ConfiguarcionDAO configuracionDAO;
	@EJB
	private ImagenDAO imagenDAO;

	private Almacen almacen;

	public int altaAlmacen(DataAlmacen almacen, DataUsuario usuario) {
		int idAlmacenGenerado = 0;
		Usuario usr;
		Imagen img = new Imagen();
		// List<Usuario> usus=new LinkedList<Usuario>();

		img.setDatos(almacen.getBytesFoto());
		usr = this.usuarioDAO.getUsuarioPorEmail(usuario.getEmail());

		this.almacen.setNombre(almacen.getNombre());
		this.almacen.setDescripcion(almacen.getDescripcion());
		this.almacen.setEstaActivo(true);
		this.almacen.setFechaAlta(new Date());
		this.almacen.setPropietario(usr);
		this.almacen.setFoto(img);

		// usus.add(usr);
		// this.almacen.agregarUsuarioCompartido(usr);
		// this.almacen.setUsuarios(usus);

		try {

			List<Usuario> listAux = new LinkedList<>();
			listAux.add(usr);

			this.almacen.setUsuarios(listAux);

			idAlmacenGenerado = this.almacenDAO.insertarAlmacen(this.almacen);

			// almacenDAO.actualizarAlmacen(a);agregado x vic

			// a.agregarUsuarioCompartido(usr);

			if (this.almacen.EsUsuariodeEsteAlmacen(usr.getEmail())) {
				System.out.println("SI YYYYYYA LO AGREGO AL ALMACEN");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return idAlmacenGenerado;
	}
	
	public void editarAlmacen(DataAlmacen almacen/*, DataUsuario usuario*/) {
		//int idAlmacenGenerado = 0;
		//Usuario usr;
		System.out.println("LLEGUE A EDITAR NEGOCIO");
		
		System.out.println(" NOMBRE ALMACEN: "+almacen.getNombre());
		
		System.out.println(" ID ALMACEN: "+almacen.getIdAlmacen());
		
		Almacen a = almacenDAO.getAlmacen(almacen.getIdAlmacen());
		
		
		
		System.out.print(" NOMBRE ALMACEN TRAIDO: "+a.getNombre());
		
		
		Imagen img = new Imagen();
		// List<Usuario> usus=new LinkedList<Usuario>();

		img.setDatos(almacen.getBytesFoto());
		//usr = this.usuarioDAO.getUsuarioPorEmail(usuario.getEmail());

		a.setNombre(almacen.getNombre());
		a.setDescripcion(almacen.getDescripcion());
		//this.almacen.setEstaActivo(true);
		//this.almacen.setFechaAlta(new Date());
		//this.almacen.setPropietario(usr);
		a.setFoto(img);

		// usus.add(usr);
		// this.almacen.agregarUsuarioCompartido(usr);
		// this.almacen.setUsuarios(usus);

		try {

			//List<Usuario> listAux = new LinkedList<>();
			//listAux.add(usr);

			//this.almacen.setUsuarios(listAux);

			//idAlmacenGenerado = this.almacenDAO.insertarAlmacen(this.almacen);

			// almacenDAO.actualizarAlmacen(a);agregado x vic

			// a.agregarUsuarioCompartido(usr);

			/*if (this.almacen.EsUsuariodeEsteAlmacen(usr.getEmail())) {
				System.out.println("SI YYYYYYA LO AGREGO AL ALMACEN");
			}*/
			
			almacenDAO.actualizarAlmacen(a);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		//return idAlmacenGenerado;
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
			dataAlmacen
					.setBytesFoto(listaAlmacenes.get(i).getFoto().getDatos());
			dataAlmacen
					.setIdFoto(listaAlmacenes.get(i).getFoto().getIdImagen());
			dataAlmacen.setIdAlmacen(listaAlmacenes.get(i).getIdAlmacen());
			listaDataAlmacen.add(dataAlmacen);
		}
		return listaDataAlmacen;
	}

	public int getCantidadAlmacenesDeUsuario(String emailUsr) {
		int idUser = this.usuarioDAO.getUsuarioPorEmail(emailUsr)
				.getIdUsuario();
		return this.almacenDAO.getCantAlmacenesUsuario(idUser);
	}

	public int getCantidadMaximaAlmacenes(String emailUsr) {
		int cant = 0;
		if (this.configuracionDAO.existeConfiguracion("maxCantAlmPremium")) {
			boolean premium = this.usuarioDAO.esPremium(emailUsr);
			if (premium)
				cant = this.configuracionDAO
						.getValorConfigInt("maxCantAlmPremium");
			else
				cant = this.configuracionDAO
						.getValorConfigInt("maxCantAlmComun");

		} else
			this.configuracionDAO.primeraConfiguracion();
		return cant;
	}

	public DataAlmacen getAlmacenPorId(int idAlmacen) {
		DataAlmacen dataAlmacen = new DataAlmacen();
		Almacen almacen = this.almacenDAO.getAlmacenPorId(idAlmacen);

		dataAlmacen.setIdAlmacen(almacen.getIdAlmacen());
		System.out.println("SETEO ID EN DATA ES: "+dataAlmacen.getIdAlmacen());
		
		dataAlmacen.setNombre(almacen.getNombre());
		dataAlmacen.setDescripcion(almacen.getDescripcion());
		dataAlmacen.setIdFoto(almacen.getFoto().getIdImagen());
		dataAlmacen.setBytesFoto(almacen.getFoto().getDatos());
		dataAlmacen.setProductos(toDataProductos(almacen.getProductos()));
		if (almacen.getAlmacenIdeal() != null) {
			dataAlmacen.setIdAlmacenIdeal(almacen.getAlmacenIdeal()
					.getIdAlmacenIdeal());
		}
		return dataAlmacen;
	}

	public boolean altaProducto(DataProducto producto, DataAlmacen almacen,
			DataCategoria categoria, DataUsuario usuario) {
		boolean altaOK = false;
		Producto productoGuardar = new Producto();
		Almacen almacenGuardar;// = new Almacen(almacen.getIdAlmacen());
		almacenGuardar = this.almacenDAO.getAlmacen(almacen.getIdAlmacen());

		Usuario usr = this.usuarioDAO.getUsuarioPorEmail(usuario.getEmail());

		Categoria catGuardar;
		if (categoria.getNombre() == null) {
			catGuardar = this.categoriaDAO.getCategoria(categoria
					.getIdCategoria());
		} else {
			catGuardar = new Categoria();
			catGuardar.setNombre(categoria.getNombre());
			catGuardar.setEsGenerica(false);

			catGuardar.setUsu(usr);
		}

		productoGuardar.setNombre(producto.getNombre());
		productoGuardar.setDescripcion(producto.getDescripcion());
		productoGuardar.setEstaActivo(true);
		productoGuardar.setEsIdeal(producto.isEsIdeal());
		productoGuardar.setPrecio(producto.getPrecio());
		productoGuardar.setAlmacen(almacenGuardar);
		productoGuardar.setCategoria(catGuardar);
		productoGuardar.setAtributos(producto.getAtributos());
		productoGuardar.setStock(producto.getStock());
		productoGuardar.setUsuario(usr);
		productoGuardar.setFechaAlta(new Date());

		List<Imagen> imgs = new ArrayList<Imagen>();
		for (int i = 0; i < producto.getFotos().size(); i++) {
			Imagen img = new Imagen();
			img.setDatos(producto.getFotos().get(i).getDatos());
			imgs.add(img);
		}
		productoGuardar.setFoto(imgs);

		try {
			this.productoDAO.insertarProducto(productoGuardar);
			// /////////////seteo idHermano conid de el
			// mismo//////////////////////////////////////////////////
			productoGuardar.setIdHermano(productoGuardar.getIdProducto());
			this.productoDAO.actualizarProducto(productoGuardar);

			altaOK = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return altaOK;
	}

	public List<DataProducto> getProductosDeAlmacen(int idAlmacen) {
		List<DataProducto> dataProductos = new ArrayList<DataProducto>();
		List<Producto> productos = this.productoDAO
				.getProductosAlmacen(idAlmacen);
		dataProductos = this.toDataProductos(productos);
		return dataProductos;
	}

	public List<DataProducto> toDataProductos(List<Producto> productos) {
		List<DataProducto> dataProductos = new ArrayList<DataProducto>();
		for (int i = 0; i < productos.size(); i++) {
			DataProducto dataProducto = new DataProducto();
			dataProducto.setIdProducto(productos.get(i).getIdProducto());
			if (productos.get(i).getProductoGenerico() != null) {
				dataProducto.setIdProductoGenerico(productos.get(i)
						.getProductoGenerico().getIdProductoGenerico());
			}
			dataProducto.setNombre(productos.get(i).getNombre());
			dataProducto.setDescripcion(productos.get(i).getDescripcion());
			dataProducto.setPrecio(productos.get(i).getPrecio());
			dataProducto.setAtributos(productos.get(i).getAtributos());
			dataProducto.setStock(productos.get(i).getStock());
			dataProducto.setStockIdeal(productos.get(i).getStockIdeal());
			if (productos.get(i).getFoto().size() == 0) {
				DataImagen di = new DataImagen();
				di.setIdImagen(1);
				List<DataImagen> imgList = new ArrayList<DataImagen>();
				imgList.add(di);
				dataProducto.setFotos(imgList);
			} else {
				dataProducto.setFotos(toDataImagen(productos.get(i).getFoto()));
			}

			dataProducto.setIdHermano(productos.get(i).getIdHermano());
			// /////////////////////////////////////////////////////////////////////copio
			// idhermano
			dataProductos.add(dataProducto);
		}
		return dataProductos;
	}

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

	public List<DataUsuario> listDataUsuarios(String email) {

		// return fabrica.convertirUsu(usuarioDAO.getUsuarios());
		return fabrica.convertirUsu(usuarioDAO.getUsuariosMenosUno(usuarioDAO
				.getUsuarioPorEmail(email).getIdUsuario()));
	};

	public List<DataComentario> listadeComentarios(int idalma) {

		return fabrica.convertirComentarios(almacenDAO.getAlmacen(idalma)
				.getComentarios());
	};

	public List<DataUsuario> listDataUsuariosParaCompartir(String email,
			int idalma) {
		// return fabrica.convertirUsu(usuarioDAO.getUsuarios());
		Almacen alma = almacenDAO.getAlmacen(idalma);
		return fabrica.convertirUsu(usuarioDAO
				.getUsuariosMenosYOyLosqueNOCompartenEsteAlmacen(usuarioDAO
						.getUsuarioPorEmail(email).getIdUsuario(), alma));
	};

	public List<DataUsuario> listDataUsuQueCompartenA(String email, int idalma) {
		// return fabrica.convertirUsu(usuarioDAO.getUsuarios());
		Almacen alma = almacenDAO.getAlmacen(idalma);
		return fabrica.convertirUsu(usuarioDAO
				.getUsuariosMenosYOyLosqueSICompartenEsteAlmacen(usuarioDAO
						.getUsuarioPorEmail(email).getIdUsuario(), alma));
	};

	public void compartirAlmacen(String emaildueno, String emailAmigo,
			int idAlmacen) {
		/*
		 * System.out.println("LLEGO PERO FALTA GUARDAR EL ALMACEN: " +
		 * idAlmacen + " EN LA LISTA DE ALMACENES DE " + emailAmigo);
		 */

		Usuario amigo = usuarioDAO.getUsuarioPorEmail(emailAmigo);

		Almacen a = almacenDAO.getAlmacen(idAlmacen);

		a.agregarUsuarioCompartido(amigo);

		almacenDAO.actualizarAlmacen(a);

		/*
		 * Almacen e = almacenDAO.getAlmacen(idAlmacen);
		 * 
		 * if (e.EsUsuariodeEsteAlmacen(emailAmigo)){
		 * 
		 * System.out.println("LO AGREGO BIEN"); };
		 */

	};

	public void comentarEnAlmacen(String emailusuario, String contenido,
			int idAlmacen) {

		Usuario usuario = usuarioDAO.getUsuarioPorEmail(emailusuario);

		Comentario co = new Comentario();
		co.setContenido(contenido);
		co.setFecha(new Date());
		co.setUsuario(usuario);

		// comentarioDAO.insertarComentario(co);

		Almacen a = almacenDAO.getAlmacen(idAlmacen);

		a.agregarComentario(co);

		almacenDAO.actualizarAlmacen(a);
	};

	public DataAlmacen getAlmacenIdealPorId(int idAlmacenIdeal) {
		DataAlmacen dataAlmacen = new DataAlmacen();
		AlmacenIdeal almacenIdeal = this.almacenIdealDAO
				.getAlmacenIdeal(idAlmacenIdeal);
		if (almacenIdeal != null) {
			dataAlmacen.setNombre(almacenIdeal.getNombre());
			dataAlmacen.setDescripcion(almacenIdeal.getDescripcion());
			dataAlmacen.setProductos(toDataProductos(almacenIdeal
					.getProductos()));
			dataAlmacen.setIdAlmacen(almacenIdeal.getIdAlmacenIdeal());
		}
		return dataAlmacen;
	}

	public int crearAlmacenIdeal(int idAlmacen) {
		// TODO Implementar crearAlmacenIdeal()
		AlmacenIdeal ai = new AlmacenIdeal();
		ai.setFechaAlta(new Date());
		ai.setEstaActivo(true);

		Almacen a = new Almacen();
		a = this.almacenDAO.getAlmacen(idAlmacen);
		a.setAlmacenIdeal(ai);
		int idAlmacenINuevo = a.getAlmacenIdeal().getIdAlmacenIdeal();

		this.almacenDAO.actualizarAlmacen(a);
		return idAlmacenINuevo;
	}

	public void agregarGenericoAAlmacenIdeal(DataProducto dataProducto,
			int idAlmacenIdeal, int idAlmacen) {
		AlmacenIdeal ai = new AlmacenIdeal();
		ai = this.almacenIdealDAO.getAlmacenIdeal(idAlmacenIdeal);
		Producto p = new Producto();
		// TODO Obtener el generico
		ProductoGenerico pg = new ProductoGenerico();
		pg = this.productoGenericoDAO.getProductoGenerico(dataProducto
				.getIdProducto());
		// Copiar todo al especifico
		Almacen a = this.almacenDAO.getAlmacen(idAlmacen);
		p.setAlmacen(a);
		p.setAtributos(pg.getAtributos());
		Categoria c = this.categoriaDAO.getCategoria(pg.getCategoria()
				.getIdCategoria());
		p.setCategoria(c);
		p.setDescripcion(pg.getDescripcion());
		p.setEstaActivo(true);
		p.setFechaAlta(new Date());
		List<Imagen> di = new ArrayList<Imagen>();
		Imagen i = this.imagenDAO.getImagen(pg.getFoto().getIdImagen());
		di.add(i);
		p.setFoto(di);
		p.setNombre(pg.getNombre());
		p.setPrecio(pg.getPrecio());
		p.setProductoGenerico(pg);
		p.setStockIdeal(1);
		p.setEsIdeal(true);// Booleano para indicar q es ideal, no se muestra en
							// el almac�n com�n.
		// Persistirlo en el almac�n com�n
		this.productoDAO.insertarProducto(p);
		// Asociarlo al almacen ideal
		ai.getProductos().add(p);
		this.almacenIdealDAO.actualizarAlmacenIdeal(ai);

		// p.setIdHermano(pg.getIdProductoGenerico());
		// no se si aca va??????????????????
		// this.almacenIdealDAO.actualizarAlmacenIdeal(ai);

	}

	public void agregarEspecificoAAlmacenIdeal(DataProducto dataProducto,
			int idAlmacenIdeal) {
		AlmacenIdeal ai = new AlmacenIdeal();
		Producto p = this.productoDAO.getProducto(dataProducto.getIdProducto());
		p.setStockIdeal(1);

		ai = this.almacenIdealDAO.getAlmacenIdeal(idAlmacenIdeal);
		ai.getProductos().add(p);

		this.almacenIdealDAO.actualizarAlmacenIdeal(ai);
	}

	public List<Imagen> toImagen(List<DataImagen> dataImagenes) {
		List<Imagen> imagenes = new ArrayList<Imagen>();
		for (int i = 0; i < dataImagenes.size(); i++) {
			Imagen img = new Imagen();
			img.setIdImagen(dataImagenes.get(i).getIdImagen());
			img.setNombre(dataImagenes.get(i).getNombre());
			img.setMime(dataImagenes.get(i).getMime());
			img.setDatos(dataImagenes.get(i).getDatos());
			imagenes.add(img);
		}
		return imagenes;
	}

	public void sincronizarLista(int idProductoObtenido, int idAlmacen) {
		Producto p = new Producto();
		Almacen a = this.almacenDAO.getAlmacen(idAlmacen);
		AlmacenIdeal ai = this.almacenIdealDAO.getAlmacenIdeal(a
				.getAlmacenIdeal().getIdAlmacenIdeal());
		p = this.productoDAO.getProducto(idProductoObtenido);
		if (p.isEsIdeal()) {
			p.setStock(p.getStockIdeal());
			p.setEsIdeal(false);

		} else {
			p.setStock(p.getStockIdeal());
		}
		p.setStockIdeal(0);
		this.productoDAO.actualizarProducto(p);

		ai.getProductos().remove(p);
		this.almacenIdealDAO.actualizarAlmacenIdeal(ai);

	}

	/*
	 * Devuelve una lista con todos los historicos de Almacenes de un usuario
	 * particular. Por ahora es VOID pero se puede cambiar
	 */
	/*
	 * public void buscarHistoricoAlmacenesPorUsuario(int idUsuario){
	 * 
	 * List<Almacen> listaAlm =
	 * this.almacenDAO.getHistoricoAlmacenesPorUsuario(idUsuario); }
	 */

	public void moverProducto(int idAlmacenDestino, int idProducto,
			int cantStock, int idAlmacenOrigen) {
		System.out.println("HOLA ESTOY MOVIENDO MOVERPRODUCTO NEGOCIO");
		System.out.println("LLEGO IDALMA DESTINO: " + idAlmacenDestino
				+ " IDPRODUCTO: " + idProducto + " CANTSTOCK: " + cantStock
				+ "IDALMAORIGEN: " + idAlmacenOrigen);

		Almacen aDestino = almacenDAO.getAlmacen(idAlmacenDestino);
		System.out.println("pido almacen DESTINO: " + aDestino.getNombre());
		// List<Producto> listProductDestino = aDestino.getProductos();
		Almacen aOrigen = almacenDAO.getAlmacen(idAlmacenOrigen);
		System.out.println("pido almacen ORIGEN: " + aOrigen.getNombre());

		List<Producto> listProductOrigen = new LinkedList<Producto>();
		// List<Producto> listProductDestino = new LinkedList<Producto>();
		/* listProductOrigen=aOrigen.getProductos(); */

		listProductOrigen = productoDAO.getProductosAlmacen(aOrigen
				.getIdAlmacen());

		// listProductDestino=productoDAO.getProductosAlmacen(aDestino.getIdAlmacen());

		System.out.println("pido la lista del almacen ORIGEN, es VACIA: "
				+ listProductOrigen.isEmpty());

		if (!listProductOrigen.isEmpty()) {
			System.out
					.println("LA LISTA DE PRODUCTOS DE ALMA ORIGEN NO ES VACIA");
			for (Producto p : listProductOrigen) {

				if (p.getIdProducto() == idProducto) {
					System.out.println("ENCONTRE EL PRODUCTO A MOVER");

					if (productoDAO.existeProductoHermano(
							aDestino.getIdAlmacen(), p.getIdHermano())) {

						System.out.println("ESTOY CANBIANDO STOCKS, EN NEGOCIO, PORQUE EXISTE HERMANO");
						Producto prod = productoDAO.getProductoHermano(
								aDestino.getIdAlmacen(), p.getIdHermano());
			///////////////////////////////////////////////////////////////////////
						
						if ((p.getStock() - cantStock) <= 0) {

							prod.setStock(p.getStock()+prod.getStock());
							p.setStock(0);

						} else {

							prod.setStock(prod.getStock()+cantStock);
							p.setStock(p.getStock() - cantStock);

						}
						/////////////////////////////////////////////////////////////////////////////////
						
						System.out.println("AUMENTO EL STOCK EN EL PRODUCTO HERMANO Y RESTO EN EL PROD DE ALMACEN ORIGEN");
						// almacenDAO.actualizarAlmacen(aDestino);
						
						productoDAO.actualizarProducto(prod);
						productoDAO.actualizarProducto(p);

					}// if existe hermano
					else {
						System.out
								.println("ESTOY COPIANDO Y CANBIANDO STOCKS, EN NEGOCIO, PORQUE NO EXISTE HERMANO");

						// copio el producto
						Producto nuevoProd;// = new Producto();

						nuevoProd = p.copiarProducto();// en la copia seteo el
														// hermano ya

						nuevoProd.setAlmacen(aDestino);// seteo a la copia el
														// almacen a donde va

						// Modifico stocks

						if ((p.getStock() - cantStock) <= 0) {

							nuevoProd.setStock(p.getStock());
							p.setStock(0);

						} else {

							nuevoProd.setStock(cantStock);
							p.setStock(p.getStock() - cantStock);

						}

						productoDAO.actualizarProducto(p);

						productoDAO.insertarProducto(nuevoProd);

					}

				}// if encontre producto

			}// for
		}// if lista de prod no es vacia

		/*
		 * Almacen e = almacenDAO.getAlmacen(idAlmacen);
		 * 
		 * if (e.EsUsuariodeEsteAlmacen(emailAmigo)){
		 * 
		 * System.out.println("LO AGREGO BIEN"); };
		 */

	}

	public List<DataAlmacen> getAlmacenesMenosUno(int idAlmacen,
			String emailUsuario) {

		List<Almacen> listAlma = almacenDAO.getAlmacenesUsuario(emailUsuario);
		List<Almacen> lisR = new LinkedList<Almacen>();

		try {

			if (!listAlma.isEmpty()) {
				for (Almacen a : listAlma) {

					if (a.getIdAlmacen() != idAlmacen) {
						lisR.add(a);
					}
				}

			}

		} catch (Exception excep) {
			throw excep;
		}

		return fabrica.convertirAlmacenes(lisR);

	}

}
