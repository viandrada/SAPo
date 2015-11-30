package com.sapo.servicios;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sapo.ejb.UsuarioNegocio;



@Stateless
@Path("/usuario")
public class loginService {

	@EJB
	UsuarioNegocio usuarioNegocio;
	
	
	@GET
	@Path("prueba")
	@Produces("text/plain")
	public String prueba()
	{
		return "Hola! REST está funcionando!";
	}
	
	
	
	
	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean login(@FormParam("email") String email, 
			@FormParam("pass") String pass) {
		return usuarioNegocio.login(email, pass);
		//return true;
	}
	
}
