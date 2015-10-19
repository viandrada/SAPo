package com.sapo.utils;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import com.datatypes.DataCategoria;
import com.sapo.entidades.Categoria;

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
}
