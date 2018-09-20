package com.firesend;
import android.app.*;
import android.os.*;
import java.util.*;

public class DataSaver extends Application
{
	private String sendRegularName;
	private ArrayList<String>sendText;
	private ArrayList<Integer>sendDelayTime;
	private String sendRegularFileSavePath;

	public int defaultAddSendRegularMaxCount=100;
    public String defaultSendRegularName="FirstRegular";
	public String defaultFileTag=".fsr";

	public void setSendRegularName(String sendRegularName)
	{
		this.sendRegularName = sendRegularName;
	}

	public String getSendRegularName()
	{
		if(sendRegularName==null)
	    {
			sendRegularName=defaultSendRegularName;
		}
		return sendRegularName;
	}
	
	public ArrayList<String> obtainSendText(){
		if(sendText==null)
		{sendText=new ArrayList<String>();}
		return sendText;
	}
	public ArrayList<Integer> obtainSendDelayTime(){
		if(sendDelayTime==null)
		{sendDelayTime=new ArrayList<Integer>();}
		return sendDelayTime;
	}
	
	public void setSendRegularFileSavePath(String sendRegularFileSavePath)
	{
		this.sendRegularFileSavePath = sendRegularFileSavePath;
	}

	public String getSendRegularFileSavePath()
	{
		if(sendRegularFileSavePath==null)
		{
			sendRegularFileSavePath=searchCanUsePath()+"/"+"FireSend";
		}
		return sendRegularFileSavePath;
	}
	
	private String searchCanUsePath(){
		String path=null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
		if(sdCardExist)   
		{                               
			path= Environment.getExternalStorageDirectory().getPath();//获取根目录
		}   
		return path;
	}
}
