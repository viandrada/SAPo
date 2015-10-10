package com.sapo.beans;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import com.datatypes.DataAdministrador;
import com.sapo.ejb.AdministradorNegocio;
import com.sapo.utils.Encrypter;


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
	@Pattern(regexp = "^[\\w!#$%&�*+/=?`{|}~^-]+(?:\\.[\\w!#$%&�*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Email no v�lido.")
	private String email;
	@NotNull(message = "El tel�fono es requerido.")
	private int telefono;
	@NotNull(message = "Debe ingresar una contrase�a.")
	private String password;

	@EJB
	AdministradorNegocio adminNegocio;

	/*
	 * @EJB AdministradorBean service;
	 */

	public RegistroBean() {
		//this.dataAdmin = new DataAdministrador();
		// this.service = new AdministradorBean();
	}

	public RegistroBean(String nombre, String apellido, String email,
			int telefono, String password) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
	}
	
	public String registroAdmin() {
		
		DataAdministrador dataAdmin= new DataAdministrador();
		dataAdmin.setNombre(nombre);
		dataAdmin.setEmail(email);
		dataAdmin.setPassword(password);
		dataAdmin.setTelefono(telefono);
		
		String md5Hash = new Encrypter().MD5(password);
		dataAdmin.setPassword(md5Hash);
		System.out.println("Password encriptado");
		System.out.println(md5Hash);
		

		boolean ok = true;

		try {
			 adminNegocio.altaAdmin(dataAdmin);
			
		} catch (Exception e) {
			ok=false;
			e.printStackTrace();
		}

		if (ok) {
			System.out.println("Alta de Administrador exitosa");
			return "/index.xhtml?faces-redirect=true";
		} else {
			System.out.println("Error. El administrador no fue dado de alta.");
			return null;
		}
		
/*		this.dataAdmin.setNombre(this.nombre);
		this.dataAdmin.setEmail(this.email);
		this.dataAdmin.setPassword(this.password);

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
		
		// this.admin.setPassword(this.password);

		boolean ok = true;// service.altaAdmin(this.getAdmin());
		AdministradorNegocioRemote manager = null;
		Context context = null;

		try {
			
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
		}*/
	}

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String showResult() {
		return "result";
	}
	
	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
}
