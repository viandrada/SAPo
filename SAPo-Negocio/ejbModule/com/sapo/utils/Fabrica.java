package com.sapo.utils;

import java.math.BigInteger;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import com.datatypes.DataAlmacen;
import com.datatypes.DataCategoria;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.datatypes.DataComentario;
import com.datatypes.DataReporteAlmacen;
import com.datatypes.DataReporteProducto;
import com.datatypes.DataReporteProductoGenerico;
import com.datatypes.DataUsuario;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.Categoria;
import com.sapo.entidades.Imagen;
import com.sapo.entidades.Producto;
import com.sapo.entidades.ProductoGenerico;
import com.sapo.entidades.Comentario;
import com.sapo.entidades.Usuario;

@Stateless
public class Fabrica {

	public List<DataCategoria> convertirCat(List<Categoria> lcat) {
		List<DataCategoria> l = new LinkedList<DataCategoria>();

		for (Categoria c : lcat) {
			DataCategoria dcat = new DataCategoria();

			dcat.setIdCategoria(c.getIdCategoria());
			dcat.setNombre(c.getNombre());

			l.add(dcat);
		}
		return l;
	}

	public List<DataUsuario> convertirUsu(List<Usuario> lcat) {
		List<DataUsuario> l = new LinkedList<DataUsuario>();

		for (Usuario c : lcat) {
			DataUsuario dcat = new DataUsuario();

			dcat.setIdUsuario(c.getIdUsuario());
			dcat.setNombre(c.getNombre());
			dcat.setEmail(c.getEmail());
			dcat.setPassword(c.getPassword());
			dcat.setEstaActivo(c.getEstaActivo());
			
			dcat.setFecha(c.getFecha());
			dcat.setFechaPago(c.getFechaPago());
			dcat.setMonto(c.getMonto());
			
			l.add(dcat);
		}
		return l;
	}

	public List<DataComentario> convertirComentarios(List<Comentario> lcat) {
		List<DataComentario> l = new LinkedList<DataComentario>();

		for (Comentario c : lcat) {
			DataComentario dcat = new DataComentario();

			dcat.setContenido(c.getContenido());
			dcat.setFecha(c.getFecha());
			dcat.setIdComentario(c.getIdComentario());
			dcat.setUsuario(c.getUsuario().getIdUsuario());
			dcat.setNombreUsu(c.getUsuario().getNombre());
			dcat.setEmailUsu(c.getUsuario().getEmail());

			l.add(dcat);
		}
		return l;
	}

	public DataUsuario convertirUserAData(Usuario usuario) {
		DataUsuario dataUser = new DataUsuario();
		dataUser.setEmail(usuario.getEmail());
		dataUser.setNombre(usuario.getNombre());
		dataUser.setPassword(usuario.getPassword());
		dataUser.setPremium(usuario.isPremium());
		dataUser.setIdUsuario(usuario.getIdUsuario());
		dataUser.setFecha(usuario.getFecha());
		dataUser.setFechaPago(usuario.getFechaPago());
		dataUser.setMonto(usuario.getMonto());
		return dataUser;

	}

	public List<DataProducto> toDataProducto(
			List<ProductoGenerico> productosGenericos) {
		List<DataProducto> dataProductos = new ArrayList<DataProducto>();
		for (int i = 0; i < productosGenericos.size(); i++) {
			DataProducto dProd = new DataProducto();
			dProd.setIdProducto(productosGenericos.get(i)
					.getIdProductoGenerico());
			dProd.setNombre(productosGenericos.get(i).getNombre());
			dProd.setDescripcion(productosGenericos.get(i).getDescripcion());
			dProd.setEstaActivo(productosGenericos.get(i).getEstaActivo());
			dProd.setFechaAlta(productosGenericos.get(i).getFechaAlta());
			dProd.setPrecio(productosGenericos.get(i).getPrecio());
			dProd.setIdCategoria(productosGenericos.get(i).getCategoria().getIdCategoria());
			dProd.setNombreCategoria(productosGenericos.get(i).getCategoria().getNombre());
			List<Imagen> img = new ArrayList<Imagen>();
			if (productosGenericos.get(i).getFoto() != null) {
				img.add(productosGenericos.get(i).getFoto());
				dProd.setFotos(toDataImagen(img));
			}
			else{
				Imagen dataImg = new Imagen();
				dataImg.setIdImagen(1);//TODO settear imagen x defecto
				img.add(dataImg);
				dProd.setFotos(toDataImagen(img));
			}
			dProd.setAtributos(productosGenericos.get(i).getAtributos());
			////////////////////////////////////le seteo en id hermano el id del prod generico
			dProd.setIdHermano(productosGenericos.get(i).getIdProductoGenerico());
			
			dataProductos.add(dProd);
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
	
	public DataReporteAlmacen toDataReporteAlmacen(Almacen alm, String mov, Date fechaMov){
		DataReporteAlmacen dataRepAlm = new DataReporteAlmacen();
		dataRepAlm.setActivo(alm.getEstaActivo());
		dataRepAlm.setBytesFoto(alm.getFoto().getDatos());
		dataRepAlm.setIdFoto(alm.getFoto().getIdImagen());
		dataRepAlm.setDescripcion(alm.getDescripcion());
		dataRepAlm.setFechaAlta(alm.getFechaAlta());
		dataRepAlm.setIdAlmacen(alm.getIdAlmacen());
		dataRepAlm.setNombre(alm.getNombre());
		dataRepAlm.setTipoMovimiento(mov);
		dataRepAlm.setFechaMovimiento(fechaMov);
		
		if((alm.getAlmacenIdeal()==null))
			dataRepAlm.setIdAlmacenIdeal(0);
		else
			dataRepAlm.setIdAlmacenIdeal(alm.getAlmacenIdeal().getIdAlmacenIdeal());
		
		return dataRepAlm;
	}
	
	public DataReporteProducto toDataReporteProducto(Producto prod, String mov, Date fechaMov){
		DataReporteProducto dataRepProd = new DataReporteProducto();
		List<Imagen> listaImgs = prod.getFoto();
		List<DataImagen> listaDataImgs = toDataImagen(listaImgs);
		
		dataRepProd.setAtributos(prod.getAtributos());
		dataRepProd.setDescripcion(prod.getDescripcion());
		dataRepProd.setEstaActivo(prod.getEstaActivo());
		dataRepProd.setFechaAlta(prod.getFechaAlta());
		dataRepProd.setFotos(listaDataImgs);
		dataRepProd.setIdProducto(prod.getIdProducto());
		dataRepProd.setIdUsuario(prod.getUsuario().getIdUsuario());
		dataRepProd.setNombre(prod.getNombre());
		dataRepProd.setNombreCategoria(prod.getCategoria().getNombre());
		dataRepProd.setPrecio(prod.getPrecio());
		dataRepProd.setStock(prod.getStock());
		dataRepProd.setTipoMovimiento(mov);
		dataRepProd.setFechaMovimiento(fechaMov);
		
		return dataRepProd;
	}
	
	public DataReporteProductoGenerico toDataReporteProductoGenerico (ProductoGenerico prodGen, BigInteger cantUsos){
		DataReporteProductoGenerico dataRepProdGen = new DataReporteProductoGenerico();
	
		dataRepProdGen.setAtributos(prodGen.getAtributos());
		dataRepProdGen.setDescripcion(prodGen.getDescripcion());
		dataRepProdGen.setEstaActivo(prodGen.getEstaActivo());
		dataRepProdGen.setFechaAlta(prodGen.getFechaAlta());
		dataRepProdGen.setIdProductoGenerico(prodGen.getIdProductoGenerico());
		dataRepProdGen.setNombre(prodGen.getNombre());
		dataRepProdGen.setNombreCategoria(prodGen.getCategoria().getNombre());
		dataRepProdGen.setCantidadUsos(cantUsos.intValue());
		
		return dataRepProdGen;
	}
	
	public List<DataAlmacen> convertirAlmacenes(List<Almacen> lcat) {
		List<DataAlmacen> l = new LinkedList<DataAlmacen>();
		for (Almacen c : lcat) {
			DataAlmacen dataAlmacen = new DataAlmacen();
			dataAlmacen.setNombre(c.getNombre());
			dataAlmacen.setDescripcion(c.getDescripcion());
			dataAlmacen.setBytesFoto(c.getFoto().getDatos());
			dataAlmacen.setIdFoto(c.getFoto().getIdImagen());
			dataAlmacen.setIdAlmacen(c.getIdAlmacen());

			l.add(dataAlmacen);
		}
		return l;
	}
}
