package webtasks;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.IBaseResult;
import models.NullModel;
import models.Pouche;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;


public class HeaderTask extends BaseTask<List<Pouche>,NullModel> {
	private final String POUCHE_NAME="pouche";
	private final String POUCHE_DATE="toedien_datum";
	private final String POUCHE_TIME="toedien_tijd";
	private final String POUCHE_MEDICINE_NUMBER="zi_nr";
	private final String POUCHE_MEDICINE_AMOUNT="aantal";
	private final String POUCHE_MEDICINE_ART="artnr";
	private final String POUCHE_MEDICINE_NAME="omschr";
	private final String POUCHE_MEDICHINE_DESCRIPTION="beschr";
	private final String POUCHE_PDF="bijsluiter";
	private final String POUCHE_IMAGE="foto";
	
	public HeaderTask(String userName,String password,IBaseResult<List<Pouche>> result)
	{
		super(userName, password,result);
	}
	
	public void exec()
	{
		HttpClient httpClient = getDefaultHttpClient();
		HttpPost httpPost = DefaultHttpPost(URL_POURCHES);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				List<Pouche> resultlist=  new ArrayList<Pouche>();
				String stringRsp = EntityUtils.toString(response.getEntity());
			
			 	JSONArray respJSONArray = new JSONArray(stringRsp);
	
			 	for (int i = 0; i < respJSONArray.length(); ++i) {
			 	    JSONObject obj = respJSONArray.getJSONObject(i);
			 	    Pouche pouche= new Pouche();
			 	    
			 	    pouche.PoucheName = obj.getString(POUCHE_NAME);
			 	    
			 	    String date=obj.getString(POUCHE_DATE);
			 	    String time=obj.getString(POUCHE_TIME);
			 	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			 	    
			 	   
			 	    
			 	    Date parcedate=df.parse(date+" "+time);
			 	    pouche.IntakeDateTime=parcedate.getTime();
			 	   
			 	   try { 
			 		   pouche.MedicineNumber=obj.getInt(POUCHE_MEDICINE_NUMBER);
			 		  } catch (Exception e) {
		
			 		  } 
			 	  try { 
			 		  pouche.MedicineAmount=obj.getInt(POUCHE_MEDICINE_AMOUNT);
			 		  } catch (Exception e) {
		
			 		  } 
			 	 try { 
			 		  pouche.MedicineArt=obj.getInt(POUCHE_MEDICINE_ART);
			 		  } catch (Exception e) {
		
			 		  } 
			 	try { 
			 		 pouche.MedicineName=obj.getString(POUCHE_MEDICINE_NAME);
			 		  } catch (Exception e) {
		
			 		  } 
			 	try { 
			 		 pouche.MedicineDescription=obj.getString(POUCHE_MEDICHINE_DESCRIPTION);
			 		  } catch (Exception e) {
		
			 		  } 
			 	try { 
			 		 pouche.HavingPDF=obj.getBoolean(POUCHE_PDF);
			 		  } catch (Exception e) {
		
			 		  } 
			 	try { 
			 		 pouche.HavingPhoto=obj.getBoolean(POUCHE_IMAGE);
			 		  } catch (Exception e) {
		
			 		  } 
			 	  
			 	    resultlist.add(pouche);
			 	}
				this.result.onSuccess(resultlist);
				return;
			}
			else
				{
					this.result.onError(ERROR_401);
					return;
				}
			} catch (Exception e) {
				Log.d("iii","iii"+e.getMessage());
				this.result.onError(ERROR_401);
				return;
			}
		
	}
}
