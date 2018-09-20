package com.firesend;
import android.content.*;
import android.util.*;
import android.view.accessibility.*;
import android.widget.*;

public class ViewScanner
{
	private Context context;
	//private String log;
	public ViewScanner(Context p0){
		context=p0;
	}
	
	 public void scan(AccessibilityNodeInfo p0,AccessibilityNodeInfo p1){
	 Log.i("","①当前线程:"+Thread.currentThread());
	 //log="①当前线程:"+Thread.currentThread()+"\n";
	 //p0事件源节点
	 if(p0!=null)
	 {
	 Log.i("","②当前事件源完整信息:"+p0);
		// log+="②当前事件源完整信息:"+p0+"\n";
	 Log.i("","---开始记录事件源元素---");
	    // log+="---开始记录事件源元素---"+"\n";
	 Log.i(" ","事件源主节点:"+p0.getClassName()+"文本:<"+p0.getText()+"> 内容描述:<"+p0.getContentDescription()+"> 点击:"+p0.isClickable()+"长按:"+p0.isLongClickable()+"可见性:"+p0.isVisibleToUser()+"窗口ID:"+p0.getWindowId());	 
	  //   log+="事件源主节点:"+p0.getClassName()+"文本:<"+p0.getText()+"> 内容描述:<"+p0.getContentDescription()+"> 点击:"+p0.isClickable()+"长按:"+p0.isLongClickable()+"可见性:"+p0.isVisibleToUser()+"窗口ID:"+p0.getWindowId()+"\n";
	 printInfo(p0,0);
	 Log.i("","---该事件源记录完毕---");
	    // log+="---该事件源记录完毕---"+"\n";
	 }
	 else
	 {
	 Log.i("","无法获取事件源节点");
		// log+="无法获取事件源节点";
	// Toast.makeText(context,"无法获取事件源节点",1000).show();
	 //Log.i("",log);
	 return;
	 }
	 //p1窗口节点
	 if(p1!=null)
	 {   
	 Log.i("","③当前窗体节点完整信息:"+p1);
		// log+="③当前窗体节点完整信息:"+p1+"\n";
	 Log.i("","+++开始记录该窗体元素+++");
		// log+="+++开始记录该窗体元素+++"+"\n";
	 Log.i(" ","窗体主节点:"+p1.getClassName()+"文本:<"+p1.getText()+"> 内容描述:<"+p1.getContentDescription()+"> 点击:"+p1.isClickable()+"长按:"+p1.isLongClickable()+"可见性:"+p1.isVisibleToUser()+"窗口ID:"+p1.getWindowId());	 
	    // log+="窗体主节点:"+p1.getClassName()+"文本:<"+p1.getText()+"> 内容描述:<"+p1.getContentDescription()+"> 点击:"+p1.isClickable()+"长按:"+p1.isLongClickable()+"可见性:"+p1.isVisibleToUser()+"窗口ID:"+p1.getWindowId()+"\n";
	printInfo(p1,0);
	Log.i("","+++该窗体元素记录完毕+++");
	  //   log+="+++该窗体元素记录完毕+++"+"\n";
	// Toast.makeText(context,"窗体节点获取成功",1000).show();
	 }
	 else 
	 {   
	Log.i("","无法获取窗体节点");
		// log+="无法获取窗体节点"+"\n";
	//Toast.makeText(context,"无法获取窗体节点",1000).show();
	 }
	 //Log.i("",log);
	 return ;
	 }
	 //递归输出
	 public void printInfo(AccessibilityNodeInfo node,int tap) {
	 if(node==null){return;}
	 int count=node.getChildCount();
	 if (count!=0)
	 {   String str="";
	 String rts="  |";
	 for(int i=0;i<tap;i++)
	 str=str.concat(rts);
	 AccessibilityNodeInfo rch=null;
	 Log.i(" ",str+"{"); 
		 //log+=str+"{"+"\n";
	 for(int i=0;i<count;i++)
	 { 
	 rch=node.getChild(i);
	 Log.i(" ",str+"  "+i+"-"+rch.getClassName()+"文本:<"+rch.getText()+"> 窗口描述:<"+rch.getContentDescription()+"> 输入类型"+rch.getInputType()+" 点击:"+rch.isClickable()+" 长按:"+rch.isLongClickable()+" 可见性:"+rch.isVisibleToUser() +"控件ID:"+rch.getViewIdResourceName()+"包名:"+rch.getPackageName()+"窗口ID:"+rch.getWindowId());	 
		// log+=str+"  "+i+"-"+rch.getClassName()+"文本:<"+rch.getText()+"> 窗口描述:<"+rch.getContentDescription()+"> 输入类型"+rch.getInputType()+" 点击:"+rch.isClickable()+" 长按:"+rch.isLongClickable()+" 可见性:"+rch.isVisibleToUser() +"控件ID:"+rch.getViewIdResourceName()+"包名:"+rch.getPackageName()+"窗口ID:"+rch.getWindowId()+"\n";
	 //Log.i("",""+rch);//打印当前扫描节点的完整信息
	 printInfo(rch,tap+1);
	 }
	 Log.i(" ",str+"}");
		// log+=str+"}"+"\n";
	 }
	 }
	
}
