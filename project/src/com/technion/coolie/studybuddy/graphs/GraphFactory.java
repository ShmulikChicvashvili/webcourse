package com.technion.coolie.studybuddy.graphs;

import java.util.LinkedHashMap;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;

import android.content.Context;
import android.view.View;

public class GraphFactory {
	public static View getCourseProgressGraph(Context ctx,
			Map<String, Integer> name2progress, int currentWeek, int lastWeek) {
		CourseProgress cp = new CourseProgress(name2progress, currentWeek,
				lastWeek);
		return ChartFactory.getBarChartView(ctx, cp.getDataset(),
				cp.getRenderer(), Type.STACKED);

	}

	public static View getSampleCourseProgress(Context ctx) {
		final int CURRENT_WEEK = 8;
		final int LAST_WEEK = 14;
		Map<String, Integer> name2progress = new LinkedHashMap<String, Integer>();

		name2progress.put("HWs", Integer.valueOf(6));
		name2progress.put("Tutorials", Integer.valueOf(4));
		name2progress.put("Lectures", Integer.valueOf(5));
		name2progress.put("Video", Integer.valueOf(9));

		return getCourseProgressGraph(ctx, name2progress, CURRENT_WEEK,
				LAST_WEEK);
	}
}
