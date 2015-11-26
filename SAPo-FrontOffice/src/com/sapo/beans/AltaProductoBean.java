package com.sapo.beans;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import com.datatypes.Atributo;
import com.datatypes.DataAlmacen;
import com.datatypes.DataCategoria;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.datatypes.DataUsuario;
import com.google.gson.Gson;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.CategoriaNegocio;
//import com.sapo.utils.Atributo;
import com.sapo.utils.PartToByteArrayConverter;

@ManagedBean
@SessionScoped
public class AltaProductoBean {

	public AltaProductoBean() {
		this.dataAlmacen = new DataAlmacen();
		this.dataCategoria = new DataCategoria();
		this.dataProducto = new DataProducto();
		this.dataUsuario = new DataUsuario();
		this.fotos = new ArrayList<DataImagen>();
		this.atributosVista = new ArrayList<Atributo>();
	}

	@EJB
	AlmacenNegocio almacenNegocio;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	@EJB
	CategoriaNegocio cNegocio;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;
	@ManagedProperty(value = "#{atributosProductoBean}")
	AtributosProductoBean atributosProductoBean;

	private DataAlmacen dataAlmacen;
	private DataCategoria dataCategoria;
	private DataProducto dataProducto;
	private DataUsuario dataUsuario;

	@NotEmpty(message = "Ups, parece que no ingresaste un nombre.")
	private String nombre;
	@Size(max = 255, message = "La descripci�n es muy larga.")
	private String descripcion;
	@Min(value = 0, message = "El precio no puede ser menor a 0.")
	private float precio;
	private String atributos;
	private List<DataImagen> fotos;
	private Part foto;
	private Part foto2;
	private Part foto3;
	private Part foto4;
	@Min(value = 0, message = "El stock no puede ser menor a 0.")
	private int stock;
	private List<String> tipoDatoList;

	/* Categor�a */
	private List<DataCategoria> listDataCat;
	private List<DataCategoria> listDataCatGeneticas;

	private String nombreCatSeleccionada;
	private int idCatSeleccionada;
	private String catNueva;

	private List<Atributo> atributosVista;
	private String nombreAtr;
	private String tipoDataAtr;
	private String valorAtr;
	private double valorAtributoNumero;
	private Date valorAtributoFecha;
	private boolean renderText;

	/* Getters and setteres */
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

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getAtributos() {
		return atributos;
	}

	public void setAtributos(String atributos) {
		this.atributos = atributos;
	}

	public List<DataImagen> getFotos() {
		return fotos;
	}

	public void setFotos(List<DataImagen> fotos) {
		this.fotos = fotos;
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

	public DataAlmacen getDataAlmacen() {
		return dataAlmacen;
	}

	public void setDataAlmacen(DataAlmacen dataAlmacen) {
		this.dataAlmacen = dataAlmacen;
	}

	public DataCategoria getDataCategoria() {
		return dataCategoria;
	}

	public void setDataCategoria(DataCategoria dataCategoria) {
		this.dataCategoria = dataCategoria;
	}

	public DataProducto getDataProducto() {
		return dataProducto;
	}

	public void setDataProducto(DataProducto dataProducto) {
		this.dataProducto = dataProducto;
	}

	public DataUsuario getDataUsuario() {
		return dataUsuario;
	}

	public void setDataUsuario(DataUsuario dataUsuario) {
		this.dataUsuario = dataUsuario;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public List<String> getTipoDatoList() {
		return tipoDatoList;
	}

	public void setTipoDatoList(List<String> tipoDatoList) {
		this.tipoDatoList = tipoDatoList;
	}

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

	public int getIdCatSeleccionada() {
		return idCatSeleccionada;
	}

	public void setIdCatSeleccionada(int idCatSeleccionada) {
		this.idCatSeleccionada = idCatSeleccionada;
	}

	public String getCatNueva() {
		return catNueva;
	}

	public void setCatNueva(String catNueva) {
		this.catNueva = catNueva;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public AtributosProductoBean getAtributosProductoBean() {
		return atributosProductoBean;
	}

	public void setAtributosProductoBean(
			AtributosProductoBean atributosProductoBean) {
		this.atributosProductoBean = atributosProductoBean;
	}

	public List<Atributo> getAtributosVista() {
		return atributosVista;
	}

	public void setAtributosVista(List<Atributo> atributosVista) {
		this.atributosVista = atributosVista;
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

	public boolean isRenderText() {
		return renderText;
	}

	public void setRenderText(boolean renderText) {
		this.renderText = renderText;
	}

	@PostConstruct
	public void init() {
		listDataCat = cNegocio.listDataCategoriasPersonal(this.usuarioLogueado
				.getEmail());
		listDataCatGeneticas = cNegocio.listDataCategoriasGenericas();
		this.listDataCat.addAll(this.listDataCatGeneticas);
		/*
		 * if(!listDataCat.isEmpty()){ this.idCatSeleccionada =
		 * listDataCat.get(0).getIdCategoria(); } else
		 * if(!listDataCatGeneticas.isEmpty()){ this.idCatSeleccionada =
		 * listDataCatGeneticas.get(0).getIdCategoria(); }
		 */
		this.nombreCatSeleccionada = null;
		this.nombre = "";
		this.stock = 0;
		this.precio = 0.0f;
		this.catNueva = null;
		this.descripcion = "";
		this.foto = null;
		this.foto2 = null;
		this.foto3 = null;
		this.foto4 = null;
		this.fotos.clear();

		this.nombreAtr = null;
		this.tipoDataAtr = "Texto";
		this.valorAtr = null;
		this.valorAtributoFecha = null;
		this.valorAtributoNumero = 0.0f;
		this.atributosVista = new ArrayList<Atributo>();
		this.tipoDatoList = new ArrayList<String>();
		this.tipoDatoList.add("Texto");
		this.tipoDatoList.add("Numero");
		this.tipoDatoList.add("Fecha");
		this.tipoDataAtr = "Texto";
	}

	public String altaProducto() {
		this.dataAlmacen.setIdAlmacen(this.nav.getIdAlmacenActual());
		this.dataProducto.setNombre(this.nombre);
		this.dataProducto.setDescripcion(this.descripcion);
		this.dataProducto.setEstaActivo(true);
		this.dataProducto.setEsIdeal(false);
		this.dataProducto.setPrecio(this.precio);
		this.dataProducto.setStock(this.stock);
		// this.dataProducto.setIdUsuario(this.idUsuario);

		// Conversi�n de atributos gen�ricos a json
		Gson gson = new Gson();
		// Type type = new TypeToken<List<Atributo>>() {}.getType();
		// String json = gson.toJson(this.getAtributosVista());
		String json = gson.toJson(this.atributosVista);
		System.out.println(json);
		this.dataProducto.setAtributos(json);
		// Fin de conversi�n a json

		// Procesando imagenes...
		/*if (this.foto != null) {
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
		}*/

		this.dataProducto.setFotos(this.fotos);

		// Procesando categor�a...
		if (this.catNueva == null || this.catNueva.isEmpty()) {
			this.dataCategoria.setIdCategoria(this.idCatSeleccionada);
		} else {
			this.dataCategoria.setNombre(this.catNueva);
		}

		this.dataUsuario.setEmail(this.usuarioLogueado.getEmail());

		// Alta producto
		this.almacenNegocio.altaProducto(this.dataProducto, this.dataAlmacen,
				this.dataCategoria, this.dataUsuario);
		this.init();
		this.atributosProductoBean.init();
		return "index";
	}

	public void comboChangeListener(AjaxBehaviorEvent event) {
		System.out.println(this.tipoDataAtr);
	}

	public void handleDateSelect(AjaxBehaviorEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(format.format(this.valorAtributoFecha));
	}

	/* �sto es para agregar atributos gen�ricos */
	public String add() {
		Atributo a = new Atributo();
		a.setNombre(this.getNombreAtr());
		a.setTipoDato(this.getTipoDataAtr());

		switch (this.getTipoDataAtr()) {
		case "Texto":
			a.setValor(this.valorAtr);
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

		this.atributosVista.add(a);
		this.nombreAtr = null;
		this.tipoDataAtr = "Texto";
		this.valorAtr = null;
		this.valorAtributoFecha = null;
		this.valorAtributoNumero = 0.0f;
		return null;
	}

	/* �sto es para subir imagenes con Richfaces */
	public void paint(OutputStream stream, Object object) throws IOException {
		stream.write(getFotos().get((Integer) object).getDatos());
		stream.close();
	}

	/* �sto es para subir imagenes con Richfaces */
	public void listener(FileUploadEvent event) throws Exception {
		UploadedFile item = event.getUploadedFile();
		DataImagen file = new DataImagen();
		file.setNombre(item.getName());
		file.setDatos(item.getData());
		fotos.add(file);
	}

	/* �sto es para subir imagenes con Richfaces */
	public String clearUploadData() {
		fotos.clear();
		return null;
	}

	/* �sto es para subir imagenes con Richfaces */
	public long getTimeStamp() {
		return System.currentTimeMillis();
	}
	
	/* �sto es para subir imagenes con Richfaces */
    public int getSize() {
        if (getFotos().size() > 0) {
            return getFotos().size();
        } else {
            return 0;
        }
    }
}
