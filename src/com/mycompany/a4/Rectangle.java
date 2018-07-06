package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Rectangle implements IDrawable{
	
	private Point bottomLeftCorner;
	private int color;
	private int width;
	private int height;
	
	private Transform rotation;
	private Transform translation;
	private Transform scaling;

	public Rectangle(int w, int h, int c) {
		bottomLeftCorner = new Point (-width/2, -height/2);
		color = c;
		width = w;
		height = h;
		
		rotation = Transform.makeIdentity();
		translation = Transform.makeIdentity();
		scaling = Transform.makeIdentity();
	}
	
	public void rotate(float degrees) {
		rotation.rotate((float)Math.toRadians(degrees), translation.getTranslateX(), translation.getTranslateY());
	}
	
	public void scale(float sx, float sy) {
		scaling.scale(sx, sy);
	}
	
	public void translate(float tx, float ty) {
		translation.translate(tx, ty);
	}
		
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		g.setColor(color);
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy();
		
		gXform.translate(pCmpRelScrn.getX(), pCmpRelScrn.getY());
		gXform.translate(translation.getTranslateX(), translation.getTranslateY());
		gXform.concatenate(rotation);
		gXform.scale(scaling.getScaleX(), scaling.getScaleY());
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
		g.setTransform(gXform);
		
		int origX = Math.round(pCmpRelPrnt.getX() + bottomLeftCorner.getX());
		int origY = Math.round(pCmpRelPrnt.getY() + bottomLeftCorner.getY());
		
		g.fillRect(origX, origY, width, height);
		
		g.setTransform(gOrigXform);
	}
}
