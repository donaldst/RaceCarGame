package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class FuelCan extends FixedObject{
	
	//private Point bottomLeftCorner;
	//private int width;
	//private int height;
	
	private Rectangle rect;
	private GameObjectText text;

	public FuelCan(float startX, float startY, int startSize) {
		super(startX, startY, startSize, ColorUtil.GRAY);
		
		rect = new Rectangle(getSize(), getSize(), getColor());
		rect.translate(-getSize()/2, -getSize()/2);
		text = new GameObjectText(String.valueOf(getSize()));
		text.translate(-getSize()/2, getSize()/2);
		/*
		width = getSize();
		height = getSize();
		bottomLeftCorner = new Point(-width/2, -height/2);
		*/
	}
	
	public String toString(){
		return "FUEL CAN: " + super.toString();
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy();
		
		gXform.translate(pCmpRelScrn.getX(), pCmpRelScrn.getY());
		gXform.translate(translation().getTranslateX(), translation().getTranslateY());
		gXform.concatenate(rotation());
		gXform.scale(scaling().getScaleX(), scaling().getScaleY());
		
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
		g.setTransform(gXform);
		
		rect.draw(g, pCmpRelPrnt, pCmpRelScrn);
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
