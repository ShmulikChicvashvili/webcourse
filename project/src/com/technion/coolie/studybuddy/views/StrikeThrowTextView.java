package com.technion.coolie.studybuddy.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import com.technion.coolie.R;

public class StrikeThrowTextView extends TextView
{
	private boolean isStriked;
	private Bitmap bitmap;

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public StrikeThrowTextView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public StrikeThrowTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public StrikeThrowTextView(Context context)
	{
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.TextView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (bitmap == null)
			try
			{
				bitmap = Bitmap.createScaledBitmap(BitmapFactory
						.decodeResource(getResources(),
								R.drawable.stb_strikethrough), getWidth(),
						getHeight(), true);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		if (isStriked)
			canvas.drawBitmap(bitmap, 0, 0, null);
	}

	/**
	 * @param isStriked
	 *            the isStriked to set
	 */
	public synchronized void setStriked(boolean isStriked)
	{
		this.isStriked = isStriked;
		postInvalidate();
	}

	/**
	 * @return the isStriked
	 */
	public synchronized boolean isStriked()
	{
		return isStriked;
	}

}
