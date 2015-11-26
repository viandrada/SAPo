package com.datatypes;



public class DataAtributoAcumulado implements Comparable<DataAtributoAcumulado> {
	public DataAtributoAcumulado() {
	}
	
	public DataAtributoAcumulado(int mes,int anio, float ganancia) {
		/*this.mes=mes;
		this.anio=anio;
		this.gananciaMes=ganancia;
		this.orden=mes+anio;*/
	}

	

	/*private float gananciaMes;
	private int mes;
	private int anio;
	private int orden;*/
	private String nombre;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	/*public float getGananciaMes() {
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

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
*/
	@Override
	public int compareTo(DataAtributoAcumulado o) {
		
		/*if (this.orden<o.getOrden())
			return -1;
		else return 1;*/
		return 1;
					
		//return orden.compareTo(o.orden);
	}
	
	

}
