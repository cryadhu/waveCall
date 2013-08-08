package com.example.waveCall;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

public class PhoneStatReceiver extends BroadcastReceiver implements SensorEventListener {        
    private static boolean incomingFlag = false;
    private static String incoming_number = null;
    private Sensor mProximity;
	private SensorManager mSensorManager;
	private NumberFormat numb = new DecimalFormat();
	private static final String SELECTED_INDEX="SelectedIndex";
	Context gcontext;
	SharedPreferences.Editor editor ;
	SharedPreferences app_preference;
	TelephonyManager tm;
	String phoneNumber;
	AudioManager am;
	 boolean callFromApp=false; 
	 boolean callFromOffHook=false;
	 
	 boolean inPocket = true;

    @Override
    public void onReceive(Context context, Intent intent) {
    		
    		 app_preference = PreferenceManager.getDefaultSharedPreferences(context);
    		 SharedPreferences.Editor editor = app_preference.edit();
    	     am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
             gcontext=context;
            if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){                        
                    incomingFlag = false;
                    phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);        
            }else{                        
                    tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);                        
                    switch (tm.getCallState()) 
                    {
	                    case TelephonyManager.CALL_STATE_RINGING:
	                            incomingFlag = true;
	                            incoming_number = intent.getStringExtra("incoming_number");
	                            if(app_preference.getString("key", "Turned ON").equals("Turned ON"))
	                            {
		                            mSensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
		                       		mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		                            mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_FASTEST);
	                            }
	                    break;
	                    
	                    case TelephonyManager.CALL_STATE_OFFHOOK:	
	                    	if(app_preference.getString("checkbox_value", "No Speaker").equals("Speaker")&incomingFlag&
	                    			(app_preference.getInt(SELECTED_INDEX, R.id.radio0)==R.id.radio0))
					    		{
	                    		 AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	                             audioManager.setSpeakerphoneOn(true);
	                             
					    		}
	                    	
	                    break;
	                    
	                    default:
	                    	AudioManager am = (AudioManager) gcontext.getSystemService(Context.AUDIO_SERVICE);
	                    	am.setStreamSolo(AudioManager.STREAM_VOICE_CALL, false);
	                    	am.setMode(AudioManager.MODE_NORMAL );
	                    break;
                    } 
            }
           
    }
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		    if(event.values[0] == 0)
		    {
			    if(!inPocket)
			    {
			    	if(app_preference.getInt(SELECTED_INDEX, R.id.radio0)!=R.id.radio0)
			    	{
			    		 mSensorManager.unregisterListener(this, mProximity);
			    		 AudioManager am = (AudioManager) gcontext.getSystemService(Context.AUDIO_SERVICE);
		                 am.setMode(AudioManager.MODE_IN_CALL);
		                 am.setStreamSolo(AudioManager.STREAM_VOICE_CALL, true);
			    		
			    	}
				    else
				    	{ 
				    	  mSensorManager.unregisterListener(this, mProximity);
				    	  Intent answer = new Intent(Intent.ACTION_MEDIA_BUTTON);
		                  answer.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
		                  gcontext.sendOrderedBroadcast(answer, null);
				    	}
			    }//timer
			    inPocket=true;
		    }
		    else inPocket=false;
		
	}
	
}