package com.trainingSpss;

import interfaces.IDocumentService;

import javax.inject.Inject;

import models.Document;
import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

public class DocumentActivity extends  YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener  {
	@Inject
	private IDocumentService _documentService;
	WebView webview;
	YouTubePlayerView youTubeView;
	private  YouTubePlayer _player;
	ScrollView scroll_view;
	static private String VIDEO = "";
	public static final String HOME_DOCUMENT_TOKEN = "DOCUMENTTOKEN";
	private Document document ;
	private int documentId;

	 @Override
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_document);
	        RoboGuice.getInjector(getApplicationContext()).injectMembers(this);
	        
	        webview = (WebView)findViewById(R.id.wvDocument);
	        youTubeView=(YouTubePlayerView)findViewById(R.id.youtube_view);
	        scroll_view=(ScrollView)findViewById(R.id.document_scroll);
	        
	        documentId =  this.getIntent().getExtras().getInt(HOME_DOCUMENT_TOKEN);
	        document = _documentService.GetDocument(documentId);
	        if(document!=null){
	        getActionBar().setTitle(document.Title);
	        String divYoutube="";
	        if(_documentService.IsNetworkAvaiable() && document.YouTubeUrl!=null && !document.YouTubeUrl.isEmpty())
	        {
	        	VIDEO = GetYoutube(document.YouTubeUrl);
	        	youTubeView.initialize("AIzaSyD9lV0bOzwq65YG7hCwFQqUXW-ml9CjaT0", this);
	        }
	        else
	        {
	        	youTubeView.setVisibility(View.GONE);
	        }
	        String summary = "<html><body>"+document.Content+"</body></html>";
	        webview.loadDataWithBaseURL(null, summary, "text/html", "UTF-8", null);
	        WebSettings settings = webview.getSettings();
	        settings.setDefaultTextEncodingName("utf-8");
	        settings.setJavaScriptEnabled(true);
	        settings.setPluginState(PluginState.ON);
	        settings.setPluginsEnabled(true);
	        settings.setJavaScriptCanOpenWindowsAutomatically(true);
	        webview.setWebChromeClient(new WebChromeClient() {

	            @Override
	            public void onShowCustomView(View view, CustomViewCallback callback) {
	                super.onShowCustomView(view, callback);
	                if (view instanceof FrameLayout) {
	                    FrameLayout frame = (FrameLayout) view;
	                    if (frame.getFocusedChild() instanceof VideoView) {
	                        VideoView video = (VideoView) frame.getFocusedChild();
	                        frame.removeView(video);
	                        video.start();
	                    }
	                }

	            }
	        });
	        }
	        
	 }
	 @Override
     public boolean onKeyDown(int keyCode, KeyEvent event)  {
         if (keyCode == KeyEvent.KEYCODE_BACK){
        	 Intent intent = new Intent(this, MainActivity.class);
        	 if(document!= null)
        	 {
        		 intent.putExtra(DocumentActivity.HOME_DOCUMENT_TOKEN,document.CategoryId );
        	 }
        	 webview.destroy();
             startActivity(intent);
           finish();
         }

         return super.onKeyDown(keyCode, event);
     }
	 private String GetYoutube(String url)
	 {
		 return url.substring(url.indexOf("v=") + 2);
	 }
	@Override
	public void onInitializationFailure(Provider arg0,
			YouTubeInitializationResult arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player,
			boolean arg2) {
		_player = player;
		player.loadVideo(VIDEO);
	}
	 
}
