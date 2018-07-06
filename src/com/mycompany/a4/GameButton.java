package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;

/**
 * 
 * @author Tyler
 * GameButton provides unified style choices for all buttons within the GUI
 */
public class GameButton extends Button{
	
	public GameButton(String name){
		super(name);
		this.getAllStyles().setPadding(Component.TOP, 2);
		this.getAllStyles().setPadding(Component.BOTTOM, 2);
		this.getAllStyles().setPadding(Component.LEFT, 5);
		this.getAllStyles().setPadding(Component.LEFT, 5);
		//transparency
		this.getUnselectedStyle().setBgTransparency(255);
		this.getSelectedStyle().setBgTransparency(200);
		//text color
		this.getAllStyles().setFgColor(ColorUtil.WHITE);
		
		//disabled style
		this.getDisabledStyle().setBgColor(ColorUtil.LTGRAY);
		this.getDisabledStyle().setFgColor(ColorUtil.BLACK);
	}
}
