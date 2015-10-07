package com.sapo.dao;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.sapo.entidades.Administrador;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AdministradorDAO {

	public AdministradorDAO(){}
	
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

	public void insertarAdministrador(Administrador admin) {
		
		/*Administrador adminEntidad = new Administrador();
		
		adminEntidad.setEmail(admin.getEmail());
		adminEntidad.setNombre(admin.getNombre());
		adminEntidad.setPassword(admin.getPassword());
		adminEntidad.setEstaActivo(true);
		adminEntidad.setFecha(new Date());*/
		admin.setEstaActivo(true);
		admin.setFecha(new Date());
		
		try {
			em.persist(admin);
		} catch (PersistenceException e) {
			System.out.print(e.getMessage());
		}
		
	}

	public void actualizarAdministrador(Administrador a) {
		em.merge(a);
	}
	
	public boolean login(Administrador admin){
		Boolean existe = false;
		try {
			Query consulta = this.em
					.createNamedQuery("Administrador.loginAdministrador.Email.Pass");
			consulta.setParameter("email", admin.getEmail());
			consulta.setParameter("pass", admin.getPassword());
			if ((!consulta.getResultList().isEmpty())) {
				existe = true;
			}
		} catch (Exception excep) {
			throw excep;
		}
		return existe;
	}

}
