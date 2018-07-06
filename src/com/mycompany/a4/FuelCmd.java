package com.mycompany.a4;

import java.util.Random;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.IIterator;

public class FuelCmd extends Command{

	GameWorld target;
	
	public FuelCmd(GameWorld gw) {
		super("Fuel");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		target.fuel();
	}
}
