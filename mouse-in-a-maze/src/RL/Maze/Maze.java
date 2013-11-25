/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RL.Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Stack;
import javax.swing.JPanel;

/**
 *
 * @author jesse
 */
public class Maze extends JPanel
{
	private static final int SIZE = 32;
	private final Cell[][] grid = new Cell[SIZE][SIZE];
	private final Random prng = new Random(System.currentTimeMillis());
	private static final int START_Y = 1;

	public Maze()
	{
		final int GENERATED_SIZE = SIZE - 2;
		boolean[][] maze = DepthFirstMazeGen(1, START_Y, GENERATED_SIZE);
		
		//make everything a wall
		for (int j = 0; j < SIZE; j++)
			for (int k = 0; k < SIZE; k++)
				grid[j][k] = new WallCell();
		
		//copy over generated maze
		for (int j = 0; j < GENERATED_SIZE; j++)
			for (int k = 0; k < GENERATED_SIZE; k++)
				grid[j][k] = maze[j][k] ? new OpenCell() : new WallCell();
		
		grid[0][START_Y] = new OpenCell();
		for (int j = 0; j < SIZE; j++)
			grid[SIZE - 1][j] = new OpenCell();
	}



	public final boolean[][] DepthFirstMazeGen(int x, int y, int size)
	{
		//https://en.wikipedia.org/wiki/Maze_generation_algorithm#Depth-first_search
		//http://stackoverflow.com/questions/13182790/dfs-maze-generator
		
		boolean[][] maze = new boolean[size][size];
		for (int j = 0; j < size; j++)
			for (int k = 0; k < size; k++)
				maze[j][k] = false;

		maze[x][y] = true;
		int total = (size * size) / 4;
		int visitedCount = 1;
		int random[] = new int[4];

		Stack<Integer> stack = new Stack<Integer>();
		while (visitedCount < total)
		{
			int neighborCount = 0;
			if (x > 1        && maze[x - 2][y] == false)
				random[neighborCount++] = 1;
			if (x < size - 2 && maze[x + 2][y] == false)
				random[neighborCount++] = 2;
			if (y > 1        && maze[x][y - 2] == false)
				random[neighborCount++] = 3;
			if (y < size - 2 && maze[x][y + 2] == false)
				random[neighborCount++] = 4;

			if (neighborCount > 0)
			{
				switch(random[prng.nextInt(neighborCount)])
				{
					case 1:
						maze[x - 2][y] = maze[x - 1][y] = true;
						x -= 2;
						stack.push(x * size + y);
						visitedCount++;
						break;

					case 2:
						maze[x + 2][y] = maze[x + 1][y] = true;
						x += 2;
						stack.push(x * size + y);
						visitedCount++;
						break;

					case 3:
						maze[x][y - 2] = maze[x][y - 1] = true;
						y -= 2;
						stack.push(x * size + y);
						visitedCount++;
						break;

					case 4:
						maze[x][y + 2] = maze[x][y + 1] = true;
						y += 2;
						stack.push(x * size + y);
						visitedCount++;
						break;
				}
			}
			else
			{
				int vert = stack.pop();
				x = vert / size;
				y = vert % size;
			}
		}

		return maze;
	}



	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		float ratioX = getWidth()  / SIZE;
		float ratioY = getHeight() / SIZE;

		for (int x = 0; x < SIZE; x++)
			for (int y = 0; y < SIZE; y++)
				grid[x][y].draw(g, new Rectangle((int)(x * ratioX), (int)(y * ratioY), (int)ratioX, (int)ratioY));
	}
}
