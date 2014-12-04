package robotMaze;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.*;

public class Robot {

	private int id, row, col;
	private Color color;
	private LinkedList<Cell> visited = new LinkedList<Cell>();
	private LinkedList<Cell> route = new LinkedList<Cell>();
	private Cavern targetCavern;
	private boolean foundCavern, foundOne, foundTwo, foundThree, foundFour, foundFive, foundSix, foundSeven, foundEight = false; // by default

	public Robot(int id, Color color, int row, int col) {
		super();
		this.id = id;
		this.color = color;
		this.row = row;
		this.col = col;
	}
	
	public void explore (int id, Maze maze) {
		visited.add(maze.getCell(row, col));
		//Loop until we find the cavern 
		outerloop: //Label the outerloop for breaking purposes
		while(!foundCavern){
			//Move to a Cell
			Cell validCell = chooseValidCell(maze);
			//If validCell is null then we have to backtrack till it's not
			if(validCell == null){
				while(chooseValidCell(maze) == null){
					//Go back in route till we find a valid cell
					Cell lastCell = route.pollLast();
					if(lastCell == null){ //Implies we didn't find cavern, leave route empty
						break outerloop;
					}
					this.row = lastCell.getRow();
					this.col = lastCell.getCol();
				}
				//Add back the current location to the route
				route.add(maze.getCell(row, col));
				validCell = chooseValidCell(maze);
			}
			this.row = validCell.getRow();
			this.col = validCell.getCol();
			//Add to the route 
			route.add(validCell);
			//Add to visited
			visited.add(validCell);
			//Check if we're at the cavern
			if(row == targetCavern.getRow() && col == targetCavern.getCol()){
				foundCavern = true;
			}
		}
		// check which cavern was found (or not found)
		switch(id) {
		case 1: 
			foundOne = foundCavern;
			break;
		case 2: 
			foundTwo = foundCavern;
			break;
		case 3: 
			foundThree = foundCavern;
			break;
		case 4: 
			foundFour = foundCavern;
			break;
		case 5: 
			foundFive = foundCavern;
			break;
		case 6: 
			foundSix = foundCavern;
			break;
		case 7: 
			foundSeven = foundCavern;
			break;
		case 8: 
			foundEight = foundCavern;
			break;
		default:
			break;
		}
	}
	public Cell chooseValidCell(Maze maze){
		LinkedList<Cell> validCells = new LinkedList<Cell>();
		// check up
		if((row-1) >= 0 && !visited.contains(maze.getCell(row-1,col)) && !maze.getCell(row-1,col).isWall())
			validCells.add(maze.getCell(row-1,col));
		// check down
		if((row+1) < maze.getNumRows() && !visited.contains(maze.getCell(row+1, col))&& !maze.getCell(row+1, col).isWall())
			validCells.add(maze.getCell(row+1, col));
		// check left
		if((col-1) >= 0 && !visited.contains(maze.getCell(row,col-1)) && !maze.getCell(row,col-1).isWall())
			validCells.add(maze.getCell(row,col-1));
		// check right
		if((col+1) < maze.getNumCols() && !visited.contains(maze.getCell(row,col+1)) && !maze.getCell(row,col+1).isWall())
			validCells.add(maze.getCell(row,col+1));
		Random rand = new Random();
		if(validCells.isEmpty())
			return null;
		else{
			return validCells.get(rand.nextInt(validCells.size())); // choose random direction
		}
	}
	
	public boolean getFoundCavern() {
		return foundCavern;
	}

	public void setFoundCavern(boolean found) {
		foundCavern = found;
	}

	public int getID() {
		return id;
	}

	public void draw (Graphics g, Maze maze) {
		// draw outline
		g.setColor(Color.BLACK);
		g.drawOval(col*SystemGUI.LENGTH, row*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);
		// draw robot
		g.setColor(color);
		g.fillOval(col*SystemGUI.LENGTH, row*SystemGUI.LENGTH, SystemGUI.LENGTH, SystemGUI.LENGTH);
	}

	public void setRobotLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public void setTargetCavern(int cavernID, Maze maze) {
		for (int i = 0; i < maze.getNumRows(); i++) {
			for (int j = 0; j < maze.getNumCols(); j++) {
				if (maze.getCell(i,j).isCavern()) {
					if (((Cavern) maze.getCell(i,j)).getCavernID() == cavernID) {
						targetCavern = (Cavern) maze.getCell(i,j);
					}
				}
			}
		}
	}

	public Cavern getTargetCavern() {
		return targetCavern;
	}

	public LinkedList<Cell> getVisited() {
		return visited;
	}

	public LinkedList<Cell> getRoute() {
		return route;
	}

	public boolean getFoundOne() {
		return foundOne;
	}
	public boolean getFoundTwo() {
		return foundTwo;
	}
	public boolean getFoundThree() {
		return foundThree;
	}
	public boolean getFoundFour() {
		return foundFour;
	}
	public boolean getFoundFive() {
		return foundFive;
	}
	public boolean getFoundSix() {
		return foundSix;
	}
	public boolean getFoundSeven() {
		return foundSeven;
	}
	public boolean getFoundEight() {
		return foundEight;
	}
	public void setFoundOne(boolean foundOne) {
		this.foundOne = foundOne;
	}
	public void setFoundTwo(boolean foundTwo) {
		this.foundTwo = foundTwo;
	}
	public void setFoundThree(boolean foundThree) {
		this.foundThree = foundThree;
	}
	public void setFoundFour(boolean foundFour) {
		this.foundFour = foundFour;
	}
	public void setFoundFive(boolean foundFive) {
		this.foundFive = foundFive;
	}
	public void setFoundSix(boolean foundSix) {
		this.foundSix = foundSix;
	}
	public void setFoundSeven(boolean foundSeven) {
		this.foundSeven = foundSeven;
	}
	public void setFoundEight(boolean foundEight) {
		this.foundEight = foundEight;
	}
}
