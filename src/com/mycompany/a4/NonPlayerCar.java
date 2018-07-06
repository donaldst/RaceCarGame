package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.util.MathUtil;

/**
 * 
 * @author Tyler
 *
 * NonPlayerCar (NPC) is a type of Car that races against the player Car. The NPCs
 * implement different strategies for the race.
 */
public class NonPlayerCar extends Car{

	private static int playerCount = 1;
	
	private int playerNo;
	private IStrategy curStrat;
	
	public NonPlayerCar(float startX, float startY, int startHeading) {
		super(startX, startY, startHeading);
		this.setMaxDamageLevel(200);
		this.setColor(ColorUtil.BLUE);
		
		this.playerNo = playerCount;
		playerCount++;
	}
	
	public int getPlayer(){
		return playerNo;
	}
	
	public void setStrategy(IStrategy s){
		curStrat = s;
	}
	
	public void invokeStrategy(){
		curStrat.apply();
	}
	
	public String toString(){
		String parentDesc = super.toString();
		String currentDesc = "NPC no: " + String.valueOf(playerNo);
		return "NPC: " + parentDesc + currentDesc;
	}

	public void swapStrategy(GameWorld g) {
		if(curStrat instanceof AttackStrategy){
			setStrategy(new RaceStrategy(this, g));
			invokeStrategy();
			//setLastPylon((getLastPylon()+1) % 4);
		}
		else if(curStrat instanceof RaceStrategy){
			setStrategy(new AttackStrategy(this, g));
			invokeStrategy();
			//setLastPylon((getLastPylon()+1) % 4);
		}
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn){
		super.draw(g, pCmpRelPrnt, pCmpRelScrn);
	}

}
