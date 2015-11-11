package com.sapo.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataReporteProducto;
import com.datatypes.DataUsuario;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.ReporteNegocio;
import com.sapo.ejb.UsuarioNegocio;

@ManagedBean
@RequestScoped
public class ReporteUsuariosBean {
	public ReporteUsuariosBean() {
		listDataUsuarios = new ArrayList<DataUsuario>();
	}
	
	@EJB
	UsuarioNegocio usuarioNegocio;
	
	@EJB
	ReporteNegocio reporteNegocio;
	
	@EJB
	AlmacenNegocio almacenNegocio;
	
	private List<DataUsuario> listDataUsuarios;
	private float ganancia;
	

	@PostConstruct
	public void init(){
		listDataUsuarios = this.usuarioNegocio.getUsuarios();
		ganancia=this.reporteNegocio.ganancias();
	}

	public float getGanancia() {
		return ganancia;
	}

	public void setGanancia(float ganancia) {
		this.ganancia = ganancia;
	}
	
	public List<DataUsuario> getListDataUsuarios() {
		return listDataUsuarios;
	}

	public void setListDataUsuarios(List<DataUsuario> listDataUsuarios) {
		this.listDataUsuarios = listDataUsuarios;
	}
	
	//public List<DataReporteProducto> getListaMovsProdPorUsuario(){
		
		
	//}
	
}
