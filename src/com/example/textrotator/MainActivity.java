package com.example.textrotator;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
		LayoutInflater inflater = LayoutInflater.from(this);
		View textSphere = inflater.inflate(R.layout.rotate, null);

		mainLayout.addView(textSphere);




	}

}
