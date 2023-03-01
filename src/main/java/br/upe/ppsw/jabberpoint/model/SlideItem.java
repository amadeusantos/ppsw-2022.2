package br.upe.ppsw.jabberpoint.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import br.upe.ppsw.jabberpoint.view.Style;
import br.upe.ppsw.jabberpoint.view.Visitor;

public abstract class SlideItem {

	private int level = 0;

	public SlideItem(int lev) {
		level = lev;
	}

	public SlideItem() {
		this(0);
	}

	public int getLevel() {
		return level;
	}
	
	abstract public void acceptDraw(Visitor visitor, int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o);

	abstract public Rectangle acceptGetBoundingBox(Visitor visitor, Graphics g, ImageObserver observer, float scale, Style myStyle);
}
