package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class StrategizeCmd extends Command{

	GameWorld target;
	
	public StrategizeCmd(GameWorld gw) {
		super("Strategize");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.strategize();
	}
}
