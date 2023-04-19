package br.upe.ppsw.jabberpoint.model;

public abstract class SlideItem {

	public void setLevel(int level) {
		this.level = level;
	}

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

}
