package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.Transform;
import com.codename1.ui.plaf.Border;
import com.mycompany.a4.IIterator;

/**
 * MapView now displays Game Objects by drawing them into the relevant container.
 * This view is updated and then re-displayed on each tick of the Game.
 * @author Tyler
 *
 */

public class MapView extends Container implements Observer{
	
	private GameWorld gWorld;
	private Game game;
	
	private ISelectable selectedOne;
	private Boolean positionFlag = false;
	
	private Transform worldToND, ndToDisplay, vtm;
	private float winLeft, winBot, winRight, winTop, winWidth, winHeight;
	
	public MapView(GameWorld gw, Game g){
		game = g;
		gWorld = gw;
		gWorld.addObserver(this);
		this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY));
		
		winLeft = 0;
		winBot = 0;
		winTop = 1265/2;
		winRight = 1833/2;

		winWidth = winRight - winLeft;
		winHeight = winTop - winBot;
	}

	public int getTrueWidth(){
		return Math.round(winWidth);
	}

	public int getTrueHeight(){
		return Math.round(winHeight);
	}

	public void update(Observable observable, Object data){
		gWorld = (GameWorld)observable;
		//gWorld.mapGameWorld();
		
		repaint();
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		worldToND = buildWorldToNDXform(winWidth, winHeight, winLeft, winBot);
		ndToDisplay = buildNDToDisplayXform(this.getWidth(), this.getHeight());
		
		vtm = ndToDisplay.copy();
		vtm.concatenate(worldToND);
		
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		gXform.translate(getAbsoluteX(), getAbsoluteY());
		gXform.concatenate(vtm);
		gXform.translate(-getAbsoluteX(), -getAbsoluteY());
		g.setTransform(gXform);
		
		Point pCmpRelPrnt = new Point(getX(), getY());
		Point pCmpRelScrn = new Point(getAbsoluteX(), getAbsoluteY());
		
		IIterator it = gWorld.getIterator();
		
		while(it.hasNext()){
			GameObject go = it.next();
			
			go.draw(g, pCmpRelPrnt, pCmpRelScrn);
		}
		g.resetAffine();
	}
	
	private Transform buildWorldToNDXform(float winWidth, float winHeight, float
			winLeft, float winBottom){
			Transform tmpXfrom = Transform.makeIdentity();
			tmpXfrom.scale( (1/winWidth) , (1/winHeight) );
			tmpXfrom.translate(-winLeft,-winBottom);
			return tmpXfrom;
	}
	
	private Transform buildNDToDisplayXform (float displayWidth, float displayHeight){
		Transform tmpXfrom = Transform.makeIdentity();
		tmpXfrom.translate(0, displayHeight);
		tmpXfrom.scale(displayWidth, -displayHeight);
		return tmpXfrom;
	}
	@Override
	public void pointerPressed(int x, int y){
		//System.out.println(getX() + " " + getY());
		
		//System.out.println(x + " " + y);
		x = x - getAbsoluteX();
		y = y - getAbsoluteY();
		
		//System.out.println(x + " " + y);
		
		Point pPtrRelPrnt = new Point(x, y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		if(game.isPaused()){
			if(positionFlag && selectedOne != null){
				GameObject obj = (GameObject)selectedOne;
				obj.translate(pPtrRelPrnt.getX(), pPtrRelPrnt.getY());
				positionFlag = false;
				selectedOne = null;
			}
			else{
				IIterator it = gWorld.getIterator();
				
				while(it.hasNext()){
					GameObject next = it.next();
					
					if(next instanceof ISelectable){
						ISelectable select = (ISelectable)next;
						
						if(select.contains(pPtrRelPrnt, pCmpRelPrnt)){
							select.setSelected(true);
							selectedOne = select;
						}
						else{
							select.setSelected(false);
						}
					}
				}
			}
		}
		
		repaint();
	}
	
	public void togglePositionFlag(){
		positionFlag = !positionFlag;
	}
}