package robotMaze;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.JPanel;

public class Maze extends JPanel {

	private Cell[][] layout;
	private int numRows, numCols;
	private Robot currentRobot;
	private Start start;

	public Maze (String layoutFile) throws FileNotFoundException {
		loadMazeDim(layoutFile);
		layout = new Cell[numRows][numCols];
		loadMazeConfig(layoutFile);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw maze
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				layout[i][j].draw(g, this);
			}
		}
		// draw cavern number
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				if (layout[i][j].isCavern()) {
					g.drawString(Integer.toString(((Cavern)layout[i][j]).getCavernID()), layout[i][j].getCol() * SystemGUI.LENGTH + (SystemGUI.LENGTH/2), layout[i][j].getRow() * SystemGUI.LENGTH + (SystemGUI.LENGTH/2));
				};
			}
		}
		if (currentRobot.getFoundCavern()) {
			// draw route to cavern
			for (Cell c : currentRobot.getRoute()) {
				if (!c.isCavern()) {
					// fill cells
					g.setColor(Color.RED);
					g.fillRect(c.getCol()*SystemGUI.LENGTH, c.getRow()*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);
					// draw cells
					g.setColor(Color.BLACK);
					g.drawRect(c.getCol()*SystemGUI.LENGTH, c.getRow()*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);
				}
			}
		}
		// draw robot
		currentRobot.draw(g, this);
	}

	public void loadMazeDim (String layoutFile) throws FileNotFoundException {
		FileReader reader = new FileReader(layoutFile);
		Scanner scan = new Scanner(reader);
		int rowCount = 0;
		int colCount = 0;
		String temp = scan.nextLine();
		rowCount++;
		String[] tempLine = temp.split(",");
		colCount = tempLine.length;
		while( scan.hasNextLine() ) {
			rowCount++;
			if( scan.hasNextLine() ) {
				scan.nextLine();
			}
		}
		scan.close();
		numRows = rowCount;
		numCols = colCount;
	}

	public void loadMazeConfig (String layoutFile) throws FileNotFoundException {
		FileReader reader = new FileReader(layoutFile);
		Scanner scan = new Scanner(reader);
		int lineCount = 0;
		while (scan.hasNextLine()) {
			String temp = scan.nextLine();
			String[] line = temp.split(",");
			for (int i = 0; i < line.length; i++) {
				if (line[i].length() == 1) {
					if (line[i].equalsIgnoreCase("p")) {
						layout[lineCount][i] = new Passage(lineCount, i);
					}
					else if (line[i].equalsIgnoreCase("w")) {
						layout[lineCount][i] = new Wall(lineCount, i);
					}
					else if (line[i].equalsIgnoreCase("s")) {
						layout[lineCount][i] = new Start(lineCount, i);
						start = new Start(lineCount, i); // set start
					}
				}
				else {
					char tempID = line[i].charAt(1);
					int id = Character.getNumericValue(tempID);
					layout[lineCount][i] = new Cavern(lineCount, i, id);
				}
			}
			lineCount++;
		}
		scan.close();
	}

	public Cell getCell(int row, int col) {
		return layout[row][col];
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public void setCurrentRobot (Robot currentRobot) {
		this.currentRobot = currentRobot;
	}

	public Start getStart() {
		return start;
	}
}
