package com.firesend;

import android.content.*;
import android.util.*;
import java.io.*;
import java.util.*;
import org.xmlpull.v1.*;
import android.widget.*;
import android.app.*;

public class RegularManager
{
	private Context context;
	private DataSaver dataSaver;
	private ArrayList<String>sendText;
	private ArrayList<Integer>sendDelayTime;
    //TAG
	private final static String FIRST_TAG="regular";
	private final static String SECOND_TAG="item";
	private final static String THIRED_TAG_0="sendtext";
	private final static String THIRED_TAG_1="senddelaytime";
	//ATTRIBUTE
	private final static String ATTRIBUTE_USER="user";
	private final static String ATTRIBUTE_NAME="name";
	private final static String ATTRIBUTE_ID="id";
	//Example
	/*
	 <?xml version="1.0" encoding="utf-8"?>
	 <regular user="com.firsend" name="">
     <item id="">
     <sendtext></sendtext>
     <senddelaytime></senddelaytime>
     </item>
	 </regular>
	*/
	public RegularManager(Context p1){
		context=p1;
		dataSaver=(DataSaver)p1.getApplicationContext();
		sendText=dataSaver.obtainSendText();
		sendDelayTime=dataSaver.obtainSendDelayTime();
	}
	
	public boolean importXMLRegularByPullParser(String p1){
		if(p1==null){
			return false;
		}
		if(!p1.contains(dataSaver.defaultFileTag)){
			Toast.makeText(context,"文件后缀名不是"+dataSaver.defaultFileTag+"，不是规则文件！",1000).show();
			return false;
		}
		String path=p1;
		boolean flag=true;
		int event=XmlPullParser.END_DOCUMENT;
		InputStream is=null;
		try
		{
			 is = new FileInputStream(path);
		}
		catch (FileNotFoundException e)
		{return false;}
		XmlPullParser pullParser = Xml.newPullParser();
		String user="";
		int id=0;
		String text="";
		int time=0;
		
        try
		{
			pullParser.setInput(is, "utf-8");
			event = pullParser.getEventType();
	    while(flag)
		{
			   switch(event)
			 {
				   case XmlPullParser.START_DOCUMENT:
					 
					   break;
				   case XmlPullParser.START_TAG:
					   
					   if (FIRST_TAG.equals(pullParser.getName())) 
					   {
						  int attrCount=pullParser.getAttributeCount();
					   	  for(int i=0;i<attrCount;i++)
						  {    
							// Toast.makeText(context,"ck0",1000).show();
							 if(ATTRIBUTE_USER.equals(pullParser.getAttributeName(i)))
							 {
								user =String.valueOf(pullParser.getAttributeValue(i));
							 }
							 if(ATTRIBUTE_NAME.equals(pullParser.getAttributeName(i)))
							 {
								
								 dataSaver.setSendRegularName(String.valueOf(pullParser.getAttributeValue(i)));
						     }
							 
						  }
					   }
					   if(!user.equals(context.getPackageName()))
					   {
						   Toast.makeText(context,"包名不符，无法使用此规则文件！",1000).show();
						   return false;
					   }
					   if (SECOND_TAG.equals(pullParser.getName()))
					   {
						   id=0;  text="" ; time=0;
						   int attrCount=pullParser.getAttributeCount();
						   for(int i=0;i<attrCount;i++)
						   {    
							   if(ATTRIBUTE_ID.equals(pullParser.getAttributeName(i)))
							   {
								   id=Integer.valueOf(pullParser.getAttributeValue(i));
							   }
							  // Toast.makeText(context,""+pullParser.getName(),1000).show();
						   }
					   }
					   if(THIRED_TAG_0.equals(pullParser.getName()))
					   {
						   text = pullParser.nextText();
					   }
					   if(THIRED_TAG_1.equals(pullParser.getName()))
					   {
						   time=Integer.valueOf(pullParser.nextText());
					   }
					   break;
				   case XmlPullParser.END_TAG:
					   if (SECOND_TAG.equals(pullParser.getName()))
					   {
						   int size=sendText.size();
						   if(size>sendDelayTime.size())
						   {
							   size=sendDelayTime.size();
						   }
						   if(size==0)
						   {
							  sendText.add(text);
						      sendDelayTime.add(time);
						   }else{
							   if(id>=0&&id<size){
								  sendText.set(id,text);
								  sendDelayTime.set(id,time);
							   }else if(id>=size){
								  sendText.add(text);
								  sendDelayTime.add(time);
							   }
						   }
						   
						   Log.i("",text+":"+time);
					   }
					   break;
				   case XmlPullParser.END_DOCUMENT:
					   flag=false;
					   break;
				   
			 }
			 event = pullParser.next();
	    }
		return true;
		}
		catch (XmlPullParserException e)
		{
			Toast.makeText(context,"解析规则文件出错！",1000).show();
			return false;
		}
		catch (IOException e)
		{
			Toast.makeText(context,"读取规则文件出错！",1000).show();
			return false;
		}
	}
	
	public boolean exportXMLRegularByPullParser(String p1,String p2){
		if(p1==null)
		{
			return false;
		}
		File dir=new File(p1);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		File file = new File(p1+"/"+p2+dataSaver.defaultFileTag);
		if(!file.exists()){
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{return false;}
		}
		
		FileOutputStream fileOutputStream=null;
        try
		{
			 fileOutputStream = new FileOutputStream(file);
		
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		
		XmlSerializer xmlSerializer = Xml.newSerializer();
		int size=sendText.size();
		if(size>sendDelayTime.size())
		{
			size=sendDelayTime.size();
		}
		try{
		xmlSerializer.setOutput(fileOutputStream,"utf-8");
		
		xmlSerializer.startDocument("utf-8", true);
		//FIRST_TAG
		xmlSerializer.startTag(null,FIRST_TAG);
		xmlSerializer.attribute(null,ATTRIBUTE_USER,context.getPackageName());
		xmlSerializer.attribute(null,ATTRIBUTE_NAME,dataSaver.getSendRegularName());
		for(int i=0;i<size;i++)
		{
			//SECOND_TAG
			xmlSerializer.startTag(null,SECOND_TAG);
			xmlSerializer.attribute(null,ATTRIBUTE_ID,i+"");
			
			//THIRED_TAG_0
			xmlSerializer.startTag(null,THIRED_TAG_0);
			xmlSerializer.text(sendText.get(i));
			xmlSerializer.endTag(null,THIRED_TAG_0);
			//THIRED_TAG_1
			xmlSerializer.startTag(null,THIRED_TAG_1);
			xmlSerializer.text(sendDelayTime.get(i)+"");
			xmlSerializer.endTag(null,THIRED_TAG_1);
			
			xmlSerializer.endTag(null,SECOND_TAG);
		}
		xmlSerializer.endTag(null,FIRST_TAG);
		xmlSerializer.endDocument();
		
		fileOutputStream.flush();
		fileOutputStream.close();
		return true;
		}
		catch (IllegalArgumentException e)
		{return false;}
		catch (IllegalStateException e)
		{return false;}
		catch (IOException e)
		{return false;}
	}
	
}
