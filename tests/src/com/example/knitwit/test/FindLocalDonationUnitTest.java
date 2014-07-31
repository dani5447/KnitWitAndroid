/**
 * 
 */
package com.example.knitwit.test;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

import com.example.knitwit.FindLocalDonationActivity;
import com.example.knitwit.R;

/**
 * @author drn
 *
 */
@RunWith(RobolectricTestRunner.class) //Required to run test with Robolectric
public class FindLocalDonationUnitTest extends
ActivityUnitTestCase<FindLocalDonationActivity> {

	private FindLocalDonationActivity activity;
	private String urlPlusZipcodeRegex; 
	
	public FindLocalDonationUnitTest() {
		super(FindLocalDonationActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
	  super.setUp();
	  Intent intent = new Intent(getInstrumentation().getTargetContext(),
			  FindLocalDonationActivity.class);
	  startActivity(intent, null, null);
	  activity = getActivity();
	  
	  urlPlusZipcodeRegex="*.\\d{5}$";
	}
	
	@Override
	protected void tearDown() throws Exception{
		super.tearDown();
	}

	/*
	 * Unit test - Test the salvation army search button exists and onclick 
	 * opens up salvation army site with zipcode
	 */
	public void testFindSalvationArmyButton() {
	  int salvationArmyBtnId = R.id.findSalvationArmy;
	  assertNotNull(activity.findViewById(salvationArmyBtnId));
	  Button view = (Button) activity.findViewById(salvationArmyBtnId);
	  assertEquals("Incorrect label of the button", "Find Salvation Army", view.getText());
	  
	  view.performClick();
	  
	  // Check the intent which was started
	  Intent triggeredIntent = getStartedActivityIntent();
	  assertNotNull("Intent was null", triggeredIntent);
	  String data = triggeredIntent.getExtras().getString("URL");
	  assertTrue(data.matches(urlPlusZipcodeRegex));	  
	}
}
