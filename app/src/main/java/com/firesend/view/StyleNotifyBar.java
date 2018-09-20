package com.firesend.view;

import android.content.*;
import android.graphics.*;
import android.provider.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.firesend.*;
import com.firesend.drawable.*;

public class StyleNotifyBar extends RelativeLayout
{
	private Context context;
	private TextView text;
	private ImageView notify;
	private StyleIcon bg_notify;
	private ImageView go;
	private StyleIcon bg_go;
	
	private float x;
	private float y;

	public StyleNotifyBar(Context context) {
		this(context, null);
	}
	public StyleNotifyBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public StyleNotifyBar(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.style_notify_bar,this,true);
		text=(TextView) findViewById(R.id.stylenotifybarTextView1);
		notify=(ImageView) findViewById(R.id.stylenotifybarImageView1);
		bg_notify=new StyleIcon(StyleIcon.NOTIFY);
		notify.setBackgroundDrawable(bg_notify);
		go=(ImageView) findViewById(R.id.stylenotifybarImageView2);
		bg_go=new StyleIcon(StyleIcon.GO);
		bg_go.setMainColor(Color.GRAY);
		go.setBackgroundDrawable(bg_go);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				x=event.getRawX();
				y=event.getRawY();
				setAlpha(0.5f);
				break;
			case MotionEvent.ACTION_UP:
				if(event.getRawX()-x<getWidth())
					if(event.getRawY()-y<getHeight())
					{context.startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));}
				setAlpha(1.0f);
				break;
		}
		return true;
	}
	public void isNotify(boolean p0){
		if(p0)
		{   
		    bg_notify.setMainColor(Color.GREEN);
			text.setText("服务开启成功,请确保已设置发送规则。");
			setBackgroundColor(Color.argb(100,0,225,0));
		}else{
			bg_notify.setMainColor(Color.RED);
			text.setText("未开启服务，请检查辅助功能。");
			setBackgroundColor(Color.argb(100,225,0,0));
		}
	}
}
