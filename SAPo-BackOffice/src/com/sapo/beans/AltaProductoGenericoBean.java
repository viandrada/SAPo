package com.sapo.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;

//import org.primefaces.event.SelectEvent;

import com.datatypes.DataCategoria;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.google.gson.Gson;
import com.sapo.ejb.CategoriaNegocio;
import com.sapo.ejb.ProductoNegocio;
import com.sapo.utils.Atributo;
import com.sapo.utils.PartToByteArrayConverter;

@ManagedBean
@SessionScoped
public class AltaProductoGenericoBean {

	public AltaProductoGenericoBean() {
	}

	private String nombre;
	private String descripcion;
	private List<DataCategoria> categorias;
	private int idCatSeleccionada;
	private String categoriaNueva;
	private List<Atributo> atributosVista;
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
	CategoriaNegocio cNegocio;
	@EJB
	ProductoNegocio productoNegocio;

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

	public List<DataCategoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<DataCategoria> categorias) {
		this.categorias = categorias;
	}

	public int getIdCatSeleccionada() {
		return idCatSeleccionada;
	}

	public void setIdCatSeleccionada(int idCatSeleccionada) {
		this.idCatSeleccionada = idCatSeleccionada;
	}

	public String getCategoriaNueva() {
		return categoriaNueva;
	}

	public void setCategoriaNueva(String categoriaNueva) {
		this.categoriaNueva = categoriaNueva;
	}

	public List<Atributo> getAtributosVista() {
		return atributosVista;
	}

	public void setAtributosVista(List<Atributo> atributosVista) {
		this.atributosVista = atributosVista;
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

	public void setValorAtributo(String valorAtributo) {
		this.valorAtributoTexto = valorAtributo;
	}

	public Date getValorAtributoFecha() {
		return valorAtributoFecha;
	}

	public void setValorAtributoFecha(Date valorAtributoFecha) {
		this.valorAtributoFecha = valorAtributoFecha;
	}

	public double getValorAtributoNumero() {
		return valorAtributoNumero;
	}

	public void setValorAtributoNumero(double valorAtributoNumero) {
		this.valorAtributoNumero = valorAtributoNumero;
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

	@PostConstruct
	public void init() {
		categorias = cNegocio.listDataCategoriasGenericas();
		atributosVista = new ArrayList<Atributo>();
		this.nombre = "";
		this.categoriaNueva = null;
		this.descripcion = "";
		this.tipoDatoList = new ArrayList<String>();
		this.tipoDatoList.add("Texto");
		this.tipoDatoList.add("Numero");
		this.tipoDatoList.add("Fecha");
		this.tipoDato = "Texto";
	}
	
	public void initPromover(String nombre){
		this.setNombre(nombre);
	}

	public String altaProductoGenerico() {

		DataCategoria dataCategoria = new DataCategoria();
		DataProducto dataProducto = new DataProducto();
		dataProducto.setNombre(this.nombre);
		dataProducto.setDescripcion(this.descripcion);
		dataProducto.setEstaActivo(true);

		// Conversi�n de atributos gen�ricos a json
		Gson gson = new Gson();
		String json = gson.toJson(this.getAtributosVista());
		System.out.println(json);
		dataProducto.setAtributos(json);
		// Fin de conversi�n a json

		// Procesando imagen...
		DataImagen dataImg = new DataImagen();
		List<DataImagen> imagenesData = new ArrayList<DataImagen>();
		if (this.foto != null) {
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto));
			this.setDataImagen(dataImg);
			imagenesData.add(this.dataImagen);
		}
		dataProducto.setFotos(imagenesData);

		// Procesando categor�a...
		if (this.categoriaNueva == null || this.categoriaNueva.isEmpty()) {
			dataCategoria.setIdCategoria(this.idCatSeleccionada);
		} else {
			dataCategoria.setNombre(this.categoriaNueva);
		}

		// Alta producto
		this.productoNegocio.altaProductoGenerico(dataProducto, dataCategoria);

		this.init();
		return "index";
	}

	// Para agregar atributo gen�rico nuevo a la lista.
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

	public void comboChangeListener(AjaxBehaviorEvent event) {
		System.out.println(this.tipoDato);
		
	}
	
	public void handleDateSelect(AjaxBehaviorEvent  event){

	    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    System.out.println(format.format(this.valorAtributoFecha));
	}
}
