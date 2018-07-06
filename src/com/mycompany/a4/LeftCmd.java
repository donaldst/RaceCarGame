package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.IIterator;

public class LeftCmd extends Command{

	GameWorld target;
	
	public LeftCmd(GameWorld gw) {
		super("Left");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.left();
	}
}
