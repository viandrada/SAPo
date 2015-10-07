package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.CategoriaGenerica;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CategoriaGenericaDAO {
	

	@PersistenceContext(unitName="SAPo-AccesoDatos")
	EntityManager em;
	
	public CategoriaGenerica getCategoriaGenerica(String email){
		return em.find(CategoriaGenerica.class, email);
	}
	
	
	//Deberia ser la clave el nombre?
	public boolean existeCategoriaGenerica(int idCategoriaGenerica){
		return (em.createQuery("SELECT a FROM CategoriaGenerica p WHERE a.idCategoriaGenerica=:idCategoriaGenerica")
				.setParameter("idCategoriaGenerica", idCategoriaGenerica)
				.getResultList().size()== 1);
	}

	
	public void insertarCategoriaGenerica (CategoriaGenerica a){
		em.persist(a);
	}
	
	public void actualizarCategoriaGenerica(CategoriaGenerica a){
		em.merge(a);		
	}


}
