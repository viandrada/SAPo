package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Categoria;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CategoriaDAO {
	
	@PersistenceContext(unitName="SAPo-Negocio")
	EntityManager em;
	
	public Categoria getCategoria(int idCat){
		return em.find(Categoria.class, idCat);
	}
	
	public boolean existeCategoria(int idCategoria){
		return (em.createQuery("SELECT a FROM Categoria p WHERE a.idCategoria=:idCategoria")
				.setParameter("idCategoria", idCategoria)
				.getResultList().size()== 1);
	}

	
	public void insertarCategoria (Categoria a){
		em.persist(a);
	}
	
	public void actualizarCategoria(Categoria a){
		em.merge(a);		
	}


}
