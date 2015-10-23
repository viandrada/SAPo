package com.sapo.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.datatypes.DataCategoria;
import com.sapo.dao.AdministradorDAO;
import com.sapo.dao.CategoriaDAO;
import com.sapo.dao.UsuarioDAO;
import com.sapo.entidades.Categoria;
import com.sapo.entidades.Usuario;
import com.sapo.utils.Fabrica;


@Stateless
@LocalBean
public class CategoriaNegocio {

    @EJB
	private CategoriaDAO cdao;
    @EJB
	private UsuarioDAO udao;
    @EJB
	private AdministradorDAO adao;
    
    
    @EJB
    private Fabrica fabrica;
    
    public CategoriaNegocio(/*String nombre*/) {
    	
       /*Categoria cat = new Categoria();
        cat.setNombre(nombre);
        
        cdao.insertarCategoria(cat);*/
   }
    
    public void altaCategoria(String nombre) {
      	       	
        Categoria cat = new Categoria();
        cat.setNombre(nombre);
        cat.setEsGenerica(true);
        cat.setUsu(null);
         
        cdao.insertarCategoria(cat);
    }
    
    public void altaCategoriaPersonal(String nombre,String email) {
	       	
        Categoria cat = new Categoria();
        cat.setNombre(nombre);
        cat.setEsGenerica(false);
        
        Usuario usu=udao.getUsuarioPorEmail(email);
        cat.setUsu(usu);
         
        cdao.insertarCategoria(cat);
    }
    
    public void promoCat(int idCat){
    	Categoria c=cdao.getCategoria(idCat);
    	c.setEsGenerica(true);
    	cdao.actualizarCategoria(c);
    }
    
    public void modivicarNombreCat(int idCat, String nom){
    	Categoria c=cdao.getCategoria(idCat);
    	c.setNombre(nom);
    	cdao.actualizarCategoria(c);
     }
    
    public List<DataCategoria> listDataCategoriasPersonal(String email){
	 	Usuario usu=udao.getUsuarioPorEmail(email);
		System.out.println(usu.getEmail()+"HHHHHHHHHHHHHHHHHHHHHHHH");
		
		//List<Categoria> l=cdao.getCatPorUsusario(usu.getIdUsuario());

		return fabrica.convertirCat(cdao.getCatPorUsusario(usu.getIdUsuario()));
	}
    
    public List<DataCategoria> listDataCategorias(){
		
		return fabrica.convertirCat(cdao.getCat());
	}
    
    public List<DataCategoria> listDataCategoriasGenericas(){
		
		return fabrica.convertirCat(cdao.getCatGenericas());
	}
    
    
    public boolean esUsuario(int idUsu){
    	return adao.existeAdministrador(idUsu);
    }
    
    public boolean esAdministrador(int idUsu){
    	return adao.existeAdministrador(idUsu);
    }

}
