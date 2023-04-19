package br.upe.ppsw.jabberpoint.model;

import java.awt.font.TextAttribute;
import java.text.AttributedString;

import br.upe.ppsw.jabberpoint.view.Style;

public class TextItem extends SlideItem {

	public void setText(String text) {
		this.text = text;
	}

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

}
