package com.firesend.drawable;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.util.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import com.firesend.*;

public class StyleIcon extends Drawable implements OnTouchListener
{
	private Paint paint;
	private Path path;
	private RectF rectf;
	private int x;
	private int y;
	private float centerX;
	private float centerY;
	private float minRXY;
	private int mainColor=Color.WHITE;
	private int paddingColor=Color.GRAY;
	private int type;

	public final static int NOTIFY=0;
	public final static int GO=1;
	public final static int ADD=2;
	public final static int DELETE=3;
	public final static int POINT=4;
	public final static int BACK=5;
	public StyleIcon(int p0){
		type=p0;
		paint=new Paint(Paint.ANTI_ALIAS_FLAG);
		path=new Path();
	    rectf=new RectF();
	}
	
	@Override
	public void draw(Canvas p1)
	{
		// TODO: Implement this method
		   x=p1.getWidth();
		   y=p1.getHeight();
		   if(x==0){
			   x=100;
		   }
		   if(y==0){
			   y=100;
		   }
		   centerX=x/2;
		   centerY=y/2;
	   	   minRXY=Math.min(centerX,centerY);
		   paint.setColor(mainColor);
		   paint.setAlpha(125);
		   switch(type){
			   case NOTIFY:
				   drawNotify(p1);
				   break;
			   case GO:
				   drawGO(p1);
				   break;
			   case ADD:
				   drawAdd(p1);
				   break;
			   case DELETE:
				   drawDelete(p1);
				   break;
			   case POINT:
				   drawPoint(p1);
				   break;
			   case BACK:
				   drawBack(p1);
				   break;
		   }
	}
	
	private void drawNotify(Canvas p1){
		p1.drawCircle(centerX,centerY,0.75f*minRXY,paint);
		paint.setColor(Color.WHITE);
		paint.setAlpha(225);
		p1.drawCircle(centerX,centerY-minRXY*0.5f,0.125f*minRXY,paint);
		p1.drawRect(centerX-minRXY*0.12f,centerY-minRXY*0.23f,centerX+minRXY*0.12f,centerY+minRXY*0.57f,paint);
		paint.setColor(Color.GRAY);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(0.25f);
		p1.drawCircle(centerX,centerY,0.75f*minRXY,paint);
		paint.setStyle(Paint.Style.FILL);
	}
	
	public void drawGO(Canvas p1){
		
	    path.moveTo(x*0.4f,y*0.2f);
		path.lineTo(x*0.65f,centerY);
		path.lineTo(x*0.4f,y*0.8f);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(((x+y)/2)*0.065f);
		p1.drawPath(path,paint);
		paint.setAlpha(250);
		paint.setColor(mainColor);
		paint.setStrokeWidth(((x+y)/2)*0.062f);
		p1.drawPath(path,paint);
	}
	
	public void drawAdd(Canvas p1){
		//p1.drawColor(Color.argb(115,256,256,256));
		
		p1.drawCircle(centerX,centerY,0.95f*minRXY,paint);
		paint.setColor(paddingColor);
		rectf.set(centerX-minRXY*0.2f,centerY-minRXY*0.8f,centerX+minRXY*0.2f,centerY+minRXY*0.8f);
		p1.drawRoundRect(rectf,minRXY*0.15f,minRXY*0.15f,paint);
		rectf.set(centerX-minRXY*0.8f,centerY-minRXY*0.2f,centerX+minRXY*0.8f,centerY+minRXY*0.2f);
		p1.drawRoundRect(rectf,minRXY*0.15f,minRXY*0.15f,paint);
		paint.setColor(Color.GRAY);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5.5f);
		p1.drawCircle(centerX,centerY,0.95f*minRXY,paint);
		paint.setStyle(Paint.Style.FILL);
	}
	public void drawDelete(Canvas p1){
		//p1.drawColor(Color.argb(115,0,256,256));
		
        p1.drawCircle(centerX,centerY,0.95f*minRXY,paint);
		paint.setColor(paddingColor);
		rectf.set(centerX-minRXY*0.8f,centerY-minRXY*0.2f,centerX+minRXY*0.8f,centerY+minRXY*0.2f);
		p1.drawRoundRect(rectf,minRXY*0.15f,minRXY*0.15f,paint);
		paint.setColor(Color.GRAY);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5.5f);
		p1.drawCircle(centerX,centerY,0.95f*minRXY,paint);
		paint.setStyle(Paint.Style.FILL);
	}
	public void drawPoint(Canvas p1){
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth((x+y)*0.02f/2);
		paint.setColor(paddingColor);
		rectf.set(x*0.1f,y*0.1f,x*0.9f,y*0.9f);
		p1.drawRect(rectf,paint);
		paint.setColor(mainColor+5);
		rectf.set(x*0.11f,y*0.11f,x*0.89f,y*0.89f);
		paint.setStyle(Paint.Style.FILL);
		p1.drawRect(rectf,paint);
		//paint.setStyle(Paint.Style.FILL);
		//paint.setTextAlign(Paint.Align.CENTER);
	    //paint.setTextSize((x+y)*0.28f/2);
		//p1.drawText("修改名称",centerX,(y-paint.ascent()-paint.descent())/2,paint);
	}
	private void drawBack(Canvas p1){
		float lr=0.95f*minRXY;
		float lx= (float) Math.cos(44.8)*lr;
		float ly=(float) Math.sin(44.8)*lr;
		
		paint.setColor(paddingColor);
		paint.setStyle(Paint.Style.FILL);
		p1.drawCircle(centerX,centerY,lr,paint);
		
		paint.setColor(mainColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(lr*0.1f);
		
		path.moveTo(centerX-lx,centerY-ly);
		path.lineTo(centerX+lx,centerY+ly);
		p1.drawPath(path,paint);
		path.moveTo(centerX+lx,centerY-ly);
		path.lineTo(centerX-lx,centerY+ly);
		p1.drawPath(path,paint);

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
	
	public void setMainColor(int p0){
		mainColor=p0;
	}
	public void setPaddingColor(int p0){
		paddingColor=p0;
	}
	
	
	@Override
	public boolean onTouch(View p1, MotionEvent p2)
	{
		// TODO: Implement this method
		int action=p2.getAction();
		switch(type){
		     case ADD:
			 case DELETE:
			 case POINT:
			 case BACK:
		         switch(action){
					case MotionEvent.ACTION_DOWN:
						mainColor=Color.GRAY;
					    paddingColor=Color.WHITE;
						break;
					case MotionEvent.ACTION_UP:
						mainColor=Color.WHITE;
					    paddingColor=Color.GRAY;
						break;
				}
				break;
		}
		invalidateSelf();
		return false;
	}

}
