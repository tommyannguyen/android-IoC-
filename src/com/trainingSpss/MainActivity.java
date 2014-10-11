package com.trainingSpss;
import interfaces.IDocumentService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.Category;
import models.IBaseResult;
import roboguice.activity.RoboActivity;
import roboguice.activity.RoboFragmentActivity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class MainActivity extends RoboFragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	@Inject
	private IDocumentService _documentService;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private Bundle _savedInstanceState = null;
	private int categoryId = 0;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pDialog = ProgressDialog.show(this, "", "Đang tải dữ liệu", true,false);
		_savedInstanceState = savedInstanceState;
		Bundle ex=this.getIntent().getExtras();
		if(ex != null)
		{
			categoryId=  ex.getInt(DocumentActivity.HOME_DOCUMENT_TOKEN);
		}
		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		
		new Connection().execute();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		_documentService.SetLoadOnWeb(false);
	}
	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			NavDrawerListAdapter currentAdapter =(NavDrawerListAdapter)parent.getAdapter();
			NavDrawerItem item =(NavDrawerItem) currentAdapter.getItem(position);
			displayView(position,item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	private void displayView(int position,NavDrawerItem item) {
		// update the main content by replacing fragments
		android.support.v4.app.Fragment fragment = null;
		
		if(item == null)
		{
			switch (position) {
			case 0:
				fragment = new CategoryFragment(CategoryFragment.HOME_CATEGORY_ID);
				break;
			default:
				break;
			}
		}
		else
		{
			fragment = new CategoryFragment(item.getId());
		}

		if (fragment != null) {
			android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).addToBackStack(null).commit();

			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			if(navMenuTitles.length>position)
			{
				setTitle(navMenuTitles[position]);
			}
			else
			{
				setTitle("");
			}
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	 private class Connection extends AsyncTask {
		 
	        @Override
	        protected Object doInBackground(Object... arg0) {
	        	_documentService.GetCategories(new IBaseResult<List<Category>>() {
	    			
	    			@Override
	    			public void onSuccess(List<Category> result) {
	    				final ArrayList<String> titles = new ArrayList<String>();
	    				titles.add("Home");
	    				navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
	    				navDrawerItems = new ArrayList<NavDrawerItem>();
	    				navDrawerItems.add(new NavDrawerItem(CategoryFragment.HOME_CATEGORY_ID,"Home",navMenuIcons.getResourceId(0, -1)));
	    				for(Category c : result)
	    				{
	    				  titles.add(c.Name);
	    				  navDrawerItems.add(new NavDrawerItem(c.Id,c.Name,navMenuIcons.getResourceId(4, -1), true,Integer.toString(c.CountDocuments)));
	    				}
	    				final String[] titlesArray = new String[titles.size()];
	    				
	    				runOnUiThread(new Runnable() {
	    				     @Override
	    				     public void run() {
	    				    	 navMenuTitles = (String[]) titles.toArray(titlesArray);
	    		    				navMenuIcons.recycle();
	    		    				
	    		    				adapter = new NavDrawerListAdapter(getApplicationContext(),
	    		    						navDrawerItems);
	    		    				
	    		    				mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	    		    	        	mDrawerList.setAdapter(adapter);
	    		    	        	if (_savedInstanceState == null && categoryId ==0) {
	    		    	    			displayView(0,null);
	    		    	    		}
	    		    	        	else if(categoryId >0)
	    		    	        	{
	    		    	        		int position =0;
	    		    	        		for(NavDrawerItem navDrawerItem : navDrawerItems)
	    		    	        		{
	    		    	        			if(navDrawerItem.getId()==categoryId)
	    		    	        			{
	    		    	        				displayView(position,navDrawerItem);
	    		    	        			}
	    		    	        			position++;
	    		    	        		}
	    		    	        	}
	    		    	        	
	    		    	        	 pDialog.dismiss();
	    				    }
	    				});
	    				
	    			}
	    			
	    			@Override
	    			public void onError(String error) {
	    				
	    			}
	    		});
	    		
	            return null;
	        }
	 
	       
	        @Override
	        protected void onProgressUpdate(Object... values) {
	        	
	        }
	    }

}


