package com.firesend;
import android.app.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.io.*;

import android.view.View.OnClickListener;

public class FilesBrowser implements OnItemClickListener,OnClickListener
{

	private Context context;
	private AlertDialog showDialog;
	private View content;
	private TextView title;
	private TextView tv;
	private ListView lv;
	private Button left;
	private Button right;
	
	private FilesAdapter filesAdapter;
	private File[] listFiles;
	private File file;
	private DataSaver dataSaver;
	private FileNotify fileNotify;
	
	public FilesBrowser(Context p1){
		context=p1;
		dataSaver=(DataSaver)p1.getApplicationContext();
		
		content=LayoutInflater.from(p1).inflate(R.layout.files_show,null);
		
		title=(TextView) content.findViewById(R.id.filesshowTextView1);
		
		tv=(TextView) content.findViewById(R.id.filesshowTextView2);
		
		lv=(ListView) content.findViewById(R.id.filesshowListView1);
		showDialog=new AlertDialog.Builder(p1).setView(content).create();
		//showDialog.setCancelable(false);//返回键也不能关闭
		showDialog.setCanceledOnTouchOutside(false);//点击外部区域不能关闭对话框
		
		filesAdapter=new FilesAdapter(p1);
		lv.setAdapter(filesAdapter);
		
		lv.setOnItemClickListener(this);
		
		left=(Button) content.findViewById(R.id.filesshowButton1);
		right=(Button) content.findViewById(R.id.filesshowButton2);
		
		left.setOnClickListener(this);
		right.setOnClickListener(this);
        
		left.setText("上层目录");
	}
    
	@Override
	public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
	{
		// TODO: Implement this method
		int index=p3;
		if(index>listFiles.length||index<0)
		{
			Toast.makeText(context,"指针错误！index:"+index+" p3:"+p3+" p4:"+p4,1000).show();
		    return;
		}
	    File f=listFiles[index];
		if(f!=null)
		{
			if(f.isDirectory())
		   {   
		       if(fileNotify!=null)
			   {
				   fileNotify.notifyOnItemClickIsDirectory(f);
			   }
		       if(f.canRead())
			   {
			    file=f;
			    listFiles=sort(file.listFiles());
			    filesAdapter.setFiles(listFiles).notifyDataSetChanged();
			    tv.setText(file.getPath());
			   }else{
				   Toast.makeText(context,"没有权限读取此目录！",1000).show();
			   }
		   }else if(f.isFile()){
			   if(fileNotify!=null)
			   {
				   fileNotify.notifyOnItemClickIsFile(f);
			   }
			 // Toast.makeText(context,"文件:"+f.getName(),1000).show();
		   }
	    }
	}
	
	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		int id=p1.getId();
		switch(id)
	    {
			case R.id.filesshowButton1:
                if(fileNotify!=null)
				{
					fileNotify.notifyOnLeftButtonClick(left);
				}
				if(file==null){return;}
				file=file.getParentFile();
				if(file==null)
				{
					Toast.makeText(context,"已经处于根目录！",1000).show();
					return;
				}
				listFiles=sort(file.listFiles());
				filesAdapter.setFiles(listFiles).notifyDataSetChanged();
				tv.setText(file.getPath());

				break;
			case R.id.filesshowButton2:
				if(fileNotify!=null)
				{
					fileNotify.notifyOnRightButtonClick(right);
				}
				break;
		}

	}
	
	public void setFileNotify(FileNotify p1){
		fileNotify=p1;
	}

	public AlertDialog obtainShowDialog()
	{
		//一旦调用就获取最新状态
		String path=dataSaver.getSendRegularFileSavePath();
		tv.setText(path);
		file=new File(path);
		listFiles=file.listFiles();
		filesAdapter.setFiles(listFiles).notifyDataSetChanged();
		return showDialog;
	}
    
	public String obtainTargetPath()
	{
		return file.getAbsolutePath();
	}
	
	public void setShowDialogTitle(String p1)
	{
		title.setText(p1);
	}
	public void setLeftButtonText(String p1)
	{
		left.setText(p1);
	}
	public void setRightButtonText(String p1)
	{
		right.setText(p1);
	}
	private File[] sort(File[] p0){
		if(p0==null){return null;}
		int low=0;
		int hight=p0.length-1;
		File temp=null;
		int i=0;
		while(low<hight)
		{
			if(p0[low].isDirectory()){low++;}
		    if(p0[hight].isFile()){hight--;}
		    if(p0[low].isFile()&&p0[hight].isDirectory())
			{
				temp=p0[low];
				p0[low]=p0[hight];
				p0[hight]=temp;
				
				Log.i("","low:"+low+" hight:"+hight+" i:"+(i++));
			}
			if(p0[low].isDirectory()&&p0[hight].isFile())
			{
				low++;
				hight--;
			}

		}/*
		if(p0.length>2)
		{
		if(low==hight)
		{
			Toast.makeText(context,low+" low==hight dir:"+p0[low].isDirectory(),1000).show();
			Log.i("",low+" >low==hight dir:"+p0[low].isDirectory());
		}
		if(low>hight)
		{
			Toast.makeText(context,low+":"+hight+" low>hight low_dir:"+p0[low].isDirectory()+" hight_dir:"+p0[hight].isDirectory(),1000).show();
			Log.i("",low+":"+hight+" low>hight low_dir:"+p0[low].isDirectory()+" hight_dir:"+p0[hight].isDirectory());
		}
		}*/
		return p0;
	}
	
	
}
