package com.sapo.beans;

import javax.annotation.PostConstruct;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

import com.datatypes.DataDatoGrafico;
import com.sapo.dao.ProductoDAO;
import com.sapo.ejb.ReporteNegocio;

@ManagedBean
public class ChartView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ReporteNegocio reporteNegocio;

	// private LineChartModel lineModel1;
	private LineChartModel lineModel2;

	private List<DataDatoGrafico> listDatos;
	
	private float gananciaMaximaEnunMes;
	
	
	public ChartView (){
		
		this.listDatos = new LinkedList<DataDatoGrafico>();
	};
	

	@PostConstruct
	public void init() {
		this.gananciaMaximaEnunMes = reporteNegocio.getMaximoGananciaEnUnMes();
		this.listDatos = reporteNegocio.getDatosGraficoGanancias();
		createLineModels();
		
	}

	/*
	 * public LineChartModel getLineModel1() { return lineModel1; }
	 */

	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	private void createLineModels() {
		// el originallineModel1 =initLinearModel();
		// lineModel1 = initCategoryModel();

		// lineModel1.setTitle("Linear Chart");
		// lineModel1.setLegendPosition("e");
		// Axis yAxis = lineModel1.getAxis(AxisType.Y);
		// yAxis.setMin(0);
		// yAxis.setMax(10);

		lineModel2 = initCategoryModel();
		lineModel2.setTitle("Ganancia-Tiempo");
		lineModel2.setLegendPosition("e");
		lineModel2.setShowPointLabels(true);

		lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Tiempo"));

		Axis yAxis = lineModel2.getAxis(AxisType.Y);

		yAxis.setLabel("Ganancias U$D");

		yAxis.setMin(0);
		
		
		yAxis.setMax(this.gananciaMaximaEnunMes+5);
	}

	/*
	 * private CartesianChartModel initLinearModel() { CartesianChartModel model
	 * = new CartesianChartModel();
	 * 
	 * LineChartSeries series1 = new LineChartSeries();
	 * series1.setLabel("Series 1");
	 * 
	 * series1.set(1, 2); series1.set(2, 1); series1.set(3, 3); series1.set(4,
	 * 6); series1.set(5, 8);
	 * 
	 * LineChartSeries series2 = new LineChartSeries();
	 * series2.setLabel("Series 2");
	 * 
	 * series2.set(1, 6); series2.set(2, 3); series2.set(3, 2); series2.set(4,
	 * 7); series2.set(5, 9);
	 * 
	 * model.addSeries(series1); model.addSeries(series2);
	 * 
	 * return model; }
	 */

	private LineChartModel initCategoryModel() {
		LineChartModel model = new LineChartModel();

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Ganancias Premium");
		
		System.out.println("LISTA DE DATOS ES VACIA?"+this.listDatos.isEmpty());
		
		if(!this.listDatos.isEmpty()){
			
			for (DataDatoGrafico dato:listDatos){
				System.out.println("SETEO DAO GRAFICO, LA FECHA ES: "+dato.getMes()+"/"+dato.getAnio()+" LA GANANCIA ES: "+dato.getGananciaMes());
				boys.set(dato.getMes()+"/"+dato.getAnio(), dato.getGananciaMes());
			}
			
		}else{
			boys.set("2004", 120);
			boys.set("2005", 100);
			boys.set("2006", 44);
			boys.set("2007", 150);
			boys.set("2008", 25);
		}
		
		
		
		
		

		/*ChartSeries girls = new ChartSeries();
		girls.setLabel("Girls");
		girls.set("2004", 52);
		girls.set("2005", 60);
		girls.set("2006", 110);
		girls.set("2007", 90);
		girls.set("2008", 120);*/

		model.addSeries(boys);
		//model.addSeries(girls);

		return model;
	}

	/*
	 * public void setLineModel1(LineChartModel lineModel1) { this.lineModel1 =
	 * lineModel1; }
	 */

	public void setLineModel2(LineChartModel lineModel2) {
		this.lineModel2 = lineModel2;
	}

	public List<DataDatoGrafico> getListDatos() {
		return listDatos;
	}

	public void setListDatos(List<DataDatoGrafico> listDatos) {
		this.listDatos = listDatos;
	}


	public float getGananciaMaximaEnunMes() {
		return gananciaMaximaEnunMes;
	}


	public void setGananciaMaximaEnunMes(float gananciaMaximaEnunMes) {
		this.gananciaMaximaEnunMes = gananciaMaximaEnunMes;
	}

}
