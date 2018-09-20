package com.firesend;

import android.content.*;
import android.util.*;
import android.view.accessibility.*;
import java.util.*;

public class WindowSearcher
{
	private Context context;
	private Target target;
	private String window_name;
	private final static String TAG="WindowSearcher";
	public final static int MODE_QQ=0x100;
	public final static int MODE_WX=0x101;
	public final static int MODE_HY=0x102;
	public final static String NOT_TARGET_WINDOW="";
	
	public WindowSearcher(Context context){
		this.context=context;
	}
	
    public String getWindowName(AccessibilityNodeInfo node,int mode){
		window_name=NOT_TARGET_WINDOW;
		if(node==null){
			Log.w(TAG,"node is "+node);
		}else{
		   switch(mode){
			  case MODE_QQ:
				 mode_0(node);
				 break;
			  case MODE_HY:
                 mode_1(node);
				 break;
			  case MODE_WX:
                 mode_2(node);
				 break;
           }
		}
		return window_name;
	}
	
	public Target getTarget(AccessibilityNodeInfo where,int mode){
		target=null;//必须重置目标
		if(where==null){
			Log.w(TAG,"where is "+where);
		}else{
		   switch(mode){
			  case MODE_QQ:
			  case MODE_HY:
				  if(!see_0(where)){
					  Log.i(TAG,"not found target.");
				  }
				 break;
			  case MODE_WX:
				   if(!see_1(where,true)){
					   Log.i(TAG,"not found target.");
				   }
			     break;
		    
		   }
		}
		    return target;
	}
	//QQ
	private boolean mode_0(AccessibilityNodeInfo node){
	    if(node!=null)
	    {
		    AccessibilityNodeInfo an10=node.getChild(node.getChildCount()-1);
		    if(an10!=null)
		    {
				if(an10.getChildCount()>2)
				{
					an10=an10.getChild(1);
					if(an10!=null)
					{
						if(an10.getClassName().equals("android.widget.TextView"))
						{
							window_name=an10.getText().toString();
							Log.i(TAG,"window_name:"+window_name);
							return true;
						}
						if(an10.getChildCount()>1)
						{
							an10=an10.getChild(0);
							if(an10.getClassName().equals("android.widget.TextView"))
							{
								window_name=an10.getText().toString();
								Log.i(TAG,"window_name:"+window_name);
								return true;
							}
						}
					}
				}
			}
			if(node.getChildCount()>2)
			{
				CharSequence str=node.getChild(0).getContentDescription();
				if(str!=null)
				{
					if(str.equals("返回消息界面")||str.equals("返回"))
					{
						AccessibilityNodeInfo point=node.getChild(1);
						window_name=point.getText().toString();
						if(window_name==null&&point.getChildCount()>0)
						{
							window_name=point.getChild(0).getText().toString();
							Log.i(TAG,"window_name:"+window_name);
							return true;
						}
					}
				}
			}

		}
		unrecognizable(node);
		return false;
	}
	//HY
	private boolean mode_1(AccessibilityNodeInfo node){
		if(node!=null)
		{
			if(node.getChildCount()>4)
			{
				AccessibilityNodeInfo a21=node.getChild(0);
				if(a21.getChildCount()>3)
				{
						AccessibilityNodeInfo a33=a21.getChild(2);
						boolean t0=a33.getClassName().equals("android.widget.EditText");
						boolean t1=a33.getText().equals("说点什么吧~");
						boolean t2=a21.getChild(3).getClassName().equals("android.widget.Button");
	                   // Log.i(TAG,"t0:"+t0+" t1:"+t1+" t2:"+t2);
					    if(t0&&(t1||t2))
						{
							if(t1){
								ClipboardManager cm=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
								cm.setText(".");
								boolean step0=a33.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
							    boolean step1= a33.performAction(AccessibilityNodeInfo.ACTION_PASTE);
								//Log.i(TAG,"step0:"+step0+" step1:"+step1);
								window_name=new Date(System.currentTimeMillis()).toLocaleString();
								Log.i(TAG,"window_name:"+window_name);
								return true;
							}
						}
					}
			}
			 
		}
		unrecognizable(node);
		return false;
	}
	//WX
	private boolean mode_2(AccessibilityNodeInfo node){
		if(node!=null){
			if(see_1(node,false)){
				window_name=node.getContentDescription().toString();
				return true;
			}
		}
		unrecognizable(node);
		return false;
	}
	private void unrecognizable(AccessibilityNodeInfo node){
		Log.i(TAG,"无法识别当前窗口:"+node);
		if(node!=null){
		     new ViewScanner(context).scan(node,null);
		}else{
			Log.i(TAG,"scan() faild! node is "+node);
		}
	}
	//递归搜索目标see_0,see_1
	private boolean see_0(AccessibilityNodeInfo node){
		int scale=node.getChildCount();
		for(int i=scale-1;i>=0;i--)
		{
			AccessibilityNodeInfo t0=node.getChild(i);
			if(t0.getClassName().equals("android.widget.EditText")&&(i+1)<scale)
		    {
				AccessibilityNodeInfo t1=node.getChild(i+1);
				if(t1.getClassName().equals("android.widget.Button"))
				{
					target=new Target();
                    target.setInput(t0);
					target.setClick(t1);
					Log.i(TAG,"capture target!");
				    return true;
				}
			}
			if(see_0(t0))
			{ return true;}
		}
		return false;
	}
	private boolean see_1(AccessibilityNodeInfo node,boolean is){
		int scale=node.getChildCount();
		for(int i=scale-1;i>=0;i--)
		{
			AccessibilityNodeInfo t0=node.getChild(i);
			if(t0.getClassName().equals("android.widget.ImageButton")&&
			   "切换到按住说话".equals(t0.getContentDescription()))
		    {
				//不安全判断
				t0=node.getChild(i+1).getChild(0);
				if(t0.getClassName().equals("android.widget.EditText"))
				{
				   AccessibilityNodeInfo t1=null;
				   if(!is){
					  ClipboardManager cm=(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
					  cm.setText(".");
					  int count=0;
					  do{
						 if(++count>3){
						     Log.i(TAG,"目标窗口异常!");
							 return false;
					     }
				         boolean step0=t0.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
					     boolean step1= t0.performAction(AccessibilityNodeInfo.ACTION_PASTE);
					     t1=node.getChild(i+3);
						 //Log.i(TAG,"step0:"+step0+" step1:"+step1);
					  }while(t1!=null&&t1.getClassName().equals("android.widget.Button"));
				   }else{
					      t1=node.getChild(i+3);
					      target=new Target();
					      target.setInput(t0);
					      target.setClick(t1);
					      Log.i(TAG,"capture target!");
				   }
				   return true;
				}
			}
			if(see_1(t0,is))
			{ return true;}
		}
		return false;
	}
}
   
   
