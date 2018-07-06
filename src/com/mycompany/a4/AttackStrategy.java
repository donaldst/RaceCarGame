package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.util.MathUtil;
import com.mycompany.a4.IIterator;

public class AttackStrategy implements IStrategy {

	private NonPlayerCar npc;
	private GameWorld gw;
	private IIterator it;
	
	public AttackStrategy(NonPlayerCar c, GameWorld g){
		npc = c;
		gw = g;
	}
	
	public void apply() {
		//Start moving
		if(npc.getSpeed() < 2){
			npc.setSpeed(npc.getSpeed()+1);
		}
		
		it = gw.getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				
				Point goal = player.getLocation();
				
				double distFromX = npc.getLocation().getX() - goal.getX();
				double distFromY = npc.getLocation().getY() - goal.getY();
				
				double distTotal = Math.sqrt(MathUtil.pow(distFromX, 2) + MathUtil.pow(distFromY, 2));
				
				double idealAngle = MathUtil.atan2(distFromY, distFromX);
				double idealHeading = Math.toDegrees(idealAngle) - 90;
				//npc.setHeading((int)Math.round(idealHeading));
				//Steer towards target
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
		public double modDegs(double degs){
			return ((degs % 360) + 360) % 360;
		}

}