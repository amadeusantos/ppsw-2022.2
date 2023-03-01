package br.upe.ppsw.jabberpoint.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.upe.ppsw.jabberpoint.model.BitmapItem;
import br.upe.ppsw.jabberpoint.model.Slide;
import br.upe.ppsw.jabberpoint.model.TextItem;

public class SlideItemViewer implements Visitor {

	public SlideItemViewer() {
	}
	
	@Override
	public void drawTextItem(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o, TextItem textItem) {
		if (textItem.getText() == null || textItem.getText().length() == 0) {
			return;
		}

		List<TextLayout> layouts = getLayouts(g, myStyle, scale, textItem);
		Point pen = new Point(x + (int) (myStyle.getIndent() * scale), y + (int) (myStyle.getLeading() * scale));

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(myStyle.getColor());

		Iterator<TextLayout> it = layouts.iterator();

		while (it.hasNext()) {
			TextLayout layout = it.next();

			pen.y += layout.getAscent();
			layout.draw(g2d, pen.x, pen.y);

			pen.y += layout.getDescent();
		}

	}

	@Override
	public Rectangle getBoundingBoxTextItem(Graphics g, ImageObserver observer, float scale, Style myStyle,
			TextItem textItem) {
		List<TextLayout> layouts = getLayouts(g, myStyle, scale, textItem);

		int xsize = 0, ysize = (int) (myStyle.getLeading() * scale);

		Iterator<TextLayout> iterator = layouts.iterator();

		while (iterator.hasNext()) {
			TextLayout layout = iterator.next();
			Rectangle2D bounds = layout.getBounds();

			if (bounds.getWidth() > xsize) {
				xsize = (int) bounds.getWidth();
			}

			if (bounds.getHeight() > 0) {
				ysize += bounds.getHeight();
			}
			ysize += layout.getLeading() + layout.getDescent();
		}

		return new Rectangle((int) (myStyle.getIndent() * scale), 0, xsize, ysize);
	}

	private List<TextLayout> getLayouts(Graphics g, Style s, float scale, TextItem textItem) {
		List<TextLayout> layouts = new ArrayList<TextLayout>();

		AttributedString attrStr = textItem.getAttributedString(s, scale);
		Graphics2D g2d = (Graphics2D) g;

		FontRenderContext frc = g2d.getFontRenderContext();
		LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);

		float wrappingWidth = (Slide.WIDTH - s.getIndent()) * scale;

		while (measurer.getPosition() < textItem.getText().length()) {
			TextLayout layout = measurer.nextLayout(wrappingWidth);
			layouts.add(layout);
		}

		return layouts;
	}

	@Override
	public void drawBitmapItem(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer,
			BitmapItem bitmapItem) {
		int width = x + (int) (myStyle.getIndent() * scale);
		int height = y + (int) (myStyle.getIndent() * scale);

		g.drawImage(bitmapItem.getBufferdImage(), width, height,
				(int) (bitmapItem.getBufferdImage().getWidth(observer) * scale),
				(int) (bitmapItem.getBufferdImage().getHeight(observer) * scale), observer);
	}

	@Override
	public Rectangle getBoundingBoxbBitmapItem(Graphics g, ImageObserver observer, float scale, Style myStyle,
			BitmapItem bitmapItem) {
		return new Rectangle((int) (myStyle.getIndent() * scale), 0,
				(int) (bitmapItem.getBufferdImage().getWidth(observer) * scale), ((int) (myStyle.getLeading() * scale))
						+ (int) (bitmapItem.getBufferdImage().getHeight(observer) * scale));
	}

}
