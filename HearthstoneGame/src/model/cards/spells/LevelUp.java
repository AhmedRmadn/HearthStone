package model.cards.spells;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class LevelUp extends Spell implements FieldSpell{

	public LevelUp() {
		super("Level Up!",6,Rarity.EPIC);
	}

	@Override
	public void performAction(ArrayList<Minion> field) {
		for(int i = 0;i<field.size();i++) {
			if(field.get(i).getName().equals("Silver Hand Recruit")) {
				field.get(i).setMaxHP(field.get(i).getMaxHP()+1);
				field.get(i).setCurrentHP(field.get(i).getCurrentHP()+1);
				field.get(i).setAttack(field.get(i).getAttack()+1);
			}
				
			}
		Sound.playSound(new File("res/Spell sound.wav"));
		
	}




}
