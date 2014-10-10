package webtasks;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import models.IBaseResult;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import android.util.Base64;

public class BaseTask<T,I>  {
	protected String url="http://nca.mooo.com:8080";
	protected String FILE_DIR="";
	protected String URL_POURCHES="";
	protected String URL_POURCHES_IMAGE="";
	protected String URL_POURCHES_PDF="";
	protected String ERROR_401="";
	protected String URL_CATEGORIES = url+ "/umbraco/api/spss/GetCategories";
	protected String URL_GETDOCUMENT =url+ "/umbraco/api/spss/GetDocument/";
	
	protected String userName="";
	protected String password="";
	protected IBaseResult<T> result;
	
	
	public BaseTask(String userName,String password,IBaseResult<T> result)
	{
		this.userName = userName;
		this.password = password;
		this.result = result;
	}
	protected HttpClient getDefaultHttpsClient()
	{
		HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

		DefaultHttpClient client = new DefaultHttpClient();

		SchemeRegistry registry = new SchemeRegistry();
		SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
		socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
		registry.register( new Scheme("http",socketFactory,80));
		SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
		DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

		// Set verifier     
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
		return httpClient;
	}
	protected HttpClient getDefaultHttpClient()
	{
		return new DefaultHttpClient();
	}
	
	protected HttpGet DefaultHttpGet(String refurl)
	{
		HttpGet httpGet = new HttpGet(refurl);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("content-type", "application/json");
		String base64EncodedCredentials = "Basic " + Base64.encodeToString((userName + ":" + password).getBytes(),Base64.NO_WRAP);
		httpGet.setHeader("Authorization", base64EncodedCredentials);
		return httpGet;
	}
	protected HttpPost DefaultHttpPost(String refurl)
	{
		HttpPost httpPost = new HttpPost(refurl);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("content-type", "application/json");
		String base64EncodedCredentials = "Basic " + Base64.encodeToString((userName + ":" + password).getBytes(),Base64.NO_WRAP);
		httpPost.setHeader("Authorization", base64EncodedCredentials);
		return httpPost;
	}
	public void exec(I para)
	 {

	 }
	public void exec()
	 {

	 }
 
}
