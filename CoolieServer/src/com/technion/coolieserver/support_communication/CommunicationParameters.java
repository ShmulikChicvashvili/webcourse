package com.technion.coolieserver.support_communication;

public class CommunicationParameters {
  public String method;
  public String userAgent;
  public String accept;
  public String acceptLanguage;
  public String acceptEncoding;
  public String referer;
  public String connection;
  public String contentType;
  public String httpParams;
  public String contentLength;

  public CommunicationParameters() {

  }

  /**
   * @param method
   * @param userAgent
   * @param accept
   * @param acceptLanguage
   * @param acceptEncoding
   * @param referer
   * @param connection
   * @param contentType
   * @param httpParams
   * @param contentLength
   */
  public CommunicationParameters(String method, String userAgent,
      String accept, String acceptLanguage, String acceptEncoding,
      String referer, String connection, String contentType, String httpParams,
      String contentLength) {
    this.method = method;
    this.userAgent = userAgent;
    this.accept = accept;
    this.acceptLanguage = acceptLanguage;
    this.acceptEncoding = acceptEncoding;
    this.referer = referer;
    this.connection = connection;
    this.contentType = contentType;
    this.httpParams = httpParams;
    this.contentLength = contentLength;
  }

}
