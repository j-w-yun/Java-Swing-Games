package maps;

import frames.panels.DisplayPanel;

public class Stage
{
	public Tile[][] tile;

	// Wall slope at (x, y)
	private double[][] wallSlope;

	// Copy access
	public Tile[][] getTileCopy()
	{
		Tile[][] toReturn = new Tile[tile.length][tile[0].length];
		for(int j = 0; j < tile.length; j++)
		{
			for(int k = 0; k < tile[0].length; k++)
			{
				toReturn[j][k] = new Tile();
			}
		}
		return toReturn;
	}

	// Constructor
	public Stage(int x, int y)
	{
		// Make tiles
		tile = new Tile[x][y];

		// Make slopes
		wallSlope = new double[x][y];

		for(int j = 0; j < x; j++)
		{
			for(int k = 0; k < y; k++)
			{
				// Set all tiles to floor
				tile[j][k] = new Tile();
			}
		}
	}

	// Make wall with the line connecting two points
	public void makeWall(int x1, int y1, int x2, int y2)
	{
		// Calculate slope
		int rise = y2 - y1;
		int run = x2 - x1;
		double slope = (double) rise / run;

		// Create wall
		int xStart = 0, xEnd = 0;
		int yStart = 0, yEnd = 0;
		if(x1 > x2 && y1 > y2)
		{
			xStart = x2;
			xEnd = x1;
			yStart = y2;
			yEnd = y1;
			slope = (double) rise / run;
		}
		else if(x1 < x2)			// For loop needs to increment
		{
			xStart = x1;
			xEnd = x2;
		}
		else if(x1 > x2)
		{
			xStart = x2;
			xEnd = x1;
			slope = -1 * (double) rise / run;
		}
		else if(y1 < y2)			// For loop needs to increment
		{
			yStart = y1;
			yEnd = y2;
		}
		else if(y1 > y2)
		{
			yStart = y2;
			yEnd = y1;
			slope = -1 * (double) rise / run;
		}
		int counter = 0;		// Begin counter
		for(int j = xStart; j < xEnd; j++)			// j is X-axis and k is Y-axis
		{
			// Store walls in the line connecting two dots
			tile[j][yStart + (int) (counter * slope)].occupied = By.WALL;

			// Store slope in the same dots
			wallSlope[j][yStart + (int) (counter * slope)] = slope;

			counter++;
		}
		counter = 0;		// Reset and begin counter

		// To fill in gaps left by lines with big slopes
		for(int k = yStart; k < yEnd; k++)
		{
			// Store walls in the line connecting two dots
			tile[(int) ((1 / slope) * counter) + xStart][k].occupied = By.WALL;

			// Store slope in the same dots
			wallSlope[(int) ((1 / slope) * counter) + xStart][k] = slope;

			counter++;
		}

		// Vertical line
		if(x1 == x2)
		{
			for(int k = yStart; k < yEnd; k++)
			{
				tile[x1][k].occupied = By.WALL;
				for(int j = xStart; j < xEnd; j++)
				{
					// Slopes of vertical lines
					wallSlope[j][k] = 999;
				}
			}
		}
	}

	public class Tile
	{
		public By occupied;

		// Constructor
		public Tile()
		{
			occupied = By.FLOOR;
		}
	}
}