package projectiles;

import maps.Stage;
import maps.By;
import players.Move;

import java.util.ArrayList;

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

	// Tail Length
	private int tailLength = 10;
	private int[][] tail = new int[tailLength][2];
	private int iterator = 0;
	private boolean deleteTail = false;

	// Delete projectile
	private boolean deleteProjectile = false;

	// Distance traveled
	private int distance = 0;

	public Projectile(int xStart, int yStart, Move facing, int speed, int hitboxLength, Stage stage)
	{
		this.stage = stage;

		this.facing = facing;

		if(this.facing == Move.LEFT)
		{
			xPos = xStart;
			yPos = yStart;
		}
		else if(this.facing == Move.RIGHT)
		{
			xPos = xStart;
			yPos = yStart;
		}
		else if(this.facing == Move.UP)
		{
			xPos = xStart;
			yPos = yStart;
		}
		else if(this.facing == Move.DOWN)
		{
			xPos = xStart;
			yPos = yStart;
		}

		this.speed = speed;

		this.hitboxLength = hitboxLength;

		xPos = xStart;
		yPos = yStart;
		xPosBefore = xPos;
		yPosBefore = yPos;

		generateHitbox();
	}
	public void generateHitbox()
	{
		radius = hitboxLength / 2;

		// Delete tail if iterator exceeds tailLength
		if(iterator > tailLength - 1)
		{	
			//Permanently true from now on
			deleteTail = true;
			iterator = 0;
		}

		if(deleteTail)
		{
			stage.tile[tail[iterator][0]][tail[iterator][1]].occupied = By.FLOOR;
		}

		// Create tail
		stage.tile[xPos][yPos].occupied = By.DEBRIS;
		tail[iterator][0] = xPos;
		tail[iterator][1] = yPos;
		iterator++;

		// Erase previous box
		for(int j = xPosBefore - radius; j < xPosBefore + radius; j++)
		{
			if(stage.tile[j][yPosBefore + radius].occupied != By.DEBRIS)
			{
				fill(j, yPosBefore + radius, By.FLOOR);			
			}
			if(stage.tile[j][yPosBefore - radius].occupied != By.DEBRIS)
			{
				fill(j, yPosBefore - radius, By.FLOOR);			
			}
		}
		for(int k = yPosBefore - radius; k < yPosBefore + radius; k++)
		{
			if(stage.tile[xPosBefore + radius][k].occupied != By.DEBRIS)
			{
				fill(xPosBefore + radius, k, By.FLOOR);			
			}
			if(stage.tile[xPosBefore - radius][k].occupied != By.DEBRIS)
			{
				fill(xPosBefore - radius, k, By.FLOOR);			
			}
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
		if(stage.tile[x][y].occupied != By.WALL)
		{
			stage.tile[x][y].occupied = what;		
		}
	}

	public void move()
	{
		if(deleteProjectile)
		{
			// Delete tail if iterator exceeds tailLength
			if(iterator > tailLength - 1)
			{	
				//Permanently true from now on
				deleteTail = true;
				iterator = 0;
			}

			if(deleteTail)
			{
				stage.tile[tail[iterator][0]][tail[iterator][1]].occupied = By.FLOOR;
			}
			iterator++;

			// Erase box
			for(int j = xPosBefore - radius; j < xPosBefore + radius; j++)
			{
				if(stage.tile[j][yPos + radius].occupied != By.DEBRIS)
				{
					fill(j, yPos + radius, By.FLOOR);			
				}
				if(stage.tile[j][yPos - radius].occupied != By.DEBRIS)
				{
					fill(j, yPos - radius, By.FLOOR);			
				}
			}
			for(int k = yPos - radius; k < yPos + radius; k++)
			{
				if(stage.tile[xPos + radius][k].occupied != By.DEBRIS)
				{
					fill(xPos + radius, k, By.FLOOR);			
				}
				if(stage.tile[xPos - radius][k].occupied != By.DEBRIS)
				{
					fill(xPos - radius, k, By.FLOOR);			
				}
			}
			return;
		}

		// Movement of projectiles
		if(facing == Move.RIGHT)
		{
			for(int j = xPos + radius; j < xPos + radius + speed; j++)
			{
				if(stage.tile[j][yPos].occupied == By.WALL)
				{
					facing = Move.LEFT;
					return;
				}
				else if(stage.tile[j][yPos].occupied == By.PLAYER1 && distance > 5)
				{
					stage.winner = "RED wins!";
				}
				else if(stage.tile[j][yPos].occupied == By.PLAYER2 && distance > 5)
				{
					stage.winner = "BLUE wins!";
				}
			}	

			xPos += speed;			// Move projectile
			distance += speed;		// Distance traveled by projectile

			for(int j = xPos - hitboxLength + speed; j < xPos + hitboxLength; j++)
			{
				for(int k = yPos - hitboxLength; k < yPos + hitboxLength; k++)
				{
					if(stage.tile[j][k].occupied == By.PROJECTILE)
					{
						deleteProjectile = true;
					}
				}
			}

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
				else if(stage.tile[j][yPos].occupied == By.PLAYER1 && distance > 5)
				{
					stage.winner = "RED wins!";
				}
				else if(stage.tile[j][yPos].occupied == By.PLAYER2 && distance > 5)
				{
					stage.winner = "BLUE wins!";
				}
			}

			xPos -= speed;
			distance += speed;

			for(int j = xPos - hitboxLength; j < xPos + hitboxLength - speed; j++)
			{
				for(int k = yPos - hitboxLength; k < yPos + hitboxLength; k++)
				{
					if(stage.tile[j][k].occupied == By.PROJECTILE)
					{
						deleteProjectile = true;
					}
				}
			}

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
				else if(stage.tile[xPos][k].occupied == By.PLAYER1 && distance > 5)
				{
					stage.winner = "RED wins!";
				}
				else if(stage.tile[xPos][k].occupied == By.PLAYER2 && distance > 5)
				{
					stage.winner = "BLUE wins!";
				}
			}

			yPos -= speed;
			distance += speed;

			for(int j = xPos - hitboxLength; j < xPos + hitboxLength; j++)
			{
				for(int k = yPos - hitboxLength - speed; k < yPos; k++)
				{
					if(stage.tile[j][k].occupied == By.PROJECTILE)
					{
						deleteProjectile = true;
					}
				}
			}
			
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
				else if(stage.tile[xPos][k].occupied == By.PLAYER1 && distance > 5)
				{
					stage.winner = "RED wins!";
				}
				else if(stage.tile[xPos][k].occupied == By.PLAYER2 && distance > 5D)
				{
					stage.winner = "BLUE wins!";
				}
			}

			yPos += speed;
			distance += speed;

			for(int j = xPos - hitboxLength; j < xPos + hitboxLength; j++)
			{
				for(int k = yPos; k < yPos + hitboxLength + speed; k++)
				{
					if(stage.tile[j][k].occupied == By.PROJECTILE)
					{
						deleteProjectile = true;
					}
				}
			}
			
			generateHitbox();
		}
	}
}