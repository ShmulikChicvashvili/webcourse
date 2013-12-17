package support_communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import support_communication.api.IHtmlService;

/**
 * Created on 11.12.2013
 * 
 * @author DANIEL
 * 
 */
public class HtmlService implements IHtmlService {
  private static final Logger log = Logger.getLogger(HtmlService.class
      .getName());

  @Override
  public String getHtmlPage(String url) {
    try {
      URL uri = new URL(url);
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          uri.openStream()));
      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
      reader.close();
      return sb.toString();
    } catch (Exception e) {
      log.severe(e.toString() + "  " + url);
      return null;
    }
  }
}
