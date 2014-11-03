package com.trainingSpss;

import roboguice.activity.RoboFragmentActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.datalayer.DatabaseOperation;

public class SettingsSize extends RoboFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_size);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings_size, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	Context ctx = this;
	
	
	@Override
	 public void onResume() {
		super.onResume();
		
		RadioButton rad14 = (RadioButton) findViewById(R.id.rbtn14); 
		RadioButton rad16 = (RadioButton) findViewById(R.id.rbtn16); 
		RadioButton rad18 = (RadioButton) findViewById(R.id.rbtn18);
		
		DatabaseOperation DB = new DatabaseOperation(ctx);
		Cursor c = DB.getInfo(DB);
		c.moveToFirst();
		if(c.getInt(0)==14)
		{
			rad14.setChecked(true);
		}
		if(c.getInt(0)==16)
		{
			rad16.setChecked(true);
		}
		if(c.getInt(0)==18)
		{
			rad18.setChecked(true);
		}
	}
	
	public void savesize(View v)
    {	
		RadioButton rad14 = (RadioButton) findViewById(R.id.rbtn14); 
		RadioButton rad16 = (RadioButton) findViewById(R.id.rbtn16); 
		RadioButton rad18 = (RadioButton) findViewById(R.id.rbtn18); 
		DatabaseOperation DB = new DatabaseOperation(ctx);
    	if(rad14.isChecked())
    	{
    		DB.saveInfo(DB, 14);
    		Toast.makeText(this, "Font Size: 14", Toast.LENGTH_SHORT).show();
    	}
    	else if(rad16.isChecked())
    	{
    		DB.saveInfo(DB, 16);
    		Toast.makeText(this, "Font Size: 16", Toast.LENGTH_SHORT).show();
    	}
    	else if(rad18.isChecked())
    	{
    		DB.saveInfo(DB, 18);
    		Toast.makeText(this, "Font Size: 18", Toast.LENGTH_SHORT).show();
    	}
    	else
    	{
    	Toast.makeText(this, "Font size no change!", Toast.LENGTH_SHORT).show();
    	}
    	this.finish();
    }
}
