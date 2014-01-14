package com.technion.coolie.tecmind;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;


public class PieChart extends Drawable {
    private int[] dataValues;
	private int[] colorValues;
	private String[] dataNames;
	private List<Float> startAngles = new ArrayList<Float>();
	Paint paint;
	private View view;
	Canvas canvas;
    RectF arcBounds;
    int x;
    int y;

	public PieChart(String[] names, View v, int[] values, int[] colors, int x, int y) {
        
        view = v;
        this.dataValues = values;
        this.dataNames = names;
        paint = new Paint();
        colorValues = colors;
        this.x = x;
        this.y = y;
        
        
    }
    
    public void draw(Canvas canvas) {

    	this.canvas = canvas;
    	 //screen width & height
    	 int view_w = view.getWidth();
         int view_h = view.getHeight();
        
    	  /*
         RectF shadowBounds = new RectF(100, 70, 630, 600);
         
         Paint shadowPaint = new Paint();
         shadowPaint.setColor(Color.parseColor("#606060"));
         shadowPaint.setAntiAlias(true);
         shadowPaint.setStyle(Paint.Style.FILL);
         shadowPaint.setStrokeWidth(0.5f);
         shadowPaint.setShadowLayer(40, -20, 0, Color.parseColor("#606060"));*/

         
          /*LinearGradient linearGradient = new LinearGradient(shadowBounds.left, 
        		  shadowBounds.top, shadowBounds.right,shadowBounds.bottom, Color.parseColor("#606060"), Color.parseColor("#0a0a0"), Shader.TileMode.CLAMP);
          shadowPaint.setShader(linearGradient);*/

         //canvas.drawArc(shadowBounds, 0, 360, true, shadowPaint);
         
	     //chart area rectangle
	      arcBounds = new RectF(x, y, x+view.getWidth()*3/4, y+view.getWidth()*3/4);

	      int valueSum = 0;
			//sum of data values
	        for (int d : dataValues) {
	        		valueSum += d;
	        }
	        
	        float startAngle = 0;
	        int i = 0;
	  
	        for (int datum : dataValues) {
	                if (datum == 0) continue;
	                
	                //calculate start & end angle for each data value
	                float endAngle = valueSum == 0 ? 0 : 360 * datum / (float) valueSum;
	                float newStartAngle = startAngle + endAngle;
	                
	             
	                int color = colorValues[i % colorValues.length];
	                paint.setColor(color);
	                paint.setAntiAlias(true);
	                paint.setStyle(Paint.Style.FILL);
	                paint.setStrokeWidth(0.5f);
	                
	                
	                //gradient fill color
	           /*     LinearGradient linearGradient = new LinearGradient(arcBounds.left, 
	                		arcBounds.top, arcBounds.right,arcBounds.bottom, color, Color.WHITE, Shader.TileMode.CLAMP);
	                paint.setShader(linearGradient);
	             */   
	                //draw fill arc
	                canvas.drawArc(arcBounds, startAngle, endAngle, true, paint);
	            
	                Paint linePaint = new Paint();
	                linePaint.setAntiAlias(true);
	                linePaint.setStyle(Paint.Style.STROKE);
	                linePaint.setStrokeJoin(Join.ROUND);
	                linePaint.setStrokeCap(Cap.ROUND);
	                linePaint.setStrokeWidth(0.01f);
	                linePaint.setColor(Color.WHITE);

	                //draw border arc
	                canvas.drawArc(arcBounds, startAngle, endAngle, true, linePaint);
	                
	    /*            
	                int barStartX = 20;
	                int barWidth = 20;
	                int barStartY = 300+(i-1)*2*barWidth;
	               
	                Rect barRect = new Rect(barStartX,barStartY,barStartX+barWidth,barStartY+barWidth);
	                
	                //draw legend box
	                canvas.drawRect(barRect, paint);
	                canvas.drawRect(barRect,linePaint);
	                
	                
	                Paint textPaint = new Paint();
	                textPaint.setAntiAlias(true);
	                textPaint.setColor(Color.GRAY);
	                textPaint.setTextSize(30);
	                
	                //draw legend text
	                canvas.drawText(dataNames[i], barStartX+2*barWidth, barStartY+barWidth, textPaint);
	      */          
	                startAngles.add(startAngle);
	                startAngle = newStartAngle;
	                i++;
	        }
	        
	        /*y+view.getWidth()*3/4)*/
	        Paint textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setColor(Color.GRAY);
            textPaint.setTextSize(30);
	       
            //draw legend text
            canvas.drawText("Tap and hold chart to see groups", x, view_h-y*4, textPaint);
	  
	 }
    
    public String getTouchedGroupName(float x, float y) {
    	float centerX = (arcBounds.left +arcBounds.right) /2,
    			centerY = (arcBounds.bottom + arcBounds.top) /2;
    	
    	if (x > arcBounds.right || x < arcBounds.left || y < arcBounds.top || y > arcBounds.bottom) {
    		return "";
    	}
    	
    	float offX = x-centerX, offY = y-centerY;
    	float tmpAngle = (float)(Math.atan(Math.abs(offY) / Math.abs(offX)) * 180 / Math.PI);
    	float angle = 0;
    	if (offX > 0 && offY < 0) {
    		angle = 360 - tmpAngle;
    	} else if (offX < 0 && offY < 0) {
    		angle = 180 + tmpAngle;
    	} else if (offX < 0 && offY > 0) {
    		angle = 180 - tmpAngle;
    	} else if (offX > 0 && offY > 0){
    		angle = tmpAngle;
    	}
    	 
    	
    	int size = startAngles.size();
    	for (int i = 0; i < size; i++) {
    		if (i == size - 1) {
    			return dataNames[size-1];
    		}
    		if (angle > startAngles.get(i) && angle < startAngles.get(i+1)) {
    			return dataNames[i];
    		}
    	}
    	
    	return "";
    	
    	    	
    }
    
    public Drawable getItemsList() {
    	
    	int i = 0;
        for (int datum : dataValues) {
                if (datum == 0) continue;
                
               int color = colorValues[i % colorValues.length];
                paint.setColor(color);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(0.5f);
            
                
                int barStartX = 20;
                int barWidth = 20;
                int barStartY = 300+(i-1)*2*barWidth;
               
                Rect barRect = new Rect(barStartX,barStartY,barStartX+barWidth,barStartY+barWidth);
                
                //draw legend box
                Canvas canvas = new Canvas();
				canvas.drawRect(barRect, paint);
                
                
                Paint textPaint = new Paint();
                textPaint.setAntiAlias(true);
                textPaint.setColor(Color.GRAY);
                textPaint.setTextSize(30);
                
                //draw legend text
                canvas.drawText(dataNames[i], barStartX+2*barWidth, barStartY+barWidth, textPaint);
                
                i++;
        }
  
     	return null;
    }
    
    

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub
		
	}
	
	


}
