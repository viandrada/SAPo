package com.sapo.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

import com.datatypes.DataAlmacen;
import com.datatypes.DataCategoria;
import com.datatypes.DataProducto;
import com.datatypes.DataUsuario;
import com.google.gson.Gson;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.CategoriaNegocio;
import com.sapo.utils.Atributo;
import com.sapo.utils.PartToByteArrayConverter;

@ManagedBean
@SessionScoped
public class AltaProductoBean {

	public AltaProductoBean() {
		this.dataAlmacen = new DataAlmacen();
		this.dataCategoria = new DataCategoria();
		this.dataProducto = new DataProducto();
		this.dataUsuario = new DataUsuario();
		this.atributosVista = new ArrayList<Atributo>();
		this.fotos = new ArrayList<byte[]>();
	}

	@EJB
	AlmacenNegocio almacenNegocio;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	@EJB
	CategoriaNegocio cNegocio;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;

	private DataAlmacen dataAlmacen;
	private DataCategoria dataCategoria;
	private DataProducto dataProducto;
	private DataUsuario dataUsuario;

	private String nombre;
	private String descripcion;
	private float precio;
	private String atributos;
	private List<byte[]> fotos;
	private Part foto;
	private Part foto2;
	private Part foto3;
	private Part foto4;
	private int stock;
	private List<Atributo> atributosVista;

	private String nombreAtr;
	private String tipoDataAtr;
	private String valorAtr;

	/*Categoría*/
	private List<DataCategoria> listDataCat;
	private List<DataCategoria> listDataCatGeneticas;

	public String nombreCatSeleccionada;
	public int idCatSeleccionada;
	private String catNueva;
	
	/*Getters and setteres*/
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

	public List<byte[]> getFotos() {
		return fotos;
	}

	public void setFotos(List<byte[]> fotos) {
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

	@PostConstruct
	public void init() {
		listDataCat=cNegocio.listDataCategorias();
		listDataCatGeneticas=cNegocio.listDataCategoriasGenericas();
		nombreCatSeleccionada="Vacío";
		idCatSeleccionada=1;
		this.nombre = "";
		this.stock = 0;
		this.precio = 0.0f;
		this.catNueva = "";
		this.descripcion = "";
		this.atributosVista = new ArrayList<Atributo>();
	}

	public String altaProducto() {
		this.dataAlmacen.setIdAlmacen(this.nav.getIdAlmacenActual());
		this.dataProducto.setNombre(this.nombre);
		this.dataProducto.setDescripcion(this.descripcion);
		this.dataProducto.setEstaActivo(true);
		this.dataProducto.setPrecio(this.precio);
		this.dataProducto.setStock(this.stock);

		// Conversión de atributos genéricos a json
		Gson gson = new Gson();
		// Type type = new TypeToken<List<Atributo>>() {}.getType();
		String json = gson.toJson(this.getAtributosVista());
		System.out.println(json);
		this.dataProducto.setAtributos(json);
		// Fin de conversión a json

		// Procesando imagenes...
		if (this.foto != null) {
			this.getFotos()
					.add(PartToByteArrayConverter.toByteArray(this.foto));
		}
		if (this.foto2 != null) {
			this.getFotos().add(
					PartToByteArrayConverter.toByteArray(this.foto2));
		}
		if (this.foto3 != null) {
			this.getFotos().add(
					PartToByteArrayConverter.toByteArray(this.foto3));
		}
		if (this.foto4 != null) {
			this.getFotos().add(
					PartToByteArrayConverter.toByteArray(this.foto4));
		}

		this.dataProducto.setFoto(this.fotos);

		//Procesando categoría...
		if(this.catNueva.isEmpty())
		{
			this.dataCategoria.setIdCategoria(this.idCatSeleccionada);
		}
		else{
			this.dataCategoria.setNombre(this.catNueva);
		}
		
		this.dataUsuario.setEmail(this.usuarioLogueado.getEmail());
		
		//Alta producto
		this.almacenNegocio.altaProducto(this.dataProducto, this.dataAlmacen,
				this.dataCategoria, this.dataUsuario);
		return "almacen";
	}

	// Para agregar atributo genérico nuevo a la lista.
	public String add() {
		Atributo a = new Atributo();
		a.setNombre(this.getNombreAtr());
		a.setTipoDato(this.getTipoDataAtr());
		a.setValor(this.getValorAtr());
		atributosVista.add(a);
		this.nombreAtr = null;
		this.tipoDataAtr = null;
		this.valorAtr = null;
		return null;
	}
}
