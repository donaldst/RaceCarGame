package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCmd extends Command{

	private Game target;
	
	public PauseCmd(Game g){
		super("Pause");
		target = g;
	}
	
	public void actionPerformed(ActionEvent e){
		target.pause();
	}
}
