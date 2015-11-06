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
import com.sapo.ejb.CategoriaNegocio;
import com.sapo.ejb.ProductoNegocio;
import com.sapo.utils.Atributo;
import com.sapo.utils.PartToByteArrayConverter;

@ManagedBean
@SessionScoped
public class EditarProductoEspBean {

	public EditarProductoEspBean() {
		this.atributosVista = new ArrayList<Atributo>();
		this.atributosNuevosVista = new ArrayList<Atributo>();
		this.atributosVista = new ArrayList<Atributo>();
		this.fotos = new ArrayList<DataImagen>();
	}

	/* Categorías para mostrar en los combo box */
	private List<DataCategoria> listDataCat;// Categorías del usuario
	private List<DataCategoria> listDataCatGeneticas;// Categorías genéricas

	public String nombreCatSeleccionada;// Nombre de la categoría en caso de
										// crear una nueva

	private DataProducto productoAEditar;
	private List<DataImagen> fotos;

	private List<Atributo> atributosVista;
	private List<Atributo> atributosNuevosVista;
	private String nombreAtr;
	private String tipoDataAtr;
	private String valorAtr;
	private double valorAtributoNumero;
	private Date valorAtributoFecha;

	private String tipoDato;
	private List<String> tipoDatoList;

	private boolean renderText;
	private boolean renderCalendar;

	private Part foto;
	private Part foto2;
	private Part foto3;
	private Part foto4;

	@EJB
	ProductoNegocio service;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;
	@EJB
	CategoriaNegocio cNegocio;

	public List<DataCategoria> getListDataCat() {
		return listDataCat;
	}

	public void setListDataCat(List<DataCategoria> listDataCat) {
		this.listDataCat = listDataCat;
	}

	public List<DataCategoria> getListDataCatGeneticas() {
		return listDataCatGeneticas;
	}

	public void setListDataCatGeneticas(List<DataCategoria> listDataCatGeneticas) {
		this.listDataCatGeneticas = listDataCatGeneticas;
	}

	public String getNombreCatSeleccionada() {
		return nombreCatSeleccionada;
	}

	public void setNombreCatSeleccionada(String nombreCatSeleccionada) {
		this.nombreCatSeleccionada = nombreCatSeleccionada;
	}

	public DataProducto getProductoAEditar() {
		return productoAEditar;
	}

	public void setProductoAEditar(DataProducto productoAEditar) {
		this.productoAEditar = productoAEditar;
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

	public List<Atributo> getAtributosNuevosVista() {
		return atributosNuevosVista;
	}

	public void setAtributosNuevosVista(List<Atributo> atributosNuevosVista) {
		this.atributosNuevosVista = atributosNuevosVista;
	}

	public String getNombreAtr() {
		return nombreAtr;
	}

	public void setNombreAtr(String nombreAtr) {
		this.nombreAtr = nombreAtr;
	}

	public String getTipoDataAtr() {
		return tipoDataAtr;
	}

	public void setTipoDataAtr(String tipoDataAtr) {
		this.tipoDataAtr = tipoDataAtr;
	}

	public String getValorAtr() {
		return valorAtr;
	}

	public void setValorAtr(String valorAtr) {
		this.valorAtr = valorAtr;
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

	public Part getFoto() {
		return foto;
	}

	public void setFoto(Part foto) {
		this.foto = foto;
	}

	public Part getFoto2() {
		return foto2;
	}

	public void setFoto2(Part foto2) {
		this.foto2 = foto2;
	}

	public Part getFoto3() {
		return foto3;
	}

	public void setFoto3(Part foto3) {
		this.foto3 = foto3;
	}

	public Part getFoto4() {
		return foto4;
	}

	public void setFoto4(Part foto4) {
		this.foto4 = foto4;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	@PostConstruct
	public void init() {
		listDataCat = cNegocio.listDataCategoriasPersonal(this.usuarioLogueado
				.getEmail());
		listDataCatGeneticas = cNegocio.listDataCategoriasGenericas();
		this.listDataCat.addAll(this.listDataCatGeneticas);
		this.nombreCatSeleccionada = null;
		this.atributosNuevosVista = new ArrayList<Atributo>();
		this.atributosVista = new ArrayList<Atributo>();
		this.foto = null;
		this.foto2 = null;
		this.foto3 = null;
		this.foto4 = null;
		this.fotos.clear();
		this.tipoDatoList = new ArrayList<String>();
		this.tipoDatoList.add("Texto");
		this.tipoDatoList.add("Numero");
		this.tipoDatoList.add("Fecha");
		this.tipoDato = "Texto";
		this.tipoDataAtr = "Texto";
	}

	public void getProducto(String id) {

		init();// Para inicializar todo cada vez q se llama.
		this.productoAEditar = service.getProductoPorId((Integer.parseInt(id)));

		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Atributo>>() {
		}.getType();
		Collection<Atributo> atribs = gson.fromJson(
				this.productoAEditar.getAtributos(), collectionType);

		for (Iterator<Atributo> iterator = atribs.iterator(); iterator
				.hasNext();) {
			Atributo atributo = (Atributo) iterator.next();
			this.atributosVista.add(atributo);

		}
	}

	// Para agregar atributo genérico nuevo a la lista.
	public String add() {
		Atributo a = new Atributo();
		a.setNombre(this.nombreAtr);
		a.setTipoDato(this.tipoDato);

		switch (this.tipoDato) {
		case "Texto":
			a.setValor(this.valorAtr);
			break;
		case "Fecha":
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			a.setValorFecha(this.valorAtributoFecha);
			a.setValor(format.format(this.valorAtributoFecha));
			break;
		case "Numero":
			a.setValorNumero(this.valorAtributoNumero);
			a.setValor(String.valueOf(this.valorAtributoNumero));
			break;
		default:
			this.renderText = true;
		}

		this.atributosNuevosVista.add(a);
		a = new Atributo();
		this.nombreAtr = null;
		this.tipoDato = "Texto";
		this.valorAtr = null;
		this.valorAtributoNumero = 0.0f;
		this.valorAtributoFecha = null;
		this.tipoDato = "Texto";
		return null;
	}

	public void comboChangeListener(AjaxBehaviorEvent event) {
		System.out.println(this.tipoDato);

	}

	public void handleDateSelect(AjaxBehaviorEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(format.format(this.valorAtributoFecha));
	}

	public String guardarProducto() {

		DataCategoria dataCategoria = new DataCategoria();

		// Procesando categoría...
		if (this.nombreCatSeleccionada == null
				|| this.nombreCatSeleccionada.isEmpty()) {
			dataCategoria.setIdCategoria(this.productoAEditar.getIdCategoria());
		} else {
			
			dataCategoria.setNombre(this.nombreCatSeleccionada);
			dataCategoria.setEmailUsuario(this.usuarioLogueado.getEmail());
		}

		// Conversión de atributos genéricos a json
		Gson gson = new Gson();
		this.atributosVista.addAll(this.getAtributosNuevosVista());
		String json = gson.toJson(this.getAtributosVista());// Se incluyen los
															// que agregó el
															// usuario a los que
															// ya tenía el
															// genérico.
		System.out.println(json);
		this.productoAEditar.setAtributos(json);
		// Fin de conversión a json
		
		// Procesando imagenes...
		DataImagen dataImg = new DataImagen();
		List<DataImagen> imagenesData = new ArrayList<DataImagen>();
		if (this.foto != null) {
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto));
			imagenesData.add(dataImg);
		} else {
			if(!this.productoAEditar.getFotos().isEmpty() && this.productoAEditar.getFotos().get(0) != null){
			dataImg.setIdImagen(this.productoAEditar.getFotos().get(0).getIdImagen());
			imagenesData.add(dataImg);
			}
		}
		if (this.foto2 != null) {
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto2));
			imagenesData.add(dataImg);
		} else {
			if(!this.productoAEditar.getFotos().isEmpty() && this.productoAEditar.getFotos().size() > 1){
			dataImg.setIdImagen(this.productoAEditar.getFotos().get(1).getIdImagen());
			imagenesData.add(dataImg);
			}
		}
		if (this.foto3 != null) {
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto3));
			imagenesData.add(dataImg);
		} else {
			if(!this.productoAEditar.getFotos().isEmpty() && this.productoAEditar.getFotos().size() > 2){
			dataImg.setIdImagen(this.productoAEditar.getFotos().get(2).getIdImagen());
			imagenesData.add(dataImg);
			}
		}
		if (this.foto4 != null) {
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto4));
			imagenesData.add(dataImg);
		} else {
			if(!this.productoAEditar.getFotos().isEmpty() && this.productoAEditar.getFotos().size() > 3){
			dataImg.setIdImagen(this.productoAEditar.getFotos().get(3).getIdImagen());
			imagenesData.add(dataImg);
			}
		}
		this.productoAEditar.setFotos(imagenesData);

		this.service.modificarProductoEspecifico(this.productoAEditar,
				dataCategoria, usuarioLogueado.getIdUsuario());

		this.init();
		this.nav.setRedirectTo("verProductos.xhtml");

		return "/index.xhtml";

	}
}
