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
import model.cards.spells.*;

public class Warlock extends Hero {

	public Warlock() throws IOException, CloneNotSupportedException{
		super("Gul'dan");
		this.setImage(ImageLoader.loadImage("/textures/heros/gul'dan.png"));
		this.setHeroPowerImage(ImageLoader.loadImage("/textures/Power/WarlockPower.png"));
		this.setHeroPowerImageHigh(ImageLoader.loadImage("/textures/heroPowerhigh/WarlockPower.png"));
		}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		Random rand=new Random();
		ArrayList<Minion> minions=Hero.getNeutralMinions(Hero.getAllNeutralMinions("res/neutral_minions.CSV"), 13);
		ArrayList<Card> warlockDeck=new ArrayList<Card>();
		minions.add(new Minion("Wilfred Fizzlebang",6,Rarity.LEGENDARY,4,4,false,false,false));
		for(int i=0;i<minions.size();i++) {	
			minions.get(i).setListener(this);
			warlockDeck.add(minions.get(i));
		}
		warlockDeck.add(new CurseOfWeakness());
		warlockDeck.add(new CurseOfWeakness());
		warlockDeck.add(new SiphonSoul());
		warlockDeck.add(new SiphonSoul());
		warlockDeck.add(new TwistingNether());
		warlockDeck.add(new TwistingNether());
		
		int index;
		//this.deck=new ArrayList<Card>();
		int size=warlockDeck.size();
		for(int i=0;i<size;i++) {
			index=rand.nextInt(warlockDeck.size());
			this.getDeck().add(warlockDeck.get(index));
			warlockDeck.remove(index);
		}
		
	}
	@Override
	public void useHeroPower() throws NotEnoughManaException,
	HeroPowerAlreadyUsedException, NotYourTurnException, FullHandException,
	FullFieldException, CloneNotSupportedException{
        super.useHeroPower();
		this.setCurrentHP(this.getCurrentHP()-2);///Current Can Exceed the Max
		if(this.getHand().size()>=10)
			throw new FullHandException("The Hand has 10 Cards",this.drawCard());
		Card draw=this.drawCard();
		if(this.isWilfredFizzlebang()&&!(draw instanceof Spell))
			draw.setManaCost(0);
		Sound.playSound(new File("res/HeroPower sound.wav"));

		
	}
	

}
