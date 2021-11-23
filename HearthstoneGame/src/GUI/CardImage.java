package GUI;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class CardImage {
	private String name;
	private BufferedImage image;
	
	
	public CardImage(String name,BufferedImage image) {
		this.name=name;
		this.image=image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}


}
