package com.sapo.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
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
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.datatypes.DataUsuario;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.Categoria;
import com.sapo.entidades.Imagen;
import com.sapo.entidades.Producto;
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
	private UsuarioDAO usuarioDAO;
	@EJB
	private ProductoDAO productoDAO;
	@EJB
	private CategoriaDAO categoriaDAO;
	@EJB
	private Fabrica fabrica;

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

	public DataAlmacen getAlmacenPorId(int idAlmacen) {
		DataAlmacen dataAlmacen = new DataAlmacen();
		Almacen almacen = this.almacenDAO.getAlmacenPorId(idAlmacen);

		dataAlmacen.setNombre(almacen.getNombre());
		dataAlmacen.setDescripcion(almacen.getDescripcion());
		dataAlmacen.setIdFoto(almacen.getFoto().getIdImagen());
		dataAlmacen.setBytesFoto(almacen.getFoto().getDatos());
		dataAlmacen.setProductos(toDataProductos(almacen.getProductos()));
		return dataAlmacen;
	}

	public boolean altaProducto(DataProducto producto, DataAlmacen almacen,
			DataCategoria categoria, DataUsuario usuario) {
		boolean altaOK = false;
		Producto productoGuardar = new Producto();
		Almacen almacenGuardar;// = new Almacen(almacen.getIdAlmacen());
		almacenGuardar = this.almacenDAO.getAlmacen(almacen.getIdAlmacen());

		Categoria catGuardar;
		if (categoria.getNombre() == null) {
			catGuardar = this.categoriaDAO.getCategoria(categoria
					.getIdCategoria());
		} else {
			catGuardar = new Categoria();
			catGuardar.setNombre(categoria.getNombre());
			catGuardar.setEsGenerica(false);
			Usuario usr = this.usuarioDAO
					.getUsuarioPorEmail(usuario.getEmail());
			catGuardar.setUsu(usr);
		}

		productoGuardar.setNombre(producto.getNombre());
		productoGuardar.setDescripcion(producto.getDescripcion());
		productoGuardar.setEstaActivo(true);
		productoGuardar.setPrecio(producto.getPrecio());
		productoGuardar.setAlmacen(almacenGuardar);
		productoGuardar.setCategoria(catGuardar);
		productoGuardar.setAtributos(producto.getAtributos());
		productoGuardar.setStock(producto.getStock());
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
			dataProducto.setNombre(productos.get(i).getNombre());
			dataProducto.setDescripcion(productos.get(i).getDescripcion());
			dataProducto.setPrecio(productos.get(i).getPrecio());
			// TODO Agregar mas campos al data (imagenes y atributos)
			dataProducto.setAtributos(productos.get(i).getAtributos());
			dataProducto.setStock(productos.get(i).getStock());
			dataProducto.setFotos(toDataImagen(productos.get(i).getFoto()));
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

	public List<DataUsuario> listDataUsuariosParaCompartir(String email,
			int idalma) {
		// return fabrica.convertirUsu(usuarioDAO.getUsuarios());
		Almacen alma = almacenDAO.getAlmacen(idalma);
		return fabrica.convertirUsu(usuarioDAO
				.getUsuariosMenosYOyLosqueNOCompartenEsteAlmacen(usuarioDAO
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

}
