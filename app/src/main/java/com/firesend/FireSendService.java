package com.firesend;

import android.accessibilityservice.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.accessibility.*;
import android.widget.*;
import java.util.zip.*;
import android.text.style.*;
import android.view.*;
import android.view.View.*;
import android.provider.*;
import java.util.*;
import java.lang.annotation.*;


public class FireSendService extends AccessibilityService
{

	private Context context;

	private String flag;
	private Target target;
	private int current_mode;
	private WindowSearcher windowSearcher;
    
	//private boolean diff;
	//private final static String OBSCUREAD_TEXT=".";//混淆字符串
	
	//private int EvCount;
	//private String EvType;
	//private ViewScanner viewscanner;
	
	private boolean isSending=false;
	private final static int NFID=1;
	private Controler controler;
	private FloatWControler floatwcontroler;
	private NotificationControler notificationcontroler;
	
	private Handler handler;
	private HandlerThread handlerthread;
	public final static int FLAG_FIRE_START=0x00;
	public final static int FLAG_FIRE_SENDING=0x01;
	public final static int FLAG_FIRE_STOP=0x02;
	public final static int FLAG_SERVICE_RESET=0x03;
	private final static String HANDLER_THREAD_FLAG="FireSendThread";
	private final static String TAG="FireSendService";
	
	private DataSaver dataSaver;
	private int count;
	private List sendText;
	private List sendDelayTime;
	private ClipboardManager clipboard;
	private int index;
	private String text;
	private int time;
	private final static int HY_MAX_SPEED=5;
	private final static float HY_CHECK_TIME=150f;
	private final static int HY_LIMITE_TIME=21;
	@Override
	protected void onServiceConnected()
	{
		// TODO: Implement this method
		super.onServiceConnected();
		context=getApplicationContext();
		floatwcontroler=new FloatWControler(context);
	    notificationcontroler=new NotificationControler(context);
		controler=floatwcontroler;
		windowSearcher=new WindowSearcher(this);
		//viewscanner=new ViewScanner(context);
		
		handlerthread=new HandlerThread(HANDLER_THREAD_FLAG);
		handlerthread.start();
		handler=new Handler(handlerthread.getLooper()){
			public void handleMessage(Message msg){
				switch(msg.what)
				{
					case FLAG_FIRE_START:
					  if(searchTarget())
					  {
						 if(sendText.size()!=0&&sendDelayTime.size()!=0)
						 {
							 firesend();
							 isSending=true;
							 controler.notifyIsSending(isSending);
						 }else
						 { 
							 Intent intent=new Intent(context,MainActivity.class);
							 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							 startActivity(intent);
							 Toast.makeText(context,"没有发送规则！",1000).show();
						 }
					  }
					  break;
					case FLAG_FIRE_SENDING:
				      firesend();
					  break;
					case FLAG_FIRE_STOP:
					  if(isSending)
					  {  
						 handler.removeMessages(FLAG_FIRE_SENDING);
					     isSending=false;
					     controler.notifyIsSending(isSending);
					  }
					  break;
					case FLAG_SERVICE_RESET:
					  handler.sendEmptyMessage(FLAG_FIRE_STOP);
					  count=0;			
					  break;
	            }
			// Log.i(TAG,"HandlerThread id:"+Thread.currentThread().getId()+" state:"+Thread.currentThread().getState());
			}
		 };
		controler.notifyHandlerControl(handler);
		 
		dataSaver=(DataSaver)context;
	    sendText=dataSaver.obtainSendText();
		sendDelayTime=dataSaver.obtainSendDelayTime();
		clipboard=(ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
		
		floatwcontroler.show();
		startForeground(NFID,notificationcontroler.getNotification());
	}

	@Override
	public void onInterrupt()
	{
		// TODO: Implement this method
	    finish();
		Log.i(TAG,"service is interrupt.");
	}

	@Override
	public boolean onUnbind(Intent intent)
	{
		// TODO: Implement this method
		finish();
		return super.onUnbind(intent);
	}
	
	private boolean searchTarget(){
		 AccessibilityNodeInfo window=getRootInActiveWindow();
		 updateMode(window);
		 String window_name=windowSearcher.getWindowName(window,current_mode);
	     if(!(WindowSearcher.NOT_TARGET_WINDOW.equals(window_name)))
		 {
			int window_id=window.getWindowId();
			if(target!=null){
				if(target.isValid(window_id,window_name)){
					 Toast.makeText(context,"目标重新出现!",1000).show();
					 Log.i(TAG,"目标重新出现");
					 return true;//目标重用
				}
			}
			Target temp_target=windowSearcher.getTarget(getRootInActiveWindow(),current_mode);
			if(temp_target!=null){
				target=temp_target;
				target.setId(window_id);
				target.setName(window_name);//目标确定
				if(!window_name.equals(flag)){
				    count=0;//不是相同窗口名称,重置count
					flag=target.getName();
				}
			    controler.notifyTargetGet(target);
			    Toast.makeText(context,"目标刷新成功",100).show();
			    Log.i(TAG,"target id:"+target.getId()+" name:"+target.getName()+" window_name:"+window_name);
				return true;
			}else{
				 Log.w(TAG,"锁定目标窗口，但无法获取目标.window_name:"+window_name);
				 Toast.makeText(context,"锁定目标窗口，但无法获取目标",1000).show();
			}
		 }else
		 {
			 if(target!=null)
			 {
				 Toast.makeText(context,"不是控制窗口，启动失败!",1000).show();
			 }else
			 {
				 Toast.makeText(context,"不是目标窗口，激活失败!",1000).show();
			 }
		 }
		 return false;
	}

	//input:输入框，click:发送按钮。
	private void firesend(){	
         if(target!=null){
		    AccessibilityNodeInfo input=target.getInput();
		    AccessibilityNodeInfo click=target.getClick();
			prepareSend(count,sendText,sendDelayTime);//处理剪切板，准备发送
			
			if(input.performAction(AccessibilityNodeInfo.ACTION_FOCUS))
			{Toast.makeText(context,"锁定目标!",1000).show();}
		    
			//input.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
			 
		    boolean step1=input.performAction(AccessibilityNodeInfo.ACTION_PASTE);
		    boolean step2= click.performAction(AccessibilityNodeInfo.ACTION_CLICK);
		    if(step1&&step2)
			{ 
				count++;
				controler.notifySendCountChanged(count);//更新发送次数
			    if(isDelaySend()){
				  //延时发送
				  handler.sendEmptyMessageDelayed(FLAG_FIRE_SENDING,HY_LIMITE_TIME*1000);
				//  Toast.makeText(context,"检测到虎牙限制，已延时处理发送！",1000).show();
				}else{
		          //正常发送
			      handler.sendEmptyMessageDelayed(FLAG_FIRE_SENDING,time);
				}
				return;//直接返回
		    }
		    if(current_mode==WindowSearcher.MODE_QQ)
			{target=null;}
			//窗口意外改变
		    Toast.makeText(context,"强制离开控制窗口!",1000).show();
		}else
	    {
	       Toast.makeText(context,"目标丢失!",1000).show();//目标丢失
		}
		handler.sendEmptyMessage(FLAG_FIRE_STOP);
	}

	private void prepareSend(int p0,List p1,List p2){
		   if(p1==null||p2==null){return;}
		   index=p0%p2.size();
		   text=(String) p1.get(index);
		   time=p2.get(index);
		   /*if(current_mode==WindowSearcher.MODE_HY){
			   Toast.makeText(this,""+diff,1000).show();
		       if(diff){
				  text+=OBSCUREAD_TEXT;
			   }
			   diff=!diff;
		   }*/
		   clipboard.setText(text);
		   //clipboard.setPrimaryClip(ClipData.new PlainText("text",text));
	}


	private void updateMode(AccessibilityNodeInfo node){
            if(node!=null){
				String package_name=node.getPackageName().toString();
				Log.i(TAG,"package_name:"+package_name);
				if("com.tencent.mobileqq".equals(package_name)){
					current_mode=WindowSearcher.MODE_QQ;
				}else if("com.duowan.kiwi".equals(package_name)){
					current_mode=WindowSearcher.MODE_HY;
				}else if("com.tencent.mm".equals(package_name)){
					current_mode=WindowSearcher.MODE_WX;
				}else{
					current_mode=WindowSearcher.MODE_QQ;//默认QQ模式
				}
				Log.i(TAG,"current_mode:"+current_mode);
			}
	}
	
	public void finish(){
		   stopForeground(true);
		   floatwcontroler.hide();
		   handlerthread.quit();
	}
	
	private boolean isDelaySend(){
		boolean is=false;
		if(current_mode==WindowSearcher.MODE_HY){
		  if(count%HY_MAX_SPEED==0){
			  int st=0;
			  for(int i=0;i<HY_MAX_SPEED;i++,index--){
				  if(index<0){index=sendDelayTime.size()-1;}
				  st+=(int)sendDelayTime.get(index);
			  }
			  if(st<HY_CHECK_TIME*1000){
				  is=true;
			  }
		  }
		}
		return is;
	}
	@Override
	public void onAccessibilityEvent(AccessibilityEvent p1)
	{
		// TODO: Implement this method
		
		/*EvType=p1.eventTypeToString(p1.getEventType());
		 Log.i("",(++EvCount)+".事件类型:"+EvType);
		 AccessibilityNodeInfo point=p1.getSource();
		 AccessibilityNodeInfo root=getRootInActiveWindow();
		 if(root==null)
		 {
		 Toast.makeText(context,"root为空！",1000).show();
		 return;
		 }
		 viewscanner.scan(point,root);
		 Toast.makeText(context,""+EvType,1000).show();*/
	}
	
}

