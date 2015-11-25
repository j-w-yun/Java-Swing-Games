package players;

import maps.Stage;
import maps.By;

public class Player
{
	private int xStart, yStart;

	private int xPos, yPos;
	private int xPosBefore, yPosBefore;

	private Stage stage;

	private int hitboxLength;
	private int radius;

	private int speed;

	// Starting position (x, y)
	public Player(int xStart, int yStart, int speed, int hitboxLength, Stage stage)
	{
		this.xStart = xStart;
		this.yStart = yStart;

		this.hitboxLength = hitboxLength;
		this.speed = speed;

		this.stage = stage;

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
			fill(j, yPosBefore - radius, By.FLOOR);
		}
		for(int k = yPosBefore - radius; k < yPosBefore + radius; k++)
		{
			fill(xPosBefore + radius, k, By.FLOOR);
			fill(xPosBefore - radius, k, By.FLOOR);
		}

		// Make new box
		for(int j = xPos - radius; j < xPos + radius; j++)
		{
			fill(j, yPos + radius, By.PLAYER);
			fill(j, yPos - radius, By.PLAYER);
		}
		for(int k = yPos - radius; k < yPos + radius; k++)
		{
			fill(xPos + radius, k, By.PLAYER);
			fill(xPos - radius, k, By.PLAYER);
		}

		xPosBefore = xPos;
		yPosBefore = yPos;
	}
	public void fill(int x, int y, By what)
	{
		stage.tile[x][y].occupied = what;
	}

	public void move(Move move)
	{
		if(move == Move.RIGHT)
		{
			for(int j = xPos + radius; j < xPos + radius + speed; j++)
			{
				if(stage.tile[j][yPos].occupied == By.WALL)
				{
					return;
				}
			}	
			xPos += speed;				// Move player

			generateHitbox();		// Fill in tiles
		}
		else if(move == Move.LEFT)
		{
			for(int j = xPos - radius - speed; j < xPos - radius; j++)
			{
				if(stage.tile[j][yPos].occupied == By.WALL)
				{
					return;
				}
			}
			xPos -= speed;
			
			generateHitbox();
		}
		else if(move == Move.UP)
		{
			for(int k = yPos - radius - speed; k < yPos - radius; k++)
			{
				if(stage.tile[xPos][k].occupied == By.WALL)
				{
					return;
				}
			}
			yPos -= speed;
			
			generateHitbox();
		}
		else if(move == Move.DOWN)
		{
			for(int k = yPos + radius; k < yPos + radius + speed; k++)
			{
				if(stage.tile[xPos][k].occupied == By.WALL)
				{
					return;
				}
			}
			yPos += speed;
			
			generateHitbox();
		}
	}
}