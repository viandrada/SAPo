package com.sapo.utils;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datatypes.DataImagen;


@WebServlet("/Image/*")
public class Image extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@EJB
	private ImagenService service;
	
    public Image() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.valueOf(request.getPathInfo().substring(1));
        DataImagen dataImg = service.getImagen(id);
        //response.setContentType(getServletContext().getMimeType(dataImg.getMime()));
        response.setContentLength(dataImg.getDatos().length);
        response.getOutputStream().write(dataImg.getDatos());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
