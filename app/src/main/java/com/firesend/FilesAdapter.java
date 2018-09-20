package com.firesend;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import com.firesend.view.*;
import java.io.*;

public class FilesAdapter extends BaseAdapter
{

	private Context context;
	private File[] files;
	public FilesAdapter(Context p1){
		this(p1,null);
	}
	public FilesAdapter(Context p1,File[] p2){
		context=p1;
		files=p2;
	}
	public FilesAdapter setFiles(File[] p1){
		if(p1!=null)
		{
			files=p1;
		}
		return this;
	}
	
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		int size=0;
		if(files!=null)
		{
			size=files.length;
		}
		return size;
	}

	@Override
	public Object getItem(int p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public long getItemId(int p1)
	{
		// TODO: Implement this method
		return p1;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		// TODO: Implement this method
        StyleFileItem sfi;
		if(p2==null)
		{
			sfi=new StyleFileItem(context);
		}else{
			sfi=(StyleFileItem) p2;
		}
		File f=files[p1];
		if(f==null){return null;}
		String s=f.getName();
		//sfi.setBackgroundColor(0);
		if(f.isDirectory()){
			sfi.setTVText("文件夹:"+s);
			//sfi.setBackgroundColor(Color.argb(127,0,255,0));
		}else if(f.isFile()){
			if(s.contains(((DataSaver)(context.getApplicationContext())).defaultFileTag))
			{
				sfi.setTVText("规则文件:"+s);
				//sfi.setBackgroundColor(Color.argb(127,255,0,0));
			}else{
				sfi.setTVText(s);
				//sfi.setBackgroundColor(Color.argb(127,0,0,255));
			}
			
		}
		return sfi;
	}
	
}
