package com.sapo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import com.datatypes.DataAlmacen;
import com.datatypes.DataImagen;
import com.datatypes.DataProducto;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@RequestScoped
public class MoverProductoBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MoverProductoBean() {
		
	}

	/*private DataAlmacen almacen;
	private List<DataProducto> productos;
	private List<DataProducto> listaCompras;*/
	
	private List<DataAlmacen> listaAlmacenesDeUsuarioMenosenElqueEsta;
	private int cantAlamcenenDeUsusario;

	public int idAlmacenActual;
	public int idAlmacenSelec;
	public int idProductoSelec;
	public int cantStockAMover;

	@EJB
	AlmacenNegocio almacenNegocio;
	@EJB
	ProductoNegocio productoNegocio;
	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	/*public DataAlmacen getAlmacen() {
		return almacen;
	}

	public void setAlmacen(DataAlmacen almacen) {
		this.almacen = almacen;
	}

	public List<DataProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<DataProducto> productos) {
		this.productos = productos;
	}

	public List<DataProducto> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(List<DataProducto> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public void obtenerAlmacen() {
		this.almacen = almacenNegocio.getAlmacenPorId(nav.getIdAlmacenActual());
		List<DataProducto> dataProductos = this.obtenerProductos(nav
				.getIdAlmacenActual());
		// Si el producto no tiene foto se indica una por defecto.
		for (int i = 0; i < dataProductos.size(); i++) {
			if (dataProductos.get(i).getFotos().isEmpty()) {
				DataImagen dataImg = new DataImagen();
				dataImg.setIdImagen(1);
				dataProductos.get(i).getFotos().add(dataImg);// TODO Guardar en
																// base una
																// imagen por
																// defecto y
																// pasarle ese
																// id.
			}
		}
		this.almacen.setProductos(dataProductos);
	}*/

	public List<DataProducto> obtenerProductos(int idAlmacen) {
		List<DataProducto> dataProductos = new ArrayList<DataProducto>();
		dataProductos = almacenNegocio.getProductosDeAlmacen(idAlmacen);
		return dataProductos;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	@PostConstruct
	public void init() {
		//obtenerAlmacen();
		//generarLista();
		listaAlmacenesDeUsuarioMenosenElqueEsta = almacenNegocio
				.getAlmacenesMenosUno(nav.getIdAlmacenActual(),
						usuarioLogueado.getEmail());
		cantAlamcenenDeUsusario = almacenNegocio
				.getCantidadAlmacenesDeUsuario(usuarioLogueado.getEmail());
		
		this.idAlmacenActual=0;
		this.idAlmacenSelec=0;
		this.idProductoSelec=0;
		this.cantStockAMover=0;
	}

/*	public String actualizarStock(int idProducto, int stock) {
		this.productoNegocio.actualizarStock(idProducto, stock);
		init();
		return "index.xhtml";
	}*/

	/*public void generarLista() {
		DataAlmacen almacenReal = this.almacenNegocio.getAlmacenPorId(nav
				.getIdAlmacenActual());
		List<DataProducto> productosReal = this.obtenerProductos(nav
				.getIdAlmacenActual());
		List<DataProducto> productosIdeal = this.almacenNegocio
				.getAlmacenIdealPorId(almacenReal.getIdAlmacenIdeal())
				.getProductos();
		if (productosIdeal != null) {
			for (int i = 0; i < productosIdeal.size(); i++) {

				DataProducto dt = buscarProductoEnLista(productosReal,
						productosIdeal.get(i).getIdProducto());
				if (dt != null) {
					int difStock = productosIdeal.get(i).getStockIdeal()
							- dt.getStock();
					if (difStock != 0) {
						productosIdeal.get(i).setStock(
								productosIdeal.get(i).getStockIdeal());
						this.listaCompras.add(productosIdeal.get(i));
					}
				}
				if (dt == null) {
					productosIdeal.get(i).setStock(
							productosIdeal.get(i).getStockIdeal());
					this.listaCompras.add(productosIdeal.get(i));
				}
			}
		}
	}

	public DataProducto buscarProductoEnLista(List<DataProducto> dataProducto,
			int idProducto) {

		for (int i = 0; i < dataProducto.size(); i++) {
			if (dataProducto.get(i).getIdProducto() == idProducto)
				return dataProducto.get(i);
		}
		return null;
	}*/

	/*
	 * 
	 * public List<DataAlmacen> getAlmacenesMenosUno(){ return
	 * almacenNegocio.getAlmacenesMenosUno
	 * (idAlmacenActual,usuarioLogueado.getEmail()); }
	 */

	public List<DataAlmacen> getListaAlmacenesDeUsuarioMenosenElqueEsta() {
		return listaAlmacenesDeUsuarioMenosenElqueEsta;
	}

	public void setListaAlmacenesDeUsuarioMenosenElqueEsta(
			List<DataAlmacen> listaAlmacenesDeUsuarioMenosenElqueEsta) {
		this.listaAlmacenesDeUsuarioMenosenElqueEsta = listaAlmacenesDeUsuarioMenosenElqueEsta;
	}

	public int getCantAlamcenenDeUsusario() {
		return cantAlamcenenDeUsusario;
	}

	public void setCantAlamcenenDeUsusario(int cantAlamcenenDeUsusario) {
		this.cantAlamcenenDeUsusario = cantAlamcenenDeUsusario;
	}

	
	/*
	 * public void actualizarSeleccion(int num1,int num2, int num3){
	 * System.out.println("IDALMA DESTINO: " + num1 + " IDPRODUCTO: " + num2 +
	 * " CANTSTOCK: " + num3 + "IDALMAORIGEN: " + nav.getIdAlmacenActual());
	 * 
	 * this.idAlmacenSelec=num1; this.idProductoSelec=num2;
	 * this.cantStockAMover=num3;
	 * 
	 * }
	 */

	public String moverProducto() {

		System.out.println("HOLA ESTOY MOVIENDO MOVERPRODUCTO BEAN");
		System.out.println("IDALMA DESTINO: " + this.idAlmacenSelec
				+ " IDPRODUCTO: " + this.idProductoSelec + " CANTSTOCK: "
				+ this.cantStockAMover + "IDALMAORIGEN: "
				+ nav.getIdAlmacenActual());
		
		
		
		if (this.idAlmacenSelec != 0 && this.idProductoSelec != 0
				&& this.cantStockAMover != 0) {
			
/////////////////muevo aca///////////////////////////////////////////
			almacenNegocio.moverProducto(this.idAlmacenSelec,
					this.idProductoSelec, this.cantStockAMover,
					nav.getIdAlmacenActual());
		}
		else System.out.println("NO HICE NADA ES TODO CERO//////////////////////////////");
		
		return "index?faces-redirect=true";
	}
	
	public int getIdAlmacenActual() {
		return idAlmacenActual;
	}

	public void setIdAlmacenActual(int idAlmacenActual) {
		System.out.println("setIdAlmacenActual(int idAlmacenActual) CON EL VALOR: "+ idAlmacenActual);
		this.idAlmacenActual = idAlmacenActual;
	}

	public int getIdAlmacenSelec() {
		return idAlmacenSelec;
	}

	public void setIdAlmacenSelec(int idAlmacenSelec) {
		System.out.println("setIdAlmacenSelec(int idAlmacenSelec)CON EL VALOR: "+ idAlmacenSelec);
		this.idAlmacenSelec = idAlmacenSelec;
	}

	public int getIdProductoSelec() {
		return idProductoSelec;
	}

	public void setIdProductoSelec(int idProductoSelec) {
		System.out.println("setIdProductoSelec(int idProductoSelec)CON EL VALOR: "+ idProductoSelec);
		this.idProductoSelec = idProductoSelec;
	}

	public int getCantStockAMover() {
		return cantStockAMover;
	}

	public void setCantStockAMover(int cantStockAMover) {
		System.out.println("setCantStockAMover(int cantStockAMover) CON EL VALOR: "+ cantStockAMover);
		this.cantStockAMover = cantStockAMover;
	}
	
}