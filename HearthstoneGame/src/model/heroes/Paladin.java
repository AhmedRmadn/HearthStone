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
import model.cards.spells.LevelUp;
import model.cards.spells.MultiShot;
import model.cards.spells.SealOfChampions;

public class Paladin extends Hero {

	public Paladin() throws IOException, CloneNotSupportedException{
		super("Uther Lightbringer");
		this.setImage(ImageLoader.loadImage("/textures/heros/uther lightbringer.png"));
	    this.setHeroPowerImage(ImageLoader.loadImage("/textures/Power/PaladinPower.png"));
	    this.setHeroPowerImageHigh(ImageLoader.loadImage("/textures/heroPowerhigh/PaladinPower.png"));
	}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		Random rand=new Random();
		ArrayList<Minion> minions=Hero.getNeutralMinions(Hero.getAllNeutralMinions("res/neutral_minions.CSV"), 15);
		ArrayList<Card> paladinDeck=new ArrayList<Card>();
		minions.add(new Minion("Tirion Fordring",4,Rarity.LEGENDARY,6,6,true,true,false));
		for(int i=0;i<minions.size();i++) {
			minions.get(i).setListener(this);
			paladinDeck.add(minions.get(i));
		}
		paladinDeck.add(new SealOfChampions());
		paladinDeck.add(new SealOfChampions());
		paladinDeck.add(new LevelUp());
		paladinDeck.add(new LevelUp());
		
		int index;
		//this.deck=new ArrayList<Card>();
		int size=paladinDeck.size();
		for(int i=0;i<size;i++) {
			index=rand.nextInt(paladinDeck.size());
			this.getDeck().add(paladinDeck.get(index));
			paladinDeck.remove(index);
		}
		
	}
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
	    if(this.getField().size()>=7)
	    	throw new FullFieldException("The Field Has 7 Cards");
        super.useHeroPower();
	    Minion silverHandRecruit=new Minion("Silver Hand Recruit",1,Rarity.BASIC,1,1,false,false,false);
	    silverHandRecruit.setListener(this);
	    if(this.getField().size()<7)
		     this.getField().add(silverHandRecruit);
	    Sound.playSound(new File("res/Play/Silver Hand Recruit Play.wav"));
		
	}



}
