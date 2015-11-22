package com.sapo.utils;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datatypes.DataUsuario;
import com.sapo.beans.LoginBean;
import com.sapo.beans.NavigationBean;
import com.sapo.ejb.UsuarioNegocio;

/**
 * Servlet implementation class googleLogin
 */
@WebServlet("/googleLogin/*")
public class googleLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UsuarioNegocio servicio;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public googleLogin() {
        super();
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getPathInfo().substring(1);
		DataUsuario dUsuario = new DataUsuario();
		dUsuario.setEmail(email);
		DataUsuario dUsuarioDB = this.servicio.loginExterno(dUsuario);
		LoginBean loginBean = (LoginBean) request.getSession().getAttribute("loginBean");
		if(loginBean != null){
		/*loginBean.setEmail(dUsuarioDB.getEmail());
		loginBean.setEstilo(dUsuarioDB.getEstilo());
		loginBean.setIdUsuario(dUsuarioDB.getIdUsuario());
		loginBean.setNombre(dUsuarioDB.getNombre());
		loginBean.setPremium(dUsuarioDB.isPremium());
		loginBean.setLogueado(true);*/
		loginBean.setShownLogin(false);
		loginBean.setGoogleLogin(true);
		loginBean.refreshSession(dUsuarioDB);
		NavigationBean navBean = (NavigationBean) request.getSession().getAttribute("navigationBean");
		navBean.setRedirectTo("areaTrabajo.xhtml");
		request.getRequestDispatcher("/index.xhtml").forward(request, response);
		}
		
		System.out.print(email);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
