package com.sapo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
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
@Audited(withModifiedFlag = false, targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Producto implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idProducto;
	private String nombre;
	private String descripcion;
	
	@Audited(withModifiedFlag=true)
	private float precio;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private List<Imagen> foto;
	@Column(length = 1000)
	private String atributos;
	private Date fechaAlta;
	private boolean estaActivo;
	private boolean esIdeal;
	
	@Audited(withModifiedFlag=true)
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
	
	public Producto copiarProducto(){
		Producto copiado=new Producto();
		//apunto al mismo Almacen que su clon OJO!!!!!
		copiado.almacen=this.almacen;
		
		copiado.atributos=this.atributos;
		copiado.categoria=this.categoria;
		copiado.descripcion=this.descripcion;
		copiado.esIdeal=this.esIdeal;
		copiado.estaActivo=this.estaActivo;
		copiado.fechaAlta=this.fechaAlta;
		//copia de fotos// OJO se DUPLICA LISTA PERO FOTOS LAS COMPARTEN
		List<Imagen> fotoCopia=new LinkedList<Imagen>();
		if (!this.foto.isEmpty()){
			for (Imagen i: this.foto){
				
				Imagen x=new Imagen();
				x.setDatos(i.getDatos()); 
				//x.setIdImagen(i.getIdImagen());
				x.setMime(i.getMime());
				x.setNombre(i.getNombre());
				
				fotoCopia.add(/*i*/x);
			}
		}
		
		copiado.foto=fotoCopia;
		//copiado.idProducto=this.idProducto;
		copiado.nombre=this.nombre;
		copiado.precio=this.precio;
		//apunto al mismo producto generico que su clon OJO!!!!!!!
		copiado.productoGenerico=this.productoGenerico;
		copiado.stock=this.stock;
		copiado.stockIdeal=this.stockIdeal;
		//apunto al mismo USUARIO que su clon OJO!!!!!!!
		copiado.usuario=this.usuario;
		return copiado;
	}

	 /*public Object clone(){
	        Producto obj=null;
	        try{
	            obj=(Producto)super.clone();
	        }catch(CloneNotSupportedException ex){
	            System.out.println(" no se puede CLONAR");
	        }
	       	       
	        List<Imagen> fotoCopia=new LinkedList<Imagen>();
			if (!this.foto.isEmpty()){
				for (Imagen i: this.foto){
					fotoCopia.add(i.);
				}
			}
			
	    
	        return obj;
	    }*/

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
