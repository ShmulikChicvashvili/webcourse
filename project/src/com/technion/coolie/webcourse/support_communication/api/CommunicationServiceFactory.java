package support_communication.api;

import support_communication.HtmlService;

/**
 * Created on 11.12.2013
 * 
 * @author DANIEL
 * 
 */
public class CommunicationServiceFactory {
  /**
   * 
   * @return instance of HtmlService thats implements IHtmlService
   */
  public static IHtmlService getHtmlService() {
    return new HtmlService();
  }

}
