package com.sapo.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;




import com.datatypes.DataAlmacen;
import com.datatypes.DataUsuario;
import com.sapo.ejb.AlmacenNegocio;

@ManagedBean
@RequestScoped
public class AltaAlmacenBean {

	public AltaAlmacenBean() {
		super();
		dataAlmacen = new DataAlmacen();
		dataUsuario = new DataUsuario();
	}

	private DataUsuario dataUsuario;
	private DataAlmacen dataAlmacen;
	@EJB
	AlmacenNegocio almacenNegocio;
	@ManagedProperty(value="#{loginBean}")
	LoginBean usuarioLogueado;
	@ManagedProperty(value="#{navigationAreaBean}")
	NavigationAreaBean nav;
	private String nombre;
	private String descripcion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public DataUsuario getDataUsuario() {
		return dataUsuario;
	}

	public void setDataUsuario(DataUsuario dataUsuario) {
		this.dataUsuario = dataUsuario;
	}

	public DataAlmacen getDataAlmacen() {
		return dataAlmacen;
	}

	public void setDataAlmacen(DataAlmacen dataAlmacen) {
		this.dataAlmacen = dataAlmacen;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public String altaAlmacen() {
		boolean ok = false;
		
		this.dataAlmacen.setNombre(this.nombre);
		this.dataAlmacen.setDescripcion(this.descripcion);
		this.dataUsuario.setEmail(this.usuarioLogueado.getEmail());
		
		ok = this.almacenNegocio.altaAlmacen(this.dataAlmacen, this.dataUsuario);
		
		if (ok) {
			System.out.println("Alta almacen exitosa");
			this.nav.setRedirectTo("almacen.xhtml");
			return "/index.xhtml?faces-redirect=true";
		} else {
			System.out.println("Error. El almacen no fue dado de alta.");
			return null;
		}
	}

}
