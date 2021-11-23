package input;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import GUI.PlaySound;
import engine.Game;
import launcher.HeroSel1;
import launcher.Launcher;
import launcher.PlayersNames;

public class MouseManager implements MouseListener, MouseMotionListener {

	private boolean leftPressed, rightPressed;
	private int mouseX, mouseY;
	private Game game;
	private HeroSel1 herosel1;
	private PlayersNames names;
	
	
	public MouseManager(){
		
	}
	
	
	// Getters
	
	public boolean isLeftPressed(){
		return leftPressed;
	}
	
	public boolean isRightPressed(){
		return rightPressed;
	}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}
	
	// Implemented methods
	
	@Override
	public synchronized void mousePressed(MouseEvent e) {

		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = true;
		else if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = true;

	}

	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		if(this.names.isRunning())return;
		//if(this.herosel1.getT1()!=null&&this.herosel1.getT1().isAlive())return;
		if(PlaySound.getClip()!=null &&PlaySound.getClip().isRunning()) {
			return;}
		
		if(this.herosel1.isRunning()) {
			this.herosel1.actiontemp(e.getX(),e.getY());
			return;
		}
		if(this.game!=null&&this.game.getT1()!=null&&this.game.getT1().isAlive())return;
		
		if(this.game.isEndGame()) {
			if((new Rectangle(Launcher.getcorW(1600), Launcher.getcorH(50), Launcher.getcorW(150), Launcher.getcorH(70))).contains(e.getX(),e.getY())) {
				game.exit();
			}
			else if(new Rectangle(Launcher.getcorW(1600)-Launcher.getcorW(170), Launcher.getcorH(50), Launcher.getcorW(150), Launcher.getcorH(70)).contains(e.getX(),e.getY())) {
				game.again();
			}
			return;
		}
		if(this.game.getExceptionGraphics().isRunning()) {
			this.game.getExceptionGraphics().action(e.getX(),e.getY());
			return;
		}
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = false;
			game.actionleft(e.getX(),e.getY());
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			rightPressed = false;
		    game.actionright();
		}
		
	}

	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		//System.out.println(this.herosel1);
		if(this.names!=null&&this.names.isRunning())return;
		//if(this.herosel1!=null&&this.herosel1.getT1()!=null&&this.herosel1.getT1().isAlive())return;
		if(PlaySound.getClip()!=null &&PlaySound.getClip().isRunning()) {
			return;}
		
		if(this.herosel1!=null&&this.herosel1.isRunning()) {
			this.herosel1.hovring(e.getX(), e.getY());
			return;
		}
		if(this.game!=null&&this.game.getT1()!=null&&this.game.getT1().isAlive())return;
		if(this.game!=null&&this.game.getExceptionGraphics()!=null&&this.game.getExceptionGraphics().isRunning())return;
		

        if(this.game!=null)
		    this.game.hovring(e);
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}


	public HeroSel1 getHerosel1() {
		return herosel1;
	}


	public void setHerosel1(HeroSel1 herosel1) {
		this.herosel1 = herosel1;
	}


	public void setNames(PlayersNames names) {
		this.names = names;
	}
	
	

}
