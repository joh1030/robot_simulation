package robotMaze;

import java.awt.Color;
import java.awt.Graphics;

public class Wall extends Cell {

	public Wall(int r, int c) {
		super(r, c);
	}

	public boolean isWall() {
		return true;
	}

	@Override
	public void draw(Graphics g, Maze maze) {
		g.setColor(Color.GREEN);
		g.fillRect(this.getCol()*SystemGUI.LENGTH, this.getRow()*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);

		g.setColor(Color.BLACK);
		g.drawRect(this.getCol()*SystemGUI.LENGTH, this.getRow()*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);
	}
}
