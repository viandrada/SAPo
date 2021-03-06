package com.datatypes;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataAlmacen {
	
	public DataAlmacen(){}
	
	private int idAlmacen;
	private int idAlmacenIdeal;
	private String nombre;
	private String descripcion;
	private int idFoto;
	private byte[] bytesFoto;
	private boolean isActivo;
	private Date fechaAlta;
	private List<DataProducto> productos;
	
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
	public boolean isActivo() {
		return isActivo;
	}
	
	public byte[] getBytesFoto() {
		return bytesFoto;
	}
	public void setBytesFoto(byte[] bytesFoto) {
		this.bytesFoto = bytesFoto;
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
	public List<DataProducto> getProductos() {
		return productos;
	}
	public void setProductos(List<DataProducto> productos) {
		this.productos = productos;
	}

}
