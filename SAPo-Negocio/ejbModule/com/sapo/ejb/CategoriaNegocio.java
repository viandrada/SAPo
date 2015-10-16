package com.sapo.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sapo.dao.CategoriaDAO;
import com.sapo.entidades.Categoria;


@Stateless
@LocalBean
public class CategoriaNegocio {

    @EJB
	private CategoriaDAO cdao;
    
    public CategoriaNegocio(/*String nombre*/) {
    	
       /*Categoria cat = new Categoria();
        cat.setNombre(nombre);
        
        cdao.insertarCategoria(cat);*/
   }
    
    public void altaCategoria(String nombre) {
    	
    	       	
        Categoria cat = new Categoria();
        cat.setNombre(nombre);
        cat.setEsGenerica(true);
         
        cdao.insertarCategoria(cat);
    }

}
