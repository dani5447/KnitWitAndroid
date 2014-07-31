/**
 * 
 */
package com.example.knitwit.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.example.knitwit.R;
import com.example.knitwit.RowCounter;

/**
 * @author drn
 *
 */
public class RowCounterUnitTest extends ActivityInstrumentationTestCase2<RowCounter> {
	private RowCounter activity;
	
	public RowCounterUnitTest() {
		super(RowCounter.class);
	}

	@Override
	protected void setUp() throws Exception {
	  super.setUp();
/*	  Intent intent = new Intent(getInstrumentation().getTargetContext(),
			  RowCounter.class);
	  startActivity(intent, null, null);*/
	  activity = getActivity();
	}
	
	@Override
	protected void tearDown() throws Exception{
		super.tearDown();
	}

	/*
	 * Unit test - Test the increment button exists and onclick 
	 * increments the counter
	 */
	public void testIncrementBtn() {
	  int incrementBtnId = R.id.increment;
	  assertNotNull(activity.findViewById(incrementBtnId));
	  Button view = (Button) activity.findViewById(incrementBtnId);
	  assertEquals("Incorrect label of the button", "+1", view.getText());
	  
	  //Get row count before increment button click
	  TextView countText = (TextView) activity.findViewById(R.id.counter_number);
	  int previousCount = Integer.parseInt(countText.getText().toString());
	  
	  view.performClick();
	  
	  // Check the row count is incremented
	  int afterCount = Integer.parseInt(countText.getText().toString());
	  
	  assertEquals("Row count not properly incremented", previousCount+1, afterCount);
	}
}
