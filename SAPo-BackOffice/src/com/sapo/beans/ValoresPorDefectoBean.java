package com.sapo.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

import com.datatypes.DataImagen;
import com.sapo.ejb.ConfiguracionNegocio;

@ManagedBean
@RequestScoped
public class ValoresPorDefectoBean {

	public ValoresPorDefectoBean() {
		this.hayFotoPerfil = true;
		this.hayFotoProducto = true;
		this.hayFotoAlmacen = true;
	}

	private Part fotoPerfil;
	private Part fotoProducto;
	private Part fotoAlmacen;
	private DataImagen fotoPerfilBD;/* Imagen 1 en la BD */
	private DataImagen fotoProductoBD;/* Imagen 2 en la BD */
	private DataImagen fotoAlmacenBD;/* Imagen 3 en la BD */
	private boolean hayFotoPerfil;
	private boolean hayFotoProducto;
	private boolean hayFotoAlmacen;

	@EJB
	private ConfiguracionNegocio configNegocio;

	public Part getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(Part fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public Part getFotoProducto() {
		return fotoProducto;
	}

	public void setFotoProducto(Part fotoProducto) {
		this.fotoProducto = fotoProducto;
	}

	public Part getFotoAlmacen() {
		return fotoAlmacen;
	}

	public void setFotoAlmacen(Part fotoAlmacen) {
		this.fotoAlmacen = fotoAlmacen;
	}

	public DataImagen getFotoPerfilBD() {
		return fotoPerfilBD;
	}

	public void setFotoPerfilBD(DataImagen fotoPerfilBD) {
		this.fotoPerfilBD = fotoPerfilBD;
	}

	public DataImagen getFotoProductoBD() {
		return fotoProductoBD;
	}

	public void setFotoProductoBD(DataImagen fotoProductoBD) {
		this.fotoProductoBD = fotoProductoBD;
	}

	public DataImagen getFotoAlmacenBD() {
		return fotoAlmacenBD;
	}

	public void setFotoAlmacenBD(DataImagen fotoAlmacenBD) {
		this.fotoAlmacenBD = fotoAlmacenBD;
	}

	public boolean isHayFotoPerfil() {
		return hayFotoPerfil;
	}

	public void setHayFotoPerfil(boolean hayFotoPerfil) {
		this.hayFotoPerfil = hayFotoPerfil;
	}

	public boolean isHayFotoProducto() {
		return hayFotoProducto;
	}

	public void setHayFotoProducto(boolean hayFotoProducto) {
		this.hayFotoProducto = hayFotoProducto;
	}

	public boolean isHayFotoAlmacen() {
		return hayFotoAlmacen;
	}

	public void setHayFotoAlmacen(boolean hayFotoAlmacen) {
		this.hayFotoAlmacen = hayFotoAlmacen;
	}

	@PostConstruct
	public void init() {
		getImagenPerfil();
		getImagenProducto();
		getImagenAlmacen();
	}

	public void getImagenPerfil() {
		this.fotoPerfilBD = this.configNegocio.getImagenPorDefecto(1);
		if (this.fotoPerfilBD.getDatos() == null) {
			this.hayFotoPerfil = false;
		}
	}

	public void getImagenProducto() {
		this.fotoProductoBD = this.configNegocio.getImagenPorDefecto(2);
		if (this.fotoProductoBD.getDatos() == null) {
			this.hayFotoProducto = false;
		}
	}

	public void getImagenAlmacen() {
		this.fotoAlmacenBD = this.configNegocio.getImagenPorDefecto(3);
		if (this.fotoAlmacenBD.getDatos() == null) {
			this.hayFotoAlmacen = false;
		}
	}

	public void guardarFotoPerfil() {
		DataImagen di = new DataImagen();
		di.setIdImagen(1);
		if (this.fotoPerfil != null) {
			InputStream is;
			try {
				is = this.fotoPerfil.getInputStream();

				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();
				is.close();

				di.setDatos((buffer.toByteArray()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.configNegocio.guardarFoto(di);
	}

	public void guardarFotoProducto() {
		DataImagen di = new DataImagen();
		di.setIdImagen(2);
		if (this.fotoProducto != null) {
			InputStream is;
			try {
				is = this.fotoProducto.getInputStream();

				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();
				is.close();

				di.setDatos((buffer.toByteArray()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.configNegocio.guardarFoto(di);
	}

	public void guardarFotoAlmacen() {
		DataImagen di = new DataImagen();
		di.setIdImagen(3);
		if (this.fotoAlmacen != null) {
			InputStream is;
			try {
				is = this.fotoAlmacen.getInputStream();

				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();
				is.close();

				di.setDatos((buffer.toByteArray()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.configNegocio.guardarFoto(di);
	}
}
