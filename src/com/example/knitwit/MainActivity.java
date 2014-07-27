package com.example.knitwit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_about:
	        // About option clicked. Show about in alert dialog.
	    	//TODO - If there's more app info later, convert to new page
	    	AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
	    	alertDialog.setTitle("About");
	    	alertDialog
				.setMessage("App Version: 1.0\nAuthor: Danielle Neuberger (insert contact email here)" +
						"\nRecent Version Updates: None"); //TODO - add contact email
			alertDialog.show();
	        return true;
	    case R.id.action_exit:
	        // Exit option clicked.
	    	Log.i("MainActivity.onOptionsItemSelected", "Exit clicked");
	    	Process.killProcess(Process.myPid()); 
	        return true;
	    case R.id.action_settings:
	        // Settings option clicked. TODO
	        return true;
	    case R.id.action_change_background:
	        // Change Background option clicked. TODO
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	public void countRows(View view){
		Intent intent = new Intent(this, RowCounter.class);
	    startActivity(intent);
	}

	public void findDonationCenters(View view){
		Intent intent = new Intent(this, FindLocalDonationActivity.class);
	    startActivity(intent);
	}
	
	public void stitchDictionary(View view){
		Intent intent = new Intent(this, StitchDictionaryActivity.class);
		startActivity(intent);
	}
}
