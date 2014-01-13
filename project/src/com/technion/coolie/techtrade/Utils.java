package com.technion.coolie.techtrade;

import java.util.Calendar;
import java.util.Vector;

import com.technion.coolie.R;

import android.content.Context;
import android.graphics.Bitmap;

public class Utils {

	/******************
	 * 
	 * @param d - Double number to be formatted
	 * @return String representing the number as is if double and without
	 * decimal point if it's an integer.
	 ******************/
	public static String parseDoubleToString(Double d){
		return (d%1 == 0)?String.valueOf(d.intValue()):d.toString();
	}

	public static Vector<Product> getProductVectorStub(Context context, int n) {
		Vector<Product> productVector = new Vector<Product>();
		Bitmap bmp = BitmapOperations.decodeBitmapFromResource(context.getResources(), R.drawable.trad_icon, 0, 0);
		for(int i=0;i<n;++i){
			productVector.add(new Product("product name "+i, "Seller id"+i, Category.CLOTHING, (double) i, "some description"+i+"\r\nand another line",""+i, bmp, "seller name "+i));
			productVector.elementAt(i).setSellDateInMillis(Calendar.getInstance().getTimeInMillis());
			productVector.elementAt(i).setBuyerName("Moshe Cohen "+i);
		}
		return productVector;
	}
}
