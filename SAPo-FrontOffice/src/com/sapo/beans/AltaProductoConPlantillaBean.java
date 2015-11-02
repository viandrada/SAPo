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

import com.datatypes.DataAlmacen;
import com.datatypes.DataCategoria;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.datatypes.DataUsuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sapo.ejb.ProductoNegocio;
import com.sapo.utils.Atributo;
import com.sapo.utils.PartToByteArrayConverter;

@ManagedBean
@SessionScoped
public class AltaProductoConPlantillaBean {
	public AltaProductoConPlantillaBean() {
		this.atributosVista = new ArrayList<Atributo>();
		this.atributosNuevosVista = new ArrayList<Atributo>();
		this.productoGenerico = new DataProducto();
		this.productoNuevo = new DataProducto();
		this.atributosVista = new ArrayList<Atributo>();
		this.fotos = new ArrayList<DataImagen>();
	}

	private DataProducto productoGenerico;
	private DataProducto productoNuevo;
	private List<Atributo> atributosVista;
	private List<Atributo> atributosNuevosVista;
	private List<DataImagen> fotos;

	private String nombreAtr;
	private String tipoDataAtr;
	private String valorAtr;

	private double valorAtributoNumero;
	private Date valorAtributoFecha;

	private boolean renderText;
	private boolean renderCalendar;

	private String tipoDato;
	private List<String> tipoDatoList;

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

	public DataProducto getProductoGenerico() {
		return productoGenerico;
	}

	public void setProductoGenerico(DataProducto productoGenerico) {
		this.productoGenerico = productoGenerico;
	}

	public DataProducto getProductoNuevo() {
		return productoNuevo;
	}

	public void setProductoNuevo(DataProducto productoNuevo) {
		this.productoNuevo = productoNuevo;
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

	public List<DataImagen> getFotos() {
		return fotos;
	}

	public void setFotos(List<DataImagen> fotos) {
		this.fotos = fotos;
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

	public List<String> getTipoDatoList() {
		return tipoDatoList;
	}

	public void setTipoDatoList(List<String> tipoDatoList) {
		this.tipoDatoList = tipoDatoList;
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
		this.atributosNuevosVista = new ArrayList<Atributo>();
		this.productoGenerico = new DataProducto();
		this.atributosVista = new ArrayList<Atributo>();
		this.foto = null;
		this.foto2 = null;
		this.foto3 = null;
		this.foto4 = null;
		this.fotos.clear();
		getPlantilla();
		this.tipoDatoList = new ArrayList<String>();
		this.tipoDatoList.add("Texto");
		this.tipoDatoList.add("Numero");
		this.tipoDatoList.add("Fecha");
		this.tipoDato = "Texto";
		this.tipoDataAtr = "Texto";
	}

	public void getPlantilla() {
		this.productoGenerico = this.service.getProductoGenericoPorId(nav
				.getIdPlantillaSeleccionada());

		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Atributo>>() {
		}.getType();
		Collection<Atributo> atribs = gson.fromJson(
				this.productoGenerico.getAtributos(), collectionType);

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
		a.setTipoDato(this.tipoDataAtr);

		switch (this.tipoDataAtr) {
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
		this.tipoDataAtr = "Texto";
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

	public void altaProducto() {
		DataAlmacen dataAlmacen = new DataAlmacen();
		dataAlmacen.setIdAlmacen(this.nav.getIdAlmacenActual());
		this.productoNuevo.setIdProductoGenerico(this.productoGenerico.getIdProducto());//Se guarda la referencia al producto genérico.
		this.productoNuevo.setNombre(this.productoGenerico.getNombre());
		this.productoNuevo.setDescripcion(this.productoGenerico.getDescripcion());
		this.productoNuevo.setEstaActivo(true);
		this.productoNuevo.setIdCategoria(this.productoGenerico.getIdCategoria());
		//El Stock, precio se cargan directamente del input.
		
		// Conversión de atributos genéricos a json
		Gson gson = new Gson();
		this.atributosVista.addAll(this.getAtributosNuevosVista());
		String json = gson.toJson(this.getAtributosVista());//Se incluyen los que agregó el usuario a los que ya tenía el genérico.
		System.out.println(json);
		this.productoNuevo.setAtributos(json);
		// Fin de conversión a json
		
		// Procesando imagenes...
		if (this.foto != null) {
			DataImagen dataImg = new DataImagen();
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto));
			this.getFotos().add(dataImg);
		}
		if (this.foto2 != null) {
			DataImagen dataImg = new DataImagen();
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto2));
			this.getFotos().add(dataImg);
		}
		if (this.foto3 != null) {
			DataImagen dataImg = new DataImagen();
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto3));
			this.getFotos().add(dataImg);
		}
		if (this.foto4 != null) {
			DataImagen dataImg = new DataImagen();
			dataImg.setDatos(PartToByteArrayConverter.toByteArray(this.foto4));
			this.getFotos().add(dataImg);
		}

		//Si no sube imagen, se guarda la del genérico.
		if(this.fotos.size() == 0){
			DataImagen dataImg = new DataImagen();
			dataImg.setIdImagen(this.productoGenerico.getFotos().get(0).getIdImagen());
			this.fotos.add(dataImg);
		}
		this.productoNuevo.setFotos(this.fotos);
		
		this.service.altaProductoDesdePlantilla(this.productoNuevo, dataAlmacen);
	}
}
