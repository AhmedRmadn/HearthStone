package launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Display.Display;
import GUI.FontLoader;

public class Launcher {
	private static Clip audioClip;


	public static void main(String[] args)  {
	// sound	
	File audioFile = new File("res/Hearthstone.wav");
	boolean playCompleted = false;
	 
    try {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(Clip.class, format);

         audioClip = (Clip) AudioSystem.getLine(info);

        audioClip.open(audioStream);
       // setVol((float) 6);
        //setVol((float) 0.1);
        audioClip.start();
         
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        //audioClip.close();
        
         
        //
         
    } catch (UnsupportedAudioFileException ex) {
        System.out.println("The specified audio file is not supported.");
        ex.printStackTrace();
    } catch (LineUnavailableException ex) {
        System.out.println("Audio line for playing back is unavailable.");
        ex.printStackTrace();
    } catch (IOException ex) {
        System.out.println("Error playing the audio file.");
        ex.printStackTrace();
    }
   // 

	GameGra g=new GameGra("Test",1850, 900);
	g.start();



	}
	
	public static Rectangle getRect(int x,int y,int w,int h) {
		return new Rectangle((int)(Display.frame.getWidth()*((float) (x/1850.0))), (int)(Display.frame.getHeight()*((float) (y/900.0))), (int)(Display.frame.getWidth()*((float) (w/1850.0))), (int)(Display.frame.getHeight()*((float) (h/900.0))));
	}
	
	public static Point getPoint(int x,int y) {
		return new Point((int)(Display.frame.getWidth()*((float) (x/1850.0))), (int)(Display.frame.getHeight()*((float) (y/900.0))));
	}
	
	public static int getcorW(int x) {
		return (int)(Display.frame.getWidth()*((float) (x/1850.0)));
	}
	public static int getcorH(int x) {
		return (int)(Display.frame.getHeight()*((float) (x/900.0)));
	}
	
	


	public static Clip getAudioClip() {
		return audioClip;
	}


	public static void setAudioClip(Clip audioClip) {
		Launcher.audioClip = audioClip;
	}

	public static void setVol(float d) {
		System.out.println("Gg");
		FloatControl gain =(FloatControl)audioClip.getControl(FloatControl.Type.MASTER_GAIN);
		float dB=(float)(Math.log(d)/(Math.log(10)*20));
		gain.setValue(d);
	}
	

}
