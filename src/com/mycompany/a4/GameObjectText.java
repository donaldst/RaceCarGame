package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class GameObjectText implements IDrawable{
	
	private String text;
	private Transform rotation;
	private Transform translation;
	private Transform scaling;
	
	public GameObjectText(String s){
		text = s;
		
		rotation = Transform.makeIdentity();
		translation = Transform.makeIdentity();
		scaling = Transform.makeIdentity();
	}
	
	public void rotate(float degrees) {
		rotation.rotate((float)Math.toRadians(degrees), 0, 0);
	}
	
	public void scale(float sx, float sy) {
		scaling.scale(sx, sy);
	}
	
	public void translate(float tx, float ty) {
		translation.translate(tx, ty);
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		g.setColor(ColorUtil.BLACK);
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy();
		
		gXform.translate(pCmpRelScrn.getX(), pCmpRelScrn.getY());
		gXform.translate(translation.getTranslateX(), translation.getTranslateY());
		gXform.concatenate(rotation);
		gXform.scale(scaling.getScaleX(), -scaling.getScaleY());
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
		g.setTransform(gXform);
		
		Font f = g.getFont();
		
		int strWidth = f.stringWidth(text);
		int strHeight = f.getHeight();
		
		Point baseline = new Point(strWidth, strHeight);
		
		int x = Math.round(pCmpRelPrnt.getX() + baseline.getX()/2);
		int y = Math.round(pCmpRelPrnt.getY() + baseline.getY()/2);
		
		g.drawString(text, x, y);
		
		g.setTransform(gOrigXform);
	}
}
