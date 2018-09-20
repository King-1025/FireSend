package com.firesend.view;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import com.firesend.*;

public class StyleLine extends View
{
	
	private Paint paint;
	private float x;
	private float y;
	private float defX;
	private float defY;
	private int colorx;
	private int colory;
	private float w;
	private float l;
	public StyleLine(Context context) {
		this(context, null);
	}
	public StyleLine(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public StyleLine(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		defX=1080;
		defY=20;
		colorx=Color.GRAY;
		colory=colorx;
		w=5F;
		l=1.5F;
		TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.StyleTextView);
		int n=a.getIndexCount();
		for(int i=0;i<n;i++){
			int attr=a.getIndex(i);
			switch(attr){
				case R.styleable.StyleLine_colorx:
					colorx=a.getColor(attr,0);
					break;
				case R.styleable.StyleLine_colory:
					colory=a.getColor(attr,0);
					break;
				case R.styleable.StyleLine_w:
					w=a.getFloat(attr,0);
					break;
				case R.styleable.StyleLine_l:
					l=a.getFloat(attr,0);
			}
		}a.recycle();
		setLayerType(LAYER_TYPE_SOFTWARE,null);
		paint.setMaskFilter(new EmbossMaskFilter(new float[] { 2,3,1F }, 0.6F,0.5F,w));
	}
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO: Implement this method
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width=0;
		int widthMode=MeasureSpec.getMode(widthMeasureSpec);
		int widthSize=MeasureSpec.getSize(widthMeasureSpec);

		int height=0;
		int heightMode=MeasureSpec.getMode(heightMeasureSpec);
		int heightSize=MeasureSpec.getSize(heightMeasureSpec);

		switch(widthMode){
			case MeasureSpec.EXACTLY://确切值或者match_parent
				width=widthSize;
				break;
			case MeasureSpec.AT_MOST://wrap_content
			    width=(int) defX;
				break;
			case MeasureSpec.UNSPECIFIED:
				break;
		}
		switch(heightMode){
			case MeasureSpec.EXACTLY://确切值或者match_parent
				height=heightSize;
				break;
			case MeasureSpec.AT_MOST://wrap_content
			    height=(int) defY;
				break;
			case MeasureSpec.UNSPECIFIED:
				break;
		}
		setMeasuredDimension(width,height);
		//确定的画布大小
		x = getMeasuredWidth();
		y = getMeasuredHeight();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
		canvas.drawColor(colorx);
		paint.setColor(colory);
	    canvas.drawRect(l,l,x-l,y-l,paint);
	}
}
