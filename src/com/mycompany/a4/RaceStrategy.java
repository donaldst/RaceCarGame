package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.util.MathUtil;
import com.mycompany.a4.IIterator;

public class RaceStrategy implements IStrategy {

	private NonPlayerCar npc;
	private GameWorld gw;
	private IIterator it;
	
	private int pylonGoal;
	
	public RaceStrategy(NonPlayerCar c, GameWorld g){
		npc = c;
		gw = g;
		
		pylonGoal = npc.getLastPylon() + 1;
	}
	
	public double modDegs(double degs){
		return ((degs % 360) + 360) % 360;
	}
	
	public void apply() {
		pylonGoal = npc.getLastPylon() + 1;
		
		//Start moving
		if(npc.getSpeed() < 40){
			npc.setSpeed(npc.getSpeed()+1);
		}
		
		it = gw.getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			
			if(next instanceof Pylon){
				Pylon pyl = (Pylon)next;
				
				if(pyl.getSequenceNo() == pylonGoal){
					Point goal = pyl.getLocation();
					
					double distFromX = npc.getLocation().getX() - goal.getX();
					double distFromY = npc.getLocation().getY() - goal.getY();
					
					//System.out.println(npc.getPlayer() + ": " + distFromX + " " + distFromY);
					
					double distTotal = Math.sqrt(MathUtil.pow(distFromX, 2) + MathUtil.pow(distFromY, 2));
					
					double idealAngle = MathUtil.atan2(distFromY, distFromX);
					double idealHeading = modDegs(Math.toDegrees(idealAngle) - 90);
					//System.out.println(idealHeading);
					//npc.setHeading((int)Math.round(idealHeading));
					
					//Steer towards target
					
					
					//System.out.println(npc.getHeading());
					
					if(modDegs(npc.getHeading() + npc.getSteeringDirection()) < idealHeading)
						npc.steer(3);
					else if(modDegs(npc.getHeading() + npc.getSteeringDirection()) > idealHeading)
						npc.steer(-3);
					
					//Catch up or slow down for target
					if(distTotal > 250)
						npc.setSpeed(npc.getSpeed()+2);
					else if(distTotal <= 50)
						npc.setSpeed(npc.getSpeed()-2);

				}
			}
		}
	}

}