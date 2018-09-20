package com.firesend.view;

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import com.firesend.*;
import android.widget.*;

public class StyleTextView extends View
{
	private Paint paint;
	
	private int x=0;
	private int y=0;
	private float defX;
	private float defY;
	private String text="StyleTextView";
	private float textSize=50.0f;
	private int textColor=Color.BLACK;
	private int bgcolor_0=Color.GRAY;
	private int bgcolor_1=Color.GRAY;
	private int rectPaddingL=10;
	private int rectPaddingT=10;
    private int rectPaddingR=10;
	private int rectPaddingB=10;
	private boolean rectOutStyle=false;
	private boolean textOutStyle=false;
	private float rectStrokeWidth=12.0f;
	private float textStrokeWidth=5.0f;
	private int maskStyle=0;
	private int blurMode=0;
	private float blurMaskWidth=10.0f;
	
	public StyleTextView(Context context) {
		this(context, null);
	}
	public StyleTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public StyleTextView(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		
		TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.StyleTextView);
		int n=a.getIndexCount();
		for(int i=0;i<n;i++){
			int attr=a.getIndex(i);
			switch(attr){
				case R.styleable.StyleTextView_text:
					text=a.getString(attr);
					break;
				case R.styleable.StyleTextView_text_size:
					textSize=a.getFloat(attr,0);
					break;
				case R.styleable.StyleTextView_text_color:
					textColor=a.getColor(attr,0);
					break;		
				case R.styleable.StyleTextView_bgcolor_0:
					bgcolor_0=a.getColor(attr,0);
					break;
				case R.styleable.StyleTextView_bgcolor_1:
					bgcolor_1=a.getColor(attr,0);
					break;
				case R.styleable.StyleTextView_rect_paddingL:
					rectPaddingL=a.getInteger(attr,0);
					break;
				case R.styleable.StyleTextView_rect_paddingT:
					rectPaddingT=a.getInteger(attr,0);
					break;
				case R.styleable.StyleTextView_rect_paddingR:
					rectPaddingR=a.getInteger(attr,0);
					break;
				case R.styleable.StyleTextView_rect_paddingB:
					rectPaddingB=a.getInteger(attr,0);
					break;
				case R.styleable.StyleTextView_rect_out_style:
					rectOutStyle=a.getBoolean(attr,false);
				    break;
				case R.styleable.StyleTextView_text_out_style:
					textOutStyle=a.getBoolean(attr,false);
				    break;
				case R.styleable.StyleTextView_rect_stroke_width:
					rectStrokeWidth=a.getFloat(attr,0);
					break;
				case R.styleable.StyleTextView_text_stroke_width:
					textStrokeWidth=a.getFloat(attr,0);
					break;
				case R.styleable.StyleTextView_mask_style:
					maskStyle=a.getInt(attr,0);
					break;
				case R.styleable.StyleTextView_blur_mode:
					blurMode=a.getInt(attr,0);
					break;
				case R.styleable.StyleTextView_blur_mask_width:
					blurMaskWidth=a.getFloat(attr,0);
					break;
			}
		}a.recycle();
		
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(textSize);
		//paint.setTypeface(Typeface.SERIF);
		paint.setTextAlign(Paint.Align.CENTER);
		

		switch(maskStyle)
		{
		   case -1:
				switch(blurMode)
				{
					case -1:
						paint.setMaskFilter(new BlurMaskFilter(blurMaskWidth,BlurMaskFilter.Blur.INNER));
						break;
					case 0:
						paint.setMaskFilter(new BlurMaskFilter(blurMaskWidth,BlurMaskFilter.Blur.NORMAL));
						break;
					case 1:
						paint.setMaskFilter(new BlurMaskFilter(blurMaskWidth,BlurMaskFilter.Blur.OUTER));
						break;
					case 2:
					    paint.setMaskFilter(new BlurMaskFilter(blurMaskWidth,BlurMaskFilter.Blur.SOLID));
						break;
				}	
			    break;
		   case 0:
		        setLayerType(LAYER_TYPE_SOFTWARE,null);
	            paint.setMaskFilter(new EmbossMaskFilter(new float[] { 2,3,1F }, 0.5F,0.5F,3F));
				break;
	    }
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

		defX=paint.measureText(text)+100;
		defY=paint.descent()-paint.ascent()+50;
		
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
		//背景色0
		canvas.drawColor(bgcolor_0);
		//边框风格
		if(!rectOutStyle)
		{
		  paint.setStyle(Paint.Style.STROKE);
		  paint.setStrokeWidth(rectStrokeWidth);
		}
		//背景色1
		paint.setColor(bgcolor_1);
		canvas.drawRect(new Rect(rectPaddingL,rectPaddingT,x-rectPaddingR,y-rectPaddingB),paint);
	    //字体风格
		if(textOutStyle)
		{ paint.setStyle(Paint.Style.FILL_AND_STROKE);}
	    else
		{ paint.setStyle(Paint.Style.STROKE);}
		paint.setStrokeWidth(textStrokeWidth);
		paint.setColor(textColor);
		canvas.drawText(text,x/2,(y-paint.ascent()-paint.descent())/2,paint);
	}
	
	
	public void setText(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	public void setTextSize(float textSize)
	{
		this.textSize = textSize;
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

	public void setBgcolor_0(int bgcolor_0)
	{
		this.bgcolor_0 = bgcolor_0;
	}

	public int getBgcolor_0()
	{
		return bgcolor_0;
	}

	public void setBgcolor_1(int bgcolor_1)
	{
		this.bgcolor_1 = bgcolor_1;
	}

	public int getBgcolor_1()
	{
		return bgcolor_1;
	}
	public void setRectPaddingL(int rectPaddingL)
	{
		this.rectPaddingL = rectPaddingL;
	}

	public int getRectPaddingL()
	{
		return rectPaddingL;
	}

	public void setRectPaddingT(int rectPaddingT)
	{
		this.rectPaddingT = rectPaddingT;
	}

	public int getRectPaddingT()
	{
		return rectPaddingT;
	}

	public void setRectPaddingR(int rectPaddingR)
	{
		this.rectPaddingR = rectPaddingR;
	}

	public int getRectPaddingR()
	{
		return rectPaddingR;
	}

	public void setRectPaddingB(int rectPaddingB)
	{
		this.rectPaddingB = rectPaddingB;
	}

	public int getRectPaddingB()
	{
		return rectPaddingB;
	}
	
	public void setRectOutStyle(boolean rectOutStyle)
	{
		this.rectOutStyle = rectOutStyle;
	}

	public boolean isRectOutStyle()
	{
		return rectOutStyle;
	}

	public void setTextOutStyle(boolean textOutStyle)
	{
		this.textOutStyle = textOutStyle;
	}

	public boolean isTextOutStyle()
	{
		return textOutStyle;
	}

	public void setRectStrokeWidth(float rectStrokeWidth)
	{
		this.rectStrokeWidth = rectStrokeWidth;
	}

	public float getRectStrokeWidth()
	{
		return rectStrokeWidth;
	}

	public void setTextStrokeWidth(float textStrokeWidth)
	{
		this.textStrokeWidth = textStrokeWidth;
	}

	public float getTextStrokeWidth()
	{
		return textStrokeWidth;
	}

	public void setMaskStyle(int maskStyle)
	{
		this.maskStyle = maskStyle;
	}

	public int getMaskStyle()
	{
		return maskStyle;
	}

	public void setBlurMode(int blurMode)
	{
		this.blurMode = blurMode;
	}

	public int getBlurMode()
	{
		return blurMode;
	}

	public void setBlurMaskWidth(float blurMaskWidth)
	{
		this.blurMaskWidth = blurMaskWidth;
	}

	public float getBlurMaskWidth()
	{
		return blurMaskWidth;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
}

