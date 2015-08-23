package com.abidroid.dailyexpense;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private static int SPLASH_TIME_OUT = 2000;
	private TextView txt1, txt2;
	private Typeface tf1, tf2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		txt1 = (TextView)findViewById(R.id.txtRdate);
		txt2 = (TextView)findViewById(R.id.txtDate);
		
		tf1 = Typeface.createFromAsset(getAssets(), "fonts/cac_champagne.ttf");
		tf2 = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
		
		txt1.setTypeface(tf1);
		txt2.setTypeface(tf2);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(i);
				finish();
				
			}
		}, SPLASH_TIME_OUT);
		
	}
}
