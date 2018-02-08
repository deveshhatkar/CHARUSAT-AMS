package com.project.charusat_ams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenLogo extends Activity 
{
	private static int SPLASH_TIMEOUT = 3000;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen_logo);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run()
			{
				Intent intent = new Intent(SplashScreenLogo.this, MainActivity.class);
				startActivity(intent);
			}
		}, SPLASH_TIMEOUT);
	}
}
