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
		View textLayout1 = inflater.inflate(R.layout.rotate1, null);
		View textLayout2 = inflater.inflate(R.layout.rotate2, null);
		mainLayout.addView(textLayout1);
		mainLayout.addView(textLayout2);

		textLayout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "GO桌面",
						Toast.LENGTH_SHORT).show();
			}
		});
		textLayout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "GO天气",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}
