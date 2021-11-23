package GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import engine.Game;
import launcher.Launcher;
import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.cards.spells.Spell;

public class DrawCard {
	//private BufferedImage image;
	private Font f20;
	private Font f30;
	private final int w=Launcher.getcorW(135);
	private final int h=Launcher.getcorH(162);;
	private Game game;
	public DrawCard(Game game) {
		this.game=game;
		init();
	}
	public void init() {
		f20=FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(20));
		f30=FontLoader.loadFont("res/fonts/Chunkfive.otf", Launcher.getcorW(30));

	}
	
	
	public void render(Graphics g,Card card,int x,int y) {
				if(card instanceof Minion&&card.getRarity()!=Rarity.LEGENDARY) {
					if(card==this.game.getCard1()||card==this.game.getCard2()) 
						g.drawImage(((Minion)card).getHighImage(),x, y,w,h,null);
					else
					   g.drawImage(((Minion)card).getImage(),x, y,w,h,null);
					if(card.getManaCost()>9)
					    Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(6), y+Launcher.getcorH(26), false, Color.white, f20);
					else {
						Text.drawString(g, card.getManaCost()+"",x+Launcher.getcorW(12), y+Launcher.getcorH(23), false, Color.white, f20);
					}
					if(((Minion) card).getAttack()>9)
				        Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(5),y+h-Launcher.getcorH(12), false, Color.white, f20);
					else 
						Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(13),y+h-Launcher.getcorH(13), false, Color.white, f20);
					if(((Minion) card).getCurrentHP()>9)
					    Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(22), y+h-Launcher.getcorH(8), false, Color.white, f20);
					else 
						Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(22), y+h-Launcher.getcorH(12), false, Color.white, f20);
					
				}
				else if(card instanceof Minion&&card.getRarity()==Rarity.LEGENDARY) {
					if(card==this.game.getCard1()||card==this.game.getCard2()) 
						g.drawImage(((Minion)card).getHighImage(),x, y,w,h,null);
					else
					   g.drawImage(((Minion)card).getImage(),x, y,w,h,null);
					if(card.getManaCost()>9)
					    Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(7), y+Launcher.getcorH(30), false, Color.white, f20);
					else
						Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(15), y+Launcher.getcorH(29), false, Color.white, f20);
					if(((Minion) card).getAttack()>9)
				        Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(8),y+h-Launcher.getcorH(12), false, Color.white, f20);
					else 
						Text.drawString(g, ((Minion) card).getAttack()+"",x+Launcher.getcorW(15),y+h-Launcher.getcorH(12), false, Color.white, f20);
					if(((Minion) card).getCurrentHP()>9)
					    Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(25), y+h-Launcher.getcorH(10), false, Color.white, f20);
					else 
						Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(22), y+h-Launcher.getcorH(13), false, Color.white, f20);
				}
				else if(card instanceof Spell) {
					if(card==this.game.getCard1()||card==this.game.getCard2()) 
						g.drawImage(((Spell)card).getHighImage(),x, y,w,h,null);
					else
					   g.drawImage(((Spell)card).getImage(),x, y,w,h,null);
					if(card.getManaCost()>9)
					    Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(6), y+Launcher.getcorH(23), false, Color.white, f20);
					else
						Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(11), y+Launcher.getcorH(25), false, Color.white, f20);
					
				}

				return;
	}
	public void render(Graphics g,Card card,int x,int y,int w,int h) {

				if(card instanceof Minion&&card.getRarity()!=Rarity.LEGENDARY) {
					///System.out.println(((Minion)card)==((Minion)card).getGraphics().getMinion());
					g.drawImage(((Minion)card).getBigImage(),x, y,w,h,null);
					if(card.getManaCost()>9)
					    Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(2), y+Launcher.getcorH(32), false, Color.white, f30);
					else
						Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(28), y+Launcher.getcorH(40), false, Color.white, f30);
					if(((Minion) card).getAttack()>9)
				        Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(3),y+h-Launcher.getcorH(8), false, Color.white, f30);
					else 
						Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(32),y+h-Launcher.getcorH(25), false, Color.white, f30);
					if(((Minion) card).getCurrentHP()>9)
					    Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(28), y+h-Launcher.getcorH(5), false, Color.white, f30);
					else 
						Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(43), y+h-Launcher.getcorH(25), false, Color.white, f30);
					
				}
				else if(card instanceof Minion&&card.getRarity()==Rarity.LEGENDARY) {
					g.drawImage(((Minion)card).getBigImage(),x, y,w,h,null);
					if(card.getManaCost()>9)
					    Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(25), y+Launcher.getcorH(55), false, Color.white, f30);
					else
						Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(32), y+Launcher.getcorH(60), false, Color.white, f30);
					if(((Minion) card).getAttack()>9)
				        Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(23),y+h-Launcher.getcorH(24), false, Color.white, f30);
					else 
						Text.drawString(g, ((Minion) card).getAttack()+"", x+Launcher.getcorW(33),y+h-Launcher.getcorH(25), false, Color.white, f30);
					if(((Minion) card).getCurrentHP()>9)
					    Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(50), y+h-Launcher.getcorH(20), false, Color.white, f30);
					else 
						Text.drawString(g, ((Minion) card).getCurrentHP()+"", x+w-Launcher.getcorW(40), y+h-Launcher.getcorH(25), false, Color.white, f30);
				}
				else if(card instanceof Spell) {
					g.drawImage(((Spell)card).getBigImage(),x, y,w,h,null);
					if(card.getManaCost()>9)
					    Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(18), y+Launcher.getcorH(43), false, Color.white, f30);
					else
						Text.drawString(g, card.getManaCost()+"", x+Launcher.getcorW(28), y+Launcher.getcorH(41), false, Color.white, f30);
					
				}
				card.setBounds(null);
				return;
	}
	public Font getF30() {
		return f20;
	}
	public void setF30(Font f30) {
		this.f20 = f30;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public int getW() {
		return w;
	}
	public int getH() {
		return h;
	}
}
