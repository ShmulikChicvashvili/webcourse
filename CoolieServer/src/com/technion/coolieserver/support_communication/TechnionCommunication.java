package com.technion.coolieserver.support_communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.technion.coolieserver.ug.framework.CourseClient;

public class TechnionCommunication implements ITechnionCommunication {
  private static final String loginUrl = "http://techmvs.technion.ac.il/cics/wmn/wmngrad?ORD=1";
  private static final String technionBaseUri = "http://techmvs.technion.ac.il";
  private static final String technionCourseUri = "http://ug.technion.ac.il/rishum/search.php";
  CommunicationParameters params;

  @Override
  public String getGradesSheet(String id, String password) throws IOException {
    params = new CommunicationParameters();
    params.method = "POST";
    params.userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:19.0) Gecko/20100101 Firefox/19.0";
    params.accept = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    params.acceptLanguage = "he,en-US,en;q=0.5";
    params.acceptEncoding = "gzip, deflate";
    params.referer = "http://techmvs.technion.ac.il/cics/wmn/wmngrad?aabtvkfd&ORD=1";
    params.connection = "keep-alive";
    params.contentType = "application/x-www-form-urlencoded";
    params.httpParams = String.format("function=signon&userid=%s&password=%s",
        id, password);
    params.contentLength = Integer
        .toString(params.httpParams.getBytes().length);
    // return GradesSheet
    // .parseFromHtml(getGradesHtml(getCurrentGradesUrl(getLoginHtml())));
    return getGradesHtml(getCurrentGradesUrl(getLoginHtml()));
  }

  @Override
  public CourseClient getCourse(String courseNumber) {
    return null;
  }

  private String getGradesHtml(final String url) throws IOException {

    final HttpURLConnection $ = (HttpURLConnection) new URL(url)
        .openConnection();
    $.setRequestMethod(params.method);
    $.setDoInput(true);
    $.setDoOutput(true);
    $.setRequestProperty("User-Agent", params.userAgent);
    $.setRequestProperty("Accept", params.accept);
    $.setRequestProperty("Accept-Language", params.acceptLanguage);
    $.setRequestProperty("Accept-Encoding", params.acceptEncoding);
    $.setRequestProperty("Referer", params.referer);
    $.setRequestProperty("Connection", params.connection);
    $.setRequestProperty("Content-Type", params.contentType);
    $.setRequestProperty("Content-Length", params.contentLength);
    try (final DataOutputStream wr = new DataOutputStream($.getOutputStream())) {
      wr.writeBytes(params.httpParams);
      wr.flush();
    }
    // Scanner inStream = new Scanner($.getInputStream());
    // while (inStream.hasNextLine())
    // System.out.println((inStream.nextLine()));
    // inStream.close();
    // Get Response
    return readStream($.getInputStream());

  }

  private static String getLoginHtml() throws IOException {
    return readStream(new URL(loginUrl).openStream());
  }

  private static String readStream(InputStream openStream) throws IOException {
    InputStream in = openStream;
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    String $, line = reader.readLine();
    $ = line;
    while ((line = reader.readLine()) != null) {
      $ += line;
    }
    return $;
  }

  private static String getCurrentGradesUrl(final String loginPageHtml) {
    final Document doc = Parser.parse(loginPageHtml, technionBaseUri);
    final Elements forms = doc.select("form");
    return forms.first().attr("action");
  }
}
