package com.sapo.beans;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.datatypes.Atributo;
import com.datatypes.DataAtributoAcumulado;
import com.datatypes.DataProducto;
import com.datatypes.DataReportePorAtributo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.ReporteNegocio;
import com.sapo.entidades.Producto;
import com.sapo.utils.Fabrica;

@ManagedBean
@RequestScoped
public class ReporteAtributoBean {

	public ReporteAtributoBean() {
		
		 listDatasAMostrar=new LinkedList<DataReportePorAtributo>();
		 resultadoNumerico=0;
		 esNumerico=false;
	}

	@EJB
	AlmacenNegocio almacenNegocio;
	
	@EJB
	Fabrica fabrica;
	
	@EJB
	ReporteNegocio reporteNegocio;

	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;

	@ManagedProperty(value = "#{navigationAreaBean}")
	NavigationAreaBean nav;

	// private String nombreUsuSelect;
	// private String emailUsuSelect;
	// private int idAlmacen;
	// private String emailUsuarioLogueado;
	// private String contenido;
	// private int idComentarioSelect;

	// private List<DataComentario> listDataComentarios;

	private List<DataAtributoAcumulado> listDataAtriAcumulados;
	
	private List<DataReportePorAtributo> listDatasAMostrar;
	
	private String nombreAtributoSelect;
	
	private double resultadoNumerico;
	
	private boolean esNumerico;
	//private List<DataProducto> listDataProductos;

	public boolean isEsNumerico() {
		return esNumerico;
	}

	public void setEsNumerico(boolean esNumerico) {
		this.esNumerico = esNumerico;
	}

	@PostConstruct
	public void init() {
	listDataAtriAcumulados = reporteNegocio.getDatasAtributosAcumulados(nav
				.getIdAlmacenActual());
			
	resultadoNumerico=0;
	esNumerico=false;
		// setListDataComentarios(almacenNegocio.listadeComentarios(nav.getIdAlmacenActual()));
		// idAlmacen=nav.getIdAlmacenActual();
		// emailUsuarioLogueado=usuarioLogueado.getEmail();
		// contenido="";
	}

	public void ejecutarReporte() {
		List<DataProducto> listDataProductos = almacenNegocio
				.getProductosActivosDeAlmacenConAtributo(
						nav.getIdAlmacenActual(), nombreAtributoSelect);
		
		
		if (!listDataProductos.isEmpty()) {
			for (DataProducto p : listDataProductos) {
				System.out.println("Producto NOMBRE: "
						+ p.getNombre()  +" ReporteAtributoBean");

			}
		}else {System.out.println(" NO HAY PRODUCTOS CON ATRIBUTO: "+ nombreAtributoSelect +" ReporteAtributoBean");}
		
		

		// List<Producto> listProd = productoDAO.getProductosAlmacen(idAlmacen);

		//List<DataAtributoAcumulado> listaDatosResult = new LinkedList<DataAtributoAcumulado>();

		//List<Atributo> atributosVista = new LinkedList<Atributo>();

		try {

			if (!listDataProductos.isEmpty()) {
				for (DataProducto p : listDataProductos) {
					List<Atributo> atributosVista = new LinkedList<Atributo>();
					String atributos = p.getAtributos();

					Gson gson = new Gson();

					Type collectionType = new TypeToken<Collection<Atributo>>() {
					}.getType();

					Collection<Atributo> atribs = gson.fromJson(atributos,
							collectionType);

					for (Iterator<Atributo> iterator = atribs.iterator(); iterator
							.hasNext();) {
						Atributo atributo = (Atributo) iterator.next();
						atributosVista.add(atributo);

					}
					/*
					 * private String nombre; 
					 * private String tipoDato; 
					 * private String valor; 
					 * private Date valorFecha; 
					 * private double valorNumero;
					 */

					if (!atributosVista.isEmpty()) {
						for (Atributo a : atributosVista) {
							//if (a.getNombre()==nombreAtributoSelect) {
							if (a.getNombre().equals(nombreAtributoSelect)) {
								System.out.println("COINCIDE ATRiBUTO "+a.getNombre()+"=="+nombreAtributoSelect);
								//Fecha, Numero, Texto
								DataReportePorAtributo dato = new DataReportePorAtributo();
								
								dato.setNombre(a.getNombre());
								dato.setNombreProducto(p.getNombre());
								
								dato.setProducto(p);
								
								dato.setTipoDato(a.getTipoDato());
								dato.setValor(a.getValor());
								//dato.setValorFecha(a.getValorFecha());
								//dato.setValorNumero(a.getValorNumero());

								listDatasAMostrar.add(dato);
								
								if(dato.getTipoDato().equals("Numero")){
									resultadoNumerico=resultadoNumerico+Double.parseDouble(dato.getValor()) ;
									System.out.println("Resultado NUMERICO ES: "+resultadoNumerico);
									esNumerico=true;
								}else System.out.println("NO ES NUMERICO");
								
								
								
								System.out.println("AGREGO EL DATO nombre producto: "+dato.getNombreProducto()+" nombre atributo: "+dato.getNombre());
							}
							else{
								System.out.println("NO COINCIDE ATRiBUTO "+a.getNombre()+"=="+nombreAtributoSelect);
								}
						}// for
					}// if
				}
				// listaDatosResult=ordenarPorFecha(listaDatosResult);
				// ESTO ES PARA ORDENAR LA LISTA EN ORDEN ASCENDENTE
				// Collections.sort(listaDatosResult,
				// Collections.reverseOrder());
				
				
				//Collections.sort(listaDatosResult);
				
				
				
				
			}// if

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*
	 * public String comentarEnAlmacen(){
	 * almacenNegocio.comentarEnAlmacen(usuarioLogueado.getEmail(), contenido,
	 * nav.getIdAlmacenActual()); contenido=""; return
	 * "index?faces-redirect=true"; }
	 * 
	 * public String descomentarEnAlmacen(){
	 * //almacenNegocio.comentarEnAlmacen(usuarioLogueado.getEmail(),
	 * idComentarioSelect, nav.getIdAlmacenActual()); //contenido="";
	 * almacenNegocio.descomentarEnAlmacen(usuarioLogueado.getEmail(),
	 * idComentarioSelect, nav.getIdAlmacenActual());
	 * 
	 * return "index?faces-redirect=true"; }
	 * 
	 * public boolean esComentarioMio(int idComentarioSelecionado){ return
	 * almacenNegocio.esComentarioDeUsuario(usuarioLogueado.getEmail(),
	 * idComentarioSelecionado, nav.getIdAlmacenActual()); }
	 * 
	 * public String getNombreUsuSelect() { return nombreUsuSelect; }
	 * 
	 * public void setNombreUsuSelect(String nombreUsuSelect) {
	 * this.nombreUsuSelect = nombreUsuSelect; }
	 * 
	 * public String getEmailUsuSelect() { return emailUsuSelect; }
	 * 
	 * public void setEmailUsuSelect(String emailUsuSelect) {
	 * this.emailUsuSelect = emailUsuSelect; }
	 */

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public NavigationAreaBean getNav() {
		return nav;
	}

	public void setNav(NavigationAreaBean nav) {
		this.nav = nav;
	}

	/*
	 * public List<DataComentario> getListDataComentarios() { return
	 * listDataComentarios; }
	 * 
	 * public void setListDataComentarios(List<DataComentario>
	 * listDataComentarios) { this.listDataComentarios = listDataComentarios; }
	 */

	/*
	 * public int getIdAlmacen() { return idAlmacen; }
	 * 
	 * public void setIdAlmacen(int idAlmacen) { this.idAlmacen = idAlmacen; }
	 * 
	 * public String getEmailUsuarioLogueado() { return emailUsuarioLogueado; }
	 * 
	 * public void setEmailUsuarioLogueado(String emailUsuarioLogueado) {
	 * this.emailUsuarioLogueado = emailUsuarioLogueado; }
	 */

	/*
	 * public String getContenido() { return contenido; }
	 * 
	 * public void setContenido(String contenido) { this.contenido = contenido;
	 * }
	 * 
	 * public int getIdComentarioSelect() { return idComentarioSelect; }
	 * 
	 * public void setIdComentarioSelect(int idComentarioSelect) {
	 * this.idComentarioSelect = idComentarioSelect; }
	 */

	public List<DataAtributoAcumulado> getListDataAtriAcumulados() {
		return listDataAtriAcumulados;
	}

	public void setListDataAtriAcumulados(
			List<DataAtributoAcumulado> listDataAtriAcumulados) {
		this.listDataAtriAcumulados = listDataAtriAcumulados;
	}

	public String getNombreAtributoSelect() {
		return nombreAtributoSelect;
	}

	public void setNombreAtributoSelect(String nombreAtributoSelect) {
		this.nombreAtributoSelect = nombreAtributoSelect;
	}

	/*public List<DataProducto> getListDataProductos() {
		return listDataProductos;
	}

	public void setListDataProductos(List<DataProducto> listDataProductos) {
		this.listDataProductos = listDataProductos;
	}*/

	public List<DataReportePorAtributo> getListDatasAMostrar() {
		return listDatasAMostrar;
	}

	public void setListDatasAMostrar(List<DataReportePorAtributo> listDatasAMostrar) {
		this.listDatasAMostrar = listDatasAMostrar;
	}

	public double getResultadoNumerico() {
		return resultadoNumerico;
	}

	public void setResultadoNumerico(double resultadoNumerico) {
		this.resultadoNumerico = resultadoNumerico;
	}

}
