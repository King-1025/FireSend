package com.firesend;

import android.view.accessibility.*;

public class Target
{
	private int id;
	private String name;
	private AccessibilityNodeInfo input;
	private AccessibilityNodeInfo click;
    //private AccessibilityNodeInfo back;
	public Target(){
		this(0,null,null,null);
	}
	public Target(int id, String name, AccessibilityNodeInfo input, AccessibilityNodeInfo click)
	{
		this.id = id;
		this.name = name;
		this.input = input;
		this.click = click;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setInput(AccessibilityNodeInfo input)
	{
		this.input = input;
	}

	public AccessibilityNodeInfo getInput()
	{
		return input;
	}

	public void setClick(AccessibilityNodeInfo click)
	{
		this.click = click;
	}

	public AccessibilityNodeInfo getClick()
	{
		return click;
	}
	
	public boolean isValid(int window_id,String window_name){
		boolean is=false;
		if(id==window_id&&name.equals(window_name)&&input!=null&&click!=null){
			is=true;
		}
		return is;
	}
	
}
