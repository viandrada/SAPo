package com.sapo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@NamedQueries({

@NamedQuery(name = "Productos.getProductosDeAlmacen.IdAlmacen", query = "SELECT p FROM Producto p WHERE p.almacen.idAlmacen = :idAlmacen and p.esIdeal = FALSE") })
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;
	private String nombre;
	private String descripcion;
	private float precio;
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private List<Imagen> foto;
	@Column(length = 1000)
	private String atributos;
	private Date fechaAlta;
	private boolean estaActivo;
	private boolean esIdeal;
	private int stock;
	private int stockIdeal;

	@ManyToOne
	private Almacen almacen;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private Categoria categoria;
	
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private Usuario usuario;
	

	@OneToOne(cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private ProductoGenerico productoGenerico;

	public Producto() {
		super();
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public List<Imagen> getFoto() {
		return foto;
	}

	public void setFoto(List<Imagen> foto) {
		this.foto = foto;
	}

	public Almacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(Almacen almacen) {
		this.almacen = almacen;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setEstaActivo(boolean estaActivo) {
		this.estaActivo = estaActivo;
	}

	public boolean getEstaActivo() {
		return estaActivo;
	}

	public boolean isEsIdeal() {
		return esIdeal;
	}

	public void setEsIdeal(boolean esIdeal) {
		this.esIdeal = esIdeal;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStockIdeal() {
		return stockIdeal;
	}

	public void setStockIdeal(int stockIdeal) {
		this.stockIdeal = stockIdeal;
	}

	public ProductoGenerico getProductoGenerico() {
		return productoGenerico;
	}

	public void setProductoGenerico(ProductoGenerico productoGenerico) {
		this.productoGenerico = productoGenerico;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
