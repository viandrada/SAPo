package com.sapo.dao;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import com.sapo.datatypes.DataAdministrador;
import com.sapo.entidades.Administrador;

@Stateless(name="adminDAO")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdministradorDAO {

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

	public void insertarAdministrador(DataAdministrador admin) {
		
		Administrador adminEntidad = new Administrador();
		
		adminEntidad.setEmail(admin.getEmail());
		adminEntidad.setNombre(admin.getNombre());
		adminEntidad.setPassword(admin.getPassword());
		adminEntidad.setEstaActivo(true);
		adminEntidad.setFecha(new Date());
		try {
			em.persist(admin);
		} catch (PersistenceException e) {
			System.out.print(e.getMessage());
		}
		
	}

	public void actualizarAdministrador(Administrador a) {
		em.merge(a);
	}

}
