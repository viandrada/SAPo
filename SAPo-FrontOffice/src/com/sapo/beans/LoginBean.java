package com.sapo.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.datatypes.DataUsuario;
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
	@ManagedProperty(value="#{navigationBean}")
	NavigationBean nav;

    private String email;
    private String nombre;
    private String password;
    private boolean premium;
    private String redirect;
    private boolean logueado;
    private boolean shownLogin;
    private int contadorLogin;
    
 
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String login() {
		this.dataUsuario.setEmail(this.email);
		
		/*Encriptar password para compararlo con el encriptado de la BD*/
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] hashedBytes = digest.digest((this.password).getBytes());
			
			StringBuffer stringBuffer = new StringBuffer();
	        for (int i = 0; i < hashedBytes.length; i++) {
	            stringBuffer.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16)
	                    .substring(1));
	        }
			String md5Hash = stringBuffer.toString();
			this.dataUsuario.setPassword(md5Hash);
			System.out.println("Password encriptado");
			System.out.println(md5Hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        /*Fin de encriptar password*/
		
		boolean ok = usuarioNegocio.login(this.dataUsuario.getEmail(), this.dataUsuario.getPassword());
		if(ok){
			DataUsuario dataUser = usuarioNegocio.getUsuarioPorEmail(email);
			System.out.println("email "+dataUser.getEmail()+" - Nombre "+dataUser.getNombre());
			this.redirect = "Login OK!";
		    this.logueado = true;
		    //this.dataUsuario.setNombre(dataUser.getNombre());
		    this.nombre=dataUser.getNombre();
		    this.premium=dataUser.isPremium();
		    //System.out.println("nombe2 "+this.getNombre()+" - Nombre "+this.dataUsuario.getNombre());
		    this.nav.setRedirectTo("areaTrabajo.xhtml");
		    this.shownLogin = false;
		    return "/index.xhtml?faces-redirect=true";
		}
		else{System.out.println("Todo mal");
		return "error";}
	}
	
	 public String logout() {
	        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	        this.shownLogin = true;
	        this.nav.setRedirectTo("home.xhtml");
	        return "/login.xhtml?faces-redirect=true";
	    }
	 
	 public void loginExterno(String email){
		 DataUsuario dUsu = new DataUsuario();
		 dUsu.setEmail(email);
		 this.usuarioNegocio.loginExterno(dUsu);
	 }
}
