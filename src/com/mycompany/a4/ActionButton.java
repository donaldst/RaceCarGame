package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;

/**
 * 
 * @author Tyler
 *
 * ActionButton provides a specially constructed button for action command buttons
 */

public class ActionButton extends GameButton{
	
	public ActionButton(String name){
		super(name);
		this.getAllStyles().setBgColor(ColorUtil.rgb(66, 139, 202));
	}
}
