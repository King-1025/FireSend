package com.firesend;

import android.app.*;
import android.content.*;
import android.widget.*;
import com.firesend.*;
import java.io.*;

public class RegularImporter
{
	private Context context;
	private FilesBrowser filesBrowser;
	private RegularImportNotify regularImportNotify;
	public RegularImporter(Context p1){
		context=p1;
		filesBrowser= new FilesBrowser(context);
		filesBrowser.setShowDialogTitle("导入规则");
		filesBrowser.setRightButtonText("取消导入");
		filesBrowser.setFileNotify(new FileNotify(){

				@Override
				public void notifyOnLeftButtonClick(Button b)
				{
					// TODO: Implement this method
				}

				@Override
				public void notifyOnRightButtonClick(Button b)
				{
					// TODO: Implement this method
					filesBrowser.obtainShowDialog().hide();
				}

				@Override
				public void notifyOnItemClickIsDirectory(File f)
				{
					// TODO: Implement this method
				}

				@Override
				public void notifyOnItemClickIsFile(File f)
				{
					// TODO: Implement this method
					ProgressDialog pd= ProgressDialog.show(context,null, "正在导入中...",true,false);
					String importFilePath=f.getPath();
					boolean isImport=new RegularManager(context).importXMLRegularByPullParser(importFilePath);
					if(isImport){
						if(regularImportNotify!=null){
							regularImportNotify.notifyImportSuccess();
						}
						filesBrowser.obtainShowDialog().hide();
					}else{
						if(regularImportNotify!=null){
							regularImportNotify.notifyImportFailed();
						}
					}
				    pd.dismiss();
				}
			});
	}
	
	public void setNotify(RegularImportNotify p1){
		regularImportNotify=p1;
	}
	
	public void importRegular(){
		filesBrowser.obtainShowDialog().show();
	}
	
	public void destroy(){
		filesBrowser.obtainShowDialog().dismiss();
	}
}
