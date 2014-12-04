package robotMaze;

import java.awt.Color;
import java.awt.Graphics;

public class Start extends Cell {

	public Start(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g, Maze maze) {
		g.setColor(Color.BLACK);
		g.drawRect(this.getCol()*SystemGUI.LENGTH, this.getRow()*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);
	}

	public boolean isStart() {
		return true;
	}
}
