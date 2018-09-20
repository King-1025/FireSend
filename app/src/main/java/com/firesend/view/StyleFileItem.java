package com.firesend.view;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.firesend.*;
import android.graphics.drawable.*;

public class StyleFileItem extends RelativeLayout
{
	private Context context;
	private TextView tv;
	private ImageView iv;
	public StyleFileItem(Context context) {
		this(context, null);
	}
	public StyleFileItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public StyleFileItem(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.file_item,this,true);
		iv=(ImageView) findViewById(R.id.fileitemImageView1);
		tv=(TextView) findViewById(R.id.fileitemTextView1);
	}
	public void setTVText(String str){
		if(tv!=null){
			tv.setText(str);
		}
	}

	public void setIVDrawable(Drawable background)
	{
		// TODO: Implement this method
		iv.setBackground(background);
	}
}
