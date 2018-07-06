package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCmd extends Command{

	GameWorld target;
	
	public AboutCmd(GameWorld gw) {
		super("About");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		Dialog.show("About", "Author: Tyler Donaldson\n Project: A4\n Version: A4", "Ok", null);
	}
	

}
