package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.datatypes.DataAlmacen;
import com.datatypes.DataCategoria;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.datatypes.DataUsuario;
import com.google.gson.Gson;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.CategoriaNegocio;
import com.sapo.utils.PartToByteArrayConverter;

@ManagedBean
@RequestScoped
public class AltaProductoBean {

	public AltaProductoBean() {
		this.dataAlmacen = new DataAlmacen();
		this.dataCategoria = new DataCategoria();
		this.dataProducto = new DataProducto();
		this.dataUsuario = new DataUsuario();
		this.fotos = new ArrayList<DataImagen>();
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

	@NotEmpty(message="Ups, parece que no ingresaste un nombre.")
	private String nombre;
	private String descripcion;
	@Min(value = 0, message="El precio no puede ser menor a 0.")
	private float precio;
	private String atributos;
	private List<DataImagen> fotos;
	private Part foto;
	private Part foto2;
	private Part foto3;
	private Part foto4;
	@Min(value = 0, message="El stock no puede ser menor a 0.")
	private int stock;
	private List<String> tipoDatoList;

	/* Categor�a */
	private List<DataCategoria> listDataCat;
	private List<DataCategoria> listDataCatGeneticas;

	private String nombreCatSeleccionada;
	private int idCatSeleccionada;
	private String catNueva;

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

	public void setAtributosProductoBean(AtributosProductoBean atributosProductoBean) {
		this.atributosProductoBean = atributosProductoBean;
	}

	@PostConstruct
	public void init() {
		listDataCat = cNegocio.listDataCategoriasPersonal(this.usuarioLogueado
				.getEmail());
		listDataCatGeneticas = cNegocio.listDataCategoriasGenericas();
		this.listDataCat.addAll(this.listDataCatGeneticas);
		/*if(!listDataCat.isEmpty()){
			this.idCatSeleccionada = listDataCat.get(0).getIdCategoria();
		}
		else if(!listDataCatGeneticas.isEmpty()){
			this.idCatSeleccionada = listDataCatGeneticas.get(0).getIdCategoria();
		}*/
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
	}

	public String altaProducto() {
		this.dataAlmacen.setIdAlmacen(this.nav.getIdAlmacenActual());
		this.dataProducto.setNombre(this.nombre);
		this.dataProducto.setDescripcion(this.descripcion);
		this.dataProducto.setEstaActivo(true);
		this.dataProducto.setEsIdeal(false);
		this.dataProducto.setPrecio(this.precio);
		this.dataProducto.setStock(this.stock);
		//this.dataProducto.setIdUsuario(this.idUsuario);

		// Conversi�n de atributos gen�ricos a json
		Gson gson = new Gson();
		// Type type = new TypeToken<List<Atributo>>() {}.getType();
		//String json = gson.toJson(this.getAtributosVista());
		String json = gson.toJson(this.atributosProductoBean.getAtributosVista());
		System.out.println(json);
		this.dataProducto.setAtributos(json);
		// Fin de conversi�n a json

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
		
		this.atributosProductoBean.init();
		return "almacen";
	}
}
