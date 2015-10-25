package com.sapo.utils;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datatypes.DataUsuario;
import com.sapo.beans.LoginBean;
import com.sapo.beans.NavigationBean;
import com.sapo.ejb.UsuarioNegocio;
import com.sapo.entidades.Usuario;


@WebServlet("/Paypal/*")
public class Paypal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UsuarioNegocio usuarioService;
	
	/*@Inject
	LoginBean usuarioLogueado;*/

	
    public Paypal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getPathInfo().substring(1);
		if (url.contains("operacionCancelada")){
			System.out.println("llegué a op cancelada");
		}
		else if (url.contains("pagoCorrecto")){
			System.out.println("llegué a Pago Correcto");
				
			//String usuarioLogueado = this.usuarioLogueado.getEmail();
			
			LoginBean loginBean = (LoginBean) request.getSession().getAttribute("loginBean");
			String usuarioLogueado = loginBean.getEmail();
			boolean ok = usuarioService.pasarAPremium(usuarioLogueado);
			
		}
		NavigationBean navBean = (NavigationBean) request.getSession().getAttribute("navigationBean");
		navBean.setRedirectTo("areaTrabajo.xhtml");
		request.getRequestDispatcher("/index.xhtml").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
	}

}
