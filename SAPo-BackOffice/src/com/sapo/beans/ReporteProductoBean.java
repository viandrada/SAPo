package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataReporteProductoGenerico;
import com.sapo.ejb.ReporteNegocio;


@ManagedBean
@RequestScoped
public class ReporteProductoBean {
	
	private List<DataReporteProductoGenerico> listaDataProdGen;
		
	@EJB
	ReporteNegocio reporteNegocio;
	
	public ReporteProductoBean() {
		listaDataProdGen = new ArrayList<DataReporteProductoGenerico>();
	}
	
	@PostConstruct
	public void init(){
		listaDataProdGen = this.reporteNegocio.buscarProductosGenericosMasUtilizados();
		
	}

	public List<DataReporteProductoGenerico> getListaDataProdGen() {
		return listaDataProdGen;
	}

	public void setListaDataProdGen(
			List<DataReporteProductoGenerico> listaDataProdGen) {
		this.listaDataProdGen = listaDataProdGen;
	}

	
}
