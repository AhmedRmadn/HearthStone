package view;

import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import input.KeyManger;
import input.MouseManager;
import model.heroes.Hero;
import model.heroes.Hunter;
import model.heroes.*;

public class Handler {
	private Game game;
	private int width;
	private int height;
	public Handler(Hero hero1,Hero hero2) {
//		this.width=width;
//		this.height=height;
		try {
			this.game=new Game(hero1,hero2);
		} catch (FullHandException | CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static Hero ch() throws IOException, CloneNotSupportedException {
		Random rand =new Random();
		int ind=rand.nextInt(5);
		if(ind==0)
			return new Hunter();
		if(ind ==1)
			return new Mage();
		if(ind==2)
			return new Paladin();
		if(ind==3)
			return new Priest();
		return new Warlock();
	}
    
	/*public  void tick() {
		
		Scanner sc =new Scanner(System.in);
		Helper help=new Helper(game);

		while(help.isRunning()) {
			try {
		System.out.println(game.getOpponent());
		System.out.println("***************************");
		System.out.println(game.getOpponent().getField());
		System.out.println("***************************");
    	System.out.println(game.getCurrentHero().getField());
		System.out.println("***************************");
		System.out.println(game.getCurrentHero().getHand());
		System.out.println("***************************");
		System.out.println(game.getCurrentHero());
		String s=sc.nextLine();
		if(s.equals("e"))game.endTurn();
		else if(s.charAt(0)=='p') {
			if(s.length()>2) game.playCard(game.getCurrentHero().getHand().get(Integer.parseInt(s.charAt(1)+"")), game.getOpponent().getField().get(Integer.parseInt(s.charAt(2)+"")));
			else
				game.playCard(game.getCurrentHero().getHand().get(Integer.parseInt(s.charAt(1)+"")),null);
				
		}
		else if(s.charAt(0)=='a') {
			if(s.length()>2) {
				game.getCurrentHero().attackWithMinion(game.getCurrentHero().getField().get(Integer.parseInt(s.charAt(1)+"")), game.getOpponent().getField().get(Integer.parseInt(s.charAt(2)+"")));
			}
			else {
				game.getCurrentHero().attackWithMinion(game.getCurrentHero().getField().get(Integer.parseInt(s.charAt(1)+"")), game.getOpponent());
			}
		}
		else if(s.charAt(0)=='h') {
			if(s.length()>1)
			     game.getCurrentHero().useHeroPower( game.getOpponent().getField().get(Integer.parseInt(s.charAt(1)+"")));
			else
				game.getCurrentHero().useHeroPower(game.getOpponent());
		}
  		}
    		catch( CloneNotSupportedException e) {
				
			}
			catch(FullHandException e) {
				System.out.println(e.getMessage());
			}
			catch(NotYourTurnException e) {
			    System.out.println(e.getMessage());
		    }
      	    catch(NotEnoughManaException e) {
		    	System.out.println(e.getMessage());
		    }
		    catch(FullFieldException e) {
		    	System.out.println(e.getMessage());
		    }
		    catch(InvalidTargetException e) {
		    	System.out.println(e.getMessage());
		    }
		    catch(CannotAttackException e) {
	    	System.out.println(e.getMessage());
		    }
		    catch(TauntBypassException e) {
		    	System.out.println(e.getMessage());
		    }
		    catch(NotSummonedException e) {
		    	System.out.println(e.getMessage());
		    }
		    catch(HeroPowerAlreadyUsedException e) {
		    	System.out.println(e.getMessage());
		    }
			catch(NullPointerException e) {
				System.out.println("NullPointerException");
			}

	}

}*/
	public void render(Graphics g,MouseManager mouseManager,KeyManger keyManger) {
		game.render(g,this.width,this.height,mouseManager,keyManger);
	}


	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}




	}

