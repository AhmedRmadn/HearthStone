package model.heroes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import GUI.ImageLoader;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.Flamestrike;
import model.cards.spells.MultiShot;
import model.cards.spells.Polymorph;
import model.cards.spells.Pyroblast;

public class Mage extends Hero{

	public Mage() throws IOException, CloneNotSupportedException{
		super("Jaina Proudmoore");
		this.setImage(ImageLoader.loadImage("/textures/heros/Jaina Proudmoore.png"));
		this.setHeroPowerImage(ImageLoader.loadImage("/textures/Power/MagePower.png"));
		this.setHeroPowerImageHigh(ImageLoader.loadImage("/textures/heroPowerhigh/MagePower.png"));
	}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		Random rand=new Random();
		ArrayList<Minion> minions=Hero.getNeutralMinions(Hero.getAllNeutralMinions("res/neutral_minions.CSV"), 13);
		ArrayList<Card> mageDeck=new ArrayList<Card>();
		minions.add(new Minion("Kalycgos",10,Rarity.LEGENDARY,4,12,false,false,false));
		for(int i=0;i<minions.size();i++) {
			minions.get(i).setListener(this);
			mageDeck.add(minions.get(i));
		}
		mageDeck.add(new Polymorph());
		mageDeck.add(new Polymorph());
		mageDeck.add(new Flamestrike());
		mageDeck.add(new Flamestrike());
		mageDeck.add(new Pyroblast());
		mageDeck.add(new Pyroblast());
		
		int index;
		int size=mageDeck.size();
		for(int i=0;i<size;i++) {
			index=rand.nextInt(mageDeck.size());
		
			this.getDeck().add(mageDeck.get(index));
			mageDeck.remove(index);
		}
		
	}


	public void useHeroPower(Minion m) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
			FullHandException, FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
	    if(m.isDivine())
	    	m.setDivine(false);
	    else
	     	m.setCurrentHP(m.getCurrentHP()-1);
	    Sound.playSound(new File("res/HeroPower sound.wav"));

		
	}
	public void useHeroPower(Hero h) throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
	FullHandException, FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		h.setCurrentHP(h.getCurrentHP()-1);
		Sound.playSound(new File("res/HeroPower sound.wav"));

    }



}
