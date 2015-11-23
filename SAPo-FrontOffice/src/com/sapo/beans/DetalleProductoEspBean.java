package com.sapo.beans;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.datatypes.Atributo;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sapo.ejb.ProductoNegocio;
//import com.sapo.utils.Atributo;

@ManagedBean
@RequestScoped
public class DetalleProductoEspBean {
	public DetalleProductoEspBean() {
	}

	@EJB
	ProductoNegocio productoNegocio;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	@ManagedProperty(value = "#{param.id}")
	private int id; // +setter

	private DataProducto producto;
	private List<DataImagen> fotos;
	private List<Atributo> atributosVista;

	public DataProducto getProducto() {
		return producto;
	}

	public void setProducto(DataProducto producto) {
		this.producto = producto;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<DataImagen> getFotos() {
		return fotos;
	}

	public void setFotos(List<DataImagen> fotos) {
		this.fotos = fotos;
	}

	public List<Atributo> getAtributosVista() {
		return atributosVista;
	}

	public void setAtributosVista(List<Atributo> atributosVista) {
		this.atributosVista = atributosVista;
	}

	public void obtenerProducto(int id) {
		this.producto = this.productoNegocio.getProductoPorId(id);
	}

	@PostConstruct
	public void init(int id) {
		// Map<String,String> params =
		// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		// String id = params.get("Id");
		obtenerProducto(id);
		this.fotos = new ArrayList<DataImagen>();
		for (int i = 0; i < this.producto.getFotos().size(); i++) {
			if (this.producto.getFotos().get(i).getIdImagen() != 0) {
				this.fotos.add(this.producto.getFotos().get(i));
			}
		}

		this.atributosVista = new ArrayList<Atributo>();
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Atributo>>() {
		}.getType();
		Collection<Atributo> atribs = gson.fromJson(
				this.producto.getAtributos(), collectionType);
		// Atributo a = gson.fromJson(this.dataProducto.getAtributos(),
		// Atributo.class);

		for (Iterator<Atributo> iterator = atribs.iterator(); iterator
				.hasNext();) {
			Atributo atributo = (Atributo) iterator.next();
			this.atributosVista.add(atributo);

		}

	}

	/*
	 * Este m�todo est� en caso que el usuario refresque la vista del
	 * detalle de producto. Si refresca no existe m�s el id. Con �sto si
	 * refresca lo redirecciona al almac�n de nuevo.
	 */
	@PreDestroy
	public void redireccion() {
		this.nav.setRedirectTo("almacen.xhtml");
	}
}
