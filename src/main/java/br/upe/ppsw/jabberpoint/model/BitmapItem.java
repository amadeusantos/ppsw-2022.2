package br.upe.ppsw.jabberpoint.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.util.ResourceUtils;

import br.upe.ppsw.jabberpoint.view.Style;
import br.upe.ppsw.jabberpoint.view.Visitor;

public class BitmapItem extends SlideItem {

	private BufferedImage bufferedImage;
	private String imageName;

	protected static final String FILE = "Arquivo ";
	protected static final String NOTFOUND = " não encontrado";

	public BitmapItem(int level, String name) {
		super(level);

		imageName = name;

		try {
			bufferedImage = ImageIO.read(ResourceUtils.getFile(imageName).getAbsoluteFile());
		} catch (IOException e) {
			System.err.println(FILE + imageName + NOTFOUND);
		}

	}

	public BitmapItem() {
		this(0, null);
	}

	public String getName() {
		return imageName;
	}

	public String toString() {
		return "BitmapItem[" + getLevel() + "," + imageName + "]";
	}

	public BufferedImage getBufferdImage() {
		return this.bufferedImage;
	}

	@Override
	public void acceptDraw(Visitor visitor, int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o) {
		visitor.drawBitmapItem(x, y, scale, g, myStyle, o, this);
	}

	@Override
	public Rectangle acceptGetBoundingBox(Visitor visitor, Graphics g, ImageObserver observer, float scale,
			Style myStyle) {
		return visitor.getBoundingBoxbBitmapItem(g, observer, scale, myStyle, this);
	}

}
