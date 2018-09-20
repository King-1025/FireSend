package com.firesend.drawable;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import com.firesend.*;

public class Logo extends Drawable
{

	private Context context;
	private Paint paint;
	private Path path;
	private float width;
	private float height;
	private Handler hd;
	private String text="";
	private int textColor=Color.BLACK;
	private float textSize=100;
	private int bgColor=Color.WHITE;
	private Notify notify;
	private boolean isLast=false;
	public Logo(Context ctx,float w,float h){
		context=ctx;
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextAlign(Paint.Align.CENTER);
		path=new Path();
		width=w;
		height=h;
		hd=new Handler(){
			public void handleMessage(Message msg){
				switch(msg.what){
					case 0:
						//text="辅";
						//textColor=Color.argb(255,0,0,0);
						bgColor=Color.RED;
						hd.sendEmptyMessageDelayed(1,500);
						break;
					case 1:
					    //text="助";
						bgColor=Color.MAGENTA;
						hd.sendEmptyMessageDelayed(2,500);
						break;
					case 2:
						//text="发";
						bgColor=Color.CYAN;
						hd.sendEmptyMessageDelayed(3,500);
						break;
					case 3:
						//text="送";
						bgColor=Color.YELLOW;
						hd.sendEmptyMessageDelayed(4,500);
						break;
					case 4:
				        isLast=true;
						break;
						
				}
				invalidateSelf();
				if(notify!=null){
					notify.onColorChange(bgColor,msg.what,text);
				}
			}
		};
	}
    public void setOnColorChange(Notify notify){
		this.notify=notify;
	}
	@Override
	public void draw(Canvas p1)
	{
		// TODO: Implement this method
		p1.drawColor(bgColor);
		//paint.setColor(textColor);
		//paint.setTextSize(textSize);
		//p1.drawText(text,p1.getWidth()/2,p1.getHeight()/5,paint);
	}

	@Override
	public void setAlpha(int p1)
	{
		// TODO: Implement this method
		setAlpha(p1);
	}

	@Override
	public void setColorFilter(ColorFilter p1)
	{
		// TODO: Implement this method
		setColorFilter(p1);
	}

	@Override
	public int getOpacity()
	{
		// TODO: Implement this method
		return 0;
	}

	public void start(){
		stop();
		hd.sendEmptyMessageDelayed(0,1000);
	}
	
	
	public void stop(){
		hd.removeMessages(0);
		hd.removeMessages(1);
		hd.removeMessages(2);
		hd.removeMessages(3);
	}
}
