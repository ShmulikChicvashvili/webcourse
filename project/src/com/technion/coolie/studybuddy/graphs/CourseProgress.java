package com.technion.coolie.studybuddy.graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

public class CourseProgress {
	private Map<String, Integer> name2progress;
	private int currentWeek;
	private int lastWeek;

	CourseProgress(Map<String, Integer> name2progress, int currentWeek,
			int lastWeek) {
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
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer() {
			private static final long serialVersionUID = -1671341831565113605L;

			{
				setOrientation(Orientation.VERTICAL);
				setShowLegend(false);
				setZoomEnabled(false);
				setPanEnabled(false);
				setBarSpacing(0.5);
				setAxisTitleTextSize(16);
				setChartTitleTextSize(20);
				setLabelsTextSize(19);
				setMargins(new int[] { 25, 20, 100, 20 }); // right, top, left, bottom

				setShowGrid(true);
				setShowAxes(false);
				
				setXLabelsAlign(Align.RIGHT);
				setYLabelsAlign(Align.LEFT);
				
				setXLabelsPadding(0);
				
				setYLabelsPadding(20);
				setYLabelsVerticalPadding(-10);
				// renderer.setYLabelsVerticalPadding(10);
				//setLabelsTextSize(12);
				//setTextTypeface(Typeface.MONOSPACE);

				setYAxisMin(0);
				setYAxisMax(lastWeek);

				setXAxisMin(0.5);
				setXAxisMax(name2progress.size() + 0.5);

				int i = 0;
				for (String name : name2progress.keySet()) {
					addXTextLabel(++i, name);
				}
				
				setXLabels(0);		// Hides numbers from, leaves only text 
				setYLabels(15);

				SimpleSeriesRenderer r = new SimpleSeriesRenderer();
				r.setColor(Color.RED);
				addSeriesRenderer(r);
				r = new SimpleSeriesRenderer();
				r.setColor(Color.GREEN);
				addSeriesRenderer(r);
			}
		};
		return renderer;
	}
}
