package com.sapo.utils;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import com.datatypes.DataCategoria;
import com.datatypes.DataUsuario;
import com.sapo.entidades.Categoria;
import com.sapo.entidades.Usuario;

@Stateless
public class Fabrica 
{
	
	public List<DataCategoria> convertirCat(List<Categoria> lcat)
	{
		List<DataCategoria> l = new LinkedList<DataCategoria>();
		
		for(Categoria c : lcat)
		{
			DataCategoria dcat = new DataCategoria();
			
			dcat.setIdCategoria(c.getIdCategoria());
			dcat.setNombre(c.getNombre());
		
			l.add(dcat);
		}
		return l;
	}
	public List<DataUsuario> convertirUsu(List<Usuario> lcat)
	{
		List<DataUsuario> l = new LinkedList<DataUsuario>();
		
		for(Usuario c : lcat)
		{
			DataUsuario dcat = new DataUsuario();
			
			dcat.setIdUsuario(c.getIdUsuario());
			dcat.setNombre(c.getNombre());
			dcat.setEmail(c.getEmail());
			dcat.setPassword(c.getPassword());
			//dcat.setFecha(c.);
			
		
			l.add(dcat);
		}
		return l;
	}
}
