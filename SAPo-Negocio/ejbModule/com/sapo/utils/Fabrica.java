package com.sapo.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import com.datatypes.DataCategoria;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.datatypes.DataComentario;
import com.datatypes.DataUsuario;
import com.sapo.entidades.Categoria;
import com.sapo.entidades.Imagen;
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
			// dcat.setFecha(c.);

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
}
