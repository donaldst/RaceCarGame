package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

/**
 * Pylon is a concrete Fixed Object in the game world that the player must pass in
 * sequence in order to advance. A pylon keeps track of its own number in the
 * sequence.
 * 
 * @author Tyler Donaldson
 *
 */

public class Pylon extends FixedObject{

	private int sequenceNo;
	
	private Triangle tri;
	private GameObjectText text;
	
	public Pylon(float startX, float startY, int startSeq) {
		super(startX, startY, 50, ColorUtil.YELLOW);
		sequenceNo = startSeq;
		
		tri = new Triangle(getSize(), getSize(), getColor());
		text = new GameObjectText(String.valueOf(getSequenceNo()));
		text.translate(-getSize()/4, getSize()/2);
	}

	/**
	 * Pylon does not change color. The setColor method should not allow
	 * changes to be made. 
	 */
	
	@Override
	public void setColor(int newColor) {
		System.out.println("Object type 'Pylon' does not support 'setColor'");
	}
	
	//Proper 'get' methods
	
	public int getSequenceNo(){
		return sequenceNo;
	}
	
	@Override
	public String toString(){
		String parentDesc = super.toString();
		String desc = "Sequence Number: " + String.valueOf(this.getSequenceNo());
		
		return "PYLON: " + parentDesc + " " + desc;
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
		
		tri.draw(g, pCmpRelPrnt, pCmpRelScrn);
		text.draw(g, pCmpRelPrnt, pCmpRelScrn);
		
		g.setTransform(gOrigXform);
	}

	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		
		double px = pPtrRelPrnt.getX();
		double py = pPtrRelPrnt.getY();
		
		double xLoc = this.getLocation().getX() - (this.getSize()/2);
		double yLoc = this.getLocation().getY() + (this.getSize()/2);
		
		if ( (px >= xLoc) && (px <= xLoc + this.getSize()) && (py <= yLoc) && (py >= yLoc - this.getSize()) )
			return true;
		else return false;
	}
	
}
