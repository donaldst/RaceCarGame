package com.mycompany.a4;

import java.util.ArrayList;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Car extends MoveableObject implements ISteerable, ICollider{

	//static attributes
	static final int TOP_SPEED = 100;
	
	//Object attributes
	
	private int steeringDirection, maximumSpeed, lastPylonReached;
	private float fuelLevel, damageLevel, fuelConsumptionRate, maxDamageLevel;
	private ArrayList<GameObject> collidingWith;
	private Sound crashSound, fuelSound;
	
	//Drawable attributes
	
	private CarBody body;
	private Shaft shaft;
	private Wheel [] wheels;
	
	//Constructor
	
	public Car(float startX, float startY, int startHeading) {
		super(startX, startY, 40, ColorUtil.MAGENTA, startHeading, 0);
		this.steeringDirection = 0;
		this.maximumSpeed = TOP_SPEED;
		this.damageLevel = 0;
		this.fuelConsumptionRate = (float)0.05;
		this.fuelLevel = 100;
		this.lastPylonReached = 0;
		this.maxDamageLevel = 100;
		this.collidingWith = new ArrayList<GameObject>();
		crashSound = new Sound("crash.wav");
		fuelSound = new Sound("fill.mp3");
		
		body = new CarBody(getSize(), getColor());
		shaft = new Shaft(getSize(), getColor());
		shaft.translate(-getSize()/4, getSize()/4);
		
		Wheel w0 = new Wheel(getSize());
		w0.translate(-getSize()/4, getSize()/4);
		Wheel w1 = new Wheel(getSize());
		w1.translate(getSize()/4, getSize()/4);
		Wheel w2 = new Wheel(getSize());
		w2.translate(-getSize()/4, -getSize()/2);
		Wheel w3 = new Wheel(getSize());
		w3.translate(getSize()/4, -getSize()/2);
		
		wheels = new Wheel [4];
		wheels[0] = w0;
		wheels[1] = w1;
		wheels[2] = w2;
		wheels[3] = w3;
	}

	//Proper get methods
	
	public float getMaxDamageLevel(){
		return maxDamageLevel;
	}

	public int getSteeringDirection(){
		return steeringDirection;
	}
	
	public int getMaxSpeed(){
		return maximumSpeed;
	}
	
	public int getLastPylon(){
		return lastPylonReached;
	}
	
	public float getFuelLevel(){
		return fuelLevel;
	}
	
	public float getDamage(){
		return damageLevel;
	}
	
	public float getFuelConsumptionRate(){
		return fuelConsumptionRate;
	}
	
	//Setter methods
	
	/**
	 * changes current steering direction of the car. Does not directly change the heading.
	 * 
	 * @param degree the new steering degree to be added. Steering direction is between -40 (left, ccw) and
	 * 		40(right, cw)
	 */
	
	public void setMaxDamageLevel(float newLevel){
		maxDamageLevel = newLevel;
	}
	
	public void setDamage(float dmg){
		if (dmg <= maxDamageLevel && dmg >= 0){
			damageLevel = dmg;
		}
		else if (dmg > maxDamageLevel){
			damageLevel = maxDamageLevel;
		}
		else System.out.println("Error: Damage level cannot be negative!");
		
	}
	public void steer(int degree){
		if(steeringDirection + degree > 40){
			steeringDirection = 40;
		}
		else if (steeringDirection + degree < -40){
			steeringDirection = -40;
		}
		else{
			steeringDirection += degree;
		}
	}
	
	/**
	 * Affects the damage level of the car, from zero up to the maximum.
	 * 
	 * @param dmg amount of damage to be taken.
	 */
	public void takeDamage(float dmg){
		
		float newDamageLevel = dmg + damageLevel;
		
		if (newDamageLevel <= maxDamageLevel && newDamageLevel >= 0){
			damageLevel = newDamageLevel;
		}
		else if (newDamageLevel > maxDamageLevel){
			damageLevel = maxDamageLevel;
		}
		else System.out.println("Error: Damage level cannot be negative!");
		
		this.setMaxSpeed();
		
	}
	
	/**
	 * Sets the last pylon reached. Pylons must be reached in sequence.
	 * 
	 * @param pylon the pylon that has been reached.
	 */
	
	public void setLastPylon(int pylon){
		if (pylon == lastPylonReached + 1){
			lastPylonReached = pylon;
		}
		else System.out.println("You must reach pylon " + String.valueOf(lastPylonReached + 1) + " first!");
	}
	
	/**
	 * Sets a new maximum speed value.
	 * 
	 * @param spd the new max speed. Must be greater than zero.
	 */
	
	public void setMaxSpeed(){
		maximumSpeed = Math.round(((maxDamageLevel - damageLevel)/maxDamageLevel) * TOP_SPEED);
	}
	
	
	/**
	 * Sets a new fuel consumption rate
	 * 
	 * @param newRate the new consumption rate. Must be greater than zero.
	 */
	
	public void setFuelConsumptionRate(float newRate){
		if (newRate > 0){
			fuelConsumptionRate = newRate;
		}
		else System.out.println("Error: Must set fuel consumption to be greater than zero!");
	}
	
	public void setFuelLevel(float newLevel){
		if(newLevel <= 0)
			fuelLevel = 0;
		else
			fuelLevel = newLevel;
	}
	
	/**
	 * Car's speed cannot exceed max speed. Overrides MoveableObject.
	 */
	
	@Override
	public void setSpeed(double d){
		if (d > maximumSpeed)
			super.setSpeed(maximumSpeed);
		else if (d <= 0 || this.fuelLevel <= 0)
			super.setSpeed(0);
		else
			super.setSpeed(d);
	}
	
	@Override
	public String toString(){
		String parentDesc = super.toString();
		String desc = "Max Speed: " + String.valueOf(this.getMaxSpeed()) + "; Steering Direction: " + String.valueOf(this.getSteeringDirection())
			+ "; Last Pylon: " + String.valueOf(this.getLastPylon()) + "; Damage: " + String.valueOf(Math.round(this.getDamage())*10.0/10.0) +
			"; Fuel Consumption Rate: " + String.valueOf(Math.round(this.getFuelConsumptionRate()*10.0/10.0)) +
			"; Fuel Level: " + String.valueOf(Math.round(this.getFuelLevel()*10.0/10.0));
		
		return "CAR: " + parentDesc + " " + desc;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		g.setColor(this.getColor());
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy();
		gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
		gXform.concatenate(rotation());
		gXform.translate(translation().getTranslateX(), translation().getTranslateY());
		gXform.scale(scaling().getScaleX(), scaling().getScaleY());
		gXform.translate(-pCmpRelScrn.getX(),-pCmpRelScrn.getY());
		g.setTransform(gXform);
		//int origX = Math.round(pCmpRelPrnt.getX() - (this.getSize()/2));
		//int origY = Math.round(pCmpRelPrnt.getY() - (this.getSize()/2));
		
		//g.fillArc(origX, origY, this.getSize(), this.getSize(), 0, 360);
		body.draw(g, pCmpRelPrnt, pCmpRelScrn);
		shaft.draw(g, pCmpRelPrnt, pCmpRelScrn);
		for(Wheel w : wheels){
			w.draw(g, pCmpRelPrnt, pCmpRelScrn);
		}
		
		g.setTransform(gOrigXform);
	}

	public boolean collidesWith(GameObject otherObject) {
		boolean result = false;
		
		//Find centers
		float thisCenterX = this.getLocation().getX() + (this.getSize()/2);
		float thisCenterY = this.getLocation().getY() + (this.getSize()/2);
		float otherCenterX = otherObject.getLocation().getX() + (otherObject.getSize()/2);
		float otherCenterY = otherObject.getLocation().getY() + (otherObject.getSize()/2);
		
		// find distance between centers
		float dx = thisCenterX - otherCenterX;
		float dy = thisCenterY - otherCenterY;
		float distBetweenCentersSqr = (dx*dx + dy*dy);
		
		// find square of sum of radii
		float thisRadius = otherObject.getSize()/2;
		float otherRadius = this.getSize()/2;
		float radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius
		+ otherRadius*otherRadius);
		if (distBetweenCentersSqr <= radiiSqr){
			result = true;
		}
		else collidingWith.remove(otherObject);
		return result;
	}

	public void handleCollision(GameObject otherObject, boolean sound) {
		
		if(collidingWith.contains(otherObject))
			return;
		else{
			collidingWith.add(otherObject);
			
			if(otherObject instanceof Pylon){
				Pylon pyl = (Pylon)otherObject;
				if(this.getLastPylon() == (pyl.getSequenceNo() - 1))
					this.setLastPylon(pyl.getSequenceNo());
			}
			else if(otherObject instanceof Car){
				Car car = (Car)otherObject;
				car.takeDamage(10);
				if(sound) crashSound.play();
			}
			else if(otherObject instanceof FuelCan){
				FuelCan can = (FuelCan)otherObject;
				this.setFuelLevel(this.getFuelLevel() + can.getSize());
				if(sound) fuelSound.play();
			}
			else if(otherObject instanceof Bird){
				this.takeDamage(5);
				int red = ColorUtil.red(getColor());
				int green = ColorUtil.green(getColor());
				int blue = ColorUtil.blue(getColor());
				setColor(ColorUtil.rgb((red), (green), (blue)));
			}
		}
	}
}