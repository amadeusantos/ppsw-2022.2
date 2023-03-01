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
import br.upe.ppsw.jabberpoint.model.SlideItem;
import br.upe.ppsw.jabberpoint.model.TextItem;

public class SlideViewerDraw {
	public final static int WIDTH = 1200;
	public final static int HEIGHT = 800;

	public SlideViewerDraw() {
	}
	
	public void drawSlide(Graphics g, Rectangle area, ImageObserver view, Slide slide) {
		float scale = getScale(area);
		SlideViewerDraw slideItemViewer = new SlideViewerDraw();

		int y = area.y;

		SlideItem slideItem = slide.getTextItemTitle();
		Style style = Style.getStyle(slideItem.getLevel());
		if (slideItem instanceof TextItem) {
			slideItemViewer.drawTextItem(area.x, y, scale, g, style, view, (TextItem) slideItem);
			y += slideItemViewer.getBoundingBoxTextItem(g, view, scale, style, (TextItem) slideItem).height;
		} else {
			slideItemViewer.drawBitmapItem(area.x, y, scale, g, style, view, (BitmapItem) slideItem);
			y += slideItemViewer.getBoundingBoxBitmapItem(g, view, scale, style, (BitmapItem) slideItem).height;
		}

		for (int number = 0; number < slide.getSize(); number++) {
			slideItem = (SlideItem) slide.getSlideItems().elementAt(number);

			style = Style.getStyle(slideItem.getLevel());
			if (slideItem instanceof TextItem) {
				slideItemViewer.drawTextItem(area.x, y, scale, g, style, view, (TextItem) slideItem);
				y += slideItemViewer.getBoundingBoxTextItem(g, view, scale, style, (TextItem) slideItem).height;
			} else {
				slideItemViewer.drawBitmapItem(area.x, y, scale, g, style, view, (BitmapItem) slideItem);
				y += slideItemViewer.getBoundingBoxBitmapItem(g, view, scale, style, (BitmapItem) slideItem).height;
			}
		}
	}

	private float getScale(Rectangle area) {
		return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
	}

	private void drawTextItem(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver o, TextItem textItem) {
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

	private Rectangle getBoundingBoxTextItem(Graphics g, ImageObserver observer, float scale, Style myStyle, TextItem textItem) {
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

	private void drawBitmapItem(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer,
			BitmapItem bitmapItem) {
		int width = x + (int) (myStyle.getIndent() * scale);
		int height = y + (int) (myStyle.getIndent() * scale);

		g.drawImage(bitmapItem.getBufferdImage(), width, height,
				(int) (bitmapItem.getBufferdImage().getWidth(observer) * scale),
				(int) (bitmapItem.getBufferdImage().getHeight(observer) * scale), observer);
	}

	private Rectangle getBoundingBoxBitmapItem(Graphics g, ImageObserver observer, float scale, Style myStyle,
			BitmapItem bitmapItem) {
		return new Rectangle((int) (myStyle.getIndent() * scale), 0,
				(int) (bitmapItem.getBufferdImage().getWidth(observer) * scale), ((int) (myStyle.getLeading() * scale))
						+ (int) (bitmapItem.getBufferdImage().getHeight(observer) * scale));
	}

}
