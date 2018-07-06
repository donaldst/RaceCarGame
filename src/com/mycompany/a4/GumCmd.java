package com.mycompany.a4;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.IIterator;

public class GumCmd extends Command{

	GameWorld target;
	
	public GumCmd(GameWorld gw) {
		super("Gum");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.gum();
	}
}
