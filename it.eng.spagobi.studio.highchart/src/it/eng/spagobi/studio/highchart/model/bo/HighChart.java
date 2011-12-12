/**
SpagoBI - The Business Intelligence Free Platform

Copyright (C) 2005-2008 Engineering Ingegneria Informatica S.p.A.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 **/
package it.eng.spagobi.studio.highchart.model.bo;

import java.util.Vector;

import it.eng.spagobi.studio.highchart.editors.sections.GeneralSection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HighChart {

	private String width;
	private String height;
	private Chart chart;
	private Title title;
	private Legend legend;
	private SubTitle subTitle;
	private XAxis xAxis;
	private YAxis yAxis;
	private PlotOptions plotOptions;
	private Drill drill;
	private SeriesList seriesList;

//	private static Logger logger = LoggerFactory.getLogger(HighChart.class);


	public Drill getDrill() {
		if(drill == null) drill = new Drill();
		return drill;
	}
	public SeriesList getSeriesList() {
		if(seriesList == null)seriesList = new SeriesList();
		return seriesList;
	}
	public void setSeriesList(SeriesList seriesList) {
		this.seriesList = seriesList;
	}
	public void setDrill(Drill drill) {
		this.drill = drill;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public Chart getChart() {
		if(chart== null)chart = new Chart();
		return chart;
	}
	public void setChart(Chart chart) {
		this.chart = chart;
	}
	public Title getTitle() {
		if(title == null) title = new Title();
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public SubTitle getSubTitle() {
		if(subTitle == null) subTitle = new SubTitle();
		return subTitle;
	}
	public void setSubTitle(SubTitle subTitle) {
		this.subTitle = subTitle;
	}
	public Legend getLegend() {
		if(legend == null) legend = new Legend();
		return legend;
	}
	public void setLegend(Legend legend) {
		this.legend = legend;
	}
	public XAxis getxAxis() {
		if(xAxis == null) xAxis = new XAxis();	
		return xAxis;
	}
	public void setxAxis(XAxis xAxis) {
		this.xAxis = xAxis;
	}
	public YAxis getyAxis() {
		if(yAxis == null) yAxis = new YAxis();
		return yAxis;
	}
	public void setyAxis(YAxis yAxis) {
		this.yAxis = yAxis;
	}
	public PlotOptions getPlotOptions() {
		if(plotOptions == null) plotOptions =  new PlotOptions();	
		return plotOptions;
	}
	public void setPlotOptions(PlotOptions plotOptions) {
		this.plotOptions = plotOptions;
	}


	public Integer getIntegerWidth() {
		Integer i=100;
		if(width!= null){
			// remove %
			String s = width.substring(0, width.length()-1);
			try{
				i = Integer.valueOf(s);
			}
			catch (Exception e) {
//				logger.error("Error in converting width value "+s+" into Integer value");
			}
		}
		return i;
	}
	
	public Integer getIntegerHeight() {
		Integer i=100;
		if(height!= null){
			// remove %
			String s = height.substring(0, height.length()-1);
			try{
				i = Integer.valueOf(s);
			}
			catch (Exception e) {
//				logger.error("Error in converting width height "+s+" into Integer value");
			}
		}
		return i;
	}

}