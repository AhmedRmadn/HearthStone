package model.cards.spells;

import java.io.File;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class SiphonSoul extends Spell implements LeechingSpell{

	public SiphonSoul() {
		super("Siphon Soul",6,Rarity.RARE);
	}

	@Override
	public int performAction(Minion m) {
		
	    m.setCurrentHP(0);
		Sound.playSound(new File("res/Spell sound.wav"));
		return 3;
	}

}
