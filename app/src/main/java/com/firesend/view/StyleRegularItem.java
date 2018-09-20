package com.firesend.view;

import android.content.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.firesend.*;
import java.util.*;

public class StyleRegularItem extends RelativeLayout
{
	private Context context;
	private TextView title;
	private TextView content;
	private TextView delaytime;
	public StyleRegularItem(Context context) {
		this(context, null);
	}
	public StyleRegularItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public StyleRegularItem(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.regular_item,this,true);
		title=(TextView) findViewById(R.id.regularitemTextView1);
		content=(TextView) findViewById(R.id.regularitemTextView2);
		delaytime=(TextView) findViewById(R.id.regularitemTextView3);
	}
	public void setData(int p0,List p1,List p2)
	{
		title.setText("第"+(p0+1)+"条");
		content.setText("发送内容:\n\t\t\t\t"+p1.get(p0));
		delaytime.setText("延迟时间:"+timeToString(p2.get(p0)));
	}
	
	public String timeToString(int time){
		String str="无延迟";
		if(time>=1&&time<1000){
			str=time+"毫秒";
		}
		else if(time>=1000&&time<60000){
			str=(time/1000)+"秒";
		}else if(time>=60000){
			str=(time/60000)+"分钟";
		}
		return str;
	}
	
}
