package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class CarBody implements IDrawable{
	private int color;
	private Triangle tri;
	
	private Transform translation, rotation, scaling;
	
	public CarBody(int size, int c){
		color = c;
		tri = new Triangle(size/2, size, color);
		
		translation = Transform.makeIdentity();
		rotation = Transform.makeIdentity();
		scaling  = Transform.makeIdentity();
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
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy();
		gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
		gXform.concatenate(rotation);
		gXform.translate(translation.getTranslateX(), translation.getTranslateY());
		gXform.scale(scaling.getScaleX(), scaling.getScaleY());
		gXform.translate(-pCmpRelScrn.getX(),-pCmpRelScrn.getY());
		g.setTransform(gXform);
		
		tri.draw(g, pCmpRelPrnt, pCmpRelScrn);
		
		g.setTransform(gOrigXform);
		
}
}
