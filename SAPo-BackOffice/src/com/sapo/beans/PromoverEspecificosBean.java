package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataProductoCandidato;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@RequestScoped
public class PromoverEspecificosBean {
	public PromoverEspecificosBean() {
		productosCandidatos = new ArrayList<DataProductoCandidato>();
	}

	@EJB
	ProductoNegocio productoNegocio;
	private List<DataProductoCandidato> productosCandidatos;
	
	public List<DataProductoCandidato> getProductosCandidatos() {
		return productosCandidatos;
	}

	public void setProductosCandidatos(
			List<DataProductoCandidato> productosCandidatos) {
		this.productosCandidatos = productosCandidatos;
	}

	@PostConstruct
	public void init(){
		productosCandidatos = this.productoNegocio.getProductosCandidatosAPromocion();
	}
}
