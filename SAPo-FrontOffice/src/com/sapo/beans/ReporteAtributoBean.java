package com.sapo.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import com.datatypes.DataAtributoAcumulado;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.ReporteNegocio;


@ManagedBean
@RequestScoped
public class ReporteAtributoBean {

	public ReporteAtributoBean() {
		
	
	}
	@EJB
	AlmacenNegocio almacenNegocio;
	@EJB
	ReporteNegocio reporteNegocio;
	
	
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;
	
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	
	//private String nombreUsuSelect;
	//private String emailUsuSelect;
	//private int idAlmacen;
	//private String emailUsuarioLogueado;
	//private String contenido;
	//private int idComentarioSelect;
	
	//private List<DataComentario> listDataComentarios;
	
	private List<DataAtributoAcumulado> listDataAtriAcumulados;
	private String nombreAtributoSelect;
	
	@PostConstruct
	public void init() {
		listDataAtriAcumulados=reporteNegocio.getDatasAtributosAcumulados(nav.getIdAlmacenActual());
		//setListDataComentarios(almacenNegocio.listadeComentarios(nav.getIdAlmacenActual()));
		//idAlmacen=nav.getIdAlmacenActual();
		//emailUsuarioLogueado=usuarioLogueado.getEmail();
		//contenido="";
	}
	
	
	/*public String comentarEnAlmacen(){
		almacenNegocio.comentarEnAlmacen(usuarioLogueado.getEmail(), contenido, nav.getIdAlmacenActual());
		contenido="";
		return "index?faces-redirect=true";
	}
		
	public String descomentarEnAlmacen(){
		//almacenNegocio.comentarEnAlmacen(usuarioLogueado.getEmail(), idComentarioSelect, nav.getIdAlmacenActual());
		//contenido="";
		almacenNegocio.descomentarEnAlmacen(usuarioLogueado.getEmail(), idComentarioSelect, nav.getIdAlmacenActual());
		
		return "index?faces-redirect=true";
	}
	
	public boolean esComentarioMio(int idComentarioSelecionado){
		return almacenNegocio.esComentarioDeUsuario(usuarioLogueado.getEmail(), idComentarioSelecionado, nav.getIdAlmacenActual());
	}
	
	public String getNombreUsuSelect() {
		return nombreUsuSelect;
	}

	public void setNombreUsuSelect(String nombreUsuSelect) {
		this.nombreUsuSelect = nombreUsuSelect;
	}

	public String getEmailUsuSelect() {
		return emailUsuSelect;
	}

	public void setEmailUsuSelect(String emailUsuSelect) {
		this.emailUsuSelect = emailUsuSelect;
	}*/
	
	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	/*public List<DataComentario> getListDataComentarios() {
		return listDataComentarios;
	}

	public void setListDataComentarios(List<DataComentario> listDataComentarios) {
		this.listDataComentarios = listDataComentarios;
	}*/

	/*public int getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public String getEmailUsuarioLogueado() {
		return emailUsuarioLogueado;
	}

	public void setEmailUsuarioLogueado(String emailUsuarioLogueado) {
		this.emailUsuarioLogueado = emailUsuarioLogueado;
	}*/

/*	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public int getIdComentarioSelect() {
		return idComentarioSelect;
	}

	public void setIdComentarioSelect(int idComentarioSelect) {
		this.idComentarioSelect = idComentarioSelect;
	}*/

	public List<DataAtributoAcumulado> getListDataAtriAcumulados() {
		return listDataAtriAcumulados;
	}

	public void setListDataAtriAcumulados(List<DataAtributoAcumulado> listDataAtriAcumulados) {
		this.listDataAtriAcumulados = listDataAtriAcumulados;
	}

	public String getNombreAtributoSelect() {
		return nombreAtributoSelect;
	}

	public void setNombreAtributoSelect(String nombreAtributoSelect) {
		this.nombreAtributoSelect = nombreAtributoSelect;
	}

}
