package GUI;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlaySound implements Runnable{
	
	private static Clip clip;
	private File sound;
	private Thread t1;
	
	

	public PlaySound() {
		
	}
	
	
	public void playSound(File sound) {
		this.sound=sound;
	    this.tempRun();
	}
	
	
	
	public static Clip getClip() {
		return clip;
	}

    @Override
	public void run() {
		try {
			//running=true;
		    clip= AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
			//clip.isRunning()

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void tempRun() {
		this.t1=new Thread(this);
		t1.setName("Sound");
		t1.start();
	}


	public Thread getT1() {
		return t1;
	}
	
	

	
	

}
