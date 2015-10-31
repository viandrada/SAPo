package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataUsuario;
import com.sapo.ejb.UsuarioNegocio;

@ManagedBean
@RequestScoped
public class ReporteUsuariosBean {
	public ReporteUsuariosBean() {
		listDataUsuarios = new ArrayList<DataUsuario>();
	}
	
	@EJB
	UsuarioNegocio usuarioNegocio;
	
	private List<DataUsuario> listDataUsuarios;

	public List<DataUsuario> getListDataUsuarios() {
		return listDataUsuarios;
	}

	public void setListDataUsuarios(List<DataUsuario> listDataUsuarios) {
		this.listDataUsuarios = listDataUsuarios;
	}
	
	@PostConstruct
	public void init(){
		listDataUsuarios = this.usuarioNegocio.getUsuarios();
	}
}
