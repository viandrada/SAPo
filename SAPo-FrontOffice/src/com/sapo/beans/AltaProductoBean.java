package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

import com.datatypes.DataAlmacen;
import com.datatypes.DataCategoria;
import com.datatypes.DataProducto;
import com.sapo.ejb.AlmacenNegocio;

@ManagedBean
@RequestScoped
public class AltaProductoBean {

	public AltaProductoBean(){
		this.dataAlmacen = new DataAlmacen();
		this.dataCategoria = new DataCategoria();
		this.categoriasDelAlmacen = new ArrayList<DataCategoria>();
		this.dataProducto = new DataProducto();
	}
	@EJB
	AlmacenNegocio almacenNegocio;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	
	private DataAlmacen dataAlmacen;
	private DataCategoria dataCategoria;
	private List<DataCategoria> categoriasDelAlmacen;
	private DataProducto dataProducto;
	
	private String nombre;
	private String descripcion;
	private float precio;
	private String atributos;
	private List<byte[]> fotos;
	private Part foto;
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
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public String getAtributos() {
		return atributos;
	}
	public void setAtributos(String atributos) {
		this.atributos = atributos;
	}
	public List<byte[]> getFotos() {
		return fotos;
	}
	public void setFotos(List<byte[]> fotos) {
		this.fotos = fotos;
	}
	
	public List<DataCategoria> getCategoriasDelAlmacen() {
		return categoriasDelAlmacen;
	}
	public void setCategoriasDelAlmacen(List<DataCategoria> categoriasDelAlmacen) {
		this.categoriasDelAlmacen = categoriasDelAlmacen;
	}

	public Part getFoto() {
		return foto;
	}
	public void setFoto(Part foto) {
		this.foto = foto;
	}
	public NavigationAreaBean getNav() {
		return nav;
	}
	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}
	public DataAlmacen getDataAlmacen() {
		return dataAlmacen;
	}
	public void setDataAlmacen(DataAlmacen dataAlmacen) {
		this.dataAlmacen = dataAlmacen;
	}
	public DataCategoria getDataCategoria() {
		return dataCategoria;
	}
	public void setDataCategoria(DataCategoria dataCategoria) {
		this.dataCategoria = dataCategoria;
	}
	public DataProducto getDataProducto() {
		return dataProducto;
	}
	public void setDataProducto(DataProducto dataProducto) {
		this.dataProducto = dataProducto;
	}
	public void altaProducto(){
		this.dataAlmacen.setIdAlmacen(this.nav.getIdAlmacenActual());
		this.dataProducto.setNombre(this.nombre);
		this.dataProducto.setDescripcion(this.descripcion);
		this.dataProducto.setEstaActivo(true);
		this.dataProducto.setPrecio(this.precio);
		this.almacenNegocio.altaProducto(this.dataProducto, this.dataAlmacen, this.dataCategoria);
	}
}
