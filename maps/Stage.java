package maps;

public class Stage
{
	public Tile[][] tile;
	public String winner;

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
		if(!(x1 == x2 || y1 == y2))
		{
			System.out.println("Invalid input: makeWall()");
			System.out.println("Only 90 deg lines accepted. Fuck yo slopes");
			System.exit(0);
		}

		// Make dots for-loop-compatible
		int xStart, xEnd;
		if(x1 < x2)
		{
			xStart = x1;
			xEnd = x2;
		}
		else
		{
			xStart = x2;
			xEnd = x1;
		}
		int yStart, yEnd;
		if(y1 < y2)
		{
			yStart = y1;
			yEnd = y2;
		}
		else
		{
			yStart = y2;
			yEnd = y1;
		}

		// Horizontal lines
		if(y1 == y2)
		{
			// j pertains to X-axis in this program
			for(int j = xStart; j < xEnd; j++)
			{
				tile[j][y1].occupied = By.WALL;
			}
		}

		// Vertical lines
		if(x1 == x2)
		{
			// k pertains to Y-axis in this program
			for(int k = yStart; k < yEnd; k++)
			{
				tile[x1][k].occupied = By.WALL;
			}
		}
	}

	public class Tile
	{
		public By occupied;

		// Constructor
		public Tile()
		{
			// Default value is floor
			occupied = By.FLOOR;
		}
	}
}