package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Triangle implements IDrawable{
	
	private Point top, left, right;
	private int color;
	private Transform rotation, translation, scaling;
	
	public Triangle(int base, int height, int c){
		top = new Point (0, height/2);
		left = new Point (-base/2, -height/2);
		right = new Point (base/2, -height/2);
		color = c;
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
		gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
		gXform.translate(translation.getTranslateX(), translation.getTranslateY());
		gXform.concatenate(rotation);
		gXform.scale(scaling.getScaleX(), scaling.getScaleY());
		gXform.translate(-pCmpRelScrn.getX(),-pCmpRelScrn.getY());
		g.setTransform(gXform);
		
		int x1 = Math.round(pCmpRelPrnt.getX() + top.getX());
		int x2 = Math.round(pCmpRelPrnt.getX() + left.getX());
		int x3 = Math.round(pCmpRelPrnt.getX() + right.getX());
		
		int y1 =  Math.round(pCmpRelPrnt.getY() + top.getY());
		int y2 =  Math.round(pCmpRelPrnt.getY() + left.getY());
		
		int [] xPoints = {x1, x2, x3};
		int [] yPoints = {y1, y2, y2};
		
		g.fillPolygon(xPoints, yPoints, 3);
		
		g.setTransform(gOrigXform);
	}

}
