package com.firesend.view;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.firesend.*;
import com.firesend.drawable.*;

public class StyleSettingItem extends RelativeLayout
{
	private Context context;
	private LinearLayout llLeft;
	private LinearLayout llRight;
	private TextView title;
	private TextView details;
	private TextView status;
    private ImageView im;
	private boolean isShowGO;
	public StyleSettingItem(Context context) {
		this(context, null);
	}
	public StyleSettingItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public StyleSettingItem(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.setting_item,this,true);
		llLeft=(LinearLayout) findViewById(R.id.settingitemLinearLayout1);
		llRight=(LinearLayout) findViewById(R.id.settingitemLinearLayout2);
		title=(TextView) findViewById(R.id.settingitemTextView1);
		details=(TextView) findViewById(R.id.settingitemTextView2);
		status=(TextView) findViewById(R.id.settingitemTextView3);
		im=(ImageView) findViewById(R.id.settingitemImageView1);
		
		title.setVisibility(GONE);
		details.setVisibility(GONE);
		status.setVisibility(GONE);
		
		isShowGO=false;
	}
	
	public void setTitle(int p0)
	{
		if(p0==0)
		{
			title.setText(null);
			title.setVisibility(GONE);
		}else{
			title.setText(p0);
		    title.setVisibility(VISIBLE);
		}
	}

	public void setDetails(int p0)
	{
		if(p0==0)
		{
			details.setText(null);
			details.setVisibility(GONE);
		}else{
			details.setText(p0);
		    details.setVisibility(VISIBLE);
		}
	}

	public void setStatus(int p0)
	{
		if(p0==0)
		{
			status.setText(null);
			status.setVisibility(GONE);
		}else{
			status.setText(p0);
		    status.setVisibility(VISIBLE);
		}
	}
	
	
	public void setTitle(String p0)
	{
		title.setText(p0);
		if(p0==null)
		{
			title.setVisibility(GONE);
		}else{
		    title.setVisibility(VISIBLE);
		}
	}

	public void setDetails(String p0)
	{
		details.setText(p0);
		if(p0==null)
		{
			details.setVisibility(GONE);
		}else{
		    details.setVisibility(VISIBLE);
		}
	}

	public void setStatus(String p0)
	{
		status.setText(p0);
		if(p0==null)
		{
			status.setVisibility(GONE);
		}else{
		    status.setVisibility(VISIBLE);
		}
	}
	public void setShowGO(boolean p0){
		if(p0)
		{im.setBackground(new StyleIcon(StyleIcon.GO));}
		else
		{im.setBackground(null);}
	}
	public LinearLayout getLeftRoom(){
		return llLeft;
	}
	public LinearLayout getRightRoom(){
		return llRight;
	}
	
}
