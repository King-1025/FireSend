package com.firesend.other;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.firesend.*;
import com.firesend.drawable.*;
import com.firesend.view.*;

public class SplashActivity extends Activity implements Notify
{

	//private View bg;
	//private LinearLayout ll;
	private StyleTextView stv0;
	private StyleTextView stv1;
	private StyleTextView stv2;
	private StyleTextView stv3;
	//private StyleTextView stv4;
	//private StyleTextView stv5;
	//private Logo logo;
	//private TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new Handler().postDelayed(new Runnable(){

				@Override
				public void run()
				{
					// TODO: Implement this method 
					startActivity(new Intent(SplashActivity.this,MainActivity.class));
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					finish();
					//Toast.makeText(SplashActivity.this,"跳转",1000).show();
				}	

			},1500);
        /*bg=findViewById(R.id.splashRelativeLayout1);
		ll=(LinearLayout) findViewById(R.id.splashLinearLayout2);
		stv0=(StyleTextView) findViewById(R.id.splashStyleTextView1);
		stv1=(StyleTextView) findViewById(R.id.splashStyleTextView2);
		stv2=(StyleTextView) findViewById(R.id.splashStyleTextView3);
		stv3=(StyleTextView) findViewById(R.id.splashStyleTextView4);
		tv=(TextView) findViewById(R.id.splashTextView1);
		logo=new Logo(this,100,100);
		logo.setOnColorChange(this);
		bg.setBackground(logo);*/
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		//logo.start();
		super.onResume();
	}
    
	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		// logo.stop();
		super.onPause();
	}
	
	@Override
	public void onColorChange(int color, int index, String text)
	{
		// TODO: Implement this method
		switch(index){
			case 0:
				setSTV(stv0,color);
				break;
			case 1:
				setSTV(stv0,color);
				setSTV(stv1,color);
				break;
			case 2:
				setSTV(stv1,color);
				setSTV(stv2,color);
				break;
			case 3:
				setSTV(stv2,color);
				setSTV(stv3,color);
				break;
			case 4:
				new Handler().postDelayed(new Runnable(){

						@Override
						public void run()
						{
							// TODO: Implement this method 
							startActivity(new Intent(SplashActivity.this,MainActivity.class));
							overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
							finish();
							//Toast.makeText(SplashActivity.this,"跳转",1000).show();
						}	

					},1000);
				break;
				
		}
		//Toast.makeText(SplashActivity.this,color+":"+index+":"+text,1000).show();
	}
	
	private void setSTV(StyleTextView stv,int color){
		if(stv!=null){
			if(stv.getVisibility()==View.VISIBLE){
				stv.setVisibility(View.INVISIBLE);
			}else{
				stv.setVisibility(View.VISIBLE);
				stv.setBgcolor_0(color);
			}
		}
	}
}
