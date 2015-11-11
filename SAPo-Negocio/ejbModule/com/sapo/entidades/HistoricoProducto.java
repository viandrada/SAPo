package com.sapo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class HistoricoProducto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// NOV 2015
	// ESTO NO SE ESTÁ USANDO, SE PODRÍA BORRAR SI CONFIRMAMOS QUE NO ESTÁ EN USO
	//
	
	//Seccion comentarios
	//POR EL MOMENTO GUARDAMOS TODOS LOS DATOS, LA IDEA ES GUARDAR SOLO
	//LOS DEL PRODUCTO CON EL TIPO DE MOVIMIENTO Y LA FHECHA QUE SE HIZO,
	//COMO ASI TB LA REFERENCIA A ALMACEN Y USUARIO YA QUE NUNCA SE VAN A BORRAR 
	//SINO QUE SE VAN A DESHABILITAR,
	
	//ERA LO ANTERIOR NO?
	
	//YA AGREGUE EL ATRIBUTO ESTAACTIVO(boolean), EN DICHAS CLASES.
	
	
	//@SuppressWarnings("unchecked")utilizar esta estiqueta si se devuelven listas en los DAOs, 
	//por el momento no fue necesario
	
	///////////////////////////////////////---FIN---///////////////////////////////////////////////
	
	
	
	//DATOS DEL PRODUCTO	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idHistoricoProducto;
	private int idProducto;
    private Date fechaCreacion;	
    
    public HistoricoProducto() {
		super();
	}
    
	public int getIdHistoricoProducto() {
		return idHistoricoProducto;
	}

	private String nombreProducto;
	private float precio;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private List<Imagen> fotoProducto;
	private String atributos;
	private Date fechaAltaProd;
	private Date fechaMovimiento;
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private Categoria categoria;
	
	//DATOS DEL USUARIO
	private int idUsuario;
	private String nombreUsuario;
	private String email;
	private String password;
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private Imagen fotoUsuario;
	private boolean premium;
	
	//DATOS DEL ALMACEN
	private int idAlmacen;
	private String nombre;
	private String descripcion;
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private Imagen fotoAlmacen;
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private Usuario propietario;
	private Date fechaAltaAlmacen;
	
	
	public void setIdHistoricoProducto(int idHistoricoProducto) {
		this.idHistoricoProducto = idHistoricoProducto;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}
	public List<Imagen> getFotoProducto() {
		return fotoProducto;
	}
	public void setFotoProducto(List<Imagen> fotoProducto) {
		this.fotoProducto = fotoProducto;
	}
	public String getAtributos() {
		return atributos;
	}
	public void setAtributos(String atributos) {
		this.atributos = atributos;
	}
	public Date getFechaAltaProd() {
		return fechaAltaProd;
	}
	public void setFechaAltaProd(Date fechaAltaProd) {
		this.fechaAltaProd = fechaAltaProd;
	}
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Imagen getFotoUsuario() {
		return fotoUsuario;
	}
	public void setFotoUsuario(Imagen fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}
	public boolean isPremium() {
		return premium;
	}
	public void setPremium(boolean premium) {
		this.premium = premium;
	}
	public int getIdAlmacen() {
		return idAlmacen;
	}
	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}
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
	public Imagen getFotoAlmacen() {
		return fotoAlmacen;
	}
	public void setFotoAlmacen(Imagen fotoAlmacen) {
		this.fotoAlmacen = fotoAlmacen;
	}
	public Usuario getPropietario() {
		return propietario;
	}
	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}
	public Date getFechaAltaAlmacen() {
		return fechaAltaAlmacen;
	}
	public void setFechaAltaAlmacen(Date fechaAltaAlmacen) {
		this.fechaAltaAlmacen = fechaAltaAlmacen;
	}

}
