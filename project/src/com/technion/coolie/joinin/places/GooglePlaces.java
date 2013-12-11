package com.technion.coolie.joinin.places;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.technion.coolie.joinin.communication.ClientProxy.Executer;
import com.technion.coolie.joinin.communication.ClientProxy.OnDone;
import com.technion.coolie.joinin.communication.ClientProxy.OnDoneAsyncTask;
import com.technion.coolie.joinin.communication.ClientProxy.OnError;

/**
 * Google Places queries encapsulation
 * 
 * @author Shimon Kama
 * 
 */
public class GooglePlaces {
  static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  private static final String PLACES_TEXT_SERVER = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
  private static final String PLACES_AUTOCOMPLETE_SERVER = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";
  private static final String API_KEY = "AIzaSyA7QHkKSZxwz52HiBnNR2XBhsmxjFH3qoQ";
  
  /**
   * A Text search request to the Google Places server.
   * 
   * @param query
   *          The query to search
   * @param onDone
   *          A listener which will be activated when the search is done
   * @param onError
   *          A listener which will be activated when an error occurs
   */
  public static void search(final String query, final OnDone<PlacesList> onDone, final OnError onError) {
    new OnDoneAsyncTask<PlacesList>(new Executer<PlacesList>() {
      @Override public PlacesList execute() {
        try {
          final HttpRequestFactory httpRequestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override public void initialize(final HttpRequest request) {
              // Nothing special...
            }
          });
          final HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl(PLACES_TEXT_SERVER));
          request.getUrl().put("key", API_KEY);
          request.getUrl().put("query", query);
          request.getUrl().put("sensor", "false");
          return PlacesList.fromJson(request.execute().parseAsString());
        } catch (final Exception e) {
          throw new RuntimeException("IO exception from server");
        }
      }
    }, onDone, onError).execute();
  }
  
  /**
   * An autocomplete query to the Google Places server
   * 
   * @param query
   *          the query to complete
   * @return a list of suggestions
   * @throws Exception
   */
  public static ArrayList<String> autocomplete(final String query) throws Exception {
    final HttpRequestFactory httpRequestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
      @Override public void initialize(final HttpRequest request) {
        // Nothing special...
      }
    });
    final HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl(PLACES_AUTOCOMPLETE_SERVER));
    request.getUrl().put("key", API_KEY);
    request.getUrl().put("input", query);
    request.getUrl().put("sensor", "false");
    // Create a JSON object hierarchy from the results
    final JSONArray predsJsonArray = new JSONObject(request.execute().parseAsString()).getJSONArray("predictions");
    // Extract the Place descriptions from the results
    final ArrayList<String> $ = new ArrayList<String>(predsJsonArray.length());
    for (int i = 0; i < predsJsonArray.length(); i++)
      $.add(predsJsonArray.getJSONObject(i).getString("description"));
    return $;
  }
}
