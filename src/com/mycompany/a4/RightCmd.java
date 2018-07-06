package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.IIterator;

public class RightCmd extends Command{

	GameWorld target;
	
	public RightCmd(GameWorld gw) {
		super("Right");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.right();
	}
}
