package br.upe.ppsw.jabberpoint.view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import br.upe.ppsw.jabberpoint.model.BitmapItem;
import br.upe.ppsw.jabberpoint.model.TextItem;

public interface Visitor {

	public void drawTextItem(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o, TextItem textItem);

	public void drawBitmapItem(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer,
			BitmapItem bitmapItem);

	public Rectangle getBoundingBoxTextItem(Graphics g, ImageObserver observer, float scale, Style myStyle,
			TextItem textItem);

	public Rectangle getBoundingBoxbBitmapItem(Graphics g, ImageObserver observer, float scale, Style myStyle,
			BitmapItem bitmapItem);

}
