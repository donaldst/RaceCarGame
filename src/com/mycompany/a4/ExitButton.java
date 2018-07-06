package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;

/**
 * 
 * @author Tyler
 * 
 * ExitButton provides a specialized constructor for an Exit command button
 */
public class ExitButton extends GameButton{
	
	public ExitButton(String name){
		super(name);
		this.getAllStyles().setBgColor(ColorUtil.rgb(217, 83, 79));
		
	}
}
