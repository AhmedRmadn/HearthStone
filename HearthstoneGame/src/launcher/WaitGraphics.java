package launcher;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import GUI.ImageLoader;

public class WaitGraphics {
	private BufferedImage frames[];
	private int tick=0;
	private int index=0;
	public WaitGraphics() {
		frames=new BufferedImage[13];
		load();
	}
	private void load() {
		
	   this.frames[0]=ImageLoader.loadImage("/textures/FramePNG/F1.png");
	   this.frames[1]=ImageLoader.loadImage("/textures/FramePNG/F2.png");
	   this.frames[2]=ImageLoader.loadImage("/textures/FramePNG/F3.png");
	   this.frames[3]=ImageLoader.loadImage("/textures/FramePNG/F4.png");
	   this.frames[4]=ImageLoader.loadImage("/textures/FramePNG/F5.png");
	   this.frames[5]=ImageLoader.loadImage("/textures/FramePNG/F6.png");
	   this.frames[6]=ImageLoader.loadImage("/textures/FramePNG/F7.png");
	   this.frames[7]=ImageLoader.loadImage("/textures/FramePNG/F8.png");
	   this.frames[8]=ImageLoader.loadImage("/textures/FramePNG/F9.png");
	   this.frames[9]=ImageLoader.loadImage("/textures/FramePNG/F10.png");
	   this.frames[10]=ImageLoader.loadImage("/textures/FramePNG/F11.png");
	   this.frames[11]=ImageLoader.loadImage("/textures/FramePNG/F12.png");
	   this.frames[12]=ImageLoader.loadImage("/textures/FramePNG/F13.png");
//	   this.frames[13]=ImageLoader.loadImage("/textures/FramePNG/F14.png");
//	   this.frames[14]=ImageLoader.loadImage("/textures/FramePNG/F15.png");
//	   this.frames[15]=ImageLoader.loadImage("/textures/FramePNG/F16.png");

		
	}
	public void render(Graphics g) {
		if(tick>=7) {
			tick=0;
			if(index+1>=this.frames.length)index=0;
			else index++;

		}
		else {
			tick++;

		}
		g.drawImage(this.frames[index], Launcher.getcorW(500), Launcher.getcorH(150),Launcher.getcorW(867), Launcher.getcorH(750),null);

	
	}

}
