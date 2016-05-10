package players;

import maps.Stage;
import maps.By;

public class Player
{
	// To be used to set tiles which the player is currently on
	private Stage stage;

	// Original position of player (stale attributes for now)
	private int xStart, yStart;

	// Current position of player
	private int xPos, yPos;
	private int xPosBefore, yPosBefore;

	// Store player size property
	private int hitboxLength;
	private int radius;

	// Store speed (pixels per keypress)
	private int speed;

	// Store which way player is facing
	private Move facing;

	// For holding down directional buttons
	public boolean releasedR = true;
	public boolean releasedL = true;
	public boolean releasedU = true;
	public boolean releasedD = true;

	// Player number
	private By player;

	// Starting position (x, y)
	public Player(int xStart, int yStart, int speed, int hitboxLength, Stage stage, By player)
	{
		this.xStart = xStart;
		this.yStart = yStart;

		this.hitboxLength = hitboxLength;
		this.speed = speed;

		this.stage = stage;

		this.player = player;

		xPos = xStart;
		yPos = yStart;
		xPosBefore = 10;
		yPosBefore = 10;

		generateHitbox();
	}

	// Player size determined here
	public void generateHitbox()
	{
		radius = hitboxLength / 2;

		// Erase previous box
		for(int j = xPosBefore - radius; j < xPosBefore + radius; j++)
		{
			fill(j, yPosBefore + radius, By.FLOOR);
			fill(j, yPosBefore + (radius + 1), By.FLOOR);
			fill(j, yPosBefore - radius, By.FLOOR);
			fill(j, yPosBefore - (radius + 1), By.FLOOR);
		}
		for(int k = yPosBefore - radius; k < yPosBefore + radius; k++)
		{
			fill(xPosBefore + radius, k, By.FLOOR);
			fill(xPosBefore + (radius + 1), k, By.FLOOR);
			fill(xPosBefore - radius, k, By.FLOOR);
			fill(xPosBefore - (radius + 1), k, By.FLOOR);
		}

		// Make new box
		for(int j = xPos - radius; j < xPos + radius; j++)
		{
			fill(j, yPos + radius, player);
			fill(j, yPos + (radius + 1), player);
			fill(j, yPos - radius, player);
			fill(j, yPos - (radius + 1), player);
		}
		for(int k = yPos - radius; k < yPos + radius; k++)
		{
			fill(xPos - radius, k, player);
			fill(xPos - (radius + 1), k, player);
			fill(xPos + radius, k, player);
			fill(xPos + (radius + 1), k, player);
		}

		if(facing == Move.RIGHT)
		{
			for(int k = yPos - radius; k < yPos + radius; k++)
			{
				if(stage.tile[xPos - radius][k].occupied != By.WALL)
				{
					fill(xPos + radius, k, By.FLOOR);
					fill(xPos + (radius + 1), k, By.FLOOR);
				}
			}
		}
		else if(facing == Move.LEFT)
		{
			for(int k = yPos - radius; k < yPos + radius; k++)
			{
				if(stage.tile[xPos - radius][k].occupied != By.WALL)
				{
					fill(xPos - radius, k, By.FLOOR);
					fill(xPos - (radius + 1), k, By.FLOOR);
				}
			}
		}
		else if(facing == Move.UP)
		{
			for(int j = xPos - radius; j < xPos + radius; j++)
			{
				if(stage.tile[j][yPos - radius].occupied != By.WALL)
				{
					fill(j, yPos - radius, By.FLOOR);
					fill(j, yPos - (radius + 1), By.FLOOR);
				}
			}
		}
		else if(facing == Move.DOWN)
		{
			for(int j = xPos - radius; j < xPos + radius; j++)
			{
				if(stage.tile[j][yPos + radius].occupied != By.WALL)
				{
					fill(j, yPos + radius, By.FLOOR);	
					fill(j, yPos + (radius + 1), By.FLOOR);				
				}
			}
		}


		xPosBefore = xPos;
		yPosBefore = yPos;
	}
	public void fill(int x, int y, By what)
	{
		if(stage.tile[x][y].occupied != By.WALL)
		{
			stage.tile[x][y].occupied = what;
		}
	}

	public int[] getPos()
	{
		int[] toReturn = {xPos, yPos};
		return toReturn;
	}

	public Move getFace()
	{
		return facing;
	}

	public void move(Move move)
	{
		if(move == Move.RIGHT)
		{
			for(int j = xPos + radius - 5; j < xPos + radius + speed + 5; j++)
			{
				if(stage.tile[j][yPos].occupied == By.WALL)
				{
					return;
				}
			}	
			xPos += speed;			// Move player

			facing = Move.RIGHT;	// Store face

			generateHitbox();		// Fill in tiles
		}
		if(move == Move.LEFT)
		{
			for(int j = xPos - radius - speed - 5; j < xPos - radius + 5; j++)
			{
				if(stage.tile[j][yPos].occupied == By.WALL)
				{
					return;
				}
			}
			xPos -= speed;

			facing = Move.LEFT;
			
			generateHitbox();
		}
		if(move == Move.UP)
		{
			for(int k = yPos - radius - speed - 5; k < yPos - radius + 5; k++)
			{
				if(stage.tile[xPos][k].occupied == By.WALL)
				{
					return;
				}
			}
			yPos -= speed;
			
			facing = Move.UP;

			generateHitbox();
		}
		if(move == Move.DOWN)
		{
			for(int k = yPos + radius - 5; k < yPos + radius + speed + 5; k++)
			{
				if(stage.tile[xPos][k].occupied == By.WALL)
				{
					return;
				}
			}
			yPos += speed;
			
			facing = Move.DOWN;

			generateHitbox();
		}
	}
}