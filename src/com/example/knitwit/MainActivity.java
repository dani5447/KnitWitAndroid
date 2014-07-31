package com.example.knitwit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder.DriveFolderResult;

public class MainActivity extends Activity {	
	
	private GoogleApiClient mGoogleApiClient;
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
	            .addConnectionCallbacks((ConnectionCallbacks) this)
	            .addOnConnectionFailedListener((OnConnectionFailedListener) this)
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

	/*
	 * Called if the mGoogleApiClient.connect() fails. (User has not previously
	 *  authorized permission)
	 */
	public void onConnectionFailed(ConnectionResult connectionResult) {
	    if (connectionResult.hasResolution()) {
	        try {
	            connectionResult.startResolutionForResult(this, RESOLVE_CONNECTION_REQUEST_CODE);
	        } catch (IntentSender.SendIntentException e) {
	            //TODO Unable to resolve, message user appropriately
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
	        	//TODO write error message
	        }
	    }
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		//Show/hide Log in/Log out based on current status
		View login = findViewById(R.id.action_log_in);
		View logout = findViewById(R.id.action_log_out);
		if(login.getVisibility()==View.VISIBLE){
			logout.setVisibility(View.GONE);
		} else{
			logout.setVisibility(View.VISIBLE);
			login.setVisibility(View.GONE);
		}
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
	        return true;
	    } else if (itemId ==R.id.action_change_background){
	        // Change Background option clicked. TODO
	        return true;
	    } else{
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	ResultCallback<DriveFolderResult> folderCreatedCallback = new
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
    };
	
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
