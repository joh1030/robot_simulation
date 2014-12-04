package robotMaze;

import java.awt.Graphics;

public abstract class Cell {
	
	private int row, col;
	
	public Cell(int r, int c) {
		row = r;
		col = c;
	}
	
	public abstract void draw(Graphics g, Maze maze);
	
	public boolean isWall() {
		return false;
	}
	@Override
	public String toString() {
		return "Cell [row=" + row + ", col=" + col + "]";
	}

	public boolean isPassage() {
		return false;
	}
	public boolean isCavern() {
		return false;
	}
	public boolean isStart() {
		return false;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
