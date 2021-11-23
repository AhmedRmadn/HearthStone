package model.heroes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import GUI.ImageLoader;
import exceptions.*;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.KillCommand;
import model.cards.spells.MultiShot;

public class Hunter extends Hero {

	public Hunter() throws IOException, CloneNotSupportedException {
		super("Rexxar");
		this.setImage(ImageLoader.loadImage("/textures/heros/rexxar.png"));
		this.setHeroPowerImage(ImageLoader.loadImage("/textures/Power/HunterPower.png"));
		this.setHeroPowerImageHigh(ImageLoader.loadImage("/textures/heroPowerhigh/HunterPower.png"));
		
	}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		Random rand=new Random();
		ArrayList<Minion> minions=Hero.getNeutralMinions(Hero.getAllNeutralMinions("res/neutral_minions.CSV"), 15);
		ArrayList<Card> hunterDeck=new ArrayList<Card>();
		minions.add(new Minion("King Krush",9,Rarity.LEGENDARY,8,8,false,false,true));
		for(int i=0;i<minions.size();i++) {
			minions.get(i).setListener(this);
			hunterDeck.add(minions.get(i));
		}
		hunterDeck.add(new KillCommand());
		hunterDeck.add(new KillCommand());
		hunterDeck.add(new MultiShot());
		hunterDeck.add(new MultiShot());
		
		int index;
		//this.deck=new ArrayList<Card>();
		int size =hunterDeck.size();
		for(int i=0;i<size;i++) {
			index=rand.nextInt(hunterDeck.size());
			this.getDeck().add(hunterDeck.get(index));
			hunterDeck.remove(index);
		}
		
	}
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
		super.useHeroPower();
		this.getListener().damageOpponent(2);
		Sound.playSound(new File("res/HeroPower sound.wav"));
		
	}



}
