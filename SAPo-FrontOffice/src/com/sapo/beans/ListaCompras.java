package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataAlmacen;
import com.datatypes.DataProducto;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@RequestScoped
public class ListaCompras {

	public ListaCompras() {
		listaCompras = new ArrayList<DataProducto>();
	}

	private List<DataProducto> listaCompras;

	public List<DataProducto> getListaCompras() {
		return listaCompras;
	}

	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	@EJB
	AlmacenNegocio almacenNegocio;
	@EJB
	ProductoNegocio productoNegocio;

	public void setListaCompras(List<DataProducto> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	@PostConstruct
	public void init() {
		DataAlmacen a = this.almacenNegocio.getAlmacenPorId(nav
				.getIdAlmacenActual());
		if (a.getIdAlmacenIdeal() != 0) {
			generarLista();
		}
	}

	public void generarLista() {
		this.listaCompras = new ArrayList<DataProducto>();
		DataAlmacen almacenReal = this.almacenNegocio.getAlmacenPorId(nav
				.getIdAlmacenActual());
		List<DataProducto> productosReal = this.obtenerProductos(nav
				.getIdAlmacenActual());
		List<DataProducto> productosIdeal = this.almacenNegocio
				.getAlmacenIdealPorId(almacenReal.getIdAlmacenIdeal())
				.getProductos();
		for (int i = 0; i < productosIdeal.size(); i++) {

			DataProducto dt = buscarProductoEnLista(productosReal,
					productosIdeal.get(i).getIdProducto());
			if (dt != null) {
				int difStock = productosIdeal.get(i).getStockIdeal()
						- dt.getStock();
				if (difStock > 0) {
					productosIdeal.get(i).setStock(
							productosIdeal.get(i).getStockIdeal() - dt.getStock());
					
					this.listaCompras.add(productosIdeal.get(i));
				}
			}
			if (dt == null) {
				productosIdeal.get(i).setStock(
						productosIdeal.get(i).getStockIdeal());
				this.listaCompras.add(productosIdeal.get(i));
			}
		}
	}

	public DataProducto buscarProductoEnLista(List<DataProducto> dataProducto,
			int idProducto) {

		for (int i = 0; i < dataProducto.size(); i++) {
			if (dataProducto.get(i).getIdProducto() == idProducto)
				return dataProducto.get(i);
		}
		return null;
	}

	public List<DataProducto> obtenerProductos(int idAlmacen) {
		List<DataProducto> dataProductos = new ArrayList<DataProducto>();
		dataProductos = almacenNegocio.getProductosDeAlmacen(idAlmacen);
		return dataProductos;
	}

	public String sincronizarLista(int idProductoObtenido, int idAlmacen) {
		System.out.println("Sincronizar Lista");
		this.almacenNegocio.sincronizarLista(idProductoObtenido, idAlmacen);
		generarLista();
		return "null";
	}
}
