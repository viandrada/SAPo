package com.sapo.ejb;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.sapo.dao.AlmacenDAO;
import com.sapo.dao.UsuarioDAO;
import com.datatypes.DataAlmacen;
import com.datatypes.DataUsuario;
import com.sapo.entidades.Almacen;
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
		// TODO Auto-generated constructor stub
	}

	@EJB
	private AlmacenDAO almacenDAO;
	@EJB
	private UsuarioDAO usuarioDAO;

	private Almacen almacen;
	private Usuario usuario;

	public boolean altaAlmacen(DataAlmacen almacen, DataUsuario usuario) {
		boolean altaOK = false;

		this.usuario.setIdUsuario((Integer)this.usuarioDAO
				.getUsuario(usuario.getEmail()).getIdUsuario());

		this.almacen.setNombre(almacen.getNombre());
		this.almacen.setDescripcion(almacen.getDescripcion());
		this.almacen.setEstaActivo(true);
		this.almacen.setFechaAlta(new Date());
		this.almacen.setPropietario(this.usuario);

		try {
			this.almacenDAO.insertarAlmacen(this.almacen);
			altaOK = true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return altaOK;
	}

}
