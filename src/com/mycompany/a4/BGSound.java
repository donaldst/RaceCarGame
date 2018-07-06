package com.mycompany.a4;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable{
private Media m;
	
	public BGSound(String fname){
		try{
			String ftype = fname.substring(fname.indexOf('.')+1);
			
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fname);
			
			m = MediaManager.createMedia(is, "audio/" + ftype, this);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void pause(){
		m.pause();
	}
	
	public void play(){
		m.play();
	}
	
	public void run(){
		m.setTime(0);
		play();
	}
}
