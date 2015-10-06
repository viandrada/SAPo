package com.sapo.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sapo.datatypes.DataAdministrador;


@ManagedBean
@SessionScoped
public class LoginBean {
	
	

	public LoginBean() {
		this.dataAdmin = new DataAdministrador();
	}

	private com.sapo.datatypes.DataAdministrador dataAdmin;
	 

    private String email;
    private String password;
    private String redirect;
    private boolean logueado;
    
 
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


	
	public com.sapo.datatypes.DataAdministrador getDataAdmin() {
		return dataAdmin;
	}

	public void setDataAdmin(com.sapo.datatypes.DataAdministrador dataAdmin) {
		this.dataAdmin = dataAdmin;
	}

	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}

	public String login() {
		this.dataAdmin.setEmail(this.email);
		
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
			this.dataAdmin.setPassword(md5Hash);
			System.out.println("Password encriptado");
			System.out.println(md5Hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        /*Fin de encriptar password*/
		
		//this.admin.setPassword(this.password);
		boolean ok = true;
		if(ok){
			this.redirect = "Login OK!";
		    this.logueado = true;
		    return "/index.xhtml?faces-redirect=true";
		}
		else{System.out.println("Todo mal");
		return "error";}
	}
	
	 public String logout() {
	        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	        return "/login.xhtml?faces-redirect=true";
	    }
}
