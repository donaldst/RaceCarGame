package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class ExitCmd extends Command{

	GameWorld target;
	
	public ExitCmd(GameWorld gw) {
		super("Exit");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		Boolean exitFlag = Dialog.show("Exit", "Are you sure you would like to quit?", "Yes", "No");
		if(exitFlag)
			System.exit(0);
	}
}
