package webtasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import models.*;


public class CategoryTask  extends BaseTask<List<Category>,NullModel>  {
	private final String CATEGORY_ID="Id";
	private final String CATEGORY_DOCUMENTS="Documents";
	private final String CATEGORY_NAME="Name";
	private final String CATEGORY_UPDATEDDATE="UpdatedDate";
	private final String CATEGORY_TITLE="Title";
	public CategoryTask(String userName,String password,IBaseResult<List<Category>> result) {
		super(userName, password,result);
	}
	public void exec()
	{
		HttpClient httpClient = getDefaultHttpClient();
		HttpGet httpGet = DefaultHttpGet(URL_CATEGORIES);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				List<Category> resultlist=  new ArrayList<Category>();
				String stringRsp = EntityUtils.toString(response.getEntity());
				JSONArray respJSONArray = new JSONArray(stringRsp);
				
			 	for (int i = 0; i < respJSONArray.length(); ++i) {
			 	   JSONObject obj = respJSONArray.getJSONObject(i);
			 	   Category category= new Category();
			 	   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
			 	   
			 	   category.Id = obj.getInt(CATEGORY_ID);
			 	   category.Name = obj.getString(CATEGORY_NAME);
			 	   String updateDate = obj.getString(CATEGORY_UPDATEDDATE);
			 	   category.UpdatedDate = df.parse(updateDate);
			 	   
			 	 JSONArray documentsArray = new JSONArray(obj.getString(CATEGORY_DOCUMENTS));
			 	 for(int j =0 ; j < documentsArray.length(); j++)
			 	 {
			 	     JSONObject docObj = (JSONObject)documentsArray.get(j);
			 	     Document doc = new Document();
			 	     doc.Id = docObj.getInt(CATEGORY_ID);
			 	     doc.CategoryId = category.Id;
			 	     doc.Title = docObj.getString(CATEGORY_TITLE);
			 	     updateDate = docObj.getString(CATEGORY_UPDATEDDATE);
			 	     doc.UpdatedDate = df.parse(updateDate);
			 	     category.Documents.add(doc);
			 	 } 
			 	   
			 	   resultlist.add(category);
			 	}
			 	this.result.onSuccess(resultlist);
			}
			else
			{
				this.result.onError(ERROR_401);
			}
		}
		catch (Exception e){
			this.result.onError(ERROR_401);
		}
	}
}
