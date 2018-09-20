package com.firesend;

import java.io.*;
import android.widget.*;

public interface FileNotify
{
	public void notifyOnItemClickIsDirectory(File f)
	public void notifyOnItemClickIsFile(File f)
	public void notifyOnLeftButtonClick(Button b)
	public void notifyOnRightButtonClick(Button b);
}
