package Display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import launcher.Launcher;

public class Display {

	public static JFrame frame;
	private Canvas canvas;
	private Canvas canvas2;
	
	private String title;
	private int width, height;
	public static boolean running=true;
	
	public Display(String title){
		this.title = title;

		
		createDisplay();
	}
	
	private void createDisplay(){
		frame = new JFrame(title);
		frame.setUndecorated(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//frame.setLayout(null);
		//frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.width=frame.getWidth();
		this.height=frame.getHeight();
		canvas = new Canvas();
		canvas2=new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		canvas2.setPreferredSize(new Dimension(width, height));
		canvas2.setMaximumSize(new Dimension(width, height));
		canvas2.setMinimumSize(new Dimension(width, height));
		canvas2.setFocusable(false);
		
		
//		frame.add(canvas);
//		frame.pack();
	}

	public Canvas getCanvas(){
		return canvas;
	}
	
	
	public Canvas getCanvas2() {
		return canvas2;
	}

	public JFrame getFrame(){
		return frame;
	}
	
}
	