package model.cards.spells;

import java.io.File;
import java.util.ArrayList;


import model.cards.Rarity;
import model.cards.minions.Minion;

public class HolyNova extends Spell implements AOESpell{

	public HolyNova() {
		super("Holy Nova",5,Rarity.BASIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		//int size=oppField.size();
		for(int i=0;i<oppField.size();i++) {
			int current=oppField.get(i).getCurrentHP();
			oppField.get(i).setCurrentHP(oppField.get(i).getCurrentHP()-2);
				if(current<=2)
					i--;	
		}
		for(int i=0;i<curField.size();i++) {
			curField.get(i).setCurrentHP(curField.get(i).getCurrentHP()+2);
		}
		Sound.playSound(new File("res/Spell sound.wav"));
		
	}

}
