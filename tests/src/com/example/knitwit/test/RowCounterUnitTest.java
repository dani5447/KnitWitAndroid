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
@RunWith(RobolectricTestRunner.class) //Required to run test with Robolectric
public class RowCounterUnitTest extends ActivityInstrumentationTestCase2<RowCounter> {
	private RowCounter activity;
	
	public RowCounterUnitTest() {
		super(RowCounter.class);
	}

	@Before
	protected void setUp() throws Exception {
	  super.setUp();
/*	  Intent intent = new Intent(getInstrumentation().getTargetContext(),
			  RowCounter.class);
	  startActivity(intent, null, null);*/
	  activity = getActivity();
	  
	  /* Example robolectric code
	   * this.activity = Robolectric.buildActivity(MainActivity.class).create().get();
	   */
	}
	
	@After
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
	  
	  //Example robolectric code
	  /*ImageView pivotalLogo = (ImageView) activity.findViewById(R.id.pivotal_logo);
	  ShadowImageView shadowPivotalLogo = Robolectric.shadowOf(pivotalLogo);
	  assertThat(shadowPivotalLogo.resourceId, equalTo(R.drawable.pivotallabs_logo));*/
	  
	  //Get row count before increment button click
	  TextView countText = (TextView) activity.findViewById(R.id.counter_number);
	  int previousCount = Integer.parseInt(countText.getText().toString());
	  
	  view.performClick();
	  
	  // Check the row count is incremented
	  int afterCount = Integer.parseInt(countText.getText().toString());
	  
	  assertEquals("Row count not properly incremented", previousCount+1, afterCount);
	}
}
