package com.datatypes;

public class DataDatoGrafico {
	public DataDatoGrafico() {
	}
	
	public DataDatoGrafico(int mes,int anio, float ganancia) {
		this.mes=mes;
		this.anio=anio;
		this.gananciaMes=ganancia;
	}

	private float gananciaMes;
	private int mes;
	private int anio;
	public float getGananciaMes() {
		return gananciaMes;
	}

	public void setGananciaMes(float gananciaMes) {
		this.gananciaMes = gananciaMes;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}
	
	

}
