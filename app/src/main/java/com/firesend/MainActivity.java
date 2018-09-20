package com.firesend;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.provider.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AdapterView.*;
import com.firesend.drawable.*;
import com.firesend.other.*;
import com.firesend.view.*;
import java.util.*;

import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener,OnItemLongClickListener
{
	
	private DataSaver dataSaver;
	private ArrayList<String>sendText;
	private ArrayList<Integer>sendDelayTime;
	private StyleAdapter styleAdapter;
    private int regularCount=0;
	
	private StyleTextView styleTextView;
	private StyleNotifyBar styleNotifyBar;
	private TextView regularName;
	private Button pointName;
	private TextView regularTotalItem;
    private TextView regularTotalDelayTime;
	private ListView regularContent;
	private View notify;
	private Button add;
	private Button delete;
	
	private StyleIcon bg_point_name;
    private StyleIcon bg_add;
	private StyleIcon bg_delete;
	
	private View name;
	private EditText nameEditText;
	private Button cancel;
	private Button ok;
	private AlertDialog nameDialog;
    
	
	private RegularImporter regularImporter;
	private RegularExporter regularExporter;
	private RegularControler regularControler;
	
	private final static String TARGET_SERVICE="com.firesend/com.firesend.FireSendService";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		dataSaver=(DataSaver)getApplicationContext();
		
	    sendText=dataSaver.obtainSendText();
		sendDelayTime=dataSaver.obtainSendDelayTime();
		styleAdapter=new StyleAdapter(this,sendText,sendDelayTime);
		
		styleTextView=(StyleTextView) findViewById(R.id.styleTextView1);
		styleNotifyBar=(StyleNotifyBar)findViewById(R.id.styleNotifyBar1);
		regularName=(TextView) findViewById(R.id.mainTextViewName);
		pointName=(Button) findViewById(R.id.mainButton3);
		regularTotalItem=(TextView) findViewById(R.id.mainTextViewItem);
		regularTotalDelayTime=(TextView) findViewById(R.id.mainTextViewDelayTime);
		regularContent=(ListView)findViewById(R.id.mainListView1);
		notify=findViewById(R.id.mainLayoutNotify);
		add=(Button) findViewById(R.id.mainButton1);
		delete=(Button) findViewById(R.id.mainButton2);
		
		bg_point_name=new StyleIcon(StyleIcon.POINT);
		bg_add=new StyleIcon(StyleIcon.ADD);
		bg_delete=new StyleIcon(StyleIcon.DELETE);
		
		pointName.setBackground(bg_point_name);
		add.setBackground(bg_add);	
		delete.setBackground(bg_delete);
		
		pointName.setOnTouchListener(bg_point_name);
		add.setOnTouchListener(bg_add);
		delete.setOnTouchListener(bg_delete);
		
		pointName.setOnClickListener(this);
		add.setOnClickListener(this);
		delete.setOnClickListener(this);
		
		regularContent.setOnItemLongClickListener(this);
		
	    name=LayoutInflater.from(this).inflate(R.layout.name,null);
		nameEditText=(EditText) name.findViewById(R.id.nameEditText1);
		cancel=(Button) name.findViewById(R.id.nameButton1);
		ok=(Button) name.findViewById(R.id.nameButton2);
		nameDialog=new AlertDialog.Builder(this).setView(name).create();
		nameDialog.setCanceledOnTouchOutside(false);
		cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					nameDialog.hide();
				}
			});
		ok.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
	
					String str=nameEditText.getText().toString();
					int length=str.length();
					if(length<=30)
					{ 
						if(str.length()<=0){
							regularName.setText("---");   
						}else{
							regularName.setText(str);
							dataSaver.setSendRegularName(str);	
						}
					    nameDialog.hide();
					}else{
						Toast.makeText(MainActivity.this,"规则名称应该少于"+30+"个字符!",1000).show();
					}
				}
			});
		
		regularExporter=new RegularExporter(this);
		regularImporter=new RegularImporter(this);
		regularImporter.setNotify(new RegularImportNotify(){

				@Override
				public void notifyImportSuccess()
				{
					// TODO: Implement this method
					regularCount=sendText.size();
					if(regularCount>sendDelayTime.size())
					{
						regularCount=sendDelayTime.size();
					}
					regularName.setText(dataSaver.getSendRegularName());
					regularTotalItem.setText(regularCount+"条");
					regularTotalDelayTime.setText(totalDelayTimeToString(updateTotalDelayTime(sendDelayTime)));
					cmpValueToHideView(regularCount,0,notify);
					styleAdapter.notifyDataSetChanged();
				}

				@Override
				public void notifyImportFailed()
				{
					// TODO: Implement this method
					Toast.makeText(MainActivity.this,"导入规则失败!",1000).show();
				}
			});
		regularControler=new RegularControler(this);
		regularControler.setNotify(new RegularControlNotify(){

				@Override
				public void notifyRegularAdded()
				{
					// TODO: Implement this method
					regularCount++;
					regularTotalItem.setText(regularCount+"条");
					regularTotalDelayTime.setText(totalDelayTimeToString(updateTotalDelayTime(sendDelayTime)));
					cmpValueToHideView(regularCount,0,notify);
					styleAdapter.notifyDataSetChanged();
				}

				@Override
				public void notifyRegularMotified()
				{
					// TODO: Implement this method
					regularTotalDelayTime.setText(totalDelayTimeToString(updateTotalDelayTime(sendDelayTime)));
					cmpValueToHideView(regularCount,0,notify);
					styleAdapter.notifyDataSetChanged();
				}
			});
	 }
	 
	/*
	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }*/
	
	boolean isFirstBack=true;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(isFirstBack)
			{
			    Toast.makeText(MainActivity.this,"再按一次退出",1000).show();
				isFirstBack=!isFirstBack;
			}else{
				moveTaskToBack(true);
				isFirstBack=!isFirstBack;
			}
		}
		return true;
	}
	
	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		
		checkServiceToNotify();
		regularCount=sendText.size();
		cmpValueToHideView(regularCount,0,notify);
		regularContent.setAdapter(styleAdapter);
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		if(name!=null)
		{nameDialog.dismiss();}
		regularImporter.destroy();
		regularExporter.destroy();
		regularControler.destroy();
		super.onDestroy();
	} 
	
    @Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
           switch(p1.getId())
		   {
			   case R.id.mainButton1:
				   addARegular();
				   break;
			   case R.id.mainButton2:
				   deleteARegular();
				   break;
			   case R.id.mainButton3:
				   nameDialog.show();
				   break;
		   }
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
	{
		// TODO: Implement this method
		regularControler.setMotifyIndex(p3);
		regularControler.setTitle("修改第"+(p3+1)+"条规则");
		regularControler.setRegular(sendText.get(p3));
		int time=sendDelayTime.get(p3);
	    if(time>=0&&time<1000){
			regularControler.setChoiceType(RegularControler.TYPE_MS);
		}
		else if(time>=1000&&time<60000){
			time=time/1000;
			regularControler.setChoiceType(RegularControler.TYPE_S);
		}else if(time>=60000){
			time=time/60000;
			regularControler.setChoiceType(RegularControler.TYPE_M);
		}
		regularControler.setSeekValue(time);
		regularControler.obtainShowDialog(RegularControler.MODE_MODIFY).show();
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.action_bar,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		// TODO: Implement this method
		
		switch(item.getItemId())
		{
			
			case R.id.service:
			    startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
				break;
			case R.id.regular_export:
				regularExporter.exportRegular();
				break;
			case R.id.regular_import:
				regularImporter.importRegular();
				break;
			case R.id.info:
                startActivity(new Intent(MainActivity.this,AboutFireSend.class));
				break;
			case R.id.use:
				startActivity(new Intent(MainActivity.this,HowToUse.class));
				break;

		}
		return super.onMenuItemSelected(featureId, item);
	}
    
	public void addARegular(){
		if(regularCount<dataSaver.defaultAddSendRegularMaxCount)
		{
			regularControler.setTitle("新建第"+(regularCount+1)+"条规则");
			regularControler.obtainShowDialog(RegularControler.MODE_ADD).show();
		}else{
			Toast.makeText(MainActivity.this,"最多添加"+dataSaver.defaultAddSendRegularMaxCount+"条规则！",1000).show();
		}
		
	}
	
    public void deleteARegular(){
		
		if(regularCount<1)
		{
			Toast.makeText(MainActivity.this,"不能删除了！",1000).show();
		}else{
			regularCount--;
			sendText.remove(regularCount);
			sendDelayTime.remove(regularCount);
			if(regularCount==0)
			{ regularTotalItem.setText("--"); }
			else
			{ regularTotalItem.setText(regularCount+"条");}
			
			regularTotalDelayTime.setText(totalDelayTimeToString(updateTotalDelayTime(sendDelayTime)));
			cmpValueToHideView(regularCount,0,notify);
			styleAdapter.notifyDataSetChanged();
		}
	}
	
	public float updateTotalDelayTime(ArrayList<Integer>p0){
		float totaldelaytime=0;
		int size=p0.size();
		for(int i=0;i<size;i++)
		{
			totaldelaytime+=p0.get(i);
		}
		return totaldelaytime;
	}
	public String totalDelayTimeToString(float time){
		String str="--";
		if(time>=1&&time<1000){
			str=time+"毫秒";
		}
		else if(time>=1000&&time<60000){
			str=(time/1000)+"秒";
		}else if(time>=60000){
			str=(time/60000)+"分钟";
		}
		return str;
	}
	public void cmpValueToHideView(int p1,int p2,View p3){
		if(p3==null){return;}
		if(p1<=p2)
		{notify.setVisibility(View.VISIBLE);}
		else
		{notify.setVisibility(View.GONE);}
	}
	public void checkServiceToNotify(){
		if(isOpenService(TARGET_SERVICE)){
			
		    //styleTextView.setVisibility(View.VISIBLE);
			//styleNotifyBar.setVisibility(View.GONE);
			styleNotifyBar.isNotify(true);
		}else{
			//styleTextView.setVisibility(View.GONE);
			//styleNotifyBar.setVisibility(View.VISIBLE);
			styleNotifyBar.isNotify(false);
		}
	}
	public boolean isOpenService(String service) {
		int count = 0;
        int ok = 0;
        try {
            ok = Settings.Secure.getInt(getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        }catch(Settings.SettingNotFoundException e) {
			e.printStackTrace();
        }
        TextUtils.SimpleStringSplitter ms = new TextUtils.SimpleStringSplitter(':');
        if (ok == 1) 
		{
            String settingValue = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) 
			{
                ms.setString(settingValue);
                while (ms.hasNext()) 
				{
                    String accessibilityService = ms.next();
					Log.i("",(++count)+"."+accessibilityService);
                    if (accessibilityService.equalsIgnoreCase(service)) 
                    {return true;}
                 }
            }
        }
		return false;
	}
}
