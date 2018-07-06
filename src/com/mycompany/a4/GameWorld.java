package com.mycompany.a4;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.mycompany.a4.GameObject;

/**
 * GameWorld encompasses the idea of a world within the game. GameWorld
 * contains all objects within the game and keeps track of important game
 * data such as time and remaining lives.
 * 
 * @author Tyler Donaldson
 *
 */

public class GameWorld extends Observable{
	
	//Game World variables
	private GameObjectCollection goc = new GameObjectCollection();
	private IIterator it;
	private Random rng = new Random();
	private int clockTime = 0;
	private int livesRemaining = 3;
	private boolean soundFlag = true;
	
	private int width = 1024;
	private int  height = 782;
	
	private Point start1;
	private Point start2;
	private Point start3;
	private Point start4;
	
	public GameWorld(){
		
	}
	
	/**
	 * Initializes the game state by instantiating game objects
	 * @param mapHeight 
	 * @param mapWidth 
	 */
	
	public void init(int mapWidth, int mapHeight){
		width = mapWidth;
		height = mapHeight;
		
		start1 = new Point(mapWidth/2, mapHeight - (mapHeight/ 10));
		//System.out.println(start1.getX() + " " + start1.getY());
		start2 = new Point(start1.getX() - 10, start1.getY() + 5);
		start3 = new Point(start1.getX() + 10, start1.getY() - 5);
		start4 = new Point(start1.getX() + 20, start1.getY() - 10);
		
		Point pyS1 = new Point(((mapWidth*9)/10), (mapHeight/2));
		Point pyS2 = new Point((mapWidth/2), ((mapHeight)/10));
		Point pyS3 = new Point(mapWidth/10, mapHeight/2);
		
		Pylon p0 = new Pylon(start1.getX(), start1.getY(), 4);
		goc.add(p0);
		Pylon p1 = new Pylon(pyS1.getX(), pyS1.getY(), 1);
		goc.add(p1);
		Pylon p2 = new Pylon(pyS2.getX(), pyS2.getY(), 2);  
		goc.add(p2);
		Pylon p3 = new Pylon(pyS3.getX(), pyS3.getY(), 3); 
		goc.add(p3);
		
		FuelCan can1 = new FuelCan(rng.nextInt(mapWidth), rng.nextInt(mapHeight), 25 + rng.nextInt(50)); 
		goc.add(can1);
		FuelCan can2 = new FuelCan(rng.nextInt(mapWidth), rng.nextInt(mapHeight), 25 + rng.nextInt(50)); 
		goc.add(can2);
		
		Bird bird1 = new Bird(rng.nextInt(mapWidth), rng.nextInt(mapHeight), rng.nextInt(359), 1 + rng.nextInt(3)); 
		goc.add(bird1);
		Bird bird2 = new Bird(rng.nextInt(mapHeight), rng.nextInt(mapHeight), rng.nextInt(359), 1 + rng.nextInt(3)); 
		goc.add(bird2);
		
		Car player = new Car(start1.getX(), start1.getY(), 90); 
		goc.add(player);
		
		NonPlayerCar npc1 = new NonPlayerCar(start2.getX(), start2.getY(), 90);
		npc1.setStrategy(new RaceStrategy(npc1, this));
		goc.add(npc1);
		NonPlayerCar npc2 = new NonPlayerCar(start3.getX(), start3.getY(), 90);
		npc2.setStrategy(new RaceStrategy(npc2, this));
		goc.add(npc2);
		NonPlayerCar npc3 = new NonPlayerCar(start4.getX(), start4.getY(), 90);
		npc3.setStrategy(new AttackStrategy(npc3, this));
		goc.add(npc3);
	}
	
	public IIterator getIterator(){
		return goc.getIterator();
	}

	public void setLivesRemaining(int i) {
		livesRemaining = i;
	}
	
	public int getLives(){
		return livesRemaining;
	}
	
	public int getTime(){
		return clockTime;
	}
	
	public Point getMapSize(){
		Point p = new Point(width, height);
		return p;
	}

	public void setSound(boolean b) {
		soundFlag = b;
		setChanged();
		notifyObservers();
	}
	
	public boolean getSound(){
		return soundFlag;
	}
	
	public void mapGameWorld(){
		StringBuilder map = new StringBuilder();
		IIterator it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			map.append(next.toString() + "\n");
		}
		System.out.println(map.toString());
	}
	
	public void accelerate(){
		it = getIterator();
		while (it.hasNext()){
			GameObject next = it.next();
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				player.setSpeed(player.getSpeed() + 2);
			}
			
			setChanged();
			notifyObservers();
		}
	}
	
	public void advance(){
		Command cOk = new Command("Ok");
		Command cCancel = new Command("Cancel");
		Command[] cmds = new Command[]{cOk, cCancel};
		
		TextField tf = new TextField();
		
		Command c = Dialog.show("Enter number 1-9: ", tf, cmds);
		
		try{
			int in = Integer.parseInt(tf.getText());
			if(c == cOk){
				it = getIterator();
				while(it.hasNext()){
					GameObject next = it.next();
					if(next instanceof Car && !(next instanceof NonPlayerCar)){
						Car player = (Car)next;
						player.setLastPylon(in);
					}
				}
			}
		}
		catch(NumberFormatException e) {
			Dialog.show("Invalid input", "Please enter a valid number", "Ok", null);
		}
		setChanged();
		notifyObservers();
	}
	
	public void brake(){
		
		it = getIterator();
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				player.setSpeed(player.getSpeed() - 0.5);
			}
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void crash(){
		it = getIterator();
		Random rng = new Random();
		int carNo = rng.nextInt(2) + 1;
		
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				player.takeDamage(10);
			}
			else if(next instanceof NonPlayerCar){
				NonPlayerCar car = (NonPlayerCar)next;
				if(car.getPlayer() == carNo){
					car.takeDamage(10);
				}
			}
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void fuel(){
		it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				Random rng = new Random();
				player.setFuelLevel(player.getFuelLevel() + (5 + rng.nextInt(20)));
			}
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void gum(){
		it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				player.takeDamage(5);
				int red = ColorUtil.red(player.getColor());
				int green = ColorUtil.green(player.getColor());
				int blue = ColorUtil.blue(player.getColor());
				player.setColor(ColorUtil.rgb((red - 12), (green - 12), (blue - 12)));
			}
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void left(){
		it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				player.steer(-5);
			}
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void right(){
		it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				player.steer(5);
			}
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void restart(){
		it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			
			if(next instanceof Car){
				Car player = (Car)next;
				if(player instanceof NonPlayerCar){
					NonPlayerCar car = (NonPlayerCar)player;
					int seq = car.getPlayer();
					
					switch(seq){
					case 1:
						car.setLocation(start2);
						car.setFuelLevel(100);
						car.setDamage(0);
						car.setSpeed(0);
						car.setHeading(0);
						break;
					case 2:
						car.setLocation(start3);
						car.setFuelLevel(100);
						car.setDamage(0);
						car.setSpeed(0);
						car.setHeading(0);
						break;
					case 3:
						car.setLocation(start4);
						car.setFuelLevel(100);
						car.setDamage(0);
						car.setSpeed(0);
						car.setHeading(0);
						break;
					default:
						car.setLocation(start1);
						car.setFuelLevel(100);
						car.setDamage(0);
						car.setSpeed(0);
						car.setHeading(0);
					}
				}
				else{
					player.setLocation(start1);
					player.setFuelLevel(100);
					player.setDamage(0);
					player.setSpeed(0);
					player.setHeading(90);
				}
			}
		}
		clockTime = 0;
		
		setChanged();
		notifyObservers();
	}
	
	public void strategize(){
		it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof NonPlayerCar){
				NonPlayerCar car = (NonPlayerCar)next;
				car.swapStrategy(this);
			}
		}
	}
	
	public void tick(){
		it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			
			if(next instanceof MoveableObject){
				MoveableObject obj = (MoveableObject)next;
				
				obj.move(clockTime);
			}
			
			if(next instanceof Car && !(next instanceof NonPlayerCar)){
				Car player = (Car)next;
				 if(getLives() == 0) {
					 Dialog.show("Game Over", "You ran out of lives!", "Ok", null);
					 setLivesRemaining(3);
				 }
				 else if (player.getFuelLevel() == 0 || player.getDamage() == player.getMaxDamageLevel()){
					 setLivesRemaining(getLives() - 1);
					 Dialog.show("Failure", "You Failed!\nYou Lost a Life!", "Try Again", null);
					 restart();
				 }
				 else if (player.getSpeed() > 0){
					 player.setHeading(player.getHeading() + player.getSteeringDirection());
					 player.setFuelLevel(player.getFuelLevel() - player.getFuelConsumptionRate());
				 }
				 else player.setFuelLevel(player.getFuelLevel() - player.getFuelConsumptionRate());
			}
			else if(next instanceof NonPlayerCar){
				NonPlayerCar car = (NonPlayerCar)next;
				car.invokeStrategy();
			}
			else if (next instanceof Bird){
				Bird birb = (Bird)next;
				 if (birb.getLocation().getX() > 0 && birb.getLocation().getX() < width && birb.getLocation().getY() > 0 && birb.getLocation().getY() < height)
					birb.turn();
				 else
					birb.avoidBounds();
			}
		}
		
		it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof ICollider){
				ICollider collider = (ICollider)next;
				
				IIterator it2 = getIterator();
				
				while(it2.hasNext()){
					GameObject other = it2.next();
					
					if(other != collider && collider.collidesWith(other)){
						collider.handleCollision(other, soundFlag);
					}
				}
			}
		}
		clockTime++;
		
		setChanged();
		notifyObservers();
	}

	public void position() {
		it = getIterator();
		
		while(it.hasNext()){
			GameObject next = it.next();
			if(next instanceof ISelectable){
				
			}
		}
	}
}
