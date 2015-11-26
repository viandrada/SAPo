package com.datatypes;

import java.util.Date;



public class DataReportePorAtributo implements Comparable<DataReportePorAtributo> {
	public DataReportePorAtributo() {
	}
	
	public DataReportePorAtributo(int mes,int anio, float ganancia) {

	}
	
	private String nombreProducto;
	private String tipoDato;
	private String valor;
	private Date valorFecha;
	private double valorNumero;
	private String nombre;
	private DataProducto producto;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Date getValorFecha() {
		return valorFecha;
	}

	public void setValorFecha(Date valorFecha) {
		this.valorFecha = valorFecha;
	}

	public double getValorNumero() {
		return valorNumero;
	}

	public void setValorNumero(double valorNumero) {
		this.valorNumero = valorNumero;
	}

	@Override
	public int compareTo(DataReportePorAtributo o) {
		
		/*if (this.orden<o.getOrden())
			return -1;
		else return 1;*/
		return 1;
					
		//return orden.compareTo(o.orden);
	}

	public DataProducto getProducto() {
		return producto;
	}

	public void setProducto(DataProducto producto) {
		this.producto = producto;
	}
	
	

}
