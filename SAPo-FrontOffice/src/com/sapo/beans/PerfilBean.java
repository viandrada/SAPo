package com.sapo.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import com.datatypes.DataUsuario;
import com.sapo.ejb.UsuarioNegocio;

@ManagedBean
@RequestScoped
public class PerfilBean {

	public PerfilBean() {
	}

	private String nombre;
	private String email;
	private String passwordActual;
	private String passwordNueva;
	private boolean premium;
	private String css;
	private int idFoto;
	private Part foto;
	private HashMap<String, String> listaCSS;

	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;

	@EJB
	UsuarioNegocio usuarioNegocio;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPasswordActual() {
		return passwordActual;
	}

	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}

	public String getPasswordNueva() {
		return passwordNueva;
	}

	public void setPasswordNueva(String passwordNueva) {
		this.passwordNueva = passwordNueva;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public int getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(int idFoto) {
		this.idFoto = idFoto;
	}

	public Part getFoto() {
		return foto;
	}

	public void setFoto(Part foto) {
		this.foto = foto;
	}

	public HashMap<String, String> getListaCSS() {
		return listaCSS;
	}

	public void setListaCSS(HashMap<String, String> listaCSS) {
		this.listaCSS = listaCSS;
	}

	public void getDatosUsuario(int id) {
		DataUsuario dataUser = usuarioNegocio.getUsuarioPorId(id);
		this.email = dataUser.getEmail();
		this.nombre = dataUser.getNombre();
		this.premium = dataUser.isPremium();
		this.css = dataUser.getEstilo();
		this.idFoto = dataUser.getIdFoto();
	}

	@PostConstruct
	public void inti() {
		// TODO Cargar nombres de css -> como mejora: levantar los nombres de la
		// carpeta css de resources.
		getDatosUsuario(this.usuarioLogueado.getIdUsuario());
		if (this.idFoto == 0) {
			this.setIdFoto(1);
		}
		this.listaCSS = new HashMap<String, String>();
		this.listaCSS.put("Default", "areaTrabajo.css");
		this.listaCSS.put("Rosado", "rosado.css");
		this.listaCSS.put("Verde", "verde.css");

	}

	public void guardarEstilo() {
		this.usuarioNegocio.guardarEstilo(this.usuarioLogueado.getIdUsuario(),
				this.css);
		FacesMessage message = new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"OK",
				"Estilo guardado. Debes volver a iniciar sesión para poder ver tu nuevo estilo.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void guardarCambios() {
		DataUsuario du = new DataUsuario();
		du.setIdUsuario(this.usuarioLogueado.getIdUsuario());
		du.setEmail(this.email);
		du.setNombre(this.nombre);
		if (this.foto != null) {
			InputStream is;
			try {
				is = this.foto.getInputStream();

				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();
				is.close();

				du.setBytesFoto(buffer.toByteArray());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.usuarioNegocio.guardarCambios(du);
		actualizarLoginBean(du);
		inti();
		FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("OK");
        message.setDetail("Cambios guardados");
        FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void actualizarLoginBean(DataUsuario du) {
		this.usuarioLogueado.setEmail(du.getEmail());
		this.usuarioLogueado.setNombre(du.getNombre());
	}

	public void cambiarPassword() {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] hashedBytes = digest
					.digest((this.passwordActual).getBytes());

			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < hashedBytes.length; i++) {
				stringBuffer.append(Integer.toString(
						(hashedBytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			String md5Hash = stringBuffer.toString();
			this.setPasswordNueva(md5Hash);
			System.out.println("Password encriptado");
			System.out.println(md5Hash);
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

		DataUsuario du = new DataUsuario();
		du.setIdUsuario(this.usuarioLogueado.getIdUsuario());
		du.setPassword(this.passwordNueva);
		this.usuarioNegocio.cambiarPassword(du);
		
		FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("OK");
        message.setDetail("Contraseña cambiada");
        FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
