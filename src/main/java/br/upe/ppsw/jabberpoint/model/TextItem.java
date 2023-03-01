package br.upe.ppsw.jabberpoint.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.awt.image.ImageObserver;
import java.text.AttributedString;

import br.upe.ppsw.jabberpoint.view.Style;
import br.upe.ppsw.jabberpoint.view.Visitor;

public class TextItem extends SlideItem {

	private String text;

	private static final String EMPTYTEXT = "No Text Given";

	public TextItem(int level, String string) {
		super(level);
		text = string;
	}

	public TextItem() {
		this(0, EMPTYTEXT);
	}

	public String getText() {
		return text == null ? "" : text;
	}

	public AttributedString getAttributedString(Style style, float scale) {
		AttributedString attrStr = new AttributedString(getText());

		attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());

		return attrStr;
	}

	public String toString() {
		return "TextItem[" + getLevel() + "," + getText() + "]";
	}

	@Override
	public void acceptDraw(Visitor visitor, int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o) {
		visitor.drawTextItem(x, y, scale, g, myStyle, o, this);	
	}

	@Override
	public Rectangle acceptGetBoundingBox(Visitor visitor, Graphics g, ImageObserver observer, float scale,
			Style myStyle) {
		return visitor.getBoundingBoxTextItem(g, observer, scale, myStyle, this);
	}

}
