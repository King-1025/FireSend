package com.firesend;

import android.content.*;
import android.graphics.*;
import android.view.*;
import android.view.View.*;
import android.view.WindowManager.*;
import android.util.*;
import android.widget.*;
import android.view.accessibility.*;
import android.os.*;
import com.firesend.view.*;
import android.widget.AbsoluteLayout.*;
import com.firesend.drawable.*;
import android.provider.*;

public class FloatWControler implements OnLongClickListener,OnTouchListener,Controler
{
	private Context context;
	private FrameLayout mainView;
	private OnTouchListener ol;
	private WindowManager windowManager;
	private WindowManager.LayoutParams layoutParams;
	
	private int x;
	private int y;
	private int centerX;
	private int centerY;
	private float startTouchX;
	private float startTouchY;
	
	private LayoutInflater layoutInflater;
	private View smallFloatWindow;
	private StyleButton longShow;
	private View largeFloatWindow;
	private LinearLayout linearLayout;
	private StyleButton sendName;
	private StyleButton sendCount;
	private StyleButton editRegular;
	private StyleButton shutdown;
	private StyleButton reset;
	
	private boolean isShow=false;
	private boolean isMoveable=true;
	private boolean isStart=true;
	private boolean isReseted=true;
	private boolean isFirstGetTarget=true;

	private int count=0;
	private Target target;
	private Handler handlerControl;
	private CharSequence windowName="";
	
	private Handler handlerUI;
	private final static int UPDATE_WINDOW_NAME=0;
	private final static int UPDATE_SEND_COUNT=1;
	private final static int FLAG_CONTROL_START=2;
	private final static int WINDOW_SELF_MOVE=3;
	
	private int type_float_window;
	private final static int TYPE_FLOAT_WINDOW_SMALL=10;
	private final static int TYPE_FLOAT_WINDOW_LARGE=11;
	private final static int STYLE_MOVE_X=20;
	private final static int STYLE_MOVE_Y=21;
	
	public FloatWControler(Context p0){
		this(p0,null);
	}
	public FloatWControler(Context p0,WindowManager.LayoutParams p1){
		
	    context=p0;
		windowManager=(WindowManager)p0.getSystemService(Context.WINDOW_SERVICE);
		x=windowManager.getDefaultDisplay().getWidth();
	    y=windowManager.getDefaultDisplay().getHeight();
		centerX=x/2;
		centerY=y/2;
		if(p1!=null)
		{
		    layoutParams=p1;
		}else{
			layoutParams=new WindowManager.LayoutParams();
			layoutParams.type=WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW+3;
			layoutParams.format=PixelFormat.RGBA_8888;
			layoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			layoutParams.width=LayoutParams.WRAP_CONTENT;
			layoutParams.height=LayoutParams.WRAP_CONTENT;
			layoutParams.x=-centerX;
		}
		type_float_window=TYPE_FLOAT_WINDOW_SMALL;
		
		layoutInflater=LayoutInflater.from(p0);
		
		smallFloatWindow=layoutInflater.inflate(R.layout.small_float_window,null);
		longShow=(StyleButton) smallFloatWindow.findViewById(R.id.smallfloatwindowStyleButton1);
		
		largeFloatWindow=layoutInflater.inflate(R.layout.large_float_window,null);
		linearLayout=(LinearLayout) largeFloatWindow.findViewById(R.id.largefloatwindowLinearLayout1);
		sendName=(StyleButton) largeFloatWindow.findViewById(R.id.largefloatwindowStyleButton1);
		sendCount=(StyleButton) largeFloatWindow.findViewById(R.id.largefloatwindowStyleButton2);
		editRegular=(StyleButton) largeFloatWindow.findViewById(R.id.largefloatwindowStyleButton3);
		shutdown=(StyleButton) largeFloatWindow.findViewById(R.id.largefloatwindowStyleButton4);
		reset=(StyleButton) largeFloatWindow.findViewById(R.id.largefloatwindowStyleButton5);
		
		longShow.setOnTouchListener(this);
		editRegular.setOnTouchListener(this);
	    shutdown.setOnTouchListener(this);
		reset.setOnTouchListener(this);
		
		longShow.setOnLongClickListener(this);
		linearLayout.setOnLongClickListener(this);
		editRegular.setOnLongClickListener(this);
		shutdown.setOnLongClickListener(this);
		reset.setOnLongClickListener(this);
		
		handlerUI=new Handler(){
		  public void  handleMessage(Message msg){  
		     switch(msg.what){
				 case UPDATE_WINDOW_NAME:
					 sendName.setText("窗口名称:"+windowName);
				     break;
			     case UPDATE_SEND_COUNT:
					 if(!isReseted)//没有重置,就刷新次数
					 {
						 longShow.setText(""+count);
					     sendCount.setText("发送次数:"+count);
					 }
				     break;
			     case FLAG_CONTROL_START:
					 longShow.setText("启动");
				     break;
				 case WINDOW_SELF_MOVE:
					 floatWindowMoveStyle(mainView,STYLE_MOVE_Y,centerY,0.1f,0.01f);
				     break;
		     }
		   }
	    };
		mainView=new FrameLayout(p0);
		windowManager.addView(mainView,layoutParams);
	    ol=new OnTouchListener(){

			@Override
			public boolean onTouch(View p1, MotionEvent p2)
			{
				// TODO: Implement this method
				if(!isMoveable){return false;}
				switch(p2.getAction()){
					case MotionEvent.ACTION_DOWN:
						handlerUI.removeMessages(WINDOW_SELF_MOVE);
						startTouchX=  p2.getRawX()-layoutParams.x; 
						startTouchY=  p2.getRawY()-layoutParams.y;
						break;
					case MotionEvent.ACTION_MOVE:
						layoutParams.x= (int)(p2.getRawX()-startTouchX);
						layoutParams.y= (int)(p2.getRawY()-startTouchY);
						windowManager.updateViewLayout(mainView, layoutParams); 
						break;
					case MotionEvent.ACTION_UP:
						handlerUI.sendEmptyMessageDelayed(WINDOW_SELF_MOVE,1000);
						break;
				}
				return false;
			}
		};
	mainView.setOnTouchListener(ol);
	}
	
	@Override
	public boolean onTouch(View p1, MotionEvent p2)
	{
		// TODO: Implement this method
		int id=p1.getId();
		int action=p2.getAction();
		switch(id)
		{
			/*
			case R.id.smallfloatwindowLinearLayout1:
			    if(!isMoveable){return false;}
				
				switch(action){
					case MotionEvent.ACTION_DOWN:
						handlerUI.removeMessages(WINDOW_SELF_MOVE);
						startTouchX=  p2.getRawX()-layoutParams.x; 
					    startTouchY=  p2.getRawY()-layoutParams.y;
						break;
					case MotionEvent.ACTION_MOVE:
					    layoutParams.x= (int)(p2.getRawX()-startTouchX);
						layoutParams.y= (int)(p2.getRawY()-startTouchY);
					    windowManager.updateViewLayout(smallFloatWindow, layoutParams); 
				        break;
					case MotionEvent.ACTION_UP:
						handlerUI.sendEmptyMessageDelayed(WINDOW_SELF_MOVE,1000);
						break;
				}
				
			break;
			*/
			case R.id.smallfloatwindowStyleButton1:
				switch(action){
					case MotionEvent.ACTION_DOWN:
					    longShow.setBgMaskFilter(false);
	                    break;
					case MotionEvent.ACTION_UP:
						longShow.setBgMaskFilter(true);
						if(isStart)
						{  
							handlerControl.sendEmptyMessage(FireSendService.FLAG_FIRE_START);
							if(isReseted)
							{isReseted=false;}
						}else
						{
							handlerControl.sendEmptyMessage(FireSendService.FLAG_FIRE_STOP);
							
						}
						break;
				}
                longShow.invalidate();
			
			break;
			
			case R.id.largefloatwindowStyleButton3:
				switch(action){
					case MotionEvent.ACTION_DOWN:
					    editRegular.setBgMaskFilter(false);
	                    break;
					case MotionEvent.ACTION_UP:
						editRegular.setBgMaskFilter(true);
						Intent intent=new Intent(context,MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
						break;
				}
				editRegular.invalidate();

			break;
				
			case R.id.largefloatwindowStyleButton4:
				switch(action){
					case MotionEvent.ACTION_DOWN:
					    shutdown.setBgMaskFilter(false);
	                    break;
					case MotionEvent.ACTION_UP:
						shutdown.setBgMaskFilter(true);
						Intent intent=new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
						break;
				}
				shutdown.invalidate();
				
			break;
			
			case R.id.largefloatwindowStyleButton5:
				switch(action){
					case MotionEvent.ACTION_DOWN:
					    reset.setBgMaskFilter(false);
	                    break;
					case MotionEvent.ACTION_UP:
						reset.setBgMaskFilter(true);
						if(!isReseted)
						{ 
						  isReseted=true;
						  handlerControl.sendEmptyMessage(FireSendService.FLAG_SERVICE_RESET);
				          sendName.setText("窗口名称");
						  sendCount.setText("发送次数");
						}else{
						 // Toast.makeText(context,"isReset:"+isReseted,1000).show();
						}
						break;
				}
				reset.invalidate();
				
			break;
		}
		
		return false;
	}
	
	@Override
	public boolean onLongClick(View p1)
	{
		// TODO: Implement this method
		int id=p1.getId();
		switch(id){
			case R.id.smallfloatwindowStyleButton1:
						longShow.setBgMaskFilter(true);
				        if(type_float_window==TYPE_FLOAT_WINDOW_SMALL)
					    {
							//windowManager.removeView(smallFloatWindow);
							mainView.setPadding(40,10,40,10);
							mainView.setBackgroundColor(Color.GRAY);
							mainView.removeAllViews();
							LayoutParams lp=new LayoutParams();
							lp.width=LayoutParams.MATCH_PARENT;
							lp.height=LayoutParams.MATCH_PARENT;
							mainView.addView(largeFloatWindow,lp);
							layoutParams.width=centerX;
							layoutParams.x=0;
							windowManager.updateViewLayout(mainView,layoutParams);
						    type_float_window=TYPE_FLOAT_WINDOW_LARGE;
						}
						break;
			case R.id.largefloatwindowLinearLayout1:
				        if(type_float_window==TYPE_FLOAT_WINDOW_LARGE)
				        {
					      // windowManager.removeView(largeFloatWindow);
						   mainView.setPadding(10,40,10,40);
						   mainView.setBackgroundColor(0);
						   mainView.removeAllViews();
						   mainView.addView(smallFloatWindow);
						   layoutParams.width=layoutParams.WRAP_CONTENT;
						   layoutParams.x=(int)-(centerX*0.21f);
					       windowManager.updateViewLayout(mainView,layoutParams);
					       type_float_window=TYPE_FLOAT_WINDOW_SMALL;
						   handlerUI.sendEmptyMessage(WINDOW_SELF_MOVE);
				        }
				        break;
		}
		return false;
	}
	
	@Override
	public void notifyHandlerControl(Handler handlerControl)
	{
		// TODO: Implement this method
		this.handlerControl=handlerControl;
	}

	
	@Override
	public void notifyTargetGet(Target target)
	{
		// TODO: Implement this method
        if(target==null){
			return;
		}
		this.target=target;
		String name=target.getName();
		if(!windowName.equals(name))
		{
			windowName=name;
		    handlerUI.sendEmptyMessage(UPDATE_WINDOW_NAME);
		}
		if(isFirstGetTarget)
		{
			isFirstGetTarget=false;
			handlerUI.sendEmptyMessage(FLAG_CONTROL_START);
		}
	}
	
	@Override
	public void notifySendCountChanged(int sendCount)
	{
		// TODO: Implement this method
		if(count!=sendCount)
		{
			count=sendCount;
			handlerUI.sendEmptyMessage(UPDATE_SEND_COUNT);
		}

	}

	@Override
	public void notifyIsSending(boolean isSending)
	{
		// TODO: Implement this method
		
		isStart=!isSending;
		if(isStart)
		{
			handlerUI.sendEmptyMessage(FLAG_CONTROL_START);
		}
	}
	
    public void show(){
		if(!isShow)
		{
			switch(type_float_window)
			{
				case TYPE_FLOAT_WINDOW_SMALL:
					mainView.setPadding(10,40,10,40);
					mainView.setBackgroundColor(0);
					mainView.removeAllViews();
					mainView.addView(smallFloatWindow);
					break;
				case TYPE_FLOAT_WINDOW_LARGE:
					mainView.setPadding(40,10,40,10);
					mainView.setBackgroundColor(Color.GRAY);
					mainView.removeAllViews();
					mainView.addView(largeFloatWindow);
					break;
		   }
		   isShow=true;
		}
	}
	public void hide(){
		if(isShow)
		{
			windowManager.removeView(mainView);
			/*switch(type_float_window)
			{
				case TYPE_FLOAT_WINDOW_SMALL:
					windowManager.removeView(smallFloatWindow);
				    break;
				case TYPE_FLOAT_WINDOW_LARGE:
					windowManager.removeView(largeFloatWindow);
				    break;
			}*/
		    isShow=false;  
		}
	}
	
	public void setIsMoveable(boolean p0){
		isMoveable=p0;
	}
	
	private void floatWindowMoveStyle(View window,int style,int s,float f,float v){
		
		switch(style)
		{
			case STYLE_MOVE_X:
				
				if(layoutParams.x>s*f)
				{
					if(layoutParams.x>=s)
					{
						handlerUI.removeMessages(WINDOW_SELF_MOVE);
						layoutParams.x=s;
						windowManager.updateViewLayout(window, layoutParams); 
					}else{
						layoutParams.x+=s*v;
						windowManager.updateViewLayout(window, layoutParams); 
						handlerUI.sendEmptyMessage(WINDOW_SELF_MOVE);
					}
				}else if(layoutParams.x<-s*f){
					if(layoutParams.x<=-s)
					{
						handlerUI.removeMessages(WINDOW_SELF_MOVE);
						layoutParams.x=-s;
						windowManager.updateViewLayout(window, layoutParams); 
					}else{
						layoutParams.x-=s*v;
						windowManager.updateViewLayout(window, layoutParams); 
						handlerUI.sendEmptyMessage(WINDOW_SELF_MOVE);
					}
				}
				break;
			case STYLE_MOVE_Y:
				if(layoutParams.y>s*f)
				{
					if(layoutParams.y>=s)
					{
						handlerUI.removeMessages(WINDOW_SELF_MOVE);
						layoutParams.y=s;
						windowManager.updateViewLayout(window, layoutParams); 
					}else{
						layoutParams.y+=s*v;
						windowManager.updateViewLayout(window, layoutParams); 
						handlerUI.sendEmptyMessage(WINDOW_SELF_MOVE);
					}
				}else if(layoutParams.y<-s*f){
					if(layoutParams.y<=-s)
					{
						handlerUI.removeMessages(WINDOW_SELF_MOVE);
						layoutParams.y=-s;
						windowManager.updateViewLayout(window, layoutParams); 
					}else{
						layoutParams.y-=s*v;
						windowManager.updateViewLayout(window, layoutParams); 
						handlerUI.sendEmptyMessage(WINDOW_SELF_MOVE);
					}
				}
				break;
		}

	}
}
