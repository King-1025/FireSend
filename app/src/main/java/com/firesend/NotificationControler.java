package com.firesend;

import android.app.*;
import android.content.*;
import android.widget.*;
import android.graphics.*;
import android.provider.*;
import com.firesend.drawable.*;

public class NotificationControler 
{
	private Notification nf;
	//private NotificationManager nfm;
	private Context context;
	
	public NotificationControler(Context p0){
	    context=p0;
		//RemoteViews rv=new RemoteViews(p0.getPackageName(),R.layout.notification);
		//rv.setOnClickPendingIntent(R.id.notificationImageView1,PendingIntent.getActivity(p0, 0, new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 0));
        //rv.setOnClickPendingIntent(R.id.notificationImageView2,PendingIntent.getActivity(p0, 0, new Intent(p0,MainActivity.class), 0));
		PendingIntent pedingIntent=PendingIntent.getActivity(p0, 0, new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS),0);
		nf= new Notification.Builder(p0)
			.setTicker("辅助发送已经启动！")
			.setSmallIcon(R.drawable.logo1)
			//.setLargeIcon(btm)
		    //.setWhen(System.currentTimeMillis())
			/*.setStyle(new Notification.InboxStyle()
			 .setBigContentTitle("大标题")
			 .addLine("第一行")
			 .addLine("第二行")
			 .addLine("第三行")
			 .addLine("第四行")
			 .addLine("第五行"))	*/															 
			//.setStyle(new Notification.BigTextStyle())
			/*.setStyle(new Notification.BigPictureStyle()
			 .setBigContentTitle("大标题")
			 .setSummaryText("摘要"))*/
			.setContentTitle("辅助发送")
		    .setContentText("点击可在辅助功能中关闭当前服务")
			//.setSubText("点击关闭服务")
			//.setProgress(0,0,false)
			.setContentIntent(pedingIntent)//点击通知后跳转
			//.setDeleteIntent(pedingIntent)//删除通知后跳转
			//.setFullScreenIntent(pedingIntent,false)//发送通知后就跳转
			//.setNumber(0)
		    .build();
		//nf.bigContentView=rv;
		//nfm=(NotificationManager)p0.getSystemService(Context.NOTIFICATION_SERVICE);

	}
    
	public Notification getNotification(){
		return nf;
	}
	/*public NotificationManager getNotificationManager(){
		return nfm;
	}*/
	
}
