package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataAlmacen;
import com.datatypes.DataReporteProducto;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.ReporteNegocio;

@ManagedBean
@RequestScoped
public class HomeUsuarioBean {

	public HomeUsuarioBean() {
	}

	private List<DataAlmacen> almacenes;
	private List<DataReporteProducto> movimientos;
	private boolean sinAlmacenes;
	@EJB
	AlmacenNegocio almacenNegocio;
	@EJB
	ReporteNegocio reporteNegocio;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;

	public List<DataAlmacen> getAlmacenes() {
		return almacenes;
	}

	public void setAlmacenes(List<DataAlmacen> almacenes) {
		this.almacenes = almacenes;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public boolean isSinAlmacenes() {
		return sinAlmacenes;
	}

	public void setSinAlmacenes(boolean sinAlmacenes) {
		this.sinAlmacenes = sinAlmacenes;
	}

	public List<DataReporteProducto> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<DataReporteProducto> movimientos) {
		this.movimientos = movimientos;
	}

	@PostConstruct
	public void init() {
		listarAlmacenes();
		for (int i = 0; i < this.almacenes.size(); i++) {
			if (this.almacenes.get(i).getBytesFoto() == null) {
				this.almacenes.get(i).setIdFoto(1);// TODO Guardar en base una
													// imagen por defecto y
													// pasarle ese id.
			}
		}
		
		/*Si no tiene almacenes muestra un mensaje*/
		if (this.almacenes.size() == 0) {
			this.sinAlmacenes = true;
		}
		
		/*Carga los movimientos de stock por almac�n*/
		this.movimientos = new ArrayList<DataReporteProducto>();
		this.obtenerMovimientos();
	}

	public void listarAlmacenes() {
		this.almacenes = this.almacenNegocio.getAlmacenes(this.usuarioLogueado
				.getEmail());
	}

	public void obtenerMovimientos() {
		//por cada uno de los almacenes obtengo los últimos 5 movimientos de stock
		//capaz que estoy sobreescribiendo los datos... hay que revisar
		
		List<DataReporteProducto>listaTodosMovs=new ArrayList<DataReporteProducto>();
		for(int j = 0; j < this.almacenes.size(); j++ ){
			
			this.movimientos = this.reporteNegocio
				.buscarHistoricoCambioStockProdPorAlmacen(this.almacenes.get(j).getIdAlmacen());
			for (int i = 0; i < this.movimientos.size(); i++) {
				switch(this.movimientos.get(i).getTipoMovimiento())
				{
				case "ADD":
					this.movimientos.get(i).setTipoMovimiento("Agregado");
					break;
				case "MOD":
					this.movimientos.get(i).setTipoMovimiento("Editado");
					break;
				}
			}
			listaTodosMovs.addAll(this.movimientos);			
		}
		//Acá lo mejor sería ordenar por fecha y filtrar resultados (hacer top)
		
		this.movimientos = listaTodosMovs;
		
	}
}
