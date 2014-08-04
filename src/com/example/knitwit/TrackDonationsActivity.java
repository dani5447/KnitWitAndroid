package com.example.knitwit;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.knitwit.MainActivity;

public class TrackDonationsActivity extends Activity {
	
	private static final int RESOLVE_CONNECTION_REQUEST_CODE = 0;
	private GoogleApiClient mGoogleApiClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_track_donations);
		
		mGoogleApiClient = MainActivity.getGoogleApiClient();
		
		//Listen for click on church search button
		Button emailRecordsBtn = (Button)findViewById(R.id.emailRecords);
		emailRecordsBtn.setOnClickListener(emailRecordsOnClickListener);
	}
	
	/**
	 * A listener for the button to email records.
	 * 
	 * @category listener
	 */
	public OnClickListener emailRecordsOnClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			mGoogleApiClient.connect();
		}
	};
	
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		// Connected to Google Play services!
		Toast.makeText(this, 
	              "Successfully connected to Google account", Toast.LENGTH_SHORT).show(); //TODO delete later?
		
		//Get email account name, and prepare email
		if(mGoogleApiClient.isConnected()){
	        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
	         
	        String[] TO = {email};
	        Intent emailIntent = new Intent(Intent.ACTION_SEND);
	        emailIntent.setData(Uri.parse("mailto:"));
	        emailIntent.setType("text/plain");
	        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KnitWit Donations History");
	        emailIntent.putExtra(Intent.EXTRA_TEXT, "Attached is your donations history.\n\n" +
	        		"Thank you for using KnitWit!");
	        //TODO attach donations info

	        try {
	           startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	           finish();
	           Log.i("Finished sending email...", "");
	        } catch (android.content.ActivityNotFoundException ex) {
	        	Log.wtf("TrackDonationsActivity.emailRecordsOnClickListener", ex);
	            Toast.makeText(TrackDonationsActivity.this, 
	            	"There is no email client installed.", Toast.LENGTH_SHORT).show();
	        }
		} else{
			Log.e("TrackDonationsActivity.emailRecordsOnClickListener", "Cannot send email, google account not connected.");
            Toast.makeText(TrackDonationsActivity.this, 
            	"Cannot send email, google account not connected.", Toast.LENGTH_SHORT).show();
		}
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
	        	Toast.makeText(this, 
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
	        	Toast.makeText(this, 
	  	              "Could not connect to Google account.", Toast.LENGTH_SHORT).show();
	        }
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.track_donations, menu);
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
}
