package support_communication.api;

/**
 * 11.12.2013
 * 
 * @author DANIEL
 * 
 */
public interface IHtmlService {
  /**
   * 
   * @param url
   *          the wanted url
   * @return the html page as string that corresponding to the url
   */
  public String getHtmlPage(String url);
}
