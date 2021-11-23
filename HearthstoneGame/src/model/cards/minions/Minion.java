package model.cards.minions;

import java.awt.image.BufferedImage;
import java.io.File;

import GUI.ImageLoader;
import exceptions.InvalidTargetException;
import model.cards.Card;
import model.cards.Rarity;
import model.heroes.Hero;

public class Minion extends Card implements Cloneable {
	private int attack;
	private int maxHP;
	private int currentHP;
	private boolean taunt;
	private boolean divine;
	private boolean sleeping;
    private boolean attacked;
    
	private BufferedImage lowSmallNsAdImage;
	private BufferedImage lowSmallNsNdImage;
	private BufferedImage lowSmallAsNdimage;
	private BufferedImage lowSmallAsAdImage;
	private BufferedImage lowBigNsAdImage;
	private BufferedImage lowBigNsNdImage;
	private BufferedImage lowBigAsNdimage;
	private BufferedImage lowBigAsAdImage;
	private BufferedImage highSmallNsAdImage;
	private BufferedImage highSmallNsNdImage;
	private BufferedImage highSmallAsNdimage;
	private BufferedImage highSmallAsAdImage;
	
	private File attackSound;
	private File playSound;
	private File deathSound;
    //new
    private MinionListener listener;
	//
    public Minion(String name,int manaCost,Rarity rarity, int attack,int maxHP,boolean taunt,boolean divine,boolean charge) {
    	super(name,manaCost,rarity);
    	if(attack<0)attack=0;
    	this.attack=attack;
    	this.maxHP=maxHP;
    	this.currentHP=maxHP;
    	this.taunt=taunt;
    	this.divine=divine;
    	this.sleeping=!charge;
    	init();
    }
    
	public void init() {
		this.lowSmallNsAdImage=ImageLoader.loadImage("/textures/All Cards/minions/low/big/not sleeping and divine/"+this.getName()+".PNG");
		this.lowSmallNsNdImage=ImageLoader.loadImage("/textures/All Cards/minions/low/big/not sleeping not divine/"+this.getName()+".png");
		this.lowSmallAsNdimage=ImageLoader.loadImage("/textures/All Cards/minions/low/big/sleeping not divine/"+this.getName()+".png");
		this.lowSmallAsAdImage=ImageLoader.loadImage("/textures/All Cards/minions/low/big/sleeping and divine/"+this.getName()+".png");
		this.lowBigNsAdImage=ImageLoader.loadImage("/textures/All Cards/minions/low/big/not sleeping and divine/"+this.getName()+".PNG");
		this.lowBigNsNdImage=ImageLoader.loadImage("/textures/All Cards/minions/low/big/not sleeping not divine/"+this.getName()+".png");
		this.lowBigAsNdimage=ImageLoader.loadImage("/textures/All Cards/minions/low/big/sleeping not divine/"+this.getName()+".png");
		this.lowBigAsAdImage=ImageLoader.loadImage("/textures/All Cards/minions/low/big/sleeping and divine/"+this.getName()+".png");
		this.highSmallNsAdImage=ImageLoader.loadImage("/textures/All Cards/minions/high/small/not sleeping and divine/"+this.getName()+".PNG");
		this.highSmallNsNdImage=ImageLoader.loadImage("/textures/All Cards/minions/high/small/not sleeping not divine/"+this.getName()+".png");
		this.highSmallAsNdimage=ImageLoader.loadImage("/textures/All Cards/minions/high/small/sleeping not divine/"+this.getName()+".png");
		this.highSmallAsAdImage=ImageLoader.loadImage("/textures/All Cards/minions/high/small/sleeping and divine/"+this.getName()+".png");
		this.attackSound=new File("res/Attack sound.wav");
		this.playSound=new File("res/Play/"+this.getName()+" Play.wav");
		this.deathSound=new File("res/Death/"+this.getName()+" Death.wav");
	}
	public BufferedImage getImage() {
		if(this.isSleeping()&&this.isDivine())
			return this.lowSmallAsAdImage;
		else if(this.isSleeping()&&!this.isDivine())
			return this.lowSmallAsNdimage;
		else if(!this.isSleeping()&&this.isDivine())
			return this.lowSmallNsAdImage;
		else {

			return this.lowSmallNsNdImage;
		}
		
	}
	
	public BufferedImage getBigImage() {
		//System.out.println("NNNN");
		if(this.isSleeping()&&this.isDivine())
			return this.lowBigAsAdImage;
		else if(this.isSleeping()&&!this.isDivine())
			return this.lowBigAsNdimage;
		else if(!this.isSleeping()&&this.isDivine())
			return this.lowBigNsAdImage;
		else {
			//System.out.println("NNNN");
			return this.lowBigNsNdImage;
		}
		
	}
	
	public BufferedImage getHighImage() {
		
		if(this.isSleeping()&&this.isDivine())
			return this.highSmallAsAdImage;
		else if(this.isSleeping()&&!this.isDivine())
			return this.highSmallAsNdimage;
		else if(!this.isSleeping()&&this.isDivine())
			return this.highSmallNsAdImage;
		else {
			//System.out.println("NNNN");
			return this.highSmallNsNdImage;
		}
		
	}

	public File getAttackSound() {
		return attackSound;
	}

	public File getPlaySound() {
		return playSound;
	}

	public File getDeathSound() {
		return deathSound;
	}
	public Object clone() throws CloneNotSupportedException {
		return (Minion) super.clone();
		
	}
	public void attack(Minion target) {
		if(this.divine&&target.getAttack()>0) 
			this.divine=false;
		else
			this.setCurrentHP(this.getCurrentHP()-target.attack);
		if(target.isDivine()&&this.getAttack()>0) {
			target.setDivine(false);
		}
		else
			target.setCurrentHP(target.getCurrentHP()-this.attack);
		this.setAttacked(true);
	}
	public void attack(Hero target) throws InvalidTargetException{
		if(this.getName().equals("Icehowl"))
			throw new InvalidTargetException("Icehowl Can Not Attack Hero");
		target.setCurrentHP(target.getCurrentHP()-this.attack);
		this.setAttacked(true);
	}
	
	
	
	
	//SETTER AND GETTER
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		if(attack<0)attack=0;
		this.attack = attack;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
		if(this.currentHP<=0)
			this.listener.onMinionDeath(this);
	}
	public boolean isTaunt() {
		return taunt;
	}
	public void setTaunt(boolean taunt) {
		this.taunt = taunt;
	}
	public boolean isDivine() {
		return divine;
	}
	public void setDivine(boolean divine) {
		this.divine = divine;
	}
	public boolean isSleeping() {
		return sleeping;
	}
	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}
	public boolean isAttacked() {
		return attacked;
	}
	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
	
    public void setListener(MinionListener listener) {
		this.listener = listener;
	}
    
    
    
	public String toString() {
    	return"(" +this.getName()+",M"+this.getManaCost()+", HP"+this.currentHP+",AT "+this.attack+",T"+this.taunt+",D"+this.divine+",C"+!this.sleeping+")";
    }
   

}
