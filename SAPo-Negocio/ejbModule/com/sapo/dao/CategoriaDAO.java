package com.sapo.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Categoria;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CategoriaDAO {

	@PersistenceContext(unitName = "SAPo-Negocio")
	EntityManager em;

	public Categoria getCategoria(int idCat) {
		return em.find(Categoria.class, idCat);
	}

	public boolean existeCategoria(int idCategoria) {
		return (em
				.createQuery(
						"SELECT a FROM Categoria a WHERE a.idCategoria=:idCategoria")
				.setParameter("idCategoria", idCategoria).getResultList()
				.size() == 1);
	}

	public void insertarCategoria(Categoria a) {
		em.persist(a);
	}

	public void actualizarCategoria(Categoria a) {
		em.merge(a);
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> getCat() {
		return em.createQuery(
				"SELECT c FROM Categoria c  WHERE c.esGenerica IS FALSE")
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> getodaslasCategorias() {
		return em.createQuery("SELECT c FROM Categoria c").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> getCatPorUsusario(int idu) {
		List<Categoria> Rcats = new LinkedList<Categoria>();
		List<Categoria> cats = new LinkedList<Categoria>();
		cats = em.createQuery(
				"SELECT c FROM Categoria c WHERE c.esGenerica IS FALSE")
				.getResultList();
		// cats=getodaslasCategorias();
		for (Categoria c : cats) {
			System.out.println("CATEGORIA: " + c.getNombre());
			if (c.tieneUsuario()) {
				if (c.getUsu().getIdUsuario() == idu) {
					//System.out.println(idu + "<--IDU HOLA JEJJEJEJ");
					Rcats.add(c);
				}
			}
		}
		return Rcats;
		// return null;
		/*
		 * return
		 * em.createQuery("SELECT c FROM Categoria c  WHERE c.usu.idusuario=:idu"
		 * ) .setParameter("idu", idu) .getResultList();
		 */
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> getCatGenericas() {
		return em.createQuery(
				"SELECT cg FROM Categoria cg WHERE cg.esGenerica IS TRUE")
				.getResultList();
	}

}
