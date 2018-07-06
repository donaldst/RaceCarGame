package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.IIterator;

public class CrashCmd extends Command{

	GameWorld target;
	
	public CrashCmd(GameWorld gw) {
		super("Crash");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.crash();
	}
}
