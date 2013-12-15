package com.technion.coolie.studybuddy.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import android.graphics.Color;

public class CourseProgress {
	private Map<String, Integer> name2progress;
	private int currentWeek;
	private int lastWeek;
	
	CourseProgress(Map<String, Integer> name2progress, int currentWeek, int lastWeek) {
		this.name2progress = name2progress;
		this.currentWeek = currentWeek;
		this.lastWeek = lastWeek;
	}
	
	protected static XYMultipleSeriesDataset buildBarDataset(String[] titles,
			List<double[]> values) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}
	
	XYMultipleSeriesDataset getDataset() {
		String[] titles = new String[] { "Total", "Done" };
	    List<double[]> values = new ArrayList<double[]>();
	    
	    double[] currentWeeks = new double[name2progress.size()];
	    Arrays.fill(currentWeeks, currentWeek);
	    values.add(currentWeeks);
	    
	    double[] progressWeeks = new double[name2progress.size()];
	    int i = 0;
	    for (Integer progress : name2progress.values()) {
	    	progressWeeks[i++] = progress.doubleValue();
	    }
	    values.add(progressWeeks);
	    return buildBarDataset(titles, values);
	}
	
	XYMultipleSeriesRenderer getRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setOrientation(Orientation.VERTICAL);
		renderer.setZoomEnabled(false);
		renderer.setPanEnabled(false);
		renderer.setBarSpacing(1);
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		
		renderer.setYAxisMin(0);
		renderer.setYAxisMax(lastWeek);
		
		renderer.setXAxisMin(0);
		renderer.setXAxisMax(name2progress.size() + 1);
		
		int i = 0;
		for (String name : name2progress.keySet()) {
			renderer.addXTextLabel(++i, name);
		}
		

		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.RED);
		renderer.addSeriesRenderer(r);
		r = new SimpleSeriesRenderer();
		r.setColor(Color.GREEN);
		renderer.addSeriesRenderer(r);
		return renderer;		
	}
}
