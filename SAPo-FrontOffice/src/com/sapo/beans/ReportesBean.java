package com.sapo.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.AjaxBehaviorEvent;

import com.sapo.utils.Atributo;


@ManagedBean
@RequestScoped
public class ReportesBean {
	
	public ReportesBean(){
	}
	
	
	private String tipoReporte;
	private String valorReporte1;
	private String valorReporte2;
	private HashMap<String,String> listaReportes;

	
	public String getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	
	


	public HashMap<String, String> getListaReportes() {
		return listaReportes;
	}

	public void setListaReportes(HashMap<String, String> listaReportes) {
		this.listaReportes = listaReportes;
	}

	public void comboChangeListener(AjaxBehaviorEvent event) {
		System.out.println(this.tipoReporte);
	}

	public String getValorReporte1() {
		return valorReporte1;
	}

	public void setValorReporte1(String valorReporte1) {
		this.valorReporte1 = valorReporte1;
	}

	public String getValorReporte2() {
		return valorReporte2;
	}

	public void setValorReporte2(String valorReporte2) {
		this.valorReporte2 = valorReporte2;
	}
	
	@PostConstruct
	public void init() {
		this.tipoReporte= "Texto";
		this.valorReporte1 = null;
		this.valorReporte2 = null;

		this.listaReportes = new HashMap<String, String>();
		this.listaReportes.put("Reporte 1","seleccionReporte1");
		this.listaReportes.put("Reporte 2", "seleccionReporte2");
		
		this.tipoReporte  = "Texto";
	}

	
}
