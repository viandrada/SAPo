package com.sapo.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.UsuarioDAO;
import com.datatypes.DataAlmacen;
import com.datatypes.DataUsuario;
import com.sapo.entidades.Almacen;
import com.sapo.entidades.Imagen;
import com.sapo.entidades.Usuario;


/**
 * Session Bean implementation class AlmacenNegocio
 */
@Stateless
@LocalBean
public class AlmacenNegocio {

	/**
	 * Default constructor.
	 */
	public AlmacenNegocio() {
		this.almacen = new Almacen();
	}

	@EJB
	private AlmacenDAO almacenDAO;
	@EJB
	private UsuarioDAO usuarioDAO;

	private Almacen almacen;

	public boolean altaAlmacen(DataAlmacen almacen, DataUsuario usuario) {
		boolean altaOK = false;
		Usuario usr;
		Imagen img = new Imagen();
		
		img.setDatos(almacen.getFoto());
		usr = this.usuarioDAO.getUsuarioPorEmail(usuario.getEmail());

		this.almacen.setNombre(almacen.getNombre());
		this.almacen.setDescripcion(almacen.getDescripcion());
		this.almacen.setEstaActivo(true);
		this.almacen.setFechaAlta(new Date());
		this.almacen.setPropietario(usr);
		this.almacen.setFoto(img);
		
		try {
			this.almacenDAO.insertarAlmacen(this.almacen);
			altaOK = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return altaOK;
	}
	public List<DataAlmacen> getAlmacenes(String emailUsr){
		
		List<DataAlmacen> listaDataAlmacen = new ArrayList<DataAlmacen>();
		List<Almacen> listaAlmacenes = new ArrayList<Almacen>();
		
		try {
			listaAlmacenes = this.almacenDAO.getAlmacenesUsuario(emailUsr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		for (int i = 0; i < listaAlmacenes.size(); i++) {
			DataAlmacen dataAlmacen = new DataAlmacen();
			dataAlmacen.setNombre(listaAlmacenes.get(i).getNombre());
			dataAlmacen.setDescripcion(listaAlmacenes.get(i).getDescripcion());
			dataAlmacen.setFoto(listaAlmacenes.get(i).getFoto().getDatos());
			dataAlmacen.setIdAlmacen(listaAlmacenes.get(i).getIdAlmacen());
			listaDataAlmacen.add(dataAlmacen);
		}
		return listaDataAlmacen;
	}
	
	public DataAlmacen getAlmacenPorId(int idAlmacen){
		DataAlmacen dataAlmacen = new DataAlmacen();
		Almacen almacen = this.almacenDAO.getAlmacenPorId(idAlmacen);
		
		dataAlmacen.setNombre(almacen.getNombre());
		dataAlmacen.setDescripcion(almacen.getDescripcion());
		dataAlmacen.setFoto(almacen.getFoto().getDatos());
		return dataAlmacen;
	}
}
