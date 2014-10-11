package com.trainingSpss;

import interfaces.IDocumentService;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SplashScreenActivity extends RoboActivity {
	 private ImageView splashImageView;
	 @Inject
	 private IDocumentService _documentService;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        final Context context= this.getApplicationContext();
	        splashImageView = new ImageView(this);
	        splashImageView.setScaleType(ScaleType.FIT_XY);
	        splashImageView.setImageResource(R.drawable.splashscreen);
	        setContentView(splashImageView);
	        Handler h = new Handler();
	        h.postDelayed(new Runnable() {
	            public void run() {
	            	Intent i = new Intent(context,MainActivity.class);
	            	i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); 
	            	_documentService.SetLoadOnWeb(true);
	            	startActivity(i);
	            	finish();
	            }
	        }, 3000);
	    }

}
