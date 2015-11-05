package com.sapo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataReporteAlmacen;
import com.datatypes.DataReporteProducto;


@ManagedBean
@RequestScoped
public class MovimientosUsuarioBean {

	
	private List<DataReporteAlmacen> listaMovsAlmacenes;
	private List<DataReporteProducto> listaMovsProductos;
	
	public MovimientosUsuarioBean(){
		listaMovsAlmacenes = new ArrayList<DataReporteAlmacen>();
		listaMovsProductos = new ArrayList<DataReporteProducto>();
	}
	
	@PostConstruct
	public void init(){
		/* HAY QUE PASAR EL ID DEL USUARIO.
		listaMovsAlmacenes = this.listaMovsAlmacenes(idUsuario);
		listaMovsProductos = this.listaMovsProductos(idUsuario);
		*/
	}

	public List<DataReporteAlmacen> getListaMovsAlmacenes() {
		return listaMovsAlmacenes;
	}

	public void setListaMovsAlmacenes(List<DataReporteAlmacen> listaMovsAlmacenes) {
		this.listaMovsAlmacenes = listaMovsAlmacenes;
	}

	public List<DataReporteProducto> getListaMovsProductos() {
		return listaMovsProductos;
	}

	public void setListaMovsProductos(List<DataReporteProducto> listaMovsProductos) {
		this.listaMovsProductos = listaMovsProductos;
	}
	
	

}
