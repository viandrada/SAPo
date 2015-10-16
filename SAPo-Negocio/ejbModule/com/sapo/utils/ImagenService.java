package com.sapo.utils;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataImagen;
import com.sapo.dao.ImagenDAO;
import com.sapo.entidades.Imagen;

/**
 * Session Bean implementation class ImagenService
 */
@Stateless
@LocalBean
public class ImagenService {


    public ImagenService() {
        // TODO Auto-generated constructor stub
    }
    
    @EJB
    private ImagenDAO imagenDAO;
    
    public DataImagen getImagen(int idImagen){
    	DataImagen imagen = new DataImagen();
    	Imagen img = imagenDAO.getImagen(idImagen);
    	imagen.setIdImagen(img.getIdImagen());
    	imagen.setNombre(img.getNombre());
    	imagen.setDatos(img.getDatos());
    	imagen.setMime(img.getMime());
    	return imagen;
    }
}
