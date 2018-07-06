package com.mycompany.a4;

import com.codename1.ui.Command;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.CheckBox;

public class SoundCmd extends Command{

	GameWorld target;
	
	public SoundCmd(GameWorld gw) {
		super("Sound");
		target = gw;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(((CheckBox)e.getComponent()).isSelected())
			target.setSound(true);
		else
			target.setSound(false);
		SideMenuBar.closeCurrentMenu();
	}
}
