package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCmd extends Command{

	GameWorld target;
	
	public HelpCmd(GameWorld gw) {
		super("Help");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		Dialog.show("Help", "Welcome to Race Game!\nCommands:\n"
				+ "A: Accelerate\nB: Brake\nL: Turn Left\nR: Turn Right\n P: Pause Game\nE: Exit game", "Ok", null);
	}
}
