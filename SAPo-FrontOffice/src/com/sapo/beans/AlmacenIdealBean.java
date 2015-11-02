package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.datatypes.DataAlmacen;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@RequestScoped
public class AlmacenIdealBean {
	public AlmacenIdealBean() {
		productosAgregados = new ArrayList<DataProducto>();
		productosGenericosLista = new ArrayList<DataProducto>();
		productosEspecificosLista = new ArrayList<DataProducto>();
	}

	private DataAlmacen almacenIdeal;
	private int idAlmacenIdeal;
	private int idProductoSeleccionado;
	private String idProdEliminar;
	private List<DataProducto> productosAgregados;
	List<DataProducto> productosGenericosLista;
	List<DataProducto> productosEspecificosLista;
	@EJB
	AlmacenNegocio service;

	@EJB
	ProductoNegocio serviceProducto;

	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;

	public DataAlmacen getAlmacenIdeal() {
		return almacenIdeal;
	}

	public void setAlmacenIdeal(DataAlmacen almacenIdeal) {
		this.almacenIdeal = almacenIdeal;
	}

	public int getIdAlmacenIdeal() {
		return idAlmacenIdeal;
	}

	public void setIdAlmacenIdeal(int idAlmacenIdeal) {
		this.idAlmacenIdeal = idAlmacenIdeal;
	}

	public int getIdProductoSeleccionado() {
		return idProductoSeleccionado;
	}

	public void setIdProductoSeleccionado(int idProductoSeleccionado) {
		this.idProductoSeleccionado = idProductoSeleccionado;
	}

	public String getIdProdEliminar() {
		return idProdEliminar;
	}

	public void setIdProdEliminar(String idProdEliminar) {
		this.idProdEliminar = idProdEliminar;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	public List<DataProducto> getProductosAgregados() {
		return productosAgregados;
	}

	public void setProductosAgregados(List<DataProducto> productosAgregados) {
		this.productosAgregados = productosAgregados;
	}

	public List<DataProducto> getProductosGenericosLista() {
		return productosGenericosLista;
	}

	public void setProductosGenericosLista(
			List<DataProducto> productosGenericosLista) {
		this.productosGenericosLista = productosGenericosLista;
	}

	public List<DataProducto> getProductosEspecificosLista() {
		return productosEspecificosLista;
	}

	public void setProductosEspecificosLista(
			List<DataProducto> productosEspecificosLista) {
		this.productosEspecificosLista = productosEspecificosLista;
	}

	@PostConstruct
	public void init() {
		obtenerAlmacenIdeal();
		cargarProductosDisponibles();
		ocultarItemsAgregados();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		this.idProdEliminar = facesContext.getExternalContext()
				.getRequestParameterMap().get("idProdEliminar");
	}

	public void obtenerAlmacenIdeal() {
		DataAlmacen da = service.getAlmacenPorId(nav.getIdAlmacenActual());
		this.almacenIdeal = service
				.getAlmacenIdealPorId(da.getIdAlmacenIdeal());
		this.idAlmacenIdeal = this.almacenIdeal.getIdAlmacen();
		if (da.getIdAlmacenIdeal() > 0) {
			List<DataProducto> dataProductos = this.almacenIdeal.getProductos();
			// Si el producto no tiene foto se indica una por defecto.
			for (int i = 0; i < dataProductos.size(); i++) {
				if (dataProductos.get(i).getFotos().isEmpty()) {
					DataImagen dataImg = new DataImagen();
					dataImg.setIdImagen(1);
					dataProductos.get(i).getFotos().add(dataImg);// TODO Guardar
																	// en base
																	// una
																	// imagen
																	// por
																	// defecto y
																	// pasarle
																	// ese id.
				}
			}
			// this.almacenIdeal.setProductos(dataProductos);
		} else {
			this.idAlmacenIdeal = service.crearAlmacenIdeal(nav
					.getIdAlmacenActual());
		}
	}

	public void cargarProductosDisponibles() {
		// Son los productos genéricos y todos los del almacén.
		this.productosGenericosLista = this.serviceProducto
				.getProductosGenericos();
		this.productosEspecificosLista = this.service.getProductosDeAlmacen(nav
				.getIdAlmacenActual());
	}

	public String eliminar() {
		this.serviceProducto.eliminarProductoAlmacenIdeal(
				Integer.parseInt(this.idProdEliminar), this.idAlmacenIdeal);
		this.idProdEliminar = "0";
		init();
		return "/index.xhtml?faces-redirect=true";
	}

	public void agregarGenerico(DataProducto dp) {
		int idx = this.productosGenericosLista.indexOf(dp);
		DataProducto dataProducto = this.productosGenericosLista.get(idx);
		this.service.agregarGenericoAAlmacenIdeal(dataProducto,
				this.idAlmacenIdeal, nav.getIdAlmacenActual());
		init();
	}

	public void agregarEspecifico(DataProducto dp) {
		int idx = this.productosEspecificosLista.indexOf(dp);
		DataProducto dataProducto = this.productosEspecificosLista.get(idx);
		this.service.agregarEspecificoAAlmacenIdeal(dataProducto,
				this.idAlmacenIdeal);
		init();
	}

	public void ocultarItemsAgregados() {
		if (this.productosEspecificosLista.size() != 0
				&& this.almacenIdeal != null && this.almacenIdeal.getProductos().size() != 0) {
			for (int i = 0; i < this.productosEspecificosLista.size(); i++) {
				for (int j = 0; j < this.almacenIdeal.getProductos().size(); j++) {
					if (this.almacenIdeal.getProductos().get(j).getIdProducto() == productosEspecificosLista
							.get(i).getIdProducto()) {
						productosEspecificosLista.get(i).setAgregado(true);
					}
				}

			}
		}
		if (this.productosGenericosLista.size() != 0
				&& this.almacenIdeal != null && this.almacenIdeal.getProductos().size() != 0) {
			for (int i = 0; i < this.productosGenericosLista.size(); i++) {
				for (int j = 0; j < this.almacenIdeal.getProductos().size(); j++) {
					if (this.almacenIdeal.getProductos().get(j)
							.getIdProductoGenerico() == productosGenericosLista
							.get(i).getIdProducto()) {
						productosGenericosLista.get(i).setAgregado(true);
					}
				}
			}
		}
	}

	public String actualizarStockIdeal(int idProducto, int stock) {
		this.serviceProducto.actualizarStockIdeal(idProducto, stock);
		init();
		return "index.xhtml";
	}
}
