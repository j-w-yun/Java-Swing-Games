package projectiles;

import maps.Stage;
import maps.By;
import players.Move;

public class Projectile
{
	// To be used to set tiles which the projectile is currently on
	private Stage stage;

	// Current position of projectile
	public int xPos, yPos;
	private int xPosBefore, yPosBefore;

	// Store projectile size property
	private int hitboxLength;
	private int radius;

	// Store speed (pixels per iteration)
	private int speed;

	// Store which way projectile is facing
	private Move facing;

	public Projectile(int xStart, int yStart, Move facing, int speed, int hitboxLength, Stage stage)
	{
		this.stage = stage;

		this.facing = facing;

		if(this.facing == Move.LEFT)
		{
			xPos = xStart - 1;
			yPos = yStart;
		}
		else if(this.facing == Move.RIGHT)
		{
			xPos = xStart + 1;
			yPos = yStart;
		}
		else if(this.facing == Move.UP)
		{
			xPos = xStart;
			yPos = yStart - 1;
		}
		else if(this.facing == Move.DOWN)
		{
			xPos = xStart;
			yPos = yStart + 1;
		}

		this.speed = speed;

		this.hitboxLength = hitboxLength;

		xPos = xStart;
		yPos = yStart;
		xPosBefore = xPos;
		yPosBefore = yPos;

		System.out.println(xPos + " " + yPos);

		generateHitbox();
	}
	public void generateHitbox()
	{
		radius = hitboxLength / 2;
		stage.tile[xPos][yPos].occupied = By.PROJECTILE;

		// Erase previous box
		for(int j = xPosBefore - radius; j < xPosBefore + radius; j++)
		{
			System.out.println(j + " " + (yPosBefore + radius));
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
			fill(j, yPos + radius, By.PROJECTILE);
			fill(j, yPos - radius, By.PROJECTILE);
		}
		for(int k = yPos - radius; k < yPos + radius; k++)
		{
			fill(xPos + radius, k, By.PROJECTILE);
			fill(xPos - radius, k, By.PROJECTILE);
		}

		xPosBefore = xPos;
		yPosBefore = yPos;
	}

	public void fill(int x, int y, By what)
	{
		stage.tile[x][y].occupied = what;
	}

	public void move()
	{
		if(facing == Move.RIGHT)
		{
			for(int j = xPos + radius; j < xPos + radius + speed; j++)
			{
				if(stage.tile[j][yPos].occupied == By.WALL)
				{
					facing = Move.LEFT;
					return;
				}
			}	
			xPos += speed;			// Move player

			generateHitbox();		// Fill in tiles
		}
		else if(facing == Move.LEFT)
		{
			for(int j = xPos - radius - speed; j < xPos - radius; j++)
			{
				if(stage.tile[j][yPos].occupied == By.WALL)
				{
					facing = Move.RIGHT;
					return;
				}
			}
			xPos -= speed;

			generateHitbox();
		}
		else if(facing == Move.UP)
		{
			for(int k = yPos - radius - speed; k < yPos - radius; k++)
			{
				if(stage.tile[xPos][k].occupied == By.WALL)
				{
					facing = Move.DOWN;
					return;
				}
			}
			yPos -= speed;
			
			generateHitbox();
		}
		else if(facing == Move.DOWN)
		{
			for(int k = yPos + radius; k < yPos + radius + speed; k++)
			{
				if(stage.tile[xPos][k].occupied == By.WALL)
				{
					facing = Move.UP;
					return;
				}
			}
			yPos += speed;
			
			generateHitbox();
		}
	}
}