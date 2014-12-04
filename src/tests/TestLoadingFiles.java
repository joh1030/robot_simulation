package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import robotMaze.Cavern;
import robotMaze.Cell;
import robotMaze.Maze;
import robotMaze.Passage;
import robotMaze.Start;
import robotMaze.SystemGUI;

public class TestLoadingFiles {
	
	private static Maze maze;
	private static final int NUM_ROWS = 25;
	private static final int NUM_COLS = 26;
	
	@Before 
	public void setup() throws FileNotFoundException {
		SystemGUI system = new SystemGUI("maze.csv");
		maze = system.getMaze();
	}
	
	@Test
	public void testMazeDimension() {
		assertEquals(maze.getNumRows(), NUM_ROWS);
		assertEquals(maze.getNumCols(), NUM_COLS);
		int totalCells = maze.getNumRows() * maze.getNumCols();
		assertEquals(totalCells, 650);
	}
	
	@Test
	public void testNumberOfCaverns() {
		int numCaverns = 0;
		for (int i = 0; i < maze.getNumRows(); i++) {
			for (int j = 0; j < maze.getNumCols(); j++) {
				if (maze.getCell(i, j).isCavern()) {
					numCaverns++;
				}
			}
		}
		assertEquals(numCaverns, 8);
	}
	
	@Test
	public void testCavernID() {
		Cell cavernOne = maze.getCell(0, 0);
		assertTrue(cavernOne.isCavern());
		assertEquals(((Cavern)cavernOne).getCavernID(), 1);
		
		Cell cavernTwo = maze.getCell(0, 6);
		assertTrue(cavernTwo.isCavern());
		assertEquals(((Cavern)cavernTwo).getCavernID(), 2);
		
		Cell cavernThree = maze.getCell(3, 21);
		assertTrue(cavernThree.isCavern());
		assertEquals(((Cavern)cavernThree).getCavernID(), 3);
	}
	
	@Test
	public void testStart() {
		Cell start = maze.getCell(24, 25);
		assertTrue(start.isStart());
	}
	
	@Test
	public void testPassage() {
		Cell passageOne = maze.getCell(3, 4);
		assertTrue(passageOne.isPassage());
		
		Cell passageTwo = maze.getCell(5, 12);
		assertTrue(passageTwo.isPassage());
	}
	
	@Test
	public void testWall() {
		Cell  wallOne = maze.getCell(1, 1);
		assertTrue(wallOne.isWall());
		
		Cell wallTwo = maze.getCell(22, 14);
		assertTrue(wallTwo.isWall());
	}
}
