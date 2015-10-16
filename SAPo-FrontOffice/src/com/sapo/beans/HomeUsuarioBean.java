package com.sapo.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.datatypes.DataAlmacen;
import com.sapo.ejb.AlmacenNegocio;

@ManagedBean
@RequestScoped
public class HomeUsuarioBean {

	public HomeUsuarioBean() {
	}

	private List<DataAlmacen> almacenes;
	@EJB
	AlmacenNegocio almacenNegocio;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;

	public List<DataAlmacen> getAlmacenes() {
		return almacenes;
	}

	public void setAlmacenes(List<DataAlmacen> almacenes) {
		this.almacenes = almacenes;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	@PostConstruct
	public void init() {
		listarAlmacenes();
		for (int i = 0; i < this.almacenes.size(); i++) {
			if(this.almacenes.get(i).getBytesFoto()== null){
				this.almacenes.get(i).setIdFoto(10);//TODO Guardar en base una imagen por defecto y pasarle ese id.
			}
		}
		
	}

	public void listarAlmacenes() {
		this.almacenes = this.almacenNegocio.getAlmacenes(this.usuarioLogueado
				.getEmail());
	}

	/*public static BufferedImage byteArrayToImage(byte[] bytes) {
		BufferedImage bufferedImage = null;
		try {
			InputStream inputStream = new ByteArrayInputStream(bytes);
			bufferedImage = ImageIO.read(inputStream);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return bufferedImage;
	}*/
}
