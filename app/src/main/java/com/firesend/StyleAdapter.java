package com.firesend;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import com.firesend.view.*;
import java.util.*;

public class StyleAdapter extends BaseAdapter 
{
	private Context context;
	private ListView list;
	private View view;
	private int when;
	//private HashMap<String,List<?>> sendData;
	private List<String>sendText;
	private List<Integer>sendDelayTime;
	//private final static String flag0="SEND_CONTENT";
	//private final static String flag1="SEND_DELAY_TIME";
    private int size;

	public StyleAdapter(Context p0,List<String>p1,List<Integer>p2){
		context=p0;
		sendText=p1;
		sendDelayTime=p2;
		/*sendData=new HashMap<>();
		sendData.put(flag0,sendText);
		sendData.put(flag1,sendDelayTime);*/
	}
	
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		size=sendText.size();
		if(size>sendDelayTime.size())
		{size=sendDelayTime.size();}
		//checkListViewItemAndNotify(list,size,view,when);
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
	
		  StyleRegularItem styleRegularItem;
		  if(p2==null)
		  {styleRegularItem=new StyleRegularItem(context);}
		  else
		  {styleRegularItem=(StyleRegularItem) p2;}
		  styleRegularItem.setData(p1,sendText,sendDelayTime);
		  return styleRegularItem;
	}

	public void setListView(ListView p1){
		list=p1;
	}
	public void setView(View p1){
		view=p1;
	}
	public void setWhen(int p1){
		when=p1;
	}
	public void checkListViewItemAndNotify(ListView p1,int p2,View p3,int p4){
		if(p1==null||p3==null){return;}
		int footerViewsCount=p1.getFooterViewsCount();
		if(p2<=p4&&footerViewsCount<=0){
			p1.addFooterView(p3);
			return;
		}
		if(p2>p4&&footerViewsCount>0){
			p1.removeFooterView(p3);
			return;
		}
	}
}
