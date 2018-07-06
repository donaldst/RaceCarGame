package com.mycompany.a4;

import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Bird extends MoveableObject{
	
	private Random rng = new Random();
	private Triangle tri;
	
	//Constructor
	
	public Bird(float startX, float startY, int startHeading, int startSpeed) {
		super(startX, startY, 10, ColorUtil.BLACK, startHeading, startSpeed);
		
		tri = new Triangle(getSize(), getSize(), getColor());
	}
	
	//Methods
	
	/**
	 * Causes a bird to turn 180 degrees. Invoked when its position reaches the edge of the game world.
	 */
	
	public void avoidBounds(){
		this.setHeading(this.getHeading() + 180);
	}
	
	/**
	 * Causes the bird to turn a random amount. Invoked each tick.
	 */

	public void turn() {
		int randomTurn = rng.nextInt(21) - 10;
		
		this.setHeading(this.getHeading() + randomTurn);
	}
	
	/**
	 * Bird does not change color. Overrides GameObject.
	 */
	@Override
	public void setColor(int newColor) {
		System.out.println("Object type 'Bird' does not support 'setColor'");
		return;
	}
	
	@Override
	public String toString(){
		String parentDesc = super.toString();
		
		return "BIRD: " + parentDesc;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		g.setColor(this.getColor());
		
		tri.draw(g, pCmpRelPrnt, pCmpRelScrn);
	}
}
