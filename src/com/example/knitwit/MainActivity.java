package com.example.knitwit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.plus.Plus;

public class MainActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {	
	
	public static GoogleApiClient mGoogleApiClient;
	private int RESOLVE_CONNECTION_REQUEST_CODE;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Get Google Drive info
		super.onCreate(savedInstanceState);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
	            .addApi(Drive.API)
	            .addScope(Drive.SCOPE_FILE)
	            .addConnectionCallbacks(this)
	            .addOnConnectionFailedListener(this)
	            .build();
		//Attempt to connect to google client
		onStart();
	}
	
	/*
	 * Attempt google client connection
	 */
	@Override
	protected void onStart() {
	    super.onStart();
	    mGoogleApiClient.connect();
	}

	@Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
	
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		// Connected to Google Play services!
        // The good stuff goes here.
		Toast.makeText(MainActivity.this, 
	              "Successfully connected to Google account", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		// The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.

	}
	
	/*
	 * Called if the mGoogleApiClient.connect() fails. (User has not previously
	 *  authorized permission)
	 */
	public void onConnectionFailed(ConnectionResult connectionResult) {
	    if (connectionResult.hasResolution()) {
	        try {
	            connectionResult.startResolutionForResult(this, RESOLVE_CONNECTION_REQUEST_CODE);
	        } catch (IntentSender.SendIntentException e) {
	            // Unable to resolve, message user appropriately
	        	Log.wtf("MainActivity.onActivityResult", "Could not connect to Google account." + e);
	        	Toast.makeText(MainActivity.this, 
	  	              "Could not connect to Google account.", Toast.LENGTH_SHORT).show();
	        }
	    } else {
	        GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
	    }
	}

	/*
	 * Called on google connection completion. Must re-try connection.
	 */
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		if(requestCode == RESOLVE_CONNECTION_REQUEST_CODE){
	        if (resultCode == RESULT_OK) {
	           mGoogleApiClient.connect();
	        } else{
	        	Log.e("MainActivity.onActivityResult", "Could not connect to Google account.");
	        	Toast.makeText(MainActivity.this, 
	  	              "Could not connect to Google account.", Toast.LENGTH_SHORT).show();
	        }
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    int itemId = item.getItemId();
	    if(itemId ==  R.id.action_about){
	        // About option clicked. Show about in alert dialog.
	    	//TODO - If there's more app info later, convert to new page
	    	AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
	    	alertDialog.setTitle("About");
	    	alertDialog
				.setMessage("App Version: 1.0\nAuthor: Danielle Neuberger (insert contact email here)" +
						"\nRecent Version Updates: None"); //TODO - add contact email
			alertDialog.show();
	        return true;
	    } else if (itemId ==R.id.action_exit){
	        // Exit option clicked.
	    	Log.i("MainActivity.onOptionsItemSelected", "Exit clicked");
	    	Process.killProcess(Process.myPid()); 
	        return true;
	    } else if (itemId == R.id.action_settings){
	        // Settings option clicked. TODO
	    	Toast.makeText(MainActivity.this, 
	  	              "clicked settings", Toast.LENGTH_SHORT).show();
	        return true;
	    } else if (itemId ==R.id.action_change_background){
	        // Change Background option clicked. TODO
	    	Toast.makeText(MainActivity.this, 
  	              "clicked background", Toast.LENGTH_SHORT).show();
	        return true;
	    } else{
	        return super.onOptionsItemSelected(item);
	    }
	}
	
/*	ResultCallback<DriveFolderResult> folderCreatedCallback = new
            ResultCallback<DriveFolderResult>() {
        @Override
        public void onResult(DriveFolderResult result) {
            if (!result.getStatus().isSuccess()) {
                Log.wtf("MainActivity.folderCreatedCallback", "Error while trying to create Google Drive folder.");
                return;
            }
            Log.i("MainActivity.folderCreatedCallback", "Successfully created folder " + 
            		result.getDriveFolder().getDriveId() + " in Google Drive account");
        }
    };*/
	
	public static GoogleApiClient getGoogleApiClient(){
		return mGoogleApiClient;
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
	
	public void trackDonations(View view){
		Intent intent = new Intent(this, TrackDonationsActivity.class);
		startActivity(intent);
	}
}
