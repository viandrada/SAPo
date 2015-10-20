package com.sapo.dao;

import java.util.List;

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
	
	@SuppressWarnings("unchecked")
	public List<Categoria> getCat()
	{
		//boolean esGenerica=false;
		//return em.createQuery("SELECT c FROM Categoria c  WHERE c.esGenerica=:esGenerica").setParameter("esGenerica", esGenerica).getResultList();
		return em.createQuery("SELECT c FROM Categoria c  WHERE c.esGenerica IS FALSE").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> getCatGenericas()
	{
		//boolean esGenerica=true;
		//return em.createQuery("SELECT cg FROM Categoria cg WHERE cg.esGenerica=:esGenerica").setParameter("esGenerica", esGenerica).getResultList();
		return em.createQuery("SELECT cg FROM Categoria cg WHERE cg.esGenerica IS TRUE").getResultList();
	}


}
