package robotMaze;

import java.awt.Color;
import java.awt.Graphics;

public class Passage extends Cell {

	public Passage(int r, int c) {
		super(r, c);
	}

	public boolean isPassage() {
		return true;
	}

	@Override
	public void draw(Graphics g, Maze maze) {
		g.setColor(Color.GRAY);
		g.fillRect(this.getCol()*SystemGUI.LENGTH, this.getRow()*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);

		g.setColor(Color.BLACK);
		g.drawRect(this.getCol()*SystemGUI.LENGTH, this.getRow()*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);
	}
}
