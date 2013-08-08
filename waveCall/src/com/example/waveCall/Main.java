package com.example.waveCall;



import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {

	
	
	Button b,bs;
	SharedPreferences app_preference;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
		b=(Button)findViewById(R.id.bstate);
		b.setOnClickListener(this);
		bs=(Button)findViewById(R.id.button1);
		bs.setOnClickListener(this);
		app_preference = PreferenceManager.getDefaultSharedPreferences(this);
		b.setText(app_preference.getString("key", "Turned ON"));
		
	}

	@Override
	public void onClick(View v) {

		switch(v.getId())
		{
		case R.id.bstate:
				SharedPreferences.Editor editor = app_preference.edit();
		        if(!b.getText().toString().equals("Turned ON"))
		        		{
		        		Toast.makeText(this, "Turned ON", Toast.LENGTH_SHORT).show();
		        		b.setText("Turned ON");
		        		}
		        else
			        	{
			        	Toast.makeText(this, "Turned OFF", Toast.LENGTH_SHORT).show();
			        	b.setText("Turned OFF");
			        	}
		        editor.putString("key", b.getText().toString());
		        editor.commit();
		        
		break;
		
		case R.id.button1:
			Intent i = new Intent("android.intent.action.SETTINGS");
			startActivity(i);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater expand = getMenuInflater();
		expand.inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch(item.getItemId())
		{
		case R.id.menu_settings:
			i = new Intent("android.intent.action.SETTINGS");
			startActivity(i); 
			break;
		case R.id.credits:
			i = new Intent("android.intent.action.CREDITS");
			startActivity(i); 
			break;
		}
		return false;
	}
	
	
}
