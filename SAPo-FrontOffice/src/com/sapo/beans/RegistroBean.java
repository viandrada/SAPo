package com.sapo.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.datatypes.DataUsuario;
import com.sapo.ejb.UsuarioNegocio;

@ManagedBean(name = "registroBean")
@RequestScoped
public class RegistroBean {

	@NotNull(message = "El nombre es requerido.")
	private String nombre;
	/*
	 * Regex sacada de:
	 * http://howtodoinjava.com/2014/11/11/java-regex-validate-email-address/
	 */
	@NotNull(message = "El email es requerido.")
	@Pattern(regexp = "^[\\w!#$%&*+/=?`{|}~^-]+(?:\\.[\\w!#$%&*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "Email no v�lido.")
	private String email;
	@NotNull(message = "El teléfono es requerido.")
	private String telefono;
	@NotNull(message = "Debe ingresar una contraseña.")
	private String password;
	
	//@NotNull(message = "Debe ingresar una contraseña.")
	//private String password2;
	
	private String mensajeErrorPassword;
		
	//@NotNull(message = "Debe ingresar una contraseña.")
	//private String password3;

	private boolean existeUsuario;

	public String getMensajeErrorPassword() {
		return mensajeErrorPassword;
	}

	public void setMensajeErrorPassword(String mensajeErrorPassword) {
		this.mensajeErrorPassword = mensajeErrorPassword;
	}

	/*public String getPassword3() {
		return password3;
	}

	public void setPassword3(String password3) {
		this.password3 = password3;
	}
*/
	DataUsuario dataUsuario;
	@EJB
	UsuarioNegocio usuarioNegocio;

	/*
	 * @EJB AdministradorBean service;
	 */

	public RegistroBean() {
		this.dataUsuario = new DataUsuario();
		// this.service = new AdministradorBean();
	}

	public RegistroBean(String nombre, String apellido, String email,
			String telefono, String password) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
		this.existeUsuario = false;
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

	public DataUsuario getDataUsuario() {
		return dataUsuario;
	}

	public void setDataUsuario(DataUsuario dataUsuario) {
		this.dataUsuario = dataUsuario;
	}

	public boolean existeUsu() {
		return usuarioNegocio.existeUsuario(this.email);
	}

	public void validateEmail(FacesContext f, UIComponent c, Object obj) {
		String s = (String) obj;
		
		if (s==null){
			
			System.out.println("EMAIL VACIO");
			throw new ValidatorException(new FacesMessage(
					"El email es requerido."));
			
		}else{
		if (usuarioNegocio.existeUsuario(s)) {
			System.out.println("EMAIL EXISTE USUARIO");
			throw new ValidatorException(new FacesMessage(
					"Este Email ya existe en el registro."));
		} else {
			System.out.println("NO EXISTE USUARIO");
			//throw new ValidatorException(new FacesMessage("OK."));
		}
		}
	}

/*	public void validatePass2(FacesContext f, UIComponent c, Object obj) {
		String s = (String) obj;
		if (!s.isEmpty()) {
			System.out.println("PASS: " + this.password);
			if (!(s.equals(this.password))) {
				System.out.println("LOS PASS NO SON IGUALES");
				throw new ValidatorException(new FacesMessage(
						"Los passwords deben ser iguales."));
			} else {
				System.out.println("SON IGUALES");
				// throw new ValidatorException(new FacesMessage("OK."));
			}
		} else {
			System.out.println("PASS 2 VACIO");
			// throw new ValidatorException(new
			// FacesMessage("Debe repetir el password."));
		}
	}*/
	
	/*public void validarPassword(AjaxBehaviorEvent evento) {
		
		if (!(password3.equals(password))) {
			System.out.println("NO SON IGUALES "+password+"=="+password3);
			mensajeErrorPassword = "Las contraseñas deben ser iguales.";
		} else {
			System.out.println("SON IGUALES "+password+"=="+password3);
				mensajeErrorPassword = "";
			
		}
		*/
		
		//ESTE ES EL QUE ANDA CON PRUEBA 1
		/*if (password3.length() < 6) {
			System.out.println("TIENE MENOS DE 6");
			mensajeErrorPassword = "La contraseña tiene que tener como minimo 6 caracteres";
		} else {
			if (password3.length() > 15) {
				System.out.println("TIENE MAS DE 15");
				mensajeErrorPassword = "La contraseña puede tener como maximo 15 caracteres";
			} else {
				System.out.println("Oki");
				mensajeErrorPassword = "";
			}
		}
	}*/
	
	/*public void validateBidAmount(FacesContext context,
			UIComponent componentToValidate, Object value)
			throws ValidatorException {
		
		//double bidAmount = ((Double) value).doubleValue();
		//double previousHighestBid = currentHighestBid();
		String s = (String) value;
		
		if (!s.equals(this.nombre)) {
			FacesMessage message = new FacesMessage("son iguales ");
			throw new ValidatorException(message);
		}
		
	}*/

	/*public void validateBidAmount(FacesContext context,
			UIComponent componentToValidate, Object value)
			throws ValidatorException {
		
		double bidAmount = ((Double) value).doubleValue();
		double previousHighestBid = currentHighestBid();
		
		if (bidAmount <= previousHighestBid) {
			FacesMessage message = new FacesMessage(
					"Bid must be higher than current " + "highest bid ($"
							+ previousHighestBid + ").");
			throw new ValidatorException(message);
		}
		
	}
*/
	/*
	 * FacesMessage message = new
	 * FacesMessage("Bid must be higher than current " + "highest bid ($" +
	 * previousHighestBid + ")."); throw new ValidatorException(message);
	 */

	public String registrar() {
		if (!usuarioNegocio.existeUsuario(this.email)) {

			this.dataUsuario.setNombre(this.nombre);

			this.dataUsuario.setEmail(this.email);

			this.dataUsuario.setEstilo("areaTrabajo.css");// Estilo por defecto,
															// el usuario lo
															// puede cambiar
															// despu�s.

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
				this.dataUsuario.setPassword(md5Hash);
				System.out.println("Password encriptado");
				System.out.println(md5Hash);
			} catch (NoSuchAlgorithmException e) {

				e.printStackTrace();
			}
			/* Fin de encriptar password */

			boolean ok = false;

			try {
				usuarioNegocio.altaUsuario(this.dataUsuario);
				ok = true;
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (ok) {
				System.out.println("Alta usuario exitosa");
				return "/index.xhtml?faces-redirect=true";
			} else {
				System.out.println("Error. El usuario no fue dado de alta.");
				return null;
			}

		} else {
			this.existeUsuario = true;
			return null;
		}

	}

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public boolean isExisteUsuario() {
		return existeUsuario;
	}

	public void setExisteUsuario(boolean existeUsuario) {
		this.existeUsuario = existeUsuario;
	}

	/*public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}*/

}
