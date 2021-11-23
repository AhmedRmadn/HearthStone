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
import model.cards.spells.DivineSpirit;
import model.cards.spells.HolyNova;
import model.cards.spells.LevelUp;
import model.cards.spells.SealOfChampions;
import model.cards.spells.ShadowWordDeath;

public class Priest extends Hero {

	public Priest() throws IOException, CloneNotSupportedException {
		super("Anduin Wrynn");
		this.setImage(ImageLoader.loadImage("/textures/heros/anduin wrynn.png"));
		this.setHeroPowerImage(ImageLoader.loadImage("/textures/Power/PriestPower.png"));
		this.setHeroPowerImageHigh(ImageLoader.loadImage("/textures/heroPowerhigh/PriestPower.png"));
	}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		Random rand=new Random();
		ArrayList<Minion> minions=Hero.getNeutralMinions(Hero.getAllNeutralMinions("res/neutral_minions.CSV"), 13);
		ArrayList<Card> priestDeck=new ArrayList<Card>();
		minions.add(new Minion("Prophet Velen",7,Rarity.LEGENDARY,7,7,false,false,false));
		for(int i=0;i<minions.size();i++) {
			minions.get(i).setListener(this);
			priestDeck.add(minions.get(i));
		}
		priestDeck.add(new DivineSpirit());
		priestDeck.add(new DivineSpirit());
		priestDeck.add(new HolyNova());
		priestDeck.add(new HolyNova());
		priestDeck.add(new ShadowWordDeath());
		priestDeck.add(new ShadowWordDeath());
		
		int index;
		//this.deck=new ArrayList<Card>();
		int size=priestDeck.size();
		for(int i=0;i<size;i++) {
			index=rand.nextInt(priestDeck.size());
			this.getDeck().add(priestDeck.get(index));
			priestDeck.remove(index);
		
	}
	}
	public void useHeroPower(Hero h) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
        super.useHeroPower();
	    if(this.isProphetVelenField())
		    h.setCurrentHP(h.getCurrentHP()+8);///Current Can Exceed the Max
	    else
	    	h.setCurrentHP(h.getCurrentHP()+2);
	    Sound.playSound(new File("res/HeroPower sound.wav"));
		
	}
	public void useHeroPower(Minion m) throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{

        super.useHeroPower();
	    if(this.isProphetVelenField())
		    m.setCurrentHP(m.getCurrentHP()+8);
	    else
		    m.setCurrentHP(m.getCurrentHP()+2);///Current Can Exceed the Max
	    Sound.playSound(new File("res/HeroPower sound.wav"));

		
	}
	
}
