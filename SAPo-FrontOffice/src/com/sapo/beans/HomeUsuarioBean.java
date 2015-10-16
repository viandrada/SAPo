package com.sapo.beans;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.imageio.ImageIO;

import sun.misc.IOUtils;

import com.datatypes.DataAlmacen;
import com.sapo.ejb.AlmacenNegocio;

@ManagedBean
@RequestScoped
public class HomeUsuarioBean {
	
	public HomeUsuarioBean(){}
	
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
	public void init(){
		listarAlmacenes();
	}

	public void listarAlmacenes(){
		this.almacenes = this.almacenNegocio.getAlmacenes(this.usuarioLogueado.getEmail());
	}
	
	public static BufferedImage  byteArrayToImage(byte[] bytes){  
        BufferedImage bufferedImage=null;
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return bufferedImage;
}
	/*public String encode(byte[] bytes){
		String encodedImage ;
		InputStream inputStream = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		//encodedImage = new String(Base64.encode(os.toByteArray()));
		return new String(Base64.encode(IOUtils.(inputStream)));
	}*/
}
