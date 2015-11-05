package com.datatypes;

import java.util.Date;
import java.util.List;

public class DataReporteProducto {
	
	
	public DataReporteProducto() {
	}

	private int idProducto;
	private String nombre;
	private String descripcion;
	private List<DataImagen> fotos;
	private float precio;
	private boolean estaActivo;
	//private boolean esIdeal;//Para el almac�n ideal
	//private boolean agregado;//Para el almac�n ideal
	private Date fechaAlta;
	private String atributos;
	private int stock;
	//private int stockIdeal;
	//private int idCategoria;
	private int idUsuario;
	private int idAlmacen;
	private String nombreCategoria;
	//private int idProductoGenerico;// En caso de usar un producto gen�rico como
									// plantilla.
	private String tipoMovimiento;
	
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
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
	public List<DataImagen> getFotos() {
		return fotos;
	}
	public void setFotos(List<DataImagen> fotos) {
		this.fotos = fotos;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	
	public boolean isEstaActivo() {
		return estaActivo;
	}
	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getAtributos() {
		return atributos;
	}
	public void setAtributos(String atributos) {
		this.atributos = atributos;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdAlmacen() {
		return idAlmacen;
	}
	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	public String getTipoMovimiento() {
		return tipoMovimiento;
	}
	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	
	

}
