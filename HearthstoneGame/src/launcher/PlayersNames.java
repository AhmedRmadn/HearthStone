package launcher;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import Display.Display;

import GUI.FontLoader;

public class PlayersNames {
	
	private static String name1;
	private static String name2;
	private boolean running;
	private JFrame frame;
	private int h,w;

	
	public PlayersNames(JFrame frame) {
		this.frame=frame;
		w=frame.getWidth();
		h=frame.getHeight();
		this.running=true;
	}
	
	public void start() {
		//(int)((w*h)*((float) (1.0/46080.0)))
		//
		   Font font = FontLoader.loadFont("res/fonts/Royale Kingdom DEMO.ttf",(int)(w*((float) (3.0/128.0))) );
		    Border loweredbevel = BorderFactory.createLoweredBevelBorder();					
					//ImageIcon ba=new ImageIcon("res/Start/Start.jpg");
		            ImageIcon ba = getImageIcon(new ImageIcon("res/Start/Start.jpg"));
					JLabel Back=new JLabel("",ba,JLabel.CENTER);
					Back.setBounds(0,0,frame.getWidth(),frame.getHeight());
				    JButton  submit = new JButton(getImageIcon(new ImageIcon("res/Start/StartButton.jpg"),(int)(w*((float) (5.0/16.0))), (int)(h*((float) (7.0/108.0)))));
		            submit.setBounds((int)(w*((float) (67.0/192.0))), (int)(h*((float) (95.0/108.0))), (int)(w*((float) (5.0/16.0))), (int)(h*((float) (7.0/108.0))));
					
				    JTextArea t1=new JTextArea();  
		            t1.setBounds((int)(w*((float) (67.0/192.0))), (int)(h*((float) (25.0/36.0))), (int)(w*((float) (5.0/16.0))), (int)(h*((float) (7.0/108.0))));			    
		            t1.setFont(font);
		            t1.setLineWrap(true);
		            t1.setWrapStyleWord(true);
				    JTextArea t2=new JTextArea(); 
			        t2.setBounds((int)(w*((float) (67.0/192.0))), (int)(h*((float) (85.0/108.0))), (int)(w*((float) (5.0/16.0))), (int)(h*((float) (7.0/108.0))));
				    t2.setFont(font);
				    t2.setBackground(new Color(232,187,118));
				    t1.setBackground(new Color(232,187,118));
				    t2.setBorder(loweredbevel);
				    t1.setBorder(loweredbevel);
				    
				    JLabel l1=new JLabel("First King Name");
				    JLabel l2=new JLabel("Secend King Name");
				    //(int)(w*((float) (3.0/32.0))), (int)(h*((float) (85.0/108.0))), (int)(w*((float) (5.0/16.0))), (int)(h*((float) (7.0/108.0)))
				    l1.setBounds((int)(w*((float) (25.0/192.0))), (int)(h*((float) (25.0/36.0))), (int)(w*((float) (5.0/16.0))), (int)(h*((float) (7.0/108.0))));
				    l2.setBounds((int)(w*((float) (25.0/192.0))), (int)(h*((float) (85.0/108.0))), (int)(w*((float) (5.0/16.0))), (int)(h*((float) (7.0/108.0))));
				    l1.setFont(font);
				    l2.setFont(font);


		 
				    frame.add(submit);
				    frame.add(t1);
				    frame.add(t2);
				    frame.add(l1);
				    frame.add(l2);
				    frame.add(Back);
				    Display.frame.revalidate();
				    Display.frame.repaint();
				   // frame.setVisible(true);
				    

				    submit.addActionListener(new ActionListener() {
			            
						@Override
						public void actionPerformed(ActionEvent e) {
							
							if(!t1.getText().equals("")&&!t2.getText().equals("")&&t1.getText().length()<10&&t2.getText().length()<10) {
								
								name1=t1.getText();
								name2=t2.getText();
								running=false;
								frame.getContentPane().removeAll();
								frame.revalidate();
								frame.repaint();
							}
							
							else if(t1.getText().length()>10||t2.getText().length()>10)
								JOptionPane.showMessageDialog(null, "Names Not Correct More than 10 characters");			
							else
								JOptionPane.showMessageDialog(null, "Names Not Correct");
						}
				    	
				    });
		
	}
	private static Image getScaledImage(Image srcImg, int w, int h){
		//System.out.println(w+" "+h);
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	private static ImageIcon getImageIcon(ImageIcon imageIcon) {
		Image bb=getScaledImage(imageIcon.getImage(),Display.frame.getWidth(), Display.frame.getHeight());
		return new ImageIcon(bb);
	}
	private static ImageIcon getImageIcon(ImageIcon imageIcon,int w,int h) {
		Image bb=getScaledImage(imageIcon.getImage(),w, h);
		return new ImageIcon(bb);
	}


	public static String getName1() {
		return name1;
	}

	public static String getName2() {
		return name2;
	}

	public boolean isRunning() {
		return running;
	}
	
	

}
