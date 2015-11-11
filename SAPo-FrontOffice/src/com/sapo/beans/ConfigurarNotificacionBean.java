package com.sapo.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;

import com.datatypes.DataNotificacionConfig;
import com.datatypes.DataProducto;
import com.sapo.ejb.NotificacionNegocio;
import com.sapo.ejb.ProductoNegocio;

@ManagedBean
@SessionScoped
public class ConfigurarNotificacionBean {
	
	public ConfigurarNotificacionBean() {
	}

	private DataProducto producto;
	private int idProducto;
	private HashMap<String, String> listaCampos;
	private String nombreCampo;
	private List<String> listaOperadores;
	private String operador;
	private double valor;
	
	@EJB
	ProductoNegocio productoService;
	@EJB
	NotificacionNegocio notificacionService;
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;
	
	public DataProducto getProducto() {
		return producto;
	}

	public void setProducto(DataProducto producto) {
		this.producto = producto;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public HashMap<String, String> getListaCampos() {
		return listaCampos;
	}

	public void setListaCampos(HashMap<String, String> listaCampos) {
		this.listaCampos = listaCampos;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public List<String> getListaOperadores() {
		return listaOperadores;
	}

	public void setListaOperadores(List<String> listaOperadores) {
		this.listaOperadores = listaOperadores;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	@PostConstruct
	public void init(int idProducto) {
		
		this.listaCampos = new HashMap<>();
		this.listaOperadores = new ArrayList<String>();
		this.setIdProducto(idProducto);
		this.producto = this.productoService.getProductoPorId(idProducto);//Cargo el producto para después obtener los atributos.
		//Cargo los operadores
		this.listaOperadores.add(">");
		this.listaOperadores.add(">=");
		this.listaOperadores.add("=>");
		this.listaOperadores.add("<");
		this.listaOperadores.add("<=");
		//Cargo los campos disponibles
		this.listaCampos.put( "Stock", "stock");
		this.listaCampos.put( "Precio", "precio");
		
	}
	
	public String configurarNotificacion(){
		DataNotificacionConfig dNotificacion = new DataNotificacionConfig();
		dNotificacion.setIdProducto(this.producto.getIdProducto());
		dNotificacion.setNombreCampo(this.getNombreCampo());
		dNotificacion.setOperador(this.getOperador());
		dNotificacion.setValor(this.getValor());
		dNotificacion.setIdUsuario(this.usuarioLogueado.getIdUsuario());
		
		this.notificacionService.configurarNotificacion(dNotificacion);
		
		this.listaCampos = new HashMap<>();
		this.listaOperadores = new ArrayList<String>();
		this.valor = 0.0;
		
		return "index.xhtml";
	}
	

	/*public void comboChangeListener(AjaxBehaviorEvent event) {
		System.out.println(this.tipoDataAtr);
	}

	public void handleDateSelect(AjaxBehaviorEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(format.format(this.valorAtributoFecha));
	}*/
}
