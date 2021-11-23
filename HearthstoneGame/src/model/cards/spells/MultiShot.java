package model.cards.spells;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class MultiShot extends Spell implements AOESpell {

	public MultiShot() {
		super("Multi-Shot",4,Rarity.BASIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		
		if(oppField.size()<=0)
			return;
		Random rand =new Random();
		if(oppField.size()==1) {
			if(oppField.get(0).isDivine())
				oppField.get(0).setDivine(false);
			else {
				oppField.get(0).setCurrentHP(oppField.get(0).getCurrentHP()-3);
			}
			Sound.playSound(new File("res/Spell sound.wav"));
			return;
		}
		else {
			boolean d=false;
			int ind=rand.nextInt(oppField.size());
			if(oppField.get(ind).isDivine())
				oppField.get(ind).setDivine(false);
			else {
				if(oppField.get(ind).getCurrentHP()-3>=0)d=true;
				oppField.get(ind).setCurrentHP(oppField.get(ind).getCurrentHP()-3);
			}

			if(!d) {
				int ind2=rand.nextInt(oppField.size());
				while(ind==ind2) {
					ind2=rand.nextInt(oppField.size());
				}
				if(oppField.get(ind2).isDivine())
					oppField.get(ind2).setDivine(false);
				else 
					oppField.get(ind2).setCurrentHP(oppField.get(ind2).getCurrentHP()-3);
				Sound.playSound(new File("res/Spell sound.wav"));
				return;
			}
			else {
				int ind3=rand.nextInt(oppField.size());
				if(oppField.get(ind3).isDivine())
					oppField.get(ind3).setDivine(false);
				else 
					oppField.get(ind3).setCurrentHP(oppField.get(ind3).getCurrentHP()-3);
			}
			Sound.playSound(new File("res/Spell sound.wav"));
			return;
		}


	}

}
