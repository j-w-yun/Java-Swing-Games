package players;

import maps.Stage;
import maps.By;

public class Player
{
	private int xStart, yStart;

	private int xPos, yPos;
	private int xPosBAK, yPosBAK;
	private int xPosUndo, yPosUndo;

	private Stage stage;

	// Starting position (x, y)
	public Player(int xStart, int yStart, Stage stage)
	{
		this.xStart = xStart;
		this.yStart = yStart;

		xPos = xStart;
		yPos = yStart;
		xPosBAK = xPos;
		yPosBAK = yPos;

		xPosUndo = 3;
		yPosUndo = 3;

		this.stage = stage;

		bringToStage();
	}

	// Player size and shape determined here
	public void bringToStage()
	{
		// Fill in new position
		//fill(xPos, yPos, By.PLAYER);		// Center
		fill(xPos, yPos - 3, By.PLAYER);
		fill(xPos, yPos - 2, By.PLAYER);
		fill(xPos, yPos - 1, By.PLAYER);
		fill(xPos, yPos + 3, By.PLAYER);
		fill(xPos, yPos + 2, By.PLAYER);
		fill(xPos, yPos + 1, By.PLAYER);
		fill(xPos - 3, yPos, By.PLAYER);
		fill(xPos - 2, yPos, By.PLAYER);
		fill(xPos - 1, yPos, By.PLAYER);
		fill(xPos + 3, yPos, By.PLAYER);
		fill(xPos + 2, yPos, By.PLAYER);
		fill(xPos + 1, yPos, By.PLAYER);

		// Delete previous position of player
		//fill(xPosUndo, yPosUndo, By.FLOOR);		// Center
		fill(xPosUndo, yPosUndo - 3, By.FLOOR);
		fill(xPosUndo, yPosUndo - 2, By.FLOOR);
		fill(xPosUndo, yPosUndo - 1, By.FLOOR);
		fill(xPosUndo, yPosUndo + 3, By.FLOOR);
		fill(xPosUndo, yPosUndo + 2, By.FLOOR);
		fill(xPosUndo, yPosUndo + 1, By.FLOOR);
		fill(xPosUndo - 3, yPosUndo, By.FLOOR);
		fill(xPosUndo - 2, yPosUndo, By.FLOOR);
		fill(xPosUndo - 1, yPosUndo, By.FLOOR);
		fill(xPosUndo + 3, yPosUndo, By.FLOOR);
		fill(xPosUndo + 2, yPosUndo, By.FLOOR);
		fill(xPosUndo + 1, yPosUndo, By.FLOOR);

		xPosUndo = xPos;
		yPosUndo = yPos;
	}
	public void fill(int x, int y, By what)
	{
		// If wall exists, don't move player
		if(stage.tile[x][y].occupied == By.WALL)
		{
			xPos = xPosBAK;
			yPos = yPosBAK;
		}
		else
		{
			stage.tile[x][y].occupied = what;
		}
	}

	public int[] getPos()
	{
		int[] toReturn = {xPos, yPos};
		return toReturn;
	}

	public void move(Move move)
	{
		if(move == Move.RIGHT)
		{
			xPosBAK = xPos;		// Make backup in case player hits wall

			xPos += 3;			// Move player

			bringToStage();		// Fill in tiles
		}
		else if(move == Move.LEFT)
		{
			xPosBAK = xPos;

			xPos -= 3;
			
			bringToStage();
		}
		else if(move == Move.UP)
		{
			yPosBAK = yPos;
			
			yPos -= 3;
			
			bringToStage();
		}
		else if(move == Move.DOWN)
		{
			yPosBAK = yPos;
			
			yPos += 3;
			
			bringToStage();
		}
	}
}