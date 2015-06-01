package com.example.carpayment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.text.NumberFormat; // for currency formatting

import android.app.Activity; // base class for activities
import android.os.Bundle; // for saving state information
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text

public class MainActivity extends Activity {

	private static final NumberFormat currencyFormat = NumberFormat
			.getCurrencyInstance();
	private static final NumberFormat percentFormat = NumberFormat
			.getPercentInstance();

	private double billAmount = 0.0; // bill amount entered by the user
	private double downpayment = 0.0;
	private double customPercent = 0.00;
	private double duration = 2.0; // initial custom tip percentage
	private TextView amountDisplayTextView; // shows formatted bill amount
	private TextView downpaymentDispayTextView;
	private TextView percentCustomTextView; // shows custom tip percentage
	private TextView monthlyInstalmentTextView; // shows 15% tip

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
		downpaymentDispayTextView = (TextView) findViewById(R.id.customerPercentDisplayTextView);
		percentCustomTextView = (TextView) findViewById(R.id.RateDisplayTextView);
		monthlyInstalmentTextView = (TextView) findViewById(R.id.monthlyRateDisplayTextView);

		// update GUI based on billAmount and customPercent
		amountDisplayTextView.setText(currencyFormat.format(billAmount));

		downpaymentDispayTextView.setText(currencyFormat.format(downpayment));
		percentCustomTextView.setText(percentFormat.format(customPercent));

		EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
		amountEditText.addTextChangedListener(amountEditTextWatcher);

		EditText downpaymentEditText = (EditText) findViewById(R.id.customerPercentEditText);
		downpaymentEditText.addTextChangedListener(downEditTextWatcher);

		EditText rateEditText = (EditText) findViewById(R.id.RateEditText);
		rateEditText.addTextChangedListener(rateEditTextWatcher);

		// set customTipSeekBar's OnSeekBarChangeListener
		SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.durationDisplay);
		customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

	}

	private void updateCustom() {
		// calculate 15% tip and total
		double reamains = billAmount - downpayment;
		double total = reamains * duration * customPercent;
		System.out.println("tttttttttttttttttttt"+total);
		
		if(duration>0){
		double newtotal = (reamains + total) / (duration * 12);
		monthlyInstalmentTextView.setText(currencyFormat.format(newtotal)+" For "+(int)duration+" years");
		}else{
			monthlyInstalmentTextView.setText("Duration must be greater than 0");
		}
		
		// display 15% tip and total formatted as currency
		

	}

	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
		// update customPercent, then call updateCustom
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// sets customPercent to position of the SeekBar's thumb
			duration = progress;
			updateCustom(); // update the custom tip TextViews
		} // end method onProgressChanged

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		} // end method onStartTrackingTouch

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		} // end method onStopTrackingTouch
	}; // end OnSeekBarChangeListener

	
	//1-------------------Text
	private TextWatcher amountEditTextWatcher = new TextWatcher() {
		// called when the user enters a number
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// convert amountEditText's text to a double
			try {
				billAmount = Double.parseDouble(s.toString()) / 100.0;
			} // end try
			catch (NumberFormatException e) {
				billAmount = 0.0; // default if an exception occurs
			} // end catch

			// display currency formatted bill amount
			amountDisplayTextView.setText(currencyFormat.format(billAmount));
			// downpaymentDispayTextView.setText(currencyFormat.format(downpayment));
			// percentCustomTextView.setText(percentFormat.format(customPercent));
			// updateStandard(); // update the 15% tip TextViews
			// updateCustom(); // update the custom tip TextViews
		} // end method onTextChanged

		@Override
		public void afterTextChanged(Editable s) {
		} // end method afterTextChanged

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		} // end method beforeTextChanged
	};

	// 2---------------Text

	private TextWatcher downEditTextWatcher = new TextWatcher() {
		// called when the user enters a number
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// convert amountEditText's text to a double
			try {
				downpayment = Double.parseDouble(s.toString()) / 100.0;
			} // end try
			catch (NumberFormatException e) {
				downpayment = 0.0; // default if an exception occurs
			} // end catch

			// display currency formatted bill amount
			downpaymentDispayTextView.setText(currencyFormat
					.format(downpayment));
			// downpaymentDispayTextView.setText(currencyFormat.format(downpayment));
			// percentCustomTextView.setText(percentFormat.format(customPercent));
			// updateStandard(); // update the 15% tip TextViews
			// updateCustom(); // update the custom tip TextViews
		} // end method onTextChanged

		@Override
		public void afterTextChanged(Editable s) {
		} // end method afterTextChanged

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		} // end method beforeTextChanged
	};

	// 3---------------------Text

	private TextWatcher rateEditTextWatcher = new TextWatcher() {
		// called when the user enters a number
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// convert amountEditText's text to a double
			try {
				customPercent = Double.parseDouble(s.toString())/100.0;
			} // end try
			catch (NumberFormatException e) {
				customPercent = 0.0; // default if an exception occurs
			} // end catch

			// display currency formatted bill amount
			percentCustomTextView.setText(percentFormat.format(customPercent));
			// downpaymentDispayTextView.setText(currencyFormat.format(downpayment));
			// percentCustomTextView.setText(percentFormat.format(customPercent));
			// updateStandard(); // update the 15% tip TextViews
			updateCustom(); // update the custom tip TextViews
		} // end method onTextChanged

		@Override
		public void afterTextChanged(Editable s) {
		} // end method afterTextChanged

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		} // end method beforeTextChanged
	};

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */
}
