package launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;

import Display.Display;
import GUI.FontLoader;
import GUI.ImageLoader;
import GUI.Text;
import engine.Game;
import engine.GameListener;
import input.KeyManger;
import input.MouseManager;
import model.cards.minions.Minion;
import model.cards.spells.Spell;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import view.Handler;


public class GameGra implements Runnable, GameListener {

	private Display display;
	private int w, h;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	private Handler handler;
	
	private KeyManger keyManager;
	private MouseManager mouseManager;
	private HeroSel1 heroSel1;
	private boolean done=true;
	private boolean GameOver=false;
	private boolean donesound=false;
	private Font fontend ;
	private Font fontExit;
	private Font fontAgain;
    private PlayersNames names;
	private boolean f=true;
	private boolean ff=true;
	
	private BufferedImage board;
	
	private Clip audioClip;
	private Font f3in;

	
	public GameGra(String title, int width, int height){
		this.title = title;
		
		keyManager = new KeyManger();
		mouseManager = new MouseManager();
		board=ImageLoader.loadImage("/textures/Board3.jpg");


	}
	
	private synchronized void init(){
		display = new Display(title);
		this.w=Display.frame.getWidth();
		this.h=Display.frame.getHeight();
		
				
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		
		this.names=new PlayersNames(this.display.getFrame());
		heroSel1=new HeroSel1(this);
		mouseManager.setHerosel1(heroSel1);
		mouseManager.setNames(names);
		this.fontend =FontLoader.loadFont("res/fonts/Royale Kingdom DEMO.ttf", Launcher.getcorW(100));
        this.fontExit = FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(50));
        this.fontAgain = FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(40));
		f3in=FontLoader.loadFont("res/fonts/SHADSER.TTF", Launcher.getcorW(30));
		
		
	}
	
	private void tick(){
		keyManager.tick();
	}
	
	private synchronized void render(){


		if(f) {
			this.names.start();
			f=false;
		}
		if(!this.names.isRunning()) {
			
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getFrame().add(display.getCanvas());
			display.getFrame().pack();
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		if(ff) {
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    GraphicsDevice screen = ge.getDefaultScreenDevice();
		    
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(
	            RenderingHints.KEY_TEXT_ANTIALIASING,
	            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	        g2d.setRenderingHint(
	            RenderingHints.KEY_RENDERING,
	            RenderingHints.VALUE_RENDER_QUALITY);
		    
		    screen.setFullScreenWindow(Display.frame);
		    ff=false;
		}

		//Clear Screen
		if(Game.getT1()!=null&&Game.getT1().isAlive())return;
		g.clearRect(0, 0, w, h);
		//Draw Here


		if(heroSel1.isRunning())
			heroSel1.render(g);
		
		else {

			if(this.GameOver) {
				renderEndGame(g);
			}
			else {
	        	g.drawImage(board, 0,0, w,h, null);
               //System.out.println()
		       this.handler.getGame().getCurrentHero().render(g,"c");
               this.handler.getGame().getOpponent().render(g,"o");

		       handler.render(g,this.mouseManager,this.keyManager);
		       if(this.handler.getGame().isHeroPower()) {
				  Text.drawString(g,"Hero Power ", 0 ,850, false, Color.white, f3in);
				  Text.drawString(g,"Is ready Now ", 0 ,900, false, Color.white, f3in);
				  Text.drawString(g,"choose the Target ", 0 ,950, false, Color.white, f3in);
		       }
		       else if(this.handler.getGame().getCard1()!=null&&this.handler.getGame().getCard1() instanceof Minion) {
					  Text.drawString(g,"The Minion ", 0 ,850, false, Color.white, f3in);
					  Text.drawString(g,"Is ready Now ", 0 ,900, false, Color.white, f3in);
					  Text.drawString(g,"attack the Target ", 0 ,950, false, Color.white, f3in);
		       }
		       else if(this.handler.getGame().getCard1()!=null&&this.handler.getGame().getCard1() instanceof Spell) {
					  Text.drawString(g,"The Spell ", 0 ,850, false, Color.white, f3in);
					  Text.drawString(g,"Is ready Now ", 0 ,900, false, Color.white, f3in);
					  Text.drawString(g,"choose the Target ", 0 ,950, false, Color.white, f3in);
		       }
		       else if(this.handler.getGame().getCard2()!=null&&this.handler.getGame().getCard1() instanceof Minion) {
					  Text.drawString(g,"The Minion ", 0 ,850, false, Color.white, f3in);
					  Text.drawString(g,"Is ready Now ", 0 ,900, false, Color.white, f3in);
					  Text.drawString(g,"attack the Target ", 0 ,950, false, Color.white, f3in);
		       }
		       else if(this.handler.getGame().getCard2()!=null&&this.handler.getGame().getCard1() instanceof Spell) {
					  Text.drawString(g,"The Spell ", 0 ,850, false, Color.white, f3in);
					  Text.drawString(g,"Is ready Now ", 0 ,900, false, Color.white, f3in);
					  Text.drawString(g,"choose the Target ", 0 ,950, false, Color.white, f3in);
		       }

		        
		       if(this.handler.getGame().getExceptionGraphics().isRunning()) {
		    	   this.handler.getGame().getExceptionGraphics().render(g);
		       }
		       
			}
			
		}
		//End Drawing!
		bs.show();
		g.dispose();
		}
	}
	
	public synchronized void renderEndGame(Graphics g) {
		if(!this.donesound) {
			Launcher.getAudioClip().close();
			File audioFile = new File("res/EndGame Sound.wav");
			boolean playCompleted = false;
			 
		    try {
		        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

		        AudioFormat format = audioStream.getFormat();

		        DataLine.Info info = new DataLine.Info(Clip.class, format);

		        this.audioClip = (Clip) AudioSystem.getLine(info);

		        audioClip.open(audioStream);

		        audioClip.start();
		         
		        audioClip.loop(Clip.LOOP_CONTINUOUSLY);

		         
		    } catch (UnsupportedAudioFileException ex) {
		        ex.printStackTrace();
		    } catch (LineUnavailableException ex) {
		        ex.printStackTrace();
		    } catch (IOException ex) {
		    	ex.printStackTrace();
		    }
		    this.donesound=true;
		}
    	g.drawImage(ImageLoader.loadImage("/EndGame Back.jpg"), 0,0, w,h, null);
    	if(this.handler.getGame().getWinner() instanceof Mage)
    	     g.drawImage(this.heroSel1.getMageImage(),Launcher.getcorW(500),Launcher.getcorH(350),Launcher.getcorW(290),Launcher.getcorH(400) ,null);
    	else if(this.handler.getGame().getWinner() instanceof Hunter)
    		g.drawImage(this.heroSel1.getHunterImage(),Launcher.getcorW(500),Launcher.getcorH(350),Launcher.getcorW(290),Launcher.getcorH(400), null);
    	else if(this.handler.getGame().getWinner() instanceof Paladin)
    		g.drawImage(this.heroSel1.getPaladinImage(),Launcher.getcorW(500),Launcher.getcorH(350),Launcher.getcorW(290),Launcher.getcorH(400), null);
    	else if(this.handler.getGame().getWinner() instanceof Priest)
    		g.drawImage(this.heroSel1.getPriestImage(),Launcher.getcorW(500),Launcher.getcorH(350),Launcher.getcorW(290),Launcher.getcorH(400) ,null);
    	else if(this.handler.getGame().getWinner() instanceof Warlock)
    		g.drawImage(this.heroSel1.getWarlockImage(),Launcher.getcorW(500),Launcher.getcorH(350),Launcher.getcorW(290),Launcher.getcorH(400), null);
    	 g.setColor(Color.red);
    	 g.fillRect(Launcher.getcorW(1600), Launcher.getcorH(50), Launcher.getcorW(150), Launcher.getcorH(70));
   	     Text.drawString(g,this.handler.getGame().getWinnerName()+"", Launcher.getcorW(100),Launcher.getcorH(100), false,Color.white,fontend);
   	     Text.drawString(g,"VICTORY"+"", Launcher.getcorW(480),Launcher.getcorH(250), false,Color.white,fontend);
   	     Text.drawString(g,"Exit"+"", Launcher.getcorW(1625),Launcher.getcorH(100), false,Color.white,fontExit);
   	     g.setColor(Color.green);
 	     g.fillRect(Launcher.getcorW(1600)-Launcher.getcorW(170), Launcher.getcorH(50), Launcher.getcorW(150), Launcher.getcorH(70));
   	     Text.drawString(g,"Again"+"", Launcher.getcorW(1445),Launcher.getcorH(100), false,Color.white,fontAgain);

	}
	
	public Display getDisplay() {
		return display;
	}

	public void run(){
		
		init();
		
		
		int fps = 180;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				//System.out.println(keyManager.aDown);
				tick();
				render();
				ticks++;
				delta--;
				
			}
			
			if(timer >= 1000000000){
				//System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
	
	@Override
	public void onGameOver() {
		this.GameOver=true;
	}
	
	

	@Override
	public void again() {
		Launcher.getAudioClip().close();
		this.audioClip.close();
		
		File audioFile = new File("res/Hearthstone.wav");
		boolean playCompleted = false;
		 
	    try {
	    	//audioClip=Luncher.getAudioClip();
	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

	        AudioFormat format = audioStream.getFormat();

	        DataLine.Info info = new DataLine.Info(Clip.class, format);

	        Launcher.setAudioClip((Clip) AudioSystem.getLine(info));

	        Launcher.getAudioClip().open(audioStream);
	       // setVol((float) 6);
	        //setVol((float) 0.1);
	        Launcher.getAudioClip().start();
	         
	        Launcher.getAudioClip().loop(Clip.LOOP_CONTINUOUSLY);
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
        Display.frame.dispose();
		GameGra g=new GameGra("Test",1850, 900);
		g.start();
		
	}

	

	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.setName("Render");
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public MouseManager getMouseManager(){
		return mouseManager;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public PlayersNames getNames() {
		return names;
	}

	@Override
	public void exit() {
        System.exit(0);		
	}
	
	


	
}
