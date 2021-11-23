package model.cards;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import GUI.CardImage;
import GUI.ImageLoader;
import model.cards.minions.Minion;

abstract public class Card implements Cloneable{
	private String name;
	private int manaCost;
	private Rarity rarity;
	private Rectangle bounds;
	public Card(String name,int manaCost,Rarity rarity) {
		this.name=name;
		if(manaCost>10)
			this.manaCost=10;
		else if(manaCost<0)
			this.manaCost=0;
		else
		    this.manaCost=manaCost;
		this.rarity=rarity;
	}

	public Object clone() throws CloneNotSupportedException {
		return (Card)super.clone();
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		if(this instanceof Minion)
			((Minion)this).init();
	}
	public int getManaCost() {
		return manaCost;
	}
	public void setManaCost(int manaCost) {
		if(manaCost>10)
			manaCost=10;
		else if(manaCost<0)
			manaCost=0;
		this.manaCost = manaCost;
	}
	public Rarity getRarity() {
		return rarity;
	}
	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
