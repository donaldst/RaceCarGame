package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;
import java.lang.Character;

/**
 * Game is the encapsulation of the program and contains several components
 * integral to the game state. Game also acts as the controller, and
 * is responsible for displaying game state information.
 * 
 * @author Tyler Donaldson
 */
public class Game extends Form implements Runnable{
	
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	
	private UITimer timer;
	
	private int mapWidth;
	private int mapHeight;
	private boolean pauseFlag = false;
	
	private Toolbar gameToolbar;
	private ActionButton accelButton;
	private ActionButton brakeButton;
	private ActionButton leftButton;
	private ActionButton rightButton;
	//private ActionButton crashButton;
	//private ActionButton advButton;
	//private ActionButton fuelButton;
	//private ActionButton gumButton;
	private ActionButton stratButton;
	private ExitButton exitButton;
	private GoButton pauseButton;
	private ActionButton positionButton;
	
	//Commands
	AboutCmd about;
	AccelerateCmd accelerate;
	//AdvanceCmd advance = new AdvanceCmd(gw);
	BrakeCmd brake;
	//CrashCmd crash = new CrashCmd(gw);
	ExitCmd exit;
	//FuelCmd fuel;
	//GumCmd gum;
	HelpCmd help;
	LeftCmd left;
	RightCmd right;
	Command sound;
	StrategizeCmd strategize;
	PauseCmd pause;
	PositionCmd position;
	//TickCmd tick = new TickCmd(gw);
	
	//Sounds
	private BGSound idleSound = new BGSound("idle.mp3");
	
	public Game() {
		setLayout(new BorderLayout());
		this.setTitle("Racing Car Game");
		
		gw = new GameWorld();
		mv = new MapView(gw, this);
		sv = new ScoreView(gw);
		
		//Game passes itself to timer
		timer = new UITimer(this);
		timer.schedule(20, true, this);
		
		//Commands
		about = new AboutCmd(gw);
		accelerate = new AccelerateCmd(gw);
		//AdvanceCmd advance = new AdvanceCmd(gw);
		brake = new BrakeCmd(gw);
		//CrashCmd crash = new CrashCmd(gw);
		exit = new ExitCmd(gw);
		//fuel = new FuelCmd(gw);
		//gum = new GumCmd(gw);
		help = new HelpCmd(gw);
		left = new LeftCmd(gw);
		right = new RightCmd(gw);
		sound = new SoundCmd(gw);
		strategize = new StrategizeCmd(gw);
		pause = new PauseCmd(this);
		position = new PositionCmd(mv);
		//TickCmd tick = new TickCmd(gw);
		
		//Toolbar
		gameToolbar = new Toolbar();
		setToolbar(gameToolbar);
		
		gameToolbar.addCommandToSideMenu(accelerate);
		gameToolbar.addCommandToSideMenu(about);
		gameToolbar.addCommandToSideMenu(exit);
		
		gameToolbar.addCommandToRightBar(help);
		
		CheckBox checkSound = new CheckBox("Sound");
		checkSound.setCommand(sound);
		sound.putClientProperty("SideComponent", checkSound);
		checkSound.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		checkSound.getAllStyles().setBgTransparency(255);
		checkSound.setSelected(true);
		
		gameToolbar.addCommandToSideMenu(sound);
		
		//Containers
		Container westCommandContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		westCommandContainer.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY));
		Container southCommandContainer = new Container(new FlowLayout(Component.CENTER));
		southCommandContainer.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.LTGRAY));
		
		//GUI Construction
		add(BorderLayout.NORTH, sv);
		add(BorderLayout.WEST, westCommandContainer);
		add(BorderLayout.SOUTH, southCommandContainer);
		add(BorderLayout.CENTER, mv);
		
		//Buttons
		accelButton = new ActionButton("Accelerate");
		accelButton.setCommand(accelerate);
		brakeButton = new ActionButton("Brake");
		brakeButton.setCommand(brake);
		leftButton = new ActionButton("turn Left");
		leftButton.setCommand(left);
		rightButton = new ActionButton("turn Right");
		rightButton.setCommand(right);
		//crashButton = new ActionButton("Collide");
		//crashButton.setCommand(crash);
		//advButton = new ActionButton("advance pylon (1-9)");
		//advButton.setCommand(advance);
		//fuelButton = new ActionButton("Fuel up");
		//fuelButton.setCommand(fuel);
		//gumButton = new ActionButton("Gum up");
		//gumButton.setCommand(gum);
		stratButton = new ActionButton("Strategize");
		stratButton.setCommand(strategize);
		//GoButton tickButton = new GoButton("Tick");
		//tickButton.setCommand(tick);
		exitButton = new ExitButton("Exit");
		exitButton.setCommand(exit);
		pauseButton = new GoButton("Pause");
		pauseButton.setCommand(pause);
		positionButton = new ActionButton("Position");
		positionButton.setCommand(position);
		positionButton.setEnabled(false);
		
		//Add Buttons to relevant containers
		//westCommandContainer.addComponent(advButton);
		westCommandContainer.addComponent(positionButton);
		westCommandContainer.addComponent(pauseButton);
		//westCommandContainer.addComponent(fuelButton);
		//westCommandContainer.addComponent(gumButton);
		westCommandContainer.addComponent(stratButton);
		westCommandContainer.addComponent(exitButton);
		
		southCommandContainer.addComponent(brakeButton);
		southCommandContainer.addComponent(leftButton);
		southCommandContainer.addComponent(rightButton);
		southCommandContainer.addComponent(accelButton);
		//southCommandContainer.addComponent(crashButton);
		//southCommandContainer.addComponent(tickButton);
		
		//Keybindings
		addKeyListener('a', accelerate);
		addKeyListener('b', brake);
		addKeyListener('l', left);
		addKeyListener('r', right);
		//addKeyListener('f', fuel);
		//addKeyListener('t', tick);
		addKeyListener('e', exit);
		addKeyListener('p', pause);

		this.show();
		
		if(gw.getSound())
			idleSound.play();
		
		mapWidth = mv.getWidth();
		mapHeight = mv.getHeight();
		
		gw.init(mapWidth/2, mapHeight/2);
	}

	public void run() {
		if(!pauseFlag)
			gw.tick();
		
		if(!gw.getSound())
			idleSound.pause();
	}
	
	public void pause(){
		
		//Disable/Enable buttons
		accelButton.setEnabled(pauseFlag);
		brakeButton.setEnabled(pauseFlag);
		leftButton.setEnabled(pauseFlag);
		rightButton.setEnabled(pauseFlag);
		//fuelButton.setEnabled(pauseFlag)
		
		positionButton.setEnabled(!pauseFlag);
		
		pauseFlag = !pauseFlag;
		
		//Disable keys
		if(pauseFlag){
			removeKeyListener('a', accelerate);
			removeKeyListener('b', brake);
			removeKeyListener('l', left);
			removeKeyListener('r', right);
			//removeKeyListener('f', fuel);
			//removeKeyListener('t', tick);
			removeKeyListener('e', exit);
			
			pauseButton.setText("Play");
			
			idleSound.pause();
			}
		else{
			addKeyListener('a', accelerate);
			addKeyListener('b', brake);
			addKeyListener('l', left);
			addKeyListener('r', right);
			//addKeyListener('f', fuel);
			//addKeyListener('t', tick);
			addKeyListener('e', exit);
			
			pauseButton.setText("Pause");
			
			if(gw.getSound())
				idleSound.play();
		}
	}
	
	public boolean isPaused(){
		return pauseFlag;
	}
	
}
