package com.firesend;

import android.app.*;
import android.content.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.RadioGroup.*;
import java.util.*;

import android.view.View.OnClickListener;

public class RegularControler implements OnClickListener
{
	private Context context;
	private DataSaver dataSaver;
	private ArrayList<String>sendText;
	private ArrayList<Integer>sendDelayTime;
	
	private AlertDialog showDialog;
	private View content;
	private TextView title;
	private EditText regular;
	private TextView delayTime;
	private TextView valueType;
	private SeekBar seekBar;
	private RadioGroup radioGroup;
	private RadioButton ms;
	private RadioButton s;
	private RadioButton m;
	private Button cancel;
	private Button ok;
	
	private int maxSeekValue;
	private int seekValue;
	private String dw;
	private int jz;
	private int modifyIndex;
	private int mode;
	private RegularControlNotify regularControlNotify;
	
	public final static int MODE_ADD=100;
	public final static int MODE_MODIFY=101;
	
	public final static int TYPE_MS=1000;
	public final static int TYPE_S=1001;
	public final static int TYPE_M=1002;
	
	public RegularControler(Context p1){
		 context=p1;
		 dataSaver=(DataSaver)p1.getApplicationContext();
	     sendText=dataSaver.obtainSendText();
		 sendDelayTime=dataSaver.obtainSendDelayTime();
		
	     content=LayoutInflater.from(p1).inflate(R.layout.regular_dialog,null);
         title=(TextView) content.findViewById(R.id.regulardialogTextView1);
	     regular=(EditText)content.findViewById(R.id.regulardialogEditText1);
		 delayTime=(TextView) content.findViewById(R.id.regulardialogTextView2);
		 valueType=(TextView) content.findViewById(R.id.regulardialogTextView3);
		 seekBar=(SeekBar)content.findViewById(R.id.regulardialogSeekBar1);
		 radioGroup=(RadioGroup)content.findViewById(R.id.regulardialogRadioGroup1);
		 ms=(RadioButton) content.findViewById(R.id.regulardialogRadioButton1);
		 s=(RadioButton) content.findViewById(R.id.regulardialogRadioButton2);
		 m=(RadioButton) content.findViewById(R.id.regulardialogRadioButton3);
		 cancel=(Button)content.findViewById(R.id.regulardialogButton1);
		 ok=(Button)content.findViewById(R.id.regulardialogButton2);
		 
		 showDialog=new AlertDialog.Builder(p1).setView(content).create();
		 showDialog.setCanceledOnTouchOutside(false);
		 
		 setSeekValue(0);
		 setChoiceType(TYPE_MS);
		 cancel.setText("取消");
		 
	     seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					// TODO: Implement this method
					setSeekValue(p2);
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}
			});

		 radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(RadioGroup p1, int p2)
				{
					// TODO: Implement this method
					switch(p2){
						case R.id.regulardialogRadioButton1:
						    setChoiceType(TYPE_MS);
							break;
						case R.id.regulardialogRadioButton2:
						    setChoiceType(TYPE_S);
							break;
						case R.id.regulardialogRadioButton3:
							setChoiceType(TYPE_M);
							break;
					}
				    setSeekValue(0);
				}
			});
			
		cancel.setOnClickListener(this);
		ok.setOnClickListener(this);

	}
	
	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		int id=p1.getId();
		switch(id)
		{
			case R.id.regulardialogButton1:
				showDialog.hide();
				break;
			case R.id.regulardialogButton2:
				String str=regular.getText().toString();
				if(str.equals(""))
				{ 
					Toast.makeText(context,"输入内容为空！",1000).show();
					return;
				}
				if(mode==MODE_ADD)
				{ 
					sendText.add(str);
					sendDelayTime.add(seekValue*jz);
					if(regularControlNotify!=null){
						regularControlNotify.notifyRegularAdded();
					}
				}else if(mode==MODE_MODIFY)
				{ 
					sendText.set(modifyIndex,str);
					sendDelayTime.set(modifyIndex,seekValue*jz);
					if(regularControlNotify!=null){
						regularControlNotify.notifyRegularMotified();
					}
				}
				showDialog.hide();
				break;
		}
	}
	
	public AlertDialog obtainShowDialog(int p1){

		if(p1==MODE_ADD){
			regular.setText("");
			ok.setText("确定");
		}else if(p1==MODE_MODIFY){
			ok.setText("修改");
		}else{
			return null;
		}
		mode=p1;
		return showDialog;
	}
	public void setMotifyIndex(int p1){
		modifyIndex=p1;
	}
	public void setTitle(String p1){
		title.setText(p1);
	}
	public void setRegular(String p1){
		regular.setText(p1);
	}
	public void setChoiceType(int p1){
		switch(p1)
		{
			case TYPE_M:
				maxSeekValue=10;
				dw="分钟";
				jz=60000;
				if(!m.isChecked())
				{m.setChecked(true);}
				break;
			case TYPE_S:
				maxSeekValue=50;
				dw="秒";
				jz=1000;
				if(!s.isChecked())
				{s.setChecked(true);}
				break;
			case TYPE_MS:
			default:
				maxSeekValue=100;
				dw="毫秒";
				jz=1;
				if(!ms.isChecked())
				{ms.setChecked(true);}
				break;
		}
		valueType.setText(dw);
		seekBar.setMax(maxSeekValue);
	}
	public void setSeekValue(int p1){
		seekValue=p1;
		delayTime.setText(p1+"");
		if(seekBar.getProgress()!=p1)
		{seekBar.setProgress(p1);}
	}
	public void setNotify(RegularControlNotify p1){
		regularControlNotify=p1;
	}
	public void destroy(){
		showDialog.dismiss();
	}
}
