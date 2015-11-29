package com.sapo.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataImagen;
import com.sapo.dao.ImagenDAO;
import com.sapo.entidades.Imagen;

/**
 * Session Bean implementation class ConfiguracionNegocio
 */
@Stateless
@LocalBean
public class ConfiguracionNegocio {

	/**
	 * Default constructor.
	 */
	public ConfiguracionNegocio() {
		// TODO Auto-generated constructor stub
	}

	@EJB
	ImagenDAO imagenDAO;

	public DataImagen getImagenPorDefecto(int idImagen) {
		DataImagen di = new DataImagen();
		Imagen i = this.imagenDAO.getImagen(idImagen);
		di.setDatos(i.getDatos());
		di.setIdImagen(i.getIdImagen());
		di.setNombre(i.getNombre());
		return di;
	}

	public void guardarFoto(DataImagen di) {
		Imagen i = this.imagenDAO.getImagen(di.getIdImagen());
		if (i != null) {
			if (di.getDatos() != null) {
				i.setDatos(di.getDatos());
				this.imagenDAO.actualizarImagen(i);
			}
		} else {
			if (di.getDatos() != null) {
				Imagen img = new Imagen();
				img.setDatos(di.getDatos());
				this.imagenDAO.insertarImagen(img);
			}
		}

	}

	/*
	 * Este metodo se ejecuta al inicio de la aplicacion para asegurarnos que
	 * las 3 primeras fotos son las x defecto
	 */
	public void cargarFotosPorDefecto() {
		Imagen primeraImg = this.imagenDAO.getImagen(1);
		if (primeraImg == null) {
			List<Imagen> imagenes = new ArrayList<Imagen>();
			Imagen iPerfil = new Imagen();
			iPerfil.setNombre("Perfil");
			imagenes.add(iPerfil);
			Imagen iProducto = new Imagen();
			iProducto.setNombre("Producto");
			imagenes.add(iProducto);
			Imagen iAlmacen = new Imagen();
			iAlmacen.setNombre("Almacen");
			imagenes.add(iAlmacen);
			for (int i = 0; i < imagenes.size(); i++) {
				this.imagenDAO.insertarImagen(imagenes.get(i));
			}
		}
	}
}
