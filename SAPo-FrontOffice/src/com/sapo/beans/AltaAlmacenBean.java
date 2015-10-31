package com.sapo.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;

import com.datatypes.DataAlmacen;
import com.datatypes.DataUsuario;
import com.sapo.ejb.AlmacenNegocio;

@ManagedBean
@RequestScoped
public class AltaAlmacenBean {

	public AltaAlmacenBean() {
		super();
		dataAlmacen = new DataAlmacen();
		dataUsuario = new DataUsuario();
	}

	private DataUsuario dataUsuario;
	private DataAlmacen dataAlmacen;
	@EJB
	AlmacenNegocio almacenNegocio;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	private String nombre;
	private String descripcion;
	private Part imagen;
	private String fileContent;
	private int cantAlmacenesActuales;
	private int cantMaxAlmacenes;

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

	public DataUsuario getDataUsuario() {
		return dataUsuario;
	}

	public void setDataUsuario(DataUsuario dataUsuario) {
		this.dataUsuario = dataUsuario;
	}

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

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	
	
	public int getCantAlmacenesActuales() {
		return cantAlmacenesActuales;
	}

	public void setCantAlmacenesActuales(int cantAlmacenesActuales) {
		this.cantAlmacenesActuales = cantAlmacenesActuales;
	}

	public int getCantMaxAlmacenes() {
		return cantMaxAlmacenes;
	}

	public void setCantMaxAlmacenes(int cantMaxAlmacenes) {
		this.cantMaxAlmacenes = cantMaxAlmacenes;
	}

	public void getCantidadesAlmacenes(String emailUsr){;
		this.cantAlmacenesActuales=this.almacenNegocio.getCantidadAlmacenesDeUsuario(emailUsr);
		this.cantMaxAlmacenes=this.almacenNegocio.getCantidadMaximaAlmacenes(emailUsr);
	}
	
	public String altaAlmacen() {
		//boolean ok = false;
		int idAlmacenGenerado = 0;
		try {
			/*
			 * fileContent = new Scanner(imagen.getInputStream()).useDelimiter(
			 * "\\A").next();
			 */
			// Esto es para pasar de InputStream a byte[]

			if (this.imagen != null) {
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

		this.dataAlmacen.setNombre(this.nombre);
		this.dataAlmacen.setDescripcion(this.descripcion);
		this.dataUsuario.setEmail(this.usuarioLogueado.getEmail());

		idAlmacenGenerado = this.almacenNegocio.altaAlmacen(this.dataAlmacen,
				this.dataUsuario);

		if (idAlmacenGenerado != 0) {
			System.out.println("Alta almacen exitosa");
			//this.nav.setRedirectTo("almacen.xhtml");
			//this.nav.setIdAlmacenActual(idAlmacenGenerado);

			/*FacesContext facesContext = FacesContext.getCurrentInstance();

			ExternalContext externalContext = facesContext.getExternalContext();

			try {
				externalContext.redirect("/SAPo-FrontOffice/index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			//return "/index.xhtml?faces-redirect=true";
		} else {
			System.out.println("Error. El almacen no fue dado de alta.");
			return null;
		}
		
		//return "/index.xhtml";
		return nav.irAlmacen(idAlmacenGenerado);
	}

	@PostConstruct
	public void init(){
		getCantidadesAlmacenes(usuarioLogueado.getEmail());
		dataAlmacen = new DataAlmacen();
		dataUsuario = new DataUsuario();
		this.nombre = null;
		this.descripcion = null;
		this.imagen = null;
		this.fileContent = null;
	}
}
