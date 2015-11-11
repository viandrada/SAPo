package com.sapo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataReporteAlmacen;
import com.datatypes.DataReporteProducto;
import com.sapo.ejb.ReporteNegocio;


@ManagedBean
@RequestScoped
public class MovimientosUsuarioBean {

	@EJB
	ReporteNegocio reporteNegocio;
	
	private List<DataReporteAlmacen> listaMovsAlmacenes;
	private List<DataReporteProducto> listaMovsProductos;
	
	private int idUsuario;
	
	public MovimientosUsuarioBean(){
		listaMovsAlmacenes = new ArrayList<DataReporteAlmacen>();
		listaMovsProductos = new ArrayList<DataReporteProducto>();
	}
	
	@PostConstruct
	public void init(int idUsuario2){
		listaMovsAlmacenes = this.reporteNegocio.buscarHistoricoAlmacenesPorUsuario(idUsuario2);
		listaMovsProductos = this.reporteNegocio.buscarHistoricoProdPorUsuario(idUsuario2);
		
	}
	
	public List<DataReporteAlmacen> getListaMovsAlmacenes() {
		return listaMovsAlmacenes;
	}

	public void setListaMovsAlmacenes(List<DataReporteAlmacen> listaMovsAlmacenes) {
		this.listaMovsAlmacenes = listaMovsAlmacenes;
	}

	public List<DataReporteProducto> getListaMovsProductos() {
		return listaMovsProductos;
	}

	public void setListaMovsProductos(List<DataReporteProducto> listaMovsProductos) {
		this.listaMovsProductos = listaMovsProductos;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	

}
