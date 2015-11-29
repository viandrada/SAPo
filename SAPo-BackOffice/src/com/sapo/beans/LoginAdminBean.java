package com.sapo.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.datatypes.DataAdministrador;
import com.sapo.ejb.AdministradorNegocio;
import com.sapo.utils.Encrypter;

@ManagedBean
@SessionScoped
public class LoginAdminBean {

	public LoginAdminBean() {
		// this.dataAdmin = new DataAdministrador();
	}

	// private DataAdministrador dataAdmin;
	@EJB
	AdministradorNegocio adminNegocio;
	@ManagedProperty(value = "#{navigationBean}")
	NavigationBean nav;

	private String email;
	private String password;
	private String redirect;
	private boolean logueado;

	public NavigationBean getNav() {
		return nav;
	}

	public void setNav(NavigationBean nav) {
		this.nav = nav;
	}

	/*
	 * public String login() { this.dataAdmin.setEmail(this.email);
	 * 
	 * 
	 * MessageDigest digest; try { digest = MessageDigest.getInstance("MD5");
	 * byte[] hashedBytes = digest.digest((this.password).getBytes());
	 * 
	 * StringBuffer stringBuffer = new StringBuffer(); for (int i = 0; i <
	 * hashedBytes.length; i++) {
	 * stringBuffer.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16)
	 * .substring(1)); } String md5Hash = stringBuffer.toString();
	 * this.dataAdmin.setPassword(md5Hash);
	 * System.out.println("Password encriptado"); System.out.println(md5Hash); }
	 * catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
	 * 
	 * boolean ok = adminNegocio.login(this.dataAdmin.getEmail(),
	 * this.dataAdmin.getPassword()); if(ok){ this.redirect = "Login OK!";
	 * this.logueado = true; return "/index.xhtml?faces-redirect=true"; }
	 * else{System.out.println("Todo mal"); return "error"; } }
	 */
	public String login() {

		FacesMessage message = null;

		if (this.email == null || this.password == null) {

			message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Error de credenciales", "email o contraseña vacios");
			FacesContext.getCurrentInstance().addMessage(null, message);

			System.out.println("Todo mal");
			return "error";
		} else {

			DataAdministrador dataAdmin = new DataAdministrador();

			dataAdmin.setEmail(email);
			String md5Hash = new Encrypter().MD5(password);
			dataAdmin.setPassword(md5Hash);

			boolean ok = adminNegocio.login(dataAdmin);

			if (ok) {
				this.redirect = "home.xhtml";
				this.nav.setRedirectTo("home.xhtml");
				this.logueado = true;

				message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Bienvenido", dataAdmin.getEmail());
				FacesContext.getCurrentInstance().addMessage(null, message);

				return "/index.xhtml";
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Error de credenciales",
						"email o contraseña incorrectos");
				FacesContext.getCurrentInstance().addMessage(null, message);
				// context.addCallbackParam("loggedIn", loggedIn);

				System.out.println("Todo mal");
				return "error";
			}

		}

	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/login.xhtml?faces-redirect=true";
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

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	/*
	 * public DataAdministrador getDataAdmin() { return dataAdmin; }
	 * 
	 * public void setDataAdmin(DataAdministrador dataAdmin) { this.dataAdmin =
	 * dataAdmin; }
	 */

	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}

}
