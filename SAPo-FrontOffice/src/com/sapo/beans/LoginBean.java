package com.sapo.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.datatypes.DataNotificacion;
import com.datatypes.DataUsuario;
import com.sapo.ejb.NotificacionNegocio;
import com.sapo.ejb.UsuarioNegocio;

@ManagedBean
@SessionScoped
public class LoginBean {

	public LoginBean() {
		this.dataUsuario = new DataUsuario();
		this.shownLogin = true;
	}

	private DataUsuario dataUsuario;
	@EJB
	UsuarioNegocio usuarioNegocio;
	@ManagedProperty(value = "#{navigationBean}")
	NavigationBean nav;
	private List<DataNotificacion> notificaciones;
	private int cantNotificaciones;
	@EJB
	NotificacionNegocio notificacionService;

	private String email;
	private int idUsuario;
	private String nombre;
	private String password;
	private boolean premium;
	private String redirect;
	private boolean logueado;
	private boolean shownLogin;
	private int contadorLogin;
	private String estilo;
	private boolean googleLogin;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}

	public boolean isShownLogin() {
		return shownLogin;
	}

	public void setShownLogin(boolean shownLogin) {
		this.shownLogin = shownLogin;
	}

	public NavigationBean getNav() {
		return nav;
	}

	public void setNav(NavigationBean nav) {
		this.nav = nav;
	}

	public int getContadorLogin() {
		return contadorLogin;
	}

	public void setContadorLogin(int contadorLogin) {
		this.contadorLogin = contadorLogin;
	}

	public DataUsuario getDataUsuario() {
		return dataUsuario;
	}

	public void setDataUsuario(DataUsuario dataUsuario) {
		this.dataUsuario = dataUsuario;
	}

	public List<DataNotificacion> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<DataNotificacion> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public int getCantNotificaciones() {
		return cantNotificaciones;
	}

	public void setCantNotificaciones(int cantNotificaciones) {
		this.cantNotificaciones = cantNotificaciones;
	}

	public String getEstilo() {
		return estilo;
	}

	public void setEstilo(String estilo) {
		this.estilo = estilo;
	}

	public boolean isGoogleLogin() {
		return googleLogin;
	}

	public void setGoogleLogin(boolean googleLogin) {
		this.googleLogin = googleLogin;
	}

	@PostConstruct
	public void init() {
		this.notificaciones = new ArrayList<DataNotificacion>();
	}

	public String login() {
		this.dataUsuario.setEmail(this.email);

		/* Encriptar password para compararlo con el encriptado de la BD */
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] hashedBytes = digest.digest((this.password).getBytes());

			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < hashedBytes.length; i++) {
				stringBuffer.append(Integer.toString(
						(hashedBytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			String md5Hash = stringBuffer.toString();
			this.dataUsuario.setPassword(md5Hash);
			System.out.println("Password encriptado");
			System.out.println(md5Hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		/* Fin de encriptar password */

		boolean ok = usuarioNegocio.login(this.dataUsuario.getEmail(),
				this.dataUsuario.getPassword());
		if (ok) {
			DataUsuario dataUser = usuarioNegocio.getUsuarioPorEmail(email);
			System.out.println("email " + dataUser.getEmail() + " - Nombre "
					+ dataUser.getNombre());
			this.redirect = "Login OK!";
			this.logueado = true;

			this.idUsuario = dataUser.getIdUsuario();
			this.nombre = dataUser.getNombre();
			this.premium = dataUser.isPremium();
			this.estilo = dataUser.getEstilo();

			this.nav.setRedirectTo("areaTrabajo.xhtml");
			this.shownLogin = false;

			this.generarNotificaciones();
			this.obtenerNotificaciones();
			return "/index.xhtml?faces-redirect=true";
		} else {
			System.out.println("Todo mal");
			return "error";
		}
	}

	public String logout() {
	
		if (!this.googleLogin) {
			FacesContext.getCurrentInstance().getExternalContext()
			.invalidateSession();
			this.shownLogin = true;
			this.nav.setRedirectTo("home.xhtml");
			return "/login.xhtml?faces-redirect=true";
		}
		return null;
	}

	public void logoutGmail() {
		
		this.email = null;
		this.idUsuario = 0;
		this.nombre = null;
		this.premium = false;
		this.redirect = "home.xhtml";
		this.logueado = false;
		this.contadorLogin = 0;
		this.estilo = "areaTrabajo.css";
		this.googleLogin = false;
		
		this.shownLogin = true;
		this.nav.setRedirectTo("home.xhtml");
	}
	
	public void loginExterno(String email) {
		DataUsuario dUsu = new DataUsuario();
		dUsu.setEmail(email);
		this.usuarioNegocio.loginExterno(dUsu);
	}

	public void obtenerNotificaciones() {
		this.notificaciones = this.notificacionService
				.obtenerNotificaciones(this.getIdUsuario());
		this.cantNotificaciones = this.notificaciones.size();
	}

	public void generarNotificaciones() {
		this.notificacionService.generarNotificaciones(this.getIdUsuario());
	}

	public void leidas() {
		this.notificacionService.actualizarNotificaciones(this.notificaciones);
		this.cantNotificaciones = 0;
	}

	public void refreshSession(DataUsuario du) {
		this.email = du.getEmail();
		this.idUsuario = du.getIdUsuario();
		this.nombre = du.getNombre();
		this.premium = du.isPremium();
		this.logueado = true;
		this.estilo = du.getEstilo();
		this.googleLogin = true;
	}
}
