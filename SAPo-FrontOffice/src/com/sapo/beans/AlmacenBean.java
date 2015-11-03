package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataAlmacen;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@RequestScoped
public class AlmacenBean {

	public AlmacenBean() {
		this.listaCompras = new ArrayList<DataProducto>();
	}

	private DataAlmacen almacen;
	private List<DataProducto> productos;
	private List<DataProducto> listaCompras;
	@EJB
	AlmacenNegocio almacenNegocio;
	@EJB
	ProductoNegocio productoNegocio;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;

	public DataAlmacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(DataAlmacen almacen) {
		this.almacen = almacen;
	}

	public List<DataProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<DataProducto> productos) {
		this.productos = productos;
	}

	public List<DataProducto> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(List<DataProducto> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public void obtenerAlmacen() {
		this.almacen = almacenNegocio.getAlmacenPorId(nav.getIdAlmacenActual());
		List<DataProducto> dataProductos = this.obtenerProductos(nav
				.getIdAlmacenActual());
		// Si el producto no tiene foto se indica una por defecto.
		for (int i = 0; i < dataProductos.size(); i++) {
			if (dataProductos.get(i).getFotos().isEmpty()) {
				DataImagen dataImg = new DataImagen();
				dataImg.setIdImagen(1);
				dataProductos.get(i).getFotos().add(dataImg);// TODO Guardar en
																// base una
																// imagen por
																// defecto y
																// pasarle ese
																// id.
			}
		}
		this.almacen.setProductos(dataProductos);
	}

	public List<DataProducto> obtenerProductos(int idAlmacen) {
		List<DataProducto> dataProductos = new ArrayList<DataProducto>();
		dataProductos = almacenNegocio.getProductosDeAlmacen(idAlmacen);
		return dataProductos;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	@PostConstruct
	public void init() {
		obtenerAlmacen();
		generarLista();
	}

	public String actualizarStock(int idProducto, int stock) {
		this.productoNegocio.actualizarStock(idProducto, stock);
		init();
		return "index.xhtml";
	}

	public void generarLista() {
		DataAlmacen almacenReal = this.almacenNegocio.getAlmacenPorId(nav
				.getIdAlmacenActual());
		List<DataProducto> productosReal = this.obtenerProductos(nav
				.getIdAlmacenActual());
		List<DataProducto> productosIdeal = this.almacenNegocio
				.getAlmacenIdealPorId(almacenReal.getIdAlmacenIdeal())
				.getProductos();
		if (productosIdeal!=null){
			for (int i = 0; i < productosIdeal.size(); i++) {
	
				DataProducto dt = buscarProductoEnLista(productosReal,
						productosIdeal.get(i).getIdProducto());
				if (dt != null) {
					int difStock = productosIdeal.get(i).getStockIdeal()
							- dt.getStock();
					if (difStock != 0) {
						productosIdeal.get(i).setStock(productosIdeal.get(i).getStockIdeal());
						this.listaCompras.add(productosIdeal.get(i));
					}
				}
				if(dt == null){
					productosIdeal.get(i).setStock(productosIdeal.get(i).getStockIdeal());
					this.listaCompras.add(productosIdeal.get(i));
				}
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
}
