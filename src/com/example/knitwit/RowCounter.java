package com.example.knitwit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RowCounter extends Activity {
	private int currentCounter; //Contains value of current row number in counters
	private TextView rowCountText; //Holds the row count on UI
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_row_counter);
		
		rowCountText = (TextView) findViewById(R.id.counter_number);
		
		if(rowCountText.getText()==null){
			currentCounter = 0;
			rowCountText.setText("" + currentCounter);
		}
			
	    //Register event listener for incrementing row number
		TextView incrementButton = (TextView)findViewById(R.id.increment);
		incrementButton.setOnClickListener(incrementButtonListener);
		
		//Register event listener for decrementing row number
		TextView decrementButton = (TextView)findViewById(R.id.decrement);
		decrementButton.setOnClickListener(decrementButtonListener);
		
		//Register event listener for zeroing row number
		TextView zeroButton = (TextView)findViewById(R.id.zero);
		zeroButton.setOnClickListener(zeroButtonListener);		
    }
	
	public OnClickListener incrementButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			currentCounter++;
			rowCountText.setText("" + currentCounter);
		}
	};
	
	public OnClickListener decrementButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			//Do not decrement counter below 0
			if(currentCounter-1 <0){
				currentCounter = 0;
			}else{
				currentCounter--;
			}
			rowCountText.setText("" + currentCounter);
		}
	};
	
	public OnClickListener zeroButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			currentCounter = 0;
			rowCountText.setText("" + currentCounter);
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
