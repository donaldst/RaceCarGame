package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCmd extends Command{
	private MapView target;
	
	public PositionCmd(MapView mv){
		super("Position");
		target = mv;
	}
	
	public void actionPerformed(ActionEvent e){
		target.togglePositionFlag();
	}
}
