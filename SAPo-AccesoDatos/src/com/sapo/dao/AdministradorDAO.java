package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Administrador;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdministradorDAO {
	
	@PersistenceContext(unitName="SAPo-AccesoDatos")
	EntityManager em;
	
	public Administrador getAdministrador(String email){
		return em.find(Administrador.class, email);
	}
	
	public boolean existeAdministrador(String email){
		return (em.createQuery("SELECT a FROM Administrador p WHERE a.email=:email")
				.setParameter("email", email)
				.getResultList().size()== 1);
	}
	
	public void insertarAdministrador (Administrador a){
		em.persist(a);
	}
	
	public void actualizarAdministrador(Administrador a){
		em.merge(a);		
	}
	
}
