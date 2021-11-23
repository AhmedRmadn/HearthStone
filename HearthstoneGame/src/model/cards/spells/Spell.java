package model.cards.spells;

import java.awt.image.BufferedImage;

import GUI.ImageLoader;
import GUI.PlaySound;
import model.cards.*;


abstract public class Spell extends Card implements Cloneable{
	
	private BufferedImage lowSmallImage;
	private BufferedImage lowBigImageLow;
	private BufferedImage highSmallImage;
	protected PlaySound Sound =new PlaySound();

	
	public Spell(String name, int manaCost ,Rarity rarity) {
		super(name,manaCost,rarity);
		init();
	}
	public Object clone() throws CloneNotSupportedException {
		return (Spell)super.clone();
		
	}

	public void init() {
		if(this.getName().equals("Shadow Word: Death")) {
			this.highSmallImage=ImageLoader.loadImage("/textures/All Cards/spells/high/small/"+"Shadow Word Death"+".PNG");
			this.lowSmallImage=ImageLoader.loadImage("/textures/All Cards/spells/low/big/"+"Shadow Word Death"+".PNG");
			this.lowBigImageLow=ImageLoader.loadImage("/textures/All Cards/spells/low/big/"+"Shadow Word Death"+".PNG");
			
		}
		else {
		this.highSmallImage=ImageLoader.loadImage("/textures/All Cards/spells/high/small/"+this.getName()+".PNG");
		this.lowSmallImage=ImageLoader.loadImage("/textures/All Cards/spells/low/big/"+this.getName()+".PNG");
		this.lowBigImageLow=ImageLoader.loadImage("/textures/All Cards/spells/low/big/"+this.getName()+".PNG");
		}
	}
	public BufferedImage getImage() {
		return this.lowSmallImage;
	}
	
	public BufferedImage getBigImage() {
		return this.lowBigImageLow;
	}
	
	public BufferedImage getHighImage() {
		return this.highSmallImage;
	}


	public String toString () {
		return "("+this.getName()+" , "+this.getManaCost()+")";
	}

}
