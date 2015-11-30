package com.sapo.utils;

import java.io.IOException;
import java.util.Enumeration;

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
		//String email = request.getPathInfo().substring(1);
		//System.out.println(email);
		System.out.println(request.getPathInfo());
		
	
		
		
		//double latitud = Double.parseDouble(request.getPathInfo().substring(2));
		//double longitud = Double.parseDouble(request.getPathInfo().substring(3));
		
		//String latitud = request.getPathInfo().substring(2);
		//String longitud = request.getPathInfo().substring(3);
		
		String coor = request.getPathInfo();
		String[] arrayCoord = coor.split("&");
		
		String email=arrayCoord[0].substring(1);
		
		double latitud=Double.parseDouble(arrayCoord[1]);
		double longitud=Double.parseDouble(arrayCoord[2]);
		
		System.out.println(email);
		System.out.println(latitud);
		System.out.println(longitud);
		 
		// En este momento tenemos un array en el que cada elemento es un color.
	//	for (int i = 0; i < arrayCoord.length; i++) {
		//	System.out.println(arrayCoord[i]);
	//	}
		
		
		//System.out.println(latitud);
		//System.out.println(longitud);
		
		
		
		DataUsuario dUsuario = new DataUsuario();
		dUsuario.setEmail(email);
		
	
		
		dUsuario.setLatitud(latitud);
		dUsuario.setLongitud(longitud);
		
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
		
		//System.out.print(email);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
