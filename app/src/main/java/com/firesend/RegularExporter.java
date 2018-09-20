package com.firesend;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.firesend.drawable.*;
import java.io.*;

public class RegularExporter implements OnClickListener
{
	private Context context;
	private AlertDialog showDialog;
	private View content;
	private EditText name;
	private RelativeLayout changePath;
	private TextView fileSavePath;
	private ImageView iv;
	private StyleIcon go;
	private Button cancel;
	private Button ok;
	private DataSaver dataSaver;
	private FilesBrowser filesBrowser;
	public RegularExporter(Context p1){
		context=p1;
		dataSaver=(DataSaver)p1.getApplicationContext();
		
		content=LayoutInflater.from(p1).inflate(R.layout.regular_export,null);
		name=(EditText) content.findViewById(R.id.regularexportEditText1);
		changePath=(RelativeLayout) content.findViewById(R.id.regularexportRelativeLayout1);
		fileSavePath=(TextView) content.findViewById(R.id.regularexportTextView4);
		iv=(ImageView) content.findViewById(R.id.regularexportImageView1);
		cancel=(Button) content.findViewById(R.id.regularexportButton1);
		ok=(Button) content.findViewById(R.id.regularexportButton2);
		
		showDialog=new AlertDialog.Builder(p1).setView(content).create();
		showDialog.setCanceledOnTouchOutside(false);
		
		go=new StyleIcon(StyleIcon.GO);
		go.setMainColor(Color.GRAY);
		iv.setBackground(go);
		
		changePath.setOnClickListener(this);
		cancel.setOnClickListener(this);
		ok.setOnClickListener(this);
		
	    filesBrowser=new FilesBrowser(p1);
		filesBrowser.setShowDialogTitle("选择保存路径");
		filesBrowser.setRightButtonText("选定");
		filesBrowser.setFileNotify(new FileNotify(){

				@Override
				public void notifyOnItemClickIsDirectory(File f)
				{
					// TODO: Implement this method
				}

				@Override
				public void notifyOnItemClickIsFile(File f)
				{
					// TODO: Implement this method
				}

				@Override
				public void notifyOnLeftButtonClick(Button b)
				{
					// TODO: Implement this method
				}

				@Override
				public void notifyOnRightButtonClick(Button b)
				{
					// TODO: Implement this method
					String str=filesBrowser.obtainTargetPath();
					fileSavePath.setText(str);
					dataSaver.setSendRegularFileSavePath(str);
					filesBrowser.obtainShowDialog().hide();
				}
			});
	}
	
	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		int id=p1.getId();
		switch(id)
		{
			case R.id.regularexportRelativeLayout1:
				filesBrowser.obtainShowDialog().show();
				break;
			case R.id.regularexportButton1:
				showDialog.hide();
				break;
			case R.id.regularexportButton2:
				 ProgressDialog pd= ProgressDialog.show(context,null, "正在导出中...",true,false);
				 String savePath=dataSaver.getSendRegularFileSavePath();
				 String fileName=name.getText().toString();//获取文件名
				 boolean isExport=new RegularManager(context).exportXMLRegularByPullParser(savePath,fileName);
				 pd.dismiss();
				 if(isExport)
				 {
				 Toast.makeText(context,"导出规则成功,文件位置:"+savePath+"/"+fileName+dataSaver.defaultFileTag,1000).show();
				 showDialog.hide();
				 }else{
				 Toast.makeText(context,"导出规则失败！",1000).show();
				 }
				break;
		}
	}
	
	public void exportRegular(){
		name.setText(dataSaver.getSendRegularName());
		fileSavePath.setText(dataSaver.getSendRegularFileSavePath());
		showDialog.show();
	}

	public void destroy(){
		showDialog.dismiss();
		filesBrowser.obtainShowDialog().dismiss();
	}
}
