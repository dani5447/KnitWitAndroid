package com.example.knitwit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FindLocalDonationActivity extends Activity{
	private int zipcode;
	private EditText zipcodeInput;
	private boolean isValidZipcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_donation);
		
		//Initialize isValidZipcode to false
		isValidZipcode = false;
		
		//Listen for changes in zipcode input field
		zipcodeInput = (EditText)findViewById(R.id.zipcodeInput);
		zipcodeInput.addTextChangedListener(zipCodeWatcher);
		
		//Listen for click on salvation army button
		Button salvationArmyButton = (Button)findViewById(R.id.findSalvationArmy);
		salvationArmyButton.setOnClickListener(salvationArmyClickListener);
	}
	
	public TextWatcher zipCodeWatcher = new TextWatcher(){
		public void afterTextChanged(Editable s){
			String tempZipcode = zipcodeInput.getText().toString();
			//For now, just test zipcode length for validity
			isValidZipcode = (tempZipcode.length() == 5);
			Log.i("zipCodeWatcher", "Zipcode length is " + tempZipcode.length());
		}
		public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		public void onTextChanged(CharSequence s, int start, int before, int count){}
	};
	
	public OnClickListener salvationArmyClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			Log.i("zipCodeWatcher", "Valid Zipcode is " + isValidZipcode);
			if(isValidZipcode){
				zipcode = Integer.parseInt(zipcodeInput.getText().toString());
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://satruck.org/search/results?q=" + zipcode));
				startActivity(browserIntent);
			} else{
				Toast.makeText(getApplicationContext(), "Zipcode is invalid. Please input valid zipcode.", 
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.row_counter, menu);
		return true;
	}
}
