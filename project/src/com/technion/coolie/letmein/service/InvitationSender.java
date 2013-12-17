package com.technion.coolie.letmein.service;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import android.util.Log;
import ch.boye.httpclientandroidlib.Consts;
import ch.boye.httpclientandroidlib.Header;
import ch.boye.httpclientandroidlib.HttpException;
import ch.boye.httpclientandroidlib.HttpMessage;
import ch.boye.httpclientandroidlib.HttpRequest;
import ch.boye.httpclientandroidlib.HttpRequestInterceptor;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.HttpResponseInterceptor;
import ch.boye.httpclientandroidlib.NameValuePair;
import ch.boye.httpclientandroidlib.client.CookieStore;
import ch.boye.httpclientandroidlib.client.ResponseHandler;
import ch.boye.httpclientandroidlib.client.entity.UrlEncodedFormEntity;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import ch.boye.httpclientandroidlib.cookie.Cookie;
import ch.boye.httpclientandroidlib.impl.client.BasicCookieStore;
import ch.boye.httpclientandroidlib.impl.client.BasicResponseHandler;
import ch.boye.httpclientandroidlib.impl.client.DecompressingHttpClient;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.impl.client.LaxRedirectStrategy;
import ch.boye.httpclientandroidlib.message.BasicNameValuePair;
import ch.boye.httpclientandroidlib.params.CoreProtocolPNames;
import ch.boye.httpclientandroidlib.protocol.HttpContext;

import com.technion.coolie.letmein.model.Invitation;

public class InvitationSender implements Closeable {

	private static final String LOG_TAG = InvitationSender.class.getSimpleName();
	private static final long VERSION_UID = 1L;
	private static final String SITE_URL = "https://portal.technion.ac.il";

	public static class LoginException extends Exception {
		private static final long serialVersionUID = VERSION_UID;

		public LoginException() {
			super("Login failed due to incorrect credantials");
		}
	}

	public static class ConnectionException extends Exception {
		private static final long serialVersionUID = VERSION_UID;

		public ConnectionException(final IOException cause) {
			super(cause);
		}
	}

	public static class InvitationFormatException extends Exception {
		private static final long serialVersionUID = VERSION_UID;

		public InvitationFormatException() {
			super("Invalid invitation format");
		}
	}

	public static class InvitationLimitExceededException extends Exception {
		private static final long serialVersionUID = VERSION_UID;

		public InvitationLimitExceededException() {
			super("Max invitation limit exceeded");
		}
	}

	public static class UnknownErrorException extends Exception {
		private static final long serialVersionUID = VERSION_UID;

		public UnknownErrorException() {
			super("An unknown error occured. Site format possibly changed.");
		}

		public UnknownErrorException(final String details) {
			super("An unknown error occured. Site format possibly changed.\n" + details);
		}
	}

	private static class SessionDescriptor {
		public String sessionId;
		public String windowId;
		public SAPSession sapSession;

		public static class SAPSession {
			public String sap_ext_sid;
			public String sap_wd_cltwndid;
			public String sap_wd_norefresh;
			public String sap_wd_secure_id;

			public List<NameValuePair> toNameValuePairs() {
				final List<NameValuePair> $ = new ArrayList<NameValuePair>();
				$.add(new BasicNameValuePair("sap-ext-sid", sap_ext_sid));
				$.add(new BasicNameValuePair("sap-wd-cltwndid", sap_wd_cltwndid));
				$.add(new BasicNameValuePair("sap-wd-norefresh", sap_wd_norefresh));
				$.add(new BasicNameValuePair("sap-wd-secure-id", sap_wd_secure_id));

				return $;
			}
		}
	}

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0";

	private final String baseUrl;
	private final SessionDescriptor sessionDescriptor = new SessionDescriptor();

	private final DecompressingHttpClient httpClient;
	private final CookieStore cookieStore = new BasicCookieStore();
	private final ResponseHandler<String> responseHandler = new BasicResponseHandler();

	private void logHttpMessage(final HttpMessage msg, final String title) {
		final StringBuilder sb = new StringBuilder();
		sb.append(title + "\n");
		sb.append(msg.toString() + "\n");
		sb.append("Cookies:\n" + cookieStore.getCookies() + "\n");
		sb.append("Headers:\n");
		for (final Header h : msg.getAllHeaders())
			sb.append(h.toString() + "\n");

		Log.d(LOG_TAG, sb.toString());
	}

	private InvitationSender(final String url) {
		final DefaultHttpClient backendClient = new DefaultHttpClient();

		// Make us look like an acceptable browser
		backendClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);

		// Handle cookies
		backendClient.setCookieStore(cookieStore);

		// Intercept request and responses for logging purposes
		backendClient.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(final HttpRequest request, final HttpContext context)
					throws HttpException, IOException {
				logHttpMessage(request, "Request");
			}
		});

		backendClient.addResponseInterceptor(new HttpResponseInterceptor() {
			@Override
			public void process(final HttpResponse response, final HttpContext context)
					throws HttpException, IOException {
				logHttpMessage(response, "Response");
			}
		});

		// Allow following POST redirects
		backendClient.setRedirectStrategy(new LaxRedirectStrategy());

		httpClient = new DecompressingHttpClient(backendClient);
		baseUrl = url;
	}

	private String executeRequest(final HttpUriRequest request) throws IOException {
		final String $ = httpClient.execute(request, responseHandler);
		System.out.println($);
		return $;
	}

	private String getAttributeValueByName(final Document document, final String attribute)
			throws UnknownErrorException {
		final Elements elements = document.getElementsByAttributeValue("name", attribute);
		if (elements.isEmpty())
			throw new UnknownErrorException("No attribute by name of " + attribute + " found");

		return elements.first().val();
	}

	private String getAttributeValueByName(final String html, final String attribute)
			throws UnknownErrorException {
		return getAttributeValueByName(Jsoup.parse(html), attribute);
	}

	private void generateSAPSessionDescriptor(final String html) throws UnknownErrorException {
		final SessionDescriptor.SAPSession $ = new SessionDescriptor.SAPSession();
		final Document document = Jsoup.parse(html);

		$.sap_ext_sid = getAttributeValueByName(document, "sap-ext-sid");
		$.sap_wd_cltwndid = getAttributeValueByName(document, "sap-wd-cltwndid");
		$.sap_wd_norefresh = getAttributeValueByName(document, "sap-wd-norefresh");
		$.sap_wd_secure_id = getAttributeValueByName(document, "sap-wd-secure-id");

		sessionDescriptor.sapSession = $;
	}

	private String generateWindowId() {
		return "WID" + new Date().getTime();
	}

	private String generateSessionId() throws UnknownErrorException {
		for (final Cookie c : cookieStore.getCookies())
			if ("JSESSIONID".equals(c.getName()))
				return c.getValue().substring(0, 40);

		throw new UnknownErrorException("SessionId not available");
	}

	private String getWindowId() {
		if (sessionDescriptor.windowId == null)
			sessionDescriptor.windowId = generateWindowId();

		return sessionDescriptor.windowId;
	}

	private String getSessionId() throws UnknownErrorException {
		if (sessionDescriptor.sessionId == null)
			sessionDescriptor.sessionId = generateSessionId();

		return sessionDescriptor.sessionId;
	}

	private SessionDescriptor.SAPSession getSAPSession() throws UnknownErrorException {
		if (sessionDescriptor.sapSession == null)
			throw new UnknownErrorException("SAP session not generated");

		return sessionDescriptor.sapSession;
	}

	private List<NameValuePair> generateDataFromHtml(final String html,
			final List<String> attributes) throws UnknownErrorException {
		final Document d = Jsoup.parse(html);

		final List<NameValuePair> $ = new ArrayList<NameValuePair>();
		for (final String attribute : attributes)
			$.add(new BasicNameValuePair(attribute, getAttributeValueByName(d, attribute)));

		return $;
	}

	public static boolean isLoginValid(final String username, final String password)
			throws ConnectionException, UnknownErrorException {
		if (username == null || password == null) {
			Log.d(LOG_TAG, "Received null pointer");
			throw new IllegalArgumentException("Received null pointer");
		}

		InvitationSender sender = null;
		try {
			sender = new InvitationSender(SITE_URL);
			sender.doLogin(username, password);
			return true;
		} catch (final LoginException e) {
			return false;
		} finally {
			if (sender != null)
				sender.close();
		}
	}

	public static void addInvitation(final String username, final String password,
			final Invitation invitation) throws LoginException, ConnectionException,
			InvitationFormatException, InvitationLimitExceededException, UnknownErrorException {
		if (username == null || password == null || invitation == null) {
			Log.d(LOG_TAG, "Received null pointer");
			throw new IllegalArgumentException("Received null pointer");
		}

		InvitationSender sender = null;
		try {
			sender = new InvitationSender(SITE_URL);
			sender.doAddInvitation(username, password, new InvitationParcel(invitation));
		} finally {
			if (sender != null)
				sender.close();
		}
	}

	private void doLogin(final String username, final String password) throws ConnectionException,
			LoginException, UnknownErrorException {
		try {
			if (!isLoginSuccessful(submitLoginForm(username, password)))
				throw new LoginException();

		} catch (final IOException e) {
			throw new ConnectionException(e);
		}
	}

	private void doAddInvitation(final String username, final String password,
			final InvitationParcel i) throws ConnectionException, LoginException,
			InvitationFormatException, InvitationLimitExceededException, UnknownErrorException {
		try {
			doLogin(username, password);
			navigateToMainScreen();
			generateSAPSessionDescriptor(enterInvitationsScreen(navigateToInvitationsScreen()));
			initNewInvitationScreen();
			checkInvitationStatus(sendInvitation(i));
		} catch (final IOException e) {
			throw new ConnectionException(e);
		}
	}

	private String submitLoginForm(final String username, final String password)
			throws IOException, UnknownErrorException {
		final HttpGet initGet = new HttpGet(baseUrl);

		final String salt = getAttributeValueByName(executeRequest(initGet), "j_salt");

		final HttpPost postLogin = new HttpPost(baseUrl + "/irj/portal");
		final List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("login_submit", "on"));
		data.add(new BasicNameValuePair("login_do_redirect", "1"));
		data.add(new BasicNameValuePair("j_salt", salt));
		data.add(new BasicNameValuePair("j_username", username));
		data.add(new BasicNameValuePair("j_password", password));
		data.add(new BasicNameValuePair("uidPasswordLogon", "Log On"));

		postLogin.setEntity(new UrlEncodedFormEntity(data, Consts.UTF_8));
		return executeRequest(postLogin);
	}

	private boolean isLoginSuccessful(final String welcomePageHtml) {
		return Jsoup.parse(welcomePageHtml).getElementById("logoff") != null;
	}

	private void navigateToMainScreen() throws IOException {
		final String url = String.format(baseUrl + "/irj/servlet/prt/portal/prteventname/Navigate/"
				+ "prtroot/pcd" + "!3aportal_content" + "!2fevery_user"
				+ "!2fgeneral!2fdefaultAjaxframeworkContent" + "!2fcom.sap.portal.contentarea"
				+ "?windowId=%s" + "&supportInitialNavNodesFilter=%s", getWindowId(), true);

		executeRequest(new HttpPost(url));
	}

	private String navigateToInvitationsScreen() throws IOException {
		final String url = String.format(baseUrl + "/irj/servlet/prt/portal/prteventname/Navigate/"
				+ "prtroot/pcd" + "!3aportal_content" + "!2fevery_user"
				+ "!2fgeneral!2fdefaultAjaxframeworkContent" + "!2fcom.sap.portal.contentarea"
				+ "?ExecuteLocally=%s" + "&DrillDownLevel=%s" + "&CurrentWindowId=%s"
				+ "&supportInitialNavNodesFilter=%s" + "&windowId=%s" + "&NavMode=%s"
				+ "&PrevNavTarget=%s", true, 1, getWindowId(), true, getWindowId(), 0,
				"navurl://c14f9327e067ce4d97724cf26e75849e");

		final HttpPost post = new HttpPost(url);
		final List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("NavigationTarget",
				"navurl://fb11b06a0b3f7d3b738151e75d7652c1"));
		data.add(new BasicNameValuePair("Command", "SUSPEND"));
		data.add(new BasicNameValuePair("SerPropString", ""));
		data.add(new BasicNameValuePair("SerKeyString", ""));
		data.add(new BasicNameValuePair("SerAttrKeyString", ""));
		data.add(new BasicNameValuePair("SerWinIdString", ""));
		data.add(new BasicNameValuePair("DebugSet", ""));
		data.add(new BasicNameValuePair("Embedded", Boolean.toString(true)));
		data.add(new BasicNameValuePair("SessionKeysAvailable", Boolean.toString(true)));

		post.setEntity(new UrlEncodedFormEntity(data, Consts.UTF_8));

		return executeRequest(post);
	}

	private String enterInvitationsScreen(final String navigationHtml) throws IOException,
			UnknownErrorException {
		final String url = String.format(baseUrl + "/webdynpro/resources/sap.com/pb/PageBuilder"
				+ ";jsessionid=%s", getSessionId());

		final HttpPost post = new HttpPost(url);
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.5");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Connection", "keep-alive");

		final List<String> attributes = new ArrayList<String>();
		attributes.add("sap-ext-sid");
		attributes.add("sap-wd-cltwndid");
		attributes.add("sap-wd-tstamp");
		attributes.add("PagePath");
		attributes.add("sap-wd-app-namespace");
		attributes.add("sap-ep-version");
		attributes.add("sap-locale");
		attributes.add("sap-accessibility");
		attributes.add("sap-rtl");
		attributes.add("sap-explanation");
		attributes.add("sap-cssurl");
		attributes.add("sap-epcm-guid");
		attributes.add("com.sap.portal.reserved.wd.pb.restart");
		attributes.add("DynamicParameter");
		attributes.add("supportInitialNavNodesFilter");
		attributes.add("SerWinIdString");
		attributes.add("NavigationTarget");
		attributes.add("NavMode");
		attributes.add("ExecuteLocally");
		attributes.add("DrillDownLevel");
		attributes.add("CurrentWindowId");
		attributes.add("PrevNavTarget");

		post.setEntity(new UrlEncodedFormEntity(generateDataFromHtml(navigationHtml, attributes),
				Consts.UTF_8));

		return executeRequest(post);
	}

	private String sendInvitation(final InvitationParcel i) throws IOException,
			UnknownErrorException {
		fillNameLicenseColorArrivalTime(i);
		fillArrivalDate(i);
		openCarTypeSelecionBox();
		fillCarType(i);
		submitCarType();
		return submitNewInvitationForm();
	}

	private String postSAPAjaxEvents(final String events) throws IOException, UnknownErrorException {
		final String url = baseUrl + "/webdynpro/resources/sap.com/pb/PageBuilder";

		final HttpPost post = new HttpPost(url);

		final List<NameValuePair> data = getSAPSession().toNameValuePairs();
		data.add(new BasicNameValuePair("SAPEVENTQUEUE", events));

		post.setEntity(new UrlEncodedFormEntity(data, Consts.UTF_8));
		return executeRequest(post);
	}

	private void initNewInvitationScreen() throws IOException, UnknownErrorException {
		final String events = "Button_Press\uE002Id\uE004aaaa.ShowCarNotifView.CreateButton\uE003\uE002"
				+ "ClientAction\uE004submit\uE003\uE002"
				+ "urEventName\uE004BUTTONCLICK\uE003\uE001"
				+ "Form_Request\uE002Id\uE004...form\uE005"
				+ "Async\uE004false\uE005"
				+ "FocusInfo\uE004@{\"sFocussedId\": \"aaaa.ShowCarNotifView.CreateButton\"}\uE005"
				+ "Hash\uE004\uE005DomChanged\uE004false\uE005IsDirty\uE004false\uE003\uE002"
				+ "EnqueueCardinality\uE004single\uE003\uE002\uE003";

		postSAPAjaxEvents(events);
	}

	private void fillNameLicenseColorArrivalTime(final InvitationParcel i) throws IOException,
			UnknownErrorException {
		final String events = String
				.format("InputField_Change\uE002Id\uE004"
						+ "aaaa.CreateCarNotifView.visitorName\uE005Value\uE004"
						+ "%s"
						+ "\uE003\uE002Delay\uE004full\uE003\uE002urEventName\uE004INPUTFIELDCHANGE\uE003\uE001"
						+ "InputField_Change\uE002Id\uE004"
						+ "aaaa.CreateCarNotifView.visitorLicenseNumber\uE005Value\uE004"
						+ "%s"
						+ "\uE003\uE002Delay\uE004full\uE003\uE002urEventName\uE004INPUTFIELDCHANGE\uE003\uE001"
						+ "InputField_Change\uE002Id\uE004"
						+ "aaaa.CreateCarNotifView.visitorCarColor\uE005Value\uE004"
						+ "%s"
						+ "\uE003\uE002Delay\uE004full\uE003\uE002urEventName\uE004INPUTFIELDCHANGE\uE003\uE001"
						+ "InputField_Validate\uE002Id\uE004"
						+ "aaaa.CreateCarNotifView.visitorArrivalTime\uE005Value\uE004"
						+ "%s"
						+ "\uE003\uE002ClientAction\uE004submitAsync\uE003\uE002urEventName\uE004"
						+ "Validate\uE003\uE001Form_Request\uE002Id\uE004...form\uE005"
						+ "Async\uE004true\uE005"
						+ "FocusInfo\uE004@{\"iCursorPosX\": 0, \"iSelectionStart\": -1, \"iSelectionEnd\": -1,"
						+ "\"sFocussedId\": \"aaaa.CreateCarNotifView.visitorArrivalDate\","
						+ "\"sApplyControlId\": \"aaaa.CreateCarNotifView.visitorArrivalDate\"}\uE005"
						+ "Hash\uE004\uE005DomChanged\uE004false\uE005IsDirty\uE004false\uE003\uE002"
						+ "EnqueueCardinality\uE004single\uE003\uE002\uE003", i.getContactName(),
						i.getCarNumber(), i.getCarColor(), i.getArrivalTime());

		postSAPAjaxEvents(events);
	}

	private void fillArrivalDate(final InvitationParcel i) throws IOException,
			UnknownErrorException {
		final String events = String
				.format("InputField_Validate\uE002Id\uE004"
						+ "aaaa.CreateCarNotifView.visitorArrivalDate\uE005Value\uE004"
						+ "%s"
						+ "\uE003\uE002ClientAction\uE004submitAsync\uE003\uE002urEventName\uE004"
						+ "Validate\uE003\uE001Form_Request\uE002Id\uE004...form\uE005"
						+ "Async\uE004true\uE005"
						+ "FocusInfo\uE004@{\"sFocussedId\": \"aaaa.CreateCarNotifView.visitorCarTypeKey-btn\"}\uE005"
						+ "Hash\uE004\uE005DomChanged\uE004false\uE005IsDirty\uE004false\uE003\uE002"
						+ "EnqueueCardinality\uE004single\uE003\uE002\uE003", i.getArrivalTime());

		postSAPAjaxEvents(events);
	}

	private void openCarTypeSelecionBox() throws IOException, UnknownErrorException {
		final String events = "ComboBox_FieldHelpPress\uE002Id\uE004"
				+ "aaaa.CreateCarNotifView.visitorCarTypeKey\uE003\uE002"
				+ "ClientAction\uE004submit\uE003\uE002urEventName\uE004"
				+ "FieldHelpPress\uE003\uE001Form_Request\uE002Id\uE004...form\uE005"
				+ "Async\uE004false\uE005"
				+ "FocusInfo\uE004@{\"sFocussedId\": \"aaaa.CreateCarNotifView.visitorCarTypeKey\"}\uE005"
				+ "Hash\uE004\uE005DomChanged\uE004false\uE005IsDirty\uE004false\uE003\uE002"
				+ "EnqueueCardinality\uE004single\uE003\uE002\uE003";

		postSAPAjaxEvents(events);
	}

	private void fillCarType(final InvitationParcel i) throws IOException, UnknownErrorException {
		final String events = String
				.format("InputField_Change\uE002Id\uE004"
						+ "aaaaKANF.EVSHandlerView.RootElement:2147483641\uE005Value\uE004"
						+ "%s"
						+ "\uE003\uE002Delay\uE004full\uE003\uE002urEventName\uE004INPUTFIELDCHANGE\uE003\uE001"
						+ "InputField_Enter\uE002Id\uE004"
						+ "aaaaKANF.EVSHandlerView.RootElement:2147483641\uE003\uE002"
						+ "ClientAction\uE004submit\uE003\uE002urEventName\uE004Enter\uE003\uE001"
						+ "Form_Request\uE002Id\uE004...form\uE005"
						+ "Async\uE004false\uE005"
						+ "FocusInfo\uE004@{\"iCursorPosX\": "
						+ "%s"
						+ ",\"iSelectionStart\": -1, \"iSelectionEnd\": -1, \"bNavigation\": true,"
						+ "\"sFocussedId\": \"aaaaKANF.EVSHandlerView.RootElement:2147483641\","
						+ "\"sApplyControlId\": \"aaaaKANF.EVSHandlerView.RootElement:2147483641\","
						+ "\"sPopupWindowId\": \"aaaaKANF.EVSHandlerWindow.Window\"}\uE005"
						+ "Hash\uE004\uE005DomChanged\uE004false\uE005IsDirty\uE004false\uE003\uE002"
						+ "EnqueueCardinality\uE004single\uE003\uE002\uE003",
						i.getCarManufacturer(), i.getCarManufacturer().length());

		postSAPAjaxEvents(events);
	}

	private void submitCarType() throws IOException, UnknownErrorException {
		final String events = "SapTable_RowSelect\uE002Id\uE004"
				+ "aaaaKANF.EVSHandlerView.RootElement\uE005" + "RowIndex\uE0042\uE005"
				+ "RowUserData\uE004model.values.1\uE005" + "CellUserData\uE004\uE005"
				+ "AccessType\uE004STANDARD\uE005" + "TriggerCellId\uE004\uE003\uE002"
				+ "ClientAction\uE004submit\uE003\uE002urEventName\uE004RowSelect\uE003\uE001"
				+ "Form_Request\uE002Id\uE004...form\uE005" + "Async\uE004false\uE005"
				+ "FocusInfo\uE004@{\"iRowIndex\": 3, \"iColIndex\": 1,"
				+ "\"sFocussedId\": \"aaaaKANF.EVSHandlerView.value_editor.1\","
				+ "\"sApplyControlId\": \"aaaaKANF.EVSHandlerView.RootElement\","
				+ "\"sPopupWindowId\": \"aaaaKANF.EVSHandlerWindow.Window\"}\uE005"
				+ "Hash\uE004\uE005DomChanged\uE004false\uE005IsDirty\uE004false\uE003\uE002"
				+ "EnqueueCardinality\uE004single\uE003\uE002\uE003";

		postSAPAjaxEvents(events);
	}

	private String submitNewInvitationForm() throws IOException, UnknownErrorException {
		final String events = "Button_Press\uE002Id\uE004"
				+ "aaaa.CreateCarNotifView.CreateButton\uE003\uE002"
				+ "ClientAction\uE004submit\uE003\uE002urEventName\uE004BUTTONCLICK\uE003\uE001"
				+ "Form_Request\uE002Id\uE004...form\uE005"
				+ "Async\uE004false\uE005"
				+ "FocusInfo\uE004@{\"sFocussedId\": \"aaaa.CreateCarNotifView.CreateButton\"}\uE005"
				+ "Hash\uE004\uE005DomChanged\uE004false\uE005IsDirty\uE004false\uE003\uE002"
				+ "EnqueueCardinality\uE004single\uE003\uE002\uE003";

		return postSAPAjaxEvents(events);
	}

	private void checkInvitationStatus(final String contentUpdatesXML)
			throws InvitationFormatException, InvitationLimitExceededException,
			UnknownErrorException {
		final String contentUpdatesHtml = Jsoup.parse(contentUpdatesXML, "", Parser.xmlParser())
				.getElementsByTag("content-update").text();

		final String maximumExceededError = "\u05D4\u05D2\u05E2\u05EA \u05DC\u05DB\u05DE\u05D5\u05EA "
				+ "\u05DE\u05E7\u05E1\u05D9\u05DE\u05DC\u05D9\u05EA \u05E9\u05DC "
				+ "\u05D4\u05D6\u05DE\u05E0\u05D5\u05EA \u05D0\u05D5\u05E8\u05D7\u05D9\u05DD, "
				+ "\u05E0\u05E1\u05D4 \u05DE\u05D0\u05D5\u05D7\u05E8 \u05D9\u05D5\u05EA\u05E8";

		final Document contentUpdates = Jsoup.parse(contentUpdatesHtml);
		final Elements errorElements = contentUpdates.getElementsByClass("urMsgBarError");
		final Elements confirmationsElements = contentUpdates.getElementsByClass("urMsgBarOk");

		// In case we have no errors, and only confirmations, we're done
		if (errorElements.isEmpty() && !confirmationsElements.isEmpty())
			return;

		// We didn't find any confirmation, yet no errors
		if (errorElements.isEmpty())
			throw new UnknownErrorException();

		// In case of multiple errors, we assume a malformed invitation
		if (errorElements.size() >= 2)
			throw new InvitationFormatException();

		// Check if exceeded maximum number of invitations
		final Element errorElement = errorElements.first();
		final String errorMessage = errorElement.getElementById(
				"aaaa.CreateCarNotifView.MessageArea1-txt").text();

		if (maximumExceededError.equals(errorMessage))
			throw new InvitationLimitExceededException();

		// We didn't exceed, but still got a single error
		throw new InvitationFormatException();
	}

	@Override
	public void close() {
		httpClient.getConnectionManager().shutdown();
	}
}