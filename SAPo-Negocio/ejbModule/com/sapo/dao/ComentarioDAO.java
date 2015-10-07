package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Comentario;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ComentarioDAO {
	
	@PersistenceContext(unitName="SAPo-AccesoDatos")
	EntityManager em;
	
	public Comentario getComentario(String email){
		return em.find(Comentario.class, email);
	}
	
	public boolean existeComentario(int idComentario){
		return (em.createQuery("SELECT a FROM Comentario p WHERE a.idComentario=:idComentario")
				.setParameter("idComentario", idComentario)
				.getResultList().size()== 1);
	}

	
	public void insertarComentario (Comentario a){
		em.persist(a);
	}
	
	public void actualizarComentario(Comentario a){
		em.merge(a);		
	}

}
