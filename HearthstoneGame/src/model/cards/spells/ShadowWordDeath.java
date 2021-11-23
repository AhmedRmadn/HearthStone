package model.cards.spells;

import java.io.File;

import exceptions.InvalidTargetException;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class ShadowWordDeath  extends Spell implements MinionTargetSpell {

	public ShadowWordDeath() {
		super("Shadow Word: Death",3,Rarity.BASIC);
	}

	@Override
	public void performAction(Minion m) throws InvalidTargetException {
		if(m.getAttack()>=5) {
			m.setCurrentHP(0);
		}
		else {
			throw new InvalidTargetException("Choose a minion with 5 or more attack");
		}
		Sound.playSound(new File("res/Spell sound.wav"));
		
	}

}
