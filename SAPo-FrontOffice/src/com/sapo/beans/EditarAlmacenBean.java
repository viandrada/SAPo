package com.sapo.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import org.hibernate.validator.constraints.NotEmpty;

import com.datatypes.DataAlmacen;
import com.datatypes.DataUsuario;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.UsuarioNegocio;

@ManagedBean
@RequestScoped
//@ViewScoped
//@SessionScoped
public class EditarAlmacenBean {

	public EditarAlmacenBean() {
		super();
		dataAlmacen = new DataAlmacen();
		//dataUsuario = new DataUsuario();
	}

	//private DataUsuario dataUsuario;
	private DataAlmacen dataAlmacen;
	
	@EJB
	AlmacenNegocio almacenNegocio;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	@NotEmpty(message="Ups, parece que no ingresaste un nombre.")
	
	//private Almacen almacen;
	
	private String nombre;
	private String descripcion;
	private Part imagen;
	//private String fileContent;
	
	
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

	/*public DataUsuario getDataUsuario() {
		return dataUsuario;
	}

	public void setDataUsuario(DataUsuario dataUsuario) {
		this.dataUsuario = dataUsuario;
	}*/

	public DataAlmacen getDataAlmacen() {
		return dataAlmacen;
	}

	public void setDataAlmacen(DataAlmacen dataAlmacen) {
		this.dataAlmacen = dataAlmacen;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public Part getImagen() {
		return imagen;
	}

	public void setImagen(Part imagen) {
		this.imagen = imagen;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	/*public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	*/
	
	public String editarAlmacen() {
		//boolean ok = false;
		//int idAlmacenGenerado = 0;
		 System.out.print("LLEGUE A EDITAR BEAN");
		 
		 System.out.print("NOMBRE ALMACEN: "+this.dataAlmacen.getNombre());
		 System.out.print("IDALMACEN: "+this.dataAlmacen.getIdAlmacen());
		try {
			/*
			 * fileContent = new Scanner(imagen.getInputStream()).useDelimiter(
			 * "\\A").next();
			 */
			// Esto es para pasar de InputStream a byte[]

			if (this.imagen != null) {
				 System.out.print("imagen es <>NULL");
				InputStream is = imagen.getInputStream();

				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();
				is.close();

				// Aca termina la conversion a byte[]

				this.dataAlmacen.setBytesFoto(buffer.toByteArray());
			}
			// System.out.print(fileContent);
		} catch (IOException e) {
			// Error handling
		}

		//this.dataAlmacen.setNombre(this.nombre);
		//this.dataAlmacen.setDescripcion(this.descripcion);
		
		//this.dataUsuario.setEmail(this.usuarioLogueado.getEmail());
		
		almacenNegocio.editarAlmacen(this.dataAlmacen/*, usuario*/);
		
		
		
	/*	idAlmacenGenerado = this.almacenNegocio.altaAlmacen(this.dataAlmacen,
				this.dataUsuario);

		if (idAlmacenGenerado != 0) {
			System.out.println("EDICION ALMACEN exitosa");
			//this.nav.setRedirectTo("almacen.xhtml");
			//this.nav.setIdAlmacenActual(idAlmacenGenerado);

			FacesContext facesContext = FacesContext.getCurrentInstance();

			ExternalContext externalContext = facesContext.getExternalContext();

			try {
				externalContext.redirect("/SAPo-FrontOffice/index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//return "/index.xhtml?faces-redirect=true";
		} else {
			System.out.println("Error. El almacen no fue EDITADO.");
			return null;
		}*/
		nav.setRedirectTo("homeUsuario.xhtml");
		return "/index.xhtml";
		//return nav.irAlmacen(idAlmacenGenerado);
	}
	
	public String bajaAlmacen(){
		
		//almacenNegocio.editarAlmacen(this.dataAlmacen/*, usuario*/);
		almacenNegocio.bajaAlmacen(this.dataAlmacen);
		nav.setRedirectTo("homeUsuario.xhtml");
		return "/index.xhtml";
		
	}

	public void getAlmacen (int idAlmacen){
		
		this.dataAlmacen = almacenNegocio.getAlmacenPorId(idAlmacen);
		
		System.out.println("GET ALMACEN ID EN BEAN ES ID VIENE DEL REPEART: "+dataAlmacen.getIdAlmacen());
		//this.nombre = dataAlmacen.getNombre();
		//this.descripcion = dataAlmacen.getDescripcion();
		
		//this.imagen =null; //dataAlmacen.;
		//this.fileContent = null;
	}
	
	@PostConstruct
	public void init(){
		
	//	this.nombre = null;
		//this.descripcion = null;
		this.imagen = null;
		//this.fileContent = null;
	}
}
