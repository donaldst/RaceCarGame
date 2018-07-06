package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

/**
 * GameObject is an abstract class that describes the very basic idea of an
 * object within the game. An object has a size, location, and color as well
 * as the proper methods to obtain these attributes. By default, color can
 * be set.
 * 
 * @author Tyler Donaldson
 *
 */

abstract public class GameObject implements IDrawable {
	private int size;
	private int color;
	
	private Transform translation, rotation, scaling;
	
	public GameObject(float startX, float startY, int startSize, int startColor){
		size = startSize;
		color = startColor;
		
		rotation = Transform.makeIdentity();
		translation = Transform.makeIdentity();
		scaling = Transform.makeIdentity();
		
		this.translate(startX, startY);
	}
	
	//Proper 'get' methods
	
	/**
	 * Size
	 * 
	 * @return integer value for GameObject size
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Location
	 * 
	 * @return Floating point values for GameObject loaction
	 */
	
	public Point getLocation(){
		float x = translation.getTranslateX();
		float y = translation.getTranslateY();
		Point p = new Point(x, y);
		return p;
	}
	
	/**
	 * Color
	 * 
	 * @return integer value for GameObject color
	 */
	
	public int getColor(){
		return color;
	}
	
	//Proper set methods
	
	/**
	 * Changes color for the GameObject in question
	 * 
	 * @param newColor the new color to be set
	 */
	
	public void setColor(int newColor){
		color = newColor;
	}
	
	@Deprecated
	public void setLocation(Point newPoint){
		System.err.println("Function Deprecated, please use proper Transformation methods!");
	}
	
	//To String method
	
	@Override
	public String toString(){
		String description = "Position: (" + this.getLocation().getX() + ", " + this.getLocation().getY()
				+ "); Size: " + Integer.valueOf(this.getSize()) + 
				"; Color: " + "[" + ColorUtil.red(this.getColor()) + ","
				+ ColorUtil.green(this.getColor()) + ","
				+ ColorUtil.blue(this.getColor()) + "]";
		
		return description;
	}
	
	//Transformation methods
	public void rotate(float degrees) {
		rotation.rotate((float)Math.toRadians(degrees), getLocation().getX(), getLocation().getY());
	}
	
	public void scale(float sx, float sy) {
		scaling.scale(sx, sy);
	}
	
	public void translate(float tx, float ty) {
		translation.translate(tx, ty);
	}
	
	public Transform rotation(){
		return rotation;
	}
	
	public Transform translation(){
		return translation;
	}
	
	public Transform scaling(){
		return scaling;
	}
	
	//Abstract Draw Method
	public abstract void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn);
	
}