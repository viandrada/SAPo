package com.sapo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Almacen
 *
 */
@NamedQueries({

@NamedQuery(name = "Almacen.getAlmacenesUsuario.Email", query = "SELECT a FROM Almacen a WHERE a.propietario.email = :email") })
@Entity
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

	public Almacen() {
		super();
		//usuarios= new LinkedList<Usuario>();
	}

	public Almacen(int id) {
		super();
		this.idAlmacen = id;
		
	}

	@OneToMany
	private List<Comentario> comentarios;

	@ManyToMany
	private List<Categoria> categorias;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Producto> productos;

	public void agregarUsuarioCompartido(Usuario u) {
		System.out.println("AGREGUE EL USUARIO: "+u.getEmail()+"AL ALMACEN : "+nombre);
		this.usuarios.add(u);
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

	public Boolean getEstaActivo() {
		return estaActivo;
	}

	public void setEstaActivo(Boolean estaActivo) {
		this.estaActivo = estaActivo;
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
	/*
	 * public List<Usuario> getListaUsuariosPropietarios() { return
	 * listaUsuariosPropietarios; }
	 * 
	 * public void setListaUsuariosPropietarios(List<Usuario>
	 * listaUsuariosPropietarios) { this.listaUsuariosPropietarios =
	 * listaUsuariosPropietarios; }
	 */

}
