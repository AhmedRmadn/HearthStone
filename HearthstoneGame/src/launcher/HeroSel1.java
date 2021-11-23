package launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.FontLoader;
import GUI.ImageLoader;
import GUI.PlaySound;
import GUI.Text;
import view.Handler;
import model.heroes.*;
import Display.Display;

public class HeroSel1 {
	

	private boolean running=true;
	private String name1;
	private String name2;
	private GameGra gameGra;
	private PlaySound Sound =new PlaySound();
	private int h=Display.frame.getHeight();
	private int w=Display.frame.getWidth();
	private Font font1= FontLoader.loadFont("res/fonts/Royale Kingdom DEMO.ttf", (int)(w*((float) (100.0/1920.0))));
	private BufferedImage back;
	private BufferedImage mageImage;
	private BufferedImage hunterImage;
	private BufferedImage paladinImage;
	private BufferedImage priestImage;
	private BufferedImage warlockImage;
	private final Rectangle hunterBoundes=new Rectangle((int)(w*((float) (172.0/1920.0))), (int)(h*((float) (300.0/1080.0))), (int)(w*((float) (225.0/1920.0))), (int)(h*((float) (280.0/1080.0))));
	private final Rectangle MageBoundes=new Rectangle((int)(w*((float) (822.0/1920.0))), (int)(h*((float) (300.0/1080.0))), (int)(w*((float) (225.0/1920.0))), (int)(h*((float) (280.0/1080.0))));
	private final Rectangle PaladinBoundes=new Rectangle((int)(w*((float) (1472.0/1920.0))), (int)(h*((float) (300.0/1080.0))), (int)(w*((float) (225.0/1920.0))), (int)(h*((float) (280.0/1080.0))));
	private final Rectangle PriestBoundes=new Rectangle((int)(w*((float) (497.0/1920.0))), (int)(h*((float) (670.0/1080.0))), (int)(w*((float) (225.0/1920.0))), (int)(h*((float) (280.0/1080.0))));
	private final Rectangle WarlockBoundes=new Rectangle((int)(w*((float) (1147.0/1920.0))), (int)(h*((float) (670.0/1080.0))), (int)(w*((float) (225.0/1920.0))), (int)(h*((float) (280.0/1080.0))));
	
	private final Rectangle hunterBoundesHov=new Rectangle((int)(w*((float) (145.0/1920.0))), (int)(h*((float) (245.0/1080.0))),(int)(w*((float) (300.0/1920.0))), (int)(h*((float) (380.0/1080.0))));
	private final Rectangle MageBoundesHov=new Rectangle((int)(w*((float) (795.0/1920.0))), (int)(h*((float) (245.0/1080.0))),(int)(w*((float) (300.0/1920.0))), (int)(h*((float) (380.0/1080.0))));
	private final Rectangle PaladinBoundesHov=new Rectangle((int)(w*((float) (1445.0/1920.0))), (int)(h*((float) (245.0/1080.0))),(int)(w*((float) (300.0/1920.0))), (int)(h*((float) (380.0/1080.0))));
	private final Rectangle PriestBoundesHov=new Rectangle((int)(w*((float) (470.0/1920.0))), (int)(h*((float) (615.0/1080.0))),(int)(w*((float) (300.0/1920.0))), (int)(h*((float) (380.0/1080.0))));
	private final Rectangle WarlockBoundesHov=new Rectangle((int)(w*((float) (1120.0/1920.0))), (int)(h*((float) (615.0/1080.0))),(int)(w*((float) (300.0/1920.0))), (int)(h*((float) (380.0/1080.0))));
	private Rectangle draw;

	private boolean wait;
	private boolean test;
	
	private String hero1Sel;
	private String hero2Sel;
	private Thread t1;
	private Thread t2;
	private Thread t3;
	private Hero hero1;
	private Hero hero2;
	
	private WaitGraphics waitGraphics;
	public HeroSel1(GameGra gameGra) {
		this.gameGra=gameGra;
		this.name1=this.gameGra.getNames().getName1();
		this.name2=this.gameGra.getNames().getName2();
		back=ImageLoader.loadImage("/textures/Hero1Back.jpg");
		mageImage=ImageLoader.loadImage("/textures/HerosFirst/Mage.png");
		hunterImage=ImageLoader.loadImage("/textures/HerosFirst/Hunter.png");
		paladinImage=ImageLoader.loadImage("/textures/HerosFirst/Paladin.png");
		priestImage=ImageLoader.loadImage("/textures/HerosFirst/Priest.png");
		warlockImage=ImageLoader.loadImage("/textures/HerosFirst/Warlock.png");
		waitGraphics=new WaitGraphics();
	}
	public void render(Graphics g) {
		g.drawImage(back,0,0,w,h, null);
		if(hero1Sel==null||(hero2Sel==null&&PlaySound.getClip().isRunning())) { 
			Text.drawString(g, this.gameGra.getNames().getName1()+" Choose Your Hero", (int)(w*((float) (5.0/96.0))),(int)(h*((float) (5.0/54.0))), false,Color.white, font1);
		}
		
		else {
			Text.drawString(g,this.gameGra.getNames().getName2()+" Choose Your Hero", (int)(w*((float) (5.0/96.0))),(int)(h*((float) (5.0/54.0))), false,Color.blue, font1);
		}
		if(this.draw!=null) {
			g.setColor(Color.GREEN);
            g.drawRect(this.draw.x,this.draw.y,this.draw.width,this.draw.height);
		}
		g.drawImage(hunterImage,(int)(w*((float) (150.0/1920.0))), (int)(h*((float) (250.0/1080.0))),(int)(w*((float) (290.0/1920.0))), (int)(h*((float) (400.0/1080.0))), null);
		g.drawImage(mageImage, (int)(w*((float) (800.0/1920.0))), (int)(h*((float) (250.0/1080.0))),(int)(w*((float) (290.0/1920.0))),(int)(h*((float) (400.0/1080.0))),null);
		g.drawImage(paladinImage,(int)(w*((float) (1450.0/1920.0))), (int)(h*((float) (250.0/1080.0))),(int)(w*((float) (290.0/1920.0))),(int)(h*((float) (400.0/1080.0))),null);
		g.drawImage(priestImage, (int)(w*((float) (475.0/1920.0))), (int)(h*((float) (620.0/1080.0))),(int)(w*((float) (290.0/1920.0))), (int)(h*((float) (400.0/1080.0))), null);
		g.drawImage(warlockImage, (int)(w*((float) (1125.0/1920.0))), (int)(h*((float) (620.0/1080.0))),(int)(w*((float) (290.0/1920.0))), (int)(h*((float) (400.0/1080.0))) ,null);

		}
	    
	public void action(int x,int y) throws IOException, CloneNotSupportedException, InterruptedException   {

		if(this.hunterBoundes.contains(x,y)) {
			//wait=true;
			if(hero1Sel==null) {
				hero1Sel="Hunter";
				Sound.playSound(new File("res/HunterStart.wav"));
				draw=null;
				t1=new Thread(r1);
				t1.start();

			}
			else if(hero2Sel==null){
				hero2Sel="Hunter";
				Sound.playSound(new File("res/HunterStart.wav"));
				draw=null;
				t2=new Thread(r2);
				t2.start();

			}
	
		}
		else if(this.MageBoundes.contains(x,y)) {
			//wait=true;

			if(hero1Sel==null) {
                hero1Sel="Mage";
				Sound.playSound(new File("res/MageStart.wav"));
				draw=null;
				t1=new Thread(r1);
				t1.start();
			}
			else if(hero2Sel==null) {
                hero2Sel="Mage";
				Sound.playSound(new File("res/MageStart.wav"));
				draw=null;
				t2=new Thread(r2);
				t2.start();
			}
			
		}
		else if(this.PaladinBoundes.contains(x,y)) {
			wait=true;

			if(hero1Sel==null) {
                hero1Sel="Paladin";
				Sound.playSound(new File("res/PaladinStart.wav"));
				draw=null;
				t1=new Thread(r1);
				t1.start();
			}
			else if(hero2Sel==null){
				hero2Sel="Paladin";
				Sound.playSound(new File("res/PaladinStart.wav"));
				draw=null;
				t2=new Thread(r2);
				t2.start();
			}
			
		}
		else if(this.PriestBoundes.contains(x,y)) {
			wait=true;

			if(hero1Sel==null) {
                hero1Sel="Priest";
				Sound.playSound(new File("res/PriestStart.wav"));	
				draw=null;
				t1=new Thread(r1);
				t1.start();

			}
			else if(hero2Sel==null) {
				hero2Sel="Priest";
				Sound.playSound(new File("res/PriestStart.wav"));	
				draw=null;
				t2=new Thread(r2);
				t2.start();
						
			
		}
		}
		else if(this.WarlockBoundes.contains(x,y) ) {
			wait=true;

			if(hero1Sel==null) {
                hero1Sel="Warlock";
				Sound.playSound(new File("res/WarlockStart.wav"));
				draw=null;
				t1=new Thread(r1);
				t1.start();
			}
			else if(hero2Sel==null){
                hero2Sel="Warlock";
				Sound.playSound(new File("res/WarlockStart.wav"));
				draw=null;
				t2=new Thread(r2);
				t2.start();

	         }
		}
			
	}
	private void start() {
		
		this.gameGra.setHandler(new Handler(hero1,hero2));
		this.gameGra.getMouseManager().setGame(this.gameGra.getHandler().getGame());
		this.gameGra.getHandler().getGame().setListener(this.gameGra);
		running=false;
		t1=null;
		wait=false;

	}
	
	public void actiontemp(int x,int y) {
           try {
			this.action(x, y);
		} catch (IOException | CloneNotSupportedException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void hovring(int x,int y) {
		//System.out.println(x+" "+y);
		if(this.hunterBoundes.contains(x,y)) {				
		   this.draw=this.hunterBoundesHov;
		}
		else if(this.MageBoundes.contains(x,y)) {
          this.draw=this.MageBoundesHov;
		}
		else if(this.PaladinBoundes.contains(x,y)) {
          this.draw=this.PaladinBoundesHov;
		}
		else if(this.PriestBoundes.contains(x,y)) {
         this.draw=this.PriestBoundesHov;
		}
		else if(this.WarlockBoundes.contains(x,y) ) {
         this.draw=this.WarlockBoundesHov;
		}
		else {
			this.draw=null;
		}
			
		
	}
	


	public Hero getHero1() {
		return hero1;
	}

	public void setHero1(Hero hero1) {
		this.hero1 = hero1;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	public Thread getT1() {
		return t1;
	}

	public BufferedImage getMageImage() {
		return mageImage;
	}
	public BufferedImage getHunterImage() {
		return hunterImage;
	}
	public BufferedImage getPaladinImage() {
		return paladinImage;
	}
	public BufferedImage getPriestImage() {
		return priestImage;
	}
	public BufferedImage getWarlockImage() {
		return warlockImage;
	}
	
	Runnable r1=new Runnable() {

		@Override
		public void run() {
			try {
			if(hero1Sel.equals("Hunter")) {
					hero1=new Hunter();
			}
			else if(hero1Sel.equals("Mage")) {
				hero1=new Mage();
			}
			else if(hero1Sel.equals("Paladin")) {
				hero1=new Paladin();
			}
			else if(hero1Sel.equals("Priest")) {
				hero1=new Priest();
			}
			else if(hero1Sel.equals("Warlock")) {
				hero1=new Warlock();
			}
			else {
				///System.out.println("error "+hero1Sel);
				System.exit(0);
			}
			}
		 catch (IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
				
	};
	Runnable r2=new Runnable() {

		@Override
		public void run() {
			try {
			if(hero2Sel.equals("Hunter")) {
					hero2=new Hunter();
					t3=new Thread(r3);
					t3.start();
			}
			else if(hero2Sel.equals("Mage")) {
				hero2=new Mage();
				t3=new Thread(r3);
				t3.start();
			}
			else if(hero2Sel.equals("Paladin")) {
				hero2=new Paladin();
				t3=new Thread(r3);
				t3.start();
			}
			else if(hero2Sel.equals("Priest")) {
				hero2=new Priest();
				t3=new Thread(r3);
				t3.start();
			}
			else if(hero2Sel.equals("Warlock")) {
				hero2=new Warlock();
				t3=new Thread(r3);
				t3.start();
			}
			else {
				System.out.println("error "+hero2Sel);
				System.exit(0);
			}
			}
		 catch (IOException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	};
	Runnable r3=new Runnable() {

		@Override
		public void run() {
			start();
			
		}
		
	};
	
	


}
