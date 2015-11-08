package com.sapo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Null;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity implementation class for Entity: Almacen
 *
 */
@NamedQueries({

@NamedQuery(name = "Almacen.getAlmacenesUsuario.Email", query = "SELECT a FROM Almacen a WHERE a.propietario.email = :email") })
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Almacen implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAlmacen;
	private String nombre;
	private String descripcion;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private Imagen foto;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private Usuario propietario;

	//@OneToMany
	//@ManyToMany(mappedBy="almacenes",cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	@ManyToMany (cascade={CascadeType.REMOVE, CascadeType.MERGE})
	private List<Usuario> usuarios;
	
	/*
	 * @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE,
	 * CascadeType.MERGE}) private List<Usuario> listaUsuariosPropietarios;
	 */

	private Date fechaAlta;
	private boolean estaActivo;
	
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE,
			CascadeType.MERGE })
	private AlmacenIdeal almacenIdeal;
	

	public Almacen() {
		super();
		//usuarios= new LinkedList<Usuario>();
	}

	public Almacen(int id) {
		super();
		this.idAlmacen = id;
		
	}
		
/////////////////////////////////////////////////////////////////////////////////////
	@ManyToMany (cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private List<Comentario> comentarios;
	
	@ManyToMany
	private List<Categoria> categorias;

	@OneToMany(fetch = FetchType.EAGER,cascade={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
	private List<Producto> productos;

public void movProducto(Producto p) {
		System.out.println("AGREGUE EL PRODUCTO ESTOY EN ALMACEN: "+p.getNombre()+"AL ALMACEN : "+nombre);
		this.productos.add(p);
	}
	
	public void sacarProducto(Producto p) {
		/*List<Producto> listProd=this.productos;
		
		if (!listProd.isEmpty()){
			//ACA NO ENTRO NO SACO EL PRODUCTO
			for (Producto q :listProd){
				if (q.getIdProducto()==p.getIdProducto()){
					this.productos.remove(q);
					System.out.println("SAQUE EL PRODUCTO ESTOY EN ALMACEN: "+p.getNombre()+"AL ALMACEN : "+nombre);
				}
			}
		}*/
		System.out.println("LA LISTA DE PRODUCTOS ES VACIA= "+this.productos.isEmpty());
		if (!this.productos.isEmpty()){
			System.out.println("LA LISTA DE PRODUCTOS NO ES VACIA");
			//ACA NO ENTRO NO SACO EL PRODUCTO
			for (Producto q :this.productos){
				if (q.getIdProducto()==p.getIdProducto()){
					this.productos.remove(q);
					System.out.println("SAQUE EL PRODUCTO ESTOY EN ALMACEN: ("+p.getNombre()+"="+q.getNombre()+") AL ALMACEN : "+nombre);
				}
			}
		}
		
	}


public boolean EsUsuariodeEsteAlmacen(String emailUsuario) {
		boolean es = false;
		try {
			if(!usuarios.isEmpty()){
			for (Usuario u : usuarios) {
				if (u.getEmail().equals(emailUsuario)) {
					es = true;
				}
			}
			}
		} catch (Exception excep) {
			throw excep;
		}

		return es;
	}
	
	public boolean EsComentariodeAlmacen(int idComent) {
		boolean es = false;
		try {
			if(!comentarios.isEmpty()){
			for (Comentario c : comentarios) {
				if (c.getIdComentario()==idComent) {
					es = true;
				}
			}
			}
		} catch (Exception excep) {
			throw excep;
		}

		return es;
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	public void agregarUsuarioCompartido(Usuario u) {
		System.out.println("AGREGUE EL USUARIO: "+u.getEmail()+"AL ALMACEN : "+nombre);
		this.usuarios.add(u);
	}
	
	public void agregarComentario(Comentario c) {
		System.out.println("AGREGUE EL COMENTARIO: "+c.getContenido()+" AL ALMACEN : "+nombre);
		this.comentarios.add(c);
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

	public Imagen getFoto() {
		return foto;
	}

	public void setFoto(Imagen foto) {
		this.foto = foto;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Usuario getPropietario() {
		return propietario;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		System.out.println("ESTOY GUARDANDO EL USUARIO " + usuarios.get(0).getNombre()
				+ " EN EL ALMACEN " + nombre );
		this.usuarios = usuarios;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public AlmacenIdeal getAlmacenIdeal() {
		return almacenIdeal;
	}

	public void setAlmacenIdeal(AlmacenIdeal almacenIdeal) {
		this.almacenIdeal = almacenIdeal;
	}

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
	}
	
	/*
	 * public List<Usuario> getListaUsuariosPropietarios() { return
	 * listaUsuariosPropietarios; }
	 * 
	 * public void setListaUsuariosPropietarios(List<Usuario>
	 * listaUsuariosPropietarios) { this.listaUsuariosPropietarios =
	 * listaUsuariosPropietarios; }
	 */

}
