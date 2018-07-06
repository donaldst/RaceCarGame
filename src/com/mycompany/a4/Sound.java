package com.mycompany.a4;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
	private Media m;
	
	public Sound(String fname){
		try{
			String ftype = fname.substring(fname.indexOf('.')+1);
			
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fname);
			
			m = MediaManager.createMedia(is, "audio/" + ftype);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void play(){
		m.setTime(0);
		m.play();
	}
}
