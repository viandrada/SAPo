package com.sapo.beans;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;

import com.datatypes.DataCategoria;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sapo.ejb.ProductoNegocio;
import com.sapo.utils.Atributo;
import com.sapo.utils.PartToByteArrayConverter;

@ManagedBean
@SessionScoped
public class EditarProductoGenericoBean {

	public EditarProductoGenericoBean() {
	}

	private DataProducto dataProducto;
	private List<Atributo> atributosVista;
	private String categoriaNueva;
	private String nombreAtributo;
	private String tipoAtributo;
	private String valorAtributoTexto;
	private double valorAtributoNumero;
	private Date valorAtributoFecha;
	private Part foto;
	private DataImagen dataImagen;
	private String tipoDato;
	private List<String> tipoDatoList;
	private boolean renderText;
	private boolean renderCalendar;
	@EJB
	ProductoNegocio service;
	@ManagedProperty(value = "#{navigationBean}")
	NavigationBean nav;

	public DataProducto getDataProducto() {
		return dataProducto;
	}

	public void setDataProducto(DataProducto dataProducto) {
		this.dataProducto = dataProducto;
	}

	public List<Atributo> getAtributosVista() {
		return atributosVista;
	}

	public void setAtributosVista(List<Atributo> atributosVista) {
		this.atributosVista = atributosVista;
	}

	public String getCategoriaNueva() {
		return categoriaNueva;
	}

	public void setCategoriaNueva(String categoriaNueva) {
		this.categoriaNueva = categoriaNueva;
	}

	public String getNombreAtributo() {
		return nombreAtributo;
	}

	public void setNombreAtributo(String nombreAtributo) {
		this.nombreAtributo = nombreAtributo;
	}

	public String getTipoAtributo() {
		return tipoAtributo;
	}

	public void setTipoAtributo(String tipoAtributo) {
		this.tipoAtributo = tipoAtributo;
	}

	public String getValorAtributoTexto() {
		return valorAtributoTexto;
	}

	public void setValorAtributoTexto(String valorAtributoTexto) {
		this.valorAtributoTexto = valorAtributoTexto;
	}

	public double getValorAtributoNumero() {
		return valorAtributoNumero;
	}

	public void setValorAtributoNumero(double valorAtributoNumero) {
		this.valorAtributoNumero = valorAtributoNumero;
	}

	public Date getValorAtributoFecha() {
		return valorAtributoFecha;
	}

	public void setValorAtributoFecha(Date valorAtributoFecha) {
		this.valorAtributoFecha = valorAtributoFecha;
	}

	public Part getFoto() {
		return foto;
	}

	public void setFoto(Part foto) {
		this.foto = foto;
	}

	public DataImagen getDataImagen() {
		return dataImagen;
	}

	public void setDataImagen(DataImagen dataImagen) {
		this.dataImagen = dataImagen;
	}

	public String getTipoDato() {
		return tipoDato;
	}

	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	public List<String> getTipoDatoList() {
		return tipoDatoList;
	}

	public void setTipoDatoList(List<String> tipoDatoList) {
		this.tipoDatoList = tipoDatoList;
	}

	public boolean isRenderText() {
		return renderText;
	}

	public void setRenderText(boolean renderText) {
		this.renderText = renderText;
	}

	public boolean isRenderCalendar() {
		return renderCalendar;
	}

	public void setRenderCalendar(boolean renderCalendar) {
		this.renderCalendar = renderCalendar;
	}

	public NavigationBean getNav() {
		return nav;
	}

	public void setNav(NavigationBean nav) {
		this.nav = nav;
	}

	@PostConstruct
	public void init() {
		atributosVista = new ArrayList<Atributo>();
		this.categoriaNueva = null;
		this.foto = null;
		this.tipoDatoList = new ArrayList<String>();
		this.tipoDatoList.add("Texto");
		this.tipoDatoList.add("Numero");
		this.tipoDatoList.add("Fecha");
		this.tipoDato = "Texto";
	}

	public void getProducto(String id) {

		this.dataProducto = service.getProductoGenericoPorId((Integer
				.parseInt(id)));

		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Atributo>>() {
		}.getType();
		Collection<Atributo> atribs = gson.fromJson(
				this.dataProducto.getAtributos(), collectionType);

		for (Iterator<Atributo> iterator = atribs.iterator(); iterator
				.hasNext();) {
			Atributo atributo = (Atributo) iterator.next();
			this.atributosVista.add(atributo);

		}
	}

	public String guardarProducto() {
		DataCategoria dataCategoria = new DataCategoria();
		DataProducto dataProductoModificado = new DataProducto();
		dataProductoModificado.setIdProducto(this.dataProducto.getIdProducto());
		dataProductoModificado.setNombre(this.dataProducto.getNombre());
		dataProductoModificado.setDescripcion(this.dataProducto
				.getDescripcion());
		dataProductoModificado.setEstaActivo(true);

		// Conversión de atributos genéricos a json
		Gson gson = new Gson();
		String json = gson.toJson(this.getAtributosVista());
		System.out.println(json);
		dataProductoModificado.setAtributos(json);
		// Fin de conversión a json

		// Procesando imagen...
		DataImagen dataImg = new DataImagen();
		List<DataImagen> imagenesData = new ArrayList<DataImagen>();
		if (this.foto != null) {
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto));
			this.setDataImagen(dataImg);
			imagenesData.add(this.dataImagen);
		} else {
			dataImg.setIdImagen(dataProducto.getFotos().get(0).getIdImagen());
			imagenesData.add(dataImg);
		}
		dataProductoModificado.setFotos(imagenesData);

		// Procesando categoría...
		if (this.categoriaNueva == null || this.categoriaNueva.isEmpty()) {
			dataCategoria.setIdCategoria(this.dataProducto.getIdCategoria());
		} else {
			dataCategoria.setNombre(this.categoriaNueva);
		}

		this.service.modificarProductoGenerico(dataProductoModificado,
				dataCategoria);

		this.init();
		this.nav.setRedirectTo("verProductos.xhtml");

		// Sol 1:
		/*
		 * try {
		 * FacesContext.getCurrentInstance().getExternalContext().redirect(
		 * "/index.xhtml"); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } //Sol 2: FacesContext context =
		 * FacesContext.getCurrentInstance(); String viewId =
		 * context.getViewRoot().getViewId(); ViewHandler handler =
		 * context.getApplication().getViewHandler(); UIViewRoot root =
		 * handler.createView(context, viewId); root.setViewId(viewId);
		 * context.setViewRoot(root);
		 */

		return "/index.xhtml";

	}

	// Para agregar atributo genérico nuevo a la lista.
	public String add() {
		Atributo a = new Atributo();
		a.setNombre(this.getNombreAtributo());
		a.setTipoDato(this.tipoDato);

		switch (this.getTipoDato()) {
		case "Texto":
			a.setValor(this.valorAtributoTexto);
			break;
		case "Fecha":
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			a.setValor(format.format(this.valorAtributoFecha));
			break;
		case "Numero":
			a.setValor(String.valueOf(this.valorAtributoNumero));
			break;
		default:
			this.renderText = true;
		}

		atributosVista.add(a);
		this.nombreAtributo = null;
		this.tipoAtributo = null;
		this.valorAtributoTexto = null;
		this.valorAtributoNumero = 0.0f;
		this.valorAtributoFecha = null;
		this.tipoDato = "Texto";
		return null;
	}

	public String eliminar(int index) {

		atributosVista.remove(index);

		return null;
	}

	public void comboChangeListener(AjaxBehaviorEvent event) {
		System.out.println(this.tipoDato);

	}

	public void handleDateSelect(AjaxBehaviorEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(format.format(this.valorAtributoFecha));
	}
}
