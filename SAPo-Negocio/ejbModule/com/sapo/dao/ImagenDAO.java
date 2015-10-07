package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Imagen;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)

public class ImagenDAO {
	
	@PersistenceContext(unitName="SAPo-AccesoDatos")
	EntityManager em;
	
	public Imagen getImagen(String email){
		return em.find(Imagen.class, email);
	}
	
	public boolean existeImagen(int idImagen){
		return (em.createQuery("SELECT a FROM Imagen p WHERE a.idImagen=:idImagen")
				.setParameter("idImagen", idImagen)
				.getResultList().size()== 1);
	}

	
	public void insertarImagen (Imagen a){
		em.persist(a);
	}
	
	public void actualizarImagen(Imagen a){
		em.merge(a);		
	}

}
