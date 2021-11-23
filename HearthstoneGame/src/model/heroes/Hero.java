package model.heroes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import GUI.FontLoader;
import GUI.PlaySound;
import GUI.Text;
import engine.ActionValidator;
import engine.Game;
import exceptions.*;
import launcher.Launcher;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Icehowl;
import model.cards.minions.Minion;
import model.cards.minions.MinionListener;
import model.cards.spells.*;


abstract public class Hero implements MinionListener{
	private String name;
	private int currentHP;
	private boolean heroPowerUsed;
	private int totalManaCrystals;
	private int currentManaCrystals;
	private ArrayList<Card> deck;
	private ArrayList<Minion> field;
	private ArrayList<Card> hand;
	private int fatigueDamage;
	private HeroListener listener;
	private ActionValidator validator;
	private BufferedImage image;
	private BufferedImage heroPowerImage;
	private BufferedImage heroPowerImageHigh;
	private Rectangle heroBounds;
	private Rectangle heroRectbounds;
	private Rectangle heroPowerBounds;
	private Point HpP;
	private Point manaPoint;
	private Font f30=FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(30));
	private Font f20=FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(20));
	private Font f3in=FontLoader.loadFont("res/fonts/AgentOrange.ttf", Launcher.getcorW(30));
	private boolean locker =false;
	protected PlaySound Sound =new PlaySound();

	public Hero(String name) throws IOException, CloneNotSupportedException {
		this.name=name;
		this.currentHP=30;
		this.heroPowerUsed=false;
		this.totalManaCrystals=0;
		this.currentManaCrystals=0;
		this.deck=new ArrayList<Card>();
		this.field=new ArrayList<Minion>();
		this.hand=new ArrayList<Card>();
		//this.fatigueDamage=1;//try
		buildDeck();

		
	}
	private static Rarity getRarity(String s) {
		if(s.equals("b"))
			return Rarity.BASIC;
		else if(s.equals("c"))
			return Rarity.COMMON;
		else if(s.equals("r"))
			return Rarity.RARE;
		else if(s.equals("e"))
			return Rarity.EPIC;
		else 
			return Rarity.LEGENDARY;
	}
	
	public final static ArrayList<Minion> getAllNeutralMinions(String filePath) throws IOException{
		ArrayList<Minion> AllneutralMinions=new ArrayList<Minion>();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String line;
		int i=0;
		while((line = br.readLine()) != null) {
			String theMinion[]=line.split(",");
			String name=theMinion[0];
			if(name.equals("Icehowl")) {
				AllneutralMinions.add(new Icehowl());
				continue;
			}
			int manaCost=Integer.parseInt(theMinion[1]);
			Rarity rarity=getRarity(theMinion[2]);
			int attack=Integer.parseInt(theMinion[3]);
			int maxHP=Integer.parseInt(theMinion[4]);
			boolean taunt=Boolean.parseBoolean(theMinion[5]);
			boolean divine=Boolean.parseBoolean(theMinion[6]);
			boolean charge=Boolean.parseBoolean(theMinion[7]);
			
			AllneutralMinions.add(new Minion(name,manaCost,rarity,attack,maxHP,taunt,divine,charge));
			
		}
		
		br.close();
		return AllneutralMinions;
		
	}
	private static ArrayList<Minion> clone(ArrayList<Minion>a) {
		ArrayList l=new ArrayList<Minion>();
		for(int i=0;i<a.size();i++) {
			l.add(a.get(i));
		}
		return l;
	}
	public final static ArrayList<Minion> getNeutralMinions(ArrayList<Minion> minions,int count) throws CloneNotSupportedException{
		ArrayList<Minion> neutralMinionsTemp=new ArrayList<Minion>();
		ArrayList<Minion> neutralMinions=new ArrayList<Minion>();
		ArrayList<Minion> AllneutralMinions=clone(minions);
		int b=0;
		for(int i=0;i<count;i++) {
			Random rand= new Random();
			int index=rand.nextInt(AllneutralMinions.size());
			if(AllneutralMinions.get(index).getRarity()==Rarity.LEGENDARY) {
				neutralMinionsTemp.add(AllneutralMinions.get(index));
				AllneutralMinions.remove(index);

			}
			else if(neutralMinionsTemp.contains(AllneutralMinions.get(index))){
				neutralMinionsTemp.add(AllneutralMinions.get(index));
				AllneutralMinions.remove(index);
			}
			else {
				neutralMinionsTemp.add(AllneutralMinions.get(index));
			}
		}
		for(int i=0;i<neutralMinionsTemp.size();i++) {
			neutralMinions.add((Minion)(neutralMinionsTemp.get(i).clone()));
		}
		return neutralMinions;
	}
	abstract public void buildDeck() throws IOException,CloneNotSupportedException;
	
    ///HEROPOWER
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
		this.validator.validateTurn(this);
		this.validator.validateUsingHeroPower(this);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-2);
		this.setHeroPowerUsed(true);
		
	}

	//
	//////Actions
	public void onMinionDeath(Minion m) {
		this.getField().remove(m);
		Sound.playSound(m.getDeathSound());
	}
	
	public void playMinion(Minion m) throws NotYourTurnException,
	NotEnoughManaException, FullFieldException{
		validator.validateTurn(this);
		validator.validateManaCost(m);
		validator.validatePlayingMinion(m);
		
		this.field.add(m);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-m.getManaCost());
		this.hand.remove(m);
		Sound.playSound(m.getPlaySound());
		
	}
	
	public void attackWithMinion(Minion attacker, Minion target) throws
	CannotAttackException, NotYourTurnException, TauntBypassException,
	InvalidTargetException, NotSummonedException{
		validator.validateTurn(this);
		validator.validateAttack(attacker, target);
		
		attacker.attack(target);
		if(attacker.getCurrentHP()>0&&target.getCurrentHP()>0)
		    Sound.playSound(attacker.getAttackSound());
		
	}
	
	public void attackWithMinion(Minion attacker, Hero target) throws
	CannotAttackException, NotYourTurnException, TauntBypassException,
	NotSummonedException, InvalidTargetException{
		validator.validateTurn(this);
		validator.validateAttack(attacker, target);
		attacker.attack(target);	
		Sound.playSound(attacker.getAttackSound());
	}
	
	/////Spells
	
	public void castSpell(FieldSpell s) throws NotYourTurnException,
	NotEnoughManaException{
		validator.validateTurn(this);
		if(this.isKalycgosField())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);
		this.validator.validateManaCost((Spell)s);
		this.getHand().remove((Spell)s);
		s.performAction(this.getField());
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());
		
	}
	public void castSpell(AOESpell s, ArrayList<Minion >oppField) throws
	NotYourTurnException, NotEnoughManaException{
		validator.validateTurn(this);
		if(this.isKalycgosField())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);
		this.validator.validateManaCost((Spell)s);
		this.getHand().remove((Spell)s);
		s.performAction(oppField, this.getField());
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());
		
	}
	public void castSpell(MinionTargetSpell s, Minion m) throws NotYourTurnException,
	NotEnoughManaException, InvalidTargetException{
		validator.validateTurn(this);
		if(s instanceof ShadowWordDeath&&m.getAttack()<5) {
			throw new InvalidTargetException("Choose a minion with 5 or more attack");
		}
		if(this.isKalycgosField())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);
		this.validator.validateManaCost((Spell)s);
		this.getHand().remove((Spell)s);
		s.performAction(m);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());
		
	}
	public void castSpell(HeroTargetSpell s, Hero h) throws NotYourTurnException,
	NotEnoughManaException{
		validator.validateTurn(this);
		if(this.isKalycgosField())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);
		this.validator.validateManaCost((Spell)s);
		this.getHand().remove((Spell)s);
		s.performAction(h);
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());

		

	}
	public void castSpell(LeechingSpell s, Minion m) throws NotYourTurnException,
	NotEnoughManaException{
		validator.validateTurn(this);
		if(this.isKalycgosField())
			((Spell)s).setManaCost(((Spell)s).getManaCost()-4);
		this.validator.validateManaCost((Spell)s);
		this.getHand().remove((Spell)s);
		this.setCurrentHP(this.getCurrentHP()+s.performAction(m));
		this.setCurrentManaCrystals(this.getCurrentManaCrystals()-((Spell)s).getManaCost());
		


	}
	///
	
	public void endTurn() throws FullHandException, CloneNotSupportedException{
		this.listener.endTurn();
	}
	public Card drawCard() throws FullHandException, CloneNotSupportedException{
		if (fatigueDamage > 0) {
			setCurrentHP(currentHP - fatigueDamage);
			fatigueDamage++;
			return null;
		}

		Card draw = deck.remove(0);

		if (deck.size() == 0)
			fatigueDamage = 1;
		
		if(this.hand.size()>=10) {
			throw new FullHandException("The Hand has 10 Cards",draw);
		}
		this.hand.add(draw);
		if(this.hand.size()<10&&this.isChromaggusField()) {
			this.hand.add((Card)draw.clone());
		}
		return draw;
		
	}
	public synchronized void render(Graphics g,String s) {
		
		g.drawImage(image,(int)this.heroBounds.getX(), (int)heroBounds.getY(),(int)this.heroBounds.getWidth(),(int)this.heroBounds.getHeight(), null);
		if(Game.isHeroPower()&&s.equals("c")) {


			g.drawImage(this.heroPowerImageHigh, (int)this.heroPowerBounds.getX(),(int) this.heroPowerBounds.getY(),(int) this.heroPowerBounds.getWidth(),(int)this.heroPowerBounds.getHeight(), null);
		}
		else {
			locker=false;
		  g.drawImage(this.heroPowerImage, (int)this.heroPowerBounds.getX(),(int) this.heroPowerBounds.getY(),(int) this.heroPowerBounds.getWidth(),(int)this.heroPowerBounds.getHeight(), null);
		}
		g.setColor(Color.red);
		g.fillOval((int) this.HpP.getX(),(int) this.HpP.getY(),Launcher.getcorW(60),Launcher.getcorW(60));
		Text.drawString(g, this.currentHP+"", (int) this.HpP.getX()+Launcher.getcorW(12),(int) this.HpP.getY()+Launcher.getcorW(45), false, Color.white, f30);
		Text.drawString(g,this.currentManaCrystals+"/"+this.totalManaCrystals, (int) this.manaPoint.getX(),(int) this.manaPoint.getY(), false, Color.white, f20);
	}

	public boolean isChromaggusField() {
		for(int i =0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Chromaggus"))
				return true;
		}
		return false;

	}


	public boolean isKalycgosField() {
		for(int i =0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Kalycgos"))
				return true;
		}
		return false;
	}


	public boolean isProphetVelenField() {
		for(int i =0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Prophet Velen"))
				return true;
		}
		return false;
	}


	public boolean isWilfredFizzlebang() {
		for(int i =0;i<this.getField().size();i++) {
			if(this.getField().get(i).getName().equals("Wilfred Fizzlebang"))
				return true;
		}
		return false;
	}
	///
	///SETTER AND GETTER


	public synchronized int getCurrentHP() {
		return currentHP;
	}

	public synchronized void  setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
		if(this.getCurrentHP()<=0)
			this.listener.onHeroDeath();
	}

	public synchronized boolean isHeroPowerUsed() {
		return heroPowerUsed;
	}

	public synchronized void setHeroPowerUsed(boolean heroPowerUsed) {
		this.heroPowerUsed = heroPowerUsed;
	}

	public synchronized int getTotalManaCrystals() {
		return totalManaCrystals;
	}

	public synchronized void setTotalManaCrystals(int totalManaCrystals) {
		if(totalManaCrystals>10)totalManaCrystals=10;
		else if(totalManaCrystals<0)totalManaCrystals=0;
		this.totalManaCrystals = totalManaCrystals;
	}

	public synchronized int getCurrentManaCrystals() {
		return currentManaCrystals;
	}

	public synchronized void setCurrentManaCrystals(int currentManaCrystals) {
		if(currentManaCrystals>10)currentManaCrystals=10;
		else if(currentManaCrystals<0)currentManaCrystals=0;
		this.currentManaCrystals = currentManaCrystals;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}


	public ArrayList<Minion> getField() {
		return field;
	}


	public ArrayList<Card> getHand() {
		return hand;
	}


	public String getName() {
		return name;
	}

	public HeroListener getListener() {
		return listener;
	}
	public void setListener(HeroListener listener) {
		this.listener = listener;
	}
	public void setValidator(ActionValidator validator) {
		this.validator = validator;
	}
	


	public String toString() {
		return "("+this.name+" , "+this.currentManaCrystals+" , "+this.currentHP+")";
	}
	public Rectangle getHeroBounds() {
		return heroBounds;
	}
	public void setHeroBounds(Rectangle heroBounds) {
		this.heroBounds = heroBounds;
	}
	public Rectangle getHeroPowerBounds() {
		return heroPowerBounds;
	}
	public void setHeroPowerBounds(Rectangle heroPowerBounds) {
		this.heroPowerBounds = heroPowerBounds;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public Rectangle getHeroRectbounds() {
		return heroRectbounds;
	}
	public void setHeroRectbounds(Rectangle heroRectbounds) {
		this.heroRectbounds = heroRectbounds;
	}
	public BufferedImage getHeroPowerImage() {
		return heroPowerImage;
	}
	public void setHeroPowerImage(BufferedImage heroPowerImage) {
		this.heroPowerImage = heroPowerImage;
	}

	public Point getHpP() {
		return HpP;
	}
	public void setHpP(Point hpP) {
		HpP = hpP;
	}
	public void setManaPoint(Point manaPoint) {
		this.manaPoint = manaPoint;
	}
	public int getFatigueDamage() {
		return fatigueDamage;
	}
	public BufferedImage getHeroPowerImageHigh() {
		return heroPowerImageHigh;
	}
	public void setHeroPowerImageHigh(BufferedImage heroPowerImageHigh) {
		this.heroPowerImageHigh = heroPowerImageHigh;
	}
	
	
	
	
	

	

}
