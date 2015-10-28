package com.sapo.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sapo.entidades.Almacen;
import com.sapo.entidades.Usuario;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioDAO {

	@PersistenceContext(unitName = "SAPo-Negocio")
	EntityManager em;

	public Usuario getUsuario(int idUsuario) {
		Usuario u = new Usuario();
		try {
			u = em.find(Usuario.class, idUsuario);
		} catch (NoResultException e) {
			System.out.print("No se encontr� el usuario.");
		}
		return u;
	}

	public Usuario getUsuarioPorEmail(String email) {
		Query consulta = this.em
				.createNamedQuery("Usuario.getUsuarioPorEmail.Email");
		consulta.setParameter("email", email);
		Usuario usr = new Usuario();
		try {
			usr = (Usuario) consulta.getSingleResult();
		} catch (NoResultException e) {
			System.out.print("No se encontr� el usuario.");
		}
		return usr;
	}

	public boolean existeUsuario(int idUsuario) {
		return (em
				.createQuery(
						"SELECT a FROM Usuario a WHERE a.idUsuario=:idUsuario")
				.setParameter("idUsuario", idUsuario).getResultList().size() == 1);
	}

	public boolean esPremium(String emailUser){
		Usuario usr = getUsuarioPorEmail(emailUser);
		return usr.isPremium();
	}
	
	public void insertarUsuario(Usuario a) {
		em.persist(a);
	}

	public void actualizarUsuario(Usuario a) {
		em.merge(a);
	}

	public boolean login(Usuario usuario) {
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

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuarios() {
		return em.createQuery("SELECT u FROM Usuario u").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosMenosUno(int idu) {
		// return em.createQuery("SELECT u FROM Usuario u").getResultList();

		return em
				.createQuery("SELECT u FROM Usuario u  WHERE u.idUsuario!=:idu")
				.setParameter("idu", idu).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosMenosYOyLosqueNOCompartenEsteAlmacen(
			int idu, Almacen a) {
		// return em.createQuery("SELECT u FROM Usuario u").getResultList();
		List<Usuario> listaU = new LinkedList<Usuario>();
		listaU = em
				.createQuery("SELECT u FROM Usuario u  WHERE u.idUsuario!=:idu")
				.setParameter("idu", idu).getResultList();
		List<Usuario> lisR = new LinkedList<Usuario>();

		if (!listaU.isEmpty()) {
			for (Usuario u : listaU) {
				if (!a.EsUsuariodeEsteAlmacen(u.getEmail())) {
					lisR.add(u);
				}
			}
		}
		return lisR;
	}

}
