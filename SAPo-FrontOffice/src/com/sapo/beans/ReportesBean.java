package com.sapo.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;

import com.datatypes.DataAlmacen;
import com.datatypes.DataReporteProducto;
import com.sapo.ejb.AlmacenNegocio;
import com.sapo.ejb.ReporteNegocio;
import com.sapo.utils.Atributo;


@ManagedBean
@RequestScoped
public class ReportesBean {
	
	public ReportesBean(){
	}
	
	
	private String tipoReporte;
	private HashMap<String,String> listaReportes;
	private boolean sinAlmacenes;
	private List<DataAlmacen> almacenes;
	
	
	private Date valorFechaInicio;
	private Date valorFechaFin;
	int idUsuario;
	int idAlmacen;
	private List<DataReporteProducto> listaReportesPorAlmacen;
	private List<DataReporteProducto> listaReportesPorFechas;
	
	
	@EJB
	AlmacenNegocio almacenNegocio;
	@EJB
	ReporteNegocio reporteNegocio;
	
	@ManagedProperty(value = "#{loginBean}")
	LoginBean usuarioLogueado;

	
	public String getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	
	
	public boolean isSinAlmacenes() {
		return sinAlmacenes;
	}

	public void setSinAlmacenes(boolean sinAlmacenes) {
		this.sinAlmacenes = sinAlmacenes;
	}

	public List<DataAlmacen> getAlmacenes() {
		return almacenes;
	}

	public void setAlmacenes(List<DataAlmacen> almacenes) {
		this.almacenes = almacenes;
	}

	public HashMap<String, String> getListaReportes() {
		return listaReportes;
	}

	public void setListaReportes(HashMap<String, String> listaReportes) {
		this.listaReportes = listaReportes;
	}

	public void comboChangeListener(AjaxBehaviorEvent event) {
		System.out.println(this.tipoReporte);
	}
	
	
	public AlmacenNegocio getAlmacenNegocio() {
		return almacenNegocio;
	}

	public void setAlmacenNegocio(AlmacenNegocio almacenNegocio) {
		this.almacenNegocio = almacenNegocio;
	}

	public ReporteNegocio getReporteNegocio() {
		return reporteNegocio;
	}

	public void setReporteNegocio(ReporteNegocio reporteNegocio) {
		this.reporteNegocio = reporteNegocio;
	}

	public LoginBean getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(LoginBean usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}
	

	public Date getValorFechaInicio() {
		return valorFechaInicio;
	}

	public void setValorFechaInicio(Date valorFechaInicio) {
		this.valorFechaInicio = valorFechaInicio;
	}

	public Date getValorFechaFin() {
		return valorFechaFin;
	}

	public void setValorFechaFin(Date valorFechaFin) {
		this.valorFechaFin = valorFechaFin;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdAlmacen() {
		return idAlmacen;
	}

	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	public List<DataReporteProducto> getListaReportesPorAlmacen() {
		return listaReportesPorAlmacen;
	}

	public void setListaReportesPorAlmacen(
			List<DataReporteProducto> listaReportesPorAlmacen) {
		this.listaReportesPorAlmacen = listaReportesPorAlmacen;
	}

	public List<DataReporteProducto> getListaReportesPorFechas() {
		return listaReportesPorFechas;
	}

	public void setListaReportesPorFechas(
			List<DataReporteProducto> listaReportesPorFechas) {
		this.listaReportesPorFechas = listaReportesPorFechas;
	}

	@PostConstruct
	public void init() {
		this.tipoReporte= "Texto";
		this.listaReportes = new HashMap<String, String>();
		this.listaReportes.put("Reporte por Almacén","seleccionReporte1");
		this.listaReportes.put("Reporte por Fechas", "seleccionReporte2");
		this.valorFechaInicio = null;
		this.valorFechaFin = null;
		this.tipoReporte  = "Texto";
		listarAlmacenes();
	}

	public void handleDateSelect(AjaxBehaviorEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
	}
	
	public void listarAlmacenes(){
		cargarAlmacenes();
		if (this.almacenes.size() == 0) {
			this.sinAlmacenes = true;
		}
		else
			this.sinAlmacenes = false;
	}
	
	public void cargarAlmacenes() {
		this.almacenes = this.almacenNegocio.getAlmacenes(this.usuarioLogueado
				.getEmail());
	}
	
	
	public void iniciarReporteAlmacen (){
		listaReportesPorAlmacen = this.reporteNegocio.
				buscarHistoricoProdPorUsuarioYAlmacen(this.usuarioLogueado.getIdUsuario(), idAlmacen);		
	}
	
	public void iniciarReporteFechas(){
		listaReportesPorFechas = this.reporteNegocio.
				buscarHistoricoProdPorUsuarioEnFecha(this.usuarioLogueado.getIdUsuario(), valorFechaInicio, valorFechaFin);		
	}
	
}
