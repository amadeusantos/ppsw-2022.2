package br.upe.ppsw.jabberpoint.model;

import java.util.Vector;

public class Slide {

  public Vector<SlideItem> getItems() {
		return items;
	}

	public void setTitle(TextItem title) {
		this.title = title;
	}

	protected TextItem title;
  protected Vector<SlideItem> items;

  public Slide() {
    items = new Vector<SlideItem>();
  }

  public void append(SlideItem anItem) {
    items.addElement(anItem);
  }

  public String getTitle() {
    return title.getText();
  }

  public void setTitle(String newTitle) {
    title = new TextItem(0, newTitle);
  }

  public void append(int level, String message) {
    append(new TextItem(level, message));
  }

  public SlideItem getSlideItem(int number) {
    return (SlideItem) items.elementAt(number);
  }

  public Vector<SlideItem> getSlideItems() {
    return items;
  }

  public int getSize() {
    return items.size();
  }
  
  public TextItem getTextItemTitle() {
	  return this.title;
  }

}
