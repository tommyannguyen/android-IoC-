package webtasks;

import java.text.SimpleDateFormat;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;
import models.*;

public class DocumentTask extends BaseTask<Document,NullModel> {
	private final String DOCUMENT_CONTENT="Content";
	private final String DOCUMENT_TITILE="Title";
	private final String DOCUMENT_YOUTUBEURL="YouTubeUrl";
	private final String DOCUMENT_UPDATEDDATE="UpdatedDate";
	private final int documentId;
	public DocumentTask(String userName,String password,int documentId,IBaseResult<Document> result) {
		super(userName, password,result);
		this.documentId = documentId;
	}

	public void exec()
	{
		HttpClient httpClient = getDefaultHttpClient();
		HttpGet httpGet = DefaultHttpGet(URL_GETDOCUMENT+Integer.toString(this.documentId));
		try {
			HttpResponse response = httpClient.execute(httpGet);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				String documentContent =  "";
				String stringRsp = EntityUtils.toString(response.getEntity());
				JSONObject obj = new JSONObject(stringRsp);

				Document doc = new Document();
				doc.Id = documentId;
				doc.Content = obj.getString(DOCUMENT_CONTENT);
				doc.Title = obj.getString(DOCUMENT_TITILE);
				doc.YouTubeUrl = obj.getString(DOCUMENT_YOUTUBEURL);
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
				String updateDate = obj.getString(DOCUMENT_UPDATEDDATE);
				doc.UpdatedDate = df.parse(updateDate);
			 	this.result.onSuccess(doc);
			}
			else
			{
				this.result.onError(ERROR_401);
			}
		}
		catch (Exception e){
			Log.d("iii","iiiError"+e.getMessage());
		}
	}
}
