package com.sapo.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Almacen;
import com.sapo.entidades.Comentario;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ComentarioDAO {
	
	@PersistenceContext(unitName="SAPo-Negocio")
	EntityManager em;
	
	public Comentario getComentario(String email){
		return em.find(Comentario.class, email);
	}
	
	public Comentario getComentario(int idComentario){
		return em.find(Comentario.class, idComentario);
	}
	
	public boolean esMiComentario(int idComentario, String email){
		return (em.createQuery("SELECT a FROM Comentario a WHERE a.idComentario=:idComentario and a.usuario.email=:email")
				.setParameter("idComentario", idComentario)
				.setParameter("email", email)
				.getResultList().size()== 1);
	}
	
	public boolean existeComentario(int idComentario){
		return (em.createQuery("SELECT a FROM Comentario a WHERE a.idComentario=:idComentario")
				.setParameter("idComentario", idComentario)
				.getResultList().size()== 1);
	}

	
	public void insertarComentario (Comentario a){
		em.persist(a);
	}
	
	public void actualizarComentario(Comentario a){
		//em.persist(a);
		em.merge(a);		
	}
	
	@SuppressWarnings("unchecked")
	public List<Comentario> getComentariosdeEsteAlmacen(
			Almacen a) {
		
		List<Comentario> listaC = new LinkedList<Comentario>();
		
		listaC = em
				.createQuery("SELECT c FROM Comentario c")
				.getResultList();
		
		List<Comentario> lisR = new LinkedList<Comentario>();

		if (!listaC.isEmpty()) {
			for (Comentario c : listaC) {
				if (!a.EsComentariodeAlmacen(c.getIdComentario())) {
					lisR.add(c);
				}
			}
		}
		return lisR;
	}

}
