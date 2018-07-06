package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;

public class GoButton extends GameButton{

	public GoButton(String name){
		super(name);
		this.getAllStyles().setBgColor(ColorUtil.rgb(92, 184, 92));
	}
}
