package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sapo.entidades.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAO {
	
	@PersistenceContext(unitName="SAPo-Negocio")
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
	
	public boolean login(Usuario usuario){
		Boolean existe = false;
		try {
			Query consulta = this.em
					.createNamedQuery("Usuario.loginUsuario.Email.Pass");
			consulta.setParameter("email", usuario.getEmail());
			consulta.setParameter("pass", usuario.getPassword());
			if ((!consulta.getResultList().isEmpty())) {
				existe = true;
			}
		} catch (Exception excep) {
			throw excep;
		}
		return existe;
	}

}
