package com.technion.coolie.studybuddy.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.technion.coolie.R;

public class StrikeThrowView extends RelativeLayout
{
	private boolean strike = false;
	private Bitmap bitmap;
	private Paint paint;

	// private Rect from;
	// private Rect to;

	private void setBitmaps()
	{
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		// bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.strikethrough);

	}

	public StrikeThrowView(Context context)
	{
		super(context);
		setBitmaps();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public StrikeThrowView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		setBitmaps();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public StrikeThrowView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setBitmaps();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
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
		if (strike)
			canvas.drawBitmap(bitmap, 0, 0, null);

	}

	/**
	 * @return the strike
	 */
	public synchronized boolean isStrike()
	{
		return strike;
	}

	/**
	 * @param strike
	 *            the strike to set
	 */
	public synchronized void setStrike(boolean strike)
	{
		this.strike = strike;
		postInvalidate();
	}

}
