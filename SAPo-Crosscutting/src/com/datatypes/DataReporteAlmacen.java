package com.datatypes;

import java.util.Date;
import java.util.List;

public class DataReporteAlmacen {
	
	
public DataReporteAlmacen(){}
	
	private int idAlmacen;
	private int idAlmacenIdeal;
	private String nombre;
	private String descripcion;
	private int idFoto;
	private byte[] bytesFoto;
	private boolean isActivo;
	private Date fechaAlta;
	//private List<DataProducto> productos;
	private String tipoMovimiento;
	
	public int getIdAlmacen() {
		return idAlmacen;
	}
	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}
	public int getIdAlmacenIdeal() {
		return idAlmacenIdeal;
	}
	public void setIdAlmacenIdeal(int idAlmacenIdeal) {
		this.idAlmacenIdeal = idAlmacenIdeal;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdFoto() {
		return idFoto;
	}
	public void setIdFoto(int idFoto) {
		this.idFoto = idFoto;
	}
	public byte[] getBytesFoto() {
		return bytesFoto;
	}
	public void setBytesFoto(byte[] bytesFoto) {
		this.bytesFoto = bytesFoto;
	}
	public boolean isActivo() {
		return isActivo;
	}
	public void setActivo(boolean isActivo) {
		this.isActivo = isActivo;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	
	

}
