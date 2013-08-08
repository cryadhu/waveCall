package com.example.waveCall;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;



public class settings extends Activity implements OnClickListener, OnCheckedChangeListener  {

	RadioGroup radioGroup;
	Button bsave;
	CheckBox cs;
	TextView tv;
	SharedPreferences app_preference;
	private static final String SELECTED_INDEX="SelectedIndex";
	SharedPreferences.Editor editor;
	SharedPreferences pref;
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings);
        
        pref=PreferenceManager.getDefaultSharedPreferences(this);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        cs = (CheckBox) findViewById(R.id.cbstate);
        tv = (TextView) findViewById(R.id.tvState);
        
        bsave=(Button)findViewById(R.id.bssave);
		bsave.setOnClickListener(this);
		app_preference = PreferenceManager.getDefaultSharedPreferences(this);
		id=getSelectedValue();
		
		radioGroup.setOnCheckedChangeListener(this);
		
		if(id<=0)
			id=R.id.radio0;
		radioGroup.check(id);
		
		if(id==R.id.radio0)
		{
			tv.setText("In Call");
			cs.setText("Speaker");
		}
		else
		{
			tv.setText("On Reject");
			cs.setText("Auto Reply");
		}
	}
	@Override
	public void onClick(View arg0) {
		saveSelectedIndex(radioGroup.getCheckedRadioButtonId());
		
		if(cs.isChecked())
			{
			  if(cs.getText().toString().equals("Speaker"))
					  editor.putString("checkbox_value", cs.getText().toString());
			  else
				  	  {
				  	  editor.putString("checkbox_value1", cs.getText().toString());
				  	  }
			}
		else
			{
				if(cs.getText().toString().equals("Speaker"))
					  editor.putString("checkbox_value", null);
				else
				  	  {
					  editor.putString("checkbox_value1", null);
				  	  }
			}
		editor.commit();
		finish();
		
	}
	private int getSelectedValue(){
	    String checked = pref.getString("checkbox_value", null);
	    if(checked!=null)
	    {
	    		cs.setChecked(true);
	    }
	    return pref.getInt(SELECTED_INDEX, -1);
	}

	private void saveSelectedIndex(int value){
	    editor.putInt(SELECTED_INDEX, value);
	    editor.commit();
	}
	@Override
	public void onCheckedChanged(RadioGroup rg, int arg1) {
		
		if(rg.getCheckedRadioButtonId()==R.id.radio0)
		{
			if(pref.getString("checkbox_value", "None").equals("Speaker"))
			{
				cs.setChecked(true);
			}else cs.setChecked(false);
			cs.setVisibility(1);
			tv.setVisibility(1);
			tv.setText("In Call");
			cs.setText("Speaker");
		}
		else
		{
			if(pref.getString("checkbox_value1", "None").equals("Auto Reply"))
			{
				cs.setChecked(true);
			}else cs.setChecked(false);
			cs.setVisibility(-1);
			tv.setVisibility(-1);
		}
		
	}


}
