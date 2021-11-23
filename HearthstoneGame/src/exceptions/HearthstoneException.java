package exceptions;

import java.io.File;

import GUI.PlaySound;


abstract public class HearthstoneException extends Exception {
	protected PlaySound Sound =new PlaySound();

	public HearthstoneException() {
		Sound.playSound(new File("res/Exception sound.wav"));
	}
	public HearthstoneException(String s) {
		super(s);
		Sound.playSound(new File("res/Exception sound.wav"));
	}

}
