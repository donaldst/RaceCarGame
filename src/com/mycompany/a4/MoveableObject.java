package com.mycompany.a4;

import com.codename1.charts.models.Point;

/**
 * Abstract concept of a moveable object within the game. Moveable Objects 
 * have a 'heading' direction in degrees and a 'speed' value.
 * 
 * @author Tyler Donaldson
 *
 */

abstract public class MoveableObject extends GameObject{

	private int heading;
	private double speed;
	
	private int timeOfLast = 0;
	
	public MoveableObject(float startX, float startY, int startSize, int startColor, int startHeading, int startSpeed) {
		super(startX, startY, startSize, startColor);
		this.heading = startHeading;
		this.speed = startSpeed;
	}

	
	public void move(int ms){
		
		int timeSince = ms - timeOfLast;
		timeOfLast = ms;
		
		float deltaX = (float) (Math.cos(Math.toRadians(90 - this.getHeading())) * this.getSpeed() * timeSince/25);
		float deltaY = (float) (Math.sin(Math.toRadians(90 - this.getHeading())) * this.getSpeed() * timeSince/25);
		float oldX = this.getLocation().getX();
		float oldY = this.getLocation().getY();
		
		this.translate(deltaX, deltaY);
		
		if(this instanceof Car){
			Car vehicle = (Car)this;
			
			vehicle.setHeading(vehicle.getHeading() + vehicle.getSteeringDirection() * timeSince/25);
			vehicle.rotate(vehicle.getSteeringDirection() * timeSince/25);
		}
	}
	
	//Proper 'get' methods
	
	public int getHeading(){
		return heading;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public void setHeading(int newHeading){
		heading = ((newHeading % 360) + 360) % 360;
	}
	
	public void setSpeed(double newSpeed){
		speed = newSpeed;
	}
	
	
	@Override
	public String toString(){
		String parentDesc = super.toString();
		String desc = "Heading: " + String.valueOf(this.getHeading()) + " Speed: " + String.valueOf(this.getSpeed());
		
		return parentDesc + " " + desc;
	}
	
}
