package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.Border;
import com.mycompany.a4.IIterator;

public class ScoreView extends Container implements Observer{
	
	private Label actualLives;
	private Label actualClock;
	private Label actualPylon;
	private Label actualFuel;
	private Label actualDamage;
	private Label actualSound;
	
	public ScoreView(GameWorld g){
		g.addObserver(this);
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY));
		
		Label livesLabel = new Label("Lives Remaining:");
		Label clockLabel = new Label("Time: ");
		Label pylonLabel = new Label("Last Pylon: ");
		Label fuelLabel = new Label("Fuel: ");
		Label damageLabel = new Label("Damage: ");
		Label soundLabel = new Label("Sound: ");
		
		actualLives = new Label(String.valueOf(g.getLives()));
		actualLives.getAllStyles().setPadding(1, 2, 0, 8);
		actualClock = new Label(String.valueOf(g.getTime()/50));
		actualClock.getAllStyles().setPadding(1, 2, 0, 8);
		actualPylon = new Label(String.valueOf(0));
		actualPylon.getAllStyles().setPadding(1, 2, 0, 8);
		actualFuel = new Label(String.valueOf(0));
		actualFuel.getAllStyles().setPadding(1, 2, 0, 8);
		actualDamage = new Label(String.valueOf(0));
		actualDamage.getAllStyles().setPadding(1, 2, 0, 8);
		actualSound = new Label(String.valueOf(g.getSound()));
		actualSound.getAllStyles().setPadding(1, 2, 0, 8);
		
		add(livesLabel);
		add(actualLives);
		add(clockLabel);
		add(actualClock);
		add(pylonLabel);
		add(actualPylon);
		add(fuelLabel);
		add(actualFuel);
		add(damageLabel);
		add(actualDamage);
		add(soundLabel);
		add(actualSound);
	}

	public void update(Observable o, Object data) {
		GameWorld gw = (GameWorld)o;
		
		actualLives.setText(String.valueOf(gw.getLives()));
		actualClock.setText(String.valueOf(gw.getTime()/50));
		actualSound.setText(String.valueOf(gw.getSound()));
		
		IIterator it = gw.getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				
				actualPylon.setText(String.valueOf(player.getLastPylon()));
				actualFuel.setText(String.valueOf(player.getFuelLevel()));
				actualDamage.setText(String.valueOf(player.getDamage()));
			}
		}
		repaint();
		
	}
	
}
