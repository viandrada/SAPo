package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAO {
	
	@PersistenceContext(unitName="SAPo-AccesoDatos")
	EntityManager em;
	
	public Usuario getUsuario(String email){
		return em.find(Usuario.class, email);
	}
	
	public boolean existeUsuario(int idUsuario){
		return (em.createQuery("SELECT a FROM Usuario p WHERE a.idUsuario=:idUsuario")
				.setParameter("idUsuario", idUsuario)
				.getResultList().size()== 1);
	}

	public void insertarUsuario (Usuario a){
		em.persist(a);
	}
	
	public void actualizarUsuario(Usuario a){
		em.merge(a);		
	}

}
