package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.IIterator;

public class AdvanceCmd extends Command{
	
	GameWorld target;
	
	public AdvanceCmd(GameWorld gw) {
		super("Advance");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		target.advance();
	}
	
}
