package webtasks;

import java.io.*;
import java.net.URL;
import java.util.List;

import models.IBaseResult;
import models.NullModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.*;
import android.os.Environment;

public class ImageTask extends BaseTask<Bitmap,NullModel>{
	
	private final Context context;
	private final String mypath;
	private final String fileId;
	private final String filename;
	private final IBaseResult<Bitmap> result;
	
	public ImageTask(String userName,String password,Context context,String fileId,IBaseResult<Bitmap> result)
	{
		super(userName, password,result);
		
		this.context=context;
		File path=Environment.getExternalStorageDirectory() ;
		
		this.mypath=path.toString() + FILE_DIR;
		path=new File(this.mypath);
		if (!path.exists()) {
	        path.mkdir();
	    }
		this.fileId=fileId;
		this.filename=fileId+".png";
		this.result=result;
	}
	public void exec()
	{
		if(!checkFileExsist())
		{
			downloadBitmap();
		}
		
	}
   private void downloadBitmap() 
   {
	        HttpClient httpClient = getDefaultHttpClient();
		    HttpGet getRequest =DefaultHttpGet(URL_POURCHES_IMAGE+filename);
		    try { 
		        HttpResponse response = httpClient.execute(getRequest);
		        final int statusCode = response.getStatusLine().getStatusCode();
		        if (statusCode == HttpStatus.SC_OK) { 
		        
		        final HttpEntity entity = response.getEntity();
		        if (entity != null) {
		            InputStream inputStream = null;
		            try {
		                inputStream = entity.getContent(); 
		                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		                saveBitmap(bitmap);
		                result.onSuccess(bitmap);
		                return;
		            } finally {
		                if (inputStream != null) {
		                    inputStream.close();  
		                }
		                entity.consumeContent();
		            }
		        }
		        }
		    } catch (Exception e) {
		        getRequest.abort();
		    }
		    
		    this.result.onError(ERROR_401);
		    
	}
	private void saveBitmap(Bitmap bmp)
	{
		File file = new File(mypath, filename);
		try {
				FileOutputStream fOut = new FileOutputStream(file);
				bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
			
				e.printStackTrace();
		}   
	}
	private Boolean checkFileExsist()
	{
		String photoPath=this.mypath+"/"+filename;
		File file = new File(photoPath);
		if(file.exists()) 
		{
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
			result.onSuccess(bitmap);
		    return true;
		}
		return false;             
	}
}
