package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import GUI.DrawCard;
import GUI.FontLoader;
import GUI.ImageLoader;
import GUI.PlaySound;
import GUI.Text;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import input.KeyManger;
import input.MouseManager;
import launcher.ExceptionGraphics;
import launcher.Launcher;
import launcher.PlayersNames;
import model.cards.Card;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.CurseOfWeakness;
import model.cards.spells.DivineSpirit;
import model.cards.spells.FieldSpell;
import model.cards.spells.Flamestrike;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.HolyNova;
import model.cards.spells.KillCommand;
import model.cards.spells.LeechingSpell;
import model.cards.spells.LevelUp;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.MultiShot;
import model.cards.spells.Polymorph;
import model.cards.spells.Pyroblast;
import model.cards.spells.SealOfChampions;
import model.cards.spells.ShadowWordDeath;
import model.cards.spells.SiphonSoul;
import model.cards.spells.Spell;
import model.cards.spells.TwistingNether;
import model.heroes.Hero;
import model.heroes.HeroListener;
import model.heroes.Hunter;
import model.heroes.Mage;
import model.heroes.Paladin;
import model.heroes.Priest;
import model.heroes.Warlock;
import Display.Display;

public class Game implements ActionValidator,HeroListener,Runnable{
	private Hero firstHero;
	private Hero secondHero;
	private Hero currentHero;
	private Hero opponent;
	private GameListener listener;
	private int h =Display.frame.getHeight();
	private int w =Display.frame.getWidth();
	private DrawCard drawCard;
	private Card card1=null;
	private Card card2=null;
	private Hero Selected=null;
	private static boolean HeroPower=false;
	private boolean endTurn=false;
	private Card hovring=null;
	private Card hov=null;
	private final Rectangle currentBounds=Launcher.getRect(1000,625, 125,125);
	private final Rectangle opponentBounds=Launcher.getRect(1000,120, 125,125);
	private final Rectangle currentHeroBounds=Launcher.getRect(943,535, 230,270);
	private final Rectangle opponentHeroBounds=Launcher.getRect(943,30, 230,270);
	private final Rectangle currentHeroPowerBounds=Launcher.getRect(1200,650, 80, 120);
	private final Rectangle opponentHeroPowerBounds=Launcher.getRect(1200,100, 80, 120);
	private final Rectangle endTurnBound=Launcher.getRect(1640, 390, 140, 40);
	private final Point HpCur=Launcher.getPoint(1120, 700) ;
	private final Point HpOpp=Launcher.getPoint(1120, 200);
	private final Point curManLoc=Launcher.getPoint(1358,830);
	private final Point oppManLoc=Launcher.getPoint(1318,75);
	private Font f20=FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(20));
	private Font f=FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(27));
	private Font f23=FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(23));
	private Hero winner;
	private String winnerName;
	private boolean endGame=false;
	private ArrayList<Minion> sleepingLastTurn=new ArrayList<Minion>();
	private PlaySound Sound =new PlaySound();
    private ExceptionGraphics exceptionGraphics;
    private boolean currentPower;
    private boolean oppPower;
    private boolean currentHerohove;
    private boolean oppHerohove;
    private BufferedImage heroInfo;
    private String currentName;
    private String oppName;
    private static Thread t1;
    private int xPos;
    private int yPos;
    
    private BufferedImage cardBack;
	public Game(Hero p1, Hero p2)  throws FullHandException, CloneNotSupportedException  {
		cardBack=ImageLoader.loadImage("/textures/CardBack.png");
		heroInfo=ImageLoader.loadImage("/textures/HeroInfo.jpg");
		Random rand =new Random();
		int first=rand.nextInt(2);
		firstHero=p1;
		secondHero=p2;
		if(first==0) {

			currentHero=p1;
			opponent=p2;
			
		}
		else {

			currentHero=p2;
			opponent=p1;
		}
		this.setNames();
		this.currentHero.setHeroRectbounds(this.currentBounds);
		this.opponent.setHeroRectbounds(this.opponentBounds);
		this.currentHero.setHeroBounds(this.currentHeroBounds);
		this.opponent.setHeroBounds(this.opponentHeroBounds);
		this.currentHero.setHeroPowerBounds(this.currentHeroPowerBounds);
		this.opponent.setHeroPowerBounds(this.opponentHeroPowerBounds);
		this.currentHero.setHpP(this.HpCur);
		this.opponent.setHpP(this.HpOpp);
		this.currentHero.setManaPoint(curManLoc);
		this.opponent.setManaPoint(oppManLoc);
		this.currentHero.setTotalManaCrystals(1);
		this.currentHero.setCurrentManaCrystals(1);
		this.currentHero.setListener(this);
		this.opponent.setListener(this);
		this.currentHero.setValidator(this);
		this.opponent.setValidator(this);
		start();
		this.drawCard=new DrawCard(this);
		this.exceptionGraphics=new ExceptionGraphics();
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public Hero getOpponent() {
		return opponent;
	}
    //new
	@Override
	public void validateTurn(Hero user) throws NotYourTurnException {
		if(this.currentHero!=user) {
			throw new NotYourTurnException("Not Your Turn");
		}
		
	}

	@Override
	public void validateAttack(Minion attacker, Minion target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		if(!(target instanceof Minion)) 
			throw new InvalidTargetException();
		
		else if(attacker.getAttack()<=0)
			throw new CannotAttackException("The Minion has 0 Attack");
		else if(attacker.isSleeping())
	         		throw new CannotAttackException("The Minion Can Not Attack this turn");
		else if(attacker.isAttacked()) 
			throw new CannotAttackException("The Minion Can Not Attack More than once");
		else if (currentHero.getField().contains(target))
			throw new InvalidTargetException("You can not Attack yourself");
		else if(!this.currentHero.getField().contains(attacker)||!this.opponent.getField().contains(target))
			throw new NotSummonedException("The Minion Can Not Attack In Hand");
		if(!target.isTaunt()) {
		for(int i=0;i<this.opponent.getField().size();i++) {
			if(this.opponent.getField().get(i).isTaunt()) {
				throw new TauntBypassException("There is a Taunt in the Field");
			}
		}
		}


	}

	@Override
	public void validateAttack(Minion attacker, Hero target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		if(!(target instanceof Hero)) 
			throw new InvalidTargetException();
		else if(attacker.getAttack()<=0)
			throw new CannotAttackException("The Minion has 0 Attack");
		else if(attacker.isSleeping())
			throw new CannotAttackException("The Minion Can Not Attack this turn");
		else if(attacker.isAttacked()) 
			throw new CannotAttackException("The Minion Can Not Attack More than once");
		else if(!this.currentHero.getField().contains(attacker))
			throw new NotSummonedException("The Minion Can Not Attack In Hand");
		if (currentHero==(target))
			throw new InvalidTargetException("You can not Attack yourself");
		else if(attacker.getName().equals("Icehowl")) 
			throw new InvalidTargetException("Icehowl Can Not Attack Hero");
		for(int i=0;i<this.opponent.getField().size();i++) {
			if(this.opponent.getField().get(i).isTaunt()) {
				throw new TauntBypassException("There is a Taunt in the Field");
			}
		}
	}

	@Override
	public void validateManaCost(Card card) throws NotEnoughManaException {
		if(this.currentHero.getCurrentManaCrystals()<card.getManaCost())
			throw new NotEnoughManaException("You Hava Not Enough ManaCrystals");
		
	}

	@Override
	public void validatePlayingMinion(Minion minion) throws FullFieldException {
		if(this.currentHero.getField().size()>=7)
			throw new FullFieldException("The Field has 7 Minions Now");
		
	}

	@Override
	public void validateUsingHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException {
	    if(this.currentHero.isHeroPowerUsed())
			 throw new HeroPowerAlreadyUsedException("The HeroPower Already Used this Turn");
	    else if(this.currentHero.getCurrentManaCrystals()<2)
			throw new NotEnoughManaException("You Hava Not Enough ManaCrystals");

	    
		
	}

	@Override
	public void onHeroDeath() {
		if(this.firstHero.getCurrentHP()<=0) {
			this.winner=this.secondHero;
			this.winnerName=PlayersNames.getName2();
		}
		else {
			this.winner=this.firstHero;
			this.winnerName=PlayersNames.getName1();
		}
		this.endGame=true;
		this.listener.onGameOver();
		
	}
	public void exit() {
		listener.exit();
	}
	public void again() {
		listener.again();
	}

	@Override
	public void damageOpponent(int amount) {
		this.opponent.setCurrentHP(this.opponent.getCurrentHP()-amount);
		
	}

	@Override
	public void endTurn() throws FullHandException, CloneNotSupportedException {
		Sound.playSound(new File("res/EndTurn Sound.wav"));
		this.currentHerohove=false;
		this.oppHerohove=false;
		this.currentPower=false;
		this.oppPower=false;
		this.hovring=null;
		this.opponent.setTotalManaCrystals(this.opponent.getTotalManaCrystals()+1);
		this.opponent.setCurrentManaCrystals(this.opponent.getTotalManaCrystals());
		this.opponent.setHeroPowerUsed(false);
		for(int i=0;i<this.currentHero.getField().size();i++) {
			this.currentHero.getField().get(i).setSleeping(false);
			this.currentHero.getField().get(i).setAttacked(false);
		}
		String temp2=this.currentName;
		this.currentName=this.oppName;
		this.oppName=temp2;
		Hero temp=this.currentHero;
		this.currentHero=this.opponent;
		this.opponent=temp;
        actionright();
		this.currentHero.setHeroRectbounds(this.currentBounds);
		this.opponent.setHeroRectbounds(this.opponentBounds);
		this.currentHero.setHeroBounds(this.currentHeroBounds);
		this.opponent.setHeroBounds(this.opponentHeroBounds);
		this.currentHero.setHeroPowerBounds(this.currentHeroPowerBounds);
		this.opponent.setHeroPowerBounds(this.opponentHeroPowerBounds);
		this.currentHero.setHpP(this.HpCur);
		this.opponent.setHpP(this.HpOpp);
		this.currentHero.setManaPoint(curManLoc);
		this.opponent.setManaPoint(oppManLoc);
		this.currentHero.drawCard();

		
	}
	public void start() throws FullHandException, CloneNotSupportedException {
		for(int i=0;i<3;i++) {
			this.currentHero.drawCard();
			this.opponent.drawCard();
		}
		this.opponent.drawCard();
		
	}

	public void setListener(GameListener listener) {
		this.listener = listener;
	}
	
	public synchronized void render(Graphics g,int width,int height,MouseManager mouseManager,KeyManger keyManger) {
		//if(this.getT1()!=null&&this.getT1().isAlive())return;
		try {
		for(int i=0;i<this.currentHero.getHand().size();i++) {
			if( this.currentHero.getHand().get(i)==hov)
				this.drawCard.render(g, this.currentHero.getHand().get(i),(this.drawCard.getW()*i)+Launcher.getcorW(500)-(i*(int)(this.drawCard.getW()/2)),h-this.drawCard.getH()+Launcher.getcorH(5)-(this.drawCard.getH()/Launcher.getcorH(3)) );
			else
				this.drawCard.render(g, this.currentHero.getHand().get(i),(this.drawCard.getW()*i)+Launcher.getcorW(500)-(i*(int)(this.drawCard.getW()/2)),h-this.drawCard.getH()+Launcher.getcorH(5) );

			if(i==this.currentHero.getHand().size()-1&&this.currentHero.getHand().get(i)==hov) {
				this.currentHero.getHand().get(i).setBounds(new Rectangle((this.drawCard.getW()*i)+Launcher.getcorW(500)-(i*(int)(this.drawCard.getW()/2)),h-this.drawCard.getH()-(this.drawCard.getH()/Launcher.getcorH(3)),this.drawCard.getW(),this.drawCard.getH()));
			}
			else if(i==this.currentHero.getHand().size()-1) {
				this.currentHero.getHand().get(i).setBounds(new Rectangle((this.drawCard.getW()*i)+Launcher.getcorW(500)-(i*(int)(this.drawCard.getW()/2)),h-this.drawCard.getH(),this.drawCard.getW(),this.drawCard.getH()));
			}
			////
			else if(this.currentHero.getHand().get(i)==hov)
			    this.currentHero.getHand().get(i).setBounds(new Rectangle((this.drawCard.getW()*i)+Launcher.getcorW(500)-(i*(int)(this.drawCard.getW()/2)),h-this.drawCard.getH()-(this.drawCard.getH()/Launcher.getcorH(3)),this.drawCard.getW()/2,this.drawCard.getH()));
			else
			    this.currentHero.getHand().get(i).setBounds(new Rectangle((this.drawCard.getW()*i)+Launcher.getcorW(500)-(i*(int)(this.drawCard.getW()/2)),h-this.drawCard.getH(),this.drawCard.getW()/2,this.drawCard.getH()));

		}
		for(int i=0;i<this.opponent.getHand().size();i++) {
			//this.drawCard.render(g, this.opponent.getHand().get(i), (this.drawCard.getW()*i)+750-(i*(int)(this.drawCard.getW()/2)), 0);
			g.drawImage(cardBack,(this.drawCard.getW()*i)+Launcher.getcorW(500)-(i*(int)(this.drawCard.getW()/2)),-(this.drawCard.getH()/2),this.drawCard.getW(),this.drawCard.getH(), null);
		}
		
		for(int i=0;i<this.currentHero.getField().size();i++) {
			this.drawCard.render(g, this.currentHero.getField().get(i), (this.drawCard.getW()*i)+Launcher.getcorW(650),h-2*this.drawCard.getH()-Launcher.getcorH(150));
		    this.currentHero.getField().get(i).setBounds(new Rectangle((this.drawCard.getW()*i)+Launcher.getcorW(650),h-2*this.drawCard.getH()-Launcher.getcorW(150),this.drawCard.getW(),this.drawCard.getH()));
		}
		for(int i=0;i<this.opponent.getField().size();i++) {
			this.drawCard.render(g, this.opponent.getField().get(i), (this.drawCard.getW()*i)+Launcher.getcorW(650), this.drawCard.getH()+Launcher.getcorH(90));
			this.opponent.getField().get(i).setBounds(new Rectangle((this.drawCard.getW()*i)+Launcher.getcorW(650),this.drawCard.getH()+Launcher.getcorH(90),this.drawCard.getW(),this.drawCard.getH()));
		}
		if(this.hovring!=null) {
			this.drawCard.render(g, hovring, Launcher.getcorW(50), Launcher.getcorH(250),Launcher.getcorW(250),Launcher.getcorH(320));
		}
		else if(this.currentPower) {
			g.drawImage(this.currentHero.getHeroPowerImage(), Launcher.getcorW(50), Launcher.getcorH(250),Launcher.getcorW(250),Launcher.getcorH(320), null);

		}
		else if(this.oppPower) {
			g.drawImage(this.opponent.getHeroPowerImage(), Launcher.getcorW(50), Launcher.getcorH(250),Launcher.getcorW(250),Launcher.getcorH(320),null);
		}
		else if(this.currentHerohove) {
			this.renderCurrentInfo(g);
 		}
		
		else if(this.oppHerohove) {
			this.renderOppInfo(g);
		}
		}
		catch(Exception e) {
//			JOptionPane.showMessageDialog(null,"I am Sorry for That Error");
//			System.exit(0);
			System.out.println("error");
		}
		

	}
	public void actionright() {
		this.card1=null;
		this.card2=null;
		this.Selected=null;
		this.HeroPower=false;
		this.endTurn=false;	
	}

	private boolean activeHand(int x,int y) throws NotYourTurnException, NotEnoughManaException, FullFieldException, InvalidTargetException, CannotAttackException, TauntBypassException, NotSummonedException, HeroPowerAlreadyUsedException, FullHandException, CloneNotSupportedException {
		Card selected=null;
		for(int i=0;i<this.currentHero.getHand().size();i++) {
			if(this.currentHero.getHand().get(i).getBounds()==null)continue;
			if(this.currentHero.getHand().get(i).getBounds().contains(x,y)) {
				selected=this.currentHero.getHand().get(i);
				break;
		    }
	}
		if(selected==null)return false;
		else if(card1!=null) {
			if(this.HeroPower||this.currentHero.getField().contains(this.card1)) {
				exceptionGraphics.start(new NotSummonedException("you can Not Target The Hand"));
			}
			actionright();
			return true;
		}
		else 
		{
		card1=selected;
		card2=null;
		if(this.HeroPower) {
			exceptionGraphics.start(new NotSummonedException("you can Not Target The Hand"));
			actionright();
			return true;
		}
		action();
		return true;}
	}
	
	private boolean activeFieldCurrent(int x,int y) throws NotYourTurnException, NotEnoughManaException, FullFieldException, InvalidTargetException, CannotAttackException, TauntBypassException, NotSummonedException, HeroPowerAlreadyUsedException, FullHandException, CloneNotSupportedException {
	    Card selected=null;
		for(int i=0;i<this.currentHero.getField().size();i++) {
			if(this.currentHero.getField().get(i).getBounds()==null)continue;
			if(this.currentHero.getField().get(i).getBounds().contains(x,y)) {
				selected=this.currentHero.getField().get(i);
				break;
			}
			
		}
		if(selected==null)return false;
		if(card1==null) {
			card1=selected;
			card2=null;
			action();
			return true;
		}
		card2=selected;
		action();
		return true;
	}
	private boolean activeFieldOpp(int x,int y) throws NotYourTurnException, NotEnoughManaException, FullFieldException, InvalidTargetException, CannotAttackException, TauntBypassException, NotSummonedException, HeroPowerAlreadyUsedException, FullHandException, CloneNotSupportedException {
		Card selected=null;
		for(int i = 0;i<this.opponent.getField().size();i++) {
			if(this.opponent.getField().get(i).getBounds()==null)continue;
			if(this.opponent.getField().get(i).getBounds().contains(x,y)) {
				selected=this.opponent.getField().get(i);
				break;
			}
		}
		if(selected==null)return false;
		card2=selected;
		action();
		return true;
	}
	private boolean activeHero(int x,int y) throws CannotAttackException, NotYourTurnException, TauntBypassException, NotSummonedException, InvalidTargetException, NotEnoughManaException, HeroPowerAlreadyUsedException, FullHandException, FullFieldException, CloneNotSupportedException {
		if(this.opponentBounds.contains(x,y)) {
			this.Selected=this.opponent;
			heroAction();
			return true;
			
		}
		else if(this.currentBounds.contains(x,y)) {
			this.Selected=this.currentHero;
			heroAction();
			return true;
		}
		return false;
	}
	private boolean activeEndTurn(int x,int y) {
		if(this.endTurnBound.contains(x,y)) {
			this.endTurn=true;
			return true;
		}
		else
			return false;
	}
	private boolean activeHeroPower(int x,int y) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException, FullFieldException, CloneNotSupportedException {
		if(this.currentHeroPowerBounds.contains(x,y)) {
			heroPowerAction();
			return true;
		}
		else
			return false;
	}

	public void actionleft(int x,int y) {
        this.xPos=x;
        this.yPos=y;
        t1=new Thread(this);
        t1.setName("Game");
        t1.start();

	}
	
	@Override
	public void run() {
		try {
			if(activeHand(this.xPos,this.yPos))return;
			if(activeFieldCurrent(this.xPos,this.yPos))return;;
			if(activeFieldOpp(this.xPos,this.yPos))return;
			if(activeHeroPower(this.xPos,this.yPos))return;
			if(activeEndTurn(this.xPos,this.yPos)) endTurn();
			if(activeHero(this.xPos,this.yPos))return;
			card1=null;
			card2=null;
		} catch (NotYourTurnException | NotEnoughManaException | FullFieldException | InvalidTargetException
				
				| CannotAttackException | TauntBypassException | NotSummonedException |FullHandException |CloneNotSupportedException |HeroPowerAlreadyUsedException e) {
			exceptionGraphics.start(e);
			actionright();
			e.printStackTrace();
		}
		
	}
	

	private void heroAction() throws CannotAttackException, NotYourTurnException, TauntBypassException, NotSummonedException, InvalidTargetException, NotEnoughManaException, HeroPowerAlreadyUsedException, FullHandException, FullFieldException, CloneNotSupportedException {
		if(this.HeroPower) {
			if(this.currentHero instanceof Mage )
			     ((Mage)this.currentHero).useHeroPower(this.Selected);
			else if( this.currentHero instanceof Priest)
				((Priest)this.currentHero).useHeroPower(this.Selected);
		}
		else if(card1!=null) {
			if(card1 instanceof Minion) {
				this.currentHero.attackWithMinion((Minion)card1,this.Selected);
				card1=null;
			}
			else if(card1 instanceof Spell) {
				if(card1 instanceof KillCommand||card1 instanceof Pyroblast) {
					this.currentHero.castSpell((HeroTargetSpell)card1, this.Selected);
					card1=null;
				}
			}
				
		}
		actionright();

	}
	


		
	
	private void heroPowerAction() throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException, FullFieldException, CloneNotSupportedException {
		if(this.currentHero instanceof Warlock || this.currentHero instanceof Paladin||this.currentHero instanceof Hunter) {
			this.currentHero.useHeroPower();
			return;
			//this.HeroPower=false;
		}
		else {
			this.HeroPower=true;
		}
	}
	public void action() throws NotYourTurnException, NotEnoughManaException, FullFieldException, InvalidTargetException, CannotAttackException, TauntBypassException, NotSummonedException, HeroPowerAlreadyUsedException, FullHandException, CloneNotSupportedException {
		if(card2==null&&card1!=null) {
			if(card1 instanceof Minion &&this.currentHero.getHand().contains(card1)) {
				
					this.currentHero.playMinion((Minion)card1);
					card1=null;
			}
			else if (card1 instanceof CurseOfWeakness||card1 instanceof MultiShot||card1 instanceof Flamestrike||card1 instanceof HolyNova||card1 instanceof TwistingNether) {
					this.currentHero.castSpell((AOESpell)card1, this.opponent.getField());
					card1=null;
			}
			else if(card1 instanceof LevelUp){
					this.currentHero.castSpell((FieldSpell)card1);
					card1=null;
				}
			else if(this.HeroPower&&this.currentHero instanceof Mage  ) {
				((Mage)this.currentHero).useHeroPower((Minion)card1);
				actionright();
			}
			else if(this.HeroPower&&this.currentHero instanceof Priest) {
				((Priest)this.currentHero).useHeroPower((Minion)card1);
				actionright();
			}
		    return;
		}
		else if(card1!=null&&card2!=null) {
			if(card1 instanceof Minion) {
				this.currentHero.attackWithMinion((Minion) card1, (Minion) card2);
			}
			else if(card1 instanceof DivineSpirit||card1 instanceof KillCommand||card1 instanceof Polymorph||card1 instanceof Pyroblast||card1 instanceof SealOfChampions||card1 instanceof ShadowWordDeath){
					this.currentHero.castSpell((MinionTargetSpell)card1, (Minion)card2);
				}
			else if(card1 instanceof SiphonSoul){
					this.currentHero.castSpell((LeechingSpell)card1,(Minion)card2);
				}
			card1=null;
			card2=null;
		}
		else {
			if(this.HeroPower&&this.currentHero instanceof Mage  ) {
				((Mage)this.currentHero).useHeroPower((Minion)card2);
				actionright();
			}
			else if(this.HeroPower&&this.currentHero instanceof Priest) {
				((Priest)this.currentHero).useHeroPower((Minion)card2);
				actionright();
			}
			else
				actionright();
				
		}
		
	}
	public void actionField() throws CannotAttackException, NotYourTurnException, TauntBypassException, InvalidTargetException, NotSummonedException {
			this.currentHero.attackWithMinion((Minion)card1,(Minion) card2);
			card1=null;
			card2=null;
	}
	public Rectangle getCurrentBounds() {
		return currentBounds;
	}

	public Rectangle getOpponentBounds() {
		return opponentBounds;
	}

	public Rectangle getCurrentHeroPowerBounds() {
		return currentHeroPowerBounds;
	}

	public Rectangle getOpponentHeroPowerBounds() {
		return opponentHeroPowerBounds;
	}

	public Rectangle getEndTurnBound() {
		return endTurnBound;
	}
	
	
	
	public Hero getWinner() {
		return winner;
	}

	public String getWinnerName() {
		return winnerName;
	}
	
	

	public boolean isEndGame() {
		return endGame;
	}

	public void hovring(MouseEvent e) {
		for(int i=0;i<this.getCurrentHero().getHand().size();i++) {
			if(this.currentHero.getHand().get(i).getBounds()==null)continue;
			if(this.currentHero.getHand().get(i).getBounds().contains(e.getX(),e.getY())) {
				try {
					this.hovring=(Card) this.currentHero.getHand().get(i).clone();
					this.hov=this.currentHero.getHand().get(i);
					this.currentPower=false;
					this.oppPower=false;
					this.currentHerohove=false;
					this.oppHerohove=false;
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				} catch (NullPointerException e1) {
					
				}
				
				return;

				}
				
							}
		for(int i=0;i<this.getCurrentHero().getField().size();i++) {
			if(this.currentHero.getField().get(i).getBounds()==null)continue;
			if(this.currentHero.getField().get(i).getBounds().contains(e.getX(),e.getY())) {
				try {
					this.hovring=(Card) this.currentHero.getField().get(i).clone();					this.currentPower=false;
					this.oppPower=false;
					this.currentPower=false;
					this.currentHerohove=false;
					this.oppHerohove=false;
				} catch (CloneNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NullPointerException e1) {
					
				}
				
				return;

				}
		}
			for(int i=0;i<this.getOpponent().getField().size();i++) {
				if(this.getOpponent().getField().get(i).getBounds()==null)continue;
					if(this.getOpponent().getField().get(i).getBounds().contains(e.getX(),e.getY())) {
						try {
							this.hovring=(Card) this.getOpponent().getField().get(i).clone();
							this.currentPower=false;
							this.oppPower=false;
							this.currentHerohove=false;
							this.oppHerohove=false;
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						} catch (NullPointerException e1) {
							
						}
						
						return;

						}
	}
			this.hov=null;
			if(this.currentHeroPowerBounds.contains(e.getX(),e.getY())) {
				this.currentPower=true;
				this.oppPower=false;
				this.currentHerohove=false;
				this.oppHerohove=false;
				this.hovring=null;
				
				
			}
			else if(this.opponentHeroPowerBounds.contains(e.getX(),e.getY())) {
				this.currentPower=false;
				this.oppPower=true;
				this.currentHerohove=false;
				this.oppHerohove=false;
				this.hovring=null;
				
			}
			else if(this.currentHeroBounds.contains(e.getX(),e.getY())) {
				this.currentHerohove=true;
				this.oppHerohove=false;
				this.currentPower=false;
				this.oppPower=false;
				this.hovring=null;
			}
			else if(this.opponentHeroBounds.contains(e.getX(),e.getY())) {
				this.currentHerohove=false;
				this.oppHerohove=true;
				this.currentPower=false;
				this.oppPower=false;
				this.hovring=null;
			}
			
		
		
	}
	private void renderCurrentInfo(Graphics g) {
		g.drawImage(heroInfo, Launcher.getcorW(50), Launcher.getcorH(250),Launcher.getcorW(250),Launcher.getcorH(320), null);
	    Text.drawString(g, "Player Name",Launcher.getcorW(50+21), Launcher.getcorH(250+41), false, Color.BLACK, f);
	    Text.drawString(g, this.currentName,Launcher.getcorW(50+37), Launcher.getcorH(250+70), false, Color.BLACK, f23);
	    Text.drawString(g, "Hero Name",Launcher.getcorW(50+21), Launcher.getcorH(250+70+50), false, Color.BLACK, f);
	    Text.drawString(g, this.currentHero.getName(),Launcher.getcorW(50+37), Launcher.getcorH(250+70+79), false, Color.BLACK, f23);
	    Text.drawString(g, "Deck Cards",Launcher.getcorW(50+21), Launcher.getcorH(250+70+79+50), false, Color.BLACK, f);
	    Text.drawString(g, this.currentHero.getDeck().size()+" "+"Cards",Launcher.getcorW(50+37), Launcher.getcorH(250+70+79+79), false, Color.BLACK, f23);
	    Text.drawString(g, "Fatigue Damage",Launcher.getcorW(50+21), Launcher.getcorH(250+70+79+79+50), false, Color.BLACK, f);
	    Text.drawString(g, this.currentHero.getFatigueDamage()+" "+"Points",Launcher.getcorW(50+37), Launcher.getcorH(250+70+79+79+79), false, Color.BLACK, f23);
	}
	
	private void renderOppInfo(Graphics g) {
		g.drawImage(heroInfo, Launcher.getcorW(50), Launcher.getcorH(250),Launcher.getcorW(250),Launcher.getcorH(320), null);
	    Text.drawString(g, "Player Name",Launcher.getcorW(50+21), Launcher.getcorH(250+41), false, Color.BLACK, f);
	    Text.drawString(g,this.oppName,Launcher.getcorW(50+37), Launcher.getcorH(250+70), false, Color.BLACK, f23);
	    Text.drawString(g, "Hero Name",Launcher.getcorW(50+21), Launcher.getcorH(250+70+50), false, Color.BLACK, f);
	    Text.drawString(g, this.opponent.getName(),Launcher.getcorW(50+37), Launcher.getcorH(250+70+79), false, Color.BLACK, f23);
	    Text.drawString(g, "Deck Cards",Launcher.getcorW(50+21), Launcher.getcorH(250+70+79+50), false, Color.BLACK, f);
	    Text.drawString(g,this.opponent.getDeck().size()+" "+"Cards",Launcher.getcorW(50+37), Launcher.getcorH(250+70+79+79), false, Color.BLACK, f23);
	    Text.drawString(g, "Fatigue Damage",Launcher.getcorW(50+21), Launcher.getcorH(250+70+79+79+50), false, Color.BLACK, f);
	    Text.drawString(g, this.currentHero.getFatigueDamage()+" "+"Points",Launcher.getcorW(50+37), Launcher.getcorH(250+70+79+79+79), false, Color.BLACK, f23);
	}
	private void setNames() {
		if(this.firstHero==this.currentHero) {
			this.currentName=PlayersNames.getName1();
			this.oppName=PlayersNames.getName2();
		}
		else {
			this.currentName=PlayersNames.getName2();
			this.oppName=PlayersNames.getName1();
		}
	}

	public Card getCard1() {
		return card1;
	}

	public Card getCard2() {
		return card2;
	}

	public ExceptionGraphics getExceptionGraphics() {
		return exceptionGraphics;
	}


	public Card getHov() {
		return hov;
	}

	public static boolean isHeroPower() {
		return HeroPower;
	}

	public static void setHeroPower(boolean heroPower) {
		HeroPower = heroPower;
	}

	public static Thread getT1() {
		return t1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	




}
