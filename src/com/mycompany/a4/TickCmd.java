package com.mycompany.a4;

import java.util.Random;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.IIterator;

public class TickCmd extends Command{

	GameWorld target;
	
	public TickCmd(GameWorld gw) {
		super("Tick");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.tick();
	}
}
