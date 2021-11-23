package launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import GUI.FontLoader;
import GUI.ImageLoader;
import GUI.Text;
import exceptions.FullHandException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.Spell;

public class ExceptionGraphics {
	
	private BufferedImage TheMinionHas0Attack;
	private BufferedImage TheMinionCanNotAttackthisturn;
	private BufferedImage TheMinionCanNotAttackMorethanonce;
	private BufferedImage YoucannotAttackyourself;
	private BufferedImage IcehowlCanNotAttackHero;
	private BufferedImage ThereisaTauntintheField;
	private BufferedImage YouHavaNotEnoughManaCrystals;
	private BufferedImage TheFieldhas7MinionsNow;
	private BufferedImage TheHeroPowerAlreadyUsedthisTurn;
	private BufferedImage SpellInvalid;
	private BufferedImage TheHand;
	private BufferedImage NotS;
	private BufferedImage image;
	private final int w=(int)(Display.Display.frame.getWidth()*(700/1920.0));
	private final int h=(int)(Display.Display.frame.getHeight()*(1000/1080.0));
	private final int x=(int)(Display.Display.frame.getWidth()*(700/1920.0));
	private final int y=(int)(Display.Display.frame.getHeight()*(40/1080.0));
	private Rectangle bounds=new Rectangle(x+(int)(Display.Display.frame.getWidth()*(257/1920.0)),y+(int)(Display.Display.frame.getHeight()*(853/1080.0)),(int)(Display.Display.frame.getWidth()*(188/1920.0)),(int)(Display.Display.frame.getHeight()*(113/1080.0)));
    private boolean hand=false;
	Font f30=FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(40));

	
	private boolean running=false;
	private Exception e;
	
	public ExceptionGraphics() {
		init();
	}
	private void init() {
		this.TheMinionHas0Attack=ImageLoader.loadImage("/textures/Exception/The Minion has 0 Attack.jpg");
		this.TheMinionCanNotAttackthisturn=ImageLoader.loadImage("/textures/Exception/The Minion Can Not Attack this turn.jpg");
		this.TheMinionCanNotAttackMorethanonce=ImageLoader.loadImage("/textures/Exception/The Minion Can Not Attack More than once.jpg");
		this.YoucannotAttackyourself=ImageLoader.loadImage("/textures/Exception/You can not Attack yourself.jpg");
		this.IcehowlCanNotAttackHero=ImageLoader.loadImage("/textures/Exception/Icehowl Can Not Attack Hero.jpg");
		this.ThereisaTauntintheField=ImageLoader.loadImage("/textures/Exception/There is a Taunt in the Field.jpg");
		this.YouHavaNotEnoughManaCrystals=ImageLoader.loadImage("/textures/Exception/You Hava Not Enough ManaCrystals.jpg");
		this.TheFieldhas7MinionsNow=ImageLoader.loadImage("/textures/Exception/The Field has 7 Minions Now.jpg");
		this.TheHeroPowerAlreadyUsedthisTurn=ImageLoader.loadImage("/textures/Exception/The HeroPower Already Used this Turn.jpg");
		this.TheHand=ImageLoader.loadImage("/textures/Exception/the Hand has 10 cards.jpg");
		this.NotS=ImageLoader.loadImage("/textures/Exception/You Can not target the Hand.jpg");
		this.SpellInvalid=ImageLoader.loadImage("/textures/Exception/Spell Invalid.jpg");
	}
	
	public void render(Graphics g) {

		
		g.drawImage(this.image,x,y,w,h,null);
		if(hand)
			renderHand(g);
	   
		// g.fillRect((int)bounds.getX(),(int) bounds.getY(), (int)bounds.getWidth(),(int) bounds.getHeight());
		
	}
	
	public void start(Exception e) {
		if(e instanceof FullHandException ) {
			this.image=this.TheHand;
			hand=true;
			this.e=e;
			running=true;
		}
	   else if(e.getMessage().equals("The Minion has 0 Attack")) {
			running=true;
			this.image=this.TheMinionHas0Attack;
		}
		else if(e.getMessage().equals("The Minion Can Not Attack this turn")) {
			running=true;
			this.image=	this.TheMinionCanNotAttackthisturn;
		}
		else if(e.getMessage().equals("The Minion Can Not Attack More than once")) {
			running=true;
			this.image=this.TheMinionCanNotAttackMorethanonce;
		}
		else if(e.getMessage().equals("You can not Attack yourself")) {
			running=true;
			this.image=this.YoucannotAttackyourself;
		}
		else if(e.getMessage().equals("Icehowl Can Not Attack Hero")) {
			running=true;
			this.image=this.IcehowlCanNotAttackHero;
		}
		else if(e.getMessage().equals("There is a Taunt in the Field")) {
			running=true;
			this.image=this.ThereisaTauntintheField;
		}
		else if(e.getMessage().equals("You Hava Not Enough ManaCrystals")) {
			running=true;
			this.image=this.YouHavaNotEnoughManaCrystals;
		}
		else if(e.getMessage().equals("The Field has 7 Minions Now")) {
			running=true;
			this.image=this.TheFieldhas7MinionsNow;
		}
		else if(e.getMessage().equals("The HeroPower Already Used this Turn")) {
			running=true;
			this.image=this.TheHeroPowerAlreadyUsedthisTurn;
		}
		
		else if(e.getMessage().equals("you can Not Target The Hand")) {
			running=true;
			this.image=this.NotS;
		}
		else if(e.getMessage().equals("Choose a minion with 5 or more attack")){
			running=true;
			this.image=this.SpellInvalid;
		}
		else  {
			running=false;
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}
	
	private void renderHand(Graphics g) {
		 int w=(int)(Display.Display.frame.getWidth()*(350/1920.0));
		 int h=(int)(Display.Display.frame.getHeight()*(500/1080.0));
		 int x=(int)(Display.Display.frame.getWidth()*(875/1920.0));
		 int y=this.y+(int)(Display.Display.frame.getHeight()*(350/1080.0));
		Card card=((FullHandException)e).getBurned();
		 //g.drawImage(ImageLoader.loadImage("/textures/All Cards/minions/low/big/not sleeping and divine/Argent Commander.PNG"),875, y+350,350,500,null);
		 if(card instanceof Minion&&card.getRarity()!=Rarity.LEGENDARY) {
				///System.out.println(((Minion)card)==((Minion)card).getGraphics().getMinion());
				g.drawImage(((Minion)card).getBigImage(),x, y,w,h,null);
				if(card.getManaCost()>9)
				    Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(12), y+Launcher.getcorH(43), false, Color.white, f30);
				else
					Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(39), y+Launcher.getcorH(50), false, Color.white, f30);
				if(((Minion) card).getAttack()>9)
			        Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(8),y+h-Launcher.getcorH(10), false, Color.white, f30);
				else 
					Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(42),y+h-Launcher.getcorH(34), false, Color.white, f30);
				if(((Minion) card).getCurrentHP()>9)
				    Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(40), y+h-Launcher.getcorH(8), false, Color.white, f30);
				else 
					Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(55), y+h-Launcher.getcorH(27), false, Color.white, f30);
				
			}
			else if(card instanceof Minion&&card.getRarity()==Rarity.LEGENDARY) {
				g.drawImage(((Minion)card).getBigImage(),x, y,w,h,null);
				if(card.getManaCost()>9)
				    Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(38), y+Launcher.getcorH(65), false, Color.white, f30);
				else
					Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(42), y+Launcher.getcorH(70), false, Color.white, f30);
				if(((Minion) card).getAttack()>9)
			        Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(30),y+h-Launcher.getcorH(29), false, Color.white, f30);
				else 
					Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(42),y+h-Launcher.getcorH(30), false, Color.white, f30);
				if(((Minion) card).getCurrentHP()>9)
				    Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(60), y+h-Launcher.getcorH(33), false, Color.white, f30);
				else 
					Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(55), y+h-Launcher.getcorH(40), false, Color.white, f30);
			}
			else if(card instanceof Spell) {
				g.drawImage(((Spell)card).getBigImage(),x, y,w,h,null);
				if(card.getManaCost()>9)
				    Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(28), y+Launcher.getcorH(52), false, Color.white, f30);
				else
					Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(37), y+Launcher.getcorH(52), false, Color.white, f30);
				
			}
	}
	
	public void action(int x,int y) {
		if(this.bounds.contains(x,y)) {
			hand=false;
			running=false;
		}
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
	
	

}
