package com.example.knitwit;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import android.app.Activity;
import android.content.Intent;
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

	        //Get email account name, and prepare email
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
	        	Log.wtf("MainActivity.onActivityResult", ex);
	            Toast.makeText(TrackDonationsActivity.this, 
	            	"There is no email client installed.", Toast.LENGTH_SHORT).show();
	        }
		}
	};

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
