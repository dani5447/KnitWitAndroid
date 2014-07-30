package com.example.knitwit;

import com.example.knitwit.R;

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
	
	//static search base URLs
	public static String baseSalvationArmySearchURL = "http://satruck.org/search/results?q=";
	public static String baseChruchSearchURL = "http://www.whitepages.com/business?utf8=%E2%9C%93&key=Churches&where=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_local_donation);
		
		//Initialize isValidZipcode to false
		isValidZipcode = false;
		
		//Listen for changes in zipcode input field
		zipcodeInput = (EditText)findViewById(R.id.zipcodeInput);
		zipcodeInput.addTextChangedListener(zipCodeWatcher);
		
		//Listen for click on salvation army search button
		Button salvationArmyButton = (Button)findViewById(R.id.findSalvationArmy);
		salvationArmyButton.setOnClickListener(salvationArmyClickListener);
		
		//Listen for click on church search button
		Button churchButton = (Button)findViewById(R.id.findLocalChurches);
		churchButton.setOnClickListener(churchSearchClickListener);
	}
	
	/**
	 * A listener for the zipcode entry field. After text change, does simple validation on
	 * zipcode and saves result to private variable for later.
	 * 
	 * @category listener
	 */
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
	
	/**
	 * A listener for the button to search for local salvation army. Uses a base URL plus user entered
	 * and validated zipcode to search for churches. Shows a toast if zipcode is invalid.
	 * 
	 * @category listener
	 */
	public OnClickListener salvationArmyClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			if(isValidZipcode){
				zipcode = Integer.parseInt(zipcodeInput.getText().toString());
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseSalvationArmySearchURL + zipcode));
				startActivity(browserIntent);
			} else{
				Toast.makeText(getApplicationContext(), "Zipcode is invalid. Please input valid zipcode.", 
						Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	/**
	 * A listener for the button to search for local churches. Uses a base URL plus user entered
	 * and validated zipcode to search for churches. Shows a toast if zipcode is invalid.
	 * 
	 * @category listener
	 */
	public OnClickListener churchSearchClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			if(isValidZipcode){
				zipcode = Integer.parseInt(zipcodeInput.getText().toString());
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseChruchSearchURL + zipcode));
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
