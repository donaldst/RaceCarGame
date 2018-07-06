package com.mycompany.a4;

import com.codename1.charts.models.Point;

abstract public class FixedObject extends GameObject implements ISelectable{
	
	private boolean isSelected;

	public FixedObject(float startX, float startY, int startSize, int startColor) {
		super(startX, startY, startSize, startColor);
		isSelected = false;
	}
	
	/**
	 * Fixed Objects can have their location changed during pause mode. Overrides GameObject.
	 */
	@Override
	public void setLocation(Point newPoint){
		super.setLocation(newPoint);
	}
	
	public void setSelected(boolean tf) {
		isSelected = tf;
	}

	public boolean isSelected() {
		return isSelected;
	}
	
	public String toString(){
		return super.toString();
	}
	
	public abstract boolean contains(Point x, Point y);
	
}