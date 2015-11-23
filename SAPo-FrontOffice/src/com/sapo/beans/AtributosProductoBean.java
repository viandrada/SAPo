package com.sapo.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import com.datatypes.Atributo;

//import com.sapo.utils.Atributo;

/*Esta clase no se usa por ahora. Es para implementar una posible mejora si da el tiempo (para que el altaProducto no sea SessionScoped).*/
@ManagedBean
@SessionScoped
public class AtributosProductoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtributosProductoBean() {
		this.atributosVista = new ArrayList<Atributo>();
	}

	private List<Atributo> atributosVista;
	private String nombreAtr;
	private String tipoDataAtr;
	private String valorAtr;
	private double valorAtributoNumero;
	private Date valorAtributoFecha;
	private boolean renderText;
	private List<String> tipoDatoList;

	public List<Atributo> getAtributos() {
		return atributosVista;
	}

	public void setAtributos(List<Atributo> atributos) {
		this.atributosVista = atributos;
	}

	public List<Atributo> getAtributosVista() {
		return atributosVista;
	}

	public void setAtributosVista(List<Atributo> atributosVista) {
		this.atributosVista = atributosVista;
	}

	public String getNombreAtr() {
		return nombreAtr;
	}

	public void setNombreAtr(String nombreAtr) {
		this.nombreAtr = nombreAtr;
	}

	public String getTipoDataAtr() {
		return tipoDataAtr;
	}

	public void setTipoDataAtr(String tipoDataAtr) {
		this.tipoDataAtr = tipoDataAtr;
	}

	public String getValorAtr() {
		return valorAtr;
	}

	public void setValorAtr(String valorAtr) {
		this.valorAtr = valorAtr;
	}

	public double getValorAtributoNumero() {
		return valorAtributoNumero;
	}

	public void setValorAtributoNumero(double valorAtributoNumero) {
		this.valorAtributoNumero = valorAtributoNumero;
	}

	public Date getValorAtributoFecha() {
		return valorAtributoFecha;
	}

	public void setValorAtributoFecha(Date valorAtributoFecha) {
		this.valorAtributoFecha = valorAtributoFecha;
	}

	public boolean isRenderText() {
		return renderText;
	}

	public void setRenderText(boolean renderText) {
		this.renderText = renderText;
	}

	public List<String> getTipoDatoList() {
		return tipoDatoList;
	}

	public void setTipoDatoList(List<String> tipoDatoList) {
		this.tipoDatoList = tipoDatoList;
	}

	@PostConstruct
	public void init() {
		this.nombreAtr = null;
		this.tipoDataAtr = "Texto";
		this.valorAtr = null;
		this.valorAtributoFecha = null;
		this.valorAtributoNumero = 0.0f;
		this.atributosVista = new ArrayList<Atributo>();
		this.tipoDatoList = new ArrayList<String>();
		this.tipoDatoList.add("Texto");
		this.tipoDatoList.add("Numero");
		this.tipoDatoList.add("Fecha");
		this.tipoDataAtr = "Texto";
	}

	public void comboChangeListener(AjaxBehaviorEvent event) {
		System.out.println(this.tipoDataAtr);
	}

	public void handleDateSelect(AjaxBehaviorEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(format.format(this.valorAtributoFecha));
	}

	public String add() {
		Atributo a = new Atributo();
		a.setNombre(this.getNombreAtr());
		a.setTipoDato(this.getTipoDataAtr());

		switch (this.getTipoDataAtr()) {
		case "Texto":
			a.setValor(this.valorAtr);
			break;
		case "Fecha":
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			a.setValor(format.format(this.valorAtributoFecha));
			break;
		case "Numero":
			a.setValor(String.valueOf(this.valorAtributoNumero));
			break;
		default:
			this.renderText = true;
		}

		this.atributosVista.add(a);
		this.nombreAtr = null;
		this.tipoDataAtr = "Texto";
		this.valorAtr = null;
		this.valorAtributoFecha = null;
		this.valorAtributoNumero = 0.0f;
		return null;
	}

}
