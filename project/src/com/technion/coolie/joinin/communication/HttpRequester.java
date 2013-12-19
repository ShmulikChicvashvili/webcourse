package com.technion.coolie.joinin.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/**
 * Class for general communication operation.
 * 
 * @author Shimon Kama (Originally by Yaniv Beaudoin)
 * 
 */
public class HttpRequester {
  private static final int GIVEUP = 10;
  
  /**
   * Converting an InputStream to a String
   * 
   * @param is
   *          The InputStream to read
   * @return The input stream as a string
   * @throws IOException
   */
  public static String readInputStream(final InputStream is) throws IOException {
    final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-16"));
    final StringBuilder sb = new StringBuilder(br.readLine());
    String line;
    while ((line = br.readLine()) != null)
      sb.append('\n' + line);
    return sb.toString();
  }
  
  /**
   * Executing a given HttpUriRequest
   * 
   * @param hr
   *          The HttpUriRequest to be executed
   * @return The result of the http request
   */
  private static String httpRequest(final HttpUriRequest hr) {
    final HttpClient hc = new DefaultHttpClient();
    int c = 0;
    String $ = null;
    while ($ == null && c < GIVEUP)
      try {
        $ = readInputStream(hc.execute(hr).getEntity().getContent());
      } catch (final Exception e) {
        // Do nothing
      } finally {
        c++;
      }
    if (c >= GIVEUP)
      throw new RuntimeException("Connection error");
    return $;
  }
  
  /**
   * Executing an http get request to a given uri
   * 
   * @param uri
   *          The uri of the http get request
   * @return The result of the http get request
   */
  public static String httpGet(final String uri) {
    return httpRequest(new HttpGet(uri));
  }
  
  /**
   * Executing an http delete request to a given uri
   * 
   * @param uri
   *          The uri of the http delete request
   * @return The result of the http delete request
   */
  public static String httpDelete(final String uri) {
    return httpRequest(new HttpDelete(uri));
  }
  
  /**
   * Executing an http put request to a given uri. The data is passed through a
   * buffer so all characters will pass as they should.
   * 
   * @param uri
   *          The uri of the http put request
   * @param data
   *          Data to be buffered and transfered to the server
   * @return The result of the http put request
   */
  public static String httpPut(final String uri, final String data) {
    String $ = null;
    try {
      final URL url = new URL(uri);
      final HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
      httpCon.setDoOutput(true);
      httpCon.setRequestMethod("PUT");
      final OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream(), "UTF-16");
      out.write(data);
      out.close();
      Log.e("Response code", "" + httpCon.getResponseCode());
      $ = readInputStream(httpCon.getInputStream());
      Log.e("Response code", "" + httpCon.getResponseCode());
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return $;
  }
}