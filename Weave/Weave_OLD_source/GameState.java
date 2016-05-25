// a static import of the constants that make Color and direction info (encoded in integer) easier to comprehend
import static util.Constants.*;

import java.util.*;

class GameState
{
	// I made an interface to call draw() on whatever display it will be fed
	// this will make it easier to integrate into android
	// for android, just implement Drawable on the display and call draw()
	private Drawable display;

	private final int WIDTH;
	private final int HEIGHT;
	private int xStart;
	private int yStart;
	private boolean randomPos;

	private int numGridsOnWidth;
	private int numGridsOnHeight;
	private int pixelsPerGrid;
	private int[][] gameGrid;
	JayList<Position> playerState;

	private final int MAX_DIFFICULTY = 4;
	private final int MIN_NUM_GRIDS = 4;
	private int difficulty;

	private final int WALLS_PER_DIFFICULTY = 2;
	private final int MIN_WALLS = 3;
	private int numWalls;

	private final int MOVEABLE_WALLS_PER_DIFFICULTY = 0;
	private final int MIN_MOVEABLE_WALLS = 0;
	private int numMoveableWalls;
	JayList<Position> movableWallState;

	private MapGenerator mapGenerator;
	private boolean solvable;

	GameState(int width, int height, Drawable display, int xStart, int yStart, boolean randomPos)
	{
		this.WIDTH = width;
		this.HEIGHT = height;
		this.display = display;
		this.xStart = xStart; // this does not apply for true randomPos
		this.yStart = yStart; // this does not apply for true randomPos
		this.randomPos = randomPos; // use this later

		difficulty = 2; // 3

		initialize();
		generateMap();
	}

	// // copy constructor
	// GameState(GameState gs)
	// {
	// 	this.display = gs.display;

	// 	this.WIDTH = gs.WIDTH;
	// 	this.HEIGHT = gs.HEIGHT;
	// 	this.xStart = gs.xStart;
	// 	this.yStart = gs.yStart;
	// 	this.numGridsOnWidth = gs.numGridsOnWidth;
	// 	this.pixelsPerGrid = gs.pixelsPerGrid;
	// 	this.difficulty = gs.difficulty;
	// 	this.mapGenerator = gs.mapGenerator;

	// 	this.gameGrid = new int[gs.gameGrid.length][];
	// 	for(int j = 0; j < gs.gameGrid.length; j++)
	// 	{
	// 		this.gameGrid[j] = new int[gs.gameGrid[j].length];
	// 	}
	// 	for(int j = 0; j < gs.gameGrid.length; j++)
	// 	{
	// 		for(int k = 0; k < gs.gameGrid[j].length; k++)
	// 		{
	// 			this.gameGrid[j][k] = gs.gameGrid[j][k];
	// 		}
	// 	}

	// 	this.playerState = new JayList<Position>();
	// 	this.playerState.setArray(gs.playerState.toArray());
	// }

	private void initialize()
	{
		playerState = new JayList<Position>();
		movableWallState = new JayList<Position>();
		initializeGrid();
	}

	private void initializeGrid()
	{
		pixelsPerGrid = WIDTH / (difficulty + MIN_NUM_GRIDS); // the size of grid should be a perfect fit for the horizontal screen width
		numGridsOnWidth = WIDTH / pixelsPerGrid; // get number of grids horizontally
		numGridsOnHeight = HEIGHT / pixelsPerGrid; // get number of grids vertically
		gameGrid = new int[numGridsOnWidth][numGridsOnHeight];

		if(randomPos)
		{
			Random rand = new Random();
			xStart = rand.nextInt(numGridsOnWidth);
			yStart = rand.nextInt(numGridsOnHeight);
		}

		gameGrid[xStart][yStart] = PLAYER;
		playerState.addLast(new Position(xStart, yStart));
	}

	// does not reset difficulty
	// initialize() clears gameGrid and playerState to start fresh
	// need to set initial player position before starting game
	void clear()
	{
		initialize();
	}

	void increaseDifficulty()
	{
		if(difficulty <= MAX_DIFFICULTY)
			difficulty++;
	}

	void setDifficulty(int difficulty)
	{
		if(difficulty <= MAX_DIFFICULTY)
			this.difficulty = difficulty;
	}

	// private void generateMap()
	void generateMap()
	{
		solvable = false;
		if(mapGenerator == null)
		{
			mapGenerator = new MapGenerator();
		}

		new Thread(new Runnable()
		{
			public void run()
			{
				while(!solvable)
				{
					initialize();
					mapGenerator.generate();
					display.draw();
				}
			}
		}).start();
	}

	// INCOMPLETE
	private class MapGenerator
	{
		public void generate()
		{
			GameState.this.initializeGrid();

			generateWalls(difficulty * WALLS_PER_DIFFICULTY + MIN_WALLS);
			generateMoveableWalls(difficulty * MOVEABLE_WALLS_PER_DIFFICULTY + MIN_MOVEABLE_WALLS);

			if(backtrack(GameState.this, NORTH))
				GameState.this.clearPlayer();
			if(backtrack(GameState.this, EAST))
				GameState.this.clearPlayer();
			if(backtrack(GameState.this, SOUTH))
				GameState.this.clearPlayer();
			if(backtrack(GameState.this, WEST))
				GameState.this.clearPlayer();

			System.out.println("DONE");
		}

		// randomly generate
		private void generateWalls(int numWalls)
		{
			GameState.this.numWalls = numWalls;
			Random rand = new Random();
			for(int j = 0; j < numWalls; j++)
			{
				int x = rand.nextInt(numGridsOnWidth);
				int y = rand.nextInt(numGridsOnHeight);

				if(gameGrid[x][y] == 0)
				{
					GameState.this.gameGrid[x][y] = WALL;
				}
				else
				{
					j--;
					continue;
				}
			}
		}

		private void generateMoveableWalls(int numMoveableWalls)
		{
			GameState.this.numMoveableWalls = numMoveableWalls;
			Random rand = new Random();
			for(int j = 0; j < numMoveableWalls; j++)
			{
				int x = rand.nextInt(numGridsOnWidth);
				int y = rand.nextInt(numGridsOnHeight);

				if(gameGrid[x][y] == 0)
				{
					GameState.this.gameGrid[x][y] = MOVEABLE_WALL;
				}
				else
				{
					j--;
					continue;
				}
			}
		}

		private boolean backtrack(GameState partialSoln, int direction)
		{
			if(GameState.this.isComplete())
			{
				System.out.println("SOLN FOUND");
				GameState.this.solvable = true;
				return true;
			}

			if(reject(partialSoln))
				return false;

			// try{
			// 	Thread.sleep(100);
			// } catch(Exception e) {}

			if(canExtend(partialSoln, direction))
				partialSoln.advance(direction);
			else
				return false;

			// backtrack in each direction
			if(backtrack(partialSoln, NORTH))
				return true;
			if(backtrack(partialSoln, EAST))
				return true;
			if(backtrack(partialSoln, SOUTH))
				return true;
			if(backtrack(partialSoln, WEST))
				return true;

			partialSoln.back();

			return false;
		}

		// need to improve
		private boolean reject(GameState partialSoln)
		{
			int holes = 0;
			for(int j = 0; j < partialSoln.gameGrid.length; j++)
			{
				for(int k = 0; k < partialSoln.gameGrid[j].length; k++)
				{
					if(partialSoln.gameGrid[j][k] == 0)
					{
						int filledSide = 0;

						// try for each side
						try
						{
							if(partialSoln.gameGrid[j + 1][k] != 0)
								filledSide++;
						} catch(Exception e) {}
						try
						{
							if(partialSoln.gameGrid[j - 1][k] != 0)
								filledSide++;
						} catch(Exception e) {}
						try
						{
							if(partialSoln.gameGrid[j][k + 1] != 0)
								filledSide++;
						} catch(Exception e) {}
						try
						{
							if(partialSoln.gameGrid[j][k - 1] != 0)
								filledSide++;
						} catch(Exception e) {}

						if(filledSide == 3)
						{
							holes++;
						}
					}
				}
			}
			if(holes > 2)
				return true;
			return false;

			// int restrictedMovement = 0;
			// for(int j = 0; j < 4; j++)
			// {
			// 	try
			// 	{
			// 		partialSoln.advance(j);
			// 		partialSoln.back();
			// 	}
			// 	catch(Exception e)
			// 	{
			// 		restrictedMovement++;
			// 	}
			// }
			// if(restrictedMovement == 4)
			// 	return true;
			// return false;
		}

		// // checks whether the input is a full solution
		// public boolean isSolution(GameState partialSoln)
		// {
		// 	for(int j = 0; j < GameState.this.gameGrid.length; j++)
		// 	{
		// 		for(int k = 0; k < GameState.this.gameGrid[j].length; k++)
		// 		{
		// 			if(partialSoln.getGrid()[j][k] == 0)
		// 			{
		// 				return false;
		// 			}
		// 		}
		// 	}
		// 	return true;
		// }

		// check whether to return false during recursive backtracking
		public boolean canExtend(GameState partialSoln, int dir)
		{
			try {
				partialSoln.advance(dir);
				partialSoln.back();
				return true;
			} catch(Exception e) {return false;}
		}

		// advance to a direction
		public void next(GameState partialSoln, int dir)
		{
			try {
				partialSoln.advance(dir);
			} catch(Exception e) {}
		}
	}

	// advances to a position
	// north is 0, east is 1, south is 2, west is 3
	void advance(int direction)
	{
		Position currentPosition = playerState.getLast();
		int x = currentPosition.x;
		int y = currentPosition.y;

		if(direction == NORTH)
			y--;
		else if(direction == EAST)
			x++;
		else if(direction == SOUTH)
			y++;
		else if(direction == WEST)
			x--;

		if(x >= 0 && y >= 0 && x <= numGridsOnWidth && y <= numGridsOnHeight)
		{
			// advance on blank
			if(gameGrid[x][y] == BLANK)
			{
				// only when these conditions are met, the player will advance to the desired direction
				gameGrid[x][y] = PLAYER;
				playerState.addLast(new Position(x, y));
			}
			else if(gameGrid[x][y] == MOVEABLE_WALL)
			{
				try
				{
					int x1 = x;
					int y1 = y;

					// if there is something behind the moveable wall other than a blank throw an exception
					// TODO

					if(direction == NORTH)
						gameGrid[x1][--y1] = MOVEABLE_WALL;
					else if(direction == EAST)
						gameGrid[++x1][y] = MOVEABLE_WALL;
					else if(direction == SOUTH)
						gameGrid[x][++y1] = MOVEABLE_WALL;
					else if(direction == WEST)
						gameGrid[--x1][y] = MOVEABLE_WALL;

					gameGrid[x][y] = PLAYER;
					playerState.addLast(new Position(x, y));
					movableWallState.addLast(new Position(x1, y1));
				}
				catch(Exception e)
				{
					throw new IllegalArgumentException();
				}
			}
			else // if player wants to advance to a position that is not blank
			{
				// throwing this will help deal with recursive backtracking
				throw new IllegalArgumentException();
			}
		}
		else
		{
			// throw this instead of array index out of bounds exception
			throw new IllegalArgumentException();
		}

		display.draw();
	}

	void back()
	{
		if(playerState.length() > 2)
		{
			Position r = playerState.removeLast();
			gameGrid[r.x][r.y] = BLANK;
			display.draw();

			if(!movableWallState.isEmpty())
			{
				Position mw = movableWallState.getLast();
				if(r.x == mw.x && r.y == mw.y)
				{
					Position mwr = movableWallState.removeLast();
					gameGrid[mwr.x][mwr.y] = MOVEABLE_WALL;
					display.draw();
				}
			}
		}
	}

	void clearPlayer()
	{
		while(playerState.length() > 2)
		{
			back();
		}
	}

	public boolean isComplete()
	{
		for(int j = 0; j < gameGrid.length; j++)
		{
			for(int k = 0; k < gameGrid[j].length; k++)
			{
				if(gameGrid[j][k] == 0)
				{
					return false;
				}
			}
		}
		return true;
	}

	// sends info to be displayed
	// blank is 0, player is 1, wall is 2
	int[][] getGrid()
	{
		return gameGrid;
	}

	int pixelsPerGrid()
	{
		return pixelsPerGrid;
	}

	int numGridsOnWidth()
	{
		return numGridsOnWidth;
	}

	int numGridsOnHeight()
	{
		return numGridsOnHeight;
	}

	// for convinience use with JayList
	// best solution I could come up with to store both x and y ints in the deque
	private class Position
	{
		int x;
		int y;
		Position(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
}