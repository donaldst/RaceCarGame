package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;

public class BrakeCmd extends Command{

	GameWorld target;
	
	public BrakeCmd(GameWorld gw) {
		super("Brake");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.brake();
	}
}

