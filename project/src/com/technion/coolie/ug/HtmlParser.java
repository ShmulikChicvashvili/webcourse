package com.technion.coolie.ug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Context;

public class HtmlParser {
	private static StringBuilder response;

	public static Document parseFromFille(String filename, Context context) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					context.getAssets().open(filename), "ISO-8859-8"));
			response = new StringBuilder();
			String inputLine;

			while ((inputLine = reader.readLine()) != null)
				response.append(inputLine);
			reader.close();
			// System.out.println(response.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = Jsoup.parse(/* "\u200F" + */response.toString());
		
	
		return doc;
	}
}
