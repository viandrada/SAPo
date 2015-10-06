package com.sapo.dao;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.sapo.entidades.Administrador;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdministradorDAO implements AdministradorDAORemote {

	@PersistenceContext(unitName = "SAPo-AccesoDatos")
	private EntityManager em;

	public Administrador getAdministrador(String email) {
		return em.find(Administrador.class, email);
	}

	public boolean existeAdministrador(String email) {
		return (em
				.createQuery(
						"SELECT a FROM Administrador p WHERE a.email=:email")
				.setParameter("email", email).getResultList().size() == 1);
	}

	public void insertarAdministrador(com.sapo.datatypes.Administrador a) {
		
		Administrador adminEntidad = new Administrador();
		
		adminEntidad.setEmail(a.getEmail());
		adminEntidad.setNombre(a.getNombre());
		adminEntidad.setPassword(a.getPassword());
		adminEntidad.setEstaActivo(true);
		adminEntidad.setFecha(new Date());
		try {
			em.persist(a);
		} catch (PersistenceException e) {
			System.out.print(e.getMessage());
		}
		
	}

	public void actualizarAdministrador(Administrador a) {
		em.merge(a);
	}

}
