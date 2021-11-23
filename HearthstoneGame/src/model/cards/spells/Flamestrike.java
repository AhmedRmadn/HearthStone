package model.cards.spells;

import java.io.File;
import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class Flamestrike extends Spell implements AOESpell{

	public Flamestrike() {
		super("Flamestrike",7,Rarity.BASIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		int size=oppField.size();
		for(int i=0;i<oppField.size();i++) {
			int current=oppField.get(i).getCurrentHP();
			if(oppField.get(i).isDivine())
				oppField.get(i).setDivine(false);
			else {
			    oppField.get(i).setCurrentHP(oppField.get(i).getCurrentHP()-4);
			
			    if(current<=4)
				    i--;
			}
				
		}
		Sound.playSound(new File("res/Spell sound.wav"));
		
	}

}
