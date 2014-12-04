package robotMaze;

import java.awt.Color;
import java.awt.Graphics;

public class Cavern extends Cell{
	
	private int cavernID;
	
	public Cavern(int r, int c, int id) {
		super(r, c);
		cavernID = id;
	}
	
	public boolean isCavern() {
		return true;
	}
	
	@Override
	public void draw(Graphics g, Maze maze) {
		g.setColor(Color.YELLOW);
		g.fillRect(this.getCol()*SystemGUI.LENGTH, this.getRow()*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);
		
		g.setColor(Color.BLACK);
		g.drawRect(this.getCol()*SystemGUI.LENGTH, this.getRow()*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);
	}
	
	public int getCavernID() {
		return cavernID;
	}
}
