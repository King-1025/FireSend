package com.firesend.view;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.provider.*;
import android.util.*;
import android.view.*;
import com.firesend.*;
import android.view.View.*;
import android.widget.*;

public class StyleButton extends View 
{

    private Context context;
	private Paint paint;
	private RectF rectf;
	
	private int widthMeasureSpec;
	private int heightMeasureSpec;
	
	private float x;
	private float y;
	private float centerX;
	private float centerY;
	private float minR;
	private float maxR;
	
	private String text="Text";
	private float textSize=50;
	private int text_length;
	private int textColor=Color.WHITE;
	private int backgroundColor=Color.BLACK;
	private boolean bgMaskFilter=true;
	private boolean textMaskFilter=true;
	private float rX=-1;
	private float rY=-1;

	public StyleButton(Context context) {
		this(context, null);
	}
	public StyleButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public StyleButton(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		this.context=context;
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		rectf=new RectF();
		setLayerType(LAYER_TYPE_SOFTWARE,paint);
		
		TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.StyleButton);
		int n=a.getIndexCount();
		for(int i=0;i<n;i++){
			int attr=a.getIndex(i);
			switch(attr){
				case R.styleable.StyleButton_text:
					text=a.getString(attr);
					break;
				case R.styleable.StyleButton_text_size:
					textSize=a.getFloat(attr,0);
					break;
				case R.styleable.StyleButton_text_color:
					textColor=a.getColor(attr,0);
					break;
				case R.styleable.StyleButton_backgroundColor:
					backgroundColor=a.getColor(attr,0);
					break;
				case R.styleable.StyleButton_bgMaskFilter:
					bgMaskFilter=a.getBoolean(attr,true);
					break;
				case R.styleable.StyleButton_textMaskFilter:
					textMaskFilter=a.getBoolean(attr,true);
					break;
				case R.styleable.StyleButton_rX:
					rX=a.getFloat(attr,-1);
					break;
				case R.styleable.StyleButton_rY:
					rY=a.getFloat(attr,-1);
					break;
			}
		}a.recycle();
        
		text_length=text.length();
		paint.setTypeface(Typeface.SERIF);
	    paint.setTextAlign(Paint.Align.CENTER);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO: Implement this method
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int width=0;
		int height=0;
		int defX=0;
		int defY=0;
	    int widthMode=MeasureSpec.getMode(widthMeasureSpec);
		int widthSize=MeasureSpec.getSize(widthMeasureSpec);
		int heightMode=MeasureSpec.getMode(heightMeasureSpec);
		int heightSize=MeasureSpec.getSize(heightMeasureSpec);
		
		this.widthMeasureSpec=widthMeasureSpec;
		this.heightMeasureSpec=heightMeasureSpec;
		if(text==null)
		{
			defX=100;
			defY=100;
			return;
		}
		defX=(int)((text_length+1.5f)*textSize);
		defY=(int)(defX*0.618f);
		
		switch(widthMode){
			case MeasureSpec.EXACTLY://确切值或者match_parent
				width=widthSize;
				break;
			case MeasureSpec.AT_MOST://wrap_content
			    width=defX;
				break;
			case MeasureSpec.UNSPECIFIED:
				break;
		}
		switch(heightMode){
			case MeasureSpec.EXACTLY://确切值或者match_parent
				height=heightSize;
				break;
			case MeasureSpec.AT_MOST://wrap_content
			    height=defY;
				break;
			case MeasureSpec.UNSPECIFIED:
				break;
		}
		setMeasuredDimension(width,height);
		//设置绘制规则
		x = getMeasuredWidth();
		y = getMeasuredHeight();
		centerX=x/2;
		centerY=y/2;
		minR=Math.min(centerX,centerY);
		maxR=Math.max(centerX,centerY);
		if(rX<0){
			rX=minR*0.25f;
		}
		if(rY<0){
			rY=minR*0.25f;
		}
		rectf.set(0,0,x,y);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
	
		if(bgMaskFilter)
		{paint.setMaskFilter(new BlurMaskFilter(minR*0.35f,BlurMaskFilter.Blur.INNER));}

		paint.setColor(backgroundColor);
		canvas.drawRoundRect(rectf,rX,rY,paint);

		if(textMaskFilter)
		{paint.setMaskFilter(new BlurMaskFilter(minR*0.15f,BlurMaskFilter.Blur.SOLID));}

		paint.setColor(textColor);
		paint.setTextSize(textSize);
		canvas.drawText(text,centerX,(y-paint.ascent()-paint.descent())/2,paint);
        paint.setMaskFilter(null);

	}
	
	public void setText(String text)
	{
		this.text=text;
		invalidate();
	}

	public String getText()
	{
		return text;
	}

	public void setTextSize(float textSize)
	{
		this.textSize = textSize;
		invalidate();
	}

	public float getTextSize()
	{
		return textSize;
	}

	public void setTextColor(int textColor)
	{
		this.textColor = textColor;
	}

	public int getTextColor()
	{
		return textColor;
	}

	public void setBackgroundColor(int backgroundColor)
	{
		this.backgroundColor = backgroundColor;
	}

	public int getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setRX(float rX)
	{
		this.rX = rX;
	}

	public float getRX()
	{
		return rX;
	}

	public void setRY(float rY)
	{
		this.rY = rY;
	}

	public float getRY()
	{
		return rY;
	}

	public void setBgMaskFilter(boolean bgMaskFilter)
	{
		this.bgMaskFilter = bgMaskFilter;
	}

	public boolean isBgMaskFilter()
	{
		return bgMaskFilter;
	}

	public void setTextMaskFilter(boolean textMaskFilter)
	{
		this.textMaskFilter = textMaskFilter;
	}

	public boolean isTextMaskFilter()
	{
		return textMaskFilter;
	}
	
}
