package model.cards.spells;

import java.io.File;
import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class TwistingNether extends Spell implements AOESpell{

	public TwistingNether() {
		super("Twisting Nether",8,Rarity.EPIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		for(int i=0;i<oppField.size();i++) {
			oppField.get(i--).setCurrentHP(0);
		}
		for(int i=0;i<curField.size();i++) {
			curField.get(i--).setCurrentHP(0);
		}
		Sound.playSound(new File("res/Spell sound.wav"));
		
	}

}
