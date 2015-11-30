package com.sapo.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sapo.entidades.Configuracion;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ConfiguarcionDAO {
	@PersistenceContext(unitName="SAPo-Negocio")
	EntityManager em;
	
	
	public Configuracion getConfiguracion (String clave){
		return em.find(Configuracion.class, clave);
	}
	
	public boolean existeConfiguracion(String clave){
		return (em.createQuery("SELECT a FROM Configuracion a WHERE a.clave=:clave")
				.setParameter("clave", clave)
				.getResultList().size()== 1);
	}

	
	public void insertarConfiguracion (Configuracion a){
		em.persist(a);
		em.flush();
	}
	
	public void actualizarConfiguracion(Configuracion a){
		em.merge(a);		
	}
	
	public void insertarMaxAlmacenes(){
		Configuracion config1 = new Configuracion ("maxCantAlmComun",5);
		Configuracion config2 = new Configuracion ("maxCantAlmPremium",100);
		Configuracion config3 = new Configuracion ("precioPremium",3.0f);
		try {
			insertarConfiguracion(config1);
			insertarConfiguracion(config2);
			insertarConfiguracion(config3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ConfigDao: Error en primeraConfiguracion");
		}
		
	}
	
	public int getValorConfigInt (String clave){
		Configuracion config =  (Configuracion) (em.createQuery("SELECT a FROM Configuracion a WHERE a.clave=:clave")
				.setParameter("clave", clave)
				.getSingleResult());
		return config.getValorInt();
	}
	
	
	public float getValorConfigFloat (String clave){
		Configuracion config =  (Configuracion) (em.createQuery("SELECT a FROM Configuracion a WHERE a.clave=:clave")
				.setParameter("clave", clave)
				.getSingleResult());
		return config.getValorFloat();
	}
	
}
