package com.sapo.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.sapo.datatypes.DataAdministrador;
import com.sapo.ejb.AdministradorNegocio;
import com.sapo.ejb.AdministradorNegocioRemote;
import com.sapo.utils.JNDILookup;

@ManagedBean
@RequestScoped
public class RegistroBean {

	@NotNull(message = "El nombre es requerido.")
	private String nombre;
	/*
	 * Regex sacada de:
	 * http://howtodoinjava.com/2014/11/11/java-regex-validate-email-address/
	 */
	@NotNull(message = "El email es requerido.")
	@Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Email no válido.")
	private String email;
	@NotNull(message = "El teléfono es requerido.")
	private String telefono;
	@NotNull(message = "Debe ingresar una contraseña.")
	private String password;

	@Inject
	DataAdministrador dataAdmin;
	@EJB
	AdministradorNegocio adminNegocio;

	/*
	 * @EJB AdministradorBean service;
	 */

	public RegistroBean() {
		this.dataAdmin = new DataAdministrador();
		// this.service = new AdministradorBean();
	}

	public RegistroBean(String nombre, String apellido, String email,
			String telefono, String password) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String showResult() {
		return "result";
	}


	public DataAdministrador getDataAdmin() {
		return dataAdmin;
	}

	public void setDataAdmin(DataAdministrador dataAdmin) {
		this.dataAdmin = dataAdmin;
	}

	public String registroAdmin() {
		this.dataAdmin.setNombre(this.nombre);
		this.dataAdmin.setEmail(this.email);
		this.dataAdmin.setPassword(this.password);

		/*
		 * Encriptar password ->
		 * http://www.codejava.net/coding/how-to-calculate-
		 * md5-and-sha-hash-values-in-java
		 */
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
			this.dataAdmin.setPassword(md5Hash);
			System.out.println("Password encriptado");
			System.out.println(md5Hash);
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		/* Fin de encriptar password */

		// this.admin.setPassword(this.password);

		boolean ok = true;// service.altaAdmin(this.getAdmin());
		AdministradorNegocioRemote manager = null;
		Context context = null;

		try {
			/*context = JNDILookup.getInitialContext();
			manager = (AdministradorNegocioRemote) context
					.lookup("ejb:SAPo-EAR/SAPo-Negocio//AdministradorNegocio!com.sapo.ejb.AdministradorNegocioRemote");
			manager.altaAdmin(this.nombre,this.email,this.password);*/
			
			adminNegocio.altaAdmin(this.nombre, this.email, this.password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ok) {
			System.out.println("Alta exitosa");
			return "/index.xhtml?faces-redirect=true";
		} else {
			System.out.println("Error. El admin no fue dado de alta.");
			return null;
		}
	}

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

}
