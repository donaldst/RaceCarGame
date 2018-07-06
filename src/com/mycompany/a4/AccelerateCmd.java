package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.IIterator;

public class AccelerateCmd extends Command{

	GameWorld target;
	
	public AccelerateCmd(GameWorld gw) {
		super("Accelerate");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.accelerate();
	}
}
