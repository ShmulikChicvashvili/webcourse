package com.technion.coolie.studybuddy.graphs;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;

import com.technion.coolie.studybuddy.models.Course;
import com.technion.coolie.studybuddy.models.Semester;
import com.technion.coolie.studybuddy.models.StudyResource;

import android.content.Context;
import android.view.View;

public class GraphFactory
{
	// TODO Dima API FOR Weekly Graph
	public static GraphicalView getWeeklyProgressGraph(Context ctx,
			Date firstDayOfWeek, int[] weeklyProgress)
	{
		WeeklyProgress wp = new WeeklyProgress(firstDayOfWeek, weeklyProgress);
		return ChartFactory.getBarChartView(ctx, wp.getDataset(),
				wp.getRenderer(), Type.DEFAULT);
	}

	/**
	 * Creates progress graph
	 * 
	 * @param ctx
	 * @param name2progress
	 *            study resource -> num week progress
	 * @param currentWeek
	 * @param lastWeek
	 *            (usually Semester.WEEKS_IN_SEMESTER)
	 * @return
	 */
	public static View getCourseProgressGraph(Context ctx,
			Map<String, Integer> name2progress, int currentWeek, int lastWeek)
	{
		CourseProgress cp = new CourseProgress(name2progress, currentWeek,
				lastWeek);
		return ChartFactory.getBarChartView(ctx, cp.getDataset(),
				cp.getRenderer(), Type.STACKED);

	}

	// TODO DIMA, do something like this in course view
	/**
	 * Example how to extract the needed information for progress graph from
	 * Course and Semester.
	 * 
	 * @param ctx
	 * @param c
	 * @param s
	 * @return progress graph
	 */
	public static View getProgressGraphFromCourseAndSemester(Context ctx,
			Course c, Semester s)
	{
		final int CURRENT_WEEK = s.getSemesterWeek(new Date(), ctx);
		final int LAST_WEEK = Semester.WEEKS_IN_SEMESTER;

		final Map<String, Integer> name2progress = new LinkedHashMap<String, Integer>();

		for (StudyResource sr : c.getStudyResources())
		{
			name2progress.put(sr.getName(),
					Integer.valueOf(sr.getDoneItemsCount()));
		}

		return getCourseProgressGraph(ctx, name2progress, CURRENT_WEEK,
				LAST_WEEK);
	}

	/**
	 * Bare-bones example.
	 * 
	 * @param ctx
	 * @return
	 */
	public static View getSampleCourseProgress(Context ctx)
	{
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
